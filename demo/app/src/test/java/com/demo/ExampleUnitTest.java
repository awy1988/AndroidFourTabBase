package com.demo;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        Integer a = new Integer(1);
        Integer b = new Integer(1);
        Object ab = new Object();
        assertEquals(true, a == b);

        //assertEquals(4, 2 + 2);
    }
}
