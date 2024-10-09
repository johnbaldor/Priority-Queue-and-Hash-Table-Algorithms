/*
 * QuadraticProbingTest.java
 *
 * Author: Your Name
 * Submitted on: Insert Date
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

import java.util.Scanner;
import wpialgs.st.IST;

/**
 * This is a quadratic probing symbol table test class that can be used to test your implementation.
 *
 * @version 2.0
 */
public class QuadraticProbingTest {

    /**
     * The main entry point to the program.
     *
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        // ASCII (https://www.asciitable.com/)
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter characters to store as keys in the hash table.");
        System.out.println("For each character, their ASCII representation will be used as value.");
        System.out.print("Enter text: ");
        String input = sc.nextLine();
        System.out.println();

        // Populate hash table
        IST<Character, Integer> ht = new QuadraticProbingHashST<>(1);
        for (int i = 0; i < input.length(); i++) {
            ht.put(input.charAt(i), (int) input.charAt(i));
        }

        // Print out the contents of the hash table
        System.out.println(ht);

        // Print out the contents of the hash table using keys()
        System.out.println("Print hash table [using keys()]: ");
        for (char x : ht.keys()) {
            System.out.println("Key: " + x + ", Value: " + ht.get(x));
        }
        System.out.println();

        // Delete keys from hash table
        System.out.print("Enter keys to be deleted from hash table: ");
        String toDelete = sc.nextLine();
        for (int i = 0; i < toDelete.length(); i++) {
            ht.delete(toDelete.charAt(i));
        }
        System.out.println();

        // Print out the contents of the hash table
        System.out.println(ht);

        // Print out the contents of the hash table using keys()
        System.out.println("Print hash table after deletion [using keys()]: ");
        for (char x : ht.keys()) {
            System.out.println("Key: " + x + ", Value: " + ht.get(x));
        }
    }
}