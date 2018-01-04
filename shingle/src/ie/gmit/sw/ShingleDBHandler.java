package ie.gmit.sw;

import java.util.ArrayList;

public class ShingleDBHandler implements ShingleHandler {
    private ShingleDatabase db;

    public ShingleDBHandler(ShingleDatabase db) {
        this.db = db;
    }

    @Override
    public Object handleShingle(ShingleRequest shingleRequest, ShingleRequestPara para) throws Exception {
        switch (shingleRequest.getType()) {
            // could have more cases here
            // e.g. return List<Shingle> etc.

            case ShingleRequest.getShinglesFromDB:
                return getAllDocs();

            case ShingleRequest.saveShinglesToDB:
                saveDocToDB(para.getDoc());
        }
        return null;
    }

    /*
     * indicate database and save doc to database
     */
    private void saveDocToDB(Document doc){
        db.save(doc);
    }

    /*
     * indicate database and return document lists
     */
    private ArrayList<Document> getAllDocs(){
        return db.getAll();
    }
}
