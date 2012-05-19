import javax.swing.*;
import java.io.*;
import java.awt.Font;
import java.awt.Dimension;

public class Tutorial {
	
	public Tutorial() {
		JFrame tutWindow = new JFrame("Tutorial");		
		JTextArea tutText = new JTextArea();
		
		String line = null, document = "";
		
		/********************
		 * reading textfile *
		 ********************/
		
		try {
			//Filereader will noch nicht (klappt bei mir nur mit komplettem Pfad...)
			FileReader fileRead = new FileReader("\\tutorialtext.txt");
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
		
		//class popup is protected, so we use a factory to obtain instances
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup tutorial = factory.getPopup(tutWindow, tutText, 10, 30);
		tutorial.show();
		
		//gets dimensions from JTextArea (calculation based on font-size etc.)
		Dimension windowSize = tutText.getPreferredSize();
		
		//setting popup size to match JTextArea
		tutWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		tutWindow.setSize(windowSize.width+20, windowSize.height+40);
		tutWindow.setVisible(true);
	}

}
