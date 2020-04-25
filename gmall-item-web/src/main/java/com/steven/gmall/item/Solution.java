package com.steven.gmall.item;

public class Solution {
    static Object o1 = new Object();
    static Object o2 = new Object();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (Solution.o1) {
                    System.out.println("thread 1 get o1");
                    try {
                        Thread.sleep(100);
                        synchronized (Solution.o2) {
                            System.out.println("thread1 get o2");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (Solution.o2) {
                    System.out.println("thread 2 get o1");
                    try {
                        Thread.sleep(100);
                        synchronized (Solution.o1) {
                            System.out.println("thread 2 get o1");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
