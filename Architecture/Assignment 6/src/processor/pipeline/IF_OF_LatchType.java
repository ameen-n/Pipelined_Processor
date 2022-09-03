package processor.pipeline;
import generic.*;
public class IF_OF_LatchType {
	
	boolean OF_enable;
	boolean is_nop;
	int instruction;

	boolean is_OF_busy;
	public void setOFenabletofalse()
	{boolean valuetobeset;
	valuetobeset=false;
	OF_enable=valuetobeset;
	}
	public IF_OF_LatchType()
	{
		OF_enable = false;
	}
	public boolean checkifOFisenabled(){
		boolean valuetobereturned;
		valuetobereturned=OF_enable;
		return valuetobereturned;
	}
	public boolean isOF_enable() {
		return OF_enable;
	}

	public void setOFenabletosomethingelse(boolean value)
	{
		boolean valuetobeset;
		valuetobeset=value;
		OF_enable=valuetobeset;
	}
	public void setOF_enable(boolean oF_enable) {
		OF_enable = oF_enable;
	}

	public int returnthevalueofinstruction()
	{int vauetobereturned;
	vauetobereturned=instruction;
	return vauetobereturned;}

	public int getInstruction() {
		return instruction;
	}

	public void changethevalueofinstruction(int val)
	{int value;
	value=val;
	this.instruction=value;
	}
	public void setInstruction(int instruction) {
		this.instruction = instruction;
	}

	public void changethevalueofisnop(boolean val)
	{
		boolean valuetobeset;
		valuetobeset=val;
		is_nop=valuetobeset;
	}
	public void set_is_nop(boolean noP)
	{
		is_nop=noP;
	}
    public boolean checkifOFbusy()
	{boolean valuetobereturned;
	valuetobereturned=is_OF_busy;
	return valuetobereturned;}
	public boolean isOF_busy() {
		return is_OF_busy;
	}


}
