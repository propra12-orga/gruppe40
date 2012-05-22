package main;

import javax.swing.JOptionPane;
import java.awt.event.*;

public class MenuListener implements WindowListener {

	@Override public void windowClosing (WindowEvent e) {
		int option = JOptionPane.showConfirmDialog(null, "Möchtest du das Fenster wirklich schließen?");
		if(option == JOptionPane.OK_OPTION) System.exit(0);
	}
	
	@Override public void windowClosed(WindowEvent e) { }
	@Override public void windowDeiconified(WindowEvent e) { }
	@Override public void windowIconified(WindowEvent e) { }
	@Override public void windowActivated(WindowEvent e) { }
	@Override public void windowDeactivated(WindowEvent e) { }
	@Override public void windowOpened(WindowEvent e) { }
	
}
