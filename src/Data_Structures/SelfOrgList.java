import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;
import java.util.List;

enum MODE { MOVETOFRONT, COUNTER, TRANSPOSE}
public class SelfOrgList {
    //MEMBER VARIABLES
    private List<Number> list;
    private List<Integer> counter;
    private MODE mode;
    private int capacity;

    //MEMBER FUNCTIONS
    //CONSTRUCTORS
    //Default constructor
    public SelfOrgList(){
        this.list = new ArrayList<>();
        this.mode = MODE.MOVETOFRONT;
    }

    //Constructor applies capacity
    public SelfOrgList(int capacity){
        this.list = new ArrayList<>(capacity);
        this.mode = MODE.MOVETOFRONT;
        this.capacity = capacity;
    }

    //Constructor applies organizational mode
    public SelfOrgList(MODE mode){
        this.list = new ArrayList<>();
        this.mode = mode;
        if(mode.equals(MODE.COUNTER))
            this.counter = new ArrayList<>();
    }

    //ACCESSORS
    //Accesses and returns specific element at given index if it exists, otherwise returns null
    public Number get(int index){
        if(index < 0 || index >= this.list.size())
            return null;
        Number output = this.list.get(index);
        switch(this.mode){
            case MOVETOFRONT : {
                this.list.remove(index);
                this.list.add(0, output);
                break;
            } case COUNTER : {
                this.counter.set(index, this.counter.get(index) + 1);
                break;
            } case TRANSPOSE : {
                Collections.swap(this.list, index - 1, index);
                break;
            }
        }
        return output;
    }

    //Finds out currently used organizational mode
    public MODE getMode(){ return this.mode; }

    //Gets the current capacity
    public int getCapacity(){ return this.capacity; }

    //MUTATORS
    //Sets new capacity on number of elements allowed
    public void setCapacity(int newCapacity){
        if(newCapacity < 0)
            return;
        this.capacity = newCapacity;
    }

    //Adds new element to the list
    public boolean add(Number el){
        if(this.list.size() == this.capacity || el == null)
            return false;
        this.list.add(el);
        if(this.mode == MODE.COUNTER)
            this.counter.add(1);
        return true;
    }

    //Adds entire collection of given elements to the list
    public boolean addAll(Collection<Number> arr){ return this.list.addAll(arr); }

    //Removes element from the list based on index
    public boolean remove(int index){
        if(index < 0 || this.list.size() <= index)
            return false;
        if(this.mode == MODE.COUNTER)
            this.counter.remove(index);
        this.list.remove(index);
        return true;
    }

    //Removes element from the list based on the first match
    public boolean remove(Number el){
        if(el == null || !this.list.contains(el))
            return false;
        int index = this.list.indexOf(el);
        if(this.mode == MODE.COUNTER)
            this.counter.remove(index);
        this.list.remove(index);
        return true;
    }

    //Removes all occurrences of given element
    public boolean removeAll(Number el){
        if(el == null || !this.list.contains(el))
            return false;
        if(this.mode == MODE.COUNTER)
            while(this.list.contains(el)){
                int index = this.list.indexOf(el);
                this.list.remove(el);
                this.counter.remove(index);
            }
        else
            this.list.removeAll(Arrays.asList(el));
        return true;
    }

    //Removes all occurrences of each element from given heterogeneous collection
    public boolean removeAll(Collection<Number> arr){ return this.list.removeAll(arr); }

}
