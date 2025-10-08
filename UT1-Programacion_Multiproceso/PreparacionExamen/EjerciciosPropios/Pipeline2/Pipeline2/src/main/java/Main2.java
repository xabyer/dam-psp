/*
Hacer el mismo ejercicio que en la clase Main, pero esta vez recreando el proceso mediante streams.
Es decir, la salida del primer proceso debe ser la entrada del siguiente. La salida final debe seguir
heredandose del proceso padre.
 */

void main() {
    startPipeLineProcessBuilder();
}

private void startPipeLineProcessBuilder() {
    ProcessBuilder dirProcessBuilder = new ProcessBuilder("CMD", "/C", "DIR", "/W");
    ProcessBuilder sortProcessBuilder = new ProcessBuilder("SORT", "/r");

    dirProcessBuilder.directory(Path.of(workingDirectory()).toFile());

    sortProcessBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

    // 1. Redirigir la salida del proceso dir a la entrada del proceso Sort:
}

private String workingDirectory() {
    return IO.readln("Introduce el directorio de trabajo");
}
