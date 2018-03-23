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
@State(from = States.INIT, to = States.INJECT_IMAGES, onError = States.INIT)
public class FindNewPrograms implements StateEntity {

    @Override
    public boolean run() {
        if (1==1) {
            try {
            TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException ex) {
                Logger.getLogger(FindNewPrograms.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return true;
    }

}
