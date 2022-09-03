package processor.memorysystem;
import processor.Processor;
import generic.*;
import java.math.BigInteger;

import static configuration.Configuration.*;
import static configuration.Configuration.L1d_associativity;
import static generic.Simulator.*;
import static processor.Clock.*;

public class Cache implements Element{

    public int getTag(String binary_address, int num_sets)
    {
        return(new BigInteger(binary_address.substring(0,33-NumberOfDigits(num_sets)),2).intValue());
    }
    public int getSet(String binary_address, int num_sets)
    {
        return NumberOfDigits(num_sets) == 1 ? (new BigInteger(binary_address.substring(31), 2).intValue()) : (new BigInteger(binary_address.substring(32 - NumberOfDigits(num_sets) + 1, 32), 2).intValue());
    }
    
    private void setICacheBusy() {
        containingProcessor.getCache_I().cache_busy = true;
    }
    private void setICacheFree() {
        containingProcessor.getCache_I().cache_busy = false;
    }
    private void setDCacheBusy() {
        containingProcessor.getCache_d().cache_busy = true;
    }
    private void setDCacheFree() {
        containingProcessor.getCache_d().cache_busy = false;
    }
    private void disallowIReads() {
        containingProcessor.getCache_I().read_request = false;
    }
    private void disallowDReads() {
        containingProcessor.getCache_d().read_request = false;
    }
    private void disallowIWrites() {
        containingProcessor.getCache_I().write_request = false;
    }
    private void disallowDWrites() {
        containingProcessor.getCache_d().write_request = false;
    }

    Processor containingProcessor;
    public CacheLine block;
    public CacheLine[] buffer;
    public Boolean read_request= false, write_request = false;
    public int set, tag, used;
    public Boolean cache_busy = false;
    Element direct_to;
    public Cache(Processor containingProcessor, int cache_type)
    {
        int L1dLine = L1d_numberOfLines+2;
        int L1iLine = L1i_numberOfLines+2;
        this.containingProcessor = containingProcessor;
        if(cache_type != 0)
        {
            buffer = new CacheLine[L1dLine];
            int k=0;
            while (k<L1dLine) {
                block = new CacheLine();
                buffer[k]=block;
                k++;
            }
        }
        else
        {
            buffer = new CacheLine[L1iLine];
            int k=0;
            while (k < L1iLine) {
                block = new CacheLine();
                buffer[k]=block;
                k++;
            }
        }

    }
    
    public int NumberOfDigits(int n)
    {
        int noOfDigits = (int)(Math.floor(
                Math.log(n) / Math.log(2)));
        return (noOfDigits + 1);
    }

    public String twosComplement(String bin)
    {
        StringBuilder ones = null;
        String twos;


        int len;
        len=bin.length();
        int initial=0;

        while(initial<len)
        {
            ones = (ones == null ? new StringBuilder("null") : ones).append(flip(bin.charAt(initial)));
            initial=initial+1;
        }

        StringBuilder builder;
        builder = new StringBuilder((ones == null) ? null : ones.toString());
        boolean b ;
        b = false;

        int len_of_ones;
        assert ones != null;
        len_of_ones=ones.length();
        int pointer;
        pointer=len_of_ones-1;

        while (pointer>=0)
        {
            if (ones.charAt(pointer) == '1')
        {
            builder.setCharAt(pointer, '0');
        }
        else
        {
            builder.setCharAt(pointer, '1');
            b = true;
            break;
        }
            pointer=pointer-1;
        }
        if (!b)
        {
            builder.append("1", 0, 7);
        }
        twos = builder.toString();
        return twos;
    }

    public char flip(char c) {
        char to_be_returned;
        if (c == '0')
            to_be_returned = '1';
        else
            to_be_returned = '0';

        return to_be_returned;
    }

