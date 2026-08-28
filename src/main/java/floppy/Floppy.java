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

            if (command.equals("list")) {
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println(" " + (i + 1) + "." + tasks[i]);
                }
            } else if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                Task task = tasks[taskIndex];
                task.markAsDone();
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println("   " + task);
            } else if (command.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(command.substring(7)) - 1;
                Task task = tasks[taskIndex];
                task.markAsNotDone();
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println("   " + task);
            } else {
                Task task = null;

                if (command.startsWith("todo ")) {
                    task = new Todo(command.substring("todo ".length()));
                } else if (command.startsWith("deadline ")) {
                    int byIndex = command.indexOf(" /by ");
                    String description = command.substring("deadline ".length(), byIndex);
                    String dueTime = command.substring(byIndex + " /by ".length());
                    task = new Deadline(description, dueTime);
                } else if (command.startsWith("event ")) {
                    int fromIndex = command.indexOf(" /from ");
                    int toIndex = command.indexOf(" /to ", fromIndex);
                    String description = command.substring("event ".length(), fromIndex);
                    String startTime = command.substring(fromIndex + " /from ".length(), toIndex);
                    String endTime = command.substring(toIndex + " /to ".length());
                    task = new Event(description, startTime, endTime);
                }

                if (task != null) {
                    tasks[taskCount] = task;
                    taskCount++;
                    System.out.println("Okies! I've added this task:");
                    System.out.println("   " + task);
                    System.out.println(" Now you have " + taskCount + " tasks in the list.");
                }
            }
            System.out.println(divider);
        }
        scanner.close();
    }
}
