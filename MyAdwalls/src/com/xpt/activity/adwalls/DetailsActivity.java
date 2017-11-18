package com.xpt.activity.adwalls;
import com.example.adwalls.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.xpt.util.bean.wallinfo;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity  extends Activity{
	private wallinfo    wif;
	private ImageView   de_image;
	private TextView    de_title;
	private TextView    de_info;
	private Button      de_web;
	private Button      de_ar;
	private ImageLoader imageloader;
	private DisplayImageOptions options;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_detail);
		 wif = new wallinfo();
		 initData();
		 de_image = (ImageView)findViewById(R.id.detailImage);
		 de_title = (TextView)findViewById(R.id.de_title);
		 de_info  = (TextView)findViewById(R.id.de_info);
		 de_web   = (Button)findViewById(R.id.de_wv);
		 de_ar    = (Button)findViewById(R.id.de_ar);
		 de_info.setMaxHeight(300);
		 de_info.setMovementMethod(ScrollingMovementMethod.getInstance());
	     imageloader = imageloader.getInstance();       
		 initImage(); 
		 
		         
	}
		
   public void initData(){
	   wif = (wallinfo) this.getIntent().getSerializableExtra("wallinfo");
	   
       if(null == wif){
      	 Log.v("detailgetwallinfo","get arg lose!!!");
       }
       else{
      	 Log.v("detailgetwallinfo",wif.toString());
       }
      
   }
   
 public  void initImage(){
	 options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.imageloader_ic_stub) // 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.imageloader_ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.imageloader_error) // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
		.displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
		.build(); // 构建完成

      imageloader.displayImage(wif.getImageurl(),de_image,options,new ImageLoadingListener(){

	@Override
	public void onLoadingCancelled(String arg0, View arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
		// TODO Auto-generated method stub
		de_title.setText(wif.getTitle());
		de_info.setText(wif.getInfo());
		
		
	}

	@Override
	public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoadingStarted(String arg0, View arg1) {
		// TODO Auto-generated method stub
		
	}
	 
 });
 }	
 
 
 public void onWebClick(View view) {  
     Intent intent = new Intent(this, WebDisplayActivity.class);  
     intent.putExtra("url", wif.getUrl());  
     startActivity(intent);  
  }  
  
  public void onAdvertisingClick(View view) {
  	Intent it = new Intent(this,AdvertisingActivity.class); 
  	
     Bundle bundle = new  Bundle();
     bundle.putSerializable("wallinfo", wif);
     it.putExtras(bundle);
     
      startActivity(it);  
	}
	

}
