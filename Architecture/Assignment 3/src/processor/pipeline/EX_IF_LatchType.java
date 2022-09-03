package processor.pipeline;

public class EX_IF_LatchType {
	boolean jumpEnabled;
	int address;
	public EX_IF_LatchType()
	{
		address = 0;
	}

	public void isJumpEnabled(boolean t)
	{
		jumpEnabled = t;
	}
	public boolean getJumpEnabled()
	{
		return jumpEnabled;
	}

	public void setJumpAddress(int a)
	{
		address = a;
	}
	public int getJumpAddress()
	{
		return address;
	}
	public void disableJump(){
		jumpEnabled = false;
	}

}
