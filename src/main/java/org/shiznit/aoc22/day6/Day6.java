package org.shiznit.aoc22.day6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Day6 {

    public Day6() throws IOException {
        System.out.println("Running Day 6 - Part 1");

        char[] input = Files.readString(Path.of("src/main/java/org/shiznit/aoc22/day6/input.txt")).toCharArray();

        System.out.println("End of Marker = " + getEndOfMarker(input, 4));

        System.out.println("Running Day 6 - Part 2");

        System.out.println("End of Marker = " + getEndOfMarker(input, 14));
    }

    private int getEndOfMarker(char[] datastream, int markerSize) {
        int[] counts = new int[26];

        int i;
        for (i = 0; i < datastream.length - markerSize; ++i) {
            Arrays.fill(counts, 0);
            for (int j = 0; j < markerSize; ++j) {
                counts[datastream[i+j]-'a']++;
            }

            if (Arrays.stream(counts).filter(j -> j > 0).count() == markerSize) {
                break;
            }
        }

        return i + markerSize;
    }
}
