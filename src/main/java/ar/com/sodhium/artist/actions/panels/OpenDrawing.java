package ar.com.sodhium.artist.actions.panels;

import java.io.File;

import javax.swing.JFileChooser;

import ar.com.sodhium.artist.ide.main.ArtistApp;
import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;

public class OpenDrawing implements ActionExecutor {

    private ArtistApp app;

    public OpenDrawing(ArtistApp app) {
        this.app = app;
    }

    @Override
    public void executeAction(String name, ParametersSet parameters) throws Exception {
        JFileChooser fileSelector = new JFileChooser();
        int returnVal = fileSelector.showOpenDialog(app.getMainFrame());
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileSelector.getSelectedFile();
            app.createDrawingPanelFromFile(selectedFile);
        }
    }

}
