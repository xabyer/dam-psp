package com.github.xabyer.dam.psp;

import com.github.xabyer.dam.psp.notepad.NotepadLauncher;

import java.io.IOException;
import java.util.List;

public class Main2 {
    static void main() {
        NotepadLauncher notepadLauncher = new NotepadLauncher();

        String workingPath = notepadLauncher.getWorkingPath();

        try {
            List<Process> notepadProcessList = notepadLauncher.launchNotePad(workingPath);
            notepadLauncher.processWait(notepadProcessList);

        } catch (IOException e) {
            System.err.println("Error de entrada/salida: " + e.getMessage());

        } catch (InterruptedException ignore) {

        } catch (Exception e) {
            System.err.println("Error imprevisto: " + e.getMessage());
        }
    }
}
