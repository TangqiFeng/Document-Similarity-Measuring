package ie.gmit.sw;

import java.io.BufferedReader;

public class ShingleRequestPara {
    String[] words;
    int shingleSize;
    StringBuffer sb;

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
}
