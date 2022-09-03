package generic;
import static generic.Event.EventType.*;

public class CacheWriteEvent_d extends Event {

	private int writeAddress, value;

	public CacheWriteEvent_d(long eventTime, Element requestingElement, Element processingElement, int address, int value) {
		super(eventTime, CacheWrite_d, requestingElement, processingElement);
		setValue(value);
		writeAddress = address;
	}

	public int returnAddress(){
		int returnAddress;
		returnAddress = writeAddress;
		return returnAddress;
	}

	public int getWriteAddress() {
		return writeAddress;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
