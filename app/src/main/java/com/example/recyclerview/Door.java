package com.example.recyclerview;

/**
 * Created by Administrator on 2018/9/13.
 */

public class Door {
    String time;
    String content;

    public Door(){

    }

    public Door(String time, String content) {
        this.time = time;
        this.content = content;
    }
    public String getTime() {
        return time;
    }
    public String getContent() {
        return content;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
