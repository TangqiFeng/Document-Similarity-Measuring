package ie.gmit.sw;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * implement ShingleHandler
 * have methods to return ArrayList/BlockingQueue etc. Shingles with parameter String[]
 * based "The chain of responsibility design pattern"
 * it is able to direct to more functions ...
 * */
public class GenerateShingleHandler implements ShingleHandler {
    /*
     * override the handleShingle method
     * use switch statement to choose correct method
     * e.g. getShingleArrayList, getShingleBlockingQueue
     */
    public Object handleShingle(ShingleRequest shingleRequest, ShingleRequestPara para) throws Exception {
        switch (shingleRequest.getType()) {
            // could have more cases here
            // e.g. return List<Shingle> etc.

            case ShingleRequest.getShingleArrayList:
                return getShinglesArrayList(para.getWords(),para.getShingleSize());

            case ShingleRequest.getShingleBlockingQueue:
                return getShinglesBlockingQueue(para.getWords(),para.getShingleSize());
        }
        return null;
    }

    /*
     return shingle arrayList,
     e.g. for Jaccard Similarity calculating by formula
     */
    private ArrayList<Shingle> getShinglesArrayList(String[] words, int shingleSize) throws InterruptedException {
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

    /*
     return shingle arrayList,
     e.g. for Jaccard Similarity calculating by MinHash function
     */
    private BlockingQueue<Shingle> getShinglesBlockingQueue(String[] words, int shingleSize) throws InterruptedException {
        BlockingQueue<Shingle> shingles = new LinkedBlockingDeque<>();
        // get shingles (3 words)
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length/shingleSize; i++) {
            for (int j = 0; j < shingleSize; j++) {
                sb.append(words[shingleSize*i+j]);
            }
            Shingle s = new Shingle(0,sb.toString().toLowerCase().hashCode());
            sb.delete( 0, sb.length() );
            shingles.put(s);
        }
        // rest one or two words also save to a shingle
        if(words.length%shingleSize >= 1){
            sb.append(words[words.length-1]);
            if(words.length%shingleSize == 2){
                sb.append(words[words.length-2]);
            }
            Shingle s = new Shingle(0,sb.toString().toLowerCase().hashCode());
            sb.delete( 0, sb.length() );
            shingles.put(s);
        }
        return shingles;
    }
}
