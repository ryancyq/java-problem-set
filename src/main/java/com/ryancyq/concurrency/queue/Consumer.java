package com.ryancyq.concurrency.queue;

import java.io.Serializable;
import java.util.concurrent.BlockingQueue;

public class Consumer<T extends Serializable> implements Runnable {

    private final BlockingQueue<T> queue;
    private final T poisonPill;
    public String name;

    public Consumer(String name, BlockingQueue<T> queue, T poisonPill) {
        this.name = name;
        this.queue = queue;
        this.poisonPill = poisonPill;
    }

    public void log(String message) {
        System.out.printf("[Consumer-%s] %s%n", name, message);
    }

    public void run() {
        try {
            while (true) {
                T object = queue.take();
                log(String.format("received %s", object));

                if (poisonPill.equals(object)) {
                    log(String.format("received poison pill %s", object));
                    break;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

