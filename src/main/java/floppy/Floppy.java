package floppy;

import java.util.Scanner;

/**
 * Starts the Floppy chatbot and stores tasks until the user exits.
 */
public class Floppy {
    /**
     * Runs the chatbot's command loop.
     *
     * @param args Command-line arguments; not used.
     */
    public static void main(String[] args) {
        String divider = "____________________________________________________________";
        String banner = " _____ _                         \n"
                + "|  ___| | ___  _ __  _ __  _   _\n"
                + "| |_  | |/ _ \\| '_ \\| '_ \\| | | |\n"
                + "|  _| | | (_) | |_) | |_) | |_| |\n"
                + "|_|   |_|\\___/| .__/| .__/ \\__, |\n"
                + "               |_|   |_|    |___/ \n";
        System.out.println(banner);
        System.out.println("Hello! I'm Floppy.");
        System.out.println("What can I do for you?");
        System.out.println(divider);

        Scanner scanner = new Scanner(System.in);
        Task[] tasks = new Task[100];
        int taskCount = 0;

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(divider);

            if (command.equals("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(divider);
                break;
            }

            try {
                if (command.equals("list")) {
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println(" " + (i + 1) + "." + tasks[i]);
                    }
                } else if (command.startsWith("mark")) {
                    int taskIndex = parseTaskIndex(command, "mark", taskCount);
                    Task task = tasks[taskIndex];
                    task.markAsDone();
                    System.out.println(" Nice! I've marked this task as done:");
                    System.out.println("   " + task);
                } else if (command.startsWith("unmark")) {
                    int taskIndex = parseTaskIndex(command, "unmark", taskCount);
                    Task task = tasks[taskIndex];
                    task.markAsNotDone();
                    System.out.println(" OK, I've marked this task as not done yet:");
                    System.out.println("   " + task);
                } else {
                    Task task = createTask(command);

                    if (taskCount >= tasks.length) {
                        throw new FloppyException("The task list is full.");
                    }
                    tasks[taskCount] = task;
                    taskCount++;
                    System.out.println("Okies! I've added this task:");
                    System.out.println("   " + task);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                }
            } catch (FloppyException exception) {
                System.out.println(" Oops! " + exception.getMessage());
            }
            System.out.println(divider);
        }
        scanner.close();
    }

    /**
     * Parses and validates the task number in a mark or unmark command.
     *
     * @param command Full command entered by the user.
     * @param commandWord Command word that precedes the task number.
     * @param taskCount Number of tasks currently stored.
     * @return Zero-based index of the selected task.
     * @throws FloppyException If the task number is missing, invalid, or out of range.
     */
    private static int parseTaskIndex(String command, String commandWord, int taskCount)
            throws FloppyException {
        String argument = command.substring(commandWord.length()).trim();
        try {
            int taskIndex = Integer.parseInt(argument) - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
                throw new FloppyException("Choose a task number shown in the list.");
            }
            return taskIndex;
        } catch (NumberFormatException exception) {
            throw new FloppyException("Give me a valid task number after " + commandWord + ".");
        }
    }

    /**
     * Creates a task from a todo, deadline, or event command.
     *
     * @param command Full command entered by the user.
     * @return Task represented by the command.
     * @throws FloppyException If the command is unknown or required details are missing.
     */
    private static Task createTask(String command) throws FloppyException {
        if (command.equals("todo")) {
            throw new FloppyException("The todo description cannot be empty.");
        }
        if (command.startsWith("todo ")) {
            String description = command.substring("todo ".length()).trim();
            if (description.isEmpty()) {
                throw new FloppyException("The todo description cannot be empty.");
            }
            return new Todo(description);
        }
        if (command.startsWith("deadline")) {
            return createDeadline(command);
        }
        if (command.startsWith("event")) {
            return createEvent(command);
        }
        throw new FloppyException("I don't recognise that command.");
    }

    /**
     * Creates a deadline from a command containing a description and due time.
     *
     * @param command Full deadline command.
     * @return Deadline represented by the command.
     * @throws FloppyException If the description or due time is missing.
     */
    private static Deadline createDeadline(String command) throws FloppyException {
        int byIndex = command.indexOf(" /by ");
        if (!command.startsWith("deadline ") || byIndex < 0) {
            throw new FloppyException("Use: deadline DESCRIPTION /by TIME.");
        }
        String description = command.substring("deadline ".length(), byIndex).trim();
        String dueTime = command.substring(byIndex + " /by ".length()).trim();
        if (description.isEmpty() || dueTime.isEmpty()) {
            throw new FloppyException("A deadline needs both a description and due time.");
        }
        return new Deadline(description, dueTime);
    }

    /**
     * Creates an event from a command containing a description and time period.
     *
     * @param command Full event command.
     * @return Event represented by the command.
     * @throws FloppyException If the description, start time, or end time is missing.
     */
    private static Event createEvent(String command) throws FloppyException {
        int fromIndex = command.indexOf(" /from ");
        int toIndex = command.indexOf(" /to ", Math.max(fromIndex, 0));
        if (!command.startsWith("event ") || fromIndex < 0 || toIndex < 0) {
            throw new FloppyException("Use: event DESCRIPTION /from START /to END.");
        }
        String description = command.substring("event ".length(), fromIndex).trim();
        String startTime = command.substring(fromIndex + " /from ".length(), toIndex).trim();
        String endTime = command.substring(toIndex + " /to ".length()).trim();
        if (description.isEmpty() || startTime.isEmpty() || endTime.isEmpty()) {
            throw new FloppyException("An event needs a description, start time, and end time.");
        }
        return new Event(description, startTime, endTime);
    }
}
