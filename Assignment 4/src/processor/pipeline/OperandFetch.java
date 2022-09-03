package processor.pipeline;

import processor.Processor;
import generic.Instruction;
import generic.Instruction.OperationType;
import generic.Operand;
import generic.Statistics;
import generic.Operand.OperandType;

public class OperandFetch {
	Processor containingProcessor;

	IF_OF_LatchType IF_OF_Latch;

	OF_EX_LatchType OF_EX_Latch;

	EX_MA_LatchType EX_MA_Latch;

	MA_RW_LatchType MA_RW_Latch;

	IF_EnableLatchType IF_EnableLatch;

	public OperandFetch(Processor containingProcessor, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch, IF_EnableLatchType iF_EnableLatch) {
		this.containingProcessor = containingProcessor;
		this.IF_OF_Latch = iF_OF_Latch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
	}

	public static char flip(char c) {
		char k;
		if(c=='0')
		{k='1';
	     return k;}
	      else
	     {k='0';
         return k;}
		
	}

	public static String twosComplement(String bin) {
		String twos = "", ones = "";

		int len=bin.length();
int i;
		for ( i = 0; i < len; i++) {

			ones += flip(bin.charAt(i));
			
		}

		StringBuilder builder = new StringBuilder(ones);
		boolean bool_checker = false;
		len=ones.length();
		for ( i= len - 1; i > 0; i--) {
			if (ones.charAt(i) == '1') {
				builder.setCharAt(i, '0');
			} else {
				builder.setCharAt(i, '1');
				bool_checker = true;
				break;
			}
		}
		if (!bool_checker) {

			builder.append("1", 0, 7);
		}
		twos = builder.toString();
		return twos;
	}
	
	public static boolean checkConflict(Instruction instruction, int reg_1, int reg_2) {
		int inst_ordinal;
		boolean check=instruction!=null && instruction.getOperationType() != null;

      if(check)
	  {
		  inst_ordinal=instruction.getOperationType().ordinal();
	  }
	  else
	  {
		  inst_ordinal=1000;
	  }

      

		if ((inst_ordinal <= 21 && inst_ordinal % 2 == 0) ) {
			int dest_reg = instruction != null ? instruction.getDestinationOperand().getValue() : -1;
			if (reg_1 == dest_reg || reg_2 == dest_reg) {

				return true;
			} 
		}
		 if ( (inst_ordinal <= 21 && inst_ordinal % 2 != 0) ) {
			int dest_reg = instruction != null ? instruction.getDestinationOperand().getValue() : -1;
			if (reg_1 == dest_reg || reg_2 == dest_reg) {

				return true;
			} 
		}
		 if ( inst_ordinal == 22 ) {
			int dest_reg = instruction != null ? instruction.getDestinationOperand().getValue() : -1;

			if (reg_1 == dest_reg || reg_2 == dest_reg) {

				return true;
			} 
		}
		 if ( inst_ordinal == 23) {
			int dest_reg = instruction != null ? instruction.getDestinationOperand().getValue() : -1;
			if (reg_1 == dest_reg || reg_2 == dest_reg) {
				return true;
			} 
		}
		 
		 return false;
		
	}
	
