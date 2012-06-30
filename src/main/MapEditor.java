package main;

import game.GameData;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import map.Map;

import draw.Drawable;
import draw.MapPanel;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Rectangle;
import java.util.LinkedList;

public class MapEditor {
    
	public final JFrame frame = new JFrame("Map-Editor");
	
	private int w = Map.SIZE_DEFAULT_X;
	private int h = Map.SIZE_DEFAULT_Y;
	private JPanel mapWrapper;
	private MapPanel mapPanel;
	
	private void initMap() {
        GameData.drawables = new LinkedList<Drawable>();
        GameData.map = new Map(w, h, false);
	    GameData.removeDeadDrawables();
        mapPanel.map = GameData.map;
        mapPanel.drawables = GameData.drawables;
        int width = mapWrapper.getWidth();
        int height = mapWrapper.getHeight();
        Rectangle rect = mapPanel.getOptimalSize(width, height);
        if (rect != null) {
            mapPanel.setBounds(rect);
        }
        frame.repaint();
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
        initMap();
	}
}
