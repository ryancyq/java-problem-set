package com.ryancyq.subarray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Program {
    public static void run(int[] input, int expected) {
        run(input, expected, false);
    }

    public static void run(int[] input, int expected, boolean debug) {
        Map<Integer, ArrayList<Integer>> frequencies = new HashMap<>();
        for (int i = 1; i < input.length; i++) {
            int prev = input[i - 1];
            int cur = input[i];
            int sum = cur + prev;

            frequencies.putIfAbsent(sum, new ArrayList<>());
            frequencies.get(sum).add(i);
        }

        if (debug) {
            String freq = frequencies.entrySet().stream().map(
                    (entry) -> {
                        return entry.getKey() +
                                ": " +
                                entry.getValue().stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
                    }).collect(Collectors.joining("\n"));
            System.out.println("frequencies:\n" + freq);
        }

        int max = Integer.MIN_VALUE;
        for (ArrayList<Integer> indicies : frequencies.values()) {
            int subtotal = 0;
            int prev = -1;
            int i = 0;
            while (i < indicies.size()) {
                int cur = indicies.get(i);
                if (i >= 1) {
                    if (prev + 1 < cur) {
                        prev = cur;
                        subtotal++;
                    }

                } else {
                    prev = cur;
                    subtotal++;
                }
                i++;
            }
            max = Math.max(max, subtotal);
        }

        System.out.println("input: " + Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(",", "[", "]")) + ", output: " + max);
        System.out.println("match: " + (max == expected) + ", expected: " + expected);
    }

    public static void main(String[] args) {
        run(new int[]{1, 1, 1, 1, 1}, 2);
        run(new int[]{1, 9, 9, 2, 8, 0, 10, 0, 3, 7}, 4);
        run(new int[]{1000000, 1, 1, 1000001, 0}, 2);
    }
}
