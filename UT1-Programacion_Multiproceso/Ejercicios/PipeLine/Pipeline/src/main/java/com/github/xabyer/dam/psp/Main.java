package com.github.xabyer.dam.psp;
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
        ProcessBuilder cmdProcessBuilder = new ProcessBuilder("cmd", "/c", "set");
        ProcessBuilder findStrProcessBuilder = new ProcessBuilder("findstr", "USER");
        ProcessBuilder sortProcessBuilder = new ProcessBuilder("sort");


    }
}
