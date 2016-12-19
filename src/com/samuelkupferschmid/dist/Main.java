package com.samuelkupferschmid.dist;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.MessageFormat;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        Double p = 0.015; //test for different p
        File file = new File("words.txt");
        Scanner scanner = new Scanner(file);
        BloomFilter bloomFilter = new BloomFilter(58000 / 2, p);
        boolean toggle = false;

        System.out.println("Adding words to bloom filter.");

        while(scanner.hasNext()){
            String word = scanner.next();

            if (toggle) { //add every second word
                bloomFilter.insert(word);
            }

            toggle = !toggle;
        }

        System.out.println("Testing words that are not contained.");

        scanner = new Scanner(file);
        toggle = true;
        int falsePositives = 0;
        int testedWords = 0;

        while(scanner.hasNext()){
            String word = scanner.next();

            if (toggle) { //check every second word
                if (bloomFilter.lookup(word)) {
                    falsePositives++;
                }

                testedWords++;
            }

            toggle = !toggle;
        }

        System.out.println("expected p=" + p);
        System.out.println("actual   p=" + MessageFormat.format("{0,number,#.######}", (double)falsePositives / (double)testedWords));
    }
}
