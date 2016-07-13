/**
 * Paquet de définition
 **/
package com.github.biconou.inotify;

/**
 * Description: Merci de donner une description du service rendu par cette classe
 */
public class DefaultInotifywaitEventListener implements InotifywaitEventListener {


    @Override
    public void doEvent(InotifywaitEvent event) {
        switch (event.getType()) {
            case access:
                doAccess(event);
                break;
            case attrib:
                doAttrib(event);
                break;
            case close_nowrite:
                doCloseNowrite(event);
                break;
            case close_write:
                doCloseWrite(event);
                break;
            case delete:
                doDelete(event);
                break;
            case delete_self:
                doDeleteSelf(event);
                break;
            case modify:
                doModify(event);
                break;
            case create:
                doCreate(event);
                break;
            case moved_from:
                doMovedFrom(event);
                break;
            case moved_to:
                doMovedTo(event);
                break;
            case move_self:
                doMoveSelf(event);
                break;
            case open:
                doOpen(event);
                break;
            case unmount:
                doUnmount(event);
                break;
        }
    }

    @Override
    public void doAccess(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doModify(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doAttrib(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doCloseWrite(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doCloseNowrite(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doOpen(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doMovedTo(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doMovedFrom(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doMoveSelf(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doCreate(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doDelete(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doDeleteSelf(InotifywaitEvent event) {
        // does nothing here
    }

    @Override
    public void doUnmount(InotifywaitEvent event) {
        // does nothing here
    }
}
 
