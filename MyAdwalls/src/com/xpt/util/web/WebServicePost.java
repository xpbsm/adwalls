package com.xpt.util.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;

import com.xpt.util.bean.User;

import android.util.Log;

public class WebServicePost {

    // IP地址
    private static String IP = "192.168.1.101:8080";
    

    public static String getIP() {
		return IP;
	}

	public static void setIP(String iP) {
		IP = iP;
	}
	
	/**
	    *  登录
	    * @param userName
	    * @param password
	    * @return
	    */
	    public static String executeHttpPost(String userName, String password) {

	        try {
	           String path = "http://" + IP + "/adWall/APP/user/login.do";
	            Log.v("path","path"+path);
	           Map<String, String> params = new HashMap<String, String>();
	            params.put("userName", userName);
	            params.put("password", password);
	            Log.v("path",userName+"pwd"+password);
	            return sendPOSTRequest(path, params, "UTF-8");
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        return "服务器连接超时";
	    }

	    public static String executeHttpPost(String realname,String userName, String  password) {

	        try {
	           
	        	String path = "http://" + IP + "/adWall/APP/user/registerUser.do";
	            Log.v("path","path"+path);
	            // 发送指令和信息
	            Map<String, String> params = new HashMap<String, String>();
	            params.put("name" ,    realname);
	            params.put("userName", userName);
	            params.put("password", password);
	         
	            return sendPOSTRequest(path, params, "UTF-8");
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        return "服务器连接超时";
	    }
	    
	    
	
	
    
    
    public static String executeHttpPost(String jsonString ,int locid) {

        try {
            String path = "http://" + IP + "/HelloWeb/RegJsonLet";

            Log.v("path","path"+path);
            // 发送指令和信息
            Map<String, String> params = new HashMap<String, String>();
         
            params.put("locid", locid+"");
            params.put("jsonString",jsonString);

            return sendPOSTRequest(path, params, "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "服务器连接超时";
    }
    
    

    
	public static String executeHttpPost(String file) {
		
		 try {
	            String path = "http://" + IP + "/adWall/uploadimage";

	            Log.v("path","path"+path);
	            // 发送指令和信息
	            Map<String, String> params = new HashMap<String, String>();
	            params.put("file", file);
	        
	            return sendPOSTRequest(path, params, "UTF-8");
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        return "服务器连接超时";
		
	}


    /**
     * 处理发送数据请求
     *
     * @return
     */
    private static String sendPOSTRequest(String path, Map<String, String> params, String encoding) throws Exception {
        // TODO Auto-generated method stub
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, encoding);

        HttpPost post = new HttpPost(path);
        post.setEntity(entity);
        DefaultHttpClient client = new DefaultHttpClient();
        // 请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        // 读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        
        HttpResponse response = client.execute(post);

        // 判断是否成功收取信息
        if (response.getStatusLine().getStatusCode() == 200) {
            return getInfo(response);
        }

        // 未成功收取信息，返回空指针
        return null;
    }

    
    // 收取数据
    private static String getInfo(HttpResponse response) throws Exception {

        HttpEntity entity = response.getEntity();
        InputStream is = entity.getContent();
        // 将输入流转化为byte型
        byte[] data = WebService.read(is);
        // 转化为字符串
        return new String(data, "UTF-8");
    }

	



}
