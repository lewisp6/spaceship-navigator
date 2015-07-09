package com.example.spaceshipnavigator.Database;

public class Score {
    public long timeStamp;
    public String person;
    public int score;

    public Score(long timeStamp, String person, int score) {
        this.timeStamp = timeStamp;
        this.person = person;
        this.score = score;
    }
}
