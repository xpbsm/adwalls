package com.example.Img;
 


import com.example.adwalls.R;

import android.content.Context;  
import android.graphics.Color;  
import android.util.AttributeSet;  
import android.view.LayoutInflater;  
import android.view.MotionEvent;  
import android.view.View;  
import android.view.View.OnTouchListener;  
import android.widget.ImageView;  
import android.widget.LinearLayout;  
import android.widget.TextView;  
  
public class MyImgBtn extends LinearLayout {  
  
    private ImageView mImgView = null;  
    private TextView mTextView = null;  
    private Context mContext;  
    public MyImgBtn(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        // TODO Auto-generated constructor stub  
        LayoutInflater.from(context).inflate(R.layout.infow_myimgbtn_layout, this, true);  
        mContext = context;  
        mImgView =  (ImageView)findViewById(R.id.Myimg);  
        mTextView = (TextView)findViewById(R.id.Mytext);  
                        
    }  
  
  
   public ImageView getmImgView() {
	      mImgView =  (ImageView)findViewById(R.id.Myimg);  
		return mImgView;
	}


	public void setmImgView(ImageView mImgView) {
		this.mImgView = mImgView;
	}


	public TextView getmTextView() 
	{
		 mTextView = (TextView)findViewById(R.id.Mytext); 
		return mTextView;
	}


	public void setmTextView(TextView mTextView) {
		this.mTextView = mTextView;
	}


	 
    public void setImageResource(int resId){  
        mImgView.setImageResource(resId);  
    }  
      
   
    public void setText(String str){  
        mTextView.setText(str);  
    }  
   
    public void setTextSize(float size){  
        mTextView.setTextSize(size);  
    }  
  
  
  

  
}