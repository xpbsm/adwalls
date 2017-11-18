package com.xpt.util.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;

import android.util.Log;

public class WebInfoJson {

	 private static String IP = "192.168.1.101:8080";

	public static String executeHttpPost(String infoJsonString) {
	    

	        try {
	           String path = "http://" + IP + "/adWall/APP/adInform/addInformToAdWall.do";
	            Log.v("path","path"+path);
	           Map<String, String> params = new HashMap<String, String>();
	            params.put("ad",infoJsonString);
	            Log.v("ad",infoJsonString);
	            return sendPOSTRequest(path, params, "UTF-8");
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }

	        return "服务器连接超时";
	    }
	
	

private static String sendPOSTRequest(String path,Map<String, String> params, String encoding) throws Exception {
	HttpClient httpClient = new DefaultHttpClient();  
    try {  

    	 List<NameValuePair> pairs = new ArrayList<NameValuePair>();
         if (params != null && !params.isEmpty()) {
             for (Map.Entry<String, String> entry : params.entrySet()) {
                 pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
             }
         }
         UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, encoding);
        HttpPost httpPost = new HttpPost(path); 
    
        httpPost.setEntity(entity);
        //httpPost.addHeader("User-Agent", " ");
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
        HttpResponse response = httpClient.execute(httpPost);  
        StatusLine statusLine = response.getStatusLine();  
        Log.v("Poststatus",statusLine.getStatusCode()+"");
        if (statusLine != null && statusLine.getStatusCode() == 200) {  
        	Log.v("Poststatus",statusLine.getStatusCode()+"");
            HttpEntity en= response.getEntity();  
            if (en != null) {  
            	return getInfo(response);
            }
        }  
    } catch (Exception e) {  
        throw new RuntimeException(e);  
    }  
    
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
