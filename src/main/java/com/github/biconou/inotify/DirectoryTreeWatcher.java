package com.github.biconou.inotify;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by remi on 28/06/16.
 */
public class DirectoryTreeWatcher {

    private String pathToWatch = null;

    private boolean stopWatch = false;

    List<InotifywaitEventListener> registeredListeners = new ArrayList<>();

    public DirectoryTreeWatcher(String pathToWatch) {
        this.pathToWatch = pathToWatch;
    }

    public DirectoryTreeWatcher addEventListener(InotifywaitEventListener eventListener) {
      registeredListeners.add(eventListener);
      return this;
    }

    public void startWatch() {

        Thread inotifywaitThread = new Thread(() -> {

            File dirToWatch = new File(pathToWatch);
            if (!dirToWatch.exists()) {
                throw new RuntimeException("Directory ["+pathToWatch+"] does not exist.");
            }
            String[] inotifywaitCmd = new String[] {
                    "inotifywait",  // command name
                    "--recursive",  // Watch directories recursively.
                    "--monitor",    // Keep listening for events forever.  Without
                    // this option, inotifywait will exit after one
                    // event is received.
                    "--format",     // Format
                    "%w/*/%:e/*/%f",
                    pathToWatch
            };

            ProcessBuilder pb = new ProcessBuilder(inotifywaitCmd);
            final Process p;
            try {
                p = pb.start();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

            Thread stopThread = new Thread(() -> {
                while (stopWatch == false) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //
                    }
                }
                p.destroy();
            });
            stopThread.start();

            String line = null;
            try {
                while ((line = br.readLine()) != null) {
                    InotifywaitEvent newEvent = InotifywaitEvent.parse(line);
                    registeredListeners.forEach(listener -> listener.doEvent(newEvent));
                    //System.out.println(line);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        inotifywaitThread.start();
    }

    public void stopWatch() {
        this.stopWatch = true;
    }

}
