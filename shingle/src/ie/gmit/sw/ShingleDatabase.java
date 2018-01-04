package ie.gmit.sw;

import java.util.ArrayList;

public interface ShingleDatabase {
    public void save(Document doc);
    public ArrayList<Document> getAll();
    //public void deleteOne(Document doc);
    //public void deleteMore(ArrayList<Document> docs);
    // ... ...
}
