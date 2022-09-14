package ar.com.sodhium.artist.ide.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;

import ar.com.sodhium.artist.actions.drawing.simple.Draw2PointsArcOnPanelAction;
import ar.com.sodhium.artist.actions.panels.NewDrawingPanel;
import ar.com.sodhium.artist.actions.simple.Draw2PointsArcAction;
import ar.com.sodhium.artist.actions.simple.DrawCircleAction;
import ar.com.sodhium.artist.actions.simple.DrawGhostAction;
import ar.com.sodhium.artist.actions.simple.DrawGreenArcsAction;
import ar.com.sodhium.artist.actions.simple.DrawHorizontalOvalAction;
import ar.com.sodhium.artist.actions.simple.DrawPinkCircleAction;
import ar.com.sodhium.artist.actions.simple.DrawVerticalOvalAction;
import ar.com.sodhium.artist.actions.simple.FindColorPickersAction;
import ar.com.sodhium.artist.actions.simple.PaintCircleAction;
import ar.com.sodhium.artist.ide.main.menu.SimpleActionExecuterMenuItem;
import ar.com.sodhium.artist.ide.panels.ImagePanel;
import ar.com.sodhium.images.mapping.ColorMap;
import ar.com.sodhium.java.swing.utils.functions.ActionsManager;
import ar.com.sodhium.swing.utils.ResizableDesktopPane;

public class ArtistApp {

    private JFrame mainFrame;
    private ActionsManager actionsManager;
    private ImagePanel drawingPanel;
    // private JScrollPane mainPane;
    private JDesktopPane mainDesktopPane;
    private JScrollPane scrollPane;

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
        actionsManager.addExecutor("draw_2_pounts_arc", new Draw2PointsArcAction());

        actionsManager.addExecutor("new_drawing", new NewDrawingPanel(this));
        actionsManager.addExecutor("draw_2_pounts_arc_on_panel", new Draw2PointsArcOnPanelAction(this));

    }

    /**
     * Initialize the contents of the frame.
     */
    private void initializeFrontEnd() {
        mainFrame = new JFrame();
        mainFrame.setBackground(new Color(0, 0, 0));
        mainFrame.setTitle("Artist v0.1");
        mainFrame.setBounds(100, 100, 800, 600);
        // frmArtistV.setExtendedState(frmArtistV.getExtendedState() |
        // JFrame.MAXIMIZED_BOTH);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

        // Setear la GUI.
        // panelPrincipal = new JDesktopPane();
        mainDesktopPane = new ResizableDesktopPane();
        mainDesktopPane.putClientProperty("JDesktopPane.dragMode", "outline");
        // Como usamos pack, no es suficiente llamar a setSize.
        // Debemos setear el tama�o preferido del panel principal.
        mainDesktopPane.setPreferredSize(new Dimension(640, 480));
        scrollPane = new JScrollPane();
        scrollPane.getViewport().add(mainDesktopPane);

        // this.setContentPane(panelPrincipal);
        mainFrame.getContentPane().add("North", scrollPane);

        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        JMenu simpleDrawingWithRobotMenu = new JMenu("Simple Drawing with robot");
        JMenu drawingInCanvasMenu = new JMenu("Drawing in canvas");
        menuBar.add(simpleDrawingWithRobotMenu);
        menuBar.add(drawingInCanvasMenu);

        JMenuItem drawBlackCircleItem = new SimpleActionExecuterMenuItem("Draw circle", "draw_circle", actionsManager);
        simpleDrawingWithRobotMenu.add(drawBlackCircleItem);

        for (int i = 1; i < 4; i++) {
            JMenuItem paintCircleItem = new SimpleActionExecuterMenuItem("Paint circle size " + i, "paint_circle_" + i,
                    actionsManager);
            simpleDrawingWithRobotMenu.add(paintCircleItem);
        }

        JMenuItem drawHorizonatalOvalItem = new SimpleActionExecuterMenuItem("Draw horizontal oval",
                "draw_horizontal_oval", actionsManager);
        simpleDrawingWithRobotMenu.add(drawHorizonatalOvalItem);

        JMenuItem drawVerticalOvalItem = new SimpleActionExecuterMenuItem("Draw vertical oval", "draw_vertical_oval",
                actionsManager);
        simpleDrawingWithRobotMenu.add(drawVerticalOvalItem);

        JMenuItem findColorPickersItem = new SimpleActionExecuterMenuItem("Find color pickers", "find_color_pickers",
                actionsManager);
        simpleDrawingWithRobotMenu.add(findColorPickersItem);

        JMenuItem drawPinkCircleItem = new SimpleActionExecuterMenuItem("Draw pink circle", "draw_pink_circle",
                actionsManager);
        simpleDrawingWithRobotMenu.add(drawPinkCircleItem);

        JMenuItem drawGreenArcsItem = new SimpleActionExecuterMenuItem("Draw green arcs", "draw_green_arcs",
                actionsManager);
        simpleDrawingWithRobotMenu.add(drawGreenArcsItem);

        JMenuItem drawGhostItem = new SimpleActionExecuterMenuItem("Draw ghost (size 2)", "draw_ghost_2",
                actionsManager);
        simpleDrawingWithRobotMenu.add(drawGhostItem);

        JMenuItem drawGhostItem4 = new SimpleActionExecuterMenuItem("Draw ghost (size 4)", "draw_ghost_4",
                actionsManager);
        simpleDrawingWithRobotMenu.add(drawGhostItem4);

        JMenuItem draw2PointsArcItem = new SimpleActionExecuterMenuItem("Draw 2 points arc", "draw_2_pounts_arc",
                actionsManager);
        simpleDrawingWithRobotMenu.add(draw2PointsArcItem);

        JMenuItem newDrawing = new SimpleActionExecuterMenuItem("New drawing", "new_drawing", actionsManager);
        drawingInCanvasMenu.add(newDrawing);
        
        JMenuItem draw2PointsArcOnPanelItem = new SimpleActionExecuterMenuItem("Draw 2 points arc", "draw_2_pounts_arc_on_panel", actionsManager);
        drawingInCanvasMenu.add(draw2PointsArcOnPanelItem);

    }

    public void createDrawingPanel() {
        if (drawingPanel == null) {
            drawingPanel = new ImagePanel("New drawing");
            ColorMap emptyMap = new ColorMap(600, 600);
            try {
                emptyMap.initializeEmpty();
                for (int x = 0; x < 600; x++) {
                    for (int y = 0; y < 600; y++) {
                        emptyMap.setColor(x, y, new Color(255, 255, 255));
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            drawingPanel.setMap(emptyMap);
            mainDesktopPane.add(drawingPanel);
        }
    }

    public ImagePanel getDrawingPanel() {
        return drawingPanel;
    }

}
