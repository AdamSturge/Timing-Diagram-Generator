package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.Controller;

public class ProgramBuilderPanel extends JPanel{
	
	private MainFrame mainFrame;
	private Controller controller;
	private JPanel programPanel; //panel where the user specifies their program
	private JPanel optionPanel; //panel where the user specifies number of instructions
	private JTextField optionField; //where number of instructions is input by user
	private ArrayList<String> operations; //ordered list of operations for the instructions specified by the user
	private ArrayList<String> destinations; //ordered list of destinations for the instructions specified by the user
	private ArrayList<String> firstOperands; //ordered list of first operands for the instructions specified by the user
	private ArrayList<String> secondOperands; //ordered list of second operands for the instructions specified by the user
	private ArrayList<JComboBox> boxes; //list of all the combo boxes used
	
	public ProgramBuilderPanel(MainFrame m, Controller c){
		mainFrame = m;
		controller = c;
		operations = new ArrayList<String>();
		destinations = new ArrayList<String>();
		firstOperands = new ArrayList<String>();
		secondOperands = new ArrayList<String>();
		boxes = new ArrayList<JComboBox>();
		
		programPanel = new JPanel();
		programPanel.setLayout(new GridLayout(0,5));
		optionPanel = new JPanel();
		optionPanel.setLayout(new GridLayout(1,3));
		
		JLabel optionLabel = new JLabel("Specify number of instructions for your program");
		optionField = new JTextField();
		JButton optionButton = new JButton("Create Program Template");
		optionButton.addActionListener(new OptionListener());
		
		optionPanel.add(optionLabel);
		optionPanel.add(optionField);
		optionPanel.add(optionButton);
		
		add(optionPanel, BorderLayout.NORTH);
		add(programPanel,BorderLayout.CENTER);
		
	}
	
	private class OptionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			generateProgramGUI(Integer.parseInt(optionField.getText()));
			
		}
	}
	
	private void generateProgramGUI(int numOfInstructions) {
		
		programPanel.removeAll(); //remove old program template;
		boxes = new ArrayList<JComboBox>(); //remove list of old boxes
		
		String[] operations = {"ADD","MULT"};
		int numOfRegisters = mainFrame.getNumOfRegisters();
		String[] registers = new String[numOfRegisters];
		String registerName = "";
		for(int i = 1; i <= numOfRegisters ; ++i){
			registerName = "R" + i;
			registers[i-1] = registerName;
		}
		
		JLabel instructionLabel; //used to indicate instruction number to user
		String instructionName = "";
		JComboBox operationBox;
		JComboBox destinationBox;
		JComboBox firstOperandBox;
		JComboBox secondOperandBox;
		for(int i = 1; i <= numOfInstructions; ++i){
			instructionName = "Instruction" + i;
			instructionLabel = new JLabel(instructionName);
			
			operationBox = new JComboBox(operations);
			destinationBox = new JComboBox(registers);
			firstOperandBox = new JComboBox(registers);
			secondOperandBox = new JComboBox(registers);
			
			boxes.add(operationBox);
			boxes.add(destinationBox);
			boxes.add(firstOperandBox);
			boxes.add(secondOperandBox);
			
			programPanel.add(instructionLabel);
			programPanel.add(operationBox);
			programPanel.add(destinationBox);
			programPanel.add(firstOperandBox);
			programPanel.add(secondOperandBox);
			
			instructionName = "";
		}
		
		JButton submit = new JButton("Generate Timing Diagram");
		submit.addActionListener(new TimingDiagramListener());
		programPanel.add(submit);
		programPanel.validate();
		programPanel.updateUI();
	}
	
	private class TimingDiagramListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			JComboBox operationBox; 
			JComboBox destinationBox;
			JComboBox firstOperandBox; 
			JComboBox secondOperandBox;
			String operation;
			String destination;
			String firstOperand;
			String secondOperand;
			for(int i = 0; i < boxes.size(); i+=4){
				operationBox = boxes.get(i);
				destinationBox = boxes.get(i+1);
				firstOperandBox = boxes.get(i+2);
				secondOperandBox = boxes.get(i+3);
				
				operation = (String) operationBox.getSelectedItem();
				destination = (String) destinationBox.getSelectedItem();
				firstOperand = (String) firstOperandBox.getSelectedItem();
				secondOperand = (String) secondOperandBox.getSelectedItem();
				
				operations.add(operation);
				destinations.add(destination);
				firstOperands.add(firstOperand);
				secondOperands.add(secondOperand);
			}
			controller.GenerateGraph(operations, destinations, firstOperands, secondOperands);
		}
		
	}
	
	public ArrayList<String> getOperations(){
		return operations;
	}
	public ArrayList<String> getDestinations(){
		return destinations;
	}
	public ArrayList<String> getFirstOperands(){
		return firstOperands;
	}
	public ArrayList<String> getSecondOperands(){
		return secondOperands;
	}
}
