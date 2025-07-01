package WordSearch;

import javafx.scene.control.Alert;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PuzzleGenerator {

    public enum DIRS{
        East(1, 0), South(0, 1), SouthEast(1, 1), NorthEast(1, -1), West(-1, 0), North(0, -1), NorthWest(-1, -1), SouthWest(-1, 1);

        private final int dx;
        private final int dy;

        private DIRS(int dx, int dy) {
            this.dx = dx;
            this.dy = dy;
        }

        public int getDy() {
            return dy;
        }

        public int getDx() {
            return dx;
        }
    }

    public static final int MaxTries = 500;
    public static Random RNG = new Random();


    public static boolean generateWordSearch(char[][] puzzle, ArrayList<String> words){

        for(String word : words){
            for (int tries = 0; tries < MaxTries; tries++){
                int row = RNG.nextInt(puzzle.length);
                int col = RNG.nextInt(puzzle[0].length);

                if (placeWord(puzzle, word, row, col)){
                    break;
                }
                else if (tries == MaxTries - 1){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Placement Error");
                    alert.setContentText("A word was mot able to be placed.\n" +
                            "Please try using a larger grid.");
                    alert.showAndWait();

                    return false;
                }
            }
        }

        return true;
    }

    private static boolean placeWord(char[][] puzzle, String word, int row, int col){
        ArrayList<DIRS> directions = new ArrayList<>(List.of(DIRS.values()));
        Collections.shuffle(directions,RNG);

        for (DIRS direction : directions){
            if (checkPlacement(puzzle, word, row, col, direction)){
                for (int i = 0; i < word.length(); i++){
                    puzzle[row + i * direction.dy][col + i * direction.getDx()] = word.charAt(i);
                }
                return true;
            }
        }

        return false;
    }

    // This method checks whether the placement of a word given the direction of placement will be valid in the puzzle grid.
    private static boolean checkPlacement(char[][] puzzle, String word, int row, int col, DIRS dir){

        for (int i = 0; i < word.length(); i++){
            int newRow = row + i * dir.getDy();
            int newCol = col + i * dir.getDx();
            // returns false if placing cell is out of bounds
            if (newRow < 0 || newCol < 0 || newRow >= puzzle.length || newCol >= puzzle[0].length){
                return false;
            }
            // returns false if puzzle cell is not empty and the new letter doesn't match the one in the puzzle cell
            if (word.charAt(i) != puzzle[newRow][newCol] && puzzle[newRow][newCol] != 0){
                return false;
            }
        }
        return true;
    }

}
