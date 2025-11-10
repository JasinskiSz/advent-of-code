package com.jasinski.aoc2015.day2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day2 {
    public static void main(String[] args) {
        // Part 1:
        // length x width x height
        // find the surface area of each box
        // 2*l*w + 2*w*h + 2*h*l
        // + the area of the smallest side

        // Part 2:
        // Ribbon required to wrap a present is the shortest distance around its sides.
        // The feet of ribbon required for the bow is equal to cubic feet of volume of the present.
        // That gives ribbon to wrap + ribbon for bow.

        List<String> lines = new ArrayList<>();

        // -----------------------------------------
        // Load file content into the memory section
        // -----------------------------------------

        Path path = Paths.get("inputs/day2/input.txt");

        try (BufferedReader reader = Files.newBufferedReader(path)) {

            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ------------------------------------
        // Calculate total paper needed section
        // ------------------------------------

        int totalPaperNeeded = 0; // in square feet
        int totalRibbonNeeded = 0; // in feet

        for (String line : lines) {

            int firstX = line.indexOf("x");
            int secondX = line.indexOf("x", firstX + 1);

            int length = Integer.parseInt(line.substring(0, firstX));
            int width = Integer.parseInt(line.substring(firstX + 1, secondX));
            int height = Integer.parseInt(line.substring(secondX + 1));

            int smallest = Math.min(length, Math.min(width, height));
            int largest = Math.max(length, Math.max(width, height));

            int secondSmallest = length + width + height - smallest - largest;

            totalPaperNeeded += 2 * (length * width + width * height + height * length) + smallest * secondSmallest;
            totalRibbonNeeded += length * width * height + smallest * 2 + secondSmallest * 2;
        }

        System.out.println("Total paper needed for all the presents is " + totalPaperNeeded + " square feet.");
        System.out.println("Total ribbon length needed to order is " + totalRibbonNeeded + " feet.");
    }
}
