/*
 * NAME: TODO
 * PID: TODO
 */

import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * TODO
 * 
 * @author TODO
 * @since TODO
 */
public class LineCounter {

    /* Constants */
    private static final int MIN_INIT_CAPACITY = 10;
    private static final int PERCENTAGE_CONVERTER = 100;

    /**
     * Method to print the filename to console
     */
    public static void printFileName(String filename) {
        System.out.println("\n" + filename + ":");
    }

    /**
     * Method to print the statistics to console
     */

    public static void printStatistics(String compareFileName, int percentage) {
        System.out.println(percentage + "% of lines are also in " + compareFileName);
    }

    public static void main(String[] args) {

        if (args.length < 2) {
            System.err.println("Invalid number of arguments passed");
            return;
        }

        int numArgs = args.length;

        // Create a hash table for every file
        HashTable[] tableList = new HashTable[numArgs];

        // Preprocessing: Read every file and create a HashTable



        for (int i = 0; i < numArgs; i++) {
            File file = new File(args[i]);
            try{
                Scanner scanner = new Scanner(file);
                tableList[i] = new HashTable();
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    tableList[i].insert(line);
                }
                scanner.close();
            } catch (FileNotFoundException e) {
                System.out.print(e.getMessage());
            }


        }



        // Find similarities across files

        for (int i = 0; i < numArgs; i++) {
            File file = new File(args[i]);
            HashTable itself = tableList[i];
            try {

                printFileName(args[i]);
                for (int n = 0; n < tableList.length; n++) {
                    Scanner scanner = new Scanner(file);
                    double totalLines = 0;
                    if (tableList[n] == itself) {
                        continue;
                    }
                    double overlappedLines = 0;
                    while (scanner.hasNextLine()) {
                        totalLines++;

                        String line = scanner.nextLine();
                        if (tableList[n].lookup(line)) {

                            overlappedLines++;
                        }
                    }

                    double percentage = PERCENTAGE_CONVERTER * (overlappedLines/totalLines);
                    int percentage1 = (int) percentage;
                    printStatistics(args[n], percentage1);
                    scanner.close();

                }


            } catch (FileNotFoundException e) {
                System.out.print(e.getMessage());
            }



        }
    }
}



