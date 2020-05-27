/*
 * Name: Yin Lam Lai
 * PID: A15779757
 */


import java.util.Objects;

/**
 * Creates a Cuckoo hashtable
 * 
 * @author Yin Lam Lai
 * @since 26/05/2020
 */
public class HashTable implements IHashTable {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;
    private static final int DEFAULT_CAPACITY = 20;
    private static final double MAX_LOAD_FACTOR = 0.5;
    private static final int CAPACITY_DIVIDER = 2;
    private static final int ALPHABET_LENGTH = 27;
    private static final int SHIFT_LEFT = 5;
    private static final int SHIFT_RIGHT = 27;

    /* Instance variables */
    private String[] table1, table2; // sub-tables
    private int nElems; // size
    private int counter, rehashCounter;
    private String stats;


    /**
     * Initialises a Hashtable with capacity of 20
     */
    public HashTable() {
        this.table1 = new String[MIN_INIT_CAPACITY];
        this.table2 = new String[MIN_INIT_CAPACITY];
        this.nElems = 0;
        this.stats = "";
        this.counter = 0;
        this.rehashCounter = 1;

    }

    /**
     * Initialises a hashtable with given capacity
     * @param capacity capacity of hashtable
     */
    public HashTable(int capacity) {
        if (capacity < MIN_INIT_CAPACITY) {
            throw new IllegalArgumentException();
        }
        capacity = capacity / CAPACITY_DIVIDER;

        this.table1 = new String[capacity];
        this.table2 = new String[capacity];
        this.nElems = 0;
        this.stats = "";
        this.counter = 0;
        this.rehashCounter = 1;

    }


    /**
     * inserts value into hashtable
     *
     * @param value given key of value
     * @return true if value is inserted, false if value cannot be inserted or already exists
     * @throws NullPointerException if value is null
     */
    @Override
    public boolean insert(String value) {
        int count = 0;

        double rehash = (double) nElems / capacity();


        if (value == null) {
            throw new NullPointerException();
        }

        // if nElems / capacity is over the load factor, rehash
        if (rehash > MAX_LOAD_FACTOR) {
            rehash();
        }

        // If value is already in the hashtable, return false
        if (lookup(value)) {
            return false;
        }

        // Note down the starter value for causes of infinite loop
        String starter = value;
        while (true) {

            // If the current value is the starter value, it is an infinite loop, therefore, rehash
            if (value == starter) {
                if (count > 0) {
                    rehash();
                }
            }

            // Hash value with hash function 1 and check if there's a space in hashtable 1
            int table1Index = hashOne(value);
            if (table1[table1Index] == null) {
                table1[table1Index] = value;
                nElems++;
                counter += count;
                return true;
            } else {
                // if there isn't, hash value with hash function 2 and check hash table 2
                String temp = table1[table1Index];
                table1[table1Index] = value;
                int table2Index = hashTwo(temp);
                count++;
                if (table2[table2Index] == null) {
                    table2[table2Index] = temp;
                    nElems++;
                    counter += count;
                    return true;
                } else {
                    // if theres no space in hash table 2, change value and repeat loop
                    value = table2[table2Index];
                    table2[table2Index] = temp;
                    count++;

                }
            }
        }
    }


    /**
     * deletes value from hashtable
     *
     * @param value given key of value
     * @return true if value is deleted, false if value cannot be deleted or does not exist
     * @throws NullPointerException if value is null
     */
    @Override
    public boolean delete(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        if (!lookup(value)) {
            return false;
        }
        if (Objects.equals(table1[hashOne(value)], value)) {
            table1[hashOne(value)] = null;
            nElems--;
            return true;
        }
        if (Objects.equals(table2[hashTwo(value)], value)) {
            table2[hashTwo(value)] = null;
            nElems--;
            return true;
        }
        return false;
    }

    /**
     * Finds value in hashtable
     *
     * @param value given key of value
     * @return true if value is in hashtable, false if value is not in hashtable
     * @throws NullPointerException if value is null
     */
    @Override
    public boolean lookup(String value) {
        if (value == null) {
            throw new NullPointerException();
        }
        if (Objects.equals(table1[hashOne(value)], value)) {
            return true;
        }
        if (Objects.equals(table2[hashTwo(value)], value)) {
            return true;
        }
        return false;
    }

    /**
     * returns the number of elements in hashtable
     *
     * @return the number of elements in the hashtable
     */
    @Override
    public int size() {
        return nElems;
    }


    /**
     * returns the capacity of the hashtable
     *
     * @return the total capacity of the hashtable
     */
    @Override
    public int capacity() {

        return table1.length + table2.length;
    }

    /**
     * Get the string representation of the hash table.
     *
     * Format Example:
     * | index | table 1 | table 2 |
     * | 0 | Marina | [NULL] |
     * | 1 | [NULL] | DSC30 |
     * | 2 | [NULL] | [NULL] |
     * | 3 | [NULL] | [NULL] |
     * | 4 | [NULL] | [NULL] |
     *
     * @return string representation
     */
    @Override
    public String toString() {
        String returned = "| index | table 1 | table2 |" + "\n";
        for (int n = 0; n < table1.length; n++) {
            returned += "| " + (n) + " | ";
            if (table1[n] == null) {
                returned += "[NULL] | ";
            } else {
                returned += table1[n] + " | ";
            }
            if (table2[n] == null) {
                returned += "[NULL] |" + "\n";
            } else {
                returned += table2[n] + " |" + "\n";
            }
        }
        return returned;
    }

    /**
     * Get the rehash stats log.
     *
     * Format Example: 
     * Before rehash # 1: load factor 0.80, 3 evictions.
     * Before rehash # 2: load factor 0.75, 5 evictions.
     *
     * @return rehash stats log
     */
    public String getStatsLog() {
        return stats;
    }

    private void rehash() {
        String[] og1 = table1;
        String[] og2 = table2;
        double d = (double) nElems / capacity();

        stats += "Before rehash # " + (rehashCounter) + ": load factor " + (d) + ", " + (counter)
                + " evictions.\n";
        counter = 0;



        table1 = new String[table1.length * CAPACITY_DIVIDER];
        table2 = new String[table2.length * CAPACITY_DIVIDER];
        nElems = 0;
        for (String n : og1) {
            if (n != null) {
                insert(n);
            }
        }
        for (String n : og2) {
            if (n != null) {
                insert(n);
            }
        }
        rehashCounter++;
    }

    /**
     * hash function for first hashtable
     *
     * @param value given key of value
     * @return index for value in hashtable 1
     */
    private int hashOne(String value) {
        int hashVal = 0;
        for (int n = 0; n < value.length(); n++) {
            int letter = value.charAt(n);
            hashVal = (hashVal * ALPHABET_LENGTH + letter) % table1.length;
        }
        return Math.abs(hashVal % table1.length);
    }

    /**
     * hash function for second hashtable
     *
     * @param value given key of value
     * @return index for value in hashtable 2
     */
    private int hashTwo(String value) {
        int hashVal = 0;
        for (int n = 0; n < value.length(); n++) {
            int left = hashVal << SHIFT_LEFT;
            int right = hashVal >>> SHIFT_RIGHT;
            hashVal = (left | right) ^ value.charAt(n);
        }
        return Math.abs(hashVal % table2.length);
    }
}
