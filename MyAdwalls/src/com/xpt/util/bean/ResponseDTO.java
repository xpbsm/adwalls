package com.xpt.util.bean;

public class ResponseDTO<T> {
	T data;//相应数据
	String message;//相应信息
	Integer status;//相应状态码，0表示失败，1表示成功。
	
	public ResponseDTO() {
		super();
		this.message="失败";
		this.status=0;
	}
	public ResponseDTO(T data, String message, Integer status) {
		super();
		this.data = data;
		this.message = message;
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
/*public static void main(String[] args) {
	
	ResponseDTO re = new ResponseDTO<String>("1","2",1);
	
	
}*/
}
