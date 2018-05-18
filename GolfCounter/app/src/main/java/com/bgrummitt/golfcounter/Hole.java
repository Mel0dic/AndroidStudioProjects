package com.bgrummitt.golfcounter;

public class Hole {

    private int holeNumber;
    private int score;

    public Hole(int holeNumber, int score) {
        this.holeNumber = holeNumber;
        this.score = score;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public void setHoleNumber(int holeNumber) {
        this.holeNumber = holeNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void increase(){
        score++;
    }

    public void decrease(){
        score = (score > 0) ? --score : 0;
    }

}
