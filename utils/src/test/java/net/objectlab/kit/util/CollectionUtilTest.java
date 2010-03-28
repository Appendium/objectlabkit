package net.objectlab.kit.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashSet;

import org.junit.Test;

public class CollectionUtilTest {

    @Test
    public void testIsEmpty() {
        assertTrue("null", CollectionUtil.isEmpty(null));
        assertTrue("empty set", CollectionUtil.isEmpty(new HashSet()));
    }

    @Test
    public void testIsNotEmptyCollectionOfQ() {
        fail("Not yet implemented");
    }

    @Test
    public void testHasOneItem() {
        fail("Not yet implemented");
    }

    @Test
    public void testIsNotEmptyObjectArray() {
        fail("Not yet implemented");
    }

    @Test
    public void testSize() {
        fail("Not yet implemented");
    }

    @Test
    public void testContains() {
        fail("Not yet implemented");
    }

    @Test
    public void testContainsAny() {
        fail("Not yet implemented");
    }

    @Test
    public void testNoneEmpty() {
        fail("Not yet implemented");
    }

    @Test
    public void testSameContent() {
        fail("Not yet implemented");
    }

}
