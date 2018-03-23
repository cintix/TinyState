package dk.cintix.tinystate.engine;

import dk.cintix.tinystate.annotations.State;
import dk.cintix.tinystate.config.States;
import dk.cintix.tinystate.states.base.StateEntity;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author migo
 */
public class Flow implements Serializable {

    private final String name;
    private final int version;

    private final List<Class<?>> entities = new LinkedList<>();

    /**
     *
     * @param name
     * @param version
     */
    public Flow(String name, int version) {
        this.name = name;
        this.version = version;
    }

    public void start() {
        System.out.println("Starting Flow [" + name + "] VERISON " + version);
        run(States.INIT);
    }

    /**
     * run
     *
     * @param fromState
     */
    public void run(States fromState) {

        List<Class<?>> stateEntities = getState(fromState);
        List<States> nextStates = new LinkedList<>();
        
        for (Class<?> stateEntity : stateEntities) {
            try {
                System.out.print("Executing " + stateEntity.getSimpleName());
                StateEntity nextState = (StateEntity) stateEntity.newInstance();
                boolean run = false;

                try {
                    run = nextState.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (run) {
                    System.out.println(" - SUCCESS");
                    nextStates.add(getNextState(stateEntity));
                } else {
                    System.out.println(" - FAILED");
                    run(getFailedState(stateEntity));
                }
            } catch (IllegalAccessException | InstantiationException exception) {
                stateEntity = null;
            }
        }
        
        for(States nextState : nextStates ) {
            run(nextState);
        }
    }

    /**
     * registerEntities
     *
     * @param state
     */
    public void registerEntities(Class<?>... state) {
        for (Class<?> entity : state) {
            if (StateEntity.class.isAssignableFrom(entity)) {
                entities.add(entity);
            } else {
                System.err.println(entity.getSimpleName() + " doesn't implements StateEntity and will not be loaded!");
            }
        }
    }

    /**
     * getState
     *
     * @param currentState
     * @return
     */
    private List<Class<?>> getState(States currentState) {
        List<Class<?>> classes = new LinkedList<>();
        for (Class<?> entity : entities) {
            State annotation = entity.getAnnotation(State.class);
            if (annotation != null) {
                if (annotation.from().equals(currentState)) {
                    classes.add(entity);
                }
            }
        }
        return classes;
    }

    /**
     * getNextState
     *
     * @param currentEntity
     * @return
     */
    private States getNextState(Class<?> currentEntity) {
        State currentState = currentEntity.getAnnotation(State.class);
        return currentState.to();
    }

    /**
     * getFailedState
     *
     * @param currentEntity
     * @return
     */
    private States getFailedState(Class<?> currentEntity) {
        State currentState = currentEntity.getAnnotation(State.class);
        return currentState.onError();
    }

    @Override
    public String toString() {
        return "Flow{" + "name=" + name + ", version=" + version + ", entities=" + entities + '}';
    }

}
