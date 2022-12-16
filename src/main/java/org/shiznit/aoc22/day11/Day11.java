package org.shiznit.aoc22.day11;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Day11 {

    public Day11() throws IOException {
        System.out.println("Running Day 11 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day11/input.txt"));

        Map<Integer, Monkey> monkeys = Lists.partition(lines, 7).stream()
                .map(this::parseMonkey)
                .collect(Collectors.toMap(m -> m.id, Function.identity()));

        // Copy monkeys for use in part 2
        Map<Integer, Monkey> monkeys2 = monkeys.values().stream()
                .map(Monkey::copy)
                .collect(Collectors.toMap(m -> m.id, Function.identity()));

        simulate(monkeys, 20, item -> item / 3);

        long monkeyBusiness = calcMonkeyBusiness(monkeys.values());

        System.out.println("Monkey Business = " + monkeyBusiness);

        System.out.println("Running Day 11 - Part 2");

        long commonMultiple = monkeys2.values().stream().map(m -> m.testValue).reduce(1, (a, b) -> a * b);
        simulate(monkeys2, 10000, item -> item % commonMultiple);

        monkeyBusiness = calcMonkeyBusiness(monkeys2.values());

        System.out.println("Monkey Business = " + monkeyBusiness);
    }

    private long calcMonkeyBusiness(Collection<Monkey> monkeys) {
        return monkeys.stream().map(m -> m.inspections)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .reduce((a, b) -> a * b).get();
    }

    private void simulate(Map<Integer, Monkey> monkeys, int rounds, Function<Long, Long> divisor) {
        for (int round = 0; round < rounds; ++round) {
            for (int turn = 0; turn < monkeys.size(); ++turn) {
                // process the monkey.
                Monkey monkey = monkeys.get(turn);

                monkey.inspections += monkey.items.size();

                for (int i = 0; i < monkey.items.size(); ++i) {
                    long item = monkey.items.get(i);
                    // Inspect an item
                    item = monkey.operation.apply(item);
                    // feel relief
                    item = divisor.apply(item);
                    // test
                    Monkey target = monkeys.get(monkey.test.apply(item));
                    // throw item
                    target.items.add(item);
                }

                monkey.items = new ArrayList<>();
            }
        }
    }

    private Monkey parseMonkey(List<String> lines) {
        Monkey monkey = new Monkey();

        // ID
        monkey.id = Integer.parseInt(lines.get(0).substring(7, 8));

        // Items
        monkey.items = Splitter.on(",")
                .trimResults()
                .splitToStream(lines.get(1).substring(18))
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // Operation
        List<String> parts = Splitter.on(' ')
                .splitToList(lines.get(2).substring(19));
        if (parts.get(1).equals("*")) {
            if (parts.get(0).equals(parts.get(2))) {
                monkey.operation = old -> old * old;
            }
            else {
                long rhs = Long.parseLong(parts.get(2));
                monkey.operation = old -> old * rhs;
            }
        }
        else {
            long rhs = Long.parseLong(parts.get(2));
            monkey.operation = old -> old + rhs;
        }

        // True
        int testTrueTarget = Integer.parseInt(Splitter.on(' ')
                .trimResults()
                .omitEmptyStrings()
                .splitToList(lines.get(4))
                .get(5));

        // False
        int testFalseTarget = Integer.parseInt(Splitter.on(' ')
                .trimResults()
                .omitEmptyStrings()
                .splitToList(lines.get(5))
                .get(5));

        // Test
        monkey.testValue = Integer.parseInt(Splitter.on(' ')
                .trimResults()
                .omitEmptyStrings()
                .splitToList(lines.get(3))
                .get(3));

        monkey.test = worry -> (worry % monkey.testValue) == 0 ? testTrueTarget : testFalseTarget;

        return monkey;
    }

    static class Monkey {
        int id;
        List<Long> items;
        Function<Long, Long> operation;
        Function<Long, Integer> test;
        int testValue;

        long inspections = 0;

        public Monkey copy() {
            Monkey monkey = new Monkey();
            monkey.id = id;
            monkey.items = Lists.newArrayList(items);
            monkey.operation = operation;
            monkey.test = test;
            monkey.testValue = testValue;
            return monkey;
        }
    }
}