    public void cacheRead_i(int address, CacheReadEvent_i event)
    {
        setICacheBusy();
        int val;
        int num_sets = L1i_numberOfLines/ L1i_associativity;
        String binary_address = address >= 0 ? String.format("%32s", Integer.toBinaryString(address)).replace(" ", "0") : twosComplement(String.format("%32s", Integer.toBinaryString(-address)).replace(" ", "0"));

        if (containingProcessor.getCache_I().buffer[L1i_associativity * getSet(binary_address,num_sets)].tag != getTag(binary_address,num_sets)) {
            if (containingProcessor.getCache_I().buffer[(L1i_associativity * getSet(binary_address,num_sets)) + 1].tag != getTag(binary_address,num_sets))
                handleCacheMiss(address, 0, 0, 0, getSet(binary_address, num_sets), getTag(binary_address, num_sets));
            else
            {
                val = containingProcessor.getCache_I().buffer[(L1i_associativity*getSet(binary_address,num_sets))+1].data[0];
                getEventQueue().addEvent(new CacheResponseEvent_i(getCurrentTime(),this, event.getRequestingElement(), val));
                setICacheFree();
                containingProcessor.getCache_I().used = 1;
            }
        }
        else
        {
            val = containingProcessor.getCache_I().buffer[L1i_associativity*getSet(binary_address,num_sets)].data[0];
            getEventQueue().addEvent(new CacheResponseEvent_i(getCurrentTime(),this, event.getRequestingElement(), val));
            setICacheFree();
            containingProcessor.getCache_I().used = 0;
        }

    }

