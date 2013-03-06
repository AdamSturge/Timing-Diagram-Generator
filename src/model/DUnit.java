package model;

public class DUnit {
	private int computationTime; 
	private int counter; //tracks how much longer this DUnit will be in use
	private boolean isFree; 
	private Instruction currentInstruction;
	
	public DUnit(int c){
		computationTime = c;
		counter = 0;
		isFree = true;
	}
	
	public void compute() throws NullPointerException{
		if(currentInstruction == null){
			throw new NullPointerException("There is no instruction in this DUnit");
		}
		counter++;
		if(counter == computationTime){
			counter = 0;
			isFree = true;
			currentInstruction = null;
		}
	}

	public boolean isFree() {
		return isFree;
	}

	public void setCurrentInstruction(Instruction s) throws Exception {
		if(isFree == true){
			currentInstruction = s;
			isFree = false;
		}
		else{
			throw new Exception("This DUnit is already executing an instruction");
		}
		
	}
	
}
