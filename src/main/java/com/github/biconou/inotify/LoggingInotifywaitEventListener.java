/**
 * Paquet de définition
 **/
package com.github.biconou.inotify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: Merci de donner une description du service rendu par cette classe
 */
public class LoggingInotifywaitEventListener extends DefaultInotifywaitEventListener {

    private static Logger log = LoggerFactory.getLogger(LoggingInotifywaitEventListener.class);

    @Override
    public void doEvent(InotifywaitEvent event) {
        log.debug("Inotifywait event triggered ["+event+"]");
    }
}
 
