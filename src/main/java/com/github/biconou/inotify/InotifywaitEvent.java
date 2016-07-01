package com.github.biconou.inotify;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by remi on 28/06/16.
 */
public class InotifywaitEvent {

    public enum EventType {

        access,		    // file or directory contents were read
        modify,		    // file or directory contents were written
        attrib,		    // file or directory attributes changed
        close_write,	// file or directory closed, after being opened in
                        // writable mode
        close_nowrite,	// file or directory closed, after being opened in
                        // read-only mode
        open,		    // file or directory opened
        moved_to,	    // file or directory moved to watched directory
        moved_from,	    // file or directory moved from watched directory
        create,		    // file or directory created within watched directory
        delete,		    // file or directory deleted within watched directory
        delete_self,	// file or directory was deleted
        unmount;		// file system containing file or directory unmounted

        public boolean isClose() {
            return this.equals(close_nowrite) || this.equals(close_write);
        }

        public boolean isMove() {
            return this.equals(moved_from) || this.equals(moved_to);
        }

        public boolean isDelete() {
            return this.equals(delete) || this.equals(delete_self);
        }

        public static EventType parse(String eventStringType) {
            switch (eventStringType) {
                case "ACCESS" :
                    return access;
                case "MODIFY" :
                    return modify;
                case "ATTRIB" :
                    return attrib;
                case "CLOSE_WRITE" :
                    return close_write;
                case "CLOSE_NOWRITE" :
                    return close_nowrite;
                case "OPEN" :
                    return open;
                case "MOVED_TO" :
                    return moved_to;
                case "MOVED_FROM" :
                    return moved_from;
                case "CREATE" :
                    return create;
                case "DELETE" :
                    return delete;
                case "DELETE_SELF" :
                    return delete_self;
                case "UNMOUNT" :
                    return unmount;
            }
            return null;
        }
    }


    private String path = null;
    private String file = null;
    private EventType type = null;
    private boolean dir = false;

    private InotifywaitEvent(){}

    static InotifywaitEvent parse(String eventString) {

        InotifywaitEvent newInotifywaitEvent = new InotifywaitEvent();

        Pattern p = Pattern.compile("(.*)/\\*/(.*)/\\*/(.*)");
        Matcher m = p.matcher(eventString);
        if (m.matches()) {
            newInotifywaitEvent.path = m.group(1);
            String eventTypes = m.group(2);
            String types[] = eventTypes.split(":");
            Arrays.stream(types).forEach(t -> {
                if ("ISDIR".equals(t)) {
                    newInotifywaitEvent.dir = true;
                } else {
                    EventType eventType = EventType.parse(t);
                    if (eventType != null) {
                        if (newInotifywaitEvent.type == null) {
                            newInotifywaitEvent.type = eventType;
                        } else {
                            throw new RuntimeException("Multiple event types");
                        }
                    }
                }
            });

            newInotifywaitEvent.file = m.group(3);
        } else {
            return null;
        }

        if (newInotifywaitEvent.type == null) {
            throw new RuntimeException("Can not determine event type");
        }
        return newInotifywaitEvent;

    }

    public String getPath() {
        return path;
    }

    public String getFile() {
        return file;
    }

    public EventType getType() {
        return type;
    }

    public boolean isDir() {
        return dir;
    }

    @Override
    public String toString() {
        return "InotifywaitEvent{" +
                "path='" + path + '\'' +
                ", file='" + file + '\'' +
                ", type=" + type +
                ", dir=" + dir +
                '}';
    }
}
