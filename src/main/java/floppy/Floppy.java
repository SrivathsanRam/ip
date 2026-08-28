package floppy;

/**
 * Coordinates command parsing, task operations, storage, and user interaction.
 */
public class Floppy {
    private static final String DATA_FILE_PATH = "data/floppy.txt";

    private final Parser parser;
    private final Storage storage;
    private final TaskList taskList;
    private final Ui ui;

    /**
     * Creates a chatbot backed by the default task data file.
     */
    public Floppy() {
        parser = new Parser();
        storage = new Storage(DATA_FILE_PATH);
        ui = new Ui();

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (FloppyException exception) {
            ui.showError(exception.getMessage());
            loadedTasks = new TaskList();
        }
        taskList = loadedTasks;
    }

    /**
     * Runs the chatbot until the user exits or closes the input stream.
     */
    public void run() {
        ui.showWelcome();
        while (ui.hasNextCommand()) {
            String commandText = ui.readCommand();
            ui.showLine();
            try {
                Command command = parser.parse(commandText);
                if (command.getCommandType() == CommandType.BYE) {
                    ui.showGoodbye();
                    ui.showLine();
                    break;
                }
                execute(command);
            } catch (FloppyException exception) {
                ui.showError(exception.getMessage());
            }
            ui.showLine();
        }
        ui.close();
    }

    /**
     * Starts the chatbot application.
     *
     * @param args Command-line arguments; not used.
     */
    public static void main(String[] args) {
        new Floppy().run();
    }

    /**
     * Executes a parsed command and persists every task-list change.
     *
     * @param command Parsed command to execute.
     * @throws FloppyException If the task operation or save fails.
     */
    private void execute(Command command) throws FloppyException {
        switch (command.getCommandType()) {
            case LIST -> ui.showTaskList(taskList.getTasks());
            case MARK -> {
                Task task = taskList.mark(command.getTaskIndex());
                storage.save(taskList.getTasks());
                ui.showTaskMarked(task);
            }
            case UNMARK -> {
                Task task = taskList.unmark(command.getTaskIndex());
                storage.save(taskList.getTasks());
                ui.showTaskUnmarked(task);
            }
            case DELETE -> {
                Task task = taskList.delete(command.getTaskIndex());
                storage.save(taskList.getTasks());
                ui.showTaskDeleted(task, taskList.size());
            }
            case ADD -> {
                Task task = command.getTask();
                taskList.add(task);
                storage.save(taskList.getTasks());
                ui.showTaskAdded(task, taskList.size());
            }
            case BYE -> throw new IllegalStateException("Exit commands are handled by the run loop.");
            default -> throw new IllegalStateException("Unsupported command type.");
        }
    }
}
