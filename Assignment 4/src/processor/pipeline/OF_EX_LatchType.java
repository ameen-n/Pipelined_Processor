package processor.pipeline;

import generic.Instruction;

public class OF_EX_LatchType {
	
	boolean EX_enable;
	Instruction instruction;
	boolean NOP;
    
	 public boolean setNOP()
	 {
		 boolean p=true;
		 return p;
	 }

	 public boolean setEX_enable(){
		 boolean p=false;
		 return p;
	 }

	public OF_EX_LatchType()
	{ boolean a=setEX_enable();
      boolean b=setNOP();
		EX_enable = a;
		NOP = b;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public boolean setEX_enable_returner(boolean p) {
		boolean h=p;
		return h;
	}
	 
	public void setEX_enable(boolean eX_enable) {
		boolean EX_enable_newvalue=setEX_enable_returner(eX_enable);
		EX_enable = EX_enable_newvalue;
	}
  
	public Instruction setInstructions(Instruction instruction) {
		Instruction tobesent=instruction;
		return tobesent;
	}

	public void setInstruction(Instruction instruction) {
		Instruction new_instruction=instruction;
		this.instruction = new_instruction;
	}

	public Instruction getInstruction() {
		return this.instruction;
	}
	


	public boolean get_NOP(boolean lam)
	{
		boolean value_to_be_returned=lam;
		return value_to_be_returned;
	}

	public boolean getIsNOP() {
        boolean NOP_getvalue=get_NOP(NOP);
		return NOP_getvalue;
	}
	

    public boolean NOP_change_setter(boolean x){
		boolean returnstuff=x;
		return returnstuff;
	}

	public void setIsNOP(boolean is_NOP) {
		boolean l=NOP_change_setter(is_NOP);
		NOP = l;
	}

}
