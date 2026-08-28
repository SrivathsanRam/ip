package floppy;

/**
 * Represents a task that occurs over a period of time.
 */
public class Event extends Task {
    private final String startTime;
    private final String endTime;

    /**
     * Creates an event with the specified description and time period.
     *
     * @param description Description of the event.
     * @param startTime Start time of the event.
     * @param endTime End time of the event.
     */
    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + startTime + " to: " + endTime + ")";
    }
}
