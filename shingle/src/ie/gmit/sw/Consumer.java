package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

/**
 *
 * this class is used to get document's MinHash codes.
 * contains threadPool to calculate min hashs,
 * and put the resust to the map stored in Class "MapStore"
 *
 * @author Tangqi Feng
 * @version 1.0
 *
 */
public class Consumer implements Runnable {
    private BlockingQueue<Shingle> queue;
    private ExecutorService pool;
    private int[] minHashs;
    private int minhashNumber;
    private int poolSize;
    private int docId;

    /**
     * constructor
     *
     * @param queue blocking queue stores shingles
     * @param minhashNumber min hash size
     * @param poolSize thread number for calculate minhash
     * @param docId document id
     */
    public Consumer(BlockingQueue<Shingle> queue, int minhashNumber, int poolSize, int docId) {
        this.queue = queue;
        this.minhashNumber = minhashNumber;
        this.poolSize = poolSize;
        this.docId = docId;
        init();
    }

    /**
     * initial method, called in constructor
     * put initial value to minHashs and create ThreadPool
     */
    private void init(){
        Random r = new Random();
        minHashs = new int[minhashNumber];
        for (int i = 0; i < minhashNumber ; i++) {
            minHashs[i] = r.nextInt();
        }
        pool = Executors.newFixedThreadPool(poolSize);
    }

    @Override
    public void run() {
        ArrayList<Integer> hashs = new ArrayList<>(minhashNumber);
        hashs.forEach((h)->hashs.set(h,Integer.MAX_VALUE));
        while(queue.size()>0){
            Shingle next = new Shingle();
            try {
                next= queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Shingle finalNext = next;
            pool.execute(new Runnable() {
                public void run() {
                    for (int i = 0; i < minhashNumber; i++) {
                        int value = finalNext.getHashcode() ^ minHashs[i];
                        if (value > hashs.get(i)) {
                            hashs.set(i, value);
                        }
                    }
                }
            });
        }
        try {
            pool.awaitTermination(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MapStore.putMap(docId,hashs);
    }

}


/**
 * this class is used to store documents' MinHash codes.
 * used for calculate jaccard similarity with min-hash functions
 *
 * @author Tangqi Feng
 * @version 1.0
 */
class MapStore{
    protected static ConcurrentHashMap<Integer,List<Integer>> map =  new ConcurrentHashMap<>();

    /**
     * static method, can by called to add item in map
     */
    public static void putMap(Integer i, List<Integer> l){
        map.put(i,l);
    }

    /**
     static method, can by called to get map
     */
    public static Map<Integer,List<Integer>> getMap(){
        return map;
    }

    /**
     static method, can by called to remove item from map
     */
    public static void removeMap(String key){
        map.remove(key);
    }
}
