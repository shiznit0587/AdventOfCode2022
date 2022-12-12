package org.shiznit.aoc22.day9;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 {

    List<Motion> motions;

    public Day9() throws IOException {
        System.out.println("Running Day 9 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day9/input.txt"));

        motions = lines.stream()
                .map(Motion::parse)
                .collect(Collectors.toList());

        List<Knot> knots = IntStream.range(0, 2).mapToObj(i -> new Knot()).collect(Collectors.toList());

        System.out.println("Num Visited Positions = " + simulate(knots));

        System.out.println("Running Day 9 - Part 2");

        knots = IntStream.range(0, 10).mapToObj(i -> new Knot()).collect(Collectors.toList());

        System.out.println("Num Visited Positions = " + simulate(knots));
    }

    int simulate(List<Knot> knots) {
        Knot head = knots.get(0);

        Set<Pair<Integer, Integer>> tailVisits = new HashSet<>();
        tailVisits.add(Pair.of(0,0));

        for (Motion motion : motions) {
            int hdx = 0, hdy = 0;
            switch (motion.dir) {
                case RIGHT:
                    hdx = 1;
                    break;
                case LEFT:
                    hdx = -1;
                    break;
                case UP:
                    hdy = 1;
                    break;
                case DOWN:
                    hdy = -1;
                    break;
            }

            // Move each knot in succession.
            for (int i = 0; i < motion.distance; ++i) {
                // Move head.
                head.x += hdx;
                head.y += hdy;

                Knot prev = head;

                for (int j = 1; j < knots.size(); ++ j) {
                    Knot knot = knots.get(j);

                    // Move the knot.
                    int tdx = knot.x - prev.x;
                    int tdy = knot.y - prev.y;

                    // Only move the knot if it's not "touching" the one before it.
                    if (!(Math.abs(tdx) <= 1 && Math.abs(tdy) <= 1)) {
                        if (tdx != 0 && tdy != 0) {
                            // Move the knot one space diagonally toward the one before it.
                            knot.x = (tdx > 0) ? knot.x - 1 : knot.x + 1;
                            knot.y = (tdy > 0) ? knot.y - 1 : knot.y + 1;
                        } else if (tdx != 0) {
                            // Move the knot one space horizontally toward the one before it.
                            knot.x = (tdx > 0) ? knot.x - 1 : knot.x + 1;
                        } else if (tdy != 0) {
                            // Move the knot one space vertically toward the one before it.
                            knot.y = (tdy > 0) ? knot.y - 1 : knot.y + 1;
                        }
                    }

                    prev = knot;
                }

                // Mark the tail's visit.
                tailVisits.add(Pair.of(prev.x, prev.y));
            }
        }

        return tailVisits.size();
    }

    static class Knot {
        int x;
        int y;
    }

    enum Direction {
        RIGHT("R"),
        LEFT("L"),
        UP("U"),
        DOWN("D");

        final String input;

        Direction(String input) {
            this.input = input;
        }

        public static Direction parse(String input) {
            return Arrays.stream(Direction.values())
                    .filter(d -> d.input.equals(input))
                    .findFirst().orElseThrow();
        }
    }

    static class Motion {
        final Direction dir;
        final int distance;

        public static Motion parse(String line) {
            String[] parts = line.split(" ");
            return new Motion(Direction.parse(parts[0]), Integer.parseInt(parts[1]));
        }

        Motion(Direction dir, int distance) {
            this.dir = dir;
            this.distance = distance;
        }
    }
}
