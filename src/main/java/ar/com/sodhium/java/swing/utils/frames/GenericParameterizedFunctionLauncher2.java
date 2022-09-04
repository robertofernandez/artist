package ar.com.sodhium.java.swing.utils.frames;

import java.util.HashMap;

import javax.swing.JInternalFrame;

import ar.com.sodhium.java.swing.utils.functions.ParametersDescriptorGroup;

public class GenericParameterizedFunctionLauncher2 extends JInternalFrame {
    private static final long serialVersionUID = 5778784396777969973L;
    private HashMap<String, ParametersDescriptorGroup> groups;

    public GenericParameterizedFunctionLauncher2(String name, HashMap<String, ParametersDescriptorGroup> groups) {
        super(name);
        this.groups = groups;
    }

    public HashMap<String, ParametersDescriptorGroup> getGroups() {
        return groups;
    }
}
