package wordSearch;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class PuzzleUtil {
    public enum DIRS {
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

    public static final int SEED = 6;

    //    public static final Random RNG = new Random(SEED);
    public static final Random RNG = new Random();


    public static String puzzleDate(){
        YearMonth date = YearMonth.now().plusMonths(1);
        String s = date.getMonth() + "" + date.getYear();
        return s;
    }

    public static char[][] generateWordSearch(ArrayList<String> words, ArrayList<Integer> grid){

        char[][] puzzle = new char[grid.get(0)][grid.get(1)];


        for (int i = 0; i < words.size(); i++) {
            for (int tries = 0; tries < MaxTries; tries++) {

                int row = RNG.nextInt(puzzle.length);
                int col = RNG.nextInt(puzzle[0].length);

                if (!placeWord(puzzle, row, col, words.get(i), grid)) {

                    if (tries == MaxTries - 1) {
                        System.out.println("Not able to place " + words.get(i) + " into the puzzle");
                    }
                } else break;

            }

        }
//        fillPuzzle(puzzle);
        return puzzle;
    }

    public static char[][] generateWordSearch(ArrayList<String> words){

        ArrayList<Integer> size = new ArrayList<>(gridSize(words));
        char[][] puzzle = new char[size.get(0)][size.get(1)];

        for (int i = 0; i < words.size(); i++) {
            for (int tries = 0; tries < MaxTries; tries++) {

                int row = RNG.nextInt(puzzle.length);
                int col = RNG.nextInt(puzzle[0].length);

                if (!placeWord(puzzle, row, col, words.get(i), size)) {

                    if (tries == MaxTries - 1) {
                        System.out.println("Not able to place " + words.get(i) + " into the puzzle");
                    }
                } else break;

            }

        }
        //fillPuzzle(puzzle);
        return puzzle;
    }

    public static boolean placeWord( char[][] grid, int row, int col, String word, ArrayList<Integer> size){

        boolean goodPlacement = false;
        int count = 0;

        while (count < DIRS.values().length && !goodPlacement){

            ArrayList<DIRS> alreadySelected = new ArrayList<>();
            DIRS dir = DIRS.values()[RNG.nextInt(DIRS.values().length)];


            if (alreadySelected.contains(dir)){
                continue;
            }

            else{
                count++;
                alreadySelected.add(dir);
                if (checkPlacement(grid, row, col, word, size, dir)){
                    goodPlacement = true;
                    for (int i = 0; i < word.length(); i++){
                        grid[row + i * dir.getDy()][col + i * dir.getDx()] = word.charAt(i);
                    }
                }
            }

        }
        return goodPlacement;
    }

    public static boolean checkPlacement(char[][] grid, int row, int col, String word,ArrayList<Integer> size, DIRS dir){

        boolean goodPlacement = false;

        for ( int i = 0; i < word.length(); i++){
            if (((row + i * dir.getDy()) >= 0) && ((row + i * dir.getDy()) < size.get(0)) && ((col + i * dir.getDx() >= 0)) && col + i * dir.getDx() < size.get(1)){
                goodPlacement = true;
                if (grid[row + i * dir.getDy()][col + i * dir.getDx()] != 0 && grid[row + i * dir.getDy()][col + i * dir.getDx()] != word.charAt(i)){
                    goodPlacement = false;
                    break;
                }
            }
            else{
                goodPlacement = false;
                break;
            }

        }

        return goodPlacement;
    }

    public static ArrayList<Integer> gridSize(ArrayList<String> words){

        ArrayList<Integer> size = new ArrayList<>();

        if (words.size() < 10 && words.size() > 0) {
            //16x14
            Collections.addAll(size, 16, 14);
        }
        else if (words.size() > 9){
            //20x20
            Collections.addAll(size, 20, 20);
        }
        return size;
    }


    public static void fillPuzzle(char[][] grid){
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                if (grid[i][j] == '\0'){
                    grid[i][j] = (char) (RNG.nextInt(26) + 'A');
                }
            }
        }
    }

    public static void printPuzzle(char[][] grid){

        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printSolution(ArrayList<String> solutions){
        for (String word : solutions) {
            System.out.println(word);
        }
    }

}