package WordSearch;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.Optional;

public class DialogService {

    // File Input Popup Block
    public static Optional<String> filePicker(Window parent){
        FileChooser chooseFile = new FileChooser();
        chooseFile.setTitle("Select File");
        File file = chooseFile.showOpenDialog(parent);
        if (file == null || file.getPath().isBlank()){
            return Optional.empty();
        }
        return Optional.of(file.getPath());
    }

    public static Optional<String> directoryPicker(Window parent){
        DirectoryChooser chooseDirectory = new DirectoryChooser();
        chooseDirectory.setTitle("Select Save Folder");
        File directory = chooseDirectory.showDialog(parent);
        if (directory == null || directory.getPath().isBlank()){
            return Optional.empty();
        }
        return Optional.of(directory.getPath());
    }

    public static Optional<String> newFileName(){
        TextInputDialog newFileName = new TextInputDialog();
        newFileName.setTitle("New File");
        newFileName.setContentText("Enter the name of the new word search file.");
        newFileName.showAndWait();

        String result = newFileName.getResult();
        if (result == null || result.isBlank()) {
            return Optional.empty();
        }
        if (!result.matches("[a-zA-Z_]+")){
            newFileNameError(result);
            return Optional.empty();
        }

        return Optional.of(result);
    }

    // Message Popup Block
    public static void saveSuccess(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Save Status");
        alert.setContentText("Saving of puzzle successful.");
    }

    // Error Popup Block
    private static void showError(String title, String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void excelFormatError(){
        String message = """
                The selected file is not a valid Excel workbook or is an unsupported format.
                Please ensure the file is a .xlsx or .xls workbook and try again.
                If the problem persists, verify that the file is not corrupted or password protected""";
        showError("Excel Invalid Format Error", message);
    }

    public static void newFileNameError(String fileName){
        String message = String.format("The chosen file name \"%s\" is invalid. Only alphabetic characters and the _ special symbol is allowed",fileName);
        showError("New File Name Error", message);
    }

    public static void directoryNotFoundError(String directoryName){
        String message = String.format("The chosen directory \"%s\" cannot be found.\nPlease double check the directory name and path", directoryName);
        showError("Directory Not Found Error", message);
    }

    public static void fileNotFoundError(String filePath){
        String message = String.format("The chosen file \"%s\" cannot be found.\nPlease double check the file name and path", filePath);
        showError("File Not Found Error", message);
    }

    public static void enoughWordsError(){
        showError("Word List Error", "The number of words in the words list must be at least 5.");
    }

    public static void nonAlphabetDetectedError(){
        showError("Non Alphabetic Error", "Words can only contain alphabetic letters.\nNumbers, punctuations, and symbols are not allowed");
    }

    public static void placeError(){
        showError("Placement Error", "A word was not able to be placed.\nPlease try using a larger grid.");
    }

    // Confirmation block
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
        return showConfirmation("Grid Size Input Error", """
                The inputted grid size is invalid.
                Only integers and blank entries are permitted.
                Would you like to use auto sizing instead?""");
    }

    public static boolean overwriteYN(String filePath){
        String message = String.format("The file \"%s\" already exists.\nWould you like to overwrite this file.", filePath);
        return showConfirmation("File Already Exists", message);
    }
}
