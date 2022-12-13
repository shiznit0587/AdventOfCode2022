package org.shiznit.aoc22.day10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day10 {

    public Day10() throws IOException {
        System.out.println("Running Day 10 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day10/input.txt"));

        List<Instruction> instructions = lines.stream()
                .map(Instruction::parse)
                .collect(Collectors.toList());

        int x = 1, cycle = 1, cursor = 0, strength = 0;

        while (cycle <= 220) {
            Instruction instr = instructions.get(cursor);

            for (int i = 0; i < instr.cycles; ++i) {
                if ((cycle + i) % 40 == 20) {
                    strength += (cycle + i) * x;
                }
            }

            cycle += instr.cycles;
            x += instr.dx;
            cursor = (cursor + 1) % instructions.size();
        }

        System.out.println("Sum Signal Strengths = " + strength);
    }

    static class Instruction {
        final int cycles;
        final int dx;

        Instruction(int cycles, int dx) {
            this.cycles = cycles;
            this.dx = dx;
        }

        static Instruction parse(String line) {
            if (line.equals("noop")) {
                return new Instruction(1, 0);
            }
            return new Instruction(2, Integer.parseInt(line.substring(5)));
        }
    }
}
