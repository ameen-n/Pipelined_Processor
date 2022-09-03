package processor.pipeline;

public class IF_EnableLatchType {
	
	boolean IF_enable;
	boolean stall_switch;
	boolean wrong_branch_taken;
	int correct_pc;
	boolean is_IF_busy;


   public void enableIFstage(){
	   boolean set;
	   set=true;
	   IF_enable=set;
   }
   
   public void stallIFstage(){
	boolean set;
	set=true;
	stall_switch=set;
   }


	public IF_EnableLatchType()
	{
		enableIFstage();
		stallIFstage();
	}
    public boolean returnboolofIFenable()
	{
		boolean returnvalue;
		returnvalue=IF_enable;
		return returnvalue;
	}
	public boolean isIF_enable() {
		boolean x;
		x=returnboolofIFenable();
	return x;
	}
    public void setIFenabletoanothervalue(boolean val)
	{
		boolean valuetobeset;
		valuetobeset=val;
		IF_enable=valuetobeset;
	}
	public void setIF_enable(boolean iF_enable) {
		IF_enable = iF_enable;
	}
    public boolean returnthevalueofstallswitch()
	{boolean valuetobereturned;
	valuetobereturned=stall_switch;
	return valuetobereturned;
	}
	public boolean stall_switch() {
		return stall_switch;
	}
    public void setstallswitchtoanothervalue(boolean val)
	{
		boolean valuetobestored;
		valuetobestored=val;
		stall_switch=valuetobestored;
	}
	public void set_stall_switch(boolean stall_Switch) {
		stall_switch = stall_Switch;
	}
    public boolean returnthevalueofwrongbranchtaken()
	{
		boolean valuetobereturned;
		valuetobereturned=wrong_branch_taken;
		return valuetobereturned;
	}

	public boolean wrong_branch_taken() {
		return wrong_branch_taken;
	}
	public void setwrongbranchtakentoanothervalue(boolean value)
	{boolean valuetobeset;
	valuetobeset=value;
	wrong_branch_taken=valuetobeset;
	}
	public void set_wrong_branch_taken(boolean wrong_branch_Taken) {
		wrong_branch_taken = wrong_branch_Taken;
	}
    public int returnthevalueofcorrectpc()
	{
		int valuetobereturned;
		valuetobereturned=correct_pc;
		return valuetobereturned;
	}
	public int get_correct_pc() {
		return correct_pc;
	}
     public void setthevalueofcorrectpctosomethingelse(int value)
	 {
		 int vauetobeset;
		 vauetobeset=value;
		 correct_pc=vauetobeset;
	 }
	public void set_correct_pc(int correct_PC) {
		correct_pc = correct_PC;
	}

	public boolean returnifIFisbusy()
	{boolean valuetobesent;
	valuetobesent=is_IF_busy;
	return valuetobesent;}
	public boolean isIF_busy() {
		return is_IF_busy;
	}
	public void setisIFbusytosomethingelse(boolean value)
	{
		boolean setvalue;
		setvalue =value;
		is_IF_busy=setvalue;
	}
	public void setIF_busy(boolean is_true) {
		is_IF_busy = is_true;
	}

}
