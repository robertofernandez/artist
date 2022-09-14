package ar.com.sodhium.artist.actions.panels;

import ar.com.sodhium.artist.ide.main.ArtistApp;
import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;

public class NewDrawingPanel implements ActionExecutor {
    
    private ArtistApp app;

    public NewDrawingPanel(ArtistApp app) {
        this.app = app;
    }

    @Override
    public void executeAction(String name, ParametersSet parameters) throws Exception {
        app.createDrawingPanel();
    }

}
