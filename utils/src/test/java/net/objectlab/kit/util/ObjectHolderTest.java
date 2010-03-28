package net.objectlab.kit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class ObjectHolderTest {

    @Test
    public void testObjectHolder() {
        final ObjectHolder<Integer> it = new ObjectHolder<Integer>();
        assertNull(it.getValue());
    }

    @Test
    public void testObjectHolderT() {
        final ObjectHolder<Integer> it = new ObjectHolder<Integer>(Integer.valueOf(987987));
        assertEquals("value", Integer.valueOf(987987), it.getValue());
    }

    @Test
    public void testSetValue() {
        final ObjectHolder<Integer> it = new ObjectHolder<Integer>();
        assertNull(it.getValue());
        final Integer val = Integer.valueOf(9879872);
        it.setValue(val);
        assertEquals("value 1", val, it.getValue());

        final ObjectHolder<Integer> it2 = new ObjectHolder<Integer>(Integer.valueOf(987987));
        assertEquals("value 2", Integer.valueOf(987987), it2.getValue());
        it.setValue(val);
        assertEquals("value 3", val, it.getValue());
    }
}
