package WordSearch;

import org.apache.poi.ss.usermodel.Workbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class FileHandling {

    public static boolean openFile(String filePath){
        File file = new File(filePath);
        Desktop desktop = Desktop.getDesktop();

        try{
            desktop.open(file);
        }
        catch (IOException e){
            return false;
        }
        return true;
    }

    public static boolean newFile(String fileName, String directoryName){
        File file = new File(directoryName + "\\" + fileName);
        if (file.exists()){
            if (!DialogService.overwriteYN(file.getPath())){
                return false;
            }
        }
        try (Workbook workbook = Excel.newFile(); FileOutputStream fileOut = new FileOutputStream(file)){
            workbook.write(fileOut);
        }
        catch (IOException e){
            DialogService.fileNotFoundError(file.getPath());
            return false;
        }

        return true;
    }

    public static Optional<Boolean> saveFile(String filePath, char [][] puzzle, char[][] solution, ArrayList<String> wordList, String sheetName){
        File file = new File(filePath);
        if (!file.exists()){
            return Optional.of(true);
        }

        Optional<Workbook> workbookTry = Excel.addPuzzle(puzzle, solution, wordList, sheetName, file);
        if (workbookTry.isEmpty()){
            return Optional.of(false);
        }
        try (Workbook workbook = workbookTry.get(); FileOutputStream fileOut = new FileOutputStream(file)){
            workbook.write(fileOut);
        }
        catch (IOException e){
            return Optional.of(true);
        }

        return Optional.empty();
    }
}
