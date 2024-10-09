/*
 * HWSTCompare.java
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

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StopwatchCPU;
import java.util.Date;
import wpialgs.st.*;

/**
 * <p>
 * This is a modified version of {@code FrequencyCounter} from pp. 372 to compare the different versions of symbol
 * table.
 * </p>
 *
 * @version 2.0
 */
public class HWSTCompare {

    private static final int WORD_LENGTH = 2;

    /**
     * The main entry point to the program.
     *
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("USAGE: java HWSTCompare <filename>");
        } else {
            String filename = args[0];
            // Welcome message
            Date today = new Date();
            StdOut.println("Welcome to CS 2223 - Symbol Table Compare!");
            StdOut.printf("\tTODAY: %s (%d)\n", today, today.getTime());
            StdOut.printf("\tRunning symbol table experiments on file: %s.\n", filename);
            StdOut.println("------------------------------------");

            // Run experiment
            for (int i = 1; i <= 7; i++) {
                // Open the file
                In in = new In(filename);

                switch (i) {
                case 1:
                    frequencyCounter(new SequentialSearchST<>(), "Sequential Search ST", in);
                    break;
                case 2:
                    frequencyCounter(new BinarySearchST<>(), "Binary Search ST", in);
                    break;
                case 3:
                    frequencyCounter(new SeparateChainingHashST<>(), "Separate Chaining Hashing ST", in);
                    break;
                case 4:
                    frequencyCounter(new LinearProbingHashST<>(), "Linear Probing Hashing ST", in);
                    break;
                case 5:
                    frequencyCounter(new QuadraticProbingHashST<>(), "Quadratic Probing Hashing ST", in);
                    break;
                case 6:
                    frequencyCounter(new BST<>(), "Binary Search Tree ST", in);
                    break;
                case 7:
                    frequencyCounter(new AVLTreeST<>(), "AVL Tree ST", in);
                    break;
                }
                StdOut.println();

                // Close file
                in.close();
            }
        }
    }

    /**
     * This runs the frequency counter on the provided input file and generates the time spent on
     * {@link IST#get(Object)}, {@link IST#put(Object, Object)}, {@link IST#contains(Object)} using the specified symbol
     * table.
     *
     * @param st
     *            A symbol table implementation
     * @param tableName
     *            Name of the symbol table implementation
     * @param in
     *            An input file
     */
    private static void frequencyCounter(IST<String, Integer> st, String tableName, In in) {
        // Statistics
        long totalWords = 0;
        long uniqueWords = 0;
        double currElapsed;
        double timeSpentOnPut = 0.0;
        double timeSpentOnGet = 0.0;
        double timeSpentOnContains = 0.0;
        double totalRunningTime = 0.0;

        // Build symbol table and count frequencies.
        StopwatchCPU timer;
        while (!in.isEmpty()) {
            String word = in.readString();
            totalWords++;

            // Ignore short keys
            if (word.length() < WORD_LENGTH) {
                continue;
            }

            // First time seeing the word
            timer = new StopwatchCPU();
            currElapsed = timer.elapsedTime();
            timeSpentOnContains += currElapsed;
            totalRunningTime += currElapsed;
            if (!st.contains(word)) {
                timer = new StopwatchCPU();
                st.put(word, 1);
                currElapsed = timer.elapsedTime();
                timeSpentOnContains += currElapsed;
                totalRunningTime += currElapsed;
                uniqueWords++;
            }
            // Have seen the word previously, and we just need to increase
            // its frequency by one.
            else {
                timer = new StopwatchCPU();
                int freq = st.get(word);
                currElapsed = timer.elapsedTime();
                timeSpentOnGet += currElapsed;
                totalRunningTime += currElapsed;

                // Increase frequency
                freq++;

                timer = new StopwatchCPU();
                st.put(word, freq);
                currElapsed = timer.elapsedTime();
                timeSpentOnPut += currElapsed;
                totalRunningTime += currElapsed;
            }
        }

        // Find a key with the highest frequency count.
        String max = "";
        timer = new StopwatchCPU();
        st.put(max, 0);
        currElapsed = timer.elapsedTime();
        timeSpentOnPut += currElapsed;
        totalRunningTime += currElapsed;

        for (String word : st.keys()) {
            timer = new StopwatchCPU();
            int freqWord = st.get(word);
            currElapsed = timer.elapsedTime();
            timeSpentOnGet += currElapsed;
            totalRunningTime += currElapsed;

            timer = new StopwatchCPU();
            int freqMax = st.get(max);
            currElapsed = timer.elapsedTime();
            timeSpentOnGet += currElapsed;
            totalRunningTime += currElapsed;

            if (freqWord > freqMax) {
                max = word;
            }
        }

        timer = new StopwatchCPU();
        int freqMax = st.get(max);
        currElapsed = timer.elapsedTime();
        timeSpentOnGet += currElapsed;
        totalRunningTime += currElapsed;

        StdOut.printf("SYMBOL TABLE IMPLEMENTATION: %s\n\nTotal Words: %d\nUnique Words: %d\n\n", tableName, totalWords,
                uniqueWords);
        StdOut.printf("Max Frequency Word: %s\nFrequency: %d\n\n", max, freqMax);
        StdOut.printf(
                "Time Spent on Get: %.5f seconds\nTime Spent on Put: %.5f seconds\nTime Spent on Contains: %.5f seconds\nTotal Elapsed Time: %.5f seconds\n",
                timeSpentOnGet, timeSpentOnPut, timeSpentOnContains, totalRunningTime);
        StdOut.printf("------------------------------------");
    }
}