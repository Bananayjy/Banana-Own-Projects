package com.example.JVMTEST.Heap;

import java.util.ArrayList;
import java.util.Random;

/**
 * OOM场景示例
 * 设置内存参数：-Xms600m -Xmx600m
 * @author banana
 * @create 2024-10-07 15:20
 */
public class OOMTest {

    public static void main(String[] args) {
        ArrayList<Picture> list = new ArrayList<>();
        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            list.add(new Picture(new Random().nextInt(1024 * 1024)));
        }
    }
}

class Picture {

    private byte[] pixles;

    public Picture(int len) {
        this.pixles = new byte[len];
    }

}
