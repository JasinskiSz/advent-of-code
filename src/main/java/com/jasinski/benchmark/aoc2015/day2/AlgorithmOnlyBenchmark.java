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
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 10, time = 1)
@Fork(1)
public class AlgorithmOnlyBenchmark {
    private List<Dimension> parsedLines;

    @Setup
    public void setup() {
        parsedLines = new ArrayList<>();
        Path path = Paths.get("inputs/day2/input.txt");

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split("x");
                parsedLines.add(new Dimension(
                        Integer.parseInt(split[0]),
                        Integer.parseInt(split[1]),
                        Integer.parseInt(split[2])
                ));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    public int solution1_ifElse() {
        int totalPaperNeeded = 0;
        for (Dimension dimension : parsedLines) {
            // Logic is now on clean, pre-parsed integers
            int smallest, secondSmallest;
            if (dimension.length() <= dimension.width() && dimension.length() <= dimension.height()) {
//                dimension.length() is the smallest
                smallest = dimension.length();
                if (dimension.width() <= dimension.height()) {
                    secondSmallest = dimension.width();
                } else {
                    secondSmallest = dimension.height();
                }
            } else if (dimension.width() <= dimension.length() && dimension.width() <= dimension.height()) {
//                dimension.width() is the smallest
                smallest = dimension.width();
                if (dimension.length() <= dimension.height()) {
                    secondSmallest = dimension.length();
                } else {
                    secondSmallest = dimension.height();
                }
            } else {
//                dimension.height() is the smallest
                smallest = dimension.height();
                if (dimension.length() <= dimension.width()) {
                    secondSmallest = dimension.length();
                } else {
                    secondSmallest = dimension.width();
                }
            }
            totalPaperNeeded += (2 * dimension.length() * dimension.width() + 2 * dimension.width() * dimension.height() + 2 * dimension.height() * dimension.length()) + smallest * secondSmallest;
        }
        return totalPaperNeeded;
    }

    @Benchmark
    public int solution2_math() {
        int totalPaperNeeded = 0;
        for (Dimension dimension : parsedLines) {
            int smallest = Math.min(dimension.length(), Math.min(dimension.width(), dimension.height()));
            int largest = Math.max(dimension.length(), Math.max(dimension.width(), dimension.height()));
            int secondSmallest = dimension.length() + dimension.width() + dimension.height() - smallest - largest;
            totalPaperNeeded += (2 * dimension.length() * dimension.width() + 2 * dimension.width() * dimension.height() + 2 * dimension.height() * dimension.length()) + smallest * secondSmallest;
        }
        return totalPaperNeeded;
    }
}
