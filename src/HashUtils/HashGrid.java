package src.HashUtils;

//STANDARD JAVA
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Function;
import java.util.Iterator;

//OTHER
import org.javatuples.Pair;
import com.google.common.base.Preconditions;

public class HashGrid <T extends Comparable<T>> {
    //MEMBER VARIABLES
    private ArrayList<T>[][] grid;
    private Pair<Function<T, Integer>, Function<T, Integer>> functionPair;

    //MEMBER FUNCTIONS
    //CONSTRUCTORS
    /** Constructor applies specific dimensions to <code>HashGrid</code>
     * @param width Number of rows in {@code HashGrid}
     * @param height Number of columns in <code>HashGrid</code>
     */
    public HashGrid(int width, int height){
        this.grid = new ArrayList[width][height];
        this.functionPair = new Pair<>(Object::hashCode, obj -> obj.hashCode() + 10);
    }

    /** Constructor ensures given capacity of non-collision elements possible to store
     * @param capacity Least possible number of spots reserved in <code>HashGrid</code>
     */
    public HashGrid(int capacity){
        //capacity cannot be 0 or negative
        int sideLength = (int)Math.round(Math.sqrt(capacity));
        this.grid = new ArrayList[sideLength][sideLength];
        this.functionPair = new Pair<>(Object::hashCode, obj -> obj.hashCode() + 10);
    }

    /** Constructor applies specific dimensions and two (2) custom hash functions to <code>HashGrid</code>
     * @param width Number of rows in <code>HashGrid</code>
     * @param height Number of columns in <code>HashGrid</code>
     * @param func1 First custom hash function
     * @param func2 Second custom hash function
     */
    public HashGrid(int width, int height, Function<T, Integer> func1, Function<T, Integer> func2){
        this.grid = new ArrayList[width][height];
        this.functionPair = new Pair<>(func1, func2);
    }

    //ACCESSORS
    /** Check if an element is stored in the {@code HashGrid}
     * @param obj Element to check
     * @return {@code true} if the object is found, otherwise {@code false}
     */
    public boolean contains(T obj) {
        try { Preconditions.checkNotNull(obj); }
        catch (NullPointerException e) { return false; }
        int rowIndex = this.functionPair.getValue0().apply(obj) % this.getWidth(),
            colIndex = this.functionPair.getValue1().apply(obj) % this.getLength();
        return this.grid[rowIndex][colIndex].contains(obj);
    }

    /** Check whether each cell in the {@code HashGrid} contains one (1) or more elements
     * @return {@code true} if each cell is non-empty, otherwise {@code false}
     */
    public boolean filled(){ return Arrays.stream(this.grid).flatMap(Arrays::stream).allMatch(n -> n.size() >= 1); }

    /** Gets the number of rows in {@code HashGrid}
     * @return Number of rows
     */
    public int getWidth() { return this.grid == null ? 0: this.grid.length; }

    /** Gets the number of columns in {@code HashGrid}
     * @return Number of columns
     */
    public int getLength() { return this.grid == null ? 0 : this.grid[0].length; }

    /** Gets an {@code Iterator<T>} instance for the chain sequence at a specified cell
     * @param rowIndex Row index
     * @param colIndex Column index
     * @return An iterator for the cell located at the specified location
     */
    public Iterator<T> iterator(int rowIndex, int colIndex){ return this.grid[rowIndex][colIndex].iterator(); }

    //MUTATORS
    /** Add new element to {@code HashGrid}
     * @param obj Element to be added
     * @return {@code true} if the {@code HashGrid} has been changed
     */
    public boolean add(T obj) {
        int rowIndex = this.functionPair.getValue0().apply(obj) % this.getWidth(),
            colIndex = this.functionPair.getValue1().apply(obj) % this.getLength();
        int scIndex = Collections.binarySearch(grid[rowIndex][colIndex], obj); //index in sequence chain
        try { Preconditions.checkArgument(scIndex >= 0); }
        catch (IllegalArgumentException e) { return false; }
        scIndex = -(scIndex + 1);
        this.grid[rowIndex][colIndex].add(scIndex, obj);
        return true;
    }

    /** Clears all stored elements from the {@code HashGrid}
     */
    public void clear() { Arrays.stream(this.grid).flatMap(Arrays::stream).forEach(ArrayList::clear); }

    /** Removes element from the {@code HashGrid}
     * @param obj Element to remove
     * @return {@code true} if the {@code HashGrid} has been changed
     */
    public boolean remove(T obj){
        int rowIndex = this.functionPair.getValue0().apply(obj) % this.getWidth(),
            colIndex = this.functionPair.getValue1().apply(obj) % this.getLength();
        return this.grid[rowIndex][colIndex].remove(obj);
    }
}