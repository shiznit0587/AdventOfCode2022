package org.shiznit.aoc22.day10;

import com.google.common.primitives.Booleans;

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

        int x = 1, cycle = 1, cursor = -1, strength = 0;
        boolean[][] crt = new boolean[6][40];

        while (cycle <= 240) {
            Instruction instr = instructions.get(++cursor);

            int end = cycle + instr.cycles;
            for (; cycle < end; ++cycle) {
                if (cycle % 40 == 20) {
                    strength += cycle * x;
                }

                int row = (cycle - 1) / 40;
                int pixel = (cycle - 1) % 40;
                crt[row][pixel] = Math.abs(pixel - x) <= 1;
            }

            x += instr.dx;
        }

        System.out.println("Sum Signal Strengths = " + strength);

        System.out.println("Running Day 10 - Part 2");

        System.out.println("CRT = ");
        for (boolean[] booleans : crt) {
            System.out.println(Booleans.asList(booleans).stream().map(b -> b ? "#" : " ").collect(Collectors.joining()));
        }
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
