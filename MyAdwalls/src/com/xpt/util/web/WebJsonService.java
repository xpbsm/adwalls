package com.xpt.util.web;

import java.io.InputStream;
import java.net.URLEncoder;
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

import com.google.gson.Gson;
import com.xpt.util.bean.User;


import android.util.Log;


public class WebJsonService {

    // IP地址
    private static String IP = "192.168.1.101:8080";
    private static   User tempuser;
    private static String   jsonString;

    
   
    
    /**
     *注册界面
     * @param userName
     * @param password
     * @param npwd
     * @return
     */
    
    public static String executeHttpPost(String realname,String userName, String  password) {

        try {
            //String path = "http://" + IP + "/HelloWeb/RegJsonLet";
        	String path = "http://" + IP + "/adWall/APP/user/registerUser.do";
            Log.v("path","path"+path);
            // 发送指令和信息
            Map<String, String> params = new HashMap<String, String>();
            params.put("name"  ,   realname);
            params.put("userName", userName);
            params.put("password", password);
         
            return sendPOSTRequest(path, params, "UTF-8");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "服务器连接超时";
    }
    
    
   
    
    
	public static String executeHttpPost(double lat, double lng) {
		// TODO Auto-generated method stub
		
		 try {
	            //String path = "http://" + IP + "/HelloWeb/RegJsonLet";
	        	String path = "http://" + IP + "/adWall/APP/user/registerUser.do";
	            Log.v("path","path"+path);
	            // 发送指令和信息
	            Map<String, String> params = new HashMap<String, String>();
	            params.put("name"          , lat+"");
	            params.put("userName", lng+"");
	       
	         
	            return sendPOSTRequest(path, params, "UTF-8");
	        } catch (Exception e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		
		
		
		
		
		return null;
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
                Log.v("params",entry.getValue());
            }
        }

        HttpClient httpClient=new DefaultHttpClient();
                   //设置请求路径        
                     HttpPost post=new HttpPost(path);  
                     //设置请求参数
                     post.setEntity(new UrlEncodedFormEntity(pairs,"UTF-8"));    
                     //设置用户识别
                     post.addHeader("User-Agent", " ");
                    // post.setHeader("Content-Type", "application/json;charset=utf-8");
                      //建立HttpParams对象
                    HttpParams httpParams=post.getParams();
                     //设置读取超时
                     httpParams.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);
                    //设置请求超时
                    httpParams.setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
                    //发送请求获得回馈
                  HttpResponse httpResponse=httpClient.execute(post);
                  
        HttpResponse response = httpClient.execute(post);
        
        Log.v("Poststatus",response.getStatusLine().getStatusCode()+"");
        // 判断是否成功收取信息
        if (response.getStatusLine().getStatusCode() == 200) {
            return getInfo(response);
        }

        // 未成功收取信息，返回空指针
        return null;
    }

    private static String sendPOSTRequest(String path,String jsonString, String encoding) throws Exception {
    	HttpClient httpClient = new DefaultHttpClient();  
        try {  
  
            HttpPost httpPost = new HttpPost(path); 
            HttpParams httpParams = new BasicHttpParams();  
            Log.v("jsonString",jsonString);
            List<NameValuePair>  list=new ArrayList<NameValuePair>();
    		NameValuePair nvp=new BasicNameValuePair("jsonstring",jsonString);
    		list.add(nvp);
           /* List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();  
            
            nameValuePair.add(new BasicNameValuePair("jsonString", URLEncoder  
                    .encode(jsonString, "utf-8")));  */
            
            httpPost.setEntity(new UrlEncodedFormEntity(list,HTTP.UTF_8));
            httpPost.setParams(httpParams); 
           // httpPost.setHeader("Content-Type", "application/json;charset=utf-8");
            //httpPost.addHeader("User-Agent", " ");
            httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
            httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
            
           // httpClient.execute(httpPost);  
           
            HttpResponse response = httpClient.execute(httpPost);  
            
            StatusLine statusLine = response.getStatusLine();  
            Log.v("Poststatus",statusLine.getStatusCode()+"");
            
            if (statusLine != null && statusLine.getStatusCode() == 200) {  
            	Log.v("Poststatus",statusLine.getStatusCode()+"");
                HttpEntity entity = response.getEntity();  
                if (entity != null) {  
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
