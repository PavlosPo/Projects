package gr.aueb.cf.projects;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.security.cert.TrustAnchor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Here we complete a program, where it reads from a file 1 to 49 numbers between 1 and 49,
 * until it finds the -1. It generates in other file *.txt the complete combinations of 6 numbers that can be created,
 * using specific filters (see below at the class docs)
 *
 */
public class Project1 {

    public static void main(String[] args) {

        try (Scanner in = new Scanner(new File("/Users/pavlospoulos/IdeaProjects/CodingFactory/tmp/Project1Numbers.txt"));
             PrintStream ps = new PrintStream("/Users/pavlospoulos/IdeaProjects/CodingFactory/tmp/ProjectNumbersOut.txt", StandardCharsets.UTF_8)) {

            final int COMBINATIONS_SIZE = 6;
            int[] inputNumbers = new int[49];
            int pivot = 0;              // δείχνει σε θέση.
            int[] result = new int[6];
            int num;                    // διαβάζω τον αριθμό
            int window;                 // πόσες θέσεις μετακινούνται

            while ((num = in.nextInt()) != -1 && pivot <= 48) {
                inputNumbers[pivot] = num;
                pivot++;
            }

            int[] numbers = Arrays.copyOfRange(inputNumbers, 0, pivot);
            Arrays.sort(numbers);

            window = pivot - COMBINATIONS_SIZE;
            for (int i = 0; i <= window; i++) {
                for (int j = i + 1; j <= window + 1; j++) {
                    for (int k = j + 1; k <= window + 2; k++) {
                        for (int l = k + 1; l <= window + 3; l++) {
                            for (int m = l + 1; m <= window + 4; m++) {
                                for (int n = m + 1; n <= window + 5; n++) {
                                    result[0] = numbers[i];
                                    result[1] = numbers[j];
                                    result[2] = numbers[k];
                                    result[3] = numbers[l];
                                    result[4] = numbers[m];
                                    result[5] = numbers[n];

                                    if (!isEven(result) &&
                                            (!isOdd(result)) &&
                                            (!isContiguous(result)) &&
                                            (!isSameEnding(result))
                                    ) {
//                                        System.out.printf("%d %d %d %d %d %d\n",
//                                                result[0], result[1], result[2], result[3], result[4], result[5]);
                                        ps.printf("%d %d %d %d %d %d\n",
                                                result[0], result[1], result[2], result[3], result[4], result[5]);
                                    }
//                                    ps.printf("%d %d %d %d %d %d\n",
//                                            result[0], result[1], result[2], result[3], result[4], result[5]);

                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Given a  path, it reads the file and checks for errors, if none it returns the list of the integers,
     * until it finds -1 or an alphanumerical character.
     * @param path      The given path for the file to read.
     */
    public static int[] readFile(String path){
        if (path == null) return new int[] {};
        try (Scanner sc = new Scanner(new File(path))){
            ArrayList<Integer> integerList = new ArrayList<>();
            while (sc.hasNextInt()) {
                int nextInt = sc.nextInt();
                if (nextInt == -1) {
                    break;
                }
                integerList.add(nextInt);
            }
            // Convert the ArrayList to an array
            int[] integers = new int[integerList.size()];
            for (int i = 0; i < integerList.size(); i++) {
                integers[i] = integerList.get(i);
            }
            return integers;
        } catch (Exception e) {
             e.printStackTrace();
            System.out.println("File not found error! Escaping.");
            System.exit(1);
        }
        return new int[0];
    }

    /**
     * If it more than 4 even numbers, return true, else return false.
     * @param arr       An array of integers
     * @return          False if array has less than 4 even numbers in total.
     */
    public static boolean isEven(int[] arr) {
        int counter = 0 ;
        for (int number : arr) {
            if (number % 2 == 0) counter++;
        }
        return counter > 4;    // boolean
    }

    /**
     * If it has the most of 4 odd numbers return false, else true.
     * @param arr       The array of integers to check
     * @return          False if there are less or equal than 4 even numbers in total.Else true.
     */
    public static boolean isOdd(int[] arr) {
        int counter = 0;
        for(int number : arr) {
            if (number % 2 != 0) counter++;
        }
        return counter > 4;     // boolean
    }

    /**
     * Checks if two contiguous numbers exists in each array oof integer given
     * @param arr       The array given with ints.
     * @return          True if there are two or more contiguous numbers, false otherwise.
     */
    public static boolean isContiguous(int[] arr) {
        int counter = 0;
        for (int i = 0; i < arr.length; i++) {
            // Skip the i == 0.
            if (i != 0) {
                if (arr[i] == arr[i-1]) {
                    return true;
                    }
            }

        }
        return false;
    }

    /**
     * Check if there are numbers ending with the same digit, returns true if there are
     * more than 3 numbers with the same ending, respectively.
     * @param arr       The array with ints given.
     * @return          True if there are more than 3 numbers with the same ending, false otherwise.
     */
    public static boolean isSameEnding(int[] arr) {
        int counter = 0;                                            // To count the same last digits
        int lastDigit = 0;                                          // Last digit of each operation
        int[] lastDigits = new int[arr.length];
        // Store the last digits of each number in the lastDigits[].
        for (int i = 0; i < arr.length; i++){
            lastDigit = arr[i] % 10;
            lastDigits[i] = lastDigit;
        }

        // Search for the same last digits.
        for (int i = 0 ; i < lastDigits.length; i++) {
            // Finds the first same number
            if (numberExistsIn(lastDigits, lastDigits[i], i, lastDigits.length)){
                // Loops the rest array
                for (int j = i; j < lastDigits.length; j++) {
                    // Finds the second same number, return True because
                    // there are 3 with the same number in total.
                    if (numberExistsIn(lastDigits, lastDigits[j], j, lastDigits.length)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * It is a helper method of the isSameEnding() and in the isSameTen() methods,
     * to search for the same number in the
     * rest of an array, given the start and end to search for it.
     *
     * @param arr       The array with integers
     * @param numberToSearch The number to search for in the array.
     * @param beg       The Index of the search to begin
     * @param end       The Index of the search to end
     * @return          True if there is a same number in the rest array, false otherwise
     */
    public static boolean numberExistsIn(int[] arr, int numberToSearch ,int beg, int end) {
        int[] subArray = new int[arr.length];
        // Create the subArray
        subArray = Arrays.copyOfRange(arr, beg + 1, end );
        // Loop in the sub array to check if there is the same number
        for (int number : subArray) {
            if (numberToSearch == number) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there are same numbers in the range of 0...9
     * @return      True if it finds same numbers that are in the range of 0 ... 9,
     *              else otherwise
     */
    public static boolean isSameTen(int[] arr){
        for(int number : arr){
            // It is in the range of 0...9
            if (number < 10 && number >= 0) {
                if (numberExistsIn(arr, number, 0, arr.length)) {
                    return true;
                }
            }
        }
        return false;
    }

}
