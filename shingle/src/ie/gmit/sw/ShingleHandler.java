package ie.gmit.sw;
/**
 * @author Tangqi Feng
 * @version 1.0
 *
 * This interface defines the abstract handleShingle method
 * all handlers which implements this obey "The chain of responsibility design pattern"
 */
public interface ShingleHandler {
    public Object handleShingle(ShingleRequest shingleRequest, ShingleRequestPara para) throws Exception;
}
