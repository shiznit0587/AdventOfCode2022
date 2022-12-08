package org.shiznit.aoc22.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {

    static final Pattern pattern = Pattern.compile("(\\d+)\\-(\\d+),(\\d+)\\-(\\d+)");

    public Day4() throws IOException {

        System.out.println("Running Day 4 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day4/input.txt"));

        List<int[]> assignments = lines.stream()
                .map(this::parseLine)
                .collect(Collectors.toList());

        long count = assignments.stream()
                .filter(a -> (a[0] <= a[2] && a[1] >= a[3]) || (a[2] <= a[0] && a[3] >= a[1]))
                .count();

        System.out.println("Count = " + count);

        System.out.println("Running Day 4 - Part 2");

        count = assignments.stream()
                .filter(a -> (a[0] < a[2] && a[1] < a[2]) || (a[2] < a[0] && a[3] < a[0]))
                .count();

        System.out.println("Count = " + (assignments.size() - count));
    }


    int[] parseLine(String line) {
        Matcher match = pattern.matcher(line);
        return new int[] {
                Integer.parseInt(match.group(1)),
                Integer.parseInt(match.group(2)),
                Integer.parseInt(match.group(3)),
                Integer.parseInt(match.group(4))
        };
    }
}
