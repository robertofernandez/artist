package ar.com.sodhium.java.swing.utils.functions;

import java.util.HashMap;

public class ActionsManager {
    private HashMap<String, ActionExecutor> executors;
    
    public ActionsManager() {
        executors = new HashMap<>();
    }
    
    public void executeAction(String name, ParametersSet parameters) {
        try {
            executors.get(name).executeAction(name, parameters);
        } catch (Exception e) {
            // TODO: log
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public HashMap<String, ActionExecutor> getExecutors() {
        return executors;
    }
    
    public void addExecutor(String name, ActionExecutor executor) {
        executors.put(name, executor);
    }

}