	public boolean checkConflictWithDivision(int reg_1, int reg_2) {
		Instruction instruction_ex_stage = OF_EX_Latch.getInstruction();
		Instruction instruction_ma_stage = EX_MA_Latch.getInstruction();
		Instruction instruction_rw_stage = MA_RW_Latch.getInstruction();
		if (reg_1 == 31 || reg_2 == 31) {
			int inst_ex_ordinal = instruction_ex_stage != null && instruction_ex_stage.getOperationType() != null ? instruction_ex_stage.getOperationType().ordinal() : 1000;
			int inst_ma_ordinal = instruction_ma_stage != null && instruction_ma_stage.getOperationType() != null ? instruction_ma_stage.getOperationType().ordinal() : 1000;
			int inst_rw_ordinal = instruction_rw_stage != null && instruction_rw_stage.getOperationType() != null ? instruction_rw_stage.getOperationType().ordinal() : 1000;
			if (inst_ex_ordinal == 6 || inst_ex_ordinal == 7 || inst_ma_ordinal == 6 || inst_ma_ordinal == 7 || inst_rw_ordinal == 6 || inst_rw_ordinal == 7) {

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public void conflictBubblePCModify () {

		IF_EnableLatch.setIF_enable(false);
		OF_EX_Latch.setIsNOP(true);

	}
 	
	public void performOF() {
		if (IF_OF_Latch.isOF_enable()) {
			Statistics.setNumberOfOFInstructions(Statistics.getNumberOfOFInstructions() + 1);
			OperationType[] operationType = OperationType.values();
			String instruction = Integer.toBinaryString(IF_OF_Latch.getInstruction());


			while (instruction.length() != 32) {

				instruction = "0" + instruction;
				
			}
			int opcode_instruction_substring_begin;
			int opcode_instruction_substring_end;
			opcode_instruction_substring_begin=0;
			opcode_instruction_substring_end=5;
			String opcode = instruction.substring(opcode_instruction_substring_begin, opcode_instruction_substring_end);
			int type_operation = Integer.parseInt(opcode, 2);
			OperationType operation = operationType[type_operation];
			
			if (operation.ordinal() == 24  ) {

				IF_EnableLatch.setIF_enable(false);
			}
			else if ( operation.ordinal() == 25  ) {

				IF_EnableLatch.setIF_enable(false);
			}
			else if ( operation.ordinal() == 26  ) {

				IF_EnableLatch.setIF_enable(false);
			}
			else if ( operation.ordinal() == 27 ) {

				IF_EnableLatch.setIF_enable(false);
			}
			else if ( operation.ordinal() == 28 ) {

				IF_EnableLatch.setIF_enable(false);
			}
			

			
			boolean conflict_inst = false;
			Instruction instruction_ex_stage;
			instruction_ex_stage = OF_EX_Latch.getInstruction();
			Instruction instruction_ma_stage;
			instruction_ma_stage = EX_MA_Latch.getInstruction();
			Instruction instruction_rw_stage;
			instruction_rw_stage = MA_RW_Latch.getInstruction();
			Instruction inst ;
			inst = new Instruction();
			switch (operation) {
			case add:
			case sub:
			case mul:
			case div:
			case and:
			case or:
			case xor:
			case slt:
			case sll:
			case srl:
			case sra:
				Operand rs1 ;
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				int registerNo ;
				int begin_string;
				int end_string;
				int length_of_the_string;
				begin_string=5;
				end_string=10;
				length_of_the_string=2;


				registerNo = Integer.parseInt(instruction.substring(begin_string, end_string), length_of_the_string);
				rs1.setValue(registerNo);

				Operand rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				int instruction_substring_begin;
				int instruction_substring_end;
				int instruction_substring_length;
                instruction_substring_begin=10;
				instruction_substring_end=instruction_substring_begin+5;
				instruction_substring_length=2;

				int registerNo2 = Integer.parseInt(instruction.substring(instruction_substring_begin, instruction_substring_end), instruction_substring_length);
				rs2.setValue(registerNo2);
				if (checkConflict(instruction_ex_stage, registerNo, registerNo2)||checkConflict(instruction_ma_stage, registerNo, registerNo2)||checkConflict(instruction_rw_stage, registerNo, registerNo2)||checkConflictWithDivision(registerNo, registerNo2))
					conflict_inst = true;
			
				if (conflict_inst) {
					this.conflictBubblePCModify();
					break;
				}

				Operand rd = new Operand();
				rd.setOperandType(OperandType.Register);

				int register_number_instruction_substring_begin;
				int register_number_instruction_substring_end;
				int register_number_instruction_substring_length;
				register_number_instruction_substring_begin=15;
				register_number_instruction_substring_end=20;
				register_number_instruction_substring_length=2;

				registerNo = Integer.parseInt(instruction.substring(register_number_instruction_substring_begin, register_number_instruction_substring_end), register_number_instruction_substring_length);
				rd.setValue(registerNo);

				inst.setOperationType(operationType[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);
				break;

			case end:
				inst.setOperationType(operationType[type_operation]);
				IF_EnableLatch.setIF_enable(false);
				break;
			case jmp:
				Operand op = new Operand();
				int imm_instruction_substring_begin;
				int imm_instruction_substring_end;
				imm_instruction_substring_begin=10;
				imm_instruction_substring_end=32;
                   int length_x=2;
				String imm = instruction.substring(imm_instruction_substring_begin, imm_instruction_substring_end);
				int imm_val = Integer.parseInt(imm, length_x);
				if (imm.charAt(0) == '1') {
					imm = twosComplement(imm);
					imm_val = Integer.parseInt(imm, length_x) * -1;
				}
				if (imm_val != 0) {
					op.setOperandType(OperandType.Immediate);
					op.setValue(imm_val);
				} else {
					register_number_instruction_substring_begin=5;
				register_number_instruction_substring_end=10;
				register_number_instruction_substring_length=2;

					registerNo = Integer.parseInt(instruction.substring(register_number_instruction_substring_begin, register_number_instruction_substring_end), register_number_instruction_substring_length);
					op.setOperandType(OperandType.Register);
					op.setValue(registerNo);
				}

				inst.setOperationType(operationType[type_operation]);
				inst.setDestinationOperand(op);
				break;

			case beq:
			case bne:
			case blt:
			case bgt:
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				register_number_instruction_substring_begin=10;
				register_number_instruction_substring_end=15;
				register_number_instruction_substring_length=2;
				registerNo = Integer.parseInt(instruction.substring(register_number_instruction_substring_begin, register_number_instruction_substring_end), register_number_instruction_substring_length);
				rs1.setValue(registerNo);
				
				
				rs2 = new Operand();
				rs2.setOperandType(OperandType.Register);
				int register_number_second_instruction_substring_begin;
				int register_number_second_instruction_substring_end;
				int register_number_second_instruction_substring_length;
				register_number_second_instruction_substring_begin=10;
				register_number_second_instruction_substring_end=15;
				register_number_second_instruction_substring_length=2;
				registerNo2 = Integer.parseInt(instruction.substring(register_number_second_instruction_substring_begin, register_number_second_instruction_substring_end), register_number_second_instruction_substring_length);
				rs2.setValue(registerNo2);
				
				if (checkConflict(instruction_ex_stage, registerNo, registerNo2)||checkConflict(instruction_ma_stage, registerNo, registerNo2)||checkConflict(instruction_rw_stage, registerNo, registerNo2)||checkConflictWithDivision(registerNo, registerNo2))
					conflict_inst = true;
			
				if (conflict_inst) {
					this.conflictBubblePCModify();
					break;
				}
				
	

				rd = new Operand();
				rd.setOperandType(OperandType.Immediate);
				imm_instruction_substring_begin=15;
				imm_instruction_substring_end=32;
				imm = instruction.substring(imm_instruction_substring_begin, imm_instruction_substring_end);
				imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = twosComplement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				rd.setValue(imm_val);
				
				inst.setOperationType(operationType[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);

				break;

			default:
				
				rs1 = new Operand();
				rs1.setOperandType(OperandType.Register);
				register_number_instruction_substring_begin=5;
				register_number_instruction_substring_end=10;
				register_number_instruction_substring_length=2;
				registerNo = Integer.parseInt(instruction.substring(register_number_instruction_substring_begin, register_number_instruction_substring_end), register_number_instruction_substring_length);
				rs1.setValue(registerNo);

				if (checkConflict(instruction_ex_stage, registerNo, registerNo)||checkConflict(instruction_ma_stage, registerNo, registerNo)||checkConflict(instruction_rw_stage, registerNo, registerNo)||checkConflictWithDivision(registerNo, registerNo)) {

					conflict_inst = true;
				}	
			
					
				if (conflict_inst) {
					this.conflictBubblePCModify();
					break;
				}

		
				rd = new Operand();
				rd.setOperandType(OperandType.Register);
				registerNo = Integer.parseInt(instruction.substring(10, 15), 2);
				rd.setValue(registerNo);

				
				rs2 = new Operand();
				rs2.setOperandType(OperandType.Immediate);
				imm_instruction_substring_begin=15;
				imm_instruction_substring_end=32;
				imm = instruction.substring(imm_instruction_substring_begin, imm_instruction_substring_end);
				imm_val = Integer.parseInt(imm, 2);
				if (imm.charAt(0) == '1') {
					imm = twosComplement(imm);
					imm_val = Integer.parseInt(imm, 2) * -1;
				}
				rs2.setValue(imm_val);
				inst.setOperationType(operationType[type_operation]);
				inst.setSourceOperand1(rs1);
				inst.setSourceOperand2(rs2);
				inst.setDestinationOperand(rd);

				break;
			}

			OF_EX_Latch.setInstruction(inst);

			OF_EX_Latch.setEX_enable(true);
		}
	}

}