    public void cacheWrite_i(int address, int value)
    {
        setICacheBusy();
        int num_sets = L1i_numberOfLines/ L1i_associativity;
        String binary_address = address >= 0 ? String.format("%32s", Integer.toBinaryString(address)).replace(" ", "0") : twosComplement(String.format("%32s", Integer.toBinaryString(-address)).replace(" ", "0"));

        if (containingProcessor.getCache_I().buffer[L1i_associativity * getSet(binary_address, num_sets)].tag != getTag(binary_address, num_sets)) {
            if (containingProcessor.getCache_I().buffer[(L1i_associativity * getSet(binary_address, num_sets)) + 1].tag != getTag(binary_address, num_sets)) {
                int lru = containingProcessor.getCache_I().used == 0 ? 1 : 0;
                handleCacheMiss(address, 0, 1, value, getSet(binary_address,num_sets), getTag(binary_address,num_sets));
                containingProcessor.getCache_I().buffer[(L1i_associativity*getSet(binary_address,num_sets))+lru].data[0]=value;
                containingProcessor.getCache_I().buffer[(L1i_associativity*getSet(binary_address,num_sets))+lru].tag = getTag(binary_address,num_sets);

            } else {
                containingProcessor.getCache_I().buffer[(L1i_associativity*getSet(binary_address,num_sets))+1].data[0]=value;
                handleCacheMiss(address, 0, 1, value, getSet(binary_address,num_sets), getTag(binary_address,num_sets));
                containingProcessor.getCache_I().used = 1;

            }
        } else {
            containingProcessor.getCache_I().buffer[L1i_associativity*getSet(binary_address,num_sets)].data[0]=value;
            handleCacheMiss(address, 0, 1, value, getSet(binary_address,num_sets), getTag(binary_address,num_sets));
            containingProcessor.getCache_I().used = 0;
        }
    }
    public void cacheRead_d(int address, CacheReadEvent_d event)
    {
        setDCacheBusy();
        int val;
        int num_sets = L1d_numberOfLines/ L1d_associativity;
        String binary_address = address >= 0 ? String.format("%32s", Integer.toBinaryString(address)).replace(" ", "0") : twosComplement(String.format("%32s", Integer.toBinaryString(-address)).replace(" ", "0"));
        if(containingProcessor.getCache_d().buffer[L1d_associativity*getSet(binary_address,num_sets)].tag==getTag(binary_address,num_sets))
        {
            val = containingProcessor.getCache_d().buffer[L1d_associativity*getSet(binary_address,num_sets)].data[0];
            getEventQueue().addEvent(new CacheResponseEvent_d(getCurrentTime(),this, event.getRequestingElement(), val));
            setDCacheFree();
            containingProcessor.getCache_d().used = 0;
        }
        else if(containingProcessor.getCache_d().buffer[(L1d_associativity*getSet(binary_address,num_sets))+1].tag==getTag(binary_address,num_sets))
        {
            val = containingProcessor.getCache_d().buffer[(L1d_associativity*getSet(binary_address,num_sets))+1].data[0];
            getEventQueue().addEvent(new CacheResponseEvent_d(getCurrentTime(),this, event.getRequestingElement(), val));
            setDCacheFree();
            containingProcessor.getCache_d().used = 1;
        }
        else
            handleCacheMiss(address, 1, 0, 0, getSet(binary_address,num_sets), getTag(binary_address,num_sets)); /* memory read request */
    }
    public void cacheWrite_d(int address, int value)
    {

        int num_sets = L1d_numberOfLines / L1d_associativity;
        String binary_address = address >= 0 ? String.format("%32s", Integer.toBinaryString(address)).replace(" ", "0") : twosComplement(String.format("%32s", Integer.toBinaryString(-address)).replace(" ", "0"));
        int tag = getTag(binary_address,num_sets);
        int set = getSet(binary_address,num_sets);
        if (containingProcessor.getCache_d().buffer[L1d_associativity * set].tag != tag) {
            if (containingProcessor.getCache_d().buffer[L1d_associativity * set + 1].tag != tag) {
                int lru = 0;
                if(containingProcessor.getCache_d().used == 0)
                {
                    lru = 1;
                }
                handleCacheMiss(address, 1, 1, value, set, tag);
                containingProcessor.getCache_d().buffer[L1d_associativity*set +lru].data[0]=value;
                containingProcessor.getCache_d().buffer[L1d_associativity*set +lru].tag = tag;

            } else {
                containingProcessor.getCache_d().buffer[L1d_associativity*set +1].data[0]=value;
                handleCacheMiss(address, 1, 1, value, set, tag);
                containingProcessor.getCache_d().used = 1;
            }
        } else {
            containingProcessor.getCache_d().buffer[L1d_associativity*set].data[0]=value;
            handleCacheMiss(address, 1, 1, value, set, tag);
            containingProcessor.getCache_d().used = 0;

        }
    }
    public void handleCacheMiss(int address, int cache_type, int request_type, int write_value, int set, int tag)
    {
        containingProcessor.getCache_I().set = set;
        containingProcessor.getCache_I().tag = tag;
        switch(cache_type) {
            case 0:
                containingProcessor.getCache_I().read_request = true;
                switch (request_type) {
                    case 0:
                        getEventQueue().addEvent(new MemoryReadEvent(getCurrentTime() + mainMemoryLatency, this, containingProcessor.getMainMemory(), address));
                        break;
                    case 1:
                        getEventQueue().addEvent(new MemoryWriteEvent(getCurrentTime() + mainMemoryLatency, this, containingProcessor.getMainMemory(), address, write_value));
                        break;
                }
                break;
            case 1:
                containingProcessor.getCache_d().read_request = true;
                switch (request_type) {
                    case 0:
                        getEventQueue().addEvent(new MemoryReadEvent(getCurrentTime()+ mainMemoryLatency,this,containingProcessor.getMainMemory(),address));
                        break;
                    case 1:
                        getEventQueue().addEvent(new MemoryWriteEvent(getCurrentTime()+ mainMemoryLatency,this,containingProcessor.getMainMemory(),address,write_value));
                        break;
                }
                break;
        }
    }
    public void handleEvent(Event e)
    {
        MemoryResponseEvent responseEvent;
        switch (e.getEventType()) {
            case ExecutionComplete: //do nothing
            case MemoryRead:
            case MemoryWrite:
            case CacheResponse_i:
            case CacheResponse_d:
                break;
            case MemoryResponse:
                responseEvent = (MemoryResponseEvent) e;
                if (!containingProcessor.getCache_I().read_request) {
                    if (!containingProcessor.getCache_d().read_request) {
                        if (!containingProcessor.getCache_I().write_request) {
                            if (containingProcessor.getCache_d().write_request)
                            {
                                //data memory write request received
                                int val = responseEvent.getValue();
                                getEventQueue().addEvent(new CacheResponseEvent_d(getCurrentTime(), this, containingProcessor.getCache_d().direct_to, val));
                                disallowDWrites();
                                setDCacheFree();
                            }
                        }
                        else
                        {
                            //instruction memory write request received
                            int val = responseEvent.getValue();
                            getEventQueue().addEvent(new CacheResponseEvent_i(getCurrentTime(), this, containingProcessor.getCache_I().direct_to, val));
                            disallowIWrites();
                            setICacheFree();
                        }
                    }
                    else
                    {
                        //data mem read request received
                        int lru = containingProcessor.getCache_d().used == 0 ? 1 : 0;
                        int val = responseEvent.getValue();
                        containingProcessor.getCache_d().buffer[L1i_associativity * containingProcessor.getCache_d().set + lru].data[0] = val;
                        containingProcessor.getCache_d().buffer[L1i_associativity * containingProcessor.getCache_d().set + lru].tag = containingProcessor.getCache_d().tag;
                        getEventQueue().addEvent(new CacheResponseEvent_d(getCurrentTime(), this, containingProcessor.getCache_d().direct_to, val));
                        disallowDReads();
                        setDCacheFree();
                    }
                }
                else
                {
                    //instruction mem read request received
                    int lru = containingProcessor.getCache_I().used == 0 ? 1 : 0;
                    int val = responseEvent.getValue();
                    containingProcessor.getCache_I().buffer[L1i_associativity * containingProcessor.getCache_I().set + lru].data[0] = val;
                    containingProcessor.getCache_I().buffer[L1i_associativity * containingProcessor.getCache_I().set + lru].tag = containingProcessor.getCache_I().tag;
                    getEventQueue().addEvent(new CacheResponseEvent_i(getCurrentTime(), this, containingProcessor.getCache_I().direct_to, val));
                    disallowIReads();
                    setICacheFree();
                }

                break;
            case CacheRead_i:
                if (containingProcessor.getCache_I().cache_busy)
                {
                    //i cache busy
                    e.setEventTime(getCurrentTime() + 1);
                    getEventQueue().addEvent(e);
                }
                else
                {
                    //send i cache read request
                    CacheReadEvent_i event = (CacheReadEvent_i) e;
                    containingProcessor.getCache_I().direct_to = event.getRequestingElement();
                    cacheRead_i(event.getReadAddress(), event);
                }
                break;
            case CacheWrite_i:
                if (containingProcessor.getCache_I().cache_busy)
                {
                    //i cache busy
                    e.setEventTime(getCurrentTime() + 1);
                    getEventQueue().addEvent(e);
                } else {
                    // send i cache write request
                    CacheWriteEvent_i event = (CacheWriteEvent_i) e;
                    containingProcessor.getCache_I().direct_to = event.getRequestingElement();
                    cacheWrite_i(event.getWriteAddress(), event.getValue());
                }
                break;
            case CacheRead_d:
                if (containingProcessor.getCache_d().cache_busy)
                {
                    // d cache busy
                    e.setEventTime(getCurrentTime() + 1);
                    getEventQueue().addEvent(e);
                }
                else
                {
                    // send d cache read request
                    CacheReadEvent_d event = (CacheReadEvent_d) e;
                    containingProcessor.getCache_d().direct_to = event.getRequestingElement();
                    cacheRead_d(event.getReadAddress(), event);
                }

                break;
            case CacheWrite_d:
                if (containingProcessor.getCache_d().cache_busy) {
                    // d cache busy
                    e.setEventTime(getCurrentTime() + 1);
                    getEventQueue().addEvent(e);

                }
                else
                {
                    // send d cache write request
                    CacheWriteEvent_d event = (CacheWriteEvent_d) e;
                    containingProcessor.getCache_d().direct_to = event.getRequestingElement();
                    cacheWrite_d(event.getWriteAddress(), event.getValue());
                }

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + e.getEventType());
        }
    }

}


