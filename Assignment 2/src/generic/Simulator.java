package generic;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
public class Simulator {


	public static String getIns(String inst) {
		if (inst.equals("add")) {
			String str = "00000";
			return str;
		}
		if (inst.equals("addi")) {
			String str = "00001";
			return str;
		}
		if (inst.equals("sub")) {
			String str = "00010";
			return str;
		}
		if (inst.equals("subi")) {
			String str = "00011";
			return str;
		}
		if (inst.equals("mul")) {
			String str = "00100";
			return str;
		}
		if (inst.equals("muli")) {
			String str = "00101";
			return str;
		}
		if (inst.equals("div")) {
			String str = "00110";
			return str;
		}
		if (inst.equals("divi")) {
			String str = "00111";
			return str;
		}
		if (inst.equals("and")) {
			String str = "01000";
			return str;
		}
		if (inst.equals("andi")) {
			String str = "01001";
			return str;
		}
		if (inst.equals("or")) {
			String str = "010101";
			return str;
		}
		if (inst.equals("ori")) {
			String str = "01011";
			return str;
		}
		if (inst.equals("xor")) {
			String str = "01100";
			return str;
		}
		if (inst.equals("xori")) {
			String str = "01101";
			return str;
		}
		if (inst.equals("slt")) {
			String str = "01110";
			return str;
		}
		if (inst.equals("slti")) {
			String str = "01111";
			return str;
		}
		if (inst.equals("sll")) {
			String str = "10000";
			return str;
		}
		if (inst.equals("slli")) {
			String str = "10001";
			return str;
		}
		if (inst.equals("srl")) {
			String str = "10010";
			return str;
		}
		if (inst.equals("srli")) {
			String str = "10011";
			return str;
		}
		if (inst.equals("sra")) {
			String str = "10100";
			return str;
		}
		if (inst.equals("srai")) {
			String str = "10101";
			return str;
		}
		if (inst.equals("load")) {
			String str = "10110";
			return str;
		}
		if (inst.equals("store")) {
			String str = "10111";
			return str;
		}if (inst.equals("jmp")) {
			String str = "11000";
			return str;
		}if (inst.equals("beq")) {
			String str = "11001";
			return str;
		}
		if (inst.equals("bne")) {
			String str = "11010";
			return str;
		}
		if (inst.equals("blt")) {
			String str = "11011";
			return str;
		}
		if (inst.equals("bgt")) {
			String str = "11100";
			return str;
		}
		if (inst.equals("end")) {
			String str = "11101";
			return str;
		}

		return "0";
	}
	public static void setupSimulation(String assemblyProgramFile)
	{
		int firstCodeAddress = ParsedProgram.parseDataSection(assemblyProgramFile);
		ParsedProgram.parseCodeSection(assemblyProgramFile, firstCodeAddress);
		ParsedProgram.printState();
	}

	private static String ConvertToBits(int num, int lenOfTargetString)
	{
		String binary = String.format("%" + lenOfTargetString + "s", Integer.toBinaryString(num)).replace(' ', '0');
		return binary;
	}

	private static int toSignedInteger(String binary)
	{
		int bin_len = binary.length();
		int n = 32 - bin_len;
		char[] sign_ext = new char[n];
		for(int i=0; i<sign_ext.length; i++){
			sign_ext[i]=binary.charAt(0);
		}

		int signedInteger = (int) Long.parseLong(new String(sign_ext) + binary, 2);
		return signedInteger;
	}
	private static String ConvertToBitString(int n)
	{

		ArrayList<Integer> bitsstorer=new ArrayList<>();

		while(n!=0)
		{
			bitsstorer.add(n%2);
			n /= 2;
		}

		StringBuilder builder = new StringBuilder();
		for(int i=bitsstorer.size()-1;i>=0;i--)
			builder.append(bitsstorer.get(i));
		return " " + builder.toString();
	}

		private static String convert(Operand inst, int precision)
		{
		if (inst == null)
			return ConvertToBits(0, precision);

		if (inst.getOperandType() == Operand.OperandType.Label)
			return ConvertToBits(ParsedProgram.symtab.get(inst.getLabelValue()), precision);


		return ConvertToBits(inst.getValue(), precision);

		}
	public static void assemble(String objectProgramFile) throws IOException
	{
		//TODO your assembler code
		//1. open the objectProgramFile in binary mode

		FileOutputStream file = new FileOutputStream(objectProgramFile);

		//2. write the firstCodeAddress to the file

		file.write(ByteBuffer.allocate(4).putInt(ParsedProgram.firstCodeAddress).array());

		//3. write the data to the file
		for (int i=0; i< ParsedProgram.data.size(); i++)
			file.write(ByteBuffer.allocate(4).putInt(ParsedProgram.data.get(i)).array());

		//4. assemble one instruction at a time, and write to the file
		for (int i=0;i< ParsedProgram.code.size();i++)
		{
			String b = "";

			String temp =  ParsedProgram.code.get(i).getOperationType().name();
			b += getIns(temp);
			int opCode = Integer.parseInt(b, 2);
			int pc = ParsedProgram.code.get(i).getProgramCounter();

			switch (temp)
			{
				case "add":
				case "sub":
				case "mul":
				case "div":
				case "and":
				case "or":
				case "xor":
				case "slt":
				case "sll":
				case "srl":
				case "sra":
				{
					// R3 Type
					b += convert(ParsedProgram.code.get(i).getSourceOperand1(), 5);
					b += convert(ParsedProgram.code.get(i).getSourceOperand2(), 5);
					b += convert(ParsedProgram.code.get(i).getDestinationOperand(), 5);
					b += ConvertToBits(0, 12);
					break;
				}
				case "end":
				{
					b += ConvertToBits(0, 27);
					break;

				}
				case "jmp":
				{
					b += ConvertToBits(0, 5);
					int value = Integer.parseInt(convert(ParsedProgram.code.get(i).getDestinationOperand(), 5), 2) - pc;
					String bin = ConvertToBits(value, 22);
					b += bin.substring(bin.length() - 22);
					break;
				}
				case "addi":
				case "subi":
				case "muli":
				case "divi":
				case "andi":
				case "ori":
				case "xori":
				case "slti":
				case "slli":
				case "srli":
				case "srai":
				case "load":
				case "store":
				{
					b += convert(ParsedProgram.code.get(i).getSourceOperand1(), 5);
					b += convert(ParsedProgram.code.get(i).getDestinationOperand(), 5);
					b += convert(ParsedProgram.code.get(i).getSourceOperand2(), 17);
					break;
				}
				case "beq":
				case "bne":
				case "blt":
				case "bgt":
				{
					int value = Integer.parseInt(convert(ParsedProgram.code.get(i).getDestinationOperand(), 5), 2) - pc;
					b += convert(ParsedProgram.code.get(i).getSourceOperand1(), 5);
					b += convert(ParsedProgram.code.get(i).getSourceOperand2(), 5);
					String bin = ConvertToBits(value, 17);
					b += bin.substring(bin.length() - 17);
					break;
				}
			}
				int instInteger = (int) Long.parseLong(b, 2);
				byte[] instBinary = ByteBuffer.allocate(4).putInt(instInteger).array();
				file.write(instBinary);

			}
			//5. close the files
			file.close();
		}


	}



