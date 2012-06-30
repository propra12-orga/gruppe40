package main;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.awt.event.*;

public class MenuListener implements WindowListener {


	@Override public void windowClosing (WindowEvent e) {
	    UIManager.put("OptionPane.cancelButtonText", "Cancel");
	    UIManager.put("OptionPane.noButtonText", "No");
	    UIManager.put("OptionPane.yesButtonText", "Yes");
	    UIManager.put("OptionPane.Title","Please select");
	   
		int option = JOptionPane.showConfirmDialog(e.getWindow(), "Close window?","Exit",JOptionPane.YES_NO_OPTION);
		if(option == JOptionPane.OK_OPTION) System.exit(0);
	}
	
	@Override public void windowClosed(WindowEvent e) { }
	@Override public void windowDeiconified(WindowEvent e) { }
	@Override public void windowIconified(WindowEvent e) { }
	@Override public void windowActivated(WindowEvent e) { }
	@Override public void windowDeactivated(WindowEvent e) { }
	@Override public void windowOpened(WindowEvent e) { }
	
}
