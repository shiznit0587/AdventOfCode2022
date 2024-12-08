package org.shiznit.aoc22.day12;

import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.PriorityQueue;

public class Day12 {

    public Day12() throws IOException {
        System.out.println("Running Day 12 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day12/input.txt"));

        System.out.println("Running Day 12 - Part 2");

        Pair<Integer, Integer> start = null;
        Pair<Integer, Integer> end = null;

        int[][] mountain = new int[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); ++i) {
            char[] chars = lines.get(i).toCharArray();
            for (int j = 0; j < chars.length; ++j) {
                if (chars[j] == 'S') {
                    start = Pair.of(i, j);
                    mountain[i][j] = 0;
                }
                else if (chars[j] == 'E') {
                    end = Pair.of(i, j);
                    mountain[i][j] = 25;
                }
                else {
                    mountain[i][j] = chars[j] - 'a';
                }
            }
        }

        Deque<Pair<Integer, Integer>> queue = new ArrayDeque<>();
        new PriorityQueue<>();

        // Find start and end.
        // S = -14, E = -28

    }



}
