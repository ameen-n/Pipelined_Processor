package processor.pipeline;

import processor.Processor;
import generic.*;
import generic.Simulator;
import java.math.BigInteger;

//import javax.lang.model.util.ElementScanner14;

public class OperandFetch {
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_IF_LatchType EX_IF_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;

	public OperandFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_IF_LatchType eX_IF_Latch, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
	}



	public boolean boolconflictexist(){
		boolean returnvalue;
		returnvalue=false;
		return returnvalue;

	}

	public String nopvalue(){
		String returnvalue;
		returnvalue="nop";
		return returnvalue;
	}

	public String jmpvalue(){
		String returnvalue;
		returnvalue="jmp";
		return returnvalue;
	}
	public String endvalue(){
		String returnvalue;
		returnvalue="end";
		return  returnvalue;
	}
	public void IF_OF_indicatortofalse(){
		boolean set;
		set=false;
		IF_OF_Latch.set_is_nop(set);
	}
	public void setstallswitchtofalse()
	{  boolean valuetobeset;
		valuetobeset=false;
		IF_EnableLatch.set_stall_switch(valuetobeset);
	}
	public void setstallswitchtotrue()
	{  boolean valuetobeset;
		valuetobeset=true;
		IF_EnableLatch.set_stall_switch(valuetobeset);
	}

	public void setEXenabletotrue(){
		boolean valuetobeset;
		valuetobeset=true;
		OF_EX_Latch.setEX_enable(valuetobeset);
	}
	public boolean DetectConflict(Instruction.OperationType opcodeA, int rs1A, int rs2A, int rdA, int immA)
	{
		boolean does_conflict_exist ;
		String nop_value;
		nop_value=nopvalue();
		String jmp_value;
		jmp_value=jmpvalue();
		String end_value;
		end_value=endvalue();

		if(opcodeA == Instruction.OperationType.valueOf(nop_value)
				||opcodeA == Instruction.OperationType.valueOf(jmp_value)
				||opcodeA == Instruction.OperationType.valueOf(end_value))
		{
			OF_EX_Latch.setoptype(opcodeA);
			does_conflict_exist = false;
			return(does_conflict_exist);
		}

		Instruction.OperationType ex_opcodeB;
		ex_opcodeB = OF_EX_Latch.getoptype();
		Instruction.OperationType ma_opcodeB ;
		ma_opcodeB = EX_MA_Latch.getoptype();
		Instruction.OperationType rw_opcodeB;
		rw_opcodeB = MA_RW_Latch.getoptype();
		int ex_dest;
		ex_dest = OF_EX_Latch.get_ex_destination();
		int ma_dest ;
		ma_dest = EX_MA_Latch.get_ma_destination();

		int rw_dest;
		rw_dest = MA_RW_Latch.get_rw_destination();
		int ma_dest_x31 ;
		ma_dest_x31 = EX_MA_Latch.get_ma_dest_x31();
		int rw_dest_x31 ;
		rw_dest_x31 = MA_RW_Latch.get_rw_dest_x31();
		boolean ex_conflict;
		ex_conflict = false;
		boolean ma_conflict = false;
		ma_conflict = false;
		boolean rw_conflict = false;

		int ex_con=0, ma_con=0, rw_con=0;

		if(ex_opcodeB == Instruction.OperationType.valueOf("nop") || ex_opcodeB == Instruction.OperationType.valueOf("beq") || ex_opcodeB == Instruction.OperationType.valueOf("bne") || ex_opcodeB == Instruction.OperationType.valueOf("blt") || ex_opcodeB == Instruction.OperationType.valueOf("bgt") || ex_opcodeB == Instruction.OperationType.valueOf("store") || ex_opcodeB == Instruction.OperationType.valueOf("jmp"))
		{
			ex_con = 1;
		}
		if(ma_opcodeB == Instruction.OperationType.valueOf("nop") || ma_opcodeB == Instruction.OperationType.valueOf("beq") || ma_opcodeB == Instruction.OperationType.valueOf("bne") || ma_opcodeB == Instruction.OperationType.valueOf("blt") || ma_opcodeB == Instruction.OperationType.valueOf("bgt") || ma_opcodeB == Instruction.OperationType.valueOf("store") || ma_opcodeB == Instruction.OperationType.valueOf("jmp"))
		{
			ma_con = 1;
		}
		if(rw_opcodeB == Instruction.OperationType.valueOf("nop") || rw_opcodeB == Instruction.OperationType.valueOf("beq") || rw_opcodeB == Instruction.OperationType.valueOf("bne") || rw_opcodeB == Instruction.OperationType.valueOf("blt") || rw_opcodeB == Instruction.OperationType.valueOf("bgt") || rw_opcodeB == Instruction.OperationType.valueOf("store") || rw_opcodeB == Instruction.OperationType.valueOf("jmp"))
		{
			rw_con = 1;
		}


		if(rw_con==0)
		{
			ex_conflict = true;
		}
		if(ma_con==0)
		{
			ma_conflict = true;
		}
		if(ex_con==0)
		{
			rw_conflict = true;
		}



		if(!(ex_conflict||ma_conflict||rw_conflict))
		{
			does_conflict_exist = false;
			return does_conflict_exist;
		}
		ex_conflict = false;
		ma_conflict = false;
		rw_conflict = false;

		int src1;
		int src2;
		src1 = rs1A;
		src2 = rs2A;

		OF_EX_Latch.setoptype(opcodeA);



		if(opcodeA==  Instruction.OperationType.valueOf("add") ||opcodeA==Instruction.OperationType.valueOf("sub")||opcodeA==Instruction.OperationType.valueOf("mul")||opcodeA==Instruction.OperationType.valueOf("div")||opcodeA==Instruction.OperationType.valueOf("and")||opcodeA==Instruction.OperationType.valueOf("or")||opcodeA==Instruction.OperationType.valueOf("xor")||opcodeA==Instruction.OperationType.valueOf("slt")||opcodeA==Instruction.OperationType.valueOf("sll")||opcodeA==Instruction.OperationType.valueOf("srl")||opcodeA==Instruction.OperationType.valueOf("sra"))
		{src1 = rs1A;
			src2 = rs2A;
		}
		else if(opcodeA==Instruction.OperationType.valueOf("addi")||opcodeA==Instruction.OperationType.valueOf("subi")||opcodeA==Instruction.OperationType.valueOf("muli")||opcodeA==Instruction.OperationType.valueOf("divi")||opcodeA==Instruction.OperationType.valueOf("andi")||opcodeA==Instruction.OperationType.valueOf("ori")||opcodeA==Instruction.OperationType.valueOf("xori")||opcodeA==Instruction.OperationType.valueOf("slti")||opcodeA==Instruction.OperationType.valueOf("slli")||opcodeA==Instruction.OperationType.valueOf("srli")||opcodeA==Instruction.OperationType.valueOf("srai"))
		{
			src1 = rs1A;
			src2 = immA;

		}
		else if(opcodeA==Instruction.OperationType.valueOf("beq")||opcodeA==Instruction.OperationType.valueOf("bne")||opcodeA==Instruction.OperationType.valueOf("bgt")||opcodeA==Instruction.OperationType.valueOf("blt"))
		{
			src1 = rs1A;
			src2 = rdA;

		}
		else if(opcodeA==Instruction.OperationType.valueOf("load"))
		{
			src1 = rs1A;
			src2 = immA;
		}
		else if(opcodeA==Instruction.OperationType.valueOf("store")){


			src1 = rs1A;
			src2 = rdA;

		}



		if(opcodeA==Instruction.OperationType.valueOf("add")||opcodeA==Instruction.OperationType.valueOf("sub")||opcodeA==Instruction.OperationType.valueOf("mul")||opcodeA==Instruction.OperationType.valueOf("div")||opcodeA==Instruction.OperationType.valueOf("and")||opcodeA==Instruction.OperationType.valueOf("or")||opcodeA==Instruction.OperationType.valueOf("xor")||opcodeA==Instruction.OperationType.valueOf("slt")||opcodeA==Instruction.OperationType.valueOf("sll")||opcodeA==Instruction.OperationType.valueOf("srl")||opcodeA==Instruction.OperationType.valueOf("sra")||opcodeA==Instruction.OperationType.valueOf("addi")||opcodeA==Instruction.OperationType.valueOf("subi")||opcodeA==Instruction.OperationType.valueOf("muli")||opcodeA==Instruction.OperationType.valueOf("divi")||opcodeA==Instruction.OperationType.valueOf("andi")||opcodeA==Instruction.OperationType.valueOf("ori")||opcodeA==Instruction.OperationType.valueOf("xori")||opcodeA==Instruction.OperationType.valueOf("slti")||opcodeA==Instruction.OperationType.valueOf("slli")||opcodeA==Instruction.OperationType.valueOf("srli")||opcodeA==Instruction.OperationType.valueOf("srai")||opcodeA==Instruction.OperationType.valueOf("beq")||opcodeA==Instruction.OperationType.valueOf("bne")||opcodeA==Instruction.OperationType.valueOf("bgt")||opcodeA==Instruction.OperationType.valueOf("blt")||opcodeA==Instruction.OperationType.valueOf("load")||opcodeA==Instruction.OperationType.valueOf("store"))
		{

			if(src1 == ex_dest )
			{
				ex_conflict = true;

			}
			else if( src2 == ex_dest)
			{
				ex_conflict = true;

			}
			else if(src1 == ma_dest )
			{
				ma_conflict = true;

			}
			else if(src2 == ma_dest)
			{
				ma_conflict = true;

			}
			else if(src1 == rw_dest )
			{
				rw_conflict = true;

			}
			else if(src2 == rw_dest)
			{
				rw_conflict = true;

			}
			if(src1 == 31 )
			{
				if(ma_dest_x31 == 31)
				{
					ma_conflict = true;
				}
				else if(rw_dest_x31 == 31)
				{
					rw_conflict = true;
				}
			}
			else if(src2 == 31)
			{
				if(ma_dest_x31 == 31)
				{
					ma_conflict = true;
				}
				else if(rw_dest_x31 == 31)
				{
					rw_conflict = true;
				}
			}

		}


		if(ex_conflict)
		{   boolean return_value;
			return_value=true;
			return return_value;
		}
		if(ma_conflict)
		{
			boolean return_value;
			return_value=true;
			return return_value;
		}
		if(rw_conflict)
		{
			boolean return_value;
			return_value=true;
			return return_value;
		}
		return false;
	}
	public String twosComplement(String bin)
	{

		String ones;
		String twos;
		ones="";
		twos="";

		int len;
		len=bin.length();
		int initial=0;

		while(initial<len)
		{ ones += flip(bin.charAt(initial));
			initial=initial+1;
		}




		int number0 ;
		number0 = new BigInteger(ones,2).intValue();
		StringBuilder builder;
		builder = new StringBuilder(ones);
		boolean b ;
		b = false;

		int len_of_ones;
		len_of_ones=ones.length();
		int pointer;
		pointer=len_of_ones-1;

		while (pointer>=0)
		{ if (ones.charAt(pointer) == '1')
		{
			builder.setCharAt(pointer, '0');
		}
		else
		{
			builder.setCharAt(pointer, '1');
			b = true;
			break;
		}
			pointer=pointer-1;
		}


		if (!b)
		{
			builder.append("1", 0, 7);
		}
		twos = builder.toString();
		return twos;
	}
	public char flip(char c)
	{    char to_be_returned;
		if(c=='0')
			to_be_returned='1';
		else
			to_be_returned='0';

		return to_be_returned;
	}
	public Instruction.OperationType check_opcode(String binary_instruction)
	{
		String opcode ;
		opcode = binary_instruction.substring(0,5);
		Instruction.OperationType optype = null;

		if(opcode.equals("11111"))
		{
			optype = Instruction.OperationType.valueOf("nop");
			return optype;
		}
		else if(opcode.equals("00000"))
		{optype = Instruction.OperationType.valueOf("add");
			return optype;}
		else if(opcode.equals("00010"))
		{optype = Instruction.OperationType.valueOf("sub");
			return optype;}
		else if(opcode.equals("00100"))
		{optype = Instruction.OperationType.valueOf("mul");
			return optype;}
		else if (opcode.equals("00110"))
		{optype = Instruction.OperationType.valueOf("div");
			return optype;}
		else if(opcode.equals("01000"))
		{optype = Instruction.OperationType.valueOf("and");
			return optype;}
		else if(opcode.equals("01010"))
		{optype = Instruction.OperationType.valueOf("or");
			return optype;}
		else if(opcode.equals("01100"))
		{optype = Instruction.OperationType.valueOf("xor");
			return optype;}
		else if(opcode.equals("01110"))
		{optype = Instruction.OperationType.valueOf("slt");
			return optype;}
		else if(opcode.equals("10000"))
		{optype = Instruction.OperationType.valueOf("sll");
			return optype;}
		else if(opcode.equals("10010"))
		{optype = Instruction.OperationType.valueOf("srl");
			return optype;}
		else if(opcode.equals("10100"))
		{optype = Instruction.OperationType.valueOf("sra");
			return optype;}
		else if(opcode.equals("00001"))
		{optype = Instruction.OperationType.valueOf("addi");
			return optype;}
		else if(opcode.equals("00011"))
		{optype = Instruction.OperationType.valueOf("subi");
			return optype;}
		else if(opcode.equals("00101"))
		{optype = Instruction.OperationType.valueOf("muli");
			return optype;}
		else if(opcode.equals("00111"))
		{optype = Instruction.OperationType.valueOf("divi");
			return optype;}
		else if(opcode.equals("01001"))
		{optype = Instruction.OperationType.valueOf("andi");
			return optype;}
		else if(opcode.equals("01011"))
		{optype = Instruction.OperationType.valueOf("ori");
			return optype;}
		else if(opcode.equals("01101"))
		{optype = Instruction.OperationType.valueOf("xori");
			return optype;}
		else if(opcode.equals("01111"))
		{optype = Instruction.OperationType.valueOf("slti");
			return optype;}
		else if(opcode.equals("10001"))
		{optype = Instruction.OperationType.valueOf("slli");
			return optype;}
		else if(opcode.equals("01011"))
		{optype = Instruction.OperationType.valueOf("ori");
			return optype;}
		else if(opcode.equals("01101"))
		{optype = Instruction.OperationType.valueOf("xori");
			return optype;}
		else if(opcode.equals("01111"))
		{optype = Instruction.OperationType.valueOf("slti");
			return optype;}
		else if(opcode.equals("10001"))
		{optype = Instruction.OperationType.valueOf("slli");
			return optype;}
		else if(opcode.equals("10011"))
		{optype = Instruction.OperationType.valueOf("srli");
			return optype;}
		else if(opcode.equals("10101"))
		{optype = Instruction.OperationType.valueOf("srai");
			return optype;}
		else if(opcode.equals("10110"))
		{optype = Instruction.OperationType.valueOf("load");
			return optype;}
		else if(opcode.equals("10111"))
		{optype = Instruction.OperationType.valueOf("store");
			return optype;}
		else if(opcode.equals("11001"))
		{optype = Instruction.OperationType.valueOf("beq");
			return optype;}
		else if (opcode.equals("11010"))
		{optype = Instruction.OperationType.valueOf("bne");
			return optype;}
		else if(opcode.equals("11011"))
		{optype = Instruction.OperationType.valueOf("blt");
			return optype;}
		else if(opcode.equals("11100"))
		{optype = Instruction.OperationType.valueOf("bgt");
			return optype;}
		else if(opcode.equals("11000"))
		{optype = Instruction.OperationType.valueOf("jmp");
			return optype;}
		else if(opcode.equals("11101"))
		{optype = Instruction.OperationType.valueOf("end");
			return optype;}
		else
		return optype;
	}


	public int get_register_value(String binary_instruction, int start_index, int end_index)
	{
		String register;
		int starting_index_ofthis_register;
		int ending_index_ofthis_register;
		starting_index_ofthis_register=start_index;
		ending_index_ofthis_register=end_index;
		ending_index_ofthis_register=ending_index_ofthis_register+1;
		register = binary_instruction.substring(starting_index_ofthis_register,ending_index_ofthis_register);
		int register_value ;
		register_value = new BigInteger(register,2).intValue();
		return register_value;
	}

	public int get_immediate_value(String binary_instruction, int start_index, int end_index)
	{int starting_index_ofthis_register;
		int ending_index_ofthis_register;
		starting_index_ofthis_register=start_index;
		ending_index_ofthis_register=end_index;
		ending_index_ofthis_register=ending_index_ofthis_register+1;
		String register = binary_instruction.substring(starting_index_ofthis_register,ending_index_ofthis_register);
		if(register.charAt(0)=='1')
		{
			int register_value = 0 - new BigInteger(twosComplement(register),2).intValue();
			return register_value;
		}
		int register_value ;
		register_value = new BigInteger(register,2).intValue();
		return register_value;

	}
	public void performOF()
	{
		if(IF_OF_Latch.isOF_enable())
		{
			int pc;
			pc = containingProcessor.getRegisterFile().getProgramCounter();
			if(IF_OF_Latch.is_nop)
			{
				int rs1;
				int rs2;
				int rd;
				int imm;
				rs1 = 0;
				rs2 = 0;
				rd = 0;
				imm = 0;

				DetectConflict(Instruction.OperationType.valueOf("nop"), rs1, rs2, rd, imm);
				IF_OF_indicatortofalse();

			}

			else
			{

				Instruction newInstruction;
				newInstruction = new Instruction();
				int instruction = IF_OF_Latch.getInstruction();
				instruction = IF_OF_Latch.getInstruction();
				String binary_instruction;
				binary_instruction = null;

				if(instruction>=0)
				{
					binary_instruction = String.format("%32s",Integer.toBinaryString(instruction)).replace(" ","0");
				}
				else
				{
					binary_instruction = twosComplement(String.format("%32s",Integer.toBinaryString(-instruction)).replace(" ","0"));
				}
				int rs1 ;
				rs1 = 0;
				int rs2;
				rs2 = 0;
				int rd;
				rd = 0;
				int imm;
				imm = 0;

				Instruction.OperationType operationType = check_opcode(binary_instruction);
				if (operationType == Instruction.OperationType.nop) {
					if (!DetectConflict(check_opcode(binary_instruction), rs1, rs2, rd, imm)) {
					} else {
						setstallswitchtofalse();
						OF_EX_Latch.force_nop();
					}
				} else if (operationType == Instruction.OperationType.add || operationType == Instruction.OperationType.sub || operationType == Instruction.OperationType.mul || operationType == Instruction.OperationType.div || operationType == Instruction.OperationType.and || operationType == Instruction.OperationType.or || operationType == Instruction.OperationType.xor || operationType == Instruction.OperationType.slt || operationType == Instruction.OperationType.sll || operationType == Instruction.OperationType.srl || operationType == Instruction.OperationType.sra) {
					rs1 = containingProcessor.getRegisterFile().getValue(get_register_value(binary_instruction, 5, 9));
					rs2 = containingProcessor.getRegisterFile().getValue(get_register_value(binary_instruction, 10, 14));
					rd = get_register_value(binary_instruction, 15, 19);
					if (!DetectConflict(check_opcode(binary_instruction), get_register_value(binary_instruction, 5, 9), get_register_value(binary_instruction, 10, 14), rd, imm)) {
						OF_EX_Latch.set_is_r3();
						setstallswitchtotrue();
						OF_EX_Latch.setop1(rs1);
						OF_EX_Latch.setop2(rs2);
						OF_EX_Latch.setrd(rd);
						OF_EX_Latch.set_ex_destination(rd);
						OF_EX_Latch.setoptype(check_opcode(binary_instruction));
					} else {
					setstallswitchtofalse();
						OF_EX_Latch.force_nop();
					}
				} else if (operationType == Instruction.OperationType.addi || operationType == Instruction.OperationType.subi || operationType == Instruction.OperationType.muli || operationType == Instruction.OperationType.divi || operationType == Instruction.OperationType.andi || operationType == Instruction.OperationType.ori || operationType == Instruction.OperationType.xori || operationType == Instruction.OperationType.slti || operationType == Instruction.OperationType.slli || operationType == Instruction.OperationType.srli || operationType == Instruction.OperationType.srai) {
					rs1 = containingProcessor.getRegisterFile().getValue(get_register_value(binary_instruction, 5, 9));
					rd = get_register_value(binary_instruction, 10, 14);
					imm = get_immediate_value(binary_instruction, 15, 31);

					if (!DetectConflict(check_opcode(binary_instruction), get_register_value(binary_instruction, 5, 9), rs2, rd, imm)) {

						OF_EX_Latch.set_is_r2i_non_conditional();
						setstallswitchtotrue();
						OF_EX_Latch.setop1(rs1);
						OF_EX_Latch.setrd(rd);
						OF_EX_Latch.set_ex_destination(rd);
						OF_EX_Latch.setimm(imm);
						OF_EX_Latch.setoptype(check_opcode(binary_instruction));
					} else {
						setstallswitchtofalse();
						OF_EX_Latch.force_nop();
					}
				} else if (operationType != Instruction.OperationType.load) {
					if (operationType == Instruction.OperationType.store) {
						rs1 = containingProcessor.getRegisterFile().getValue(get_register_value(binary_instruction, 5, 9));
						rd = containingProcessor.getRegisterFile().getValue(get_register_value(binary_instruction, 10, 14));
						imm = get_immediate_value(binary_instruction, 15, 31);

						if (!DetectConflict(check_opcode(binary_instruction), get_register_value(binary_instruction, 5, 9), rs2, get_register_value(binary_instruction, 10, 14), imm)) {

							OF_EX_Latch.set_is_load_store();
						setstallswitchtotrue();

							OF_EX_Latch.setop1(rs1);
							OF_EX_Latch.setrd(rd);
							OF_EX_Latch.set_ex_destination(rs1);
							OF_EX_Latch.setimm(imm);
							OF_EX_Latch.setoptype(check_opcode(binary_instruction));
						} else {
							setstallswitchtofalse();
							OF_EX_Latch.force_nop();
						}
					} else if (operationType == Instruction.OperationType.beq || operationType == Instruction.OperationType.bne || operationType == Instruction.OperationType.blt || operationType == Instruction.OperationType.bgt) {
						rs1 = containingProcessor.getRegisterFile().getValue(get_register_value(binary_instruction, 5, 9));
						rd = containingProcessor.getRegisterFile().getValue(get_register_value(binary_instruction, 10, 14));
						imm = get_immediate_value(binary_instruction, 15, 31);

						if (!DetectConflict(check_opcode(binary_instruction), get_register_value(binary_instruction, 5, 9), rs2, get_register_value(binary_instruction, 10, 14), imm)) {

							OF_EX_Latch.set_is_r2i_conditional();

							setstallswitchtotrue();

							OF_EX_Latch.setop1(rs1);
							OF_EX_Latch.setop2(rd);
							OF_EX_Latch.setimm(imm);
							OF_EX_Latch.setpc(pc);
							OF_EX_Latch.setoptype(check_opcode(binary_instruction));
							OF_EX_Latch.setbranchtarget_conditional(pc - 1 + imm);
						} else {
						setstallswitchtofalse();
							OF_EX_Latch.force_nop();
						}
					} else if (operationType == Instruction.OperationType.jmp) {
						rd = containingProcessor.getRegisterFile().getValue(get_register_value(binary_instruction, 5, 9));
						imm = get_immediate_value(binary_instruction, 10, 31);

						if (!DetectConflict(check_opcode(binary_instruction), rs1, rs2, get_register_value(binary_instruction, 5, 9), imm)) {

							OF_EX_Latch.set_is_jmp();
							setstallswitchtotrue();
							OF_EX_Latch.setop2(rd);
							OF_EX_Latch.setimm(imm);
							OF_EX_Latch.setpc(pc);
							OF_EX_Latch.setoptype(check_opcode(binary_instruction));
							OF_EX_Latch.setbranchtarget_jmp(rd + pc + imm - 1);
						} else {
							setstallswitchtofalse();
							OF_EX_Latch.force_nop();
						}
					} else if (operationType == Instruction.OperationType.end) {
						if (!DetectConflict(check_opcode(binary_instruction), rs1, rs2, rd, imm)) {
							setstallswitchtotrue();
							OF_EX_Latch.set_is_end();
							OF_EX_Latch.setoptype(check_opcode(binary_instruction));
						} else {
						setstallswitchtofalse();
							OF_EX_Latch.force_nop();
						}
					}
				} else {
					rs1 = containingProcessor.getRegisterFile().getValue(get_register_value(binary_instruction, 5, 9));
					rd = get_register_value(binary_instruction, 10, 14);
					imm = get_immediate_value(binary_instruction, 15, 31);

					if (!DetectConflict(check_opcode(binary_instruction), get_register_value(binary_instruction, 5, 9), rs2, rd, imm)) {

						OF_EX_Latch.set_is_load_store();
						setstallswitchtotrue();
						OF_EX_Latch.setop1(rs1);
						OF_EX_Latch.setrd(rd);
						OF_EX_Latch.set_ex_destination(rd);
						OF_EX_Latch.setimm(imm);
						OF_EX_Latch.setoptype(check_opcode(binary_instruction));
					} else {
						setstallswitchtofalse();
						OF_EX_Latch.force_nop();
					}
				}
			}
			setEXenabletotrue();
		}
	}
}