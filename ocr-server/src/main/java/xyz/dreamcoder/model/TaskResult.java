package xyz.dreamcoder.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class TaskResult {

    @Id
    @GeneratedValue
    private long id;
    private int position;

    @Lob
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @JsonIgnore
    public String getPositionText() {

        int totalSeconds = position / 1000;
        int second = totalSeconds % 60;
        int minute = (totalSeconds - second) / 60 % 60;
        int hour = (totalSeconds - minute * 60 - second) / 3600 % 24;

        return (hour < 10 ? "0" + hour : String.valueOf(hour)) + ":"
                + (minute < 10 ? "0" + minute : String.valueOf(minute)) + ":"
                + (second < 10 ? "0" + second : String.valueOf(second));
    }
}