//Names: Vivaan and Samuel, Date: 12/4/2025

/*
    Single-threaded solution that:
    1) Reads "gatsby.txt" and "mobydick.txt"
    2) Capitalizes every character (slow because many new strings are created)
    3) Writes the modified text back to each file
    4) Measures and prints total execution time
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        processFile("gatsby.txt");
        processFile("mobydick.txt");

        long endTime = System.currentTimeMillis();
        System.out.println("Total time: " + (endTime - startTime) + " ms");
    }

    private static void processFile(String fileName) {
        try {
            String content = Files.readString(Path.of(fileName));

            StringBuilder result = new StringBuilder(content.length());
            for (int i = 0; i < content.length(); i++) {
                result.append(Character.toUpperCase(content.charAt(i)));
            }

            Files.writeString(
                    Path.of(fileName),
                    result.toString(),
                    StandardOpenOption.TRUNCATE_EXISTING
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
