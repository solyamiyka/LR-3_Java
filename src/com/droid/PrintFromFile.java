package com.droid;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrintFromFile {
    public static String FILE = "console.txt";
    public void PrintFight() {

        try { // open file
            BufferedReader buff = new BufferedReader(new FileReader(FILE));

            while (true) {

                String line = buff.readLine();
                if (line != null) {
                    System.out.println(line);
                } else break;
            }

            buff.close();
        } catch (IOException e) {
            System.out.println("Can't open: " + FILE);
        }
    }
}
