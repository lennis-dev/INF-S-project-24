package dev.lennis.school.notes;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        // Create a lock file

        File lockFile = new File("notes.lock");
        if (lockFile.exists()) {
            System.out.println("Another instance of the program is already running");
            System.exit(1);
        }
        try {
            lockFile.createNewFile();
        } catch (Exception e) {
            System.out.println("Error creating lock file");
            System.exit(1);
        }

        // Launch the GUI

    }

    static {
        // Delete the lock file on shutdown
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            File lockFile = new File("notes.lock");
            if (lockFile.exists()) {
                lockFile.delete();
            }
        }));
    }
}
