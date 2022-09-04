package ar.com.sodhium.java.swing.utils.functions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ParameterDescriptor {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public ParameterDescriptor(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}
