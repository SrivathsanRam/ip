package floppy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controls the main Floppy GUI.
 */
public class MainWindow extends AnchorPane {
    private final Image userImage = new Image(
            MainWindow.class.getResourceAsStream("/images/DaUser.png"));
    private final Image floppyImage = new Image(
            MainWindow.class.getResourceAsStream("/images/DaFloppy.png"));

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private Floppy floppy;

    /**
     * Keeps the latest dialog visible as the conversation grows.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Supplies the chatbot that processes commands entered in this window.
     *
     * @param floppy Chatbot instance used by the GUI.
     */
    public void setFloppy(Floppy floppy) {
        this.floppy = floppy;
        dialogContainer.getChildren().add(
                DialogBox.getFloppyDialog(floppy.getWelcomeMessage(), floppyImage));
    }

    /**
     * Displays the user's command and Floppy's response, then clears the input.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) {
            return;
        }

        String response = floppy.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getFloppyDialog(response, floppyImage));
        userInput.clear();

        if (input.equals("bye")) {
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }
}
