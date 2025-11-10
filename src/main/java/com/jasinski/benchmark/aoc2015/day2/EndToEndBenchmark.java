package com.jasinski.benchmark.aoc2015.day2;

import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
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
}
