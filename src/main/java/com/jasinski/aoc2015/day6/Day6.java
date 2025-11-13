package com.jasinski.aoc2015.day6;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();

        // -----------------------------------------
        // Load file content into the memory section
        // -----------------------------------------

        Path path = Paths.get("inputs/day6/input.txt");

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

        // ---------------------
        // Grid creation section
        // ---------------------

        Boolean[][] grid = new Boolean[1000][1000]; // this grid represents a grid of light bulbs

        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                // filling the grid with turned off light bulbs
                grid[x][y] = false;
            }
        }

        // ---------------------------
        // Lights on/off logic section
        // ---------------------------

        for (String line : lines) {
            String[] split = line.split(" ");
            int x, y;
            int endX, endY;
            Boolean lightsOn = null;
            String[] startCoords, endCoords;

            if (split.length == 5) {
                if (split[1].equals("off")) {
                    lightsOn = false;
                } else if (split[1].equals("on")) {
                    lightsOn = true;
                } else {
                    throw new IllegalArgumentException("Unexpected argument - got something else than 'on' or 'off'.");
                }

                startCoords = split[2].split(",");
                endCoords = split[4].split(",");
            } else if (split.length == 4) {
                startCoords = split[1].split(",");
                endCoords = split[3].split(",");
            } else {
                throw new IllegalArgumentException("Unexpected line format or its length.");
            }

            x = Integer.parseInt(startCoords[0]);
            y = Integer.parseInt(startCoords[1]);

            endX = Integer.parseInt(endCoords[0]);
            endY = Integer.parseInt(endCoords[1]);

            for (int i = y; i <= endY; i++) {
                for (int j = x; j <= endX; j++) {
                    if (lightsOn == null) {
                        grid[j][i] = !grid[j][i];
                    } else {
                        grid[j][i] = lightsOn;
                    }
                }
            }
        }

        // --------------------------
        // Count the turned on lights
        // --------------------------

        int counter = 0;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length; x++) {
                if (grid[x][y]) {
                    counter++;
                }
            }
        }
        System.out.print("Number of bulbs that are turned on: ");
        System.out.println(counter);
    }
}
