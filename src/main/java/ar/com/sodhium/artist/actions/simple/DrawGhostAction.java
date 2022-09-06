package ar.com.sodhium.artist.actions.simple;

import ar.com.sodhium.artist.toolapps.drawception.DrawceptionArtistRobot;
import ar.com.sodhium.artist.toolapps.drawception.concept.ConceptDrawceptionArtistRobot;
import ar.com.sodhium.java.swing.utils.functions.ActionExecutor;
import ar.com.sodhium.java.swing.utils.functions.ParametersSet;

public class DrawGhostAction implements ActionExecutor {
    private Integer size;

    public DrawGhostAction(Integer size) {
        this.size = size;

    }

    @Override
    public void executeAction(String name, ParametersSet parameters) throws Exception {
        Thread.sleep(3000L);
        ConceptDrawceptionArtistRobot artist = new ConceptDrawceptionArtistRobot();
        artist.init();

        artist.drawEye("#0247fe", "#00ffff", 550, 300, size);

    }

}
