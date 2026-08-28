package floppy;

/**
 * Represents a task and its completion status.
 */
public abstract class Task {
    /** Description of the task. */
    protected String description;

    /** Whether the task has been completed. */
    protected boolean isDone;

    private final TaskType taskType;

    /**
     * Creates a task with the specified description and type.
     *
     * @param description Description of the task.
     * @param taskType Category of the task.
     */
    protected Task(String description, TaskType taskType) {
        this.description = description;
        this.isDone = false;
        this.taskType = taskType;
    }

    /**
     * Returns an icon representing the task's completion status.
     *
     * @return {@code X} if the task is completed, or a blank space otherwise.
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + taskType.getSymbol() + "][" + getStatusIcon() + "] " + description;
    }
}
