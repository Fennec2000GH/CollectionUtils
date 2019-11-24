import java.util.Arrays;
import java.util.List;

public class BinarySearch {
    //MEMBER VARIABLES
    public final static String timeComplexity = "n";
    public final static String spaceComplexity = "1";
    public final static CLASS searchClass = CLASS.BINARY;

    //MEMBER FUNCTIONS
    public static String getTimeComplexity(){ return InterpolationSearch.timeComplexity; }

    public static String getSpaceComplexity(){ return InterpolationSearch.spaceComplexity; }

    public static CLASS getSearchClass(){ return InterpolationSearch.searchClass; }

    public static int search(List<Number> arr, Number key){
        int a = 0, b = arr.size(), mid = 0;
        while(a < b){
            mid = (a + b) / 2;
            if(arr.get(mid).equals(key))
                return mid;
            else if(arr.get(mid).doubleValue() > key.doubleValue())
                b = mid;
            else
                a = mid;
        }
        return -1;
    }

    public static int search(Number[] arr, Number key){ return BinarySearch.search(Arrays.asList(arr), key); }

}
