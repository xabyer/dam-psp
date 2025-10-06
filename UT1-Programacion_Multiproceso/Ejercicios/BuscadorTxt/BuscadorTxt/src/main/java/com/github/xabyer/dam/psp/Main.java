package com.github.xabyer.dam.psp;

import java.io.*;

/*
Elabora un programa Java que lance en un proceso independiente la búsqueda de los archivos ".txt" que haya dentro de
una carpeta concreta. Para hacer la búsqueda, utiliza el comando "where" de Windows con una sintaxis similar a estas:

where /r c:\ruta *.txt

where c:\ruta:*.txt

(Cambia c:\ruta por la que quieras usar; la primera versión busca recursivamente, y la segunda no.)

Este comando devolverá los nombres de los ficheros encontrados, uno por línea. Procesa dichas líneas mientras se
generan, lanzando un bloc de notas para cada una, de forma que todos los ficheros encontrados por "where" se vayan
abriendo en blocs de notas respectivos conforme se vayan encontrando.

Por último, el programa principal deberá esperar a que todos los blocs de notas lanzados se cierren antes de salir.
 */
public class Main {
    static void main() {

        menu();

    }

    private static void menu(){
        IO.println("1. Buscar archivos txt en la ruta especificada recursivamente.");
        IO.println("2. Buscar archivos txt solo en la ruta especificada.");
        doMenuOperation();
    }

    @SuppressWarnings("unused")
    private static void menuRecursivo() {
        String opcionMenu = "0";

        while (!opcionMenu.equals("3")) {

            IO.println("1. Buscar archivos txt en la ruta especificada recursivamente.");
            IO.println("2. Buscar archivos txt solo en la ruta especificada.");
            IO.println("3. Salir.");

            opcionMenu = doMenuOperation();
        }

    }

    private static String doMenuOperation() {
        var opcionMenu = IO.readln("Introduce un número para seleccionar la opción de menú que deseas: ");

        switch (opcionMenu) {
            case "1" -> txtLauncherConfig(true);
            case "2" -> txtLauncherConfig(false);
            case "3" -> {
                IO.println("Hasta la próxima.");
                opcionMenu = "3";
            }
            default -> IO.println("La opción de menú " + opcionMenu + " seleccionada no es válida.");
        }
        return opcionMenu;
    }

    private static void txtLauncherConfig(boolean isRecursive) {
        var path = requestPath();
        ProcessBuilder txtProcessBuilder = new ProcessBuilder();
        if (path.isEmpty()) {
            IO.println("Debe introducir un directorio de búsqueda de archivos .txt");
            return;

        } else if (isRecursive) {
            IO.println("Recursive");
            txtProcessBuilder.command("cmd", "/C", "where", "/r", path, "*.txt");
        } else {

            IO.println("Not Recursive.");
            txtProcessBuilder.command("cmd", "/C", "where", path + ":*.txt");
        }
        txtLauncher(txtProcessBuilder);

    }

    private static void txtLauncher(ProcessBuilder txtProcessBuilder) {

        try {
            Process txtLauncherProcess = txtProcessBuilder.start();

            try(
                InputStream cmdStdOutput = txtLauncherProcess.getInputStream();
                BufferedReader cmdBufferedStdOutput = new BufferedReader(new InputStreamReader(cmdStdOutput))

            ){

                ProcessBuilder notePadProcessBuilder = new ProcessBuilder();
                Process notepadProcess = null;
                String line;

                while ((line = cmdBufferedStdOutput.readLine()) != null) {
                    IO.println(line);
                    notePadProcessBuilder.command("notepad.exe", line);
                    notepadProcess = notePadProcessBuilder.start();
                }
                if (notepadProcess != null)
                    notepadProcess.waitFor();
            }

        } catch (IOException e) {
            System.err.println("Error al abrir el archivo: " + e.getMessage());

        } catch (InterruptedException _) {
        }
    }

    private static String requestPath() {
        return IO.readln("Introduce la ruta donde buscar archivos txt: ");
    }
}
