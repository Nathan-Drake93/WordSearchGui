package WordSearch;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

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

    private Window getWindow(){
        return anchorPane.getScene().getWindow();
    }

    // live gui word counter
    @FXML
    private void typedWords(){
        ObservableList<CharSequence> words = wordsText.getParagraphs();
        int count = words.size();
        counter.setText("Words: " + count);
    }

    // row/column valid input live check
    @FXML
    private void liveGridCheck(){
        boolean rowsValid = rowsNum.getText().matches("\\d*") || rowsNum.getText().isBlank();
        boolean colsValid = colsNum.getText().matches("\\d*") || colsNum.getText().isBlank();

        rowsNum.setStyle(rowsValid ? null : "-fx-border-color: red; -fx-border-width: 2;");
        colsNum.setStyle(colsValid ? null : "-fx-border-color: red; -fx-border-width: 2;");
    }

    // puzzle creation block
    @FXML
    private void onCreatePuzzleClick(){
        if (!wordsText.getText().matches("[a-zA-Z]+")){
            DialogService.nonAlphabetDetectedError();
            return;
        }
        ArrayList<String> words = new ArrayList<>();
        Collections.addAll(words, wordsText.getText().toUpperCase().split("\n"));
        if (enoughWords(words)){
            return;
        }

        if (!GridHandling.validGridInput(rowsNum.getText(), colsNum.getText())){
            if (DialogService.fixGridInputYN()){
                return;
            }
        }

        Optional<char[][]> puzzleTry = PuzzleGenerator.makePuzzle(words, rowsNum.getText(), colsNum.getText(), DialogService::autoSizeYN);

        if (puzzleTry.isEmpty()){
            DialogService.placeError();
            return;
        }

        char[][] puzzle = puzzleTry.get();
        char[][] solution = PuzzleGenerator.copyGrid(puzzle);
        PuzzleGenerator.fillGrid(puzzle);

        words.sort(null);
        Optional<Boolean> saveTry = FileHandling.saveFile(fileLocationText.getText(), puzzle, solution, words);
        if (saveTry.isPresent()){
            boolean error = saveTry.get();
            if (error){
                DialogService.fileNotFoundError(fileLocationText.getText());
            }
            else{
                DialogService.excelFormatError();
            }
        }
        else{
            DialogService.saveSuccess();
        }

    }

    private static boolean enoughWords(ArrayList<String> words){
        if (words.size() < 5){
            DialogService.enoughWordsError();
            return false;
        }
        else if (words.getLast().isBlank() && words.size() - 2 < 5) {
            DialogService.enoughWordsError();
            return false;
        }
        return true;
    }

    // File Handling Block
    @FXML
    private void newFileClick(){
        Optional<String> tryDirectory = DialogService.directoryPicker(getWindow());
        if (tryDirectory.isEmpty()){
            DialogService.directoryNotFoundError(" ");
            return;
        }
        String directoryPath = tryDirectory.get();

        Optional<String> tryFileName = DialogService.newFileName();
        if (tryFileName.isEmpty()){
            return;
        }
        String fileName = tryFileName.get();

        if (!FileHandling.newFile(fileName, directoryPath)){
            return;
        }

        fileLocationText.setText(directoryPath + "\\" + fileName);
    }

    @FXML
    private void openFileClick(){
        Optional<String> tryFile = DialogService.filePicker(getWindow());
        if (tryFile.isEmpty()){
            DialogService.fileNotFoundError(" ");
            return;
        }
        String filePath = tryFile.get();

        if (!FileHandling.openFile(filePath)){
            DialogService.fileNotFoundError(filePath);
        }
    }

    @FXML
    private void editFileClick(){
        Optional<String> tryFile = DialogService.filePicker(getWindow());
        if (tryFile.isEmpty()){
            DialogService.fileNotFoundError(" ");
            return;
        }
        String filePath = tryFile.get();
        fileLocationText.setText(filePath);
    }
}