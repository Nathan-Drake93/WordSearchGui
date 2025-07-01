package WordSearch;

import java.util.ArrayList;
import java.util.Collections;


public class CreatePuzzle {

    // this method
    public static char[][] makePuzzle(ArrayList<String> solution, String rows, String cols){
        ArrayList<String> words = new ArrayList<>();
        for (String s : solution){
            String r = s.replaceAll(" ", "");
            words.add(r);
        }
        Collections.sort(solution);

        char[][] puzzle;
        PuzzleGenerator.generateWordSearch(puzzle, words);

        return puzzle;
    }
}
