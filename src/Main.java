import gui.MainFrame;

import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Controller;
import logic.InstructionController;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Controller controller = new Controller();
		MainFrame mainFrame = new MainFrame(controller);
		controller.setMainFrame(mainFrame);
		
	}

	
	
}
