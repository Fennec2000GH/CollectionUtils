package CollectionUtils.SortingUtils;

import CollectionUtils.base.CLASS;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public abstract class PigeonholeSort {
    //MEMBER VARIABLES
    public final static String timeComplexity = "n + Range";
    public final static String spaceComplexity = "Range";
    public final static CLASS sortClass = CLASS.LINEAR;

    //MEMBER FUNCTIONS
    public static String getTimeComplexity(){ return PancakeSort.timeComplexity; }

    public static String getSpaceComplexity(){ return PancakeSort.spaceComplexity; }

    public static CLASS getSortClass(){ return PancakeSort.sortClass; }

    public static List<Integer> sorted(List<Integer> arr){
        int maximum = Collections.max(arr), minimum = Collections.min(arr);
        int span = maximum - minimum + 1;
        HashMap<Integer, List<Integer>> bucketList = new HashMap<>(span);
        for(int el : arr)
            if(bucketList.containsKey(el))
                bucketList.get(el).add(el);
            else
                bucketList.put(el, List.of(el));
        List<Integer> output = bucketList.values()
                .stream()
                .filter(Objects::nonNull)
                .sorted()
                .reduce(new ArrayList<>(), (current, next) -> {List<Integer> list = current; list.addAll(next); return list; });
        return output;
    }

    public static List<Integer> sorted(Integer[] arr){ return PigeonholeSort.sorted(Arrays.asList(arr)); }

}
