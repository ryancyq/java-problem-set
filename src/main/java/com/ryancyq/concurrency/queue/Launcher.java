package com.ryancyq.concurrency.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class Launcher {
    private static final int DEFAULT_PRODUCER_SIZE = 5;
    private static final int DEFAULT_CONSUMER_SIZE = 2;
    private static final int DEFAULT_QUEUE_SIZE = 10;
    private static final int DEFAULT_POISON_PILL = -1;

    public void run() {
        run(DEFAULT_PRODUCER_SIZE, DEFAULT_CONSUMER_SIZE, DEFAULT_QUEUE_SIZE, DEFAULT_POISON_PILL);
    }

    public void run(int producerSize, int consumerSize, int queueSize, int poisonPill) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(queueSize);
        int pillSize = consumerSize / producerSize + 1;

        try (ExecutorService executor = Executors.newFixedThreadPool(consumerSize + producerSize)) {
            IntStream.rangeClosed(1, producerSize).forEach(
                    (i) -> executor.submit(new Producer(String.valueOf(i), queue, poisonPill, pillSize))
            );
            IntStream.rangeClosed(1, consumerSize).forEach(
                    (i) -> executor.submit(new Consumer<>(String.valueOf(i), queue, poisonPill))
            );
        }
    }
}
