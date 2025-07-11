package WordSearch;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class GridHandlingTest {

    private ArrayList<String> words;

    @BeforeEach
    void setUp(){
        words = new ArrayList<>(List.of("JAVA", "TEST", "CODE", "PUZZLE", "GENERATE"));
    }

    @Test
    void validGridInputTestFail(){
        // All of these should return false
        assertFalse(GridHandling.validGridInput("a", "15"));
        assertFalse(GridHandling.validGridInput("a", "b"));
        assertFalse(GridHandling.validGridInput("15", "b"));
        assertFalse(GridHandling.validGridInput("5 3","15"));
        assertFalse(GridHandling.validGridInput("15","5 3"));
        assertFalse(GridHandling.validGridInput("15", "."));
        assertFalse(GridHandling.validGridInput(".", "15"));
        assertFalse(GridHandling.validGridInput("15", ""));
        assertFalse(GridHandling.validGridInput("", "15"));
        assertFalse(GridHandling.validGridInput("15", " "));
        assertFalse(GridHandling.validGridInput(" ", "15"));
    }

    @Test
    void validGridInputTestPass(){
        // All of these should return true
        assertTrue(GridHandling.validGridInput("15", "5"));
        assertTrue(GridHandling.validGridInput("", ""));
        assertTrue(GridHandling.validGridInput(" ", " "));
    }

    @Test
    void gridSetPass(){
        Optional<int[]> result = GridHandling.gridSet(words, "10", "10", () -> false);

        assertTrue(result.isPresent());
        int[] grid = result.get();
        assertEquals(10, grid[0], "Row should return 10");
        assertEquals(10, grid[1], "Column should return 10");
    }

    @Test
    void gridSetFail(){
        Optional<int[]> result = GridHandling.gridSet(words, "3", "3", () -> false);

        assertTrue(result.isEmpty(), "grid size array return should be empty");
    }

    @Test
    void defaultGridSetBlankTest(){
        Optional<int[]> result = GridHandling.gridSet(words, "", "", () -> false);

        assertTrue(result.isPresent());
        int [] grid = result.get();
        assertEquals(10, grid[0], "Default row size for the number of words and largest word should be 10");
        assertEquals(10, grid[1], "Default column size for the number of words and largest word should be 10");
    }

    @Test
    void defaultGridSetCustomGridToSmallTest(){
        Optional<int[]> result = GridHandling.gridSet(words, "3", "3", () -> true);

        assertTrue(result.isPresent());
        int[] grid = result.get();
        assertEquals(10, grid[0], "Default row size for the number of words and largest word should be 10");
        assertEquals(10, grid[1], "Default column size for the number of words and largest word should be 10");
    }
}
