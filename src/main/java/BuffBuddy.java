// @@author TVageesan
import command.Command;
import command.CommandResult;
import command.ExitCommand;
import storage.Storage;

import history.History;
import parser.Parser;
import ui.Ui;
import programme.ProgrammeList;

public class BuffBuddy {
    private static final String DEFAULT_FILE_PATH = "./data/data.json";
    private final Ui ui;

    private final History history;
    private final ProgrammeList programmes;
    private final Storage storage;
    private final Parser parser;
    private boolean isRunning;


    public BuffBuddy(String filePath) {
        ui = new Ui();
        parser = new Parser();
        storage = new Storage(filePath);

        programmes = storage.loadProgrammeList();
        history = storage.loadHistory();

        isRunning = true;
    }

    public static void main(String[] args) {
        new BuffBuddy(DEFAULT_FILE_PATH).run();
    }

    public void run() {
        ui.showWelcome();
        while (isRunning) {
            handleCommand();
        }
        ui.showFarewell();
        storage.saveData(programmes, history);
    }

    private void handleCommand() {
        try {
            String fullCommand = ui.readCommand();
            Command command = parser.parse(fullCommand);
            CommandResult result = command.execute(programmes, history);
            ui.showMessage(result);

            if (command instanceof ExitCommand) {
                isRunning = false;
            }

        } catch (Exception e) {
            ui.showMessage(e);
        }
    }
}

