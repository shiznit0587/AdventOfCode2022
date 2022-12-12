package org.shiznit.aoc22.day9;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day9 {

    public Day9() throws IOException {
        System.out.println("Running Day 9 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day9/input.txt"));

        Knot head = new Knot();
        Knot tail = new Knot();

        Table<Integer, Integer, Integer> tailVisits = HashBasedTable.create();

        for (String line : lines) {
            String[] parts = line.split(" ");
            int hdx = 0, hdy = 0;
            if (parts[0].equals("R")) {
                hdx = 1;
            } else if (parts[0].equals("L")) {
                hdx = -1;
            } else if (parts[0].equals("U")) {
                hdy = 1;
            } else if (parts[0].equals("D")) {
                hdy = -1;
            }

            int distance = Integer.parseInt(parts[1]);

            for (int i = 0; i < distance; ++i) {

                // Move head.
                head.x += hdx;
                head.y += hdy;

                // Move tail.
                int tdx = tail.x - head.x;
                int tdy = tail.y - head.y;

                // Only move the tail if it's not "touching" the head.
                if (!(Math.abs(tdx) <= 1 && Math.abs(tdy) <= 1)) {
                    if (tdx != 0 && tdy != 0) {
                        // Move the tail one space diagonally toward head.
                        tail.x = (tdx > 0) ? tail.x - 1 : tail.x + 1;
                        tail.y = (tdy > 0) ? tail.y - 1 : tail.y + 1;
                    } else if (tdx != 0) {
                        // Move the tail one space horizontally toward head.
                        tail.x = (tdx > 0) ? tail.x - 1 : tail.x + 1;
                    } else if (tdy != 0) {
                        // Move the tail one space vertically toward head.
                        tail.y = (tdy > 0) ? tail.y - 1 : tail.y + 1;
                    }
                }

                // Mark the tail's visit.
                Integer visits = tailVisits.get(tail.x, tail.y);
                visits = (visits == null) ? 1 : visits + 1;
                tailVisits.put(tail.x, tail.y, visits);
            }
        }

        // The number of cells in the table is the answer for part 1.
        System.out.println("Num Visited Positions = " + tailVisits.cellSet().size());
    }

    static class Knot {
        int x;
        int y;
    }
}
