package com.example.util.jsonutil;

import com.example.recyclerview.DoorBean;
import com.example.net.UserBean;

import java.util.List;

public class Responce {
    public  String connect_status;
    public  Info info;
    public  DoorBean doorBean;
	public UserBean userBean;
	public List<DoorBean> doorList;

     public Responce(){
    	 
     }
     
	public Responce(String connect_status, Info info, DoorBean doorBean) {
		super();
		this.connect_status = connect_status;
		this.info = info;
		this.doorBean= doorBean;
	}
	public List<DoorBean> getDoorList() {
		return doorList;
	}

	public void setDoorList(List<DoorBean> doorList) {
		this.doorList = doorList;
	}

	public String getConnect_status() {
		return connect_status;
	}
	public Responce setConnect_status(String connect_status) {
		this.connect_status = connect_status;
		return this;
	}
	public Info getInfo() {
		return info;
	}
	public Responce setInfo(Info info) {
		this.info = info;
		return this;
	}

	public DoorBean getDoorBean() {
		return doorBean;
	}

	public void setDoorBean(DoorBean doorBean) {
		this.doorBean = doorBean;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}
