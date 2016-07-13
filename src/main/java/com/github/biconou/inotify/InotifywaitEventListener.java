/**
 * Paquet de définition
 **/
package com.github.biconou.inotify;

/**
 * Description: Merci de donner une description du service rendu par cette interface
 */
public interface InotifywaitEventListener {

    void doEvent(InotifywaitEvent event);

    void doAccess(InotifywaitEvent event);

    void doModify(InotifywaitEvent event);

    void doAttrib(InotifywaitEvent event);

    void doCloseWrite(InotifywaitEvent event);

    void doCloseNowrite(InotifywaitEvent event);

    void doOpen(InotifywaitEvent event);

    void doMovedTo(InotifywaitEvent event);

    void doMovedFrom(InotifywaitEvent event);

    void doMoveSelf(InotifywaitEvent event);

    void doCreate(InotifywaitEvent event);

    void doDelete(InotifywaitEvent event);

    void doDeleteSelf(InotifywaitEvent event);

    void doUnmount(InotifywaitEvent event);

}
 
