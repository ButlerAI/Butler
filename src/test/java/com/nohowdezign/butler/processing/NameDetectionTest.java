package com.nohowdezign.butler.processing;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Noah Howard
 */
public class NameDetectionTest {
    private NameDetection nameDetection;

    @Before
    public void setUp() throws Exception {
        nameDetection = new NameDetection();
    }

    @Test
    public void testIsNameInSentence() throws Exception {
        assertTrue(nameDetection.isNameInSentence("Hello Butler!"));
    }

    @Test
    public void testSetName() {
        nameDetection.setName("fred");
        assertEquals(nameDetection.getName(), "fred");
    }

}