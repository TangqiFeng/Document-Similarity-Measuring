package ie.gmit.sw;

import java.io.BufferedReader;
import java.util.Set;

public class ShingleRequestPara {
    private String[] words;
    private int shingleSize;
    private StringBuffer sb;
    private Set in_set;
    private Set out_set;

    public ShingleRequestPara() {
    }

    public String[] getWords() {
        return words;
    }

    public void setWords(String[] words) {
        this.words = words;
    }

    public int getShingleSize() {
        return shingleSize;
    }

    public void setShingleSize(int shingleSize) {
        this.shingleSize = shingleSize;
    }

    public StringBuffer getStringBuffer() {
        return sb;
    }

    public void setStringBuffer(StringBuffer sb) {
        this.sb = sb;
    }

    public Set getIn_set() {
        return in_set;
    }

    public void setIn_set(Set in_set) {
        this.in_set = in_set;
    }

    public Set getOut_set() {
        return out_set;
    }

    public void setOut_set(Set out_set) {
        this.out_set = out_set;
    }
}
