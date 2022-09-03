package processor.pipeline;

public class MA_RW_LatchType {

	public void setResult(int r){
		this.result= r;
	}
	public int getResult(){
		return result;
	}

	boolean RW_enable;
	int result,ld_result;
	
	public MA_RW_LatchType()
	{
		RW_enable = false;
	}

	public boolean isRW_enable() {
		return RW_enable;
	}

	public void setRW_enable(boolean rW_enable) {
		RW_enable = rW_enable;
	}

	String opcode, imm, rs1, rs2, rd;


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

	public void setLoad_result(int result) {

		ld_result = result;
	}

	public int getLoad_result() {

		return ld_result;
	}

}
