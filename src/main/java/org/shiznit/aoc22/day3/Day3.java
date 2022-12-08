package org.shiznit.aoc22.day3;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day3 {

    private static final int OFFSET = 'A';

    static final Map<Long, Integer> scores = new HashMap<>();

    static {
        int lowercaseOffset = (int)'a' - OFFSET;
        for (int i = 0; i < 26; ++i) {
            scores.put(1L << i, 27 + i);
            scores.put(1L << (lowercaseOffset + i), 1 + i);
        }
    }

    public Day3() throws IOException {
        System.out.println("Running Day 3 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day3/input.txt"));

        List<Pair<Long, Long>> rucksacks = lines.stream()
                .map(this::parseLine)
                .collect(Collectors.toList());

        int score = rucksacks.stream()
                .mapToInt(rs -> scores.get(rs.getLeft() & rs.getRight()))
                .sum();

        System.out.println("Sum = " + score);

        System.out.println("Running Day 3 - Part 2");

        List<Long> rucksacks2 = lines.stream()
                .map(this::parseCompartment)
                .collect(Collectors.toList());

        List<List<Long>> groups = Lists.partition(rucksacks2, 3);

        score = groups.stream()
                .mapToInt(rs -> scores.get(rs.get(0) & rs.get(1) & rs.get(2)))
                .sum();

        System.out.println("Sum = " + score);
    }

    private Pair<Long, Long> parseLine(String line) {
        int div = line.length() / 2;
        String firstCompartment = line.substring(0, div);
        String secondCompartment = line.substring(div);
        return Pair.of(parseCompartment(firstCompartment), parseCompartment(secondCompartment));
    }

    private long parseCompartment(String compartmentStr) {
        long compartment = 0L;
        for (char item : compartmentStr.toCharArray()) {
            compartment = compartment | (1L << (int)item - OFFSET);
        }
        return compartment;
    }
}
