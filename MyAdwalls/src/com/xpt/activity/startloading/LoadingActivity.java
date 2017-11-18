
package com.xpt.activity.startloading;

import com.example.adwalls.R;
import com.example.adwalls.R.layout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.view.View.OnClickListener;

import android.view.Window;

import android.widget.ImageView;


public class LoadingActivity extends Activity  implements OnClickListener{

    
    private ImageView image;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
     
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(layout.activity_startloading);
        image = (ImageView)findViewById(R.id.splash_image);
        image.setOnClickListener(this);
        
    }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		  Log.v("startloading","click");
		  switch(v.getId())
		  {
			   case R.id.splash_image:
				    Intent intent = new Intent(LoadingActivity.this,com.xpt.activity.login.MainActivity.class);
		            startActivity(intent);
		            Log.v("startloading","click");
		            break;
		  }

		  
		  
		  
		  
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
    
}
