package com.example.JVMTEST.Heap;

/**
 * 堆空间内存相关内容获取方式
 * @author banana
 * @create 2024-10-07 1:04
 */
public class HeapSpaceInitial {

    public static void main(String[] args) throws InterruptedException {
        // java虚拟机中的堆内存总量
        long initialMemory = Runtime.getRuntime().totalMemory() / 1024 / 1024;

        // java虚拟机视图使用的最大堆内存量
        long maxMemory = Runtime.getRuntime().maxMemory() / 1024 / 1024;

        System.out.println("-Xms:" + initialMemory + "M");
        System.out.println("-Xmx:" + maxMemory + "M");

        Thread.sleep(100000000);
    }
}
