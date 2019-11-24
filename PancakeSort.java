import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;

public abstract class PancakeSort {
    //MEMBER FUNCTIONS
    //ACCESSORS
    public static String getTimeComplexity(){ return "n"; }

    public static String getSpaceComplexity(){ return "log(n)"; }

    public static String getSortClass(){ return "Other"; }

    //MUTATORS
    public static List<Number> sorted(List<Number> arr){
        if(arr == null)
            return null;
        List<Number> output = new ArrayList<>(arr);
        Number maximum = Collections.max(arr.stream().mapToDouble(Number::doubleValue).boxed().collect(Collectors.toList()));
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
