package gr.aueb.cf.projects;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Takes the max cars been at the same time in a parking lot, by counting the time of their arrival/departure.
 */
public class Project4 {

public static void main(String[] args) {
        int[][] arr = {{1012, 1136}, {1317, 1417}, {1015, 1020}};
        int[][] transformed;

        transformed = transform(arr);
        sortByTime(transformed);

        System.out.println("Max Arrivals: " + getMaxConcurrentCars(transformed));
        }

public static int[][] transform(int[][] arr) {
        int[][] transformed = new int[arr.length * 2][2];
        for (int i = 0; i < arr.length; i++) {
                // arrival time
                transformed[i*2][0] = arr[i][0];
                // arrival time, set 2nd dimension to 1.
                transformed[i*2][1] = 1;
                // Departure time
                transformed[i*2 + 1][0] = arr[i][1];
                // departure time, set 2nd dimension to 0.
                transformed[i*2 + 1][1] = 0;
        }
        return transformed;
        }

public static void sortByTime(int[][] arr) {
        Arrays.sort(arr, Comparator.comparing(a -> a[0]));      // lambda expression, ταξινόμησε για κάθε a το πρώτο στοιχείο.
        // άρα την ώρα.
        }

public static int getMaxConcurrentCars(int[][] arr) {
        int count = 0;
        int maxCount = 0;

        for (int[] row : arr ){
        // arrival = 1 , leaving = 0
        if (row[1] == 1) {
        count++;
        if (count > maxCount) maxCount = count;
        } else { // if row[1] == 0
                count--;
        }
        }
        return  maxCount;
        }

}
