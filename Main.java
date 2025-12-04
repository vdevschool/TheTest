// Names: Vivaan and Samuel, Date: 12/4/2025
/*
    High-performance multithreaded version using ExecutorService, Callable, and Futures.
    Runs faster than the single-threaded solution by:
    1) Using raw byte arrays for fast IO
    2) Uppercasing bytes in place (very efficient)
    3) Processing each file in parallel using a thread pool
    4) Writing the modified bytes back to the file
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        ExecutorService executor = Executors.newFixedThreadPool(2);

        List<Callable<Void>> tasks = Arrays.asList(
                () -> { fastProcessFile("gatsby.txt"); return null; },
                () -> { fastProcessFile("mobydick.txt"); return null; }
        );

        try {
            List<Future<Void>> futures = executor.invokeAll(tasks);
            for (Future<Void> f : futures) f.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("TOTAL TIME (FAST MULTITHREAD): " + (endTime - startTime) + " ms");
    }

    private static void fastProcessFile(String fileName) {
        try {
            Path path = Path.of(fileName);

            byte[] bytes = Files.readAllBytes(path);

            for (int i = 0; i < bytes.length; i++) {
                int b = bytes[i];
                if (b >= 'a' && b <= 'z') {
                    bytes[i] = (byte) (b - 32);
                }
            }

            Files.write(path, bytes, StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Finished: " + fileName);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
