import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BeadSort {
    //MEMBER VARIABLES
    public final static String timeComplexity = "n";
    public final static String spaceComplexity = "log(n^2)";
    public final static CLASS sortClass = CLASS.OTHER;

    //MEMBER FUNCTIONS
    public static String getTimeComplexity(){ return BeadSort.timeComplexity; }

    public static String getSpaceComplexity(){ return BeadSort.spaceComplexity; }

    public static CLASS getSortClass(){ return BeadSort.sortClass; }

    public static List<Integer> sorted(List<Integer> arr){
        if(arr == null)
            return null;
        List<Integer> output = new ArrayList<>();
        int maximum = Collections.max(arr), minimum = Collections.min(arr);
        int span = maximum - minimum + 1;
        int[] digitCount = new int[span];
        Arrays.fill(digitCount, 0);
        for(int el : arr)
            for(int digitIndex = 0; digitIndex <= el - minimum; digitIndex++)
                ++digitCount[digitIndex];
        for(int i = 0; i <= span - 2; i++)
            output.addAll(Collections.nCopies(digitCount[i] - digitCount[i + 1], i + minimum));
        output.addAll(Collections.nCopies(digitCount[span - 1], maximum));
        return output;
    }

    public static List<Integer> sorted(Integer[] arr){ return BeadSort.sorted(Arrays.asList(arr)); }

}
