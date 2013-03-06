package logic;

import java.util.ArrayList;

import model.Instruction;

public class InstructionController {

	//finds the IO,OI,and OO dependencies for a list of instructions
	public void findDataDependencies(ArrayList<Instruction> instructions){
		
		Instruction i1;
		Instruction i2; //instruction to which i1 will be checked against for dependencies
		String i1Operand1;
		String i1Operand2;
		String i1Destination;
		String i2Operand1;
		String i2Operand2;
		String i2Destination;
		
		for(int i = 0; i < instructions.size(); ++i){
			i1 = instructions.get(i); 
			i1Operand1 = i1.getFirstOperandRegister();
			i1Operand2 = i1.getSecondOperandRegister();
			i1Destination = i1.getDestinationRegister();
			for(int j = i+1; j <instructions.size(); ++j){
				i2 = instructions.get(j);
				i2Operand1 = i2.getFirstOperandRegister();
				i2Operand2 = i2.getSecondOperandRegister();
				i2Destination = i2.getDestinationRegister();
				
				if(i1Operand1.equals(i2Destination) || i1Operand2.equals(i2Destination)){
					//IO dependency detected
					i2.addDependency("IO", i1);
				}
				if(i1Destination.equals(i2Operand1) || i1Destination.equals(i2Operand2)){
					//OI dependency detected
					i2.addDependency("OI", i1);
				}
				if(i1Destination.equals(i2Destination)){
					//OO dependency detected
					i2.addDependency("OO", i1);
				}
			}
		}
	}
}
