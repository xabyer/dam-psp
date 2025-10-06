package com.github.xabyer.dam.psp.ut1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Main {
    static void main() {
        Path workingPath = requestPath();

        File errorOutputFile = new File("errorOutput.txt");

        try (Stream<Path> directoryList = Files.list(workingPath).filter(Files::isDirectory)) {
            AtomicInteger fileNameIterator = new AtomicInteger();
            directoryList
                    .forEach(currentPath -> {
                        fileNameIterator.getAndIncrement();

                        File standardOuputFile = new File("standardOuput" + fileNameIterator.get() + ".txt");

                        ProcessBuilder processBuilder = new ProcessBuilder(
                                "cmd", "/c", "tree", currentPath.toString()
                        );

                        processBuilder.redirectOutput(standardOuputFile);
                        processBuilder.redirectError(ProcessBuilder.Redirect.appendTo(errorOutputFile));
                        try {
                            processBuilder.start();
                        } catch (IOException e) {
                            System.err.println("Ha pasado algo con la ruta: " + e.getMessage());
                        }
                    });
        }
        catch (IOException e) {
            System.err.println("Ha pasado algo con la ruta: " + e.getMessage());
        }


    }

    private static Path requestPath() {
        return Path.of(IO.readln("Introduce el directorio de trabajo: "));
    }
}
