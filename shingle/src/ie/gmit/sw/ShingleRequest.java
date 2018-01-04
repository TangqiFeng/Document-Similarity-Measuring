package ie.gmit.sw;

public class ShingleRequest {
    public final static int getEngWords = 0;
    public final static int getShingleArrayList = 10;
    public final static int getShingleBlockingQueue = 11;
    public final static int getJaccardValue = 20;
    public final static int getJaccardValueByMinhash = 21;
    public final static int getShinglesFromDB = 31;
    public final static int saveShinglesToDB = 32;
    private int type;
    public ShingleRequest(int type) throws Exception {
        if ((type == getEngWords) || (type == getShingleArrayList)
                || (type == getJaccardValue) || (type == getShingleBlockingQueue)
                || (type == getJaccardValueByMinhash) || (type == getShinglesFromDB)
                || (type == saveShinglesToDB)) {
            this.type = type; } else {
            throw new Exception("Illegal Request creation with unknown type"); }
    }
    public int getType() { return type;
    }
}
