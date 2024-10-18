package core;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import programme.Day;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

public class History {

    // LinkedHashMap to store Day with its respective date in the order of insertion
    private final LinkedHashMap<LocalDateTime, Day> history;

    // Constructor
    public History() {
        history = new LinkedHashMap<>();  // Use LinkedHashMap instead of HashMap
    }

    // Logs a completed day into the history with a given date
    public void logDay(Day day, LocalDateTime date) {
        history.put(date, day);  // This will overwrite if a day with the same date exists
    }

    // Converts the History object to a JSON string
    public JsonObject toJson() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new DateSerializer())
                .create();

        return gson.toJsonTree(this).getAsJsonObject();
    }

    // Creates a History object from a JSON string
    public static History fromJson(JsonObject jsonObject) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new DateSerializer())
                .create();
        return gson.fromJson(jsonObject, History.class);
    }

    // Standard toString method for History class that represents the history
    @Override
    public String toString() {
        StringBuilder historyString = new StringBuilder();

        if (history.isEmpty()) {
            return "No workout history available.";
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Iterate over the history HashMap
        for (LocalDateTime date : history.keySet()) {
            Day day = history.get(date);

            // Adjust the format by removing the extra "Day:" text
            historyString.append(day.toString());  // Use the Day class's toString directly

            // Append the formatted date at the end
            historyString.append(String.format("Completed On: %s%n%n", date.format(formatter)));
        }

        return historyString.toString();
    }

}


