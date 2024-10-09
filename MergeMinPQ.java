/*
 * MergeMinPQ.java
 *
 * Author: John Baldor
 * Submitted on: 9/25/24
 *
 * Academic Honesty Declaration:
 *
 * The following code represents my own work and I have neither received nor given assistance
 * that violates the collaboration policy posted with this assignment. I have not copied or modified code
 * from any other source other than the homework assignment, course textbook, or course lecture slides.
 * Any unauthorized collaboration or use of materials not permitted will be subjected to academic integrity policies of
 * WPI and CS 2223.
 *
 * I acknowledge that this homework assignment is based upon an assignment created by WPI and that any publishing or
 * posting of this code is prohibited unless I receive written permission from WPI.
 */
package wpialgs.hw03;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import java.util.Date;
import java.util.Random;

/**
 * <p>
 * This class requires you complete the {@link #isSorted(int[])} and {@link #merge(MinPQ, MinPQ)} methods to produce an
 * array in sorted order.
 * </p>
 *
 * @version 2.0
 */
public class MergeMinPQ {

    /**
     * The main entry point to the program.
     *
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("USAGE: java MergeMinPQ <seed for random number generator>");
        } else {
            long seed = Long.parseLong(args[0]);

            // Welcome message
            Date today = new Date();
            StdOut.println("Welcome to CS 2223 - Merge Min Priority Queues!");
            StdOut.printf("\tTODAY: %s (%d)\n", today, today.getTime());
            StdOut.printf("\tRunning MergeMinPQ using seed: %d\n", seed);
            StdOut.println("------------------------------------");

            // run the experiment
            runExperiment(seed);
        }
    }

    /**
     * This checks to see if the array is in sorted order.
     *
     * @param arr
     *            An array to check
     *
     * @return {@code true} if the array is in sorted order, {@code false} wise.
     */
    public static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] > arr[i]) {
                return false; // If any element is greater than the next, return false
            }
        }
        return true;
    }


    /**
     * Retrieve all values from one and two (leaving them both empty) and return a single int[] array that contains the
     * combined values in sorted order.
     *
     * @param one
     *            First min priority queue
     * @param two
     *            Second min priority queue
     *
     * @return Array in sorted order
     */
    public static int[] merge(MinPQ<Integer> one, MinPQ<Integer> two) {
        int[] result = new int[one.size() + two.size()];

        int i = 0;


        while (!one.isEmpty() || !two.isEmpty()) {
            if (one.isEmpty()) {
                result[i++] = two.delMin();
            }
            else if (two.isEmpty()) {
                result[i++] = one.delMin();
            }
            else {

                if (one.min() < two.min()) {
                    result[i++] = one.delMin();
                } else {
                    result[i++] = two.delMin();
                }
            }
        }


        assert isSorted(result);
        return result;
    }

    /***************************************************************************
     * Helper functions
     ***************************************************************************/

    /**
     * Create a heap of N random integers from 1 to 1048576.
     * <p>
     * NOTE: USE AS IS.
     *
     * @param N
     *            Number of elements in the heap
     * @param seed
     *            A seed to be used with a random number generator
     *
     * @return A min priority queue of the specified size.
     */
    private static MinPQ<Integer> randomHeap(int N, long seed) {
        Random rnd = new Random(seed);
        MinPQ<Integer> pq = new MinPQ<>(N);
        for (int i = 0; i < N; i++) {
            pq.insert(rnd.nextInt(1048576));
        }

        return pq;
    }

    /**
     * A helper method to run the {@link #merge(MinPQ, MinPQ)} on min priority queues of different sizes.
     * <p>
     * NOTE: USE AS IS.
     *
     * @param seed
     *            A seed to be used with a random number generator
     *
     * @throws RuntimeException
     *             We didn't complete the assignment correctly and the resulting array is not in sorted order.
     */
    private static void runExperiment(long seed) {
        for (int n1 = 1024; n1 <= 65536; n1 *= 2) {
            for (int n2 = 1024; n2 <= 65536; n2 *= 2) {
                MinPQ<Integer> h1 = randomHeap(n1, seed);
                MinPQ<Integer> h2 = randomHeap(n2, seed);

                // now validate that merge into a single ascending array worked.
                int[] combined = merge(h1, h2);
                if (!isSorted(combined)) {
                    throw new RuntimeException("NOT IN SORTED ORDER!");
                } else {
                    StdOut.printf(
                            "h1 contains: %5d elements, h2 contains: %5d elements, sorted and merged array contains: %6d elements\n",
                            n1, n2, combined.length);
                }
            }
            StdOut.println("------------------------------------");
        }
    }
}