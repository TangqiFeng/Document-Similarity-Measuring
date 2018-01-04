package ie.gmit.sw;

import com.db4o.*;
import com.db4o.config.*;
import com.db4o.ta.*;
import xtea_db4o.*;

import java.util.ArrayList;

public class DB4OShingleDatabase implements ShingleDatabase {
    private String dataPath;
    private ObjectContainer db = null;

    public DB4OShingleDatabase(String dataPath) {
        this.dataPath = dataPath;
        init();
    }

    private void init(){
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().add(new TransparentActivationSupport()); //Real lazy. Saves all the config commented out below
        config.common().add(new TransparentPersistenceSupport()); //Lazier still. Saves all the config commented out below
        config.common().updateDepth(7); //Propagate updates

        //Use the XTea lib for encryption. The basic Db4O container only has a Caesar cypher... Dicas quod non est ita!
        config.file().storage(new XTeaEncryptionStorage("password", XTEA.ITERATIONS64));

		/*
		config.common().objectClass(Patient.class).cascadeOnUpdate(true);
		config.common().objectClass(Patient.class).cascadeOnActivate(true);
		config.common().objectClass(MDTReview.class).cascadeOnUpdate(true);
		config.common().objectClass(MDTReview.class).cascadeOnActivate(true);
		config.common().objectClass(User.class).cascadeOnUpdate(true);
		config.common().objectClass(HospitalList.class).cascadeOnUpdate(true);
		config.common().objectClass(TumourSet.class).cascadeOnUpdate(true);
		config.common().objectClass(GPLetter.class).cascadeOnUpdate(true);
		*/

        //Open a local database. Use Db4o.openServer(config, server, port) for full client / server
        db = Db4oEmbedded.openFile(config, dataPath);
    }

    @Override
    public void save(Document doc) {
        db.store(doc);
    }

    @Override
    public ArrayList<Document> getAll() {
        ObjectSet<Document> docs = db.query(Document.class);
        // just for tesing
        if (docs.size() == 0){
            ArrayList<Integer> list = new ArrayList<>();
            list.add(-761363859);
            list.add(1650764646);
            list.add(239957193);
            list.add(110251550);
            Document d = new Document();
            d.setShingles(list);
            ArrayList<Document> lists = new ArrayList<>();
            lists.add(d);
            return lists;
        }
        return new ArrayList<Document>(docs);
    }
}
