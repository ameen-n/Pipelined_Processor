package processor.pipeline;

import processor.Processor;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;




	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
	}


	public void performMA()
	{

		//System.out.println("MA");
		if(EX_MA_Latch.isMA_enable())
		{
			String opcode = EX_MA_Latch.getOpcode();
			String rs1 = EX_MA_Latch.getRs1();
			String rs2 = EX_MA_Latch.getRs2();
			String rd = EX_MA_Latch.getRd();
			String imm = EX_MA_Latch.getImm();
			int result = EX_MA_Latch.getResult();
			//System.out.println("result in MA = " + result);
			MA_RW_Latch.setOpcode(opcode);
			MA_RW_Latch.setRs1(rs1);
			MA_RW_Latch.setRs2(rs2);
			MA_RW_Latch.setRd(rd);
			MA_RW_Latch.setImm(imm);
			MA_RW_Latch.setResult(result);

			if(EX_MA_Latch.getsflag()==1)
			{
				EX_MA_Latch.resetsflag();
				containingProcessor.getMainMemory().setWord(EX_MA_Latch.getAddress(), EX_MA_Latch.getResult());
			}
		}
		MA_RW_Latch.setResult(EX_MA_Latch.getResult());
		EX_MA_Latch.setMA_enable(false);
		MA_RW_Latch.setRW_enable(true);

	}

}
