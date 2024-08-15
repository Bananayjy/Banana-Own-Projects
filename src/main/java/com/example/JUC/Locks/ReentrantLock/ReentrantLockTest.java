package com.example.JUC.Locks.ReentrantLock;

import lombok.Data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 场景：顾客向销售员买票
 * @author banana
 * @create 2024-08-15 21:29
 */
public class ReentrantLockTest {

    public static void main(String[] args) {

        // 创建一个售票员对象（创建共享资源，方便后面对共享资源进行抢夺）
        TicketSeller ticketSeller = new TicketSeller();

        // 顾客1
        customer customer1 = new customer(ticketSeller);
        customer1.setName("customer1");

        // 顾客2
        customer customer2 = new customer(ticketSeller);
        customer2.setName("customer2");

        // 顾客3
        customer customer3 = new customer(ticketSeller);
        customer3.setName("customer3");

        // 开始抢票
        customer1.start();
        customer2.start();
        customer3.start();

    }
}

// 售票员
class TicketSeller{

    // 共有10张票（线程共享资源）
    private Integer tickets = 10;

    // 好比管理卖票的人
    Lock lock = new ReentrantLock();

    // 卖票
    public void sell() {
        // 放一个顾客进来买票
        lock.lock();
        if(tickets > 0) {
            System.out.println(Thread.currentThread().getName() + "买到第" + tickets -- + "张票!");
        }
        // 让买完票的顾客离开
        lock.unlock();
    }

}

// 顾客
class customer extends Thread {

    // 为该顾客服务的售票员
    TicketSeller ticketSeller;

    public customer(TicketSeller ticketSeller) {
        this.ticketSeller = ticketSeller;
    }

    @Override
    public void run() {
        while(true) {
            ticketSeller.sell();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
