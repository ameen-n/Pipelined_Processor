package generic;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Statistics {
	
	static int numberOfInstructions;
	static int numberOfStaticInstructions;
	static long numberOfCycles;
	public static void setNumberOfCycles(long numberOfCycles) {
		Statistics.numberOfCycles = numberOfCycles;
	}
	public static void setNumberOfInstructions(int numberOfInstructions) {
		Statistics.numberOfInstructions = numberOfInstructions;
	}
	public static void setNumberOfStaticInstructions(int numberOfInstructions) {
		Statistics.numberOfStaticInstructions = numberOfInstructions;
	}
	public static double computeCPI(long numberOfCycles, int numberOfInstructions){
		double CPI = (double)numberOfCycles/numberOfInstructions;
		return CPI;
	}
	public static double computeIPC(){
		double IPC = 1/computeCPI(numberOfCycles, numberOfInstructions);
		return IPC;
	}


	public static void printStatistics(String statFile)
	{

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(statFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		writer.println("Number of Static Instructions = " + numberOfStaticInstructions);
			writer.println("Number of Dynamic Instructions = " + numberOfInstructions);
			writer.println("Number of Cycles = " + numberOfCycles);
			writer.println("IPC = " + computeIPC());
			writer.close();

	}



}
