package com.jasinski.aoc2015.day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day5 {
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();

        // -----------------------------------------
        // Load file content into the memory section
        // -----------------------------------------

        Path path = Paths.get("inputs/day5/input.txt");

        try (BufferedReader reader = Files.newBufferedReader(path)) {

            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ----------------
        // Solution section
        // ----------------

        // How many strings are nice?
        Character[] vowels = {'a', 'e', 'i', 'o', 'u'};
        String[] forbidden = {"ab", "cd", "pq", "xy"};

        System.out.print("Number of Strings that are nice is ");
        System.out.println(lines.stream()
                .filter(line -> Arrays.stream(forbidden).noneMatch(line::contains))
                .filter(line -> line.chars()
                        .filter(letter -> Arrays.stream(vowels).anyMatch(vowel -> letter == vowel))
                        .count() >= 3)
                .filter(line -> {
                    Character previousChar = null;
                    for (char c : line.toCharArray()) {
                        if (previousChar != null && c == previousChar) {
                            return true; // A letter, that appears twice in a row
                        } else {
                            previousChar = c;
                        }
                    }
                    return false;
                })
                .count());
    }
}

