package floppy;

/**
 * Represents a task that must be completed by a given time.
 */
public class Deadline extends Task {
    private final String dueTime;

    /**
     * Creates a deadline with the specified description and due time.
     *
     * @param description Description of the deadline.
     * @param dueTime Due time of the deadline.
     */
    public Deadline(String description, String dueTime) {
        super(description, TaskType.DEADLINE);
        this.dueTime = dueTime;
    }

    /**
     * Returns the due time as entered by the user.
     *
     * @return Due time of this deadline.
     */
    public String getDueTime() {
        return dueTime;
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + dueTime + ")";
    }
}
