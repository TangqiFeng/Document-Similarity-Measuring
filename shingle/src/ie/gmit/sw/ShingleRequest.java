package ie.gmit.sw;

public class ShingleRequest {
    public final static int getEngWords = 0;
    public final static int getShingleBlockingQueue = 10;
    private int type;
    public ShingleRequest(int type) throws Exception {
        if ((type == getEngWords) || (type == getShingleBlockingQueue)) {
            this.type = type; } else {
            throw new Exception("Illegal Request creation with unknown type"); }
    }
    public int getType() { return type;
    }
}
