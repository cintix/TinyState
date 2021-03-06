package dk.cintix.tinystate.states;

import dk.cintix.tinystate.annotations.State;
import dk.cintix.tinystate.config.States;
import dk.cintix.tinystate.states.base.StateEntity;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author migo
 */
@State(from = States.INJECT_IMAGES, to = States.INJECT_TIMESTAMPS, onError = States.INIT)
public class UploadImagesToIMBO implements StateEntity {

    @Override
    public boolean run() {
        if (1==1) {
            try {
                TimeUnit.MILLISECONDS.sleep(350);
                
                
                if ((System.currentTimeMillis() % 2 ) == 0) {
                    
                    throw new RuntimeException("FAKE IO ERROR!");
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(UploadImagesToIMBO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

}
