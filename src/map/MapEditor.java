package map;

import game.Drawable;
import game.GameData;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.MapPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class MapEditor {

    public final JFrame frame = new JFrame("Map-Editor");

    private int         w     = Map.SIZE_DEFAULT_X;
    private int         h     = Map.SIZE_DEFAULT_Y;
    private JPanel      mapWrapper;
    private MapPanel    mapPanel;
    private JComboBox   tileSelection;

    private void redrawMap() {
        GameData.removeDeadDrawables();
        int width = mapWrapper.getWidth();
        int height = mapWrapper.getHeight();
        Rectangle rect = mapPanel.getOptimalSize(width, height);
        if (rect != null) {
            mapPanel.setBounds(rect);
        }
        frame.repaint();
    }

    private void initMap() {
        GameData.drawables = new LinkedList<Drawable>();
        GameData.map = new Map(w, h, false);
        mapPanel.map = GameData.map;
        mapPanel.drawables = GameData.drawables;
        redrawMap();
    }

    private void placeTile(int x, int y) {
        // Screen space to map space coordinates
        x -= mapPanel.getX() + mapWrapper.getX();
        y -= mapPanel.getY() + mapWrapper.getY();
        int width = mapPanel.getWidth();
        int height = mapPanel.getHeight();
        int tx = x * w / width;
        int ty = (int) (y * h / (double) height - 0.5);

        if (GameData.map.contains(tx, ty)) {
            try {
                String s = (String) tileSelection.getSelectedItem();
                // Get class for field
                Class<?> whichClass = Class.forName("map." + s);
                // Create a field using a constructor with parameters x and y
                Field field = (Field) whichClass.getConstructor(Integer.TYPE, Integer.TYPE).newInstance(tx, ty);
                GameData.map.setField(field);
                
                //TODO implement jlabels (red/green)
                System.out.println("Is map symmetric? - " + GameData.map.isSymmetric());
                System.out.println("Is map valid? - " + GameData.map.isValid());
                
                redrawMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public MapEditor() {
        GameData.drawables = new LinkedList<Drawable>();
        GameData.map = new Map(w, h, false);
        Container pane = frame.getContentPane();
        frame.setSize(600, 600);
        pane.setLayout(new BorderLayout());

        final JSlider widthSlider = new JSlider(JSlider.HORIZONTAL, Map.SIZE_MIN_X, Map.SIZE_MAX_X, Map.SIZE_DEFAULT_X);
        widthSlider.setMinorTickSpacing(2);
        widthSlider.setMajorTickSpacing(2);
        widthSlider.setSnapToTicks(true);
        widthSlider.setPaintLabels(true);
        widthSlider.setPaintTicks(true);
        pane.add(widthSlider, BorderLayout.SOUTH);

        final JSlider heightSlider = new JSlider(JSlider.VERTICAL, Map.SIZE_MIN_Y, Map.SIZE_MAX_Y, Map.SIZE_DEFAULT_Y);
        heightSlider.setMinorTickSpacing(2);
        heightSlider.setMajorTickSpacing(2);
        heightSlider.setSnapToTicks(true);
        heightSlider.setPaintLabels(true);
        heightSlider.setPaintTicks(true);
        pane.add(heightSlider, BorderLayout.EAST);

        JPanel control = new JPanel();
        pane.add(control, BorderLayout.NORTH);

        JButton saveButton = new JButton("Save");
        control.add(saveButton);
        JButton loadButton = new JButton("Load");
        control.add(loadButton);
        JButton clearButton = new JButton("Clear");
        control.add(clearButton);

        mapPanel = new MapPanel();
        mapWrapper = new JPanel();
        mapWrapper.setLayout(null);
        mapWrapper.add(mapPanel);
        pane.add(mapWrapper, BorderLayout.CENTER);

        ChangeListener widthListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                w = widthSlider.getValue();
                initMap();
                frame.requestFocus();
            }
        };
        widthSlider.addChangeListener(widthListener);

        ChangeListener heightListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                h = heightSlider.getValue();
                initMap();
                frame.requestFocus();
            }
        };
        heightSlider.addChangeListener(heightListener);

        ComponentListener componentListener = new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                redrawMap();
            }

            @Override
            public void componentShown(ComponentEvent e) {
                redrawMap();
            }

            @Override
            public void componentHidden(ComponentEvent e) {}

            @Override
            public void componentMoved(ComponentEvent e) {}
        };
        pane.addComponentListener(componentListener);

        MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                placeTile(e.getX(), e.getY());
                frame.requestFocus();
            }

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                placeTile(e.getX(), e.getY());
                frame.requestFocus();
            }

            @Override
            public void mouseReleased(MouseEvent e) {}
        };
        frame.addMouseListener(mouseListener);

        ActionListener clearListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int y = 0; y < h; y++) {
                    for (int x = 0; x < w; x++) {
                        GameData.map.setField(new EmptyField(x, y));
                    }
                }
                redrawMap();
                frame.requestFocus();
            }
        };
        clearButton.addActionListener(clearListener);

        ActionListener saveListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MapCreator.saveMap(GameData.map, "Azeroth");
                frame.requestFocus();
            }
        };
        saveButton.addActionListener(saveListener);

        ActionListener loadListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameData.drawables = new LinkedList<Drawable>();
                GameData.map = new Map("Map1");
                w = GameData.map.getWidth();
                h = GameData.map.getHeight();
                widthSlider.setValue(w);
                heightSlider.setValue(h);
                mapPanel.map = GameData.map;
                mapPanel.drawables = GameData.drawables;
                redrawMap();
                frame.requestFocus();
            }
        };

        KeyListener exitListener = new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    int option = JOptionPane.showConfirmDialog(frame, "Do you really want to exit?","Exit",JOptionPane.YES_NO_OPTION);
                    if(option == JOptionPane.OK_OPTION) {
                        frame.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent arg0) {}

            @Override
            public void keyTyped(KeyEvent arg0) {}
        };

        frame.setFocusable(true);
        frame.addKeyListener(exitListener);
        loadButton.addActionListener(loadListener);

        String[] tileNames = { "EmptyField", "IndestructibleWall", "MediumWall", "NormalWall", "Exit" };
        tileSelection = new JComboBox(tileNames);
        control.add(tileSelection, BorderLayout.WEST);

        initMap();
    }
}
