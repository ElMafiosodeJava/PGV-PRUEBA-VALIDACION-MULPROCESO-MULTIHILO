package Tema_2;

import Tema_1.TextUtils;

public class CountThread extends Thread {

    private String text;
    private String word;
    private int result;

    public CountThread(String text, String word) {
        this.text = text;
        this.word = word;
    }

    @Override
    public void run() {
        result = TextUtils.countWord(text, word);
    }

    public int getResult() {
        return result;
    }
}