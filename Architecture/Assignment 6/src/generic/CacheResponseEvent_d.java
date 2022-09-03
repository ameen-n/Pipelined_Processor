package generic;
import static generic.Event.EventType.*;

public class CacheResponseEvent_d extends Event {
	private int val;

	public int getValue() {
		return val;
	}
	public void setValue(int value) {
		val = value;
	}

	public int returnValue(){
		int returnValue;
		returnValue = val;
		return returnValue;
	}

	public CacheResponseEvent_d(long eventTime, Element requestingElement, Element processingElement, int value) {
		super(eventTime, CacheResponse_d, requestingElement, processingElement);
		setValue(value);
	}
}
