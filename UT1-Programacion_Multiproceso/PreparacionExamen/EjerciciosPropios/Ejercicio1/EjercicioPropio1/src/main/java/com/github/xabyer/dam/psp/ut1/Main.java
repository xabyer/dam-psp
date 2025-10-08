package com.github.xabyer.dam.psp.ut1;

/*
Modifica el ejemplo 2 del libro de PSP de Garceta para que haga lo siguiente:

-Cada línea debe estar númerada.
-Cada 20 líneas debe aparecer un mensaje con "Pulse enter para continuar." Y el flujo debe
detenerse hasta que el usuario pulse Enter.
-Se debe pedir la ruta donde aplicar Dir al usuario.
 */

import java.io.IOException;
import java.io.InputStream;

public class Main {
    static void main() {
        var workingPathStr = requestWorkingPath();
        try {
            launchDirCommand(workingPathStr);
        } catch (IOException e) {
            e.printStackTrace(System.err);
        } catch (InterruptedException _) {
        }
    }

    private static void launchDirCommand(String workingPathStr) throws IOException, InterruptedException {

        Process dirProcess = new ProcessBuilder("CMD", "/C", "DIR", workingPathStr).start();

        InputStream dirProcessInputStream = dirProcess.getInputStream();

        // Mostramos en pantalla caracter a caracter.
        int character;
        int line = 1;
        boolean esInicioLinea = true;
        while ((character = dirProcessInputStream.read()) != -1) {

            if (esInicioLinea) {
                System.out.print("Line " + line + ": ");
                esInicioLinea = false;
            }
            System.out.print((char) character);

            if(isStartLine((char) character)){
                ++line;
                esInicioLinea = true;
            }

            /*if(line % 20 == 0) {
                IO.readln("Puslse Intro para continuar");
            }*/
            //Nota: Carácter a carácter toma el intro como salida y se rompe el flujo del código.
        }
        dirProcessInputStream.close();

        // COMPROBACION DE ERROR - 0 bien - 1 mal
        int exitValue;
        exitValue = dirProcess.waitFor();
        System.out.println("Valor de salida: " + exitValue);
    }

    private static boolean isStartLine(char character) {
        return character == '\n';
    }

    private static String requestWorkingPath() {
        return IO.readln("Introduce la ruta donde aplicar el comando DIR: ");
    }
}
