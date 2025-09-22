package com.github.xabyer.dam.psp.notepad;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NotepadLauncher {

    ProcessBuilder notepadProcessBuilder;
    Process notepadProcess;
    List<Process> notepadProcessList;
    File workingDirectory;

    public NotepadLauncher(){
        notepadProcessBuilder = new ProcessBuilder();
        notepadProcessList = new ArrayList<>();
        notepadProcess = null;
        workingDirectory = null;
    }

    public String getWorkingPath() {
        return IO.readln("Introduce a working path -> ");
    }

    public List<Process> launchNotePad(String path) throws IOException, InterruptedException {
        if(Files.isDirectory(Path.of(path))) {
            //Get file extension
            FilenameFilter getFileExtension = (directory, name) -> name.endsWith(".txt");

            //Set working directory
            workingDirectory = new File(path);
            IO.println(workingDirectory.toString());
            String [] notepadFiles = workingDirectory.list(getFileExtension);
            notepadProcessBuilder.directory(workingDirectory);

            //Launch notepads
            if(notepadFiles != null) {

                for (String notepadFile : notepadFiles) {
                    IO.println(notepadFile);
                    notepadProcessBuilder.command("notepad.exe", notepadFile);

                    notepadProcess = notepadProcessBuilder.start();
                    notepadProcessList.add(notepadProcess);
                }

                processWait(notepadProcessList);
            }

        }
        else {
            IO.println("The path introduced was not a directory.");
        }

        return notepadProcessList;
    }

    public void processWait(List<Process> notepadProcessList) throws InterruptedException {
        if (notepadProcessList.isEmpty()) {
            IO.println("The list of process is empty.");

        }
        else {
            for(var process: notepadProcessList) {
                process.waitFor();
            }
        }
    }
}
