package gr.aueb.cf.projects;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Project3 {
    public static String[][] arrayOfCharCounts = new String[256][2];

    public static void main(String[] args) {
        try{
            readFile("/Users/pavlospoulos/IdeaProjects/CodingFactory/tmp/test1.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readFile(String pathGiven) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(pathGiven))) {
            int ch;
            while (((ch = br.read()) != -1)) {
                System.out.println(ch  + " " + ((char) ch));
                updateArrayOfCharCounts((char) ch);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void updateArrayOfCharCounts(char ch) {
        int index = (int) ch;

        // If the character hasn't been encountered before, initialize the count to 1
        if (arrayOfCharCounts[index][0] == null) {
            arrayOfCharCounts[index][0] = String.valueOf(ch);
            arrayOfCharCounts[index][1] = "1";
        } else {
            // If the character has been encountered before, increment the count
            int currentCount = Integer.parseInt(arrayOfCharCounts[index][1]);
            int newCount = currentCount + 1;
            arrayOfCharCounts[index][1] = String.valueOf(newCount);
        }
    }
}
