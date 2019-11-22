import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class BeadSort {
    //MEMBER FUNCTIONS
    //ACCESSORS
    public static String getTimeComplexity(){ return "n"; }

    public static String getSpaceComplexity(){ return "log(n)"; }

    public static String getSortClass(){ return "Other"; }

    //MUTATORS
    public static List<Number> sorted(List<Number> arr){
        if(arr == null)
            return null;
        Number maximum = Collections.max(arr.stream().mapToDouble(Number::doubleValue).boxed().collect(Collectors.toList()));
        int[] digitCount = new int[(int)Math.log10(maximum.doubleValue())];
        Arrays.fill(digitCount, 0);
        for(Number el : arr)
            for(int digitIndex = 0; digitIndex <= (int)Math.log10(el.doubleValue()); digitIndex++)
                ++digitCount[digitIndex];
        List<Number> output = new ArrayList<>();
        for(int digitIndex = 0; digitIndex <= digitCount.length - 2; digitIndex++)
            output.add(digitCount[digitIndex] - digitCount[digitIndex + 1]);
        output.add(digitCount[digitCount.length - 1]);
        return output;
    }

    public static List<Number> sorted(Number[] arr){ return BeadSort.sorted(Arrays.asList(arr)); }

}
