package WordSearch;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;

public class GridHandling {

    // regex check is for any non digit
    public static boolean validGridInput(String rows, String cols){
        return (rows.matches("\\d+") && cols.matches("\\d+")) || (rows.isBlank() && cols.isBlank());
    }

    // this method returns the grid size whether a custom grid size is given or not
    public static Optional<int[]> gridSet(ArrayList<String> words, String rows, String cols, Supplier<Boolean> confirmAutoSize){
        int[] grid = new int[2];
        int row, col;

        if (!rows.isBlank() && !cols.isBlank()){
            try{
                row = Integer.parseInt(rows);
                col = Integer.parseInt(cols);
                if (words.getFirst().length() + 2 > row || words.getFirst().length() + 2 > col){
                    if (confirmAutoSize.get()){
                        grid = defaultGridSet(words);
                    }
                    else return Optional.empty();
                }
                else{
                    grid[0] = row;
                    grid[1] = col;
                }

            }
            catch (NumberFormatException e){
                grid = defaultGridSet(words);
            }
        }
        else grid = defaultGridSet(words);

        return Optional.of(grid);
    }

    // this method holds the default grid sizes based on word count and the largest word.
    private static int[] defaultGridSet(ArrayList<String> words){
        int numWords = words.size();
        int bigWord = words.getFirst().length() + 2;

        if (numWords <= 10 && bigWord <= 10) return new int[]{10, 10};
        if (numWords <= 15 && bigWord <= 12) return new int[]{12, 12};
        if (numWords <= 25 && bigWord <= 15) return new int[]{15, 15};
        if (numWords <= 35 && bigWord <= 20) return new int[]{20, 20};
        if (numWords <= 45 && bigWord <= 25) return new int[]{25, 25};
        if (numWords <= 60 && bigWord <= 10) return new int[]{30, 30};
        return new int[]{35, 35};
    }
}
