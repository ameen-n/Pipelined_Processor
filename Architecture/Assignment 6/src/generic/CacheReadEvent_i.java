package generic;
import static generic.Event.EventType.CacheRead_i;

public class CacheReadEvent_i extends Event {
	private int readAddress;
	public CacheReadEvent_i(long eventTime, Element requestingElement, Element processingElement, int address)
	{
		super(eventTime, CacheRead_i, requestingElement, processingElement);
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
