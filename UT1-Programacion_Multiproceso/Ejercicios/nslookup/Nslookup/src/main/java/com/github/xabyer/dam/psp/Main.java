package com.github.xabyer.dam.psp;

import java.io.*;
//import java.nio.file.Files;
//import java.nio.file.Path;

/*
Ejercicio nslookup:

Ejecutar el proceso "nslookup", con las siguientes redirecciones:

    La salida estándar irá a un fichero, respetando el contenido que éste pueda tener ya.
    La salida de error se heredará del proceso padre.
    La entrada estándar será un fichero de texto en el que aparecerán las siguientes líneas:

www.google.es
www.ieschirinos.com
exit
 */
public class Main {
    static void main() {

        try {
            // Write the file with the text that will be the input to the child process using main (parent process).
            File standardInputFile = writeTextToFile();

            // Create files where child process should write
/*
            Path filePath = Path.of("standardOutput.txt");
            if(Files.notExists(filePath))
                Files.createFile(filePath);

            File standardOuputFile = filePath.toFile();*/

            // Nota: Al final lo he creado "al vuelo" en el redirect.

            // Create processChild (nslookup)
            createNslookupProcess(standardInputFile/*, standardOuputFile*/);


        }
        catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace(System.err);
        } catch (InterruptedException ignore) {
        }

    }

    private static void createNslookupProcess(File standardInputFile/*, File standardOuputFile*/) throws IOException, InterruptedException {
        ProcessBuilder nslookupProcessBuilder = new ProcessBuilder("nslookup");
        nslookupProcessBuilder.redirectInput(standardInputFile);
        nslookupProcessBuilder.redirectOutput(ProcessBuilder.Redirect.appendTo(new File("standardOutput.txt")));
        nslookupProcessBuilder.redirectError(ProcessBuilder.Redirect.INHERIT);

        Process nslookupProcess = nslookupProcessBuilder.start();
        nslookupProcess.waitFor();
        System.out.println("Proceso terminado");
    }

    private static File writeTextToFile() throws IOException {
        File textFile = new File("text.txt");
        PrintWriter textToWrite = new PrintWriter(new BufferedWriter(new FileWriter(textFile)), true);

        textToWrite.println("www.google.es");
        textToWrite.println("www.ieschirinos.com");
        textToWrite.println("exit");

        return textFile;
    }
}
