package ie.gmit.sw;

import com.db4o.reflect.generic.GenericObject;

import java.util.ArrayList;

public class Document {
    private ArrayList<Integer> shingles;
    private int docId;
    private String docTitle;

    public Document(ArrayList<Integer> shingles, int docId, String docTitle) {
        this.shingles = shingles;
        this.docId = docId;
        this.docTitle = docTitle;
    }

    public Document(ArrayList<Integer> shingles, String docTitle) {
        this.shingles = shingles;
        this.docTitle = docTitle;
    }

    public Document() {
    }

    public ArrayList<Integer> getShingles() {
        return shingles;
    }

    public void setShingles(ArrayList<Integer> shingles) {
        this.shingles = shingles;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }
}
