package com.ryancyq;

import java.util.*;
import java.util.stream.Collectors;

// given an array A of N integers, returns the smallest positive integer (greater than 0) that does not occur in A.
// For example, given A = [1, 3, 6, 4, 1, 2], the function should return 5.
// Given A = [1, 2, 3], the function should return 4.
// Given A = [-1, -3], the function should return 1.
// Write an efficient algorithm for the following assumptions:

// N is an integer within the range [1..100,000];
// each element of array A is an integer within the range [-1,000,000..1,000,000].

public class SmallestPositive {
    public boolean isDebug = false;

    public int find(int[] A) {
        Set<Integer> seen = new HashSet<>();
        List<Integer> positive = new ArrayList<>();
        for (int a : A) {
            if (a > 0 && !seen.contains(a)) {
                seen.add(a);
                positive.add(a);
            }
        }

        if (isDebug) System.out.println("seen: " + seen.stream().map(String::valueOf).collect(Collectors.joining(",")));
        Collections.sort(positive);
        if (isDebug)
            System.out.println("sorted: " + positive.stream().map(String::valueOf).collect(Collectors.joining(",")));

        int prev = 0;
        for (int p : positive) {
            if (isDebug) System.out.println("p: " + p + ", prev: " + prev);
            if (p - prev > 1) return prev + 1;
            prev = p;
        }

        if (positive.isEmpty()) return 1;

        return positive.get(positive.size() - 1) + 1;
    }


    public void run(int[] input, int expected) {
        int output = find(input);
        System.out.println("input: " + Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(",", "[", "]")));
        System.out.println("match: " + (output == expected) + ", actual:" + output + ", expected: " + expected);
    }

    public static void main(String[] args) {
        new SmallestPositive().run(new int[]{1, 3, 6, 4, 1, 2}, 5);
        new SmallestPositive().run(new int[]{1, 2, 3}, 4);
        new SmallestPositive().run(new int[]{-1, -3}, 1);
    }
}
