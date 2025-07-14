package wordSearch;

import java.io.*;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;


public class Excel {

    public static void newFile(String fileName) throws IOException{
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Home Sheet");
        sheet.createRow(0);
        sheet.createRow(0).createCell(0).setCellValue("The Crafty Geo");
        sheet.autoSizeColumn(0);

        File file = new File(fileName);
        FileOutputStream fileOut = new FileOutputStream(file);
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();

    }

    public static void excel(char[][] puzzle, String sheetName, String fileName, ArrayList<String> solution, char[][] puzzleSolution) throws IOException, NullPointerException, DecoderException {

//        String date = PuzzleUtil.puzzleDate();
        File file = new File(fileName);

        XSSFWorkbook workbook = null;
        if (file.exists()) {
        try {
            workbook = new XSSFWorkbook(new FileInputStream(file));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            }
        }
        else workbook = new XSSFWorkbook();

        FileOutputStream fileOut = new FileOutputStream(file);
        Sheet sheet;
        int num = 1;
        String name = sheetName;
        while (workbook.getSheetIndex(name) != -1){
            name = sheetName + num;
            num++;
            //System.out.println(name);
        }
        sheet = workbook.createSheet(name);

        //        Sheet sheet = workbook.createSheet("AUGUST2023");

        // printing puzzle to excel sheet
        for (int i = 0; i < puzzle.length; i++){
        Row row = sheet.createRow(1 + i);
        for (int j = 0; j < puzzle[0].length; j++){
            Cell cell = row.createCell(3 + j);
            cell.setCellValue(Character.toString(puzzle[i][j]));
            }
        }

        // coloring puzzle solution yellow
        String rgbS = "FFFF00";
        byte[] rgbB = Hex.decodeHex(rgbS);
        XSSFColor color = new XSSFColor(rgbB, null);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillForegroundColor(color);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        for (int i = 0; i < puzzleSolution.length; i++){
            Row row = sheet.getRow(1 + i);
            for (int j = 0; j < puzzleSolution[0].length; j++){
                if (puzzleSolution[i][j] != '\0'){
                    Cell cell = row.getCell(3 + j);
                    cell.setCellStyle(cellStyle);
                }
            }
        }

        // printing solution on left hand side of sheet
        for (int x = 0; x < solution.size(); x++){
            Row row;
        if (sheet.getRow(2 + x) == null){
            row = sheet.createRow(2 + x);
        }
        else {
            row = sheet.getRow(2 + x);
        }
        Cell cell = row.createCell(0);
        cell.setCellValue(solution.get(x));
        }

        for (int i = 0; i < 3 + puzzle[0].length; i++){
        sheet.autoSizeColumn(i);
        }
        workbook.write(fileOut);


        fileOut.close();
        workbook.close();
    }
}