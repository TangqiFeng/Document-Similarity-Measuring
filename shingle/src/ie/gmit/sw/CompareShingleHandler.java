package ie.gmit.sw;

import java.util.Set;
import java.util.TreeSet;

public class CompareShingleHandler implements ShingleHandler {
    @Override
    public Object handleShingle(ShingleRequest shingleRequest, ShingleRequestPara para) throws Exception {
        // implement further chaining if required doRequest();

        return getJaccardValue(para.getIn_set(), para.getOut_set());
    }

    private Double getJaccardValue(Set in_set, Set db_set){
        Set n = new TreeSet(in_set);
        n.retainAll(db_set);
        // Jaccard index formula: |A n B| / ( |A| + |B| - |A U B| )
        double jaccardValue =  n.size() / (in_set.size() + db_set.size() - n.size() * 1.0);
        return jaccardValue;
    }
}
