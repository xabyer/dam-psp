/*
Realiza una versión del anterior programa donde el pipeline se realice mediente streams entre procesos.
 */

void main() {
    String osName = System.getProperty("os.name");
    Charset charset;


    ProcessBuilder sortProcessBuilder = new ProcessBuilder("sort");

    if (!osName.contains("Windows 11")) {
        charset = Charset.forName("Cp850"); // Charset Win 10, 7, vista --> cmd
        // Hay que hacer la redirección de salida con streams para cambiar el charset a UTF-8
    } else {
        charset = StandardCharsets.UTF_8; // Charset Win 11
        sortProcessBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT); //El proceso hereda la salida estandar del main.
    }

    //Iniciar los procesos:
    try {
        Process setProcess = new ProcessBuilder("cmd", "/c", "set").start();
        Process findStrProcess = new ProcessBuilder("findstr", "USER").start();
        Process sortProcess = sortProcessBuilder.start();

        // Redirigir las salidas a las entradas del siguiente proceso.
        redirectOutputToInput(setProcess, findStrProcess, charset);
        redirectOutputToInput(findStrProcess, sortProcess, charset);

        //Heredar salida estándar manualmente en el caso de no tratarse de win 11 y tener diferente charset cmd e IJ.
        if(!osName.contains("Windows 11"))
            redirectToStandardOutput(findStrProcess, charset);

    } catch (IOException e) {
        System.err.println("Error iniciando el proceso: " + e.getMessage());
        e.printStackTrace(System.err);
    }
}


private void redirectOutputToInput(
        Process outputStreamProcess,
        Process inputStreamProcess,
        Charset charset
) throws IOException {
    try (
            BufferedReader mainInputStream = new BufferedReader(
                    new InputStreamReader(outputStreamProcess.getInputStream(), charset)
            );
            BufferedWriter mainOutputStream = new BufferedWriter(
                    new OutputStreamWriter(inputStreamProcess.getOutputStream(), charset)
            )
    ) {
        String line;
        while ( (line = mainInputStream.readLine()) != null) {
            mainOutputStream.write(line);
            mainOutputStream.newLine();
        }
    }
}


private void redirectToStandardOutput(
        Process outputStreamProcess,
        Charset inputCharset
) throws IOException {
    try (
            BufferedReader mainInputStream = new BufferedReader(
                    new InputStreamReader(outputStreamProcess.getInputStream(), inputCharset)
            );
            BufferedWriter mainOutputStream = new BufferedWriter(
                    new OutputStreamWriter(System.out, StandardCharsets.UTF_8)
            )
    ) {
        String line;
        while ( (line = mainInputStream.readLine()) != null) {
            mainOutputStream.write(line);
            mainOutputStream.newLine();
        }
    }
}