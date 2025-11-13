package com.jasinski.aoc2015.day5;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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

        // -----------------------
        // Part 2 solution section
        // -----------------------

        // Part 2 introduced new rules and obsolete the old ones.
        System.out.print("Number of Strings that are nice (part 2) is ");
        System.out.println(lines.stream()
                .filter(line -> {
                    char[] letters = line.toCharArray();
                    for (int i = 2; i < letters.length; i++) {
                        if (letters[i] == letters[i - 2]) {
                            return true;
                        }
                    }
                    return false;
                })
                .filter(line -> {
                    // take a single pair, two first letters, index 0 and 1
                    // go over a whole string:
                    // - start at index = 2
                    // - compare a pair with two letters at index 2 and 3
                    // - if a match, return true
                    // if no match found, try taking pair from index 1 and 2 and do the above
                    // if no more pairs, return false

                    char[] letters = line.toCharArray();
                    for (int i = 0; i < letters.length - 1; i++) {
                        char[] pair = {letters[i], letters[i + 1]};

                        // Go over a char array, starting from 2 and stopping at length - 1
                        for (int j = i + 2; j < letters.length - 1; j++) {
                            // here, compare two letters with a pair
                            if (pair[0] == letters[j] && pair[1] == letters[j + 1]) {
                                return true;
                            }
                        }
                    }

                    return false;
                })
                .count());
    }
}