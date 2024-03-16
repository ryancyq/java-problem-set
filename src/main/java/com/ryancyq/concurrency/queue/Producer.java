package com.ryancyq.concurrency.queue;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable{

    private final BlockingQueue<Integer> queue;
    private final Integer poisonPill;
    private final Integer pillSize;

    public String name;

    public Producer(String name, BlockingQueue<Integer> queue, Integer poisonPill, Integer pillSize) {
        this.name = name;
        this.queue = queue;
        this.poisonPill = poisonPill;
        this.pillSize = pillSize;
    }

    public void log(String message) {
        System.out.printf("[Producer-%s] %s%n", name, message);
    }

    public void run() {
        try {
            for(int i=0;i<5;i++) {
                log("put " + (i+1));
                queue.put(i+1);
                log("queue size " + queue.remainingCapacity() + "/" + queue.size());
                Thread.sleep(50);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            try {
                for (int i = 0; i < pillSize; i++) {
                    queue.put(poisonPill);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();;
            }
        }
    }
}
