package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.IOException;

public class PreShingleHandler implements ShingleHandler {

    @Override
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

            case ShingleRequest.getShinglesFromDB:
            case ShingleRequest.saveShinglesToDB:
                return new ShingleDBHandler(para.getDb()).handleShingle(shingleRequest,para);
        }
        return null;
    }

    // get words string[] from bufferReader
    private String getEngWords(StringBuffer sb) throws IOException {
        // store all words to a string array
        // use regex to get words, and store in a String array
        String txt = sb.toString().replaceAll("[^a-zA-Z]", " ");
        txt = txt.replaceAll("\\s{2,}", " ");
        //String [] words = txt.split(" ");
        return txt;
    }
}
