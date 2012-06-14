package main;

import javax.swing.*;
import java.io.*;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.Toolkit;

public class Tutorial {
	
	public Tutorial(String filepath) {
		JFrame tutWindow = new JFrame("Tutorial");		
		JTextArea tutText = new JTextArea();
		
		String line = null, document = "";
		
		/********************
		 * reading textfile *
		 ********************/
		try {
			FileReader fileRead = new FileReader(System.getProperty("user.dir")+"\\bin\\main\\"+filepath);
			BufferedReader bufferRead = new BufferedReader(fileRead);
			while((line = bufferRead.readLine()) != null) {
				document = document + line + "\n";
			}
			//editing JTextArea
			tutText.setText(document);
			tutText.setEditable(false);
			tutText.setFont(new Font("Arial", Font.PLAIN, 32));
		}
		catch (IOException e) {
			tutText.setText("Datei nicht gefunden");
		}
		finally {
		//	bufferRead.close();
		}
		
		//gets dimensions from JTextArea (calculation based on font-size etc.)
		Dimension windowSize = tutText.getPreferredSize();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//setting popup size to match JTextArea
		tutWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		tutWindow.setSize(windowSize.width+20, windowSize.height+40);
		tutWindow.setVisible(true);
		
		Dimension center = new Dimension((int)screenSize.getWidth()/2 - tutWindow.getWidth()/2, (int)screenSize.getHeight()/2 - tutWindow.getHeight()/2);
		tutWindow.setLocation((int)center.getWidth(), (int)center.getHeight());
		
		//class popup is protected, so we use a factory to obtain instances
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup tutorial = factory.getPopup(tutWindow, tutText, (int)center.getWidth()+10, (int)center.getHeight()+30);
		tutorial.show();
	}

}
