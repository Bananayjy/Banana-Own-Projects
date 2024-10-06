package com.example.JVMTEST;

/**
 * @author banana
 * @create 2024-06-30 15:52
 */
public class OperandStackTest {

    public void testAddOperation() {
        int i = 10;
        int j = 20;
        int k = i + j;
    }

    public int getSum() {
        int i = 10;
        int j = 20;
        int k = i + j;
        return k;
    }

    public void testGetSum() {
        int i = getSum();
        int j = 10;
    }
}
