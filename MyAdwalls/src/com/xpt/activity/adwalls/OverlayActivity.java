package com.xpt.activity.adwalls;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.Img.MyImgBtn;
import com.example.adwalls.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xpt.activity.login.MainActivity;
import com.xpt.activity.login.MainActivity.MyThread;

import com.xpt.util.bean.ResponseDTO;
import com.xpt.util.bean.wallinfo;
import com.xpt.util.bean.AdWall;
import com.xpt.util.web.JsonUtil;
import com.xpt.util.web.WebJsonService;
import com.xpt.util.web.WebService;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * 演示覆盖物的用法
 */
public class OverlayActivity extends Activity {

    /**
     * MapView 是地图主控件
     */
	//更新ui返回的数据
	private static Handler          handler=new Handler();
	//overlay
    private MapView                  mMapView;
    private BaiduMap                 mBaiduMap;
    
    private Marker                   mMarkerB;
    private List<AdWall>             li=null;
    
    private InfoWindow               mInfoWindow;
 
    //location 
    private Button                   requestLocButton;  
    private LocationMode             mCurrentMode;  
    private LocationClient           mLocClient;  
    public  MyLocationListenner      myListener = new MyLocationListenner();  
    private BitmapDescriptor         mCurrentMarker; 
    //测试infowindow
   //MyImgBtn
    private MyImgBtn                 myImgBtn;
    private DisplayImageOptions      options;
    private ImageLoader              imageLoader;
    private String                   url,imgtitle;
    private int                      wallId;
    private LatLng                   initll;
    private double                  lat;
    private double                  lng;
    private ProgressDialog           dialog;
    private ResponseDTO              response;
    // 初始化全局 bitmap 信息，不用时及时 recycle
   
    BitmapDescriptor bdB = BitmapDescriptorFactory
            .fromResource(R.drawable.icon_markb);
   
   
    BitmapDescriptor  Convert = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overlay);
      
        /*AdWall ws1=new AdWall(Integer.valueOf(1),"西华大学图书馆",103.959545,30.781098, "http://cdn.duitang.com/uploads/blog/201308/18/20130818150526_Ru2Bk.thumb.600_0.png");
        AdWall ws2=new AdWall(Integer.valueOf(2),"西华大学南门",103.956433,30.775138, "http://www.bkill.com/u/info_img/2012-09/02/2012083116140522302.jpg");
        AdWall ws3=new AdWall(Integer.valueOf(3),"成都植物园",104.135263,30.7714, "http://www.jhq8.cn/qqtouxiang/UploadPic/2012-9/201291016107737.jpg");
        AdWall ws4=new AdWall(Integer.valueOf(4),"西华大学6教",103.962132,30.783416, "http://www.qqcan.com/uploads/allimg/c120822/13455c923250-91E45.jpg");
        li.add(ws1);
        li.add(ws2);
        li.add(ws3);116.33327800
        li.add(ws4);*/
        //查询数据库得到广告墙的list<wall>
       
    
        requestLocButton  = (Button) findViewById(R.id.button1);  
        mCurrentMode      = LocationMode.NORMAL;  
        requestLocButton.setText("普通");    
        OnClickListener btnClickListener=new OnClickListener() {  
              
            @Override  
            public void onClick(View v) {  
            	 switch (mCurrentMode) {
                 case NORMAL:
                     requestLocButton.setText("跟随");
                     mCurrentMode = LocationMode.FOLLOWING;
                     mBaiduMap
                             .setMyLocationConfigeration(new MyLocationConfiguration(
                                     mCurrentMode, true, mCurrentMarker));
                     break;
                 case COMPASS:
                     requestLocButton.setText("普通");
                     mCurrentMode = LocationMode.NORMAL;
                     mBaiduMap
                             .setMyLocationConfigeration(new MyLocationConfiguration(
                                     mCurrentMode, true, mCurrentMarker));
                     break;
                 case FOLLOWING:
                     requestLocButton.setText("罗盘");
                     mCurrentMode = LocationMode.COMPASS;
                     mBaiduMap
                             .setMyLocationConfigeration(new MyLocationConfiguration(
                                     mCurrentMode, true, mCurrentMarker));
                     break;
                 default:
                     break;
             }
         }
     };
        requestLocButton.setOnClickListener(btnClickListener);  
   
        //百度定位
        mLocClient = new LocationClient(this);  
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption(); 
        option.setOpenGps(true); //打开gps
        option.setCoorType("bd09ll");//设置坐标类型 
        option.setScanSpan(1000);  //设置每一秒钟检查
        mLocClient.setLocOption(option);  
        mLocClient.start();  
      
        //地图初始化
        mMapView  = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL); 
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(9.0f);
        //设置定位图层
        mBaiduMap.setMyLocationEnabled(true);
        //设置定位图层的比例
        mBaiduMap.setMapStatus(msu);
       
        myImgBtn=(MyImgBtn)findViewById(R.id.MyIBtn_2); 
       //imageloader框架初始化
		imageLoader=imageLoader.getInstance();
		
        //标记响应事件
        mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
               
                    initll = marker.getPosition();
                    initimgtextBtn(li,initll);
                return true;
            }
        });
    }
 
