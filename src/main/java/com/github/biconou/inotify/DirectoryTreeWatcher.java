package com.github.biconou.inotify;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by remi on 28/06/16.
 */
public class DirectoryTreeWatcher {

    String pathToWatch = null;

    public DirectoryTreeWatcher(String pathToWatch) {
        this.pathToWatch = pathToWatch;
    }



    public void startWatch() {

        Thread newThread = new Thread(new Runnable() {
            @Override
            public void run() {

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
                        "--csv",        // Print events in CSV format.
                        pathToWatch
                };

                ProcessBuilder pb = new ProcessBuilder(inotifywaitCmd);
                Process p = null;
                try {
                    p = pb.start();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line = null;
                try {
                    while ((line = br.readLine()) != null) {
                        InotifywaitEvent newEvent = InotifywaitEvent.parse(line);
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        newThread.start();
    }
}
