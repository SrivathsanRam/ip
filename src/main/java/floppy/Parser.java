package floppy;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Converts raw user input into commands that the chatbot can execute.
 */
public class Parser {
    /**
     * Parses a complete line of user input.
     *
     * @param commandText Raw command entered by the user.
     * @return Structured command ready for execution.
     * @throws FloppyException If the command is unknown or malformed.
     */
    public Command parse(String commandText) throws FloppyException {
        String command = commandText.trim();
        if (command.equals("bye")) {
            return new Command(CommandType.BYE);
        }
        if (command.equals("list")) {
            return new Command(CommandType.LIST);
        }
        if (command.startsWith("mark")) {
            return new Command(CommandType.MARK, parseTaskIndex(command, "mark"));
        }
        if (command.startsWith("unmark")) {
            return new Command(CommandType.UNMARK, parseTaskIndex(command, "unmark"));
        }
        if (command.startsWith("delete")) {
            return new Command(CommandType.DELETE, parseTaskIndex(command, "delete"));
        }
        return new Command(parseTask(command));
    }

    /**
     * Parses the positive task number in an indexed command.
     *
     * @param command Full command entered by the user.
     * @param commandWord Command word before the task number.
     * @return Zero-based task index.
     * @throws FloppyException If the task number is missing or invalid.
     */
    private int parseTaskIndex(String command, String commandWord) throws FloppyException {
        String argument = command.substring(commandWord.length()).trim();
        try {
            int taskIndex = Integer.parseInt(argument) - 1;
            if (taskIndex < 0) {
                throw new NumberFormatException();
            }
            return taskIndex;
        } catch (NumberFormatException exception) {
            throw new FloppyException("Give me a valid task number after " + commandWord + ".");
        }
    }

    /**
     * Parses a todo, deadline, or event command into a task.
     *
     * @param command Full command entered by the user.
     * @return Task represented by the command.
     * @throws FloppyException If the task command is unknown or incomplete.
     */
    private Task parseTask(String command) throws FloppyException {
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
            return parseDeadline(command);
        }
        if (command.startsWith("event")) {
            return parseEvent(command);
        }
        throw new FloppyException("I don't recognise that command.");
    }

    /**
     * Parses a deadline command containing a description and due date.
     *
     * @param command Full deadline command.
     * @return Deadline represented by the command.
     * @throws FloppyException If the description or due date is invalid.
     */
    private Deadline parseDeadline(String command) throws FloppyException {
        int byIndex = command.indexOf(" /by ");
        if (!command.startsWith("deadline ") || byIndex < 0) {
            throw new FloppyException("Use: deadline DESCRIPTION /by yyyy-MM-dd.");
        }
        String description = command.substring("deadline ".length(), byIndex).trim();
        String dueDateText = command.substring(byIndex + " /by ".length()).trim();
        if (description.isEmpty() || dueDateText.isEmpty()) {
            throw new FloppyException("A deadline needs both a description and due date.");
        }
        return new Deadline(description, parseDate(dueDateText));
    }

    /**
     * Parses an event command containing a description and date range.
     *
     * @param command Full event command.
     * @return Event represented by the command.
     * @throws FloppyException If the description or date range is invalid.
     */
    private Event parseEvent(String command) throws FloppyException {
        int fromIndex = command.indexOf(" /from ");
        int toIndex = command.indexOf(" /to ", Math.max(fromIndex, 0));
        if (!command.startsWith("event ") || fromIndex < 0 || toIndex < 0) {
            throw new FloppyException("Use: event DESCRIPTION /from yyyy-MM-dd /to yyyy-MM-dd.");
        }
        String description = command.substring("event ".length(), fromIndex).trim();
        String startDateText = command.substring(fromIndex + " /from ".length(), toIndex).trim();
        String endDateText = command.substring(toIndex + " /to ".length()).trim();
        if (description.isEmpty() || startDateText.isEmpty() || endDateText.isEmpty()) {
            throw new FloppyException("An event needs a description, start date, and end date.");
        }
        LocalDate startDate = parseDate(startDateText);
        LocalDate endDate = parseDate(endDateText);
        if (endDate.isBefore(startDate)) {
            throw new FloppyException("The event end date cannot be before its start date.");
        }
        return new Event(description, startDate, endDate);
    }

    /**
     * Parses an ISO date entered in {@code yyyy-MM-dd} format.
     *
     * @param dateText Date supplied by the user.
     * @return Parsed date.
     * @throws FloppyException If the date is not a valid ISO date.
     */
    private LocalDate parseDate(String dateText) throws FloppyException {
        try {
            return LocalDate.parse(dateText);
        } catch (DateTimeParseException exception) {
            throw new FloppyException("Use dates in yyyy-MM-dd format, such as 2026-08-28.");
        }
    }
}
