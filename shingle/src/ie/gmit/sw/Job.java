package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;

/*
this is the bean class, stores job object
 */
public class Job {
    private String taskNumber;
    private String docTitle;
    private String result;
    private BlockingQueue<Shingle> shingles;

    public Job() {
    }

    public Job(String taskNumber, String docTitle) {
        this.taskNumber = taskNumber;
        this.docTitle = docTitle;
    }

    public String getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(String taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getDocTitle() {
        return docTitle;
    }

    public void setDocTitle(String docTitle) {
        this.docTitle = docTitle;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public BlockingQueue<Shingle> getShingles() {
        return shingles;
    }

    public void setShingles(BlockingQueue<Shingle> shingles) {
        this.shingles = shingles;
    }
}
