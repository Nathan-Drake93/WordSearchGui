package WordSearch;

import java.util.*;
import java.util.function.Supplier;

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


    // this method
    public static Optional<char[][]> makePuzzle(ArrayList<String> solution, String rows, String cols, Supplier<Boolean> confirmAutoSize){
        ArrayList<String> words = new ArrayList<>();
        for (String s : solution){
            String r = s.replaceAll(" ", "");
            words.add(r);
        }
        Collections.sort(solution);
        words.sort(Comparator.comparingInt(String::length).reversed());


        Optional<int[]> gridTry = GridHandling.gridSet(words, rows, cols, confirmAutoSize);
        if (gridTry.isEmpty()){
            return Optional.empty();
        }
        int[] grid = gridTry.get();

        char[][] puzzle = new char[grid[0]][grid[1]];
        if (PuzzleGenerator.generateWordSearch(puzzle, words)){
            return Optional.of(puzzle);
        }
        else return Optional.empty();
    }

    private static boolean generateWordSearch(char[][] puzzle, ArrayList<String> words){

        for(String word : words){
            for (int tries = 0; tries < MaxTries; tries++){
                int row = RNG.nextInt(puzzle.length);
                int col = RNG.nextInt(puzzle[0].length);

                if (placeWord(puzzle, word, row, col)){
                    break;
                }
                else if (tries == MaxTries - 1){
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

            if (newRow < 0 || newCol < 0 || newRow >= puzzle.length || newCol >= puzzle[0].length){
                return false;
            }

            if (word.charAt(i) != puzzle[newRow][newCol] && puzzle[newRow][newCol] != 0){
                return false;
            }
        }
        return true;
    }

}
