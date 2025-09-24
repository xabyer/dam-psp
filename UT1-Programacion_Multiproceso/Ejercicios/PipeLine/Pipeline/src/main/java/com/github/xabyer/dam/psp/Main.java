package com.github.xabyer.dam.psp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
Lanza tres procesos a la vez en modo "pipeline", es decir, cada uno de ellos (salvo el primero) debe tomar como entrada la salida del proceso anterior. Los procesos son:

    "cmd", con los argumentos "/c" y "set"
    "findstr", con el argumento "USER"
    "sort", sin argumentos

El último de ellos debe heredar la salida estándar de su proceso padre.
 */
//@SuppressWarnings("SpellCheckingInspection")
public class Main {
    /*
        Nota: Leer el ProcessBuilder-Pipeline.md
     */
    static void main() {
        //1 Lista ProcessBuilder y los processBuilder a añadir:
        List<ProcessBuilder> processBuilderList = new ArrayList<>();
        ProcessBuilder cmdProcessBuilder = new ProcessBuilder("cmd", "/c", "set");
        ProcessBuilder findStrProcessBuilder = new ProcessBuilder("findstr", "USER");
        ProcessBuilder sortProcessBuilder = new ProcessBuilder("sort");

        // 2. Redirección de la salida del último proceso
        sortProcessBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

        // 3. Añadir procesos a la lista:
        processBuilderList.add(cmdProcessBuilder);
        processBuilderList.add(findStrProcessBuilder);
        processBuilderList.add(sortProcessBuilder);

        try {
            var processPipeList = ProcessBuilder.startPipeline(processBuilderList);
        } catch (IOException e) {
            System.err.println("Error lanzando procesos: " + e.getMessage());
        }


    }
}
