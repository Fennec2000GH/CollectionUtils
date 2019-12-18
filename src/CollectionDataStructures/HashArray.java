package CollectionUtils.CollectionDataStructures;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;
import com.google.common.base.Preconditions;

enum TYPE {SET, MULTISET, LIMITED_MULTISET}

/**
 * @param <T> Data type of data
 */
public class HashArray<T> {
    //MEMBER VARIABLES
    /** Hash structure using 2D array with space-saving techniques
     */
    private TreeMap<T, Integer>[] hashArray;

    /** User-set hash function used to determine hash values for storing elements
     */
    private Function<T, Integer> hashFunction;

    /** Emulation of a specific type of set, essentially whether to allow duplicate values or not
     */
    private TYPE setType;

    /** Number of duplicates allowed per distinct element - used solely when <code>setType</code> is
     * <code>LIMITED_MULTISET</code>
     */
    private int duplicateCapacity;

    /** Maximum number of distinct elements allowed per hash value
     */
    private int chainSequenceCapacity;

    //MEMBER FUNCTIONS
    //CONSTRUCTORS
    /** Constructor applies custom <code>hashFunction</code> and creates new <code>hashArray</code>
     * @param hashFunc New custom hash function
     * @param hashValueRange Countable integer range from 0 to a positive number that denotes hash values employed
     */
    public HashArray(Function<T, Integer> hashFunc, int hashValueRange){
        try {
            Preconditions.checkNotNull(hashFunc, "Hash function must exist!");
            Preconditions.checkArgument(hashValueRange > 0,
                    "Number of possible hash values must be positive!");
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
        this.hashArray = new TreeMap[hashValueRange];
        for(int i = 0; i < this.getMaximumHashValue(); i++)
            this.hashArray[i] = new TreeMap<>();
        this.hashFunction = hashFunc;
        this.chainSequenceCapacity = -1;
    }

    /** Constructor applies custom <code>hashFunction</code>, sets type of set to be modeled after, and creates new
     * <code>hashArray</code>
     * @param hashFunc New custom hash function
     * @param hashValueRange Countable integer range from 0 to a positive number that denotes hash values employed
     * @param newType Value of enum class <code>TYPE</code> describing which schema of a set to use
     */
    public HashArray(Function<T, Integer> hashFunc, int hashValueRange, TYPE newType){
        this(hashFunc, hashValueRange);
        this.setType = newType;
    }

    /** Constructor applies custom <code>hashFunction</code>, sets type of set to be modeled after, sets new capacity
     * for each hash value chain sequence, and creates new <code>hashArray</code>
     * @param hashFunc New custom hash function
     * @param hashValueRange Countable integer range from 0 to a positive number that denotes hash values employed
     * @param newType Value of enum class <code>TYPE</code> describing which schema of a set to use
     * @param newCapacity Positive <code>int</code> value denoting maximum number of elements allowed per chain sequence
     */
    public HashArray(Function<T, Integer> hashFunc, int hashValueRange, TYPE newType, int newCapacity){
        this(hashFunc, hashValueRange, newType);
        this.chainSequenceCapacity = newCapacity;
    }

    //ACCESSORS
    /** Checks whether given value is already stored
     * @param el Element to check for containment
     * @return <code>true</code> if <code>el</code> is found somewhere in <code>hashArray</code>
     */
    public boolean contains(T el){
        int hashValue = Math.abs(this.hashFunction.apply(el)) % (this.getMaximumHashValue() + 1);
        if(this.hashArray[hashValue] == null)
            return false;
        return this.hashArray[hashValue].containsKey(el);
    }

    /** Finds currently set capacity on number of distinct elements allowed per chain sequence
     * @return Current chain sequence capacity
     */
    public int getChainSequenceCapacity(){ return this.chainSequenceCapacity; }

    /** Finds the current number of elements that can be stored, including the ones already stored
     * @return Number of possible hash values times chain sequence capacity if such capacity is enforced, otherwise
     * <code>Integer.MAX_VALUE</code> if chain sequence capacity is not enforced i.e. there is no limit
     */
    public int getCapacity(){
        if(this.getChainSequenceCapacity() == -1)
            return Integer.MAX_VALUE;
        return (this.getMaximumHashValue() + 1) * this.getChainSequenceCapacity();
    }

    /** Finds the number of duplicates allowed per distinct element, which is only positive when <code>hashArray</code>
     * is modeled after <code>LIMITED_MULTISET</code>
     * @return Duplicate capacity
     */
    public int getDuplicateCapacity() {
        if(this.getSetType() != TYPE.LIMITED_MULTISET)
            return -1;
        return this.duplicateCapacity;
    }

    /** Finds the largest usable hash value
     * @return 1 less than length of hash array
     */
    public int getMaximumHashValue(){ return this.hashArray.length - 1; }

    /** Finds the type of set
     * @return An enum value under enum class <code>TYPE</code>
     */
    public TYPE getSetType(){ return this.setType; }

    /** Finds the number of non-null elements currently being stored
     * @param countDuplicates Whether to count each element stored multiple times as 1 or as number of duplicates
     * @return The number of elements being stored
     */
    public int size(boolean countDuplicates){
        try {
            Preconditions.checkNotNull(this.hashArray);
            Preconditions.checkArgument(Arrays.stream(this.hashArray).parallel().anyMatch(Objects::nonNull));
        } catch (IllegalArgumentException | NullPointerException e) {
            return 0;
        }
        return IntStream.rangeClosed(0, this.getMaximumHashValue())
                .parallel()
                .map(hv -> this.hashArray[hv] == null ? 0 : this.size(hv, countDuplicates))
                .sum();
    }

    /** Finds the number of non-null elements currently being stored in the array linked to a given hash value
     * @param hashValue <code>int</code> value between 0 and maximum hash value (inclusive)
     * @param countDuplicates Whether to count each element stored multiple times as 1 or as number of duplicates
     * @return The number of elements being stored at <code>hashValue</code>
     */
    private int size(int hashValue, boolean countDuplicates){
        try {
            Preconditions.checkArgument(hashValue >= 0 && hashValue <= this.getMaximumHashValue(),
                    "Invalid hash value!");
        } catch (IllegalArgumentException i) {
            i.printStackTrace();
            return -1;
        }
        if(countDuplicates)
            return this.hashArray[hashValue].values().stream().parallel().mapToInt(Integer::intValue).sum();
        return this.hashArray[hashValue].size();
    }

    //MUTATORS
    /** Adds new element
     * @param el Element to be added
     * @return <code>true</code> if element is added validly given the circumstances 
     */
    public boolean add(T el){
        try {
            Preconditions.checkNotNull(el, "Element to be added must not be null!");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        int hashValue = Math.abs(this.hashFunction.apply(el)) % (this.getMaximumHashValue() + 1);
        if(this.getChainSequenceCapacity() >= 1 && this.getChainSequenceCapacity() < this.size(hashValue, false))
            return false;
        if(!this.hashArray[hashValue].containsKey(el)) {
            this.hashArray[hashValue].put(el, 1);
            return true;
        }
        switch (this.getSetType()) {
            case SET : { return false; }
            case MULTISET : { this.hashArray[hashValue].put(el, this.hashArray[hashValue].get(el) + 1); return true; }
            case LIMITED_MULTISET : {
                if(this.hashArray[hashValue].get(el) >= this.getDuplicateCapacity())
                    return false;
                this.hashArray[hashValue].put(el, this.hashArray[hashValue].get(el) + 1);
                return true;
            }
        }
        return false;
    }



}
