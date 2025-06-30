package wordSearch;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.codec.DecoderException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.awt.Desktop;

public class CreatePuzzle {

    @FXML
    private Button createButton;
    @FXML
    private Button openFile;
    @FXML
    private Button newFile;
    @FXML
    private Button editFile;
    @FXML
    private TextArea wordsText;
    @FXML
    private Label counter;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField fileLocationText;
    @FXML
    private TextField sheetName;
    @FXML
    private TextField rowsNum;
    @FXML
    private TextField colsNum;


    @FXML
    void textAreaTyped(){
        ObservableList<CharSequence> list = wordsText.getParagraphs();
        int words = list.size();
        counter.setText("Words: " + words);
    }

    @FXML
    public void puzzle() throws IOException, DecoderException{
        createPuzzle();
    }
    @FXML
    public void openFile()throws IOException{
        open();
    }
    @FXML
    public void editFile(){
        edit();
    }
    @FXML
    public void newFile() throws IOException{
        createFile();
    }


    public void createPuzzle() throws IOException, DecoderException {
        ArrayList<String> words = new ArrayList<>();
        ArrayList<String> solution = new ArrayList<>();
        Collections.addAll(solution, wordsText.getText().toUpperCase().split("\n"));
        for (String s : solution) {
            String r = s;
            r = r.replace(" ", "");
            words.add(r);
        }
        Collections.sort(words, Comparator.comparingInt(String::length).reversed());
        Collections.sort(solution);
        char[][] puzzle; //= PuzzleUtil.generateWordSearch(words, null);

        if (rowsNum == null || rowsNum.getText().isEmpty() || colsNum.getText().isEmpty() || colsNum.getText() == null){
            puzzle = PuzzleUtil.generateWordSearch(words);
        }

        else {
            ArrayList<Integer> grid = new ArrayList<>();
            Collections.addAll(grid, Integer.parseInt(rowsNum.getText()), Integer.parseInt(colsNum.getText()));
            puzzle = PuzzleUtil.generateWordSearch(words, grid);
        }

        // copying the word puzzle set to the solution set for use in highlighting the answer
       char[][] puzzleSolution = new char[puzzle.length][];
        for (int i = 0; i < puzzle.length; i++) {
            puzzleSolution[i] = new char[puzzle[i].length];
            System.arraycopy(puzzle[i], 0, puzzleSolution[i], 0, puzzle[i].length);
        }

        //PuzzleUtil.printPuzzle(puzzleSolution);
        PuzzleUtil.fillPuzzle(puzzle);

        Excel.excel(puzzle, sheetName.getText(), fileLocationText.getText(), solution, puzzleSolution);
    }

    // opens selected file
    public void open() throws IOException{
        if (fileLocationText == null || fileLocationText.getText().isEmpty()){
           FileChooser chooser = new FileChooser();
           chooser.setTitle("Open File");
           Stage stage = (Stage)anchorPane.getScene().getWindow();
           fileLocationText.setText(chooser.showOpenDialog(stage).getPath());
        }

        File file = new File(fileLocationText.getText());
        Desktop desktop = Desktop.getDesktop();
           if (file.exists()){
               desktop.open(file);
           }

    }

    // creates a new Excel file
    public void createFile() throws IOException{
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("New File");
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        TextInputDialog name = new TextInputDialog();
        name.setHeaderText("Enter new file name.");
        String file = chooser.showDialog(stage).getPath();
        name.showAndWait();
        String s = name.getEditor().getText();
        file = file + "\\" + s + ".xlsx";
        fileLocationText.setText(file);
        Excel.newFile(file);

    }

    // selects a file that has already been created
    public void edit(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Edit File");
        Stage stage = (Stage)anchorPane.getScene().getWindow();
        fileLocationText.setText(chooser.showOpenDialog(stage).getPath());
    }

}
