package com.example.JUC.Locks.ReentrantReadWriteLock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author banana
 * @create 2024-10-01 20:12
 */
public class ReentrantReadWriteLockest {

    public static void main(String[] args) {
        // 创建共享资源
        SharedResource sharedResource = new SharedResource();


        // 读线程
        new Thread("【读线程】"){
          public void run() {
              String val = sharedResource.readData();
              System.out.println(Thread.currentThread().getName() + "执行读操作读到的值" + val);
          }
        }.start();


        // 写线程
        new Thread("【写线程】"){
            public void run() {
                sharedResource.writeData("12345");
            }
        }.start();


    }
}

/**
 * 共享资源类
 */
class SharedResource {

    // 定义一个读写锁
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    // 数据
    private String data;

    // 读取数据方法(加锁)
    public String readData() {
        lock.readLock().lock();  // 获取读锁
        try {
            System.out.println(Thread.currentThread().getName() + "执行读操作前……");
            Thread.sleep(10000);
            return data;           // 读取数据
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.readLock().unlock(); // 确保释放读锁
            System.out.println(Thread.currentThread().getName() + "执行读操作后……");
        }
    }

    // 写数据方法(加锁)
    public void writeData(String newData) {
        lock.writeLock().lock();   // 获取写锁
        try {
            System.out.println(Thread.currentThread().getName() + "执行写操作前……");
            Thread.sleep(10000);
            this.data = newData;    // 写入数据
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.writeLock().unlock(); // 确保释放写锁
            System.out.println(Thread.currentThread().getName() + "执行写操作后……");
        }
    }

}
