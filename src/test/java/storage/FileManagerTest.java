package storage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTest {
    private FileManager fileManager;
    private final String testFilePath = "./src/test/resources/test_data.json";

    @BeforeEach
    public void setUp() {
        fileManager = new FileManager(testFilePath);
        File file = new File(testFilePath);
        if (file.exists()) {
            file.delete();
        }
    }

    private void createTestFile(String content) throws IOException {
        try (FileWriter writer = new FileWriter(testFilePath)) {
            writer.write(content);
        }
    }

    @Test
    public void testLoadProgrammeList_trivialCase() throws IOException {
        JsonObject testData = new JsonObject();
        testData.add("programmeList", JsonParser.parseString("{ \"programme\": \"test\" }"));
        createTestFile(testData.toString());

        JsonObject result = fileManager.loadProgrammeList();
        assertTrue(result.has("programme"), "Programme list should contain the expected 'programme' key.");
    }

    @Test
    public void testLoadProgrammeList_noProgrammeListKey() throws IOException {
        createTestFile("{}");
        JsonObject result = fileManager.loadProgrammeList();
        assertEquals(0, result.size(), "Programme list should be empty when no 'programmeList' key is present.");
    }

    @Test
    public void testLoadHistory_trivialCase() throws IOException {
        JsonObject testData = new JsonObject();
        testData.add("history", JsonParser.parseString("{ \"date\": \"2024-10-28\" }"));
        createTestFile(testData.toString());

        JsonObject result = fileManager.loadHistory();
        assertTrue(result.has("date"), "History should contain the expected 'date' key.");
    }

    //@Test
    //public void testLoadHistory_fileNotFound() {
    //    JsonObject result = fileManager.loadHistory();
    //    assertTrue(result.isJsonNull() || result.size() == 0, "History should be empty when the file doesn't exist.");
    //}

    @Test
    public void testLoadHistory_noHistoryKey() throws IOException {
        createTestFile("{}");
        JsonObject result = fileManager.loadHistory();
        assertEquals(0, result.size(), "History should be empty when no 'history' key is present.");
    }

    @Test
    public void testSave_trivialCase() throws IOException {
        JsonObject testData = new JsonObject();
        testData.addProperty("key", "value");

        fileManager.save(testData);
        String fileContent = Files.readString(Path.of(testFilePath));
        JsonObject result = JsonParser.parseString(fileContent).getAsJsonObject();

        assertEquals("value", result.get("key").getAsString(), "Saved data should contain the key and value.");
    }

    @Test
    public void testSave_emptyData() throws IOException {
        JsonObject emptyData = new JsonObject();
        fileManager.save(emptyData);

        String fileContent = Files.readString(Path.of(testFilePath));
        JsonObject result = JsonParser.parseString(fileContent).getAsJsonObject();

        assertEquals(0, result.size(), "File should contain an empty JSON object.");
    }
}
