package main;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MapEditor {
	
	static final int mapSize_MIN = 5;
	static final int mapSize_MAX = 25;
	static final int mapSize_STANDARD = 13;
	boolean mapModified;
	File mapFile;
	int height, width;
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	public MapEditor() {
		//panels etc...
		final JFrame editorWindow = new JFrame("Karteneditor");
		JPanel base = new JPanel();
		JPanel slideLabels = new JPanel();
		JPanel sliders = new JPanel();
		JPanel loadsave = new JPanel();
		JLabel labelWidth = new JLabel("Kartenbreite               ");
		JLabel labelHeight = new JLabel("               Kartenhoehe");
		JSlider slideWidth = new JSlider(JSlider.HORIZONTAL, mapSize_MIN, mapSize_MAX, mapSize_STANDARD);
		JSlider slideHeight = new JSlider(JSlider.HORIZONTAL, mapSize_MIN, mapSize_MAX, mapSize_STANDARD);
		final JButton buttonEditMap = new JButton("Karte bearbeiten");
		final JButton buttonSaveMap = new JButton("Karte speichern");
		final JButton buttonLoadMap = new JButton("Karte laden");
		final JTextPane paneMap = new JTextPane();
		final JFileChooser fc = new JFileChooser();
		JScrollPane scrollMap = new JScrollPane(paneMap);
		
		base.setLayout(new FlowLayout());
		sliders.setLayout(new FlowLayout());
		
		slideLabels.add(labelWidth);
		slideLabels.add(labelHeight);
		
		sliders.add(slideWidth);
		sliders.add(slideHeight);
		
		loadsave.add(buttonLoadMap);
		loadsave.add(buttonSaveMap);
		
		base.add(scrollMap);
		base.add(buttonEditMap);
		base.add(slideLabels);
		base.add(sliders);
		base.add(loadsave);
		
		//editing Sliders
		slideWidth.setMinorTickSpacing(1);
		slideWidth.setMajorTickSpacing(5);
		slideWidth.setSnapToTicks(true);
		slideWidth.setPaintLabels(true);
		slideWidth.setPaintTicks(true);
		
		slideHeight.setMinorTickSpacing(1);
		slideHeight.setMajorTickSpacing(5);
		slideHeight.setSnapToTicks(true);
		slideHeight.setPaintLabels(true);
		slideHeight.setPaintTicks(true);
		
		buttonSaveMap.setEnabled(false);

		//editing JTextPane
		paneMap.setText("Hier Karte eintragen\n z.B.:\n0 0 0\n0 1 0\n0 0 0");
		paneMap.setEditable(false);
		paneMap.setMargin(new Insets(10, 10, 10, 10));
		
		//edit map
		ActionListener alChange = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if(paneMap.isEditable()) {
					mapModified = true;
					buttonSaveMap.setEnabled(true);
				}
				else {
					paneMap.setText("");
					paneMap.setEditable(true);
					showLegend();
					buttonEditMap.setText("Karte bestaetigen");
				}
			}
		};
		
		//listener for slider (width)
		ChangeListener widthChange = new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {

					width = source.getValue();
				}
			}
		};
		
		//listener for slider (height)
		ChangeListener heightChange = new ChangeListener() {
			@Override public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider)e.getSource();
				if(!source.getValueIsAdjusting()) {
					height = source.getValue();
				}
				
			}
		};
		
		ActionListener alLoadMap = new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {		
				int returnVal = fc.showOpenDialog(editorWindow);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					//make sure user read a .xml file
					if(fc.getSelectedFile().getName().toLowerCase().endsWith(".xml")) {
						mapFile = fc.getSelectedFile();
						buttonLoadMap.setText("Karte laden");
					}
					//file isn't a .xml file
					else {
						buttonLoadMap.setText("falsches Dateiformat");
					}
				}
			}
		};
		
		//adding actionlisteners
		buttonEditMap.addActionListener(alChange);
		slideWidth.addChangeListener(widthChange);
		slideHeight.addChangeListener(heightChange);
		buttonLoadMap.addActionListener(alLoadMap);
		
		
		//setting up sizes to match everything
		paneMap.setPreferredSize(new Dimension(350, 150));
		buttonEditMap.setPreferredSize(new Dimension(350, 35));
		buttonLoadMap.setPreferredSize(new Dimension(175, 25));
		buttonSaveMap.setPreferredSize(new Dimension(175, 25));
		editorWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		editorWindow.setSize(450, 355);
		editorWindow.setResizable(false);
		
		//gets dimensions from screen and calculates center
		Dimension center = new Dimension((int)screenSize.getWidth()/2 - editorWindow.getWidth()/2, (int)screenSize.getHeight()/2 - editorWindow.getHeight()/2);
		
		editorWindow.setLocation((int)center.getWidth(), (int)center.getHeight());
		editorWindow.add(base);
		editorWindow.setVisible(true);
	}
	
	//map legend - new window
	public void showLegend() {
		
		JFrame legend = new JFrame("Legende");
		JPanel base = new JPanel();
		JTextPane textLegend = new JTextPane();
		
		textLegend.setText(
				"BEISPIELTEXT!!\n"+	
				"0 = Unzerstoerbare Mauer\n"+
				"1 = Spieler\n"+
				"2 = Wuff\n"
			);
		
		textLegend.setEditable(false);
		
		base.add(textLegend);
		
		legend.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		legend.setSize(300, 250);
		legend.setResizable(false);
		legend.add(base);
		legend.setLocation(10, 10);
		legend.setVisible(true);
		
	}

}
