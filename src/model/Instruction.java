package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Instruction {
	private boolean done;
	private String destinationRegister; //where the result will be stored
	private String firstOperandRegister; 
	private String secondOperandRegister; 
	private String operation; 
	private HashMap<String,ArrayList<Instruction>> dataDependencies; 
	/*maps "IO" to instructions which have an IO dependency with this instruction,
	 * similarly for other dataDependencies*/
	private int timeRemaining;
	private int duration;
	private int startTime;
	public static int addTime;
	public static int multTime;
	public static int iUTime;
	
	public Instruction(String o, String d, String i1, String i2){
		this.operation = o;
		this.destinationRegister = d;
		this.firstOperandRegister = i1;
		this.secondOperandRegister = i2;
		
		dataDependencies = new HashMap<String,ArrayList<Instruction>>();
		dataDependencies.put("IO", new ArrayList<Instruction>());
		dataDependencies.put("OI", new ArrayList<Instruction>());
		dataDependencies.put("OO", new ArrayList<Instruction>());
		
	}
	
	public boolean addDependency(String dependencyType,Instruction s){
		if(dependencyType.equals("IO") || dependencyType.equals("OI") || dependencyType.equals("OO")){
			dataDependencies.get(dependencyType).add(s);
			return true; //adding was successful
		}
		else{
			return false; //incorrect format for dependency type.
		}
	}
	
	public void decrementTimeRemaining(){
		timeRemaining--;
		if(timeRemaining == 0){
			done = true;
		}
	}

	public String getDestinationRegister() {
		return destinationRegister;
	}

	public String getFirstOperandRegister() {
		return firstOperandRegister;
	}

	public String getSecondOperandRegister() {
		return secondOperandRegister;
	}
	
	public ArrayList<Instruction> getIODependencies(){
		return dataDependencies.get("IO");
	}
	public ArrayList<Instruction> getOIDependencies(){
		return dataDependencies.get("OI");
	}
	public ArrayList<Instruction> getOODependencies(){
		return dataDependencies.get("OO");
	}

	public HashMap<String, ArrayList<Instruction>> getDataDependencies() {
		return dataDependencies;
	}
	
	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	public void setStartTime(int startTime) {
		this.startTime = startTime;

		if(operation.equals("ADD")){
			duration = iUTime + addTime;
		} else if(operation.equals("MULT")){
			duration = iUTime + multTime;
		}
		timeRemaining = duration;
	}

	public int getStartTime() {
		return startTime;
	}

	public String getOperation() {
		return operation;
	}


}
