package net.objectlab.kit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import org.junit.Test;

public class CollectionUtilTest {

    @Test
    public void testIsEmpty() {
        assertTrue("null", CollectionUtil.isEmpty(null));
        HashSet col = new HashSet();
        assertTrue("empty set", CollectionUtil.isEmpty(col));
        col.add(1);
        assertFalse("not empty set", CollectionUtil.isEmpty(col));
        col.add(2);
        assertFalse("not empty set", CollectionUtil.isEmpty(col));
    }

    @Test
    public void testIsNotEmptyCollection() {
        assertFalse("null", CollectionUtil.isNotEmpty((Collection) null));
        HashSet col = new HashSet();
        assertFalse("empty set", CollectionUtil.isNotEmpty(col));
        col.add(1);
        assertTrue("not empty set", CollectionUtil.isNotEmpty(col));
        col.add(2);
        assertTrue("2 empty set", CollectionUtil.isNotEmpty(col));
    }

    @Test
    public void testIsNotEmptyObjectArray() {
        assertFalse("null", CollectionUtil.isNotEmpty((Object[]) null));
        Object[] col = new String[0];
        assertFalse("empty set", CollectionUtil.isNotEmpty(col));
        col = new String[1];
        assertTrue("not empty set", CollectionUtil.isNotEmpty(col));
    }

    @Test
    public void testHasOneItem() {
        assertFalse("null", CollectionUtil.hasOneItem((Collection) null));
        HashSet col = new HashSet();
        assertFalse("empty set", CollectionUtil.hasOneItem(col));
        col.add(1);
        assertTrue("not empty set", CollectionUtil.hasOneItem(col));
        col.add(2);
        assertFalse("2 items set", CollectionUtil.hasOneItem(col));
    }

    @Test
    public void testSize() {
        assertEquals("null", 0, CollectionUtil.size((Collection) null));
        HashSet col = new HashSet();
        assertEquals("empty ", 0, CollectionUtil.size(col));
        col.add(1);
        assertEquals("1", 1, CollectionUtil.size(col));
        col.add(2);
        assertEquals("2", 2, CollectionUtil.size(col));
    }

    @Test
    public void testContains() {
        assertFalse("null", CollectionUtil.contains((Collection) null, "bla"));
        HashSet col = new HashSet();
        assertFalse("empty", CollectionUtil.contains(col, "bla"));
        col.add(1);
        assertFalse("1", CollectionUtil.contains(col, "bla"));
        col.add("bla");
        assertTrue("1 + bla", CollectionUtil.contains(col, "bla"));
    }

    @Test
    public void testContainsAny() {
        assertFalse("null", CollectionUtil.containsAny((Collection) null, "bla", 2));
        HashSet col = new HashSet();
        assertFalse("empty", CollectionUtil.containsAny(col, "bla", 2));
        col.add(1);
        assertFalse("contains 1", CollectionUtil.containsAny(col, "bla", 2));
        col.add(2);
        assertTrue("contains 2", CollectionUtil.containsAny(col, "bla", 2));
        col.add("bla");
        assertTrue("contains 2 and bla", CollectionUtil.containsAny(col, "bla", 2));
        col.remove(2);
        assertTrue("contains bla", CollectionUtil.containsAny(col, "bla", 2));
        col.remove("bla");
        assertFalse("contains nothing", CollectionUtil.containsAny(col, "bla", 2));
    }

    @Test
    public void testNoneEmpty() {
        assertFalse("1 null", CollectionUtil.noneEmpty(null));
        assertFalse("2 null", CollectionUtil.noneEmpty(null, null));
        assertFalse("empty null", CollectionUtil.noneEmpty(Collections.EMPTY_LIST, null));
        assertFalse("empty empty", CollectionUtil.noneEmpty(Collections.EMPTY_LIST, Collections.EMPTY_LIST));
        HashSet col = new HashSet();
        assertFalse("empty empty", CollectionUtil.noneEmpty(col, col));
        col.add(1);
        assertTrue("1", CollectionUtil.noneEmpty(col));
        HashSet col2 = new HashSet();
        assertFalse("1 and 2 empty", CollectionUtil.noneEmpty(col, col2));
        assertFalse("1 and null 2 empty", CollectionUtil.noneEmpty(col, null, col2));
        col2.add("bla");
        assertFalse("1 and null 2", CollectionUtil.noneEmpty(col, null, col2));
        assertTrue("1 and 2", CollectionUtil.noneEmpty(col, col2));
    }

    @Test
    public void testSameContent() {
        assertTrue("null null", CollectionUtil.sameContent(null, null));
        HashSet col = new HashSet();
        assertTrue("empty null", CollectionUtil.sameContent(col, null));
        assertTrue("same", CollectionUtil.sameContent(col, col));
        HashSet col1 = new HashSet();
        col.add(1);
        assertFalse("not same empty 1", CollectionUtil.sameContent(col, col1));
        assertFalse("not same 1 empty", CollectionUtil.sameContent(col1, col));
        col1.add(1);
        assertTrue("same 1 1", CollectionUtil.sameContent(col1, col));
        col1.add(2);
        assertFalse("same 1 2", CollectionUtil.sameContent(col1, col));
    }

}
