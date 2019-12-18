package CollectionUtils.SearchUtils;

import CollectionUtils.base.CLASS;
import java.util.Arrays;
import java.util.List;

public abstract class InterpolationSearch {
    //MEMBER VARIABLES
    public final static String timeComplexity = "n";
    public final static String spaceComplexity = "1";
    public final static CLASS searchClass = CLASS.INTERPOLATION;

    //MEMBER FUNCTIONS
    public static String getTimeComplexity(){ return InterpolationSearch.timeComplexity; }

    public static String getSpaceComplexity(){ return InterpolationSearch.spaceComplexity; }

    public static CLASS getSearchClass(){ return InterpolationSearch.searchClass; }

    public static int search(List<Number> arr, Number key){
        double indexDensity = arr.size() / (arr.get(arr.size() - 1).doubleValue() - arr.get(0).doubleValue());
        int guessIndex = (int)((key.doubleValue() - arr.get(0).doubleValue()) * indexDensity);
        while(arr.get(guessIndex).doubleValue() > key.doubleValue())
            --guessIndex;
        while(arr.get(guessIndex).doubleValue() < key.doubleValue())
            ++guessIndex;
        if(guessIndex == 0 && !arr.get(0).equals(key) || guessIndex == arr.size() - 1 && !arr.get(arr.size() - 1).equals(key))
            return -1;
        return guessIndex;
    }

    public static int search(Number[] arr, Number key){ return JumpSearch.search(Arrays.asList(arr), key); }

}
