package com.github.biconou.inotify;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

/**
 * Created by remi on 28/06/16.
 */
public class DirectoryTreeWatcherTest {

    private String baseDirPath() {
        return DirectoryTreeWatcherTest.class.getResource("/").getPath();
    }

    @Test
    public void simpleTest() {

        String dirToWatch = baseDirPath() + "__test_dir";

        File dir = new File(dirToWatch);
        if (dir.exists()) {
            try {
                FileUtils.deleteDirectory(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        dir.mkdir();

        DirectoryTreeWatcher directoryTreeWatcher = new DirectoryTreeWatcher(dirToWatch);
        directoryTreeWatcher.startWatch();

       try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File newDir1 = new File(dirToWatch+"/newDir_1");

        newDir1.mkdir();
        File newDir2 = new File(newDir1.getPath()+"/newDir_2");
        newDir2.mkdir();
        File newFile21 = new File(newDir2.getPath()+"/newFile_2_1");
        try {
            FileWriter fileWriter = new FileWriter(newFile21);
            fileWriter.write("this is a test");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileUtils.deleteDirectory(newDir1);
            FileUtils.deleteDirectory(dir);
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
