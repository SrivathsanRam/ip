package floppy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    void setUp() {
        taskList = new TaskList(List.of(
                new Todo("read book"),
                new Deadline("return BOOK", LocalDate.of(2026, 9, 1)),
                new Todo("join sports club")));
    }

    @Test
    void find_partialKeyword_returnsMatchingTasks() {
        List<Task> matches = taskList.find("book");

        assertEquals(2, matches.size());
        assertEquals("read book", matches.get(0).getDescription());
        assertEquals("return BOOK", matches.get(1).getDescription());
    }

    @Test
    void find_mixedCaseKeyword_matchesIgnoringCase() {
        List<Task> matches = taskList.find("SpOrTs");

        assertEquals(1, matches.size());
        assertEquals("join sports club", matches.get(0).getDescription());
    }

    @Test
    void find_absentKeyword_returnsEmptyList() {
        assertTrue(taskList.find("homework").isEmpty());
    }
}
