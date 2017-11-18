package com.xpt.activity.navigation;

import com.example.adwalls.R;
import com.xpt.activity.adwalls.OverlayActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_navigation);
	}
	
	 public void onAboutClick(View view){
	    	Intent intent=new Intent(this,com.xpt.activity.about.MainActivity.class);
	    	startActivity(intent);
	    }

	public void onMapClick(View view) {  
	        Intent intent = new Intent(this, OverlayActivity.class);  
	        startActivity(intent);  
	     }  
	
	
}