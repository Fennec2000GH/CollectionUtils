package CollectionUtils.CollectionDataStructures;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import com.google.common.base.Preconditions;

enum TYPE {SET, MULTISET, LIMITED_MULTISET}

/**
 * @param <T> Data type of data
 */
public class HashForest<T> {
    //MEMBER VARIABLES
    /** Hash structure using 2D array with space-saving techniques
     */
    private TreeMap<T, Integer>[] hashForest;

    /** User-set hash function used to determine hash values for storing elements
     */
    private Function<T, Integer> hashFunction;

    /** Emulation of a specific type of set, essentially whether to allow duplicate values or not
     */
    private TYPE type;

    /** Number of duplicates allowed per distinct element - used solely when <code>setType</code> is
     * <code>LIMITED_MULTISET</code>
     */
    private int duplicateCapacity;

    /** Maximum number of distinct elements allowed per hash value linked <code>TreeMap</code>
     */
    private int treeCapacity;

    //MEMBER FUNCTIONS
    //CONSTRUCTORS
    /** Constructor applies custom <code>hashFunction</code> and creates new <code>hashForest</code>
     * @param hashFunc New custom hash function
     * @param hashValueRange Countable integer range from 0 to a positive number that denotes hash values employed
     */
    public HashForest(Function<T, Integer> hashFunc, int hashValueRange){
        try {
            Preconditions.checkNotNull(hashFunc, "Hash function must exist!");
            Preconditions.checkArgument(hashValueRange > 0,
                    "Number of possible hash values must be positive!");
        } catch (IllegalArgumentException | NullPointerException e) {
            e.printStackTrace();
        }
        this.hashForest = new TreeMap[hashValueRange];
        for(int i = 0; i < this.getMaximumHashValue(); i++)
            this.hashForest[i] = new TreeMap<>();
        this.hashFunction = hashFunc;
        this.treeCapacity = -1;
    }

    /** Constructor applies custom <code>hashFunction</code>, sets type of set to be modeled after, and creates new
     * <code>hashForest</code>
     * @param hashFunc New custom hash function
     * @param hashValueRange Countable integer range from 0 to a positive number that denotes hash values employed
     * @param newType Value of enum class <code>TYPE</code> describing which schema of a set to use
     */
    public HashForest(Function<T, Integer> hashFunc, int hashValueRange, TYPE newType){
        this(hashFunc, hashValueRange);
        this.type = newType;
    }

    /** Constructor applies custom <code>hashFunction</code>, sets type of set to be modeled after, sets new capacity
     * for each hash value chain sequence, and creates new <code>hashForest</code>
     * @param hashFunc New custom hash function
     * @param hashValueRange Countable integer range from 0 to a positive number that denotes hash values employed
     * @param newType Value of enum class <code>TYPE</code> describing which schema of a set to use
     * @param newCapacity Positive <code>int</code> value denoting maximum number of elements allowed per <code>TreeMap</code>
     */
    public HashForest(Function<T, Integer> hashFunc, int hashValueRange, TYPE newType, int newCapacity){
        this(hashFunc, hashValueRange, newType);
        this.treeCapacity = newCapacity;
    }

    //ACCESSORS
    /** Checks whether given value is already stored
     * @param el Element to check for containment
     * @return <code>true</code> if <code>el</code> is found somewhere in <code>hashForest</code>
     */
    public boolean contains(T el){
        try {
            Preconditions.checkNotNull(el, "Element must be not null!");
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        int hashValue = Math.abs(this.hashFunction.apply(el)) % (this.getMaximumHashValue() + 1);
        if(this.hashForest[hashValue] == null)
            return false;
        return this.hashForest[hashValue].containsKey(el);
    }

    /** Finds the number of duplicates of given element already inserted
     * @param el Element to count duplicates
     * @return How many times given element has been previously inserted
     */
    public int count(T el){
        if(!this.contains(el))
            return 0;
        int hashValue = Math.abs(this.hashFunction.apply(el)) % (this.getMaximumHashValue() + 1);
        return this.hashForest[hashValue].get(el);
    }

    /** Finds currently set capacity on number of distinct elements allowed per chain sequence
     * @return Current chain sequence capacity
     */
    public int getTreeCapacity(){ return this.treeCapacity; }

    /** Finds the current number of elements that can be stored, including the ones already stored
     * @return Number of possible hash values times <code>treeCapacity</code> if such capacity is enforced, otherwise
     * <code>Integer.MAX_VALUE</code> if <code>treeCapacity</code> is not enforced i.e. there is no limit to number of
     * distinct elements per hash value
     */
    public int getCapacity(){
        if(this.getTreeCapacity() == -1)
            return Integer.MAX_VALUE;
        return (this.getMaximumHashValue() + 1) * this.getTreeCapacity();
    }

    /** Increments count for duplicate elements added
     * @param el Element for which to increment count in its <code>TreeMap</code>
     * @return <code>true</code> if incrementing is successful given current constraints of <code>hashForest</code>
     */
    private void incrementCount(T el){
        int hashValue = Math.abs(this.hashFunction.apply(el)) % (this.getMaximumHashValue() + 1);
        try {
            Preconditions.checkState (this.getType() != TYPE.SET,
                    "Type does not allow duplicates of same element!");
            Preconditions.checkState (this.size(hashValue, false) > this.getTreeCapacity() && this.getTreeCapacity() >= 1,
                    "TreeMap linked to hash value is already full!");
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return;
        }
        this.hashForest[hashValue].computeIfPresent(el, (k, v) -> ++v);
        this.hashForest[hashValue].putIfAbsent(el, 1);
    }

    /** Finds the number of duplicates allowed per distinct element, which is only positive when <code>hashForest</code>
     * is modeled after <code>LIMITED_MULTISET</code>
     * @return Duplicate capacity
     */
    public int getDuplicateCapacity() {
        if(this.getType() != TYPE.LIMITED_MULTISET)
            return -1;
        return this.duplicateCapacity;
    }

    /** Finds the largest usable hash value
     * @return 1 less than length of hash array
     */
    public int getMaximumHashValue(){ return this.hashForest.length - 1; }

    /** Finds the type of set
     * @return An enum value under enum class <code>TYPE</code>
     */
    public TYPE getType(){ return this.type; }

    /** Checks if no elements are stored yet
     * @return <code>true</code> if 0 elements are found in <code>hashForest</code> currently
     */
    public boolean isEmpty(){ return Arrays.stream(this.hashForest).parallel().allMatch(TreeMap::isEmpty); }

    /** Finds the number of non-null elements currently being stored
     * @param countDuplicates Whether to count each element stored multiple times as 1 or as number of duplicates
     * @return The number of elements currently being stored
     */
    public int size(boolean countDuplicates){
        try {
            Preconditions.checkNotNull(this.hashForest);
            Preconditions.checkArgument(Arrays.stream(this.hashForest).parallel().anyMatch(Objects::nonNull));
        } catch (IllegalArgumentException | NullPointerException e) {
            return 0;
        }
        return IntStream.rangeClosed(0, this.getMaximumHashValue())
                .parallel()
                .map(hv -> this.hashForest[hv] == null ? 0 : this.size(hv, countDuplicates))
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
            return this.hashForest[hashValue].values().stream().parallel().mapToInt(Integer::intValue).sum();
        return this.hashForest[hashValue].size();
    }

    /** Creates an <code>unmodifiableList</code> view of all non-null elements
     * @param countDuplicates Whether to count each element stored multiple times as 1 or as number of duplicates
     * @return <code>unmodifiableList</code> view of elements
     */
    public List<T> toList(boolean countDuplicates){
        List<T> output = new ArrayList<>();
        if(countDuplicates)
            for(int i = 0; i <= this.getMaximumHashValue(); i++)
                for(Map.Entry<T, Integer> e : this.hashForest[i].entrySet())
                    output.addAll(Collections.nCopies(e.getValue(), e.getKey()));
        else
            Arrays
                    .stream(this.hashForest)
                    .filter(n -> !n.isEmpty())
                    .map(TreeMap::keySet)
                    .forEach(output::addAll);
        return Collections.unmodifiableList(output);
    }

    /** Creates an array of all elements currently being stored
     * @param countDuplicates Whether to count each element stored multiple times as 1 or as number of duplicates
     * @return Array of elements
     */
    public T[] toArray(boolean countDuplicates){
        Object[] output = new Object[this.size(countDuplicates)];
        this.toList(countDuplicates).toArray(output);
        return (T[])output;
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
        if(this.getTreeCapacity() >= 1 && this.getTreeCapacity() < this.size(hashValue, false))
            return false;
        if(!this.hashForest[hashValue].containsKey(el)) {
            this.hashForest[hashValue].put(el, 1);
            return true;
        }
        switch (this.getType()) {
            case SET : { return false; }
            case MULTISET : { this.hashForest[hashValue].put(el, this.hashForest[hashValue].get(el) + 1); return true; }
            case LIMITED_MULTISET : {
                if(this.hashForest[hashValue].get(el) >= this.getDuplicateCapacity())
                    return false;
                this.hashForest[hashValue].put(el, this.hashForest[hashValue].get(el) + 1);
                return true;
            }
        }
        return false;
    }

    /** Removes all elements from <code>hashForest</code>
     */
    public void clear(){
        for(int i = 0; i <= this.getMaximumHashValue(); i++)
            this.hashForest[i].clear();
    }

}



