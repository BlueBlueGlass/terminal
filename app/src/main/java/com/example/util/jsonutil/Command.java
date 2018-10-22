package com.example.util.jsonutil;

/**
 * Created by Administrator on 2018/9/13.
 */

public class Command {
    String time;
    String command;
    public Command(){

    }
    public Command(String time, String command) {
        this.time = time;
        this.command = command;
    }

    public String getTime() {
        if(time==null){
            return "null time";
        }
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCommand() {
        if(command==null){
            return "null command";
        }
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
