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
        // length x width x height
        // find the surface area of each box
        // 2*l*w + 2*w*h + 2*h*l
        // + the area of the smallest side

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

        for (String line : lines) {

            String[] dimensions = line.split("x");
            int length = Integer.parseInt(dimensions[0]);
            int width = Integer.parseInt(dimensions[1]);
            int height = Integer.parseInt(dimensions[2]);

            int smallest;
            int secondSmallest;
            if (length <= width && length <= height) {
//                length is the smallest
                smallest = length;
                if (width <= height) {
                    secondSmallest = width;
                } else {
                    secondSmallest = height;
                }
            } else if (width <= length && width <= height) {
//                width is the smallest
                smallest = width;
                if (length <= height) {
                    secondSmallest = length;
                } else {
                    secondSmallest = height;
                }
            } else {
//                height is the smallest
                smallest = height;
                if (length <= width) {
                    secondSmallest = length;
                } else {
                    secondSmallest = width;
                }
            }

            totalPaperNeeded += 2 * length * width + 2 * width * height + 2 * length * height + smallest * secondSmallest;
        }

        System.out.println("Total paper needed for all the presents is " + totalPaperNeeded + " square feet.");
    }
}
