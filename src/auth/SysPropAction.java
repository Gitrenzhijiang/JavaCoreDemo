package auth;

import java.security.PrivilegedAction;

public class SysPropAction implements PrivilegedAction<String> {
    private String propertyName;
    /**
     * Constructs a action for looking up a given property.
     * @param propertyName
     */
    public SysPropAction(String propertyName) {
        this.propertyName = propertyName;
    }
    
    @Override
    public String run() {
        
        return System.getProperty(propertyName);
    }

}
