package com.kadiomr.guessgame;

public class Word {

    long id;
    String word;
    int answered;

    public Word(long id, String word, int answered) {
        this.id = id;
        this.word = word;
        this.answered = answered;}

    public Word(String word) {
        this.word = word;
        this.answered = 0;}

    public Word() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getAnswered() {
        return answered;
    }

    public void setAnswered(int answered) {
        this.answered = answered;
    }
}