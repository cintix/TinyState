package dk.cintix.tinystate.config;

/**
 *
 * @author migo
 */
public enum States {

    INIT("Initialize"),
    INJECT_IMAGES("Upload images"),
    INJECT_TIMESTAMPS("Retrive timestamps from Maria"),
    EXPORT("Export model"),
    ERROR("Error"),
    DONE("Done"),
    NONE("None state"); 
    
    private final String value;

    private States(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
}
