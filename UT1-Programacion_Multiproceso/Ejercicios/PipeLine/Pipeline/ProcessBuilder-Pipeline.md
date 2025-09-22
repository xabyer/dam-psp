# Las 3 posibles formas de lanzar comandos en pipeline sin recurrir a startPipeline:

1. Conexion manual entre streams conectar salida de uno a entrada de otro (opcionalmente error si lo piden)
2. Usar ficheros como puente con redirect, preferiblemente archivos temporales que borrar.
3. Se puede lanzar 1 único proceso con los comandos en tuberia.

-Creamos 1 lista y:
````java
List<String> comandos = new ArrayList<>();
comandos.add("cmd.exe");
comandos.add("/c");
comandos.add("set | findstr.exe USER | sort");
processBuilder.command(comandos);
````
Nota: Es importante que tras iniciar el cmd la lista de comandos vaya junta para poder lanzarlos en tubería.

# Obviamente la forma más sencilla de hacerlo es lanzar el método startPipeline. 

- Ejemplo en:
  [stackoverflow](https://stackoverflow.com/questions/3776195/using-java-processbuilder-to-execute-a-piped-command)


# Otros enlaces con ejemplos interesantes sobre ProcessBuilder:

- [geeksforgeeks](https://www.geeksforgeeks.org/java/java-lang-processbuilder-class-java/)
- [Mkyong](https://mkyong.com/java/java-processbuilder-examples/)
- [zetcode](https://zetcode.com/java/lang-processbuilder/)
- [baeldung](https://www.baeldung.com/java-lang-processbuilder-api)