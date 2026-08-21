import java.util.Scanner;

/**
 * Starts the Floppy chatbot and echoes commands until the user exits.
 */
public class Floppy {
    /**
     * Runs the chatbot's command loop.
     *
     * @param args command-line arguments; not used
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
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            System.out.println(divider);

            if (command.equals("bye")) {
                System.out.println(" Bye. Hope to see you again soon!");
                System.out.println(divider);
                break;
            }

            System.out.println(" " + command);
            System.out.println(divider);
        }
        scanner.close();
    }
}
