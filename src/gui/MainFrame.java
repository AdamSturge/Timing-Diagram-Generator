package gui;

import java.awt.CardLayout;
import java.util.ArrayList;

import javax.swing.JFrame;

import logic.Controller;

public class MainFrame extends JFrame{
	
	private int numOfAdders;
	private int numOfMultipliers;
	private int numOfRegisters;
	private int addTime;
	private int multTime;
	private int iUTime;
	private CardLayout cardLayout;
	private MachineBuilderPanel machineBuilderPanel;
	private ProgramBuilderPanel programBuilderPanel;
	private final String MACHINE_CARD = "Machine Builder Panel";
	private final String PROGRAM_CARD = "Program Builder Panel";
	
	public MainFrame(Controller controller){
		
		cardLayout = new CardLayout();
		setLayout(cardLayout);
		
		machineBuilderPanel = new MachineBuilderPanel(this);
		add(machineBuilderPanel, MACHINE_CARD);
		
		programBuilderPanel = new ProgramBuilderPanel(this, controller);
		add(programBuilderPanel,PROGRAM_CARD);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Timing Diagram Creator");
		setResizable(true);
		pack();
		setVisible(true);
		
		showMachineBuilderPanel();
	}
	
	public void showMachineBuilderPanel(){
		cardLayout.show(this.getContentPane(), MACHINE_CARD);
	
	}
	
	public void showProgramBuilderPanel(){
		cardLayout.show(this.getContentPane(), PROGRAM_CARD);
	
	}
	
	public int getNumOfAdders() {
		return numOfAdders;
	}

	public void setNumOfAdders(int numOfAdders) {
		this.numOfAdders = numOfAdders;
	}

	public int getNumOfMultipliers() {
		return numOfMultipliers;
	}

	public void setNumOfMultipliers(int numOfMultipliers) {
		this.numOfMultipliers = numOfMultipliers;
	}

	public int getNumOfRegisters() {
		return numOfRegisters;
	}

	public void setNumOfRegisters(int numOfRegisters) {
		this.numOfRegisters = numOfRegisters;
	}
	
	public ArrayList<String> getOperations(){
		return programBuilderPanel.getOperations();
	}
	public ArrayList<String> getDestinations(){
		return programBuilderPanel.getDestinations();
	}
	public ArrayList<String> getFirstOperands(){
		return programBuilderPanel.getFirstOperands();
	}
	public ArrayList<String> getSecondOperands(){
		return programBuilderPanel.getSecondOperands();
	}

	public void setAddTime(int addTime) {
		this.addTime = addTime;
		
	}

	public void setMultTime(int multTime) {
		this.multTime = multTime;
		
	}

	public void setIUTime(int iUTime) {
		this.iUTime = iUTime;
		
	}

	public int getiUTime() {
		return iUTime;
	}

	public void setiUTime(int iUTime) {
		this.iUTime = iUTime;
	}

	public int getAddTime() {
		return addTime;
	}

	public int getMultTime() {
		return multTime;
	}
	

}
