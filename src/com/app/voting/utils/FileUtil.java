package com.app.voting.utils;


import java.io.*;

import java.util.*;


public class FileUtil {


    public static void writeLinesToFile(String filename, List<String> lines) throws IOException {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {

            for (String line : lines) {

                writer.write(line);

                writer.newLine();

            }

        }

    }


    public static List<String> readLinesFromFile(String filename) throws IOException {

        List<String> lines = new ArrayList<>();

        File file = new File(filename);

        if (!file.exists()) return lines;


        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            String line;

            while((line = reader.readLine()) != null) {

                lines.add(line.trim());

            }

        }


        return lines;

    }

}