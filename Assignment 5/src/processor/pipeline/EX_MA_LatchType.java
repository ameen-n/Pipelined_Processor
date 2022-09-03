package processor.pipeline;
import generic.*;
public class EX_MA_LatchType {
	

	boolean MA_enable;
	boolean is_load = false;
	boolean is_store = false;
	boolean is_alu = false;
	boolean is_MA_busy;
	Instruction.OperationType optype;

	int word_at = 0;
	int destination_register = 0;
	int source_register = 0;
	int alu_result = 0;
	int x31_result = 0;
	int ma_destination = 0;
	int ma_dest_x31 = 0;

    public void setMAenabletofalse(){
		boolean set;
		set=false;
		MA_enable=false;
	}
	public EX_MA_LatchType()
	{
		setMAenabletofalse();

	}

	public boolean isMA_enable() 
	{

		return MA_enable;
	}

	public void setMAenabletobooleanvalue(boolean value)
	{
		boolean set;
		set=value;
		MA_enable=set;
	}
	public void setMA_enable(boolean mA_enable) 
	{

		setMAenabletobooleanvalue(mA_enable);
	}
    public void setdestinationregister(int x)
	{
		int valuetobeset;
		valuetobeset=x;
		destination_register=valuetobeset;
	}
	public void setdestination_register(int rd) 
	{

		setdestinationregister(rd);
	}
    public int getthevalueofdestinationregister()
	{int valuetobereturned;
	valuetobereturned=destination_register;
	return valuetobereturned;
	}
	public int getdestination_register() 
	{

		return destination_register;
	}
	public void setwordat(int x)
	{
		int valuetobeset;
		valuetobeset=x;
		word_at=valuetobeset;
	}
	public void setword_at(int word) 
	{
		setwordat(word);

	}
    public int getthevalueofwordat()
	{int valuetobereturned;
	valuetobereturned=word_at;
	return valuetobereturned;
	}
	public int getword_at() 
	{

		return word_at;
	}
	public void setx31result(int x)
	{
		int valuetobeset;
		valuetobeset=x;
		x31_result=valuetobeset;
	}
	public void setx31_result(int x31) 
	{

		setx31result(x31);
	}

	public int returnthevalueofx31result()
	{int valuetobereturned;
	valuetobereturned=x31_result;

	return valuetobereturned;
	}
	public int getx31_result() 
	{
		return x31_result;
	}
	public void setsourceregister(int x)
	{
		int valuetobeset;
		valuetobeset=x;
		source_register=valuetobeset;
	}
	public void setsource_register(int rs) 
	{
		setsourceregister(rs);

	}

	public int getsource_register() 
	{

		return source_register;
	}
	public void setisloadtotrue()
	{
		boolean valuetobeset;
		valuetobeset=true;
		is_load=valuetobeset;
	}
	public void setis_load() 
	{
		setisloadtotrue();

	}
	public void setisstoretotrue()
	{
		boolean valuetobeset;
		valuetobeset=true;
		is_store=valuetobeset;
	}
	public void setis_store() 
	{

		setisstoretotrue();
	}
	public void setisalutotrue()
	{
		boolean valuetobeset;
		valuetobeset=true;
		is_alu=valuetobeset;
	}
	public void setis_alu() 
	{
		setisalutotrue();

	}
    public void setaluresulttoavalue(int input)
	{
		int result;
		result=input;
		alu_result=result;
	}
	public void setalu_result(int alu) 
	{
		setaluresulttoavalue(alu);

	}
	
	public int getalu_result() 
	{
		return alu_result;
	}

	public int get_ma_destination() 
	{
		return ma_destination;
	}

	public void setmadestinationtoavalue(int input)
	{
		int valuetobeset;
		valuetobeset=input;
		ma_destination=valuetobeset;
	}
	public void set_ma_destination(int mA_destination) 
	{
		setmadestinationtoavalue(mA_destination);

	}
    public void setoptypetoavalue(Instruction.OperationType input)
	{
		Instruction.OperationType set;
		set=input;
		optype=set;
	}
	public void setoptype(Instruction.OperationType given_optype) 
	{

		setoptypetoavalue(given_optype);
	}
	public Instruction.OperationType getoptype() 
	{
		return optype;
	}
	public int get_ma_dest_x31() 
	{
		return ma_dest_x31;
	}
    public void setmadestx31(int value)
	{
		int valuetobeset;
		valuetobeset=value;
		ma_dest_x31=valuetobeset;
	}
	public void set_ma_dest_x31(int mA_dest_x31) 
	{

		setmadestx31(mA_dest_x31);
	}
	public boolean isMA_busy() {
		return is_MA_busy;
	}
    public void setMAbusytoavalue(boolean input)
	{
		boolean valuetobeset;
		valuetobeset=input;
		is_MA_busy=valuetobeset;
	}
	public void setMA_busy(boolean is_true) {
		setMAbusytoavalue(is_true);

	}
}
