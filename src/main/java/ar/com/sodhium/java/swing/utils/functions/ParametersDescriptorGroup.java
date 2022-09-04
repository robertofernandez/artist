package ar.com.sodhium.java.swing.utils.functions;

import java.util.HashMap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParametersDescriptorGroup {
    @SerializedName("name")
    @Expose
    private String name;
    
    @SerializedName("parameters")
    @Expose
    private HashMap<String, ParameterDescriptor> parameters;

    public ParametersDescriptorGroup(String name, HashMap<String, ParameterDescriptor> parameters) {
        this.name = name;
        this.parameters = parameters;
    }
    
    
    public String getName() {
        return name;
    }
    
    public HashMap<String, ParameterDescriptor> getParameters() {
        return parameters;
    }
    
    public ParameterDescriptor getParameter(String id) {
        return parameters.get(id);
    }
    
    public void setParameter(String id, ParameterDescriptor descriptor) {
        parameters.put(id, descriptor);
    }
}
