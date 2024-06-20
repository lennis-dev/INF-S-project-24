package dev.lennis.school.notes;

import java.io.File;

public class Main {
  static File lockFile = new File("notes.lock");

  public static void main(String[] args) {
    // Create a lock file
    if (lockFile.exists()) {
      lockFile = null;
      System.out.println("Another instance of the program is already running");
      System.exit(1);
    }
    try {
      lockFile.createNewFile();
    } catch (Exception e) {
      System.out.println("Error creating lock file");
      System.exit(1);
    }

    Gui.main();
  }

  static {
    // Delete the lock file on shutdown
    Runtime.getRuntime()
        .addShutdownHook(
            new Thread(
                () -> {
                  if (Main.lockFile == null) return;

                  if (Main.lockFile.exists()) {
                    Main.lockFile.delete();
                  }
                }));
  }
}
