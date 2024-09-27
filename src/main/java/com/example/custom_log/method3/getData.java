package com.example.custom_log.method3;

/**
 * @author banana
 * @create 2024-09-25 19:48
 */
public interface getData {

    default  <T> T getOldData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    default  <T> T getNewData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }




}
