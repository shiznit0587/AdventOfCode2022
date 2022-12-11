package org.shiznit.aoc22.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day7 {
    public Day7() throws IOException {
        System.out.println("Running Day 7 - Part 1");

        List<String> lines = Files.readAllLines(Path.of("src/main/java/org/shiznit/aoc22/day7/input.txt"));

        Directory root = new Directory("/");
        Directory currentDir = root;

        List<Directory> allDirs = new ArrayList<>();
        allDirs.add(root);

        for (String line : lines) {
            if (line.charAt(0) == '$') {
                // This is a command

                if (line.startsWith("cd", 2)) {
                    if (line.startsWith("/", 5)) {
                        currentDir = root;
                    }
                    else if (line.startsWith("..", 5)) {
                        currentDir = currentDir.parent;
                    }
                    else {
                        String dirName = line.substring(5);
                        Directory dir = currentDir.childDirs.get(dirName);

                        if (dir == null) {
                            dir = new Directory(dirName);
                            dir.parent = currentDir;
                            currentDir.childDirs.put(dirName, dir);
                            allDirs.add(dir);
                        }

                        currentDir = dir;
                    }
                }
                // else if (line.startsWith("ls", 2)) {
                    // I don't actually need to do anything here.
                //}
            }
            // It's the output from an `ls` command.
            // It's either a file or a directory.
            else if (line.startsWith("dir")) {
                // It's a directory
                String dirName = line.substring(4);
                Directory dir = currentDir.childDirs.get(dirName);

                if (dir == null) {
                    dir = new Directory(dirName);
                    dir.parent = currentDir;
                    currentDir.childDirs.put(dirName, dir);
                    allDirs.add(dir);
                }
            }
            else {
                // It's a file
                String[] parts = line.split(" ");
                int size = Integer.parseInt(parts[0]);
                String fileName = parts[1];
                currentDir.files.put(fileName, new File(fileName, size));
            }
        }

        // Now, starting from the root, calculate all directory sizes.
        computeDirSize(root);

        int sum = allDirs.stream()
                .filter(d -> d.totalSize < 100000)
                .mapToInt(d -> d.totalSize)
                .sum();

        System.out.println("Total Size of eligible dirs = " + sum);

        System.out.println("Running Day 7 - Part 2");

        int totalDiskSpace = 70000000;
        int updateSize = 30000000;
        int unusedSpace = totalDiskSpace - root.totalSize;

        Directory candidate = allDirs.stream()
                .filter(d -> unusedSpace + d.totalSize >= updateSize)
                .min(Comparator.comparingInt(a -> a.totalSize)).get();

        System.out.println("Size of deleted dir = " + candidate.totalSize);
    }

    private void computeDirSize(Directory dir) {
        dir.fileSizeSum = dir.files.values().stream()
                .mapToInt(d -> d.size)
                .sum();

        for (Directory childDir : dir.childDirs.values()) {
            computeDirSize(childDir);
        }

        dir.totalSize = dir.fileSizeSum + dir.childDirs.values().stream()
                .mapToInt(d -> d.totalSize)
                .sum();
    }

    static class Directory {
        final String name;
        Directory parent;
        Map<String, Directory> childDirs = new HashMap<>();
        Map<String, File> files = new HashMap<>();

        int fileSizeSum;
        int totalSize;

        public Directory(String name) {
            this.name = name;
        }
    }

    static class File {
        final String name;
        final int size;

        public File(String name, int size) {
            this.name = name;
            this.size = size;
        }
    }
}
