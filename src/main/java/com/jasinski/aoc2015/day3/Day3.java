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

        Set<House> houses = new HashSet<>();

        // Delivery is made first, only then Santa moves.
        int x = 0;
        int y = 0;

        // First house is on the starting position and only then Santa receives next instructions.
        houses.add(new House(x, y));

        for (String line : lines) {
            for (char c : line.toCharArray()) {
                switch (c) {
                    case '^':
                        y++;
                        break;
                    case '>':
                        x++;
                        break;
                    case 'v':
                        y--;
                        break;
                    case '<':
                        x--;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid input");
                }

                houses.add(new House(x, y));

            }
        }

        System.out.println("At least one present was received by " + houses.size() + " houses.");
    }
}