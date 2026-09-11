# Floppy User Guide

Floppy is a task-management chatbot that accepts typed commands through its
JavaFX conversation window.

## Creating a task to complete within a period

Use `period` when a task may be completed at any time between two dates.

Format:

`period DESCRIPTION /from yyyy-MM-dd /to yyyy-MM-dd`

Example:

`period collect certificate /from 2026-09-15 /to 2026-09-25`

Expected response:

```text
Okies! I've added this task:
  [P][ ] collect certificate (within: Sep 15 2026 to Sep 25 2026)
Now you have 1 tasks in the list.
```

The start and end dates are inclusive. The end date cannot be earlier than the
start date. Period tasks can be listed, found, marked, unmarked, and deleted in
the same way as other tasks.

## Other commands

- `todo DESCRIPTION`
- `deadline DESCRIPTION /by yyyy-MM-dd`
- `event DESCRIPTION /from yyyy-MM-dd /to yyyy-MM-dd`
- `list`
- `find KEYWORD`
- `mark TASK_NUMBER`
- `unmark TASK_NUMBER`
- `delete TASK_NUMBER`
- `bye`
