package ie.gmit.sw;

import java.util.ArrayList;
import java.util.concurrent.BlockingQueue;

/**
 * @author Tangqi Feng
 * @version 1.0
 *
 * This is the bean class,
 * store a job object, used to identify multi requests from clients
 */
public class Job {
    private String taskNumber;
    private String docTitle;
    private Double result;
    private ArrayList<Shingle> shingles;
    private BlockingQueue<Shingle> BlockingQueueShingles;

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

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public ArrayList<Shingle> getShingles() {
        return shingles;
    }

    public void setShingles(ArrayList<Shingle> shingles) {
        this.shingles = shingles;
    }

    public BlockingQueue<Shingle> getBlockingQueueShingles() {
        return BlockingQueueShingles;
    }

    public void setBlockingQueueShingles(BlockingQueue<Shingle> blockingQueueShingles) {
        BlockingQueueShingles = blockingQueueShingles;
    }
}
