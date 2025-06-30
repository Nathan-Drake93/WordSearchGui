package WordSearch;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;

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
    void typedWords(){
        ObservableList<CharSequence> words = wordsText.getParagraphs();
        int count = words.size();
        counter.setText("Words: " + words);
    }

    @FXML
    public void onCreatePuzzleClick(){
        ArrayList<String> words = new ArrayList<>();
        Collections.addAll(words, wordsText.getText().toUpperCase().split("\n"));
        String rows = rowsNum.getText();
        String cols = colsNum.getText();
        char[][] puzzle = CreatePuzzle.makePuzzle(words, rows, cols);
    }

}
