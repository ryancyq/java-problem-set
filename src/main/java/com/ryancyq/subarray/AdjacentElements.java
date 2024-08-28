package com.ryancyq.subarray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class AdjacentElements {
    public boolean isDebug = false;

    public int count(int[] input) {
        Map<Integer, ArrayList<Integer>> frequencies = new HashMap<>();
        for (int i = 1; i < input.length; i++) {
            int prev = input[i - 1];
            int cur = input[i];
            int sum = cur + prev;

            frequencies.putIfAbsent(sum, new ArrayList<>());
            frequencies.get(sum).add(i);
        }

        if (isDebug) {
            String freq = frequencies.entrySet().stream().map(
                    (entry) -> {
                        return entry.getKey() +
                                ": " +
                                entry.getValue().stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
                    }).collect(Collectors.joining("\n"));
            System.out.println("frequencies:\n" + freq);
        }

        int max = Integer.MIN_VALUE;
        for (ArrayList<Integer> indices : frequencies.values()) {
            int subtotal = 0;
            int prev = -1;
            int i = 0;
            while (i < indices.size()) {
                int cur = indices.get(i);
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

        return max;
    }

    public void run(int[] input, int expected) {
        int max = count(input);
        System.out.println("input: " + Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(",", "[", "]")));
        System.out.println("match: " + (max == expected) + ", actual:" + max + ", expected: " + expected);
    }

    public static void main(String[] args) {
        new AdjacentElements().run(new int[]{1, 1, 1, 1, 1}, 2);
        new AdjacentElements().run(new int[]{1, 9, 9, 2, 8, 0, 10, 0, 3, 7}, 4);
        new AdjacentElements().run(new int[]{1000000, 1, 1, 1000001, 0}, 2);

        int[] identical = new int[100000];
        Arrays.fill(identical, 1);
        new AdjacentElements().run(identical, 50000);

        int[] maxValue = new int[100000];
        Arrays.fill(identical, 1000000000);
        new AdjacentElements().run(maxValue, 50000);
    }
}
