package ie.gmit.sw;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Tangqi Feng
 * @version 1.0
 *
 * implement ShingleHandler
 * have methods(default formula, minhash etc.) to return Jaccard result
 * based "The chain of responsibility design pattern"
 * it is able to direct to more functions ...
 * */
public class CompareShingleHandler implements ShingleHandler {
    /**
     * override the handleShingle method
     * use switch statement to choose correct method
     * e.g. getJaccardValue, getJaccardValueByMinhash
     *
     * @param shingleRequest shingle commands
     * @param para required parameters
     * @return Object
     * @throws Exception
     */
    public Object handleShingle(ShingleRequest shingleRequest, ShingleRequestPara para) throws Exception {
        switch (shingleRequest.getType()) {
            // could have more cases here
            // e.g. return List<Shingle> etc.

            case ShingleRequest.getJaccardValue:
                return getJaccardValueByFomular(para.getIn_set(), para.getOut_set());

            case ShingleRequest.getJaccardValueByMinhash:
                return getJaccardValueByMinHash(para.getIn_set(), para.getOut_set(), para.getK());
        }
        return null;
    }

    /**
     * this method calculate Jaccard Similarity using formula
     *
     * @param in_set upload file shingles
     * @param db_set compare shingles
     * @return jaccard similarity
     */
    private Double getJaccardValueByFomular(Set in_set, Set db_set){
        Set n = new TreeSet(in_set);
        n.retainAll(db_set);
        // Jaccard index formula: |A n B| / ( |A| + |B| - |A U B| )
        double jaccardValue =  n.size() / (in_set.size() + db_set.size() - n.size() * 1.0);
        return jaccardValue;
    }

    /**
     * this method calculate Jaccard Similarity using MinHash function
     *
     * @param in_set upload file shingles
     * @param db_set compare shingles
     * @return jaccard similarity
     */
    private Double getJaccardValueByMinHash(Set in_set, Set db_set, int k){
        Set n = new TreeSet(in_set);
        n.retainAll(db_set);
        // Min Hash formula: |A n B| / k
        double jaccardValue =  n.size() / ( k * 1.0);
        return jaccardValue;
    }
}
