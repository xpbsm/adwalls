package com.xpt.util.bean;

import java.io.Serializable;

public class wallinfo implements Serializable {
 int         id;
 int         locid;
 int         userid;
 String   title;
 String   info ;   
 String   imageurl;
 String   url;  
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public int getLocid() {
	return locid;
}
public void setLocid(int locid) {
	this.locid = locid;
}
public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getInfo() {
	return info;
}
public void setInfo(String info) {
	this.info = info;
}
public String getImageurl() {
	return imageurl;
}
public void setImageurl(String imageurl) {
	this.imageurl = imageurl;
}
public String getUrl() {
	return url;
}
public void setUrl(String url) {
	this.url = url;
}
@Override
public String toString() {
	return "wallinfo [id=" + id + ", locid=" + locid + ", userid=" + userid
			+ ", title=" + title + ", info=" + info + ", imageurl=" + imageurl
			+ ", url=" + url + "]";
}
public wallinfo(int id, int locid, int userid, String title, String info,
		String imageurl, String url) {
	super();
	this.id = id;
	this.locid = locid;
	this.userid = userid;
	this.title = title;
	this.info = info;
	this.imageurl = imageurl;
	this.url = url;
}
public  wallinfo(){
	
}
public wallinfo(int locid, int userid, String title, String info,
		String imageurl, String url) {
	super();
	this.locid = locid;
	this.userid = userid;
	this.title = title;
	this.info = info;
	this.imageurl = imageurl;
	this.url = url;
}


}
