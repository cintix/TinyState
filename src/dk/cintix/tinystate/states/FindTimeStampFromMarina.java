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
@State(from = States.INJECT_TIMESTAMPS, to = States.EXPORT, onError = States.INIT)
public class FindTimeStampFromMarina implements StateEntity {

    private static int timesTries = 1;

    @Override
    public boolean run() {
        timesTries++;
        try {
            TimeUnit.MILLISECONDS.sleep(2500);
        } catch (InterruptedException ex) {
            Logger.getLogger(FindTimeStampFromMarina.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (timesTries < 3) return false;
        return true;
    }

}
