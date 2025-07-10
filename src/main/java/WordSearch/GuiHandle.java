package WordSearch;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class GuiHandle {

    // gui variables
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextArea wordsText;
    @FXML
    private Label counter;
    @FXML
    private TextField rowsNum;
    @FXML
    private TextField colsNum;
    @FXML
    private TextField fileLocationText;
    @FXML
    private TextField sheetName;
    @FXML
    private Button createButton;
    @FXML
    private Button openFile;
    @FXML
    private Button newFile;
    @FXML
    private Button editFile;

    // gui word counter
    @FXML
    private void typedWords(){
        ObservableList<CharSequence> words = wordsText.getParagraphs();
        int count = words.size();
        counter.setText("Words: " + count);
    }

    @FXML
    private void gridSizeCheck(){

    }

    @FXML
    private void onCreatePuzzleClick(){
        if (!wordsText.getText().matches("[a-zA-Z]+")){
            nonAlphabetDetectedError();
            return;
        }
        ArrayList<String> words = new ArrayList<>();
        Collections.addAll(words, wordsText.getText().toUpperCase().split("\n"));
        if (enoughWords(words)){
            return;
        }

        if (!GridHandling.validGridInput(rowsNum.getText(), colsNum.getText())){
            if (fixGridInputYN()){
                return;
            }
        }

        Optional<char[][]> result = PuzzleGenerator.makePuzzle(words, rowsNum.getText(), colsNum.getText(), GuiHandle::autoSizeYN);

        if (result.isEmpty()){
            placeError();
            return;
        }
        char[][] puzzle = result.get();

    }

    @FXML
    private void newFileClick(){
        return;
    }

    @FXML
    private void openFileClick(){
        return;
    }

    @FXML
    private void editFileClick(){
        return;
    }

    private static boolean enoughWords(ArrayList<String> words){
        if (words.size() < 5){
            enoughWordsError();
            return false;
        }
        else if (words.getLast().isBlank() && words.size() - 2 < 5) {
            enoughWordsError();
            return false;
        }
        return true;
    }

    private static void showError(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private static void enoughWordsError(){
        showError("Word List Error", "The number of words in the words list must be at least 5.");
    }

    private static void nonAlphabetDetectedError(){
        showError("Non Alphabetic Error", "Words can only contain alphabetic letters.\nNumbers, punctuations, and symbols are not allowed");
    }

    private static void placeError(){
        showError("Placement Error", "A word was not able to be placed.\nPlease try using a larger grid.");
    }

    private static boolean showConfirmation(String title, String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        ButtonType yesButton = new ButtonType("Yes");
        ButtonType noButton = new ButtonType("No");
        alert.getButtonTypes().setAll(yesButton, noButton);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == yesButton;
    }

    public static boolean autoSizeYN(){
        return showConfirmation("Grid Size Error", "The grid size is too small.\n Would you like to switch to auto sizing?");
    }

    public static boolean fixGridInputYN(){
        return showConfirmation("Grid Size Error", "The inputted grid size is invalid.\nOnly integers and blank entries are permitted.\n" +
                "Would you like to use auto sizing instead?");
    }
}