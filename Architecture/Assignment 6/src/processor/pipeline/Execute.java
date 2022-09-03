package processor.pipeline;

import processor.Processor;
import generic.*;
import java.math.BigInteger;
public class Execute 
{
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_IF_LatchType EX_IF_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public Execute(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_IF_LatchType eX_IF_Latch, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
	}
    public int returnOFEXlatchfirstoperand(){
		int returnvalue;
		returnvalue=OF_EX_Latch.getop1();
		return returnvalue;
	}
	public int returnOFEXlatchsecondoperand(){
		int returnvalue;
		returnvalue=OF_EX_Latch.getop2();
		return returnvalue;
	}
	public int returnOFEXlatchimmediate(){
		int returnvalue;
		returnvalue=OF_EX_Latch.getimm();
		return returnvalue;
	}
	public int returnOFEXlatchdestinationregister(){
		int returnvalue;
		returnvalue=OF_EX_Latch.getrd();
		return returnvalue;
	}
	public int returnOFEXlatchbranchtarget(){
		int returnvalue;
		returnvalue=OF_EX_Latch.getbranchtarget_conditional();
		return returnvalue;
	}
	public int returnOFEXlatchbranchtargetjmp(){
		int returnvalue;
		returnvalue=OF_EX_Latch.getbranchtarget_jmp();
		return returnvalue;
	}

	public int initialaluresultvalue()
	{
		int returnvalue;
		returnvalue=0;
		return returnvalue;
	}
	public void setOFEXlatchisr3tofalse(){
		boolean setvalue;
		setvalue=false;
		OF_EX_Latch.is_r3 =setvalue;
	}

	public void IFenablesetwrongbranchtaken(){
		boolean set;
		set=true;
		IF_EnableLatch.set_wrong_branch_taken(set);
	}
     public void setOFEXlatchisconditionalr2itofalse(){
		boolean set;
		set=false;
	  OF_EX_Latch.is_r2i_conditional = set;
      }
	 public void setOFEXlatchisnonconditionalr2itofalse(){
		boolean set;
		set=false;
		OF_EX_Latch.is_r2i_non_conditional = set;
	}
      public void setOFEXlatchisloadstoretofalse(){
		boolean set;
		set=false;
	 OF_EX_Latch.is_load_store = set;
       }
      public void EXMAlatchsetMAenabletotrue(){
		boolean value;
		value=true;
	   EX_MA_Latch.setMA_enable(value);
         }

       public boolean OFEXlatchisr3()
      {
	boolean value;
	value=OF_EX_Latch.is_r3 ;
	return value;
      }


