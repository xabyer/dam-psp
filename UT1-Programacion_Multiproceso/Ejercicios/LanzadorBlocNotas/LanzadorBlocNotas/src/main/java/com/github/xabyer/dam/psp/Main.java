package com.github.xabyer.dam.psp;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    static void main() {
        //1. Pedir directorio de trabajo:
        var path = requestPath();

        //2. Comprobar si es un directorio antes de lanzar el proceso.
        if (isADirectory(path))
            lanzadorBlockNotas(path);
    }

    private static void lanzadorBlockNotas(Path path) {
        ProcessBuilder lanzarBlocNotas = new ProcessBuilder();
        lanzarBlocNotas.directory(new File(path.toString()));
        Process notepadProccess = null;
        try (var listDirectory = Files.list(path).filter(Files::isRegularFile)) {

            for(var currentpath : listDirectory.toList()) {

                        var currentExtensionFile = getExtensionFile(currentpath);

                        if (currentExtensionFile.equals("txt")) {
                            try {
                                lanzarBlocNotas.command("notepad.exe", currentpath.getFileName().toString());
                                notepadProccess = lanzarBlocNotas.start();

                            } catch (IOException e) {
                                System.err.println("Error al lanzar el bloc de notas: " + e.getMessage());

                            }  catch (Exception e) {
                                System.err.println("Error inesperado: " + e.getMessage());
                            }
                        }
            }
            if(notepadProccess != null)
                notepadProccess.waitFor();

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (InterruptedException ignore) {

        }
    }

    private static String getExtensionFile(Path path) {
        String extensionFile;

        var fileName = path.getFileName();
        var fileLastDot = fileName.toString().lastIndexOf(".");
        var splitFile = fileName.toString().split("\\.", fileLastDot);
        extensionFile = splitFile[1];

        IO.println(splitFile[0]);
        IO.println("Dot position: " + fileLastDot);
        IO.println("Extension file:" + extensionFile);

        return extensionFile;
    }

    private static Path requestPath() {
        return Path.of(IO.readln("Introduce el directorio donde buscar archivos txt: "));
    }

    private static Boolean isADirectory(Path path) {
        if (Files.isDirectory(path)) {

            return true;
        }

        System.out.println("La ruta introducida no es un directorio.");
        return false;
    }
}
