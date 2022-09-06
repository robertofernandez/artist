package ar.com.sodhium.artist.ide.main;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.border.BevelBorder;

import ar.com.sodhium.artist.actions.simple.DrawCircleAction;
import ar.com.sodhium.artist.actions.simple.DrawGhostAction;
import ar.com.sodhium.artist.actions.simple.DrawGreenArcsAction;
import ar.com.sodhium.artist.actions.simple.DrawHorizontalOvalAction;
import ar.com.sodhium.artist.actions.simple.DrawPinkCircleAction;
import ar.com.sodhium.artist.actions.simple.DrawVerticalOvalAction;
import ar.com.sodhium.artist.actions.simple.FindColorPickersAction;
import ar.com.sodhium.artist.actions.simple.PaintCircleAction;
import ar.com.sodhium.artist.ide.main.menu.SimpleActionExecuterMenuItem;
import ar.com.sodhium.java.swing.utils.functions.ActionsManager;

public class ArtistApp {

    private JFrame mainFrame;
    private ActionsManager actionsManager;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ArtistApp window = new ArtistApp();
                    window.mainFrame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public ArtistApp() {
        initializeLogic();
        initializeFrontEnd();
    }

    private void initializeLogic() {
        actionsManager = new ActionsManager();
        actionsManager.addExecutor("draw_circle", new DrawCircleAction());
        actionsManager.addExecutor("paint_circle_1", new PaintCircleAction(1));
        actionsManager.addExecutor("paint_circle_2", new PaintCircleAction(2));
        actionsManager.addExecutor("paint_circle_3", new PaintCircleAction(3));
        actionsManager.addExecutor("draw_horizontal_oval", new DrawHorizontalOvalAction());
        actionsManager.addExecutor("draw_vertical_oval", new DrawVerticalOvalAction());
        actionsManager.addExecutor("find_color_pickers", new FindColorPickersAction());
        actionsManager.addExecutor("draw_pink_circle", new DrawPinkCircleAction());
        actionsManager.addExecutor("draw_green_arcs", new DrawGreenArcsAction());
        actionsManager.addExecutor("draw_ghost_2", new DrawGhostAction(2));
        actionsManager.addExecutor("draw_ghost_4", new DrawGhostAction(4));
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initializeFrontEnd() {
        mainFrame = new JFrame();
        mainFrame.setBackground(new Color(0, 0, 0));
        mainFrame.setTitle("Artist v0.1");
        mainFrame.setBounds(100, 100, 640, 480);
        // frmArtistV.setExtendedState(frmArtistV.getExtendedState() |
        // JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

        JScrollPane mainPane = new JScrollPane();
        mainPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        mainPane.setBackground(new Color(240, 240, 240));
        mainFrame.getContentPane().add(mainPane);

        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        JMenu simpleDrawingsMenu = new JMenu("Simple Drawings");
        menuBar.add(simpleDrawingsMenu);

        JMenuItem drawBlackCircleItem = new SimpleActionExecuterMenuItem("Draw circle", "draw_circle", actionsManager);
        simpleDrawingsMenu.add(drawBlackCircleItem);

        for (int i = 1; i < 4; i++) {
            JMenuItem paintCircleItem = new SimpleActionExecuterMenuItem("Paint circle size " + i, "paint_circle_" + i,
                    actionsManager);
            simpleDrawingsMenu.add(paintCircleItem);
        }

        JMenuItem drawHorizonatalOvalItem = new SimpleActionExecuterMenuItem("Draw horizontal oval",
                "draw_horizontal_oval", actionsManager);
        simpleDrawingsMenu.add(drawHorizonatalOvalItem);

        JMenuItem drawVerticalOvalItem = new SimpleActionExecuterMenuItem("Draw vertical oval", "draw_vertical_oval",
                actionsManager);
        simpleDrawingsMenu.add(drawVerticalOvalItem);

        JMenuItem findColorPickersItem = new SimpleActionExecuterMenuItem("Find color pickers", "find_color_pickers",
                actionsManager);
        simpleDrawingsMenu.add(findColorPickersItem);

        JMenuItem drawPinkCircleItem = new SimpleActionExecuterMenuItem("Draw pink circle", "draw_pink_circle",
                actionsManager);
        simpleDrawingsMenu.add(drawPinkCircleItem);

        JMenuItem drawGreenArcsItem = new SimpleActionExecuterMenuItem("Draw green arcs", "draw_green_arcs",
                actionsManager);
        simpleDrawingsMenu.add(drawGreenArcsItem);

        JMenuItem drawGhostItem = new SimpleActionExecuterMenuItem("Draw ghost (size 2)", "draw_ghost_2", actionsManager);
        simpleDrawingsMenu.add(drawGhostItem);
        
        JMenuItem drawGhostItem4 = new SimpleActionExecuterMenuItem("Draw ghost (size 4)", "draw_ghost_4", actionsManager);
        simpleDrawingsMenu.add(drawGhostItem4);

    }

}
