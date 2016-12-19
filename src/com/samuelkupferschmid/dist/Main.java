package com.samuelkupferschmid.dist;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        double p = 0.015;
        File file = new File("words.txt");
        Scanner scanner = new Scanner(file);

        ArrayList<String> words = new ArrayList<>();

        while(scanner.hasNext()){
            words.add(scanner.next());
        }

        BloomFilter bloomFilter = new BloomFilter(words.size() * 2 / 3, p);

        ArrayList<String> testWords = new ArrayList<>();


        System.out.println("Insert words into bloom filter (75%) and test dataset (25%)");
        for(int i = 0; i < words.size();i++){
            if(i % 3 == 0)
                testWords.add(words.get(i));
            else
                bloomFilter.insert(words.get(i));
        }

        int fpCnt = 0;

        for(String w : testWords)
            if(bloomFilter.lookup(w))
                fpCnt++;


        System.out.println("expected p: " + p);
        System.out.println("actual   p: " + (double)fpCnt / (double)testWords.size());
    }
}
