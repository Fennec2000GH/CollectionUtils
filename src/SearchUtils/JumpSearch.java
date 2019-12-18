package CollectionUtils.SearchUtils;

import CollectionUtils.base.CLASS;
import java.util.Arrays;
import java.util.List;

public abstract class JumpSearch {
    //MEMBER VARIABLES
    public final static String timeComplexity = "√n";
    public final static String spaceComplexity = "1";
    public final static CLASS searchClass = CLASS.JUMP;

    //MEMBER FUNCTIONS
    public static String getTimeComplexity(){ return JumpSearch.timeComplexity; }

    public static String getSpaceComplexity(){ return JumpSearch.spaceComplexity; }

    public static CLASS getSearchClass(){ return JumpSearch.searchClass; }

    public static int search(List<Number> arr, Number key){
        int jump = (int)Math.sqrt(arr.size()), output = -1;
        for(int i = 0; i <= arr.size() - 1 - jump; i += jump)
            if(arr.get(i).equals(key))
                return i;
            else if(arr.get(i).doubleValue() > key.doubleValue()){
                output = Arrays.binarySearch(arr.subList(i - jump, i + 1).toArray(), key);
                if(output >= 0)
                    output = i - jump + output;
            }
            return output;
    }

    public static int search(Number[] arr, Number key){ return JumpSearch.search(Arrays.asList(arr), key); }

}

