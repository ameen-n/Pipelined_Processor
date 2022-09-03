package processor.pipeline;

import processor.Processor;
import processor.Clock;
import generic.Simulator;
import generic.*;
import configuration.*;

public class InstructionFetch implements Element{
	
	Processor containingProcessor;
	IF_EnableLatchType IF_EnableLatch;
	IF_OF_LatchType IF_OF_Latch;
	OF_EX_LatchType OF_EX_Latch;
	EX_IF_LatchType EX_IF_Latch;
	EX_MA_LatchType EX_MA_Latch;
	MA_RW_LatchType MA_RW_Latch;
	
	public InstructionFetch(Processor containingProcessor, IF_EnableLatchType iF_EnableLatch, IF_OF_LatchType iF_OF_Latch, OF_EX_LatchType oF_EX_Latch, EX_IF_LatchType eX_IF_Latch, EX_MA_LatchType eX_MA_Latch, MA_RW_LatchType mA_RW_Latch)
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
	void setIFbusytofalse(){
		boolean value;
		value=false;
		IF_EnableLatch.setIF_busy(value);
	}

	void setIFbusytotrue(){
		boolean value;
		value=true;
		IF_EnableLatch.setIF_busy(value);
	}

	void setNOPtotrueinIFOFlatch(){
		boolean value;
		value=true;
		IF_OF_Latch.set_is_nop(value);
	}

	public void performIF()
	{
		if(IF_EnableLatch.isIF_enable())
		{
			int currentPC;
			currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			if(IF_EnableLatch.stall_switch())
			{
				if(!IF_EnableLatch.isIF_busy())
				{

					Simulator.getEventQueue().addEvent(new MemoryReadEvent(Clock.getCurrentTime()+Configuration.mainMemoryLatency,this,containingProcessor.getMainMemory(),currentPC));
					setIFbusytotrue();
					disableOFstage();
					disableEXstage();
					disableMAstage();
					disableRWstage();
				}
			}
		}
	}
	@Override
	public void handleEvent(Event e)
	{
		if(IF_OF_Latch.isOF_busy())
		{
			e.setEventTime(Clock.getCurrentTime()+1);
			Simulator.getEventQueue().addEvent(e);
		}
		else if(!IF_EnableLatch.stall_switch())
		{
			e.setEventTime(Clock.getCurrentTime()+1);
			Simulator.getEventQueue().addEvent(e);
		}
		else
		{
			int currentPC;
			currentPC = containingProcessor.getRegisterFile().getProgramCounter();
			MemoryResponseEvent event;
			event = (MemoryResponseEvent) e;
			IF_OF_Latch.setInstruction(event.getValue());
			containingProcessor.getRegisterFile().setProgramCounter(currentPC + 1);
			Simulator.increment_dynamic_instructions();
			if(IF_EnableLatch.wrong_branch_taken())
			{
				setNOPtotrueinIFOFlatch();
				OF_EX_Latch.setoptype(Instruction.OperationType.valueOf("nop"));
				containingProcessor.getRegisterFile().setProgramCounter(IF_EnableLatch.get_correct_pc());
				IF_EnableLatch.set_wrong_branch_taken(false);
			}
			setIFbusytofalse();
			enableOFstage();
			enableEXstage();
			enableMAstage();
			enableRWstage();
			
		}
	}

}
