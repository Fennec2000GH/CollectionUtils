package src.CollectionDataStructures;

//STANDARD JAVA
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Collections;
import java.util.List;

//OTHER
import com.google.common.base.Preconditions;

/** A stratified queue physically made of individual queues lined up together single-file. Allows for categorization of
 * type <i>T</i> objects based on member data. There are also helpful functionality for queue concatenation and f
 * flattening of the whole structure into a single queue.
 * @param <T> Type of element
 * @author Caijun Qin
 * @version 1.0.0
 * @since 02-14-2020
 */
public class MultiQueue <T extends Comparable<T>> {
    //MEMBER VARIABLES
    /** Physical implementation holding stored data
     */
    private ArrayList<ArrayBlockingQueue<T>>  multiQ;

    //MEMBER FUNCTIONS
    //CONSTRUCTORS
    /** Constructs empty {@code MultiQueue}
     */
    public MultiQueue(){ this.multiQ = new ArrayList<>(); }

    /** Constructs {@code MultiQueue} with specified number of sections and queue size
     * @param numSections Number of sections making up the stratified queue structure
     * @param sectionCapacity Equal capacity for each queue section
     * @throws IllegalArgumentException If {@code numSections} or {@code sectionCapacity} is non-positive
     */
    public MultiQueue(int numSections, int sectionCapacity){
        Preconditions.checkArgument(numSections >= 1,
                                    "The number of sections must be positive!");
        Preconditions.checkArgument(sectionCapacity >= 1,
                                    "Section capacity must be positive!");
        this.multiQ = new ArrayList<>();
        this.multiQ.ensureCapacity(numSections);
        for(int i = 0; i < numSections; i++) this.multiQ.add(new ArrayBlockingQueue<T>(sectionCapacity));
    }

    /** Constructs {@code MultiQueue} with specified number of sections and assigns individual queue sizes from a list
     * @param numSections Number of sections making up the stratified queue structure
     * @param sectionCapacityList List of capacities in assignment order
     */
    public MultiQueue(int numSections, List<Integer> sectionCapacityList){
        Preconditions.checkArgument(numSections >= 1,
                                        "The number of sections must be positive!");
        Preconditions.checkArgument(sectionCapacityList.size() != numSections,
                                        "List of section capacities must match the number of sections!");
        Preconditions.checkArgument(sectionCapacityList.stream().parallel().allMatch(n -> n > 0),
                                        "Only positive section capacities allowed!");
        for(int i = 0; i < numSections; i++) this.multiQ.add(new ArrayBlockingQueue<>(sectionCapacityList.get(i)));
    }

    /** Copy constructor
     * @param otherMQ Another {@code MultiQueue} to perform a deep copy from
     */
    public MultiQueue(MultiQueue<T> otherMQ){
        this.multiQ = new ArrayList<>();
        this.multiQ.ensureCapacity(otherMQ.multiQ.size());
        ArrayList<T> temp;
        int index = 0;
        for(ArrayBlockingQueue<T> abq : otherMQ.multiQ) {
            this.multiQ.add(new ArrayBlockingQueue<>(abq.size()));
            temp = new ArrayList<>(abq.size());
            while(!abq.isEmpty()) temp.add(abq.poll());
            Collections.reverse(temp);
            for (T el : temp) this.multiQ.get(index).offer(el);
        }
    }

    //ACCESSORS
    /** Gets the number of sections in the {@code MultiQueue}
     * @return Number of sections
     */
    public int getSectionCount(){ return this.multiQ.size(); }

    /** Gets the capacity of a section
     * @param index Index of section in {@code MultiQueue}
     * @return The capacity of a section
     */
    public int getSectionCapacity(int index){
        Preconditions.checkElementIndex(index, multiQ.size());
    return this.multiQ.get(index).size();
    }

    //MUTATORS


}

