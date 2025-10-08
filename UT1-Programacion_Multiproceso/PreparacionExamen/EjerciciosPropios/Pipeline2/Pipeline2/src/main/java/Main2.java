/*
Hacer el mismo ejercicio que en la clase Main, pero esta vez recreando el proceso mediante streams.
Es decir, la salida del primer proceso debe ser la entrada del siguiente. La salida final debe seguir
heredandose del proceso padre.
 */

void main() {
    try {
        startPipeLineProcessBuilder();
    } catch (IOException e) {
        e.printStackTrace(System.err);
    }
}

private void startPipeLineProcessBuilder() throws IOException {
    final String CHARSET = "UTF-8";

    ProcessBuilder dirProcessBuilder = new ProcessBuilder("CMD", "/C", "DIR", "/W");
    ProcessBuilder sortProcessBuilder = new ProcessBuilder("SORT", "/r");

    dirProcessBuilder.directory(Path.of(workingDirectory()).toFile());

    sortProcessBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);

    Process dirProcess = dirProcessBuilder.start();
    Process sortProcess = sortProcessBuilder.start();

    // 1. Redirigir la salida del proceso dir a la entrada del proceso Sort:

    try(
        BufferedReader mainInputStream = new BufferedReader(new InputStreamReader(dirProcess.getInputStream(), CHARSET));
        BufferedWriter mainOutputStream = new BufferedWriter(new OutputStreamWriter(sortProcess.getOutputStream(), CHARSET))
    ){
        String line;
        while ( (line = mainInputStream.readLine()) != null) {
            mainOutputStream.write(line);
            mainOutputStream.newLine();
        }
    }


}

private String workingDirectory() {
    return IO.readln("Introduce el directorio de trabajo: ");
}
