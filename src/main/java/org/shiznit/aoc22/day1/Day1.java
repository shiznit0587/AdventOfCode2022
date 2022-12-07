package org.shiznit.aoc22.day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day1 {

    public Day1() throws IOException {
        System.out.println("Running Day 1 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day1/input.txt"));

        List<List<Integer>> elves = new ArrayList<>();
        List<Integer> elf = new ArrayList<>();
        elves.add(elf);

        for (String line : lines) {
            if (line.isEmpty()) {
                elf = new ArrayList<>();
                elves.add(elf);
            }
            else {
                elf.add(Integer.parseInt(line));
            }
        }

        List<Integer> elfTotals = elves.stream()
                .map(e -> e.stream().mapToInt(Integer::intValue).sum())
                .collect(Collectors.toList());

        int elfMax = elfTotals.stream()
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow();

        System.out.println("Max = " + elfMax);

        System.out.println("Running Day 1 - Part 2");

        int top3Total = elfTotals.stream()
                .sorted(Collections.reverseOrder())
                .limit(3)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Top Three Total = " + top3Total);
    }
}
