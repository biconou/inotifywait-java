package com.github.biconou.inotify;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by remi on 28/06/16.
 */
public class DirectoryTreeWatcherTest {

    private String baseDirPath() {
        return DirectoryTreeWatcherTest.class.getResource("/").getPath();
    }

    @Test
    public void simpleTest() {

        // Base directory is the target classes directory
        String dirToWatch = baseDirPath() + "__test_dir";

        // If __test_dir exists delete and create it.
        File testDir = new File(dirToWatch);
        if (testDir.exists()) {
            try {
                FileUtils.deleteDirectory(testDir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        testDir.mkdir();

        // Init a watcher on __test_dir directory
        DirectoryTreeWatcher directoryTreeWatcher = new DirectoryTreeWatcher(dirToWatch)
                .addEventListener(new LoggingInotifywaitEventListener());
        directoryTreeWatcher.startWatch();

        // Wait 5 seconds
       try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create a sub dir newDir_1
        File newDir1 = new File(dirToWatch+"/newDir_1");
        newDir1.mkdir();

        // Create a sub dir newDir_2 in newDir_1
        File newDir2 = new File(newDir1.getPath()+"/newDir_2");
        newDir2.mkdir();

        // Create a file
        File newFile21 = new File(newDir2.getPath()+"/newFile_2_1");
        try {
            FileWriter fileWriter = new FileWriter(newFile21);
            fileWriter.write("this is a test");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Delete directories
        try {
            FileUtils.deleteDirectory(newDir1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Stop watching
        directoryTreeWatcher.stopWatch();

        newDir1.mkdir();

        // Restart watch
        directoryTreeWatcher.startWatch();

        newDir2.mkdir();

        // Delete directories
        try {
            FileUtils.deleteDirectory(testDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("End.");

    }
}
