import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;

public abstract class DoubleSelectionSort {
    //MEMBER VARIABLES
    public final static String timeComplexity = "n^2";
    public final static String spaceComplexity = "log(n)";
    public final static CLASS sortClass = CLASS.OTHER;

    //MEMBER FUNCTIONS
    public static String getTimeComplexity(){ return PancakeSort.timeComplexity; }

    public static String getSpaceComplexity(){ return PancakeSort.spaceComplexity; }

    public static CLASS getSortClass(){ return PancakeSort.sortClass; }

    public static List<Number> sorted(List<Number> arr){
        List<Number> output = new ArrayList<>(arr);
        int leftIndex = 0, rightIndex = arr.size() - 1;
        Number maximum, minimum;
        int frequency;
        while(rightIndex - leftIndex >= 1){
            maximum = Collections.max(arr.
                    subList(leftIndex, rightIndex + 1)
                    .stream()
                    .mapToDouble(Number::doubleValue)
                    .boxed()
                    .collect(Collectors.toList()));
            minimum = Collections.min(arr
                    .subList(leftIndex, rightIndex + 1)
                    .stream()
                    .mapToDouble(Number::doubleValue)
                    .boxed()
                    .collect(Collectors.toList()));
            frequency = Collections.frequency(output.subList(leftIndex, rightIndex + 1), maximum);
            output.removeAll(List.of(maximum));
            rightIndex = rightIndex - frequency;
            for(int i = 1; i <= frequency; i++)
                output.add(rightIndex + 1, maximum);
            if(leftIndex == rightIndex)
                break;
            frequency = Collections.frequency(output.subList(leftIndex, rightIndex + 1), minimum);
            output.removeAll(List.of(minimum));
            for(int i = 1; i <= frequency; i++)
                output.add(leftIndex, minimum);
            leftIndex = leftIndex + frequency;
        }
        return output;
    }

    public static List<Number> sorted(Number[] arr){ return DoubleSelectionSort.sorted(Arrays.asList(arr)); }
}

