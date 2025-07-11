package WordSearch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;




public class PuzzleGeneratorTest {

    private ArrayList<String> words;

    @BeforeEach
    void setup(){
        PuzzleGenerator.RNG = new Random(42);
        words = new ArrayList<>(List.of("JAVA", "TEST", "CODE", "PUZZLE", "GENERATE"));
    }

    @Test
    void testMakePuzzle_ReturnsGridForValidInput(){

        Optional<char[][]> result = PuzzleGenerator.makePuzzle(words, "15", "10", () -> false);

        assertTrue(result.isPresent(), "Puzzle should be generated for valid input");

        char[][] grid = result.get();
        assertEquals(15, grid.length, "Row length should be 15");
        assertEquals(10, grid[0].length, "Column length should be 10");
    }

    @Test
    void testMakePuzzle_ReturnsDefaultGridForWTooBigWordsInput(){

        ArrayList<String> bigWords = new ArrayList<>(List.of("JAVA", "TEST", "CODE", "PUZZLE", "GENERATE", "HUGEWORDTHATCANTFIT", "ANOTHERBIGWORD"));
        Optional<char[][]> result = PuzzleGenerator.makePuzzle(bigWords, "15", "10", () -> true);

        assertTrue(result.isPresent(), "Puzzle should be generated with a default grid");

        char[][] grid = result.get();
        assertEquals(25, grid.length, "Row length should be 25");
        assertEquals(25, grid[0].length, "Column length should be 25");
    }

    @Test
    void testMakePuzzle_WordsTooBigFail(){
        ArrayList<String> bigWords = new ArrayList<>(List.of("JAVA", "TEST", "CODE", "PUZZLE", "GENERATE", "HUGEWORDTHATCANTFIT", "ANOTHERBIGWORD"));
        Optional<char[][]> result = PuzzleGenerator.makePuzzle(bigWords, "10", "10", () -> false);

        assertTrue(result.isEmpty(), "When auto size is false, and words are too big it should return empty.");
    }

    @Test
    void testMakePuzzle_WordsPlaced(){

        Optional<char[][]> result = PuzzleGenerator.makePuzzle(words, "10", "10", () -> true);

        assertTrue(result.isPresent());

        char[][] grid = result.get();

        assertTrue(allWordsInGrid(words, grid), "Not all words were placed in the grid");
    }

    private boolean allWordsInGrid(ArrayList<String> words, char[][] grid){

        for (String word : words){
            if (!wordInGrid(word, grid)){
                System.out.println("Word not found in grid: " + word);
                return false;
            }
        }
        return true;
    }

    private boolean wordInGrid(String word, char[][] grid){
        int rows = grid.length;
        int cols = grid[0].length;

        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                for (PuzzleGenerator.DIRS dir : PuzzleGenerator.DIRS.values()){
                    if (matchesAt(word, grid, row, col, dir)){
                        return true;
                    }
                }
            }
        }

        return false;
    }
    private boolean matchesAt(String word, char[][] grid, int startRow,int startCol, PuzzleGenerator.DIRS dir){
        for (int i = 0; i < word.length(); i++) {
            int row = startRow + i * dir.getDy();
            int col = startCol + i * dir.getDx();

            if (row < 0 || col < 0 || row >= grid.length || col >= grid[0].length){
                return false;
            }
            if (grid[row][col] != word.charAt(i)){
                return false;
            }
        }

        return true;
    }
}
