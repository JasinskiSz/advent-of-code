package com.jasinski.aoc2015.day3;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Day3 {
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();

        // -----------------------------------------
        // Load file content into the memory section
        // -----------------------------------------

        Path path = Paths.get("inputs/day3/input.txt");

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

        // Part 2:
        // First house gets 2 presents - 1 from Santa and 1 from robot
        // Santa and Robot take turns in moving

        Set<House> houses = new HashSet<>();

        // Delivery is made first, only then Santa moves.
        // [0] is Santa coords
        // [1] is Robot coords
        int[] x = {0, 0};
        int[] y = {0, 0};

        // First house is on the starting position and only then Santa receives next instructions.
        houses.add(new House(0, 0));

        int index = 0; // because Santa moves first

        for (String line : lines) {
            for (char c : line.toCharArray()) {
                switch (c) {
                    case '^':
                        y[index]++;
                        break;
                    case '>':
                        x[index]++;
                        break;
                    case 'v':
                        y[index]--;
                        break;
                    case '<':
                        x[index]--;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid input");
                }

                houses.add(new House(x[index], y[index]));

                if (index == 0) {
                    index = 1;
                } else {
                    index = 0;
                }
            }
        }

        System.out.println("At least one present was received by " + houses.size() + " houses.");
    }
}