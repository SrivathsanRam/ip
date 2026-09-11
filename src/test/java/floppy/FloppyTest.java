package floppy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class FloppyTest {
    @Test
    void getResponse_invalidCommand_returnsErrorMessage() {
        Floppy floppy = new Floppy();

        String response = floppy.getResponse("dance");

        assertTrue(response.contains("I don't recognise that command."));
    }

    @Test
    void getResponse_byeCommand_returnsGoodbyeMessage() {
        Floppy floppy = new Floppy();

        String response = floppy.getResponse("bye");

        assertTrue(response.contains("Bye. Hope to see you again soon!"));
    }
}
