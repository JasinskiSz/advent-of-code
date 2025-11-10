package com.jasinski.aoc2015.day1;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day1 {
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();

        // Load file content into the memory
        Path path = Paths.get("inputs/day1/input.txt");

        try (BufferedReader reader = Files.newBufferedReader(path)) {

            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Santa starts at floor 0
        int floor = 0;

        // opening parenthesis means go up one floor
        // closing parenthesis means go down one floor

        for (int lineCount = 0; lineCount < lines.size(); lineCount++) {

            String line = lines.get(lineCount);

            for (int i = 0; i < line.length(); i++) {
                char character = line.charAt(i);

                switch (character) {
                    case '(':
                        floor++;
                        break;
                    case  ')':
                        floor--;
                        break;
                    default:
                        break;
                }
            }
        }

        System.out.printf("Instructions take Santa to the floor no " + floor + ".");
    }
}
