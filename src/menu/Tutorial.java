import javax.swing.*;
import java.io.*;
import java.awt.Font;
import java.awt.Dimension;

public class Tutorial {
	
	public Tutorial() {
		JFrame tutWindow = new JFrame("Tutorial");
		
		tutWindow.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
		JTextArea tutText = new JTextArea();
		
		String line = null, document = "";
		
		try {
			//Filereader will noch nicht (klappt momentan nur mit komplettem Pfad...)
			FileReader fileRead = new FileReader("\\tutorialtext.txt");
			BufferedReader bufferRead = new BufferedReader(fileRead);
			while((line = bufferRead.readLine()) != null) {
				document = document + line + "\n";
			}
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
		
		PopupFactory factory = PopupFactory.getSharedInstance();
		final Popup tutorial = factory.getPopup(tutWindow, tutText, 10, 30);
		tutorial.show();
		
		Dimension windowSize = tutText.getPreferredSize();
		
		tutWindow.setSize(windowSize.width+20, windowSize.height+40);
		tutWindow.setVisible(true);
	}

}
