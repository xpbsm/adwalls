package com.xpt.util.web;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.xpt.util.bean.AdWall;
import com.xpt.util.bean.User;
import com.xpt.util.bean.wallinfo;


public class JsonUtil {  
	
    public List<User> StringFromJson (String jsondata)  
    {       
        Type listType = new TypeToken<List<User>>(){}.getType();  
        Gson gson=new Gson();  
        ArrayList<User> list=gson.fromJson(jsondata, listType);  
        return list;  
  
  
    } 
    
    public static List<?> StringFromJson2 (String jsondata)  
    {      
        Type listType = new TypeToken<List<?>>(){}.getType();  
        Gson gson=new Gson();  
        ArrayList<?> list=gson.fromJson(jsondata, listType);  
        return list;  
  
  
    } 
    
    
    public List<AdWall> StringFromWallsJson(String jsondata)  
    {       
        Type listType = new TypeToken<List<AdWall>>(){}.getType();  
        Gson gson=new Gson();  
        ArrayList<AdWall> list=gson.fromJson(jsondata, listType);  
        return list;  
  
  
    } 
    
    public List<wallinfo> StringFromwallinfoJson(String jsondata)  
    {       
        Type listType = new TypeToken<List<wallinfo>>(){}.getType();  
        Gson gson=new Gson();  
        ArrayList<wallinfo> list=gson.fromJson(jsondata, listType);  
        return list;  
  
  
    } 
    /**
     * 声明为泛型类型 static <T>
     * 
     * @param jsonString
     * @param cls
     * @return
     */
    public static <T> List<T> getObjectList(String jsonString,Class<T> cls){  
        List<T> list = new ArrayList<T>();  
        try {  
            Gson gson = new Gson();  
            JsonArray arry = new JsonParser().parse(jsonString).getAsJsonArray();  
            for (JsonElement jsonElement : arry) {  
                list.add(gson.fromJson(jsonElement, cls));  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return list;  
    }  
   
    
    

   /* public static void main(String[] args) {
		
    	getObjectList("nihao", AdWall.class);
    	List li=new ArrayList<AdWall>();
        	AdWall ad=new AdWall(1,103.95954500,30.78109800,"西华大学图书馆","http://www.bkill.com/u/info_img/2012-09/02/2012083116140522302.jpg");
    	    
        	StringFromJson2("nihao");
    	
    	
	}*/
}