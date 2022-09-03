package processor.pipeline;

import generic.Simulator;
import processor.Processor;

public class RegisterWrite {
	Processor containingProcessor;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;



	
	public RegisterWrite(Processor containingProcessor, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}
	
	public void performRW()
	{

		//System.out.println("RW");
		if(MA_RW_Latch.isRW_enable())
		{
			String opcode = MA_RW_Latch.getOpcode();

			if(opcode.equals("11101")) //end
			{
				//System.out.println("PC count: " + containingProcessor.getRegisterFile().getProgramCounter());
				Simulator.setSimulationComplete(true);

			}
			else if(opcode.equals("10110")) //load
			{
				int loadThis= MA_RW_Latch.getResult();
				String rd = MA_RW_Latch.getRd();
				int rdi = Integer.parseInt(rd, 2);
				containingProcessor.getRegisterFile().setValue(rdi, loadThis);

			}
			else if(opcode.equals("10111") || opcode.equals("11000") || opcode.equals("11001") || opcode.equals("11011") || opcode.equals("11100"))
			{

			}
			else
			{
				String rd = MA_RW_Latch.getRd();
				int rdi = Integer.parseInt(rd, 2);
				//System.out.println( rdi+ " w/ result "+ MA_RW_Latch.getResult() );
				containingProcessor.getRegisterFile().setValue(rdi, MA_RW_Latch.getResult() );



			}
			
			MA_RW_Latch.setRW_enable(false);
			IF_EnableLatch.setIF_enable(true);
		}
	}

}
