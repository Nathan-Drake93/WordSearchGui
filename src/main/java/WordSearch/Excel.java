package WordSearch;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
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

    public static Optional<Workbook> addPuzzle(char[][] puzzle, char[][] solution, ArrayList<String> wordList, String sheetName, File file){
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.createSheet(getSheetName(workbook,sheetName));
            CellStyle solutionColor = setCellStyle(workbook);

            for (int rowNum = 0; rowNum < puzzle.length; rowNum++) {
                Row row = sheet.createRow(rowNum + 1);
                for (int colNum = 0; colNum < puzzle[0].length; colNum++) {
                    Cell gridCell = row.createCell(colNum + 3);
                    gridCell.setCellValue(Character.toString(puzzle[rowNum][colNum]));

                    if (solution[rowNum][colNum] == puzzle[rowNum][colNum]){
                        gridCell.setCellStyle(solutionColor);
                    }
                    if (rowNum == puzzle.length - 1){
                        sheet.autoSizeColumn(colNum + 3);
                    }
                }
            }
            for (int word = 0; word < wordList.size(); word++) {
                Row wordRow;
                if (sheet.getRow(1 + word) == null){
                    wordRow = sheet.createRow(1 + word);
                }
                else {
                    wordRow = sheet.getRow(1 + word);
                }

                Cell wordCell = wordRow.createCell(1);
                wordCell.setCellValue(wordList.get(word));

                if (word == wordList.size() - 1){
                    sheet.autoSizeColumn(1);
                }
            }

            return Optional.of(workbook);
        }
        catch (IOException | InvalidFormatException e){
            return Optional.empty();
        }
    }

    private static String getSheetName(Workbook workbook, String sheetName){
        boolean sheetNameCheck = false;
        String nameBase = sheetName.endsWith("_") ? sheetName : sheetName + "_";
        int count = 1;
        do {
            if (workbook.getSheetIndex(sheetName) != -1){
                sheetNameCheck = true;
                sheetName = nameBase + count;
                count++;
            }
            else {
                sheetNameCheck = false;
            }
        }
        while (sheetNameCheck);

        return sheetName;
    }

    private static CellStyle setCellStyle(Workbook workbook){
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return cellStyle;
    }
}
