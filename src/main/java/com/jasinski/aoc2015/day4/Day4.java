package com.jasinski.aoc2015.day4;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Day4 {
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();

        // -----------------------------------------
        // Load file content into the memory section
        // -----------------------------------------

        Path path = Paths.get("inputs/day4/input.txt");

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

        // In the first part hex hash has to start with five zeroes.
        int numberOfZeroes = 6;
        String algorithm = "MD5";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfZeroes; i++) {
            sb.append(0);
        }
        String zeroes = sb.toString();

        Map<String, Integer> hexHashes = new HashMap<>();
        MessageDigest md;
        try {
            md = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        for (String line : lines) {
            StringBuilder secretKey = new StringBuilder(line);
            int originalLength = secretKey.length();

            String hexHash;
            int counter = 0;
            boolean found = false;
            while (!found) {
                byte[] digest = md.digest(secretKey.toString().getBytes());
                hexHash = HexFormat.of().formatHex(digest);

                if (hexHash.startsWith(zeroes)) {
                    found = true;
                    hexHashes.put(hexHash, counter);
                } else {
                    counter++;
                    secretKey.delete(originalLength, secretKey.length());
                    secretKey.append(counter);
                }

                if (counter % 10_000_000 == 0) {
                    System.out.println("Checked " + counter + " hashes...");
                }

                if (counter == Integer.MAX_VALUE) {
                    System.out.println("Counter overflowing!");
                    System.out.println("All numbers have been checked");
                    break;
                }
            }
        }

        System.out.println("Hashes from appended number to the secret key:");
        int counter = 1;
        for (String hex : hexHashes.keySet()) {
            System.out.println(counter++ + ". " + hex + " was produced by adding " + hexHashes.get(hex));
        }
    }
}
