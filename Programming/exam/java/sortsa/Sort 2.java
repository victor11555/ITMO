package ru.ifmo.stddev.zabrovskiy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Sort {

    public static void main(String[] args) {
        BufferedReader reader;
        try {
            if (args[0].startsWith("--output=")) {
                reader = new BufferedReader(new FileReader(args[0].substring("--output=".length())));
            } else {
                reader = new BufferedReader(new InputStreamReader(System.in));
            }
            Set<String> params = new HashSet<>();
            for (int i = 1; i < args.length; i++) {
                params.add(args[i]);
            }
            NavigableSet<String> set = new TreeSet<>(new SortComparator(params));

            String s;
            while ((s = reader.readLine()) != null) {
                set.add(s);
            }
            for (String str : set) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
