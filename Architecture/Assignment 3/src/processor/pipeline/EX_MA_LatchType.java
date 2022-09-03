package processor.pipeline;

public class EX_MA_LatchType {

	boolean MA_enable;
	String opcode, imm, rs1, rs2, rd;
	int result,	address, lflag, sflag, wflag;

	public void setOpcode(String opcode) { this.opcode = opcode; }

	public void setImm(String imm) { this.imm = imm; }

	public void setRs1(String rs1) { this.rs1 = rs1; }

	public void setRs2(String rs2) { this.rs2 = rs2; }

	public void setRd(String rd) { this.rd = rd; }

	public String getOpcode() { return opcode; }

	public String getImm() { return imm; }

	public String getRs1() { return rs1; }

	public String getRs2() { return rs2; }

	public String getRd() { return rd; }

	public EX_MA_LatchType()
	{
		MA_enable = false;
		lflag = 0;
		sflag = 0;
		wflag = 0;
	}

	public boolean isMA_enable()
	{
		return MA_enable;
	}

	public void setMA_enable(boolean mA_enable)
	{
		MA_enable = mA_enable;
	}

	public void setlflag(int l, int a){
		lflag = l;
		address = a;
	}

	public void setsflag(int s, int a){
		sflag = s;
		address = a;
	}
	public void resetsflag(){
		sflag = 0;
	}
	public int getsflag(){
		return sflag;
	}

	public int getAddress(){
		return address;
	}
	public void setResult(int r){
		this.result= r;
	}
	public int getResult(){
		return result;
	}



}
