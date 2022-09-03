package processor.pipeline;

public class OF_EX_LatchType {
	String opcode, rs1, rs2, rd, imm;

	boolean EX_enable;
	
	public OF_EX_LatchType()
	{
		EX_enable = false;
	}

	public boolean isEX_enable() {
		return EX_enable;
	}

	public void setEX_enable(boolean eX_enable) {
		EX_enable = eX_enable;
	}

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

}