boolean isFirstLoc=true;
/**
 * 定位启动调用
 * @author Administrator
 *
 */
public class MyLocationListenner implements BDLocationListener {  
	

    @Override  
    public void onReceiveLocation(BDLocation location) {  
    	//Receive Location 
    	Log.v("location",location+"");
        StringBuffer sb = new StringBuffer(256);
        sb.append("time : ");
        sb.append(location.getTime());
        sb.append("error code : ");
        sb.append(location.getLocType());
        sb.append("latitude : ");
        sb.append(location.getLatitude());
         lat=location.getLatitude();
        sb.append("lontitude : ");
        sb.append(location.getLongitude());
        sb.append("radius : ");
        sb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation){
            sb.append("speed : ");
            sb.append(location.getSpeed());
            sb.append("satellite : ");
            sb.append(location.getSatelliteNumber());
            sb.append("direction : ");
            sb.append("addr : ");
            sb.append(location.getAddrStr());
            sb.append(location.getDirection());
        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){
            sb.append("addr : ");
            sb.append(location.getAddrStr());
            //运营商信息
            sb.append("operationers : ");
            sb.append(location.getOperators());
        }
       // LocationResult.setText(sb.toString());
        Log.v("bdmap", sb.toString());
        if (location == null || mMapView == null) {  
            return;  
        }  
        
        navigateTo(location);
           
        }  
    
    /**
     * 定位监听器得到当前位置经纬度
     * @param location
     */
    
  public void navigateTo(BDLocation location) {
        if (isFirstLoc) {
        	lat  = location.getLatitude();
        	lng = location.getLongitude();
        	Log.v("lat",lat+""+"lng"+lng);
        	initwalls(lat,lng);
            LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            mBaiduMap.animateMapStatus(update);
            isFirstLoc = false;
	        }
	       //构造定位数据       
	        MyLocationData.Builder locationBuilder = new MyLocationData.
	                Builder();
	        locationBuilder.latitude(location.getLatitude());
	        locationBuilder.longitude(location.getLongitude());
	        MyLocationData locationData = locationBuilder.build();
	        mBaiduMap.setMyLocationData(locationData);
	    }

    public void onReceivePoi(BDLocation poiLocation) {  
    }  
  
}
/**
 * http请求得到一定范围内的广告墙list
 * @param lat
 * @param lng
 */

public void initwalls(double lat, double lng){
	   
	   Log.v("initwalls","lat1+lng1"+lat+""+lng+"");
	   
	dialog = new ProgressDialog(this);
    dialog.setTitle("提示");
    dialog.setMessage("正在加载，请稍后...");
    dialog.setCancelable(false);
    dialog.show();
    // 创建子线程，分别进行Get和Post传输
    new Thread(new MyThread()).start();


}

/**
 * 得到广告墙list  在初始化 marker
 * @author Administrator
 *
 */
