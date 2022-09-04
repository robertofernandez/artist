package ar.com.sodhium.artist.ide.main.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import ar.com.sodhium.java.swing.utils.functions.ActionsManager;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;

public class SimpleActionExecuterMenuItem extends JMenuItem {
    private static final long serialVersionUID = 5441722133489271738L;
    private String actionName;
    private ActionsManager actionsManager;

    public SimpleActionExecuterMenuItem(String name, String actionName, ActionsManager actionsManager) {
        super(name);
        this.actionName = actionName;
        this.actionsManager = actionsManager;
        addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Action to execute: " + actionName);
                actionsManager.executeAction(actionName, new ParametersSet());
            }
        });
    }

    public String getActionName() {
        return actionName;
    }

    public ActionsManager getActionsManager() {
        return actionsManager;
    }
}
