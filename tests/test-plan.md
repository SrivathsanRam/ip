# Manual test plan

## Add a task to complete within a period

1. Start Floppy with `./gradlew run`.
2. Enter `period collect certificate /from 2026-09-15 /to 2026-09-25`.
3. Verify that Floppy adds a `[P]` task and displays both dates.
4. Restart Floppy and enter `list`.
5. Verify that the period task and both dates were restored.

## Reject a reversed completion period

1. Enter `period collect certificate /from 2026-09-25 /to 2026-09-15`.
2. Verify that Floppy reports that the end date cannot precede the start date.
3. Enter `list` and verify that the invalid task was not added.

## Reject incomplete period commands

1. Enter each of the following commands separately:
   - `period`
   - `period collect certificate`
   - `period collect certificate /from 2026-09-15`
2. Verify that each command displays the required command format and does not
   add a task.
