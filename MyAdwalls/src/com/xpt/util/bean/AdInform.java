package com.xpt.util.bean;

import java.io.Serializable;
import java.util.Date;

public class AdInform implements Serializable {
	private Integer id;
	private Integer createUserId;
	private String title;
	private String text;
	private String image;
	private String viedo;
	private String associated_url;
	private String createTime;//创建时间
	private String  endTime;//结束时间
	
	public AdInform() {
		super();
	}

	public AdInform(Integer id, Integer createUserId, String title, String text, String image, String viedo,
			String associated_url, String createTime, String endTime) {
		super();
		this.id = id;
		this.createUserId = createUserId;
		this.title = title;
		this.text = text;
		this.image = image;
		this.viedo = viedo;
		this.associated_url = associated_url;
		this.createTime = createTime;
		this.endTime = endTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getViedo() {
		return viedo;
	}

	public void setViedo(String viedo) {
		this.viedo = viedo;
	}

	public String getAssociated_url() {
		return associated_url;
	}

	public void setAssociated_url(String associated_url) {
		this.associated_url = associated_url;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "AdInfrom [id=" + id + ", createUserId=" + createUserId + ", title=" + title + ", text=" + text
				+ ", image=" + image + ", viedo=" + viedo + ", associated_url=" + associated_url + ", createTime="
				+ createTime + ", endTime=" + endTime + "]";
	}


	
	
	
}
