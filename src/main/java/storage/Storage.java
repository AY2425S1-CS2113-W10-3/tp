//@@author Bev-Low

package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import history.DailyRecord;
import history.History;
import programme.ProgrammeList;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
    Storage acts as an adapter layer between the FileManager and BuffBuddy classes,
    translating between JSON and objects
*/
public class Storage {

    private static final Logger logger = Logger.getLogger(Storage.class.getName());
    private final FileManager fileManager;

    public Storage(String path) {
        this.fileManager = new FileManager(path);
    }

    public ProgrammeList loadProgrammeList() {
        try {
            JsonObject programmeListJson = fileManager.loadProgrammeList();
            logger.info("Loading programmeList");
            return programmeListFromJson(programmeListJson);
        } catch (Exception e) {
            logger.info("No programme list found, empty list initialised");
            return new ProgrammeList();
        }
    }

    public History loadHistory() {
        try {
            JsonObject historyJson = fileManager.loadHistory();
            logger.info("Loading history");
            return historyFromJson(historyJson);
        } catch (Exception e) {
            logger.info("No history found, empty history initialised");
            return new History();
        }
    }

    public void saveData(ProgrammeList programmeList, History history) {
        assert programmeList != null : "programmeList must not be null";
        assert history != null : "history must not be null";

        JsonObject jsonObject = createJSON(programmeList, history);
        logger.info("JsonObject containing programme list and history created.");

        try{
            fileManager.save(jsonObject);
        } catch (Exception ignored) {
            // For now, leave this as a quiet failure for simplicity
            // User will be notified of corrupted data when next loading app
        }
    }

    private JsonObject createJSON(ProgrammeList programmeList, History history) {
        JsonObject jsonObject = new JsonObject();

        assert programmeList != null : "programmeList must not be null";
        assert history != null : "history must not be null";

        jsonObject.add("programmeList", programmeListToJson(programmeList));
        logger.info("Programme list converted to JsonObject.");
        jsonObject.add("history", historyToJson(history));
        logger.info("History converted to JsonObject.");
        return jsonObject;
    }

    private JsonObject programmeListToJson(ProgrammeList programmeList) {
        Gson gson = new Gson();
        logger.log(Level.INFO, "Programme list converted to Json for saving.");
        return gson.toJsonTree(programmeList).getAsJsonObject();
    }

    private ProgrammeList programmeListFromJson(JsonObject jsonObject) {
        Gson gson = new Gson();
        logger.log(Level.INFO, "Programme list converted from Json for loading.");
        return gson.fromJson(jsonObject, ProgrammeList.class);
    }

    private JsonObject historyToJson(History history) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new DateSerializer())  // Custom serializer for LocalDate
                .setPrettyPrinting()
                .create();

        JsonObject historyJson = new JsonObject();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LinkedHashMap<LocalDate, DailyRecord> historyMap = history.getHistory(); //To access the Hashmap

        for (LocalDate date : historyMap.keySet()) {
            DailyRecord dailyRecord = historyMap.get(date);
            historyJson.add(date.format(formatter), gson.toJsonTree(dailyRecord));
        }
        logger.log(Level.INFO, "History converted to Json for saving.");
        return historyJson;
    }

    private History historyFromJson(JsonObject jsonObject) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new DateSerializer())  // Custom deserializer for LocalDate
                .create();
        History history = new History();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LinkedHashMap<LocalDate, DailyRecord> historyMap = history.getHistory(); //To access the Hashmap


        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            LocalDate date = LocalDate.parse(entry.getKey(), formatter);
            DailyRecord dailyRecord = gson.fromJson(entry.getValue(), DailyRecord.class);
            historyMap.put(date, dailyRecord);
        }
        logger.log(Level.INFO, "historyJson converted from Json for loading.");
        return history;
    }
}
