package processor.pipeline;

public class EX_IF_LatchType {

	boolean IS_enable;
	int PC;
	

    public boolean IS_enabler_boolean()
	{
		boolean false_signifier=false;
		return false_signifier;
	}

	public EX_IF_LatchType()
	{
		boolean set=IS_enabler_boolean();
		PC = 0;
		IS_enable = set;
	}
       

	public boolean IS_enable_returner(boolean x){
      boolean value_to_be_returned=x;
	  return value_to_be_returned;
	}

	public boolean getIS_enable() {
		boolean setter=IS_enable_returner(IS_enable);
		return setter;
	}
    
	public boolean setIS_enable_boolean_returner(boolean x) {
		return x;
	}
	public void setIS_enable(boolean iS_enable, int newPC) {
	    boolean value=setIS_enable_boolean_returner(iS_enable);
		IS_enable = value;
		PC = newPC;
	}

	public void setIS_enable(boolean iS_enable) {
		IS_enable = iS_enable;
	}



	public int PC_return_value(int p) {
		int value=p;
		return value;
	}

	public int getPC() {
		int return_value=PC_return_value(PC);

		return return_value;
	}

}
