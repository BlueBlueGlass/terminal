package com.example.recyclerview;

import java.util.Date;

public class DoorBean {
  String doorAction;
  String actionTime;
  public DoorBean(){

  }
	public DoorBean(String doorAction, String actionTime) {
		this.doorAction = doorAction;
		this.actionTime = actionTime;
	}

	public String getDoorAction() {
	return doorAction;
}
public void setDoorAction(String doorAction) {
	this.doorAction = doorAction;
}
public String getActionTime() {
	return actionTime;
}
public void setActionTime(String actionTime) {
	this.actionTime = actionTime;
}
  
}
