package processor.pipeline;
import generic.*;
public class MA_RW_LatchType {
	
	boolean RW_enable;
	boolean is_end = false;
	Instruction.OperationType optype;
	int load_result = 0;
	int load_register = 0;
	int x31_result = 0;
	int rw_destination = 0;
	int rw_dest_x31 = 0;
	int end_pc;

	public void setRWenabletofalse(){
		boolean setvalue;
		setvalue=false;
		RW_enable=setvalue;
	}
	public MA_RW_LatchType()
	{
		setRWenabletofalse();

	}

	public boolean isRW_enable() 
	{
		return RW_enable;
	}

    public void setRWenabletoanothervalue(boolean value){
		boolean valuetobeset;
		valuetobeset=value;
		RW_enable=valuetobeset;
	}
	public void setRW_enable(boolean rW_enable) 
	{
		setRWenabletoanothervalue(rW_enable);

	}
    public void setloadresulttoanothervalue(int value)
   {int valuetobeset;
   valuetobeset=value;
   load_result=valuetobeset;}

	public void setload_result(int result)
	{
		setloadresulttoanothervalue(result);

	}

	public int getload_result()
	{
		return load_result;
	}

	public void setx31_result(int x31)
	{
		x31_result = x31;
	}
	public int getx31_result()
	{
		return x31_result;
	}

    public int getthevalueoftheloadregister(int val)
	{
		int valuetobereturned;
		valuetobereturned=val;
		return valuetobereturned;
	}
	public void setload_register(int rd)
	{ int set;
		set=getthevalueoftheloadregister(rd);
		load_register = set;
	}

	public int getload_register()
	{
		return load_register;
	}

	public int get_rw_destination() 
	{
		return rw_destination;
	}

	public void setrwdestinationtoavalue(int val)
	{
		int valuetobeset;
		valuetobeset=val;
		rw_destination=valuetobeset;
	}
	public void set_rw_destination(int rW_destination) 
	{
		setrwdestinationtoavalue(rW_destination);
	}
	public void setoptypetoavalue(Instruction.OperationType val)
	{
		Instruction.OperationType valuetobeset;
		valuetobeset=val;
		optype=valuetobeset;
	}
	public void setoptype(Instruction.OperationType given_optype) 
	{
		setoptypetoavalue(given_optype);
	}
	public Instruction.OperationType getoptype() 
	{
		return optype;
	}
	public int get_rw_dest_x31() 
	{
		return rw_dest_x31;
	}
    public void setrwdestx31toavalue(int value)
	{
		int valuetobeset;
		valuetobeset=value;
		rw_dest_x31=valuetobeset;
	}
	public void set_rw_dest_x31(int rW_dest_x31) 
	{
		setrwdestx31toavalue(rW_dest_x31);

	}
	public int returnthevalueofpc(int val){
		int valuetobereturned;
		valuetobereturned=val;
		return valuetobereturned;
	}
	public int get_end_pc() 
	{
		int val;
		val=returnthevalueofpc(end_pc);
		return val;
	}
    public void setendpctoavalue(int val)
	{
		int valuetobechanged;
		valuetobechanged=val;
		end_pc=valuetobechanged;
	}
	public void set_end_pc(int pc) 
	{
		setendpctoavalue(pc);

	}
}
