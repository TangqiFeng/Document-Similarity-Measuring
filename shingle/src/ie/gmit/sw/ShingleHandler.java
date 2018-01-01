package ie.gmit.sw;
/*
this based chain of responsibility design pattern
 */
public interface ShingleHandler {
    public Object handleShingle(ShingleRequest shingleRequest, ShingleRequestPara para) throws Exception;
}
