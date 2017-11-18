package com.xpt.util.bean;

public class AdWall {
	//广告墙用户不能添加，因为涉及到AR去识别，需要对特定广告墙去识别。
	private Integer       id;
	private Double longitude;//经度
	private Double latitude;//纬度
	private String   title;
	private String   image;//图片url
	 
	public AdWall() {
		super();
	}

	public AdWall(Integer id, Double longitude, Double latitude, String title, String image) {
		super();
		this.id = id;
		this.longitude = longitude;
		this.latitude = latitude;
		this.title = title;
		this.image = image;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Override
	public String toString() {
		return "AdWall [id=" + id + ", longitude=" + longitude + ", latitude="
				+ latitude + ", title=" + title + ", image=" + image + "]";
	}

/*	public static void main(String[] args) {
		AdWall ad=new AdWall(1,2.0,2.0,"nihao","dfd");
		AdWall ad1=new AdWall(Integer.valueOf(1),2.0,2.0,"nihao","dfd");
		
	}
	*/
	
}
