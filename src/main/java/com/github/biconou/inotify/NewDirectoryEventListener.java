package com.github.biconou.inotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by remi on 01/07/16.
 */
public class NewDirectoryEventListener extends DefaultInotifywaitEventListener {

    private static Logger log = LoggerFactory.getLogger(LoggingInotifywaitEventListener.class);


    @Override
    public void doCreate(InotifywaitEvent event) {
        if (event.isDir()) {
            log.debug("Directory created : "+event.getPath()+event.getFile());
        }
    }
}
