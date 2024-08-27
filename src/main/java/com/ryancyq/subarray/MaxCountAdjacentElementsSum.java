package com.ryancyq.subarray;

import java.util.*;
import java.util.stream.Collectors;

// You are given an array A of integers. Find the maximum number of non-intersecting segments of length 2 (two // adjacent elements), such that segments have an equal sum.
// For example, given A = [10, 1, 3, 1, 2, 2, 1, 0, 4], there are three non-intersecting segments, each whose sum is // equal to 4: (1, 3), (2, 2),
// (0, 4). Another three non-intersecting segments are: (3, 1), (2, 2), (0, 4).
// Write a function:
// class Solution { public int solution (int[l A); }
// that, given an array A of N integers, returns the maximum number of segments with equal sums.
// Examples:
// 1. Given A = [10, 1, 3, 1, 2, 2, 1, 0, 4], the function should return 3, as explained above.
// 2. Given A = [5, 3, 1, 3, 2, 3], the function should return 1. Each sum of two adjacent elements is different from // the others.
// 3. Given A = [9, 9, 9, 9, 9], the function should return 2.
// 4. Given A = [1, 5, 2, 4, 3, 3], the function should return 3. There are three segments: (1, 5), (2, 4), (3, 3) // whose sums are equal to 6.
// Write an efficient algorithm for the following assumptions:
// • N is an integer within the range [2..100,000];
// • each element of array A is an integer within the range [0..1,000,000,000].

public class MaxCountAdjacentElementsSum {

    public boolean isDebug = false;

    public int pick(List<Integer> candidates, int candidateIndex, Map<Integer, Boolean> taken, int takenSum) {
        if (candidateIndex < 0 || candidateIndex >= candidates.size()) {
            return takenSum;
        }

        int candidate = candidates.get(candidateIndex);
        boolean availabile = true;
        for (int x = -1; x <= 0; x++) availabile &= !taken.getOrDefault(candidate + x, false);

        if (isDebug)
            System.out.println("candidate: " + candidate + ", index: " + candidateIndex + ", available: " + availabile);

        int max = pick(candidates, candidateIndex + 1, taken, takenSum);
        if (availabile) {
            for (int x = -1; x <= 0; x++) taken.put(candidate + x, true);
            max = Math.max(max, pick(candidates, candidateIndex + 1, taken, takenSum + 1));
        }
        return max;
    }

    public int count(int[] A) {
        Map<Long, ArrayList<Integer>> sumMap = new HashMap<>();
        for (int a = 1; a < A.length; a++) {
            long sum = 0;
            for (int x = -1; x <= 0; x++) sum += A[a + x];
            sumMap.putIfAbsent(sum, new ArrayList<>());
            sumMap.get(sum).add(a);
        }

        if (isDebug) {
            System.out.println("sumMap:\n" + sumMap.entrySet().stream().map((e) -> {
                return e.getKey() +
                        " - " +
                        e.getValue().stream().map(String::valueOf).collect(Collectors.joining(",", "[", "]"));
            }).collect(Collectors.joining("\n")));
        }

        int max = Integer.MIN_VALUE;
        for (List<Integer> indices : sumMap.values()) {
            Map<Integer, Boolean> seen = new HashMap<>();
            if (isDebug)
                System.out.println("indices: " + indices.stream().map(String::valueOf).collect(Collectors.joining(",")));
            if (indices.size() > max) {
                max = Math.max(max, pick(indices, 0, seen, 0));
            }
        }

        return max;
    }

    public void run(int[] input, int expected) {
        int max = count(input);
        System.out.println("input: " + Arrays.stream(input).mapToObj(String::valueOf).collect(Collectors.joining(",", "[", "]")) + ", output: " + max);
        System.out.println("match: " + (max == expected) + ", actual: " + max + ", expected: " + expected);
    }

    public static void main(String[] args) {
        new MaxCountAdjacentElementsSum().run(new int[]{1, 1}, 1);
        new MaxCountAdjacentElementsSum().run(new int[]{1, 3, 5, 6, 7, 9, 11}, 1);
        new MaxCountAdjacentElementsSum().run(new int[]{1, 3, 5, 6, 7, 9, 11}, 1);
        new MaxCountAdjacentElementsSum().run(new int[]{0, 1000000000, 0, 1, 999999999, 1}, 2);
        new MaxCountAdjacentElementsSum().run(new int[]{1, 1, 3, 2, 2, 1, 1, 3}, 3);
    }
}
