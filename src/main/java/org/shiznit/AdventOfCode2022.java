package org.shiznit;

public class AdventOfCode2022 {
    public static void main(String[] args) {
        System.out.println("\n🎅🎅🎅🎅🎅 ADVENT OF CODE 2022 🎅🎅🎅🎅🎅\n");

        for (int i = 1; i <= 25; ++i) {
            try {
                Class<?> day = Class.forName("org.shiznit.aoc22.day" + i + ".Day" + i);
                long time = System.currentTimeMillis();
                day.getConstructor().newInstance();
                System.out.printf("Elapsed = %d ms\n\n", System.currentTimeMillis() - time);
            } catch (ReflectiveOperationException e) {
                // probably doesn't exist yet.
            }
        }
    }
}
