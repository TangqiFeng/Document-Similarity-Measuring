package ie.gmit.sw;
/**
 * This interface defines the abstract handleShingle method
 * all handlers which implements this obey "The chain of responsibility design pattern"
 *
 * @author Tangqi Feng
 * @version 1.0
 */
public interface ShingleHandler {
    public Object handleShingle(ShingleRequest shingleRequest, ShingleRequestPara para) throws Exception;
}
