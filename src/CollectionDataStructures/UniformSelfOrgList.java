package CollectionUtils.CollectionDataStructures;

import java.util.ArrayList;
import java.util.AbstractMap;
import java.util.List;
import com.google.common.base.Preconditions;

/** Enumeration for type of criteria used to judge which elements count as least frequently accessed
 */
enum ORG {RANK, NUMBER, PROPORTION}

/** Self-organizing list that aims to at least guarantee an approximate uniform distribution of accesses to all elements
 * @param <T> Data type of elements
 */
public class UniformSelfOrgList<T> {
    //MEMBER VARIABLE
    /** Exact copy of original input <code>List</code> or array stored as an <code>ArrayList</code>
     */
    private ArrayList<T> original;

    /** Selection of elements that are least frequently accessed, stored as <code>ArrayList</code> of map entries. Here,
     * each key is an element and each corresponding value is the number of times accessed.
     */
    private ArrayList<AbstractMap.SimpleEntry<T, Integer>> unlockedList;

    /** Binary minimum heap used to hold all elements accessed more frequently than acceptable for further access
     */
    private T[] binaryMinHeap;

    /** Type of criteria used to judge which elements count as least frequently accessed
     */
    private ORG organizationType;

    //MEMEBER FUNCTIONS
    //CONSTRUCTORS
    /**
     * @param input
     * @param newOrg
     * @param resetCountOnModify
     */
    public UniformSelfOrgList(List<T> input, ORG newOrg, boolean resetCountOnModify){
        try {
            Preconditions.checkNotNull(input, "Input list cannot be null!");
            Preconditions.checkNotNull(newOrg, "Valid organization type must be specified!");
        } catch (NullPointerException e) {
            return;
        }
        this.original = new ArrayList<>(input.size());
        for(int i = 0; i < this.original.size(); i++)
            this.original.set(i, input.get(i));

    }

    public UniformSelfOrgList(){



    }




}

