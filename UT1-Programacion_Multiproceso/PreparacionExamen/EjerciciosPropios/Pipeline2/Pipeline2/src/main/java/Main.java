/*
Lanzar 2 procesos en modo pipeline:
1.- "CMD", "/C", "DIR", "/W"
2.- "SORT", "/r"

Pregunta al usuario cual debe ser la ruta sobre la que aplicar estos comandos.
El último proceso debe heredar la salida de error del proceso padre.
 */
void main() {
    try {
        startPipeLineProcessBuilder();
    } catch (IOException e) {
        System.err.println("Error lanzando procesos: " + e.getMessage());
    }
}

private void startPipeLineProcessBuilder() throws IOException {
    List<ProcessBuilder> processBuilderList = new ArrayList<>();
    ProcessBuilder dirProcessBuilder = new ProcessBuilder("CMD", "/C", "DIR", "/W");
    ProcessBuilder sortProcessBuilder = new ProcessBuilder("SORT", "/r");

    dirProcessBuilder.directory(Path.of(workingDirectory()).toFile());

    sortProcessBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

    processBuilderList.add(dirProcessBuilder);
    processBuilderList.add(sortProcessBuilder);

    ProcessBuilder.startPipeline(processBuilderList);
}

private String workingDirectory() {
    return IO.readln("Introduce el directorio de trabajo.");
}