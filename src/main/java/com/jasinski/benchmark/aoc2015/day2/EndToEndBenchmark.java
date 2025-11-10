package com.jasinski.benchmark.aoc2015.day2;

import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(1)
public class EndToEndBenchmark {

    private List<String> lines;

    @Setup
    public void setup() {
        lines = new ArrayList<>();

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
    }

    @Benchmark
    public int solution1_ifElse() {
        int totalPaperNeeded = 0;

        for (String line : lines) {
            String[] split = line.split("x");
            int length = Integer.parseInt(split[0]);
            int width = Integer.parseInt(split[1]);
            int height = Integer.parseInt(split[2]);

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

        return totalPaperNeeded;
    }

    @Benchmark
    public int solution2_math() {
        int totalPaperNeeded = 0;

        for (String line : lines) {
            String[] split = line.split("x");
            int length = Integer.parseInt(split[0]);
            int width = Integer.parseInt(split[1]);
            int height = Integer.parseInt(split[2]);

            int smallest = Math.min(length, Math.min(width, height));
            int largest = Math.max(length, Math.max(width, height));

            int secondSmallest = length + width + height - smallest - largest;

            totalPaperNeeded += 2 * length * width + 2 * width * height + 2 * length * height + smallest * secondSmallest;
        }
        return totalPaperNeeded;
    }

    /**
     * This is a solution 2 variant, that uses precompiled split patter that might be faster.
     */

    private static final Pattern SPLIT_PATTERN = Pattern.compile("x");

    @Benchmark
    public int solution2_math_precompiledPattern() {
        int totalPaperNeeded = 0;

        for (String line : lines) {
            String[] split = SPLIT_PATTERN.split(line);
            int length = Integer.parseInt(split[0]);
            int width = Integer.parseInt(split[1]);
            int height = Integer.parseInt(split[2]);

            int smallest = Math.min(length, Math.min(width, height));
            int largest = Math.max(length, Math.max(width, height));

            int secondSmallest = length + width + height - smallest - largest;

            totalPaperNeeded += 2 * length * width + 2 * width * height + 2 * length * height + smallest * secondSmallest;
        }
        return totalPaperNeeded;
    }

    @Benchmark
    public int solution3_arraySort() {
        int totalPaperNeeded = 0;

        for (String line : lines) {
            String[] split = line.split("x");
            int[] dimensions = {
                    Integer.parseInt(split[0]),
                    Integer.parseInt(split[1]),
                    Integer.parseInt(split[2])
            };
            Arrays.sort(dimensions);

            int length = dimensions[0];
            int width = dimensions[1];
            int height = dimensions[2];

            totalPaperNeeded += 2 * length * width + 2 * width * height + 2 * length * height + length * width;
        }

        return totalPaperNeeded;
    }

    @Benchmark
    public int solution4_manualParsing() {
        int totalPaperNeeded = 0;

        for (String line : lines) {
            int firstX = line.indexOf("x");
            int secondX = line.indexOf("x", firstX + 1);

            int length = Integer.parseInt(line.substring(0, firstX));
            int width = Integer.parseInt(line.substring(firstX + 1, secondX));
            int height = Integer.parseInt(line.substring(secondX + 1));

            int smallest = Math.min(length, Math.min(width, height));
            int largest = Math.max(length, Math.max(width, height));

            int secondSmallest = length + width + height - smallest - largest;

            totalPaperNeeded += 2 * length * width + 2 * width * height + 2 * length * height + smallest * secondSmallest;
        }
        return totalPaperNeeded;
    }

    @Benchmark
    public int solution4_manualParsing_differentCalculations() {
        int totalPaperNeeded = 0;

        for (String line : lines) {
            int firstX = line.indexOf("x");
            int secondX = line.indexOf("x", firstX + 1);

            int length = Integer.parseInt(line.substring(0, firstX));
            int width = Integer.parseInt(line.substring(firstX + 1, secondX));
            int height = Integer.parseInt(line.substring(secondX + 1));

            int smallest = Math.min(length, Math.min(width, height));
            int largest = Math.max(length, Math.max(width, height));

            int secondSmallest = length + width + height - smallest - largest;

            int side1 = length * width;
            int side2 = width * height;
            int side3 = height * length;

//            totalPaperNeeded += 2 * length * width + 2 * width * height + 2 * length * height + smallest * secondSmallest;
            totalPaperNeeded += 2 * (side1 + side2 + side3) + smallest * secondSmallest;
        }
        return totalPaperNeeded;
    }
}
