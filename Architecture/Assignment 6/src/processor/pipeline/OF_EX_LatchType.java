package processor.pipeline;
import generic.*;

public class OF_EX_LatchType {
	
	boolean EX_enable;


	boolean is_r3 = false;
	boolean is_r2i_non_conditional = false;
	boolean is_load_store = false;
	boolean is_r2i_conditional = false;
	boolean is_jmp = false;
	boolean is_end = false;
	boolean is_nop = false;

	Instruction.OperationType optype = null;
	int pc = 0; 
	int op1 = 0;
	int op2 = 0;
	int imm = 0;
	int rd = 0;
	int branch_target_conditional = 0;
	int branch_target_jmp = 0;
	int ex_destination = 0;

    public void setEXenabletofalse()
	{
		boolean value;
		value=false;
		EX_enable=value;
	}
	public OF_EX_LatchType()
	{

		setEXenabletofalse();
	}

	public boolean isEX_enable() 
	{
		return EX_enable;
	}
   public void setEXenabletoanothervalue(boolean value)
   {
	   boolean valuetobeset;
	   valuetobeset=value;
	   EX_enable=valuetobeset;

   }
	public void setEX_enable(boolean eX_enable) 
	{
		setEXenabletoanothervalue(eX_enable);
	}
    public void setoptypetoanothervalue(Instruction.OperationType tobeset)
	{
		Instruction.OperationType valuetobeset;
		valuetobeset=tobeset;
		optype=valuetobeset;
	}
	public void setoptype(Instruction.OperationType given_optype) 
	{
		setoptypetoanothervalue(given_optype);

	}
	public Instruction.OperationType getoptype() 
	{
		return optype;
	}

	public void setisr3totrue()
	{
		boolean valuetobeset;
	valuetobeset=true;
	is_r3=valuetobeset;
	}

	public void set_is_r3() 
	{
		setisr3totrue();

	}
    public void setisr2inonconditionaltotrue()
	{boolean valuetobeset;
	valuetobeset=true;
	is_r2i_non_conditional=valuetobeset;
	}
	public void set_is_r2i_non_conditional() 
	{
		setisr2inonconditionaltotrue();

	}
    public void setisr2iconditionaltotrue()
	{
		boolean valuetobeset;
		valuetobeset=true;
		is_r2i_conditional=valuetobeset;
	}
	public void set_is_r2i_conditional() 
	{
		is_r2i_conditional=true;

	}
    public void setisloadstoretotrue()
	{boolean value;
	value=true;
	is_load_store=value;
	}
	public void set_is_load_store() 
	{
		setisloadstoretotrue();
	}

	public void setisjmptotrue()
	{
		boolean valuetobeset;
		valuetobeset=true;
		is_jmp=valuetobeset;
	}
	public void set_is_jmp() 
	{
		setisjmptotrue();

	}

	public void setisendtotrue()
	{
		boolean valuetobeset;
		valuetobeset=true;
		is_end=valuetobeset;
	}
	public void set_is_end() 
	{
		setisendtotrue();
	}

	public void setplctoanothervalue(int value)
	{
		int valuetobeset;
		valuetobeset=value;
		pc=valuetobeset;
	}
	public void setpc(int given_pc) 
	{
		setplctoanothervalue(given_pc);

	}
	 public int getthevalueofrd()
	 {int valuetobereturned;
	 valuetobereturned=rd;
	 return valuetobereturned;}

	public int getrd() 
	{
		return rd;
	}
    public void setrdtoanothervalue(int value)
	{
		int valuetobeset;
		valuetobeset=value;
		rd=valuetobeset;
	}
	public void setrd(int given_rd) 
	{
		setrdtoanothervalue(given_rd);

	}

	public int getop1() 
	{
		return op1;
	}
    public void setop1toanothervalue(int val)
	{
		int valuetobeset;
		valuetobeset=val;
		op1=valuetobeset;
	}
	public void setop1(int given_op1) 
	{
		op1 = given_op1;
	}

	public int getop2() 
	{
		return op2;
	}

	public void setop2toanothervalue(int val)
	{
		int valuetobeset;
		valuetobeset=val;
		op2=valuetobeset;
	}
	public void setop2(int given_op2) 
	{
		op2 = given_op2;
	}

	public int getimm() 
	{
		return imm;
	}
    public void setimmtoagivenvalue(int val)
	{
		int valuetobeset;
		valuetobeset=val;
		imm=valuetobeset;
	}
	public void setimm(int given_imm) 
	{
		imm = given_imm;
	}
	public int getbranchtarget_conditional() 
	{
		return branch_target_conditional;
	}
    public void setbranchconditionaltoanothervalue(int val)
	{int valuetobeset;
	valuetobeset=val;
	branch_target_conditional=valuetobeset;
	}
	public void setbranchtarget_conditional(int branch_target) 
	{
		branch_target_conditional = branch_target;
	}
	public int getbranchtarget_jmp() 
	{
		return branch_target_jmp;
	}

    public void setbranchtargetjmptoanothervalue(int value)
	{int valuetobeset;
	valuetobeset=value;
	branch_target_jmp=valuetobeset;
	}
	public void setbranchtarget_jmp(int branch_target) 
	{
		branch_target_jmp = branch_target;
	}

	public int get_ex_destination() 
	{
		return ex_destination;
	}

	public void setexdestinationtoanothervalue(int val)
	{
		int valuetobeset;
		valuetobeset=val;
		ex_destination=valuetobeset;
	}
	public void set_ex_destination(int eX_destination) 
	{
		ex_destination = eX_destination;
	}

	public void force_nop()
	{
		optype = Instruction.OperationType.valueOf("nop");
	}

	
}