public class MyThread implements Runnable {
@Override
public void run() {
	Log.v("lat","lat="+lat+"lng="+lng);
	String  info = null;
	
               info = WebService.executeHttpGet(lat,lng);
               Log.v("info",info);
               JsonUtil ju = new JsonUtil();
               li = new ArrayList<AdWall>();
               li = JsonUtil.getObjectList(info, AdWall.class);
            	Log.v("li",li.get(2).toString());
                Log.v("li",li.get(0).getId()+li.get(0).getImage()+li.get(0).getTitle()+"");    
                initOverlay(li);
      
                    
    handler.post(new Runnable() {
        @Override
        public void run() {
            // 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast
        
            dialog.dismiss();
            Toast toast = Toast.makeText(OverlayActivity.this, "初始化地图成功", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
    });
}
}
    
    /**
     * 数据库查找广告墙得到json并解析为广告墙对象 ，list遍历得到经纬初始化
     * @param lat
     * @param lng
     */
    public void initOverlay(List<AdWall> li){
    	Log.v("initOver",li+"");
        for( int  i = 0 ;  i <  li.size() ; i++){
        	
    	    LatLng ll        = new LatLng(li.get(i).getLatitude(),li.get(i).getLongitude());
    	    Log.v("initOver",ll+"");
    	    //设置缩放等级
    	    MarkerOptions oo = new  MarkerOptions().position(ll).icon(bdB).zIndex(10);
    	    oo.animateType(MarkerAnimateType.grow);
    	    Marker mk;
    	    mk               =  (Marker)(mBaiduMap.addOverlay(oo));
    	   }
        
    }
    

    /**
     * 清除所有Overlay
     *
     * @param view
     */
    public void clearOverlay(View view) {
        mBaiduMap.clear();
       
        mMarkerB = null;
       
    }

    /**
     * 重新添加Overlay
     *
     * @param view
     */
    public void resetOverlay(View view) {
        clearOverlay(null);
        initOverlay(li);
    }
    
    /**
     * 点击marker响应点击事件根据marker的经纬度得到一个广告墙信息
     * 得到一个广告墙的id，图片路径url，标题title
     * 调用 initImgBtn(url,title,wallId);
     * @param liw
     * @param position
     */
    
    public  void initimgtextBtn(List<AdWall> liw,LatLng position){
    	   	
    	 //myImgBtn.setImageResource(R.drawable.sun);  
    	//得到图片？
    	Log.v("initimgbtn",li+""+position);
    	    LatLng  ll   = position;
    	    double de    = ll.latitude;
    	    double de2   = ll.longitude;
    	    String  url   = null;
    	    String  title = null;
    	    int    wallId = 0;
    	for(int i = 0 ;i < liw.size() ; i++){
    		 if( liw.get(i).getLatitude() == de&&liw.get(i).getLongitude() == de2)
    		 {    
    		  Log.v("equal","equal");
    			    url = liw.get( i ).getImage();
    	          title = liw.get( i ).getTitle();
    	         wallId = liw.get(i).getId();
    	      Log.v("url",url+title);
    	      initImgBtn(url,title,wallId);
    			 break;
    		 }
    		
    	}
 
    }
    /**
     * 根据广告墙信息加载图片与文字到组合控件
     * 图片加载完成 在显示infowindow
     * @param url
     * @param title
     */
   
    public void initImgBtn(String url,String  title ,int id){
		     imgtitle = title;
		     wallId   = id;
    	 Log.v("initurl",url+title);
			options = new DisplayImageOptions.Builder()
			.showImageOnLoading(R.drawable.imageloader_ic_stub) // 设置图片下载期间显示的图片
			.showImageForEmptyUri(R.drawable.imageloader_ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
			.showImageOnFail(R.drawable.imageloader_error) // 设置图片加载或解码过程中发生错误显示的图片
			.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
			.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
			.displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
			.build(); // 构建完成
	
			imageLoader.displayImage(url,myImgBtn.getmImgView(), options,new ImageLoadingListener() {
				@Override
				public void onLoadingComplete(String arg0, View arg1,
						Bitmap arg2) {
					// TODO Auto-generated method stub
					    myImgBtn.setText(imgtitle);
					    OnInfoWindowClickListener listener = null;
	                    Convert = BitmapDescriptorFactory.fromView(myImgBtn); 
	                    //Create T outside the constructor 
	                    listener = new OnInfoWindowClickListener() {
	                        public void onInfoWindowClick() {
	            Intent it = new Intent(OverlayActivity.this,com.xpt.vuforia.app.ImageTargets.ImageTargets.class);
	                        	it.putExtra("img", wallId);  
	                        	startActivity(it);
	                            mBaiduMap.hideInfoWindow();
	                        }
	                    };
	                    mInfoWindow = new InfoWindow(Convert, initll, -47, listener);
	                    mBaiduMap.showInfoWindow(mInfoWindow);
				}

				@Override
				public void onLoadingFailed(String arg0, View arg1,
						FailReason arg2) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onLoadingStarted(String arg0, View arg1) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onLoadingCancelled(String arg0, View arg1) {
					// TODO Auto-generated method stub
					
				}  
	                         
	         
	        });   
	}
    
    @Override
    protected void onPause() {
        // MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        // MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
        mMapView.onResume();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
        mMapView.onDestroy();
        mLocClient.stop();
        mBaiduMap.setMyLocationEnabled(false);
        // 回收 bitmap 资源
        bdB.recycle();
        super.onDestroy();
    }

}
