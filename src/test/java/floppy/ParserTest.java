package floppy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ParserTest {
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    void parse_todoCommand_returnsAddCommand() throws FloppyException {
        Command command = parser.parse("todo read book");

        assertEquals(CommandType.ADD, command.getCommandType());
        assertInstanceOf(Todo.class, command.getTask());
        assertEquals("read book", command.getTask().getDescription());
    }

    @Test
    void parse_deadlineCommand_parsesIsoDate() throws FloppyException {
        Command command = parser.parse("deadline return book /by 2026-09-01");

        Deadline deadline = assertInstanceOf(Deadline.class, command.getTask());
        assertEquals(LocalDate.of(2026, 9, 1), deadline.getDueDate());
    }

    @Test
    void parse_eventWithReversedDates_throwsException() {
        assertThrows(FloppyException.class,
                () -> parser.parse("event camp /from 2026-09-03 /to 2026-09-01"));
    }

    @Test
    void parse_invalidDate_throwsException() {
        assertThrows(FloppyException.class,
                () -> parser.parse("deadline return book /by 2026-02-30"));
    }

    @Test
    void parse_indexedCommand_convertsToZeroBasedIndex() throws FloppyException {
        Command command = parser.parse("mark 2");

        assertEquals(CommandType.MARK, command.getCommandType());
        assertEquals(1, command.getTaskIndex());
    }

    @Test
    void parse_unknownCommand_throwsException() {
        assertThrows(FloppyException.class, () -> parser.parse("dance"));
    }

    @Test
    void parse_findCommand_returnsKeyword() throws FloppyException {
        Command command = parser.parse("find project book");

        assertEquals(CommandType.FIND, command.getCommandType());
        assertEquals("project book", command.getKeyword());
    }

    @Test
    void parse_findWithoutKeyword_throwsException() {
        assertThrows(FloppyException.class, () -> parser.parse("find"));
    }
}
