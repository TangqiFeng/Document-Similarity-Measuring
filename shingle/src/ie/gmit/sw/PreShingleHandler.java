package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * implement ShingleHandler
 * have a private method to return words string[] from bufferReader
 * based "The chain of responsibility design pattern"
 * this class act the start of the chain, which means all request for handlers indicates through this class
 */
public class PreShingleHandler implements ShingleHandler {

    /*
     * override the handleShingle method
     * use switch statement to redirect request to correct handler
     */
    public Object handleShingle(ShingleRequest shingleRequest, ShingleRequestPara para) throws Exception {
        // implement further chaining if required doRequest();
        // e.g. return List<Shingle> etc.
        switch (shingleRequest.getType()) {
            case ShingleRequest.getEngWords:
                return getEngWords(para.getStringBuffer());
                //break;
            // could have more cases here
            // e.g. getChineseWords, getJapanWords etc.

            case ShingleRequest.getShingleArrayList:
            case ShingleRequest.getShingleBlockingQueue:
                return new GenerateShingleHandler().handleShingle(shingleRequest, para);
                //break;

            case ShingleRequest.getJaccardValue:
            case ShingleRequest.getJaccardValueByMinhash:
                return new CompareShingleHandler().handleShingle(shingleRequest, para);
        }
        return null;
    }

    /*
     * get words string[] from bufferReader
     */
    private String getEngWords(StringBuffer sb) throws IOException {
        // store all words to a string array
        // use regex to get words, and store in a String array
        String txt = sb.toString().replaceAll("[^a-zA-Z]", " ");
        txt = txt.replaceAll("\\s{2,}", " ");
        //String [] words = txt.split(" ");
        return txt;
    }
}
