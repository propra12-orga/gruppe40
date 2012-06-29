package main;

import javax.swing.*;
import java.io.*;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Tutorial {
	
	public Tutorial(String filepath) {
		final JFrame tutWindow = new JFrame("Controls");	
		JPanel base = new JPanel();
		JTextArea tutText = new JTextArea();
		
		String line = null, document = "";
		
		/********************
		 * reading textfile *
		 ********************/
		try {
			FileReader fileRead = new FileReader(this.getClass().getClassLoader().getResource("main/"+filepath).getPath());
			BufferedReader bufferRead = new BufferedReader(fileRead);
			while((line = bufferRead.readLine()) != null) {
				document = document + line + "\n";
			}
			//editing JTextArea
			tutText.setText(document);
			tutText.setEditable(false);
			tutText.setFont(new Font("Arial", Font.PLAIN, 24));
		}
		catch (IOException e) {
			tutText.setText("Datei nicht gefunden");
		}
		
		//gets dimensions from JTextArea (calculation based on font-size etc.)
		Dimension windowSize = tutText.getPreferredSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		base.add(tutText);
		
		//setting up popup size to match JTextArea
		tutWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		tutWindow.setSize(windowSize.width+20, windowSize.height+40);
		tutWindow.add(base);
		tutWindow.setVisible(true);
		tutWindow.setResizable(false);
		
		Dimension center = new Dimension((int)screenSize.getWidth()/2 - tutWindow.getWidth()/2, (int)screenSize.getHeight()/2 - tutWindow.getHeight()/2);
		tutWindow.setLocation((int)center.getWidth(), (int)center.getHeight());
	}

}
