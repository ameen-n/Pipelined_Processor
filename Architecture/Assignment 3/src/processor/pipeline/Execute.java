package processor.pipeline;

import processor.Processor;

public class Execute {
	Processor containingProcessor;
	OF_EX_LatchType OF_EX_Latch;
	EX_MA_LatchType EX_MA_Latch;
	EX_IF_LatchType EX_IF_Latch;
	RegisterWrite rw;
	
	public Execute(Processor containingProcessor, OF_EX_LatchType oF_EX_Latch, EX_MA_LatchType eX_MA_Latch, EX_IF_LatchType eX_IF_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.EX_IF_Latch = eX_IF_Latch;

	}

	static char flip(char c)
	{
		return (c == '0') ? '1' : '0';
	}


	static String TwosComplement(String bin) //from GFG
	{
		int n = bin.length();
		int i;

		String ones = "",twos;


		for (i = 0; i < n; i++)
		{
			ones += flip(bin.charAt(i));
		}

		twos = ones;
		for (i = n - 1; i >= 0; i--)
		{
			if (ones.charAt(i) == '1')
				twos = twos.substring(0, i) + '0' + twos.substring(i + 1);

			else
			{
				twos = twos.substring(0, i) + '1' + twos.substring(i + 1);
				break;
			}
		}
		if (i == -1)
		{
			twos = '1' + twos;
		}
		return twos;
	}

	static int convInt(String number)
	{
		if(number.charAt(0) == '1')
		{
			String twosComp = TwosComplement(number);

			int val = Integer.parseInt(twosComp,2);
			return (-1 * val);
		}
		else
		{
			return (Integer.parseInt(number,2));
		}
	}



	public void performEX()
	{
		//System.out.println("EX");
		String opcode = OF_EX_Latch.getOpcode();
		String rs1 = OF_EX_Latch.getRs1();
		String rs2 = OF_EX_Latch.getRs2();
		String rd = OF_EX_Latch.getRd();
		String imm = OF_EX_Latch.getImm();
		//System.out.println("OPCODE = " + Integer.parseInt(opcode, 2));
		//System.out.println("IMM = " + Integer.parseInt(imm, 2));


		EX_MA_Latch.setOpcode(opcode);
		EX_MA_Latch.setRs1(rs1);
		EX_MA_Latch.setRs2(rs2);
		EX_MA_Latch.setRd(rd);
		EX_MA_Latch.setImm(imm);

		int currentPC = containingProcessor.getRegisterFile().getProgramCounter() - 1;

		int op=0, rs1i=0, rs2i=0, rdi=0, immi=0;
		if(opcode!=null)
			op = Integer.parseInt(opcode, 2);

		if(rs1!=null)
			rs1i = containingProcessor.getRegisterFile().getValue(Integer.parseInt(rs1, 2));

		if(rs2!=null)
			rs2i = containingProcessor.getRegisterFile().getValue(Integer.parseInt(rs2, 2));

		if(rd!=null)
		 	rdi = containingProcessor.getRegisterFile().getValue(Integer.parseInt(rd, 2));

		if(imm!=null)
		 	immi = convInt(imm);

		int result = 0;

		if(OF_EX_Latch.isEX_enable()) {
		if((op>=0)&(op<22))
		{
			if(op%2==0){//R3

				switch(op)
				{
					case 0 : {
						result = rs1i + rs2i;

						break;
					}
					case 2 : {
						result = rs1i - rs2i;


						break;
					}
					case 4 : {
						result = rs1i * rs2i;

						break;
					}
					case 6 : {
						if(rs2i!=0){
						result = rs1i / rs2i;
						int remainder = rs1i % immi;
						containingProcessor.getRegisterFile().setValue(31, remainder);
						}

						break;
					}
					case 8 : {
						result = rs1i & rs2i;

						break;
					}

					case 10 : {
						result = rs1i | rs2i;

						break;
					}
					case 12 : {
						result = rs1i ^ rs2i;

						break;
					}
					case 14 : {
						if(rs2i>rs1i)
							result = 1;
						else
							result = 0;

						break;
					}
					case 16 : {
						result = rs1i << rs2i;

						break;
					}
					case 18 : {
						result = rs1i >>> rs2i;

						break;
					}
					case 20 : {
						result = rs1i >> rs2i;

						break;
					}

				}


				EX_MA_Latch.setResult(result);
				EX_MA_Latch.setRd(rd);
				//write result

			}

			else{//R2I
				switch(op) {
					case 1: {
						result = rs1i + immi;
						//System.out.println("addi imm= " +immi);
						break;
					}
					case 3: {
						result = rs1i - immi;

						break;
					}
					case 5: {
						result = rs1i * immi;

						break;
					}
					case 7: {

						if(immi!=0){
						result = rs1i / immi;

						int remainder = rs1i % immi;
						containingProcessor.getRegisterFile().setValue(31, remainder);


						}
						break;
					}
					case 9: {
						result = rs1i & immi;

						break;
					}

					case 11: {
						result = rs1i | immi;

						break;
					}
					case 13: {
						result = rs1i ^ immi;

						break;
					}
					case 15: {
						if (rs2i > rs1i)
							result = 1;
						else
							result = 0;

						break;
					}
					case 17: {
						result = rs1i << immi;

						break;
					}
					case 19: {
						result = rs1i >>> immi;

						break;
					}
					case 21: {
						result = rs1i >> immi;

						break;
					}
				}

				EX_MA_Latch.setResult(result);
				EX_MA_Latch.setRd(rd);

			}
		}
		else if(op == 24)	//RI
		{
			//System.out.println("VALUE =" + (immi+currentPC));
			EX_IF_Latch.isJumpEnabled(true);
			EX_IF_Latch.setJumpAddress(immi+currentPC+rdi);



			// make changes in EX_IF_LatchType

		}
		else	//remaining R2I
		{
			switch(op)
			{
				case 22: { //load
					result = containingProcessor.getMainMemory().getWord(rs1i + immi); //load from here, load works??
					//System.out.println("result = " +  result);
					EX_MA_Latch.setlflag(1, rdi); //load to here

					break;

				}
				case 23: { //store
					result = rs1i; //store in this
					EX_MA_Latch.setsflag(1, rdi + immi); //word at this location


					break;
				}
				case 25: { //beq
					if(rs1i == rdi) {
						EX_IF_Latch.isJumpEnabled(true);
						EX_IF_Latch.setJumpAddress(immi+currentPC);
					}



					break;
				}
				case 26: { //bne
					if(rs1i != rdi)
					{
						EX_IF_Latch.isJumpEnabled(true);
						EX_IF_Latch.setJumpAddress(immi+currentPC);
					}



					break;
				}
				case 27: { //blt
					if(rs1i < rdi)
					{
						EX_IF_Latch.isJumpEnabled(true);
						EX_IF_Latch.setJumpAddress(immi+currentPC);
					}


					break;
				}
				case 28: { //bgt
					if(rs1i > rdi)
					{
						EX_IF_Latch.isJumpEnabled(true);
						EX_IF_Latch.setJumpAddress(immi+currentPC);
						//System.out.println("herebgt");

					}


					break;
				}
				case 29: {

					break;
				}
				default : break;
			}
			EX_MA_Latch.setResult(result);

		}

		}
		//System.out.println("execute");
		OF_EX_Latch.setEX_enable(false);
		EX_MA_Latch.setMA_enable(true);
	}

}
