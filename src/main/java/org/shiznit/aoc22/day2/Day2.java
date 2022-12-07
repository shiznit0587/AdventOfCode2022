package org.shiznit.aoc22.day2;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day2 {
    public Day2() throws IOException {
        System.out.println("Running Day 2 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day2/input.txt"));

        List<List<Shape>> rounds = lines.stream()
                .map(line -> Splitter.on(' ').splitToStream(line).map(Shape::parse).collect(Collectors.toList()))
                .collect(Collectors.toList());

        List<Integer> scores = rounds.stream()
                .map(r -> calcScore(r.get(1), r.get(0)))
                .collect(Collectors.toList());

        int totalScore = scores.stream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Total Score = " + totalScore);

        System.out.println("Running Day 2 - Part 2");

        List<Pair<Shape, Outcome>> part2Rounds = lines.stream()
                .map(line -> {
                    List<String> parts = Splitter.on(' ').splitToList(line);
                    return Pair.of(Shape.parse(parts.get(0)), Outcome.parse(parts.get(1)));
                })
                .collect(Collectors.toList());

        scores = part2Rounds.stream()
                .map(p -> calcScore(p.getRight(), p.getLeft()))
                .collect(Collectors.toList());

        totalScore = scores.stream()
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Total Score = " + totalScore);
    }

    int calcScore(Shape player, Shape opponent) {
        return player.score + outcomeByShape.get(player).get(opponent.ordinal()).score;
    }

    int calcScore(Outcome outcome, Shape opponent) {
        return outcome.score + shapeByOutcome.get(outcome).get(opponent.ordinal()).score;
    }

    Map<Shape, List<Outcome>> outcomeByShape = Map.of(
            Shape.ROCK, List.of(Outcome.DRAW, Outcome.LOSS, Outcome.WIN),
            Shape.PAPER, List.of(Outcome.WIN, Outcome.DRAW, Outcome.LOSS),
            Shape.SCISSORS, List.of(Outcome.LOSS, Outcome.WIN, Outcome.DRAW)
    );

    Map<Outcome, List<Shape>> shapeByOutcome = Map.of(
            Outcome.WIN, List.of(Shape.PAPER, Shape.SCISSORS, Shape.ROCK),
            Outcome.DRAW, List.of(Shape.ROCK, Shape.PAPER, Shape.SCISSORS),
            Outcome.LOSS, List.of(Shape.SCISSORS, Shape.ROCK, Shape.PAPER)
    );

    public enum Outcome {
        WIN(6),
        DRAW(3),
        LOSS(0);

        final int score;

        Outcome(int score) {
            this.score = score;
        }

        static Outcome parse(String code) {
            if (code.equals("X")) {
                return LOSS;
            }
            if (code.equals("Y")) {
                return DRAW;
            }
            return WIN;
        }
    }

    enum Shape {
        ROCK(1),
        PAPER(2),
        SCISSORS(3);

        final int score;

        Shape(int score) {
            this.score = score;
        }

        static Shape parse(String code) {
            if (code.equals("A") || code.equals("X")) {
                return ROCK;
            }
            if (code.equals("B") || code.equals("Y")) {
                return PAPER;
            }
            return SCISSORS;
        }
    }
}
