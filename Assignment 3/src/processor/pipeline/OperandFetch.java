package processor.pipeline;


import processor.Processor;

public class OperandFetch {
	Processor containingProcessor;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
	}




    public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{

			//TODO
			//System.out.println("OF");
			String bin = Integer.toBinaryString(IF_OF_Latch.getInstruction());

			if(IF_OF_Latch.getInstruction() > 0)
				bin = String.format("%32s", Integer.toBinaryString(IF_OF_Latch.getInstruction())).replace(' ', '0');
			//System.out.println("Binary Instruction: " + bin);

			int inst = IF_OF_Latch.getInstruction();
			String imm = null, rs1 = null, rs2 = null, rd = null;
			//System.out.println(inst);
			String opcode = bin.substring(0, 5);
			int op = Integer.parseInt(opcode,2);
			OF_EX_Latch.setOpcode(opcode);

			if((op>=0)&(op<22))
			{
					if(op%2==0){	//R3
						rs1 = bin.substring(5, 10);
						rs2 = bin.substring(10, 15);
						rd = bin.substring(15, 20);
						OF_EX_Latch.setRs1(rs1);
						OF_EX_Latch.setRs2(rs2);
						OF_EX_Latch.setRd(rd);
					}
					else{		//R2I
						rs1 = bin.substring(5, 10);
						rd = bin.substring(10, 15);
						imm = bin.substring(15, 32);
						OF_EX_Latch.setImm(imm);
						OF_EX_Latch.setRs1(rs1);
						OF_EX_Latch.setRd(rd);
					}
			}
			else if(op == 24)	//RI
			{
				rd = bin.substring(5,10);
				imm = bin.substring(10, 32);

               // System.out.println("JUMP IMMEDIATE IN OF = "+ imm);
				OF_EX_Latch.setImm(imm);
				OF_EX_Latch.setRd(rd);
			}
			else	//R2I
			{
				rs1 = bin.substring(5,10);
				rd = bin.substring(10,15);
				imm = bin.substring(15, 32);
				OF_EX_Latch.setImm(imm);
				OF_EX_Latch.setRs1(rs1);
				OF_EX_Latch.setRd(rd);
			}




			IF_OF_Latch.setOF_enable(false);
			OF_EX_Latch.setEX_enable(true);
		}
	}

}
