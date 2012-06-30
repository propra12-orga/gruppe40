package main;

import game.GameData;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import map.EmptyField;
import map.Field;
import map.Map;

import draw.Drawable;
import draw.MapPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;

public class MapEditor {
    
	public final JFrame frame = new JFrame("Map-Editor");
	
	private int w = Map.SIZE_DEFAULT_X;
	private int h = Map.SIZE_DEFAULT_Y;
	private JPanel mapWrapper;
	private MapPanel mapPanel;
	private JComboBox tileSelection;
	
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
	    //Screen space to map space coordinates
	    x -= mapPanel.getX() + mapWrapper.getX();
	    y -= mapPanel.getY() + mapWrapper.getY();
        int width = mapPanel.getWidth();
        int height = mapPanel.getHeight();
        int tx = x*w/width;
        int ty = (int)(y*h/(double)height - 0.5);
        try {
            String s = (String) tileSelection.getSelectedItem();
            //Get class for field
            Class<?> whichClass = Class.forName("map." + s);
            //Create a field using a constructor with parameters x and y
            Field field = (Field)whichClass.getConstructor(Integer.TYPE, Integer.TYPE).newInstance(tx, ty);
            GameData.map.setField(field);
            redrawMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public MapEditor() {
        GameData.drawables = new LinkedList<Drawable>();
	    GameData.map = new Map(w, h, false);
	    Container pane = frame.getContentPane();
	    frame.setSize(600, 600);
	    pane.setLayout(new BorderLayout());
	    
	    final JSlider widthSlider = new JSlider(JSlider.HORIZONTAL, Map.SIZE_MIN_X, Map.SIZE_MAX_X, Map.SIZE_DEFAULT_X);
        widthSlider.setMinorTickSpacing(1);
        widthSlider.setMajorTickSpacing(5);
        widthSlider.setSnapToTicks(true);
        widthSlider.setPaintLabels(true);
        widthSlider.setPaintTicks(true);
        pane.add(widthSlider, BorderLayout.SOUTH);
        
        final JSlider heightSlider = new JSlider(JSlider.VERTICAL, Map.SIZE_MIN_Y, Map.SIZE_MAX_Y, Map.SIZE_DEFAULT_Y);
        heightSlider.setMinorTickSpacing(1);
        heightSlider.setMajorTickSpacing(5);
        heightSlider.setSnapToTicks(true);
        heightSlider.setPaintLabels(true);
        heightSlider.setPaintTicks(true);
        pane.add(heightSlider, BorderLayout.EAST);
        
        JPanel control = new JPanel();
        pane.add(control, BorderLayout.NORTH);
        
        JButton saveButton = new JButton("Save");
        control.add(saveButton);
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
            }
        };
	    widthSlider.addChangeListener(widthListener);
	    
        ChangeListener heightListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                h = heightSlider.getValue();
                initMap();
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
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
            @Override
            public void mousePressed(MouseEvent e) {
                placeTile(e.getX(), e.getY());
            }
            @Override
            public void mouseReleased(MouseEvent e) {}
        };
        frame.addMouseListener(mouseListener);
        
        
        ActionListener clearListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                
                
                for(int n=0;n<GameData.map.getHeight();n++){
                    for(int m=0;m<GameData.map.getWidth();m++){
                        
             
                            Field field = new EmptyField(m, n);
                            GameData.map.setField(field);

                        
                           
                    }
                }
                redrawMap();
            }
        };
        clearButton.addActionListener(clearListener);
        
        
        
        
        
        
        ActionListener saveListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CreateMap.saveMap(GameData.map, "Azeroth");
            }
        };
        saveButton.addActionListener(saveListener);

        String[] tileNames = {"EmptyField","IndestructibleWall", "MediumWall", "NormalWall","Exit"};
        tileSelection = new JComboBox(tileNames);
        control.add(tileSelection, BorderLayout.WEST);
        
        initMap();
	}
}
