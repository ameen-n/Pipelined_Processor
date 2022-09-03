package processor.pipeline;

import processor.Processor;
import processor.Clock;
import generic.Simulator;
import generic.*;
import configuration.*;
public class MemoryAccess implements Element{
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_IF_LatchType EX_IF_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public MemoryAccess(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_IF_LatchType eX_IF_Latch, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
	{
		this.containingProcessor = containingProcessor;
		this.MA_RW_Latch = mA_RW_Latch;
		this.IF_EnableLatch = iF_EnableLatch;
		this.OF_EX_Latch = oF_EX_Latch;
		this.EX_IF_Latch = eX_IF_Latch;
		this.EX_MA_Latch = eX_MA_Latch;
		this.IF_OF_Latch = iF_OF_Latch;
	}

	void setMAbusytotrue(){
		boolean value;
		value=true;
		EX_MA_Latch.setMA_busy(value);
	}
	void setMAbusytofalse(){
		boolean value;
		value=false;
		EX_MA_Latch.setMA_busy(value);
	}

	void setstallswitchtofalse(){
		boolean value;
		value=false;
		IF_EnableLatch.set_stall_switch(value);
	}
	void setstallswitchtotrue(){
		boolean value;
		value=true;
		IF_EnableLatch.set_stall_switch(value);
	}
    void disableOFstage()
	{
		boolean value;
		value=false;
		IF_OF_Latch.setOF_enable(value);
	}
	void enableOFstage()
	{
		boolean value;
		value=true;
		IF_OF_Latch.setOF_enable(value);
	}
	void disableEXstage()
	{
		boolean value;
		value=false;
		OF_EX_Latch.setEX_enable(value);
	}
	void enableEXstage()
	{
		boolean value;
		value=true;
		OF_EX_Latch.setEX_enable(value);
	}
	void disableMAstage()
	{
		boolean value;
		value=false;
		EX_MA_Latch.setMA_enable(value);
	}
	void enableMAstage()
	{
		boolean value;
		value=true;
		EX_MA_Latch.setMA_enable(value);
	}
	void disableRWstage()
	{
		boolean value;
		value=false;
		MA_RW_Latch.setRW_enable(value);
	}
	void enableRWstage()
	{
		boolean value;
		value=true;
		MA_RW_Latch.setRW_enable(value);
	}

	void setEX_MA_latchisalutofalse(){
		EX_MA_Latch.is_alu = false;
	}

	public void performMA()
	{
		if(EX_MA_Latch.isMA_enable())
		{
			MA_RW_Latch.setoptype(EX_MA_Latch.getoptype());
			if(EX_MA_Latch.is_load)
			{
				if(!EX_MA_Latch.isMA_busy())
				{
					Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime()+Configuration.mainMemoryLatency,this,containingProcessor.getMainMemory(),EX_MA_Latch.getword_at()));
					setMAbusytotrue();
					setstallswitchtofalse();
					disableOFstage();
					disableEXstage();
					disableMAstage();
					disableRWstage();
				}
			}
			else if(EX_MA_Latch.is_store)
			{
				if(!EX_MA_Latch.isMA_busy())
				{
					int getword_at = EX_MA_Latch.getword_at();
					int getsource_register = EX_MA_Latch.getsource_register();
					Simulator.getEventQueue().addEvent(new MemoryWriteEvent(Clock.getCurrentTime()+Configuration.mainMemoryLatency,this,containingProcessor.getMainMemory(),getword_at,getsource_register));
					setMAbusytotrue();
					setstallswitchtofalse();
					disableOFstage();
					disableEXstage();
					disableMAstage();
					disableRWstage();
				}
			}
			else if(EX_MA_Latch.is_alu)
			{

				int load_result ;
				load_result = EX_MA_Latch.getalu_result();
				MA_RW_Latch.setload_result(load_result);
				int x31_result ;
				x31_result = EX_MA_Latch.getx31_result();
				MA_RW_Latch.setx31_result(x31_result);
				if(EX_MA_Latch.get_ma_dest_x31()==31)
				{
					EX_MA_Latch.set_ma_dest_x31(0);
					MA_RW_Latch.set_rw_dest_x31(31);
				}
				int load_register;
				load_register = EX_MA_Latch.getdestination_register();
				MA_RW_Latch.setload_register(load_register);
				MA_RW_Latch.set_rw_destination(load_register);

				setEX_MA_latchisalutofalse();
			}
		}
	}
	@Override
	public void handleEvent(Event e)
	{
			MemoryResponseEvent event = (MemoryResponseEvent) e;
			if(EX_MA_Latch.is_load)
			{
				int load_result;
				load_result = event.getValue();
				MA_RW_Latch.setload_result(load_result);
				int x31_result;
				x31_result = EX_MA_Latch.getx31_result();
				MA_RW_Latch.setx31_result(x31_result);
				int load_register ;
				load_register = EX_MA_Latch.getdestination_register();
				MA_RW_Latch.setload_register(load_register);
				MA_RW_Latch.set_rw_destination(load_register);
				EX_MA_Latch.is_load = false;
			}
			else if(EX_MA_Latch.is_store)
			{
				EX_MA_Latch.is_store = false;
			}
		    setMAbusytofalse();
	     	setstallswitchtotrue();
	     	enableOFstage();
		    enableEXstage();
			enableMAstage();
	     	enableRWstage();
	}

}
