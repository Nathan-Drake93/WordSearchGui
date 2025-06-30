package WordSearch;

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


    public static char[][] generateWordSearch(ArrayList<String> words, int rows, int cols){
        char[][] puzzle = new char[rows][cols];

        for(String word : words){
            for (int tries = 0; tries < MaxTries; tries++){
                int row = RNG.nextInt(puzzle.length);
                int col = RNG.nextInt(puzzle[0].length);
                if (!placeWord(puzzle, word, row, col)){
                    if (tries == MaxTries - 1){
                        System.out.println("Not able to place " + word + " into the puzzle");
                    }
                }
                else break;
            }
        }

        return puzzle;
    }

    public static char[][] generateWordSearch(ArrayList<String> words){

        return null;
    }

    public static boolean placeWord(char[][] puzzle, String word, int row, int col){
        ArrayList<DIRS> directions = new ArrayList<DIRS>(List.of(DIRS.values()));
        Collections.shuffle(directions,RNG);

        for (DIRS direction : directions){
            if (checkPlacement()){
                for (int i = 0; i < word.length(); i++){
                    puzzle[row + i * direction.dy][col + i * direction.getDx()] = word.charAt(i);
                }
                return true;
            }
        }

        return false;
    }

    public static boolean checkPlacement(){
        return true;
    }

}
