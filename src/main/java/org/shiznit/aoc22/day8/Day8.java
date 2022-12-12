package org.shiznit.aoc22.day8;

import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day8 {

    private final int[][] trees;

    public Day8() throws IOException {
        System.out.println("Running Day 8 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day8/input.txt"));

        trees = new int[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); ++i) {
            trees[i] = lines.get(i).chars()
                    .map(t -> Integer.parseInt(String.valueOf(t - '0')))
                    .toArray();
        }

        int visibleCount = 0, bestScore = 0;
        for (int i = 0; i < trees.length; ++i) {
            for (int j = 0; j < trees[i].length; ++j) {
                Pair<Boolean, Integer> up = exploreFromTree(i, j, -1, 0);
                Pair<Boolean, Integer> down = exploreFromTree(i, j, 1, 0);
                Pair<Boolean, Integer> left = exploreFromTree(i, j, 0, -1);
                Pair<Boolean, Integer> right = exploreFromTree(i, j, 0, 1);

                boolean visible = up.getLeft() || down.getLeft() || left.getLeft() || right.getLeft();
                if (visible) {
                    ++visibleCount;
                }

                int score = up.getRight() * down.getRight() * left.getRight() * right.getRight();
                if (score > bestScore) {
                    bestScore = score;
                }
            }
        }

        System.out.println("Num Visible Trees = " + visibleCount);
        System.out.println("Running Day 8 - Part 2");
        System.out.println("Best Scenic Score = " + bestScore);
    }

    private Pair<Boolean, Integer> exploreFromTree(int x, int y, int dx, int dy) {
        int tree = trees[x][y];
        x += dx;
        y += dy;

        boolean visible = true;
        int seen = 0;
        while (0 <= x && x < trees.length && 0 <= y && y < trees[x].length) {
            ++seen;

            if (trees[x][y] >= tree) {
                visible = false;
                break;
            }

            x += dx;
            y += dy;
        }

        return Pair.of(visible, seen);
    }
}
