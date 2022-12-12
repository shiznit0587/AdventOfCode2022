package org.shiznit.aoc22.day8;

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

        int visibleCount = 0;
        for (int i = 0; i < trees.length; ++i) {
            for (int j = 0; j < trees[i].length; ++j) {
                if (isTreeVisible(i, j)) {
                    ++visibleCount;
                }
            }
        }

        System.out.println("Num Visible Trees = " + visibleCount);

        System.out.println("Running Day 8 - Part 2");

        int bestScore = 0;
        // Exclude edges, their scores are all 0.
        for (int i = 1; i < trees.length - 1; ++i) {
            for (int j = 1; j < trees[i].length - 1; ++j) {
                int score = calculateScenicScore(i, j);
                if (score > bestScore) {
                    bestScore = score;
                }
            }
        }

        System.out.println("Best Scenic Score = " + bestScore);
    }

    private boolean isTreeVisible(int x, int y) {
        boolean visibleUp = true, visibleDown = true, visibleLeft = true, visibleRight = true;
        int tree = trees[x][y];

        // Check Up
        for (int i = 0; i < x; ++i) {
            if (trees[i][y] >= tree) {
                visibleUp = false;
                break;
            }
        }

        // Check Down
        for (int i = x + 1; i < trees.length; ++i) {
            if (trees[i][y] >= tree) {
                visibleDown = false;
                break;
            }
        }

        // Check Left
        for (int i = 0; i < y; ++i) {
            if (trees[x][i] >= tree) {
                visibleLeft = false;
                break;
            }
        }

        // Check Right
        for (int i = y + 1; i < trees[x].length; ++i) {
            if (trees[x][i] >= tree) {
                visibleRight = false;
                break;
            }
        }

        return visibleUp || visibleDown || visibleLeft || visibleRight;
    }

    private int calculateScenicScore(int x, int y) {
        int up = 0, down = 0, left = 0, right = 0;
        int tree = trees[x][y];

        // Count Up
        for (int i = x - 1; i >= 0; --i) {
            ++up;
            if (trees[i][y] >= tree) {
                break;
            }
        }

        // Count Down
        for (int i = x + 1; i < trees.length; ++i) {
            ++down;
            if (trees[i][y] >= tree) {
                break;
            }
        }

        // Count Left
        for (int i = y - 1; i >= 0; --i) {
            ++left;
            if (trees[x][i] >= tree) {
                break;
            }
        }

        // Count Right
        for (int i = y + 1; i < trees[x].length; ++i) {
            ++right;
            if (trees[x][i] >= tree) {
                break;
            }
        }

        return up * down * left * right;
    }
}
