package com.ryancyq.concurrency;

import com.ryancyq.concurrency.queue.Launcher;

public class Program {

    public static void main(String[] args) {
        char mode = '0';
        for (String arg : args) {
            if (arg.equals("-q")) mode = 'q';
        }

        if (mode == 'q') new Launcher().run();
    }
}
