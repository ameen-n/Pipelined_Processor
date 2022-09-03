package generic;
import java.io.*;
import processor.Clock;
import processor.Processor;
import generic.Statistics;

public class Simulator {
		
	static Processor processor;
	static boolean simulationComplete;

	
	public static void setupSimulation(String assemblyProgramFile, Processor p)
	{
		Simulator.processor = p;
		loadProgram(assemblyProgramFile);

		simulationComplete = false;
	}
	
	static void loadProgram(String assemblyProgramFile)
	{
		/*
		 * TODO
		 * 1. load the program into memory according to the program layout described
		 *    in the ISA specification
		 * 2. set PC to the address of the first instruction in the main
		 * 3. set the following registers:
		 *     x0 = 0
		 *     x1 = 65535
		 *     x2 = 65535
		 */
		final int mem = 65535;
		InputStream fInput = null;
		try {
			fInput = new FileInputStream(assemblyProgramFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream dInput = new DataInputStream(fInput);


		int n= 0;
		try {
			n = dInput.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		}
		int i=0;
			while(i<n){
				try {
					processor.getMainMemory().setWord(i,dInput.readInt());
				} catch (IOException e) {
					e.printStackTrace();
				}
				i++;
			}
			int pc = i;
			processor.getRegisterFile().setProgramCounter(pc);

			while(true){
				try {
					if (!(dInput.available() > 0)) break;
					processor.getMainMemory().setWord(i, dInput.readInt());
					i++;
				}
				catch(IOException e)	{
					e.printStackTrace();
				}
			}


			processor.getRegisterFile().setValue(0,0);
			processor.getRegisterFile().setValue(1,mem);
			processor.getRegisterFile().setValue(2,mem);

		try {
			dInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}


	}



	
	public static void simulate()
	{

		while(!simulationComplete)
		{
			processor.getIFUnit().performIF();
			Clock.incrementClock();
			processor.getOFUnit().performOF();
			Clock.incrementClock();
			processor.getEXUnit().performEX();
			Clock.incrementClock();
			processor.getMAUnit().performMA();
			Clock.incrementClock();
			processor.getRWUnit().performRW();
			Clock.incrementClock();
			Statistics.incrementNumberOfCycles();
			Statistics.incrementNumberOfInstructions();
		}
		
		// TODO
		// set statistics
		Statistics.setNumberOfCycles(Statistics.getNumberOfCycles());
		Statistics.setNumberOfInstructions(Statistics.getNumberOfInstructions());

	}

	public static void setSimulationComplete(boolean value)
	{
		simulationComplete = value;
	}
}
