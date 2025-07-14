package WordSearch;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;


public class Excel {

    public static Workbook newFile(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Home Sheet");
        sheet.createRow(0).createCell(0).setCellValue("This is a blank sheet used to create the Excel file");
        sheet.autoSizeColumn(0);

        return workbook;
    }

    public static Optional<Workbook> addPuzzle(char[][] puzzle, char[][] solution, ArrayList<String> wordList, File file){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            return Optional.of(workbook);
        }
        catch (IOException | InvalidFormatException e){
            return Optional.empty();
        }
    }
}
