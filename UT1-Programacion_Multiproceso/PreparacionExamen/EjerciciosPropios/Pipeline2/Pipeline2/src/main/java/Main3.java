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

/*
Nota extra: Crear una función que reciba un Proceso de salida, un proceso de entrada, el charset y realice la redirección
Utilizarlo en el ejercicio del pipeline con 3 procesos.
 */

private void startPipeLineProcessBuilder() throws IOException {
    String osName = System.getProperty("os.name");
    //IO.println(!osName.contains("Windows 11")); //--> True if not Win11; Win 11 --> UTF8
    Charset charset;


    ProcessBuilder dirProcessBuilder = new ProcessBuilder("CMD", "/C", "DIR", "/W");
    ProcessBuilder sortProcessBuilder = new ProcessBuilder("SORT", "/r");

    dirProcessBuilder.directory(Path.of(workingDirectory()).toFile());

    if (!osName.contains("Windows 11")) {
        charset = Charset.forName("Cp850");
    } else {
        charset = StandardCharsets.UTF_8;
        sortProcessBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
    }

    Process dirProcess = dirProcessBuilder.start();
    Process sortProcess = sortProcessBuilder.start();

    // 1. Redirigir la salida del proceso dir a la entrada del proceso Sort:

    try (
            BufferedReader mainInputStream = new BufferedReader(new InputStreamReader(dirProcess.getInputStream(), charset));
            BufferedWriter mainOutputStream = new BufferedWriter(new OutputStreamWriter(sortProcess.getOutputStream(), charset))
    ) {
        String line;
        while ((line = mainInputStream.readLine()) != null) {
            mainOutputStream.write(line);
            mainOutputStream.newLine();
        }
    }

    // Recodificar la salida estandar para que lea correctamente la codificación de caracteres en caso no ser Win 11:
    // Así IJ leerá correctamente los carácteres de win10.
    if (!osName.contains("Windows 11")) {
        try (
                BufferedReader mainInputStream = new BufferedReader(new InputStreamReader(sortProcess.getInputStream(), charset));
                BufferedWriter mainOutputStream = new BufferedWriter(new OutputStreamWriter(System.out, StandardCharsets.UTF_8))
        ) {
            String line;
            while ((line = mainInputStream.readLine()) != null) {
                mainOutputStream.write(line);
                mainOutputStream.newLine();

            }
        }
    }

}

private String workingDirectory() {
    return IO.readln("Introduce el directorio de trabajo: ");
}
