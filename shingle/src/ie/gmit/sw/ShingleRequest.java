package ie.gmit.sw;

/**
 * This is the bean class,
 * includes all command or valid method of ShingleHandler
 * This class act as parameter to call different methods defined in ShingleHandlers
 * a part of "The chain of responsibility design pattern" component
 *
 * @author Tangqi Feng
 * @version 1.0
 * */
public class ShingleRequest {
    public final static int getEngWords = 0;
    public final static int getShingleArrayList = 10;
    public final static int getShingleBlockingQueue = 11;
    public final static int getJaccardValue = 20;
    public final static int getJaccardValueByMinhash = 21;
    private int type;
    public ShingleRequest(int type) throws Exception {
        if ((type == getEngWords) || (type == getShingleArrayList)
                || (type == getJaccardValue) || (type == getShingleBlockingQueue)
                || (type == getJaccardValueByMinhash)) {
            this.type = type; } else {
            throw new Exception("Illegal Request creation with unknown type"); }
        }
    public int getType() { return type;
    }
}
