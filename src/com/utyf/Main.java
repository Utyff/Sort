package com.utyf;

import java.util.*;


public class Main {

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String str;

        DataFile df = new DataFile("f1.txt");
        df.createRandom(1000);
        // str = s.nextLine(); System.out.println(str);

        String[] arr = MergeSort.getRandomArray(20);
        //System.out.println(arr.length);

        MergeSort.doSort(arr);
        for (String anArr : arr) System.out.println(anArr);

        // str = s.nextLine(); System.out.println(str);
    }
}