	public boolean OFEXlatchisr2inonconditional()
	{
		boolean value;
		value=OF_EX_Latch.is_r2i_non_conditional ;
		return value;
	}
	public boolean OFEXlatchisloadstore()
	{
		boolean value;
		value=OF_EX_Latch.is_load_store ;
		return value;
	}
	public boolean OFEXlatchisr2iconditional()
	{
		boolean value;
		value=OF_EX_Latch.is_r2i_conditional ;
		return value;
	}
	public boolean OFEXlatchisjmp()
	{
		boolean value;
		value=OF_EX_Latch.is_jmp ;
		return value;
	}
	public boolean OFEXlatchisend()
	{
		boolean value;
		value=OF_EX_Latch.is_end;
		return value;
	}
	public void performEX()
	{

		if(OF_EX_Latch.isEX_enable())
		{

			Instruction.OperationType optype ;
			optype = OF_EX_Latch.getoptype();
			int op1;
			op1 = returnOFEXlatchfirstoperand();
			int op2;
			op2 = returnOFEXlatchsecondoperand();
			int imm;
			imm = returnOFEXlatchimmediate();
			int rd;
			rd = returnOFEXlatchdestinationregister();
			int branch_target_conditional;
			branch_target_conditional = returnOFEXlatchbranchtarget();
			int branch_target_jmp;
			branch_target_jmp = returnOFEXlatchbranchtargetjmp();
			int alu_result;
			alu_result = initialaluresultvalue();
			EX_MA_Latch.setoptype(optype);
			if(OFEXlatchisr3())
			{
				if(optype==Instruction.OperationType.valueOf("nop"))
				{optype=optype;}
				else if(optype==Instruction.OperationType.valueOf("add"))
				{alu_result = op1;
				alu_result=alu_result+op2;}
				else if(optype==Instruction.OperationType.valueOf("sub"))
			    {alu_result = op1 ;
				alu_result=alu_result-op2;}
				else if(optype==Instruction.OperationType.valueOf("mul"))
				{alu_result=op1;
					alu_result=alu_result*op2;
				}
				else if(optype==Instruction.OperationType.valueOf("div"))
				{alu_result = op1 ;
					alu_result=alu_result/op2;
					EX_MA_Latch.setx31_result(op1%op2);
					EX_MA_Latch.set_ma_dest_x31(31);}
				else if(optype==Instruction.OperationType.valueOf("and"))
				{alu_result = op1 ;
				alu_result=alu_result&op2;}
				else if(optype==Instruction.OperationType.valueOf("or"))
				{alu_result = op1 ;
				alu_result=alu_result| op2;}
				else if (optype==Instruction.OperationType.valueOf("xor"))
				{alu_result = op1 ;
				alu_result=alu_result^ op2;}
				else if(optype==Instruction.OperationType.valueOf("slt"))
				{if(op1<op2)
				{
					alu_result = 1;
				}
				else
					alu_result = 0;}
				else if(optype==Instruction.OperationType.valueOf("sll"))
				{
					alu_result = op1;
					alu_result=alu_result<<op2;
					String op1_string;
					op1_string = String.format("%32s",Integer.toBinaryString(op1)).replace(" ","0");
					String op1_shifted_out;
					op1_shifted_out = op1_string.substring(0,op2);
					int shifted_out_bits;
					shifted_out_bits = new BigInteger(op1_shifted_out,2).intValue();
					EX_MA_Latch.setx31_result(shifted_out_bits);
					EX_MA_Latch.set_ma_dest_x31(31);}
				else if(optype==Instruction.OperationType.valueOf("srl"))
				{
					alu_result = op1>>>op2;
					String op1_string;
					op1_string = String.format("%32s",Integer.toBinaryString(op1)).replace(" ","0");
					String op1_shifted_out;
					op1_shifted_out = op1_string.substring(32-op2,32);
					int shifted_out_bits;
					shifted_out_bits = new BigInteger(op1_shifted_out,2).intValue();
					EX_MA_Latch.setx31_result(shifted_out_bits);
					EX_MA_Latch.set_ma_dest_x31(31);}
				else if(optype==Instruction.OperationType.valueOf("sra"))
				{
					alu_result = op1;
					alu_result=alu_result>>op2;
					String op1_string;
					op1_string = String.format("%32s",Integer.toBinaryString(op1)).replace(" ","0");
					String op1_shifted_out;
					op1_shifted_out = op1_string.substring(32-op2,32);
					int shifted_out_bits;
					shifted_out_bits = new BigInteger(op1_shifted_out,2).intValue();

					EX_MA_Latch.setx31_result(shifted_out_bits);
					EX_MA_Latch.set_ma_dest_x31(31);}



				EX_MA_Latch.setis_alu();
				EX_MA_Latch.setalu_result(alu_result);
				EX_MA_Latch.setdestination_register(rd);
				EX_MA_Latch.set_ma_destination(rd);
				setOFEXlatchisr3tofalse();

			}
			else if(OFEXlatchisr2inonconditional())
			{         if(optype==Instruction.OperationType.valueOf("addi"))
			{alu_result = op1 ;
				alu_result=alu_result+imm;}
			else if(optype==Instruction.OperationType.valueOf("subi"))
			{alu_result = op1 - imm;}
			else if(optype==Instruction.OperationType.valueOf("muli"))
			{alu_result = op1 * imm;}
			else if(optype==Instruction.OperationType.valueOf("divi"))
			{alu_result = op1 / imm;
				EX_MA_Latch.setx31_result(op1%imm);
				EX_MA_Latch.set_ma_dest_x31(31);}
			else if(optype==Instruction.OperationType.valueOf("andi"))
			{alu_result = op1 & imm;}
			else if(optype==Instruction.OperationType.valueOf("ori"))
			{alu_result = op1 | imm;}
			else if(optype==Instruction.OperationType.valueOf("xori"))
			{alu_result = op1 ^ imm;}
			else if (optype==Instruction.OperationType.valueOf("slti"))
			{if(op1<imm)
			{
				alu_result = 1;
			}
			else
				alu_result = 0;}
			else if(optype==Instruction.OperationType.valueOf("slt"))
			{if(op1<op2)
			{
				alu_result = 1;
			}
			else
				alu_result = 0;}
			else if(optype==Instruction.OperationType.valueOf("slli"))
			{
				alu_result = op1<<imm;
				String op1_string;
				op1_string = String.format("%32s",Integer.toBinaryString(op1)).replace(" ","0");
				String op1_shifted_out;
				op1_shifted_out = op1_string.substring(0,imm);
				int shifted_out_bits;
				shifted_out_bits = new BigInteger(op1_shifted_out,2).intValue();

				EX_MA_Latch.setx31_result(shifted_out_bits);
				EX_MA_Latch.set_ma_dest_x31(31);
			}
			else if(optype==Instruction.OperationType.valueOf("srli"))
			{
				alu_result = op1>>>imm;
				String op1_string;
				op1_string = String.format("%32s",Integer.toBinaryString(op1)).replace(" ","0");
				String op1_shifted_out;
				op1_shifted_out = op1_string.substring(32-imm,32);
				int shifted_out_bits;
				shifted_out_bits = new BigInteger(op1_shifted_out,2).intValue();

				EX_MA_Latch.setx31_result(shifted_out_bits);
				EX_MA_Latch.set_ma_dest_x31(31);

			}
			else if(optype==Instruction.OperationType.valueOf("srai"))
			{
				alu_result = op1>>imm;
				String op1_string;
				op1_string = String.format("%32s",Integer.toBinaryString(op1)).replace(" ","0");
				String op1_shifted_out;
				op1_shifted_out = op1_string.substring(32-imm,32);
				int shifted_out_bits;
				shifted_out_bits = new BigInteger(op1_shifted_out,2).intValue();

				EX_MA_Latch.setx31_result(shifted_out_bits);
				EX_MA_Latch.set_ma_dest_x31(31);
			}

				EX_MA_Latch.setis_alu();
				EX_MA_Latch.setalu_result(alu_result);
				EX_MA_Latch.setdestination_register(rd);
				EX_MA_Latch.set_ma_destination(rd);

				setOFEXlatchisnonconditionalr2itofalse();
			}
			else if(OFEXlatchisloadstore())
			{
				if(optype==Instruction.OperationType.valueOf("load"))
				{EX_MA_Latch.setis_load();
					int word_at;
					word_at = op1;
					word_at=word_at+imm;
					EX_MA_Latch.setWordAt(word_at);
					EX_MA_Latch.setdestination_register(rd);
					EX_MA_Latch.set_ma_destination(rd);}
				else if(optype==Instruction.OperationType.valueOf("store"))
				{EX_MA_Latch.setis_store();
					int word_at;
					word_at =rd;
					word_at=word_at+imm;
					EX_MA_Latch.setWordAt(word_at);
					EX_MA_Latch.setsource_register(op1);
					EX_MA_Latch.set_ma_destination(op1);}
                setOFEXlatchisloadstoretofalse();

			}
			else if(OFEXlatchisr2iconditional())
			{

				if(optype==Instruction.OperationType.valueOf("beq"))
				{if(op1 == op2)
				{
					IFenablesetwrongbranchtaken();

					IF_EnableLatch.set_correct_pc(branch_target_conditional);
				}}
				else if(optype==Instruction.OperationType.valueOf("bne"))
				{if(op1 != op2)
				{
					IFenablesetwrongbranchtaken();

					IF_EnableLatch.set_correct_pc(branch_target_conditional);
				}}
				else if(optype==Instruction.OperationType.valueOf("blt"))
				{if(op1 < op2)
				{
					IFenablesetwrongbranchtaken();

					IF_EnableLatch.set_correct_pc(branch_target_conditional);
				}}
				else if(optype==Instruction.OperationType.valueOf("bgt"))
				{if(op1 > op2)
				{IFenablesetwrongbranchtaken();

					IF_EnableLatch.set_correct_pc(branch_target_conditional);
				}}
				setOFEXlatchisconditionalr2itofalse();

			}
			else if(OFEXlatchisjmp())
			{	
				IF_EnableLatch.set_wrong_branch_taken(true);
				IF_EnableLatch.set_correct_pc(branch_target_jmp);
				OF_EX_Latch.is_jmp = false;
			}
			else if(OFEXlatchisend())
			{
				OF_EX_Latch.is_end = false;
				MA_RW_Latch.set_end_pc(containingProcessor.getRegisterFile().getProgramCounter());
			}
               EXMAlatchsetMAenabletotrue();

		}

	}
		
}
