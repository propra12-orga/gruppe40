package main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MapEditor {
	
	static final int mapSize_MIN = 5;
	static final int mapSize_MAX = 25;
	static final int mapSize_STANDARD = 13;
	int height, width;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public MapEditor() {
		//panels etc...
		JFrame editorWindow = new JFrame("Karteneditor");
		JPanel base = new JPanel();
		JPanel slideLabels = new JPanel();
		JPanel sliders = new JPanel();
		JLabel labelWidth = new JLabel("Kartenbreite               ");
		JLabel labelHeight = new JLabel("               Kartenhoehe");
		JSlider slideWidth = new JSlider(JSlider.HORIZONTAL, mapSize_MIN, mapSize_MAX, mapSize_STANDARD);
		JSlider slideHeight = new JSlider(JSlider.HORIZONTAL, mapSize_MIN, mapSize_MAX, mapSize_STANDARD);
		final JButton buttonEditMap = new JButton("Karte bearbeiten");
		final JTextPane paneMap = new JTextPane();
		JScrollPane scrollMap = new JScrollPane(paneMap);
		
		base.setLayout(new FlowLayout());
		sliders.setLayout(new FlowLayout());
		
		slideLabels.add(labelWidth);
		slideLabels.add(labelHeight);
		
		sliders.add(slideWidth);
		sliders.add(slideHeight);
		
		base.add(scrollMap);
		base.add(buttonEditMap);
		base.add(slideLabels);
		base.add(sliders);
		
		//editing Sliders
		slideWidth.setMinorTickSpacing(1);
		slideWidth.setMajorTickSpacing(5);
		slideWidth.setPaintLabels(true);
		slideWidth.setPaintTicks(true);
		
		slideHeight.setMinorTickSpacing(1);
		slideHeight.setMajorTickSpacing(5);
		slideHeight.setPaintLabels(true);
		slideHeight.setPaintTicks(true);

		//editing JTextPane
		paneMap.setText("Hier Karte eintragen\n z.B.:\n0 0 0\n0 1 0\n0 0 0");
		paneMap.setEditable(false);
		
		//changing ip
		ActionListener alChange = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if(paneMap.isEditable()) {
					//TODO
				}
				else {
					paneMap.setText("");
					paneMap.setEditable(true);
					buttonEditMap.setText("Karte bestaetigen");
				}
			}
		};
		
		ChangeListener widthChange = new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					width = source.getValue();
				}
			}
		};
		
		ChangeListener heightChange = new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					height = source.getValue();
				}
				
			}
		};
		
		buttonEditMap.addActionListener(alChange);
		slideWidth.addChangeListener(widthChange);
		slideHeight.addChangeListener(heightChange);
		
		
		//setting up sizes to match everything
		paneMap.setPreferredSize(new Dimension(350, 150));
		buttonEditMap.setPreferredSize(new Dimension(350, 35));
		editorWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		editorWindow.setSize(450, 350);
		editorWindow.setResizable(false);
		
		//gets dimensions from screen and calculates center
		Dimension center = new Dimension((int)screenSize.getWidth()/2 - editorWindow.getWidth()/2, (int)screenSize.getHeight()/2 - editorWindow.getHeight()/2);
		
		editorWindow.setLocation((int)center.getWidth(), (int)center.getHeight());
		editorWindow.add(base);
		editorWindow.setVisible(true);
	}
	
	public void showLegend() {
		
		JFrame legend = new JFrame("Legende");
		JPanel base = new JPanel();
		JTextPane textLegend = new JTextPane();
		
		textLegend.setText(
					"LEGENDE\n"+
					"LEGENDE\n"+
					"LEGENDE\n"
				);
		
		base.add(textLegend);
		
		legend.add(base);
		
		legend.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		legend.setSize(450, 350);
		legend.setResizable(false);
		
		Dimension center = new Dimension((int)screenSize.getWidth()/2 - legend.getWidth()/2, (int)screenSize.getHeight()/2 - legend.getHeight()/2);
		
		legend.setLocation((int)center.getWidth(), (int)center.getHeight());
		legend.add(base);
		legend.setVisible(true);
		
	}

}
