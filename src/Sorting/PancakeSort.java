import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;

public abstract class PancakeSort {
    //MEMBER VARIABLES
    public final static String timeComplexity = "n";
    public final static String spaceComplexity = "log(n)";
    public final static CLASS sortClass = CLASS.OTHER;

    //MEMBER FUNCTIONS
    public static String getTimeComplexity(){ return PancakeSort.timeComplexity; }

    public static String getSpaceComplexity(){ return PancakeSort.spaceComplexity; }

    public static CLASS getSortClass(){ return PancakeSort.sortClass; }

    public static List<Number> sorted(List<Number> arr){
        if(arr == null)
            return null;
        List<Number> output = new ArrayList<>(arr);
        double maximum = Collections.max(arr.stream().mapToDouble(Number::doubleValue).boxed().collect(Collectors.toList()));
        int maxIndex = output.lastIndexOf(maximum), endIndex = arr.size() - 1;
        while(endIndex > 0){
            Collections.reverse(output.subList(0, maxIndex + 1));
            Collections.reverse(output.subList(0, endIndex + 1));
            --endIndex;
            maximum = Collections.max(output.subList(0, endIndex + 1).stream().mapToDouble(Number::doubleValue).boxed().collect(Collectors.toList()));
            maxIndex = output.subList(0, endIndex + 1).lastIndexOf(maximum);
        }
        return output;
    }

    public static List<Number> sorted(Number[] arr){ return PancakeSort.sorted(Arrays.asList(arr)); }
}
