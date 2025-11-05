package threadperformance;

import java.io.*;

public class BlockingFileIOExample {
    public static void main(String[] args) {
        String inputFile = "d:/input.txt";
        String outputFile = "d:/output_blocking.txt";

        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            // Blocking call — waits until bytes are read
            while ((bytesRead = fis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead); // Blocking write
            }

            System.out.println("File copied using Blocking I/O");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
/*
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.file.*;

public class NonBlockingFileIOExample {
    public static void main(String[] args) {
        Path inputPath = Paths.get("d:/input.txt");
        Path outputPath = Paths.get("d:/output_nonblocking.txt");

        try (FileChannel inChannel = FileChannel.open(inputPath, StandardOpenOption.READ);
             FileChannel outChannel = FileChannel.open(outputPath,
                     StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (inChannel.read(buffer) > 0) { // Non-blocking read possible
                buffer.flip(); // Switch buffer to read mode
                outChannel.write(buffer); // Non-blocking write
                buffer.clear(); // Prepare for next read
            }

            System.out.println("File copied using Non-blocking (NIO) I/O");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

*/
