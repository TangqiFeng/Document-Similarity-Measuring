package ie.gmit.sw;

/**
 * @author Tangqi Feng
 * @version 1.0
 *
 * This is the bean class,
 * store a single shingle
 */
public class Shingle {
    private int docId;
    private int hashcode;

    public Shingle() {
    }

    public Shingle(int docId, int hashcode) {
        this.docId = docId;
        this.hashcode = hashcode;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public int getHashcode() {
        return hashcode;
    }

    public void setHashcode(int hashcode) {
        this.hashcode = hashcode;
    }
}
