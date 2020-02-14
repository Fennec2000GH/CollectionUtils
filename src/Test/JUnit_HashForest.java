package CollectionUtils.Test;

import CollectionUtils.CollectionDataStructures.HashForest;

//STANDARD JAVA
import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;


//JUNIT
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.*;

//HashForest using type Integer
class JUnit0 {
    //CONSTRUCTOR
    public JUnit0(){}

    //TEST
    @Test
    public void test0() {
        System.out.println("Testing default constructor for correct construction.");
        HashForest<Integer> hashForest = new HashForest<>();
        assertNotNull("HashForest must be constructed!", hashForest);
        assertNotNull("Hash function must not be null!", hashForest.getHashFunction());
        assertEquals("Default range of hash values is 0-9 inclusive!",9, hashForest.getMaximumHashValue());
        assertEquals("Default TYPE is SET!", HashForest.TYPE.SET, hashForest.getType());
        System.out.println("\n");
    }

    @Test(expected = NullPointerException.class)
    public void test1a() {
        System.out.println("Testing constructor 1 for correct construction.");
        HashForest<Integer> hashForest = new HashForest<>(n -> Math.abs(n) + 12, 20);
        assertNotNull("HashForest must be constructed!", hashForest);
        assertNotNull("Hash function cannot be null!", hashForest.getHashFunction());
        assertEquals("Set hash value range should be 0-19 inclusive!", 19, hashForest.getMaximumHashValue());
        assertEquals("Default TYPE is SET!", HashForest.TYPE.SET, hashForest.getType());
        System.out.println("\n");
    }

    @Test
    public void test1b() {
        System.out.println("Testing constructor 1 for null Function parameter.");
        HashForest<Integer> hashForest = new HashForest<>(null, 20);
        assertNotNull("Hash function cannot be null!", hashForest.getHashFunction());
        System.out.println("\n");
    }

    @Test(expected = IllegalArgumentException.class)
    public void test1c() {
        System.out.println("Testing constructor 1 for invalid length of hash range.");
        HashForest<Integer> hashForest = new HashForest<>(n -> Math.abs(n) + 12, -1);
        assertEquals("Invalid hash range value must be converted to default value of 10!", hashForest.getMaximumHashValue(), 9);
        System.out.println("\n");
    }


    @Test
    public void test2() {
        System.out.println("Testing constructor 2.");
        HashForest<Integer> hashForest = new HashForest<>(n -> Math.abs(n) + 12, 20, HashForest.TYPE.MULTISET);
        assertEquals("TYPE should be MULTISET!", HashForest.TYPE.MULTISET, hashForest.getType());
        System.out.println("\n");
    }
}

@RunWith(Suite.class)
@SuiteClasses(JUnit0.class)
class JUnit_Suite_HashForest {}

public class JUnit_HashForest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(JUnit_Suite_HashForest.class);
        for (int i = 0; i < result.getFailureCount(); i++)
            System.out.print(result.getFailures().get(i).toString());
        System.out.println("Run count: " + result.getRunCount());
        System.out.println("Run time: " + result.getRunTime());
        System.out.println("All tests successful? " + result.wasSuccessful());
    }
}
