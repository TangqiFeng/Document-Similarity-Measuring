package ie.gmit.sw;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class GenerateShingleHandler implements ShingleHandler {

    @Override
    public Object handleShingle(ShingleRequest shingleRequest, ShingleRequestPara para) throws Exception {
        // implement further chaining if required doRequest();
        // e.g. return List<Shingle> etc.

        // this method return shingles in a blocking queue
        return getShingles(para.getWords(),para.getShingleSize());

    }

    private ArrayList<Shingle> getShingles(String[] words, int shingleSize) throws InterruptedException {
        ArrayList<Shingle> shingles = new ArrayList<>();
        // get shingles (3 words)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length/shingleSize; i++) {
            for (int j = 0; j < shingleSize; j++) {
                sb.append(words[shingleSize*i+j]);
            }
            Shingle s = new Shingle(0,sb.toString().toLowerCase().hashCode());
            sb.delete( 0, sb.length() );
            shingles.add(s);
        }
        // rest one or two words also save to a shingle
        if(words.length%shingleSize >= 1){
            sb.append(words[words.length-1]);
            if(words.length%shingleSize == 2){
                sb.append(words[words.length-2]);
            }
            Shingle s = new Shingle(0,sb.toString().toLowerCase().hashCode());
            sb.delete( 0, sb.length() );
            shingles.add(s);
        }
        return shingles;

    }


}
