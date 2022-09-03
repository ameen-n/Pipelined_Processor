package processor.pipeline;

import generic.Instruction;

public class EX_MA_LatchType {
	
	boolean MA_enable;
	int alu_result;
	boolean NOP;
	Instruction instruction;

    public boolean EX_MA_boolean_setter_first(){
		boolean return_type_first=false;
		return return_type_first;
	}
    
	public boolean EX_MA_boolean_setter_second(){
		boolean return_type_second=false;
		return return_type_second;
	}

	public EX_MA_LatchType()
	{
		boolean first=EX_MA_boolean_setter_first();
        boolean second=EX_MA_boolean_setter_second();
		MA_enable = first;
		NOP = second;
	}


	public boolean MA_enable_boolean_value(){
	      boolean result=MA_enable;
		return result;
	}
	public boolean isMA_enable() {
      
		boolean final_boolean_value=MA_enable_boolean_value();
		return final_boolean_value;
	}

	public void setMA_enable(boolean mA_enable) {
		boolean setter=mA_enable;
		MA_enable = setter;
	}

	public Instruction getInstruction() {
		return instruction;
	}

	public void setInstruction(Instruction inst) {
		instruction = inst;
	}


    public int ALU_result_returner(int r){
		int value_to_be_returned=r;
		return value_to_be_returned;
	}

	public int getALU_result() {
		int getter=ALU_result_returner(alu_result);
		return getter;
	}


	public int get_result(int x)
	{
		int returned=x;
		return returned;
	}
	public void setALU_result(int result) {

        int p=get_result(result);

		alu_result = p;
	}
	
	public boolean NOP_returner(boolean bool){
       boolean value_to_be_returned=bool;
	   return value_to_be_returned;

	}
	public boolean getIsNOP() {

        boolean x=NOP_returner(NOP);
		return x;
	}
	


     public boolean set_is_NOP_returner(boolean j){
		 boolean value_to_be_returned=j;
		 return value_to_be_returned;
	 }

	public void setIsNOP(boolean is_NOP) {
		boolean NOP_setter=set_is_NOP_returner(is_NOP);
		NOP = NOP_setter;
	}


}
