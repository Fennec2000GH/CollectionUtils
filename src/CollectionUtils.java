import java.util.List;

enum CLASS {BINARY, INTERPOLATION, JUMP, LINEAR, BUBBLE, INSERTION, SELECTION, DIVIDE_AND_CONQUER, SLOW, OTHER}
public abstract class CollectionUtils {
    //MEMBER VARIABLES

    //MEMBER FUNCTIONS
    //SEARCHING

    public static int jumpSearch(List<Number> arr, Number key){ return JumpSearch.search(arr, key); }
    public static int jumpSearch(Number[] arr, Number key){ return JumpSearch.search(arr, key); }
    public static int interpolationSort(List<Number> arr, Number key){ return InterpolationSearch.search(arr, key); }
    public static int interpolationSort(Number[] arr, Number key){ return InterpolationSearch.search(arr, key); }

    //SORTING
    public static void beadSort(List<Integer> arr){
        List<Integer> sortedArr = BeadSort.sorted(arr);
        for(int i = 0; i <= arr.size() - 1; i++)
            arr.set(i, sortedArr.get(i));
    }
    public static void beadSort(Integer[] arr){
        List<Integer> sortedArr = BeadSort.sorted(arr);
        for(int i = 0; i <= arr.length - 1; i++)
            arr[i] = sortedArr.get(i);
    }
    public static void doubleSelectionSort(List<Number> arr){
        List<Number> sortedArr = DoubleSelectionSort.sorted(arr);
        for(int i = 0; i <= arr.size() - 1; i++)
            arr.set(i, sortedArr.get(i));
    }
    public static void doubleSelectionSort(Number[] arr){
        List<Number> sortedArr = DoubleSelectionSort.sorted(arr);
        for(int i = 0; i <= arr.length - 1; i++)
            arr[i] = sortedArr.get(i);
    }
    public static void pancakeSort(List<Number> arr){
        List<Number> sortedArr = PancakeSort.sorted(arr);
        for(int i = 0; i <= arr.size() - 1; i++)
            arr.set(i, sortedArr.get(i));
    }
    public static void pancakeSort(Number[] arr){
        List<Number> sortedArr = PancakeSort.sorted(arr);
        for(int i = 0; i <= arr.length - 1; i++)
            arr[i] = sortedArr.get(i);
    }
    public static void pigeonholeSort(List<Integer> arr){
        List<Integer> sortedArr = PigeonholeSort.sorted(arr);
        for(int i = 0; i <= arr.size() - 1; i++)
            arr.set(i, sortedArr.get(i));
    }
    public static void pigeonholeSort(Integer[] arr){
        List<Integer> sortedArr = PigeonholeSort.sorted(arr);
        for(int i = 0; i <= arr.length - 1; i++)
            arr[i] = sortedArr.get(i);
    }

}

