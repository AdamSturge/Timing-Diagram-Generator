package gui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MachineBuilderPanel extends JPanel{

	private MainFrame mainFrame;
	private JTextField adderInput;
	private JTextField multiplierInput;
	private JTextField registerInput;
	private JTextField multTimeInput;
	private JTextField addTimeInput;
	private JTextField iUTimeInput;
	
	public MachineBuilderPanel(MainFrame m){
		
		this.mainFrame = m;
		this.setLayout(new GridLayout(0,2));
		
		adderInput = new JTextField();
		multiplierInput = new JTextField();
		registerInput = new JTextField();
		multTimeInput = new JTextField();
		addTimeInput = new JTextField();
		iUTimeInput = new JTextField();
		JLabel adderLabel = new JLabel("Enter number of adders");
		JLabel multiplyerLabel = new JLabel("Enter number of mulitpliers");
		JLabel registerLabel = new JLabel("Enter number of registers");
		JLabel addTimeLabel = new JLabel("Enter time it takes to add");
		JLabel multTimeLabel = new JLabel("Enter time it takes to multipy");
		JLabel iUTimeLabel = new JLabel("Enter duration of the IU stage");
		
		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new SubmitListener());
		
		
		this.add(adderLabel);
		this.add(adderInput);
		this.add(multiplyerLabel);
		this.add(multiplierInput);
		this.add(registerLabel);
		this.add(registerInput);
		this.add(addTimeLabel);
		this.add(addTimeInput);
		this.add(multTimeLabel);
		this.add(multTimeInput);
		this.add(iUTimeLabel);
		this.add(iUTimeInput);
		this.add(submitButton);
		
	}
	
	private class SubmitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int numOfAdders = Integer.parseInt(adderInput.getText());
			int numOfMultipliers = Integer.parseInt(multiplierInput.getText());
			int numOfRegisters = Integer.parseInt(registerInput.getText());
			int addTime = Integer.parseInt(addTimeInput.getText());
			int multTime = Integer.parseInt(multTimeInput.getText());
			int iUTime = Integer.parseInt(iUTimeInput.getText());
			
			mainFrame.setNumOfAdders(numOfAdders);
			mainFrame.setNumOfMultipliers(numOfMultipliers);
			mainFrame.setNumOfRegisters(numOfRegisters);
			mainFrame.setAddTime(addTime);
			mainFrame.setMultTime(multTime);
			mainFrame.setIUTime(iUTime);
			
			mainFrame.showProgramBuilderPanel();
			
			}
		
	}
}
