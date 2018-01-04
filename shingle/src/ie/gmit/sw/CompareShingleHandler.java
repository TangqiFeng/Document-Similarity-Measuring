package ie.gmit.sw;

import java.util.Set;
import java.util.TreeSet;

public class CompareShingleHandler implements ShingleHandler {
    @Override
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

    /*
     this method calculate Jaccard Similarity using formula
     */
    private Double getJaccardValueByFomular(Set in_set, Set db_set){
        Set n = new TreeSet(in_set);
        n.retainAll(db_set);
        // Jaccard index formula: |A n B| / ( |A| + |B| - |A U B| )
        double jaccardValue =  n.size() / (in_set.size() + db_set.size() - n.size() * 1.0);
        return jaccardValue;
    }

    /*
     this method calculate Jaccard Similarity using MinHash function
     */
    private Double getJaccardValueByMinHash(Set in_set, Set db_set, int k){
        Set n = new TreeSet(in_set);
        n.retainAll(db_set);
        // Min Hash formula: |A n B| / k
        double jaccardValue =  n.size() / ( k * 1.0);
        return jaccardValue;
    }
}
