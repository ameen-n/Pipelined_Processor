package generic;
import static generic.Event.EventType.CacheRead_d;

public class CacheReadEvent_d extends Event {
	private int readAddress;
	public CacheReadEvent_d(long eventTime, Element requestingElement, Element processingElement, int address)
	{
		super(eventTime, CacheRead_d, requestingElement, processingElement);
		readAddress = address;
	}

	public int returnAddress(){
		int returnAddress;
		returnAddress = readAddress;
		return returnAddress;
	}
	public int getReadAddress() {
		return readAddress;
	}
}
