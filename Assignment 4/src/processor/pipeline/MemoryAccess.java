package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;

public class MemoryAccess {
	Processor containingProcessor;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	IF_EnableLatchType IF_EnableLatch;

	public MemoryAccess(Processor containingProcessor, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.containingProcessor = containingProcessor;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}

	public void performMA() {
		if (EX_MA_Latch.getIsNOP()) {
              boolean setIsNOP_check_1=true;

			MA_RW_Latch.setIsNOP(setIsNOP_check_1);

			MA_RW_Latch.setInstruction(null);

			boolean setIsNOP_check_2=false;
			   
			EX_MA_Latch.setIsNOP(setIsNOP_check_2);
		} else if (EX_MA_Latch.isMA_enable()) {
			int alu_result;
			alu_result = EX_MA_Latch.getALU_result();
			System.out.println("MA is enabled: " + EX_MA_Latch.getInstruction());
			MA_RW_Latch.setALU_result(alu_result);
			OperationType op_type = EX_MA_Latch.getInstruction().getOperationType();
			switch (op_type) {
			case store:
				int number_that_is_to_be_stored;
				number_that_is_to_be_stored = containingProcessor.getRegisterFile()
						.getValue(EX_MA_Latch.getInstruction().getSourceOperand1().getValue());
				containingProcessor.getMainMemory().setWord(alu_result, number_that_is_to_be_stored);
				break;

			case load:
				int load_result;

				load_result = containingProcessor.getMainMemory().getWord(alu_result);
				MA_RW_Latch.setLoad_result(load_result);
				break;

			default:
				break;
			}
			
			if (EX_MA_Latch.getInstruction().getOperationType().ordinal() == 29) {
				boolean check=false;
				IF_EnableLatch.setIF_enable(check);
			} 
			MA_RW_Latch.setInstruction(EX_MA_Latch.getInstruction());
			boolean setRW_enable_check=true;
			MA_RW_Latch.setRW_enable(setRW_enable_check);
			
		}
	}

}
