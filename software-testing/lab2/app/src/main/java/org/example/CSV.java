package org.example;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CSV {
    public static void writeCSV(String filePath, List<FuncRun> funcRuns) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // header
            writer.write("x;y");
            writer.newLine();

            // data
            for (var fRun : funcRuns) {
                writer.write(fRun.x() + ";" + fRun.y());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("can't write to CSV file '" + filePath + "': " + e.getMessage());
        }
    }
}
