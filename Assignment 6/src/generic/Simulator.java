package generic;

import java.io.*;
import processor.*;
import processor.Processor;
import processor.memorysystem.*;
import processor.pipeline.*;


public class Simulator {
	
	static MainMemory newMemory = new MainMemory();
	static RegisterFile rf = new RegisterFile();
	static FileInputStream fis = null;
	static DataInputStream dis = null;
	static int number_of_instructions = 0;
	static int number_of_dynamic_instructions = 0;
	static Processor processor;
	static boolean simulationComplete;
	static EventQueue eventQueue = new EventQueue();


	public static void setupSimulation(String assemblyProgramFile, Processor proc)
	{
		Simulator.processor = proc;
		loadProgram(assemblyProgramFile);
		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
		int index = 0;
		int firstCodeAddress=0;

		try {
			fis = new FileInputStream(assemblyProgramFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		dis = new DataInputStream(fis);
		try {
			if (dis.available() > 0) {
				do {
					int instruction = dis.readInt();
					if (index == 0) {
						firstCodeAddress = instruction;
						index++;
						continue;
					} else {
						if (index >= firstCodeAddress) {
							number_of_instructions++;
						}
						newMemory.memory[index - 1] = instruction;
						index++;
					}
				} while (dis.available() > 0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}


		try {
			dis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


		rf.setProgramCounter(firstCodeAddress);
		rf.setValue(0,0);
		for(int i=3;i<31;i++)
		{
			rf.setValue(i,0);
		}
		rf.setValue(1,65535);
		rf.setValue(2,65535);

		processor.setMainMemory(newMemory);
		processor.setRegisterFile(rf);
	}
	
	public static void simulate()
	{
		while(!simulationComplete)
		{
			eventQueue.processEvents();
			processor.getRWUnit().performRW();
			processor.getMAUnit().performMA();
			processor.getEXUnit().performEX();
			processor.getOFUnit().performOF();
			processor.getIFUnit().performIF();
			Clock.incrementClock();
		}
	}
	public static void increment_dynamic_instructions()
	{
		number_of_dynamic_instructions++;
	}
	public static void set_stats()
	{
		Statistics.setNumberOfCycles(Clock.getCurrentTime());
		Statistics.setNumberOfStaticInstructions(number_of_instructions);
		Statistics.setNumberOfInstructions(number_of_dynamic_instructions);

	}
	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
	public static EventQueue getEventQueue()
	{ 
		return eventQueue;
	}


}
