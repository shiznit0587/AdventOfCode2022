package org.shiznit.aoc22.day5;

import com.google.common.base.Splitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day5 {

    public Day5() throws IOException {

        System.out.println("Running Day 5 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day5/input.txt"));

        List<Deque<Character>> stacks1 = new ArrayList<>();
        List<Deque<Character>> stacks2 = new ArrayList<>();
        List<Instruction> instructions = new ArrayList<>();

        for (String line : lines) {
            if (line.contains("[")) {
                // This is a stack line.
                int cursor = line.indexOf('[');
                while (cursor != -1) {
                    char crate = line.charAt(cursor + 1);
                    int stackIdx = cursor / 4;

                    while (stacks1.size() <= stackIdx) {
                        stacks1.add(new ArrayDeque<>());
                    }

                    stacks1.get(stackIdx).add(crate);
                    cursor = line.indexOf('[', cursor + 1);
                }
            }
            else if (line.contains("move")) {
                List<String> parts = Splitter.on(" ").splitToList(line);
                Instruction instruction = new Instruction();
                instruction.move = Integer.parseInt(parts.get(1));
                instruction.from = Integer.parseInt(parts.get(3)) - 1;
                instruction.to = Integer.parseInt(parts.get(5)) - 1;
                instructions.add(instruction);
            }
        }

        for (Deque<Character> stack : stacks1) {
            stacks2.add(new ArrayDeque<>(stack));
        }

        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.move; ++i) {
                char crate = stacks1.get(instruction.from).pop();
                stacks1.get(instruction.to).push(crate);
            }
        }

        String stackTops = stacks1.stream()
                .map(s -> "" + s.pop())
                .collect(Collectors.joining());

        System.out.println("Top of Stacks = " + stackTops);

        System.out.println("Running Day 5 - Part 2");

        for (Instruction instruction : instructions) {
            Deque<Character> temp = new ArrayDeque<>();
            for (int i = 0; i < instruction.move; ++i) {
                char crate = stacks2.get(instruction.from).pop();
                temp.push(crate);
            }
            while (!temp.isEmpty()) {
                stacks2.get(instruction.to).push(temp.pop());
            }
        }

        stackTops = stacks2.stream()
                .map(s -> "" + s.pop())
                .collect(Collectors.joining());

        System.out.println("Top of Stacks = " + stackTops);
    }

    static class Instruction {
        int move;
        int from;
        int to;
    }
}
