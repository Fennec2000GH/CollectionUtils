import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class CustomizedIndexArray<K> {
    //MEMBER VARIABLES
    private int startIndex;
    private Function<Integer, Integer> inverseFunction;
    private K[] arr;

    //MEMBER FUNCTIONS
    //CONSTRUCTORS
    //Default constructor implements conventional 0-based indexing
    public CustomizedIndexArray(){
        this.startIndex = 0;
        this.inverseFunction = i -> i ;
    }

    //Constructor allows customization of array settings
    public CustomizedIndexArray(int startIndex, Function<Integer, Integer> func){
        this.startIndex = startIndex;
        this.inverseFunction = func;
    }

    //ACCESSORS
    //Gets element stored at given customized index
    public K get(int customizedIndex){
        int normalIndex = this.inverseFunction.apply(customizedIndex);
        if(normalIndex < 0 || normalIndex >= this.arr.length)
            return null;
        return this.arr[normalIndex];
    }

    //Counts number of non-null elements
    public int size(){ return (int)Arrays.stream(this.arr).filter(Objects::nonNull).count(); }

    //MUTATORS
    //Sets element at valid customized index
    public void set(int customizedIndex, K element){
        int normalIndex = this.inverseFunction.apply(customizedIndex);
        if(normalIndex < 0 || normalIndex >= this.arr.length)
            return;
        this.arr[normalIndex] = element;
    }

    //Clears out all elements
    public void clear(){ Arrays.fill(this.arr, null); }

}
