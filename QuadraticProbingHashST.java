/*
 * QuadraticProbingHashST.java
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

import edu.princeton.cs.algs4.Queue;
import wpialgs.day14.LinearProbingHashST;
import wpialgs.st.IST;

/**
 * <p>
 * This class implements a quadratic probing hash table using a similar logic as {@link LinearProbingHashST}, but uses a
 * quadratic function to handle collisions. Complete this class as part of your homework.
 * </p>
 * <p>
 * You may use the {@link LinearProbingHashST} code in {@code wpialgs.day14} as a starting point since it is less
 * complicated than the one provided by Sedgewick and Wayne.
 * </p>
 *
 * @version 2.0
 */
public class QuadraticProbingHashST<Key, Value> implements IST<Key, Value> {

    private final static int INIT_CAPACITY = 2; // initial default size (don't change this)

    // Class attributes
    private int N; // number of key-value pairs in the symbol table
    private int M; // size of linear probing table
    private Key[] keys; // the keys
    private Value[] vals; // the values
    private boolean[] tombstones; // true if element at this index has been deleted, false otherwise

    /**
     * Initializes an empty symbol table.
     */
    public QuadraticProbingHashST() {
        this(INIT_CAPACITY);
    }

    /**
     * This initializes an empty hash table with an initial capacity.
     *
     * @param capacity
     *            Initial array capacity in the hash table.
     */
    public QuadraticProbingHashST(int capacity) {
        M = capacity;
        N = 0;
        keys = (Key[]) new Object[M];
        vals = (Value[]) new Object[M];
        tombstones = new boolean[M];
    }

    @Override
    public void put(Key key, Value val) {
        if (N >= M / 2)
            resize(2 * M);
        int index = hash(key);
        int Hits = 0;
        int tombstoneIndex = -1;


        while (keys[index] != null) {
            if (keys[index].equals(key) && !tombstones[index]) {
                vals[index] = val;
                return;
            }
            if (tombstones[index] && tombstoneIndex == -1) {
                tombstoneIndex = index;
            }
            Hits++;
            index = (hash(key) + (Hits + Hits * Hits)/2) % M;
        }
        if (tombstoneIndex != -1) {
            index = tombstoneIndex;
        }
        keys[index] = key;
        vals[index] = val;
        tombstones[index] = false;
        N++;
    }









    @Override
    public Value get(Key key) {
        int index = hash(key);
        int Hits = 0;
        while (keys[index] != null || tombstones[index]) {
            if (keys[index] != null && keys[index].equals(key) && !tombstones[index]) {
                return vals[index];
            }
            Hits++;
            index = (hash(key) + (Hits + Hits * Hits)/2) % M;
        }
        return null;
    }

    @Override
    public void delete(Key key) {
        if (!contains(key))
            return;
        int index = hash(key);
        int Hits = 0;
        while (!key.equals(keys[index])) {
            Hits++;
            index = (hash(key) + (Hits + Hits * Hits)/2) % M;
        }
        keys[index] = null;
        vals[index] = null;
        tombstones[index] = true;
        Hits++;
        index = (hash(key) + (Hits + Hits * Hits)/2) % M;
        while (keys[index] != null) {
            if (!tombstones[index]) {
                Key keyToRehash = keys[index];
                Value valToRehash = vals[index];
                keys[index] = null;
                vals[index] = null;
                tombstones[index] = true;
                N--;
                put(keyToRehash, valToRehash);
            }
            Hits++;
            index = (hash(key) + (Hits + Hits * Hits)/2) % M;
        }
        N--;
        if (N > 0 && N <= M / 8)
            resize(M / 2);
    }

    // GH and YS: Store the number of elements in our symbol table for faster results
    @Override
    public int size() {
        return N;
    }

    // Default implementation from pp. 364
    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    // Default implementation from pp. 364
    @Override
    public boolean contains(Key key) {
        return get(key) != null;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < M; i++)
            if (keys[i] != null)
                queue.enqueue(keys[i]);

        return queue;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < M; i++) {
            s.append(i).append(" : ").append(keys[i]).append("/").append(vals[i]).append("\n");
        }

        return s.toString();
    }

    /**
     * A helper method that hashes the key and returns a value between 0 and M - 1.
     *
     * @param key
     *            Key to be hashed
     *
     * @return An index that {@code key} hashed to.
     */
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    /**
     * A helper method to resize the hash table to have the given number of chains by rehashing all the keys.
     *
     * @param capacity
     *            Desired number of array capacity.
     */
    private void resize(int capacity) {
        QuadraticProbingHashST<Key, Value> temp = new QuadraticProbingHashST<>(capacity);
        for (int i = 0; i < M; i++) {
            if (keys[i] != null) {
                temp.put(keys[i], vals[i]);
            }
        }

        keys = temp.keys;
        vals = temp.vals;
        M = temp.M;
        tombstones = temp.tombstones;
    }
}