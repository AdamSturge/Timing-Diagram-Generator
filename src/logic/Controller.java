package logic;

import gui.MainFrame;
import gui.TimingDiagramFrame;

import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Instruction;

public class Controller {

	private InstructionController instructionController;
	private ArrayList<Instruction> instructions;
	private MainFrame mainFrame;
	private int addTime;
	private int multTime;
	private int iUTime;
	private int numOfAdders;
	private int numOfMultipliers;

	public Controller(){
		instructionController = new InstructionController();
		instructions = new ArrayList<Instruction>();
	}

	public void setMainFrame(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	public void GenerateGraph(ArrayList<String> operations, ArrayList<String> destinations,
			ArrayList<String> firstOperands, ArrayList<String> secondOperands){

		for(int i = 0; i < operations.size(); ++i){
			instructions.add(new Instruction(operations.get(i), destinations.get(i), 
					firstOperands.get(i), secondOperands.get(i)));
		}

		addTime = mainFrame.getAddTime();
		multTime = mainFrame.getMultTime();
		iUTime = mainFrame.getiUTime();
		numOfAdders = mainFrame.getNumOfAdders();
		numOfMultipliers = mainFrame.getNumOfMultipliers();
		Instruction.addTime = addTime;
		Instruction.multTime = multTime;
		Instruction.iUTime = iUTime;

		instructionController.findDataDependencies(instructions);
		simulateMachine(instructions);

		TimingDiagramFrame timingDiagram = new TimingDiagramFrame(instructions);
		timingDiagram.pack();
		timingDiagram.setVisible(true);
	
	}

	private void simulateMachine(ArrayList<Instruction> instructions){

		int time = 0; //current time slice for the machine
		int activeAdders = 0;
		int activeMultipliers = 0;
		Instruction currentInstruction; //instruction we are checking if we can issue
		ArrayList<Instruction> currentIODependencies; 
		ArrayList<Instruction> currentOIDependencies;
		ArrayList<Instruction> currentOODependencies;
		Instruction s; //variable used to loop backwards through instructions from current instruction 
		String currentOperation;
		for(int i = 0; i < instructions.size(); ++i) {
			currentInstruction = instructions.get(i);
			currentOperation = currentInstruction.getOperation();
			currentIODependencies = currentInstruction.getIODependencies();
			currentOIDependencies = currentInstruction.getOIDependencies();
			currentOODependencies = currentInstruction.getOODependencies();

			//first check for data dependencies and wait for the bottleneck to clear if any are found
			for(int j = i-1; j >= 0; j--){
				s = instructions.get(j);
				if(currentIODependencies.contains(s) || currentOIDependencies.contains(s) 
						|| currentOODependencies.contains(s)){
					while(!s.isDone()){
						//waiting for bottleneck to clear 
						for(int k = i; k >= 0; k--){
							instructions.get(k).decrementTimeRemaining();
						}
						time++;
					}
				}
			}
			//Now check to see if there is a free DUnit, two cases. One for ADD, one for MULT
			if(currentOperation.equals("ADD")){
				if(activeAdders < numOfAdders){
					activeAdders++; //reserve an adder for the current instruction
				}
				else{
					//there are no free adders, move the all the previous instructions ahead until an adder is freed up
					while(activeAdders == numOfAdders){
						for(int k = i-1; k >=0; k--){
							s = instructions.get(k);
							s.decrementTimeRemaining();
							if(s.isDone()){
								if(s.getOperation().equals("ADD")){
									activeAdders--;
								}else if(s.getOperation().equals("MULT")){
									activeMultipliers--;
								}
							}
						}
						time++; //increment time after all instructions have moved forward a step
					}
					activeAdders++; //reserve an adder for the current instruction
				}
			}if(currentOperation.equals("MULT")){
				if(activeMultipliers < numOfMultipliers){
					activeMultipliers++;
				}else{
					while(activeMultipliers == numOfMultipliers){
						for(int k = i-2; k >=0; k--){
							s = instructions.get(k);
							s.decrementTimeRemaining();
							if(s.isDone()){
								if(s.getOperation().equals("ADD")){
									activeAdders--;
								}else if(s.getOperation().equals("MULT")){
									activeMultipliers--;
								}
							}
						}
						time++;
					}
					activeMultipliers++;
				}
			}
			currentInstruction.setStartTime(time); //current instruction can start at this time
			currentInstruction.setDone(false); //current instruction is not done, it just started

			//end of the cycle, deduce remaining time on all issued instructions by 1 and increment the time
			for(int k = i; k >=0; k--){
				s = instructions.get(k);
				s.decrementTimeRemaining();
				if(s.isDone()){
					if(s.getOperation().equals("ADD")){
						activeAdders--;
					}else if(s.getOperation().equals("MULT")){
						activeMultipliers--;
					}
				}
			}
			time++;

		}
	}
}
