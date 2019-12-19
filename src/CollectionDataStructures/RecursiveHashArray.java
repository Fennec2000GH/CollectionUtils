package CollectionUtils.CollectionDataStructures;

import java.util.Arrays;
import java.util.function.Function;
import java.util.Objects;
import com.google.common.base.Preconditions;

enum MARKER {START, INTERMEDIATE, TERMINAL}

class RecursiveHashArray <T> {
    //MEMBER VARIABLES
    /** Recursive array that holds objects of its own <code>class</code>>
     */
    private RecursiveHashArray<T>[] arr;

    private Function<T, Integer>[] hashFunctions;
    /** <code>int</code> array denoting length of cells i.e. the number of hash arrays from the next higher dimension
     * that can be stored
     */
    private int[] shape;

    /** The level of recursion synonymous with axis of dimension, where 0 denotes 1st dimension, 1 denotes 2nd dimension,
     * 2 denotes 3rd dimension, and so on
     */
    private int axis;

    /** Indicates whether current array is the top-level, somewhere in the middle, or terminal hash array having no more
     * arrays branching off
     */
    private MARKER marker;

    /** Only if <code>terminal</code> is <code>true</code>, then inserted element is stored here
     */
    private T data;

    //MEMBER FUNCTIONS
    //CONSTRUCTORS 
    /** Constructor builds new recursive hash array with given dimensions and length of each dimension used
     * @param newShape <code>int</code> array denoting how many next-dimension hash arrays, or if terminal, how many
     *                 elements can be stored with the current dimension's hash array
     */
    public RecursiveHashArray(Function<T, Integer>[] hashFunctionArr, int[] newShape){
        try {
            Preconditions.checkNotNull(hashFunctionArr, "Array of hash functions cannot be null!");
            Preconditions.checkNotNull(newShape, "Shape array cannot be null!");
            Preconditions.checkArgument(Arrays.stream(hashFunctionArr).parallel().noneMatch(Objects::isNull),
                    "Each hash function must not be null!");
            Preconditions.checkArgument(hashFunctionArr.length == shape.length,
                    "The number of hash functions must match the dimensionality i.e. length of shape!");
            Preconditions.checkArgument(Arrays.stream(newShape).parallel().allMatch(n -> n >= 1),
                    "All dimension sizes must be positive!");
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
            return;
        }
        this.hashFunctions = hashFunctionArr;
        this.shape = newShape;
        this.recursiveBuilder(newShape, 0);
    }

    /** Copy constructor
     * @param newArr Another array of <code>RecursiveHashArray</code> objects to copy over
     * @param deepCopy Whether to merge reference or perform deep copy of entire possibly multidimensional array
     */
    public RecursiveHashArray(RecursiveHashArray<T>[] newArr, boolean deepCopy){
        try {
            Preconditions.checkNotNull(newArr, "Array to copy from must not be null!");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return;
        }
        if(deepCopy) {
            this.recursiveDeepCopy(this.arr, newArr);
            this.hashFunctions = new Function[newArr.length];
//            for(int i = 0; i < newArr.length; i++)
//                this.hashFunctions[i] = newArr[0].hashFunctions[i];
        } else
            this.arr = newArr;
    }

    //ACCESSORS
    /** Find the total number of axis involved in hashing and storing an element
     * @return N where N represents the N-dimensional array the overall recursive hash array is built
     */
    public int getDimension(){ return this.shape.length; }

    //MUTATORS
    /** Adds new element to recursive hash array
     * @param el Element to be added
     * @return <code>true</code> if element has not been previously added
     */
    public boolean add(T el){
        try {
            Preconditions.checkNotNull(el, "Element cannot be null!");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        int[] hashValues = new int[this.getDimension()];
        for(int i = 0; i < this.getDimension(); i++)
            hashValues[i] = this.hashFunctions[i].apply(el) % this.shape[i];
        if(this.getDimension() == 1)
            if(this.arr[hashValues[0]].data ==null) {
                this.arr[hashValues[0]].data = el;
                return true;
            } else
                return false;
        RecursiveHashArray<T>[] currentArr = this.arr;
        for(int j = 0; j < this.getDimension() - 1; j++)
            currentArr = currentArr[hashValues[j]].arr;
        if(currentArr[this.getDimension() - 1].data == null){
            currentArr[this.getDimension() - 1].data = el;
            return true;
        }
        return false;
    }

    /** Builds recursive array structure from scratch
     * @param newShape <code>int</code> array denoting how many next-dimension hash arrays, or if terminal, how many
     *      *                 elements can be stored with the current dimension's hash array
     * @param currentAxis Current axis number denoting which dimension is being focused on
     */
    public void recursiveBuilder(int[] newShape, int currentAxis){
        try {
            Preconditions.checkNotNull(shape, "Shape array cannot be null!");
            Preconditions.checkElementIndex(currentAxis, shape.length);
        } catch (IndexOutOfBoundsException | NullPointerException e) {
            return;
        }
        switch (currentAxis) {
            case 0 : { this.marker = MARKER.START; break; }
            default : {
                if(currentAxis < shape.length - 1)
                    this.marker = MARKER.INTERMEDIATE;
                else
                    this.marker = MARKER.TERMINAL;
            }
        }
        this.arr = new RecursiveHashArray[shape[currentAxis]];
        for(int i = 0; i < shape[currentAxis]; i++)
            this.arr[i].recursiveBuilder(shape, currentAxis + 1);
    }

    /** Performs deep copy of multidimensional recursive hash array without needing to know number of dimensions
     * @param destination Array to be modified
     * @param source Array to be copied from
     */
    private void recursiveDeepCopy(RecursiveHashArray<T>[] destination, RecursiveHashArray<T>[] source){
        try {
            Preconditions.checkNotNull(source, "Source array cannot be null!");
        } catch (NullPointerException n) {
            return;
        }
        destination = new RecursiveHashArray[source.length];
        for(int i = 0; i < source.length; i++) {
            if(source[i].arr == null) {
                destination[i].data = source[i].data;
                destination[i].marker = MARKER.TERMINAL;
                continue;
            }
            this.recursiveDeepCopy(destination[i].arr, source[i].arr);
        }
    }

}