package com.xpt.activity.register;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;



import com.example.adwalls.R;
import com.google.gson.Gson;
import com.xpt.util.bean.ResponseDTO;
import com.xpt.util.bean.User;
import com.xpt.util.web.JsonUtil;
import com.xpt.util.web.WebJsonService;
import com.xpt.util.web.WebServicePost;
import com.xpt.util.web.WriteJson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputFilter.LengthFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private EditText       realname;
	private EditText       username;
	private EditText       pwd;
	private EditText       apwd;
	private Button         reset;
	private Button         register;
	private ProgressDialog dialog;
	private String         info;
	private int            ength;
	private String         jsonString=null;
	private boolean       ischecked = false;
	private static Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        
        username    = (EditText)findViewById(R.id.username);
        pwd         = (EditText)findViewById(R.id.password1);
        apwd        = (EditText)findViewById(R.id.password2);
        reset       = (Button)findViewById(R.id.reset);
        register    = (Button)findViewById(R.id.register);
        realname    = (EditText)findViewById(R.id.rename);
        
        reset.setOnClickListener(this);
        register.setOnClickListener(this);
      
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.reset:
			username.setText("");
			     pwd.setText("");
			    apwd.setText(""); 
			    break;
		case R.id.register:
			if (!checkNetwork()) {
				Toast toast = Toast.makeText(MainActivity.this,"网络未连接", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
			    toast.show();
			    break;
		      }
			
			//judge

			   String urealname  = realname.getText().toString().trim();
	           String uname      = username.getText().toString().trim();
	           String upwd       = pwd.getText().toString().trim();
	           String uapwd      = apwd.getText().toString().trim();
	           
	           if(urealname == null || urealname.length() <= 0)
	           {
	        	    realname.requestFocus();  
		            realname.setError("对不起，真实名不能为空"); 
					break;
	           }
	           else if(uname == null || uname.length() <= 0 )
				{
				
					username.requestFocus();  
		            username.setError("对不起，用户名不能为空"); 
					break;
				}
			   else if(upwd == null || upwd.length() <= 0)
				{
					 pwd.requestFocus();  
			         pwd.setError("密码不能为空"); 
					 break;
				}
				else if(uapwd == null || uapwd.length() <=0)
				{
					 apwd.requestFocus();  
			         apwd.setError("请重复密码"); 
					 break;
				}
				else{
					
					if(!upwd.equals(uapwd))
					{
						Log.v("uname",(upwd.equals(uapwd))+"");
						apwd.requestFocus();  
				        apwd.setError("重复密码错误"); 
						break;
					}
				
			}
			 dialog = new ProgressDialog(this);
			 dialog.setTitle("提示");
			 dialog.setMessage("正在注册，请稍等。。。");
			 dialog.setCancelable(false);
			 dialog.show();
			 new  Thread(new MyRegisterThread()).start();
		    break;
		}
	}
		
		public  class MyRegisterThread  implements Runnable{

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.v("user","username="+username.getText().toString()+"password="+pwd.getText().toString()+"apassword="+apwd.getText().toString());
				info = WebServicePost.executeHttpPost(realname.getText().toString().trim(),username.getText().toString().trim(), pwd.getText().toString().trim());
				Log.v("info",info);
				 Gson gson = new Gson();
		            ResponseDTO response = new ResponseDTO();
		                 response=gson.fromJson(info,ResponseDTO.class );
		                 if(response.getStatus() == 0)
		                 {
		                	 info=response.getMessage();
		                 }
		                 else if(response.getStatus() == 1)
		                 {
		                	info=response.getMessage(); 
		                	ischecked=true;
		                 }
				
				
				
				
			    handler.post(new Runnable(){
					@Override
					public void run() {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Toast toast=Toast.makeText(MainActivity.this, info+"4秒回到主界面", Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
		                toast.show();
		                if(ischecked){
		                	ischecked = false;
			                final Intent logItn = new Intent(MainActivity.this, com.xpt.activity.login.MainActivity.class);
			                //注册成功后3秒跳转到登录界面
			    			 Timer timer=new Timer();
			    			 TimerTask tast=new TimerTask(){
			    				@Override
			    				public void run() {
			    					// TODO Auto-generated method stub
			    					startActivity(logItn);
			    				  }	 
			    			  };
			    			 timer.schedule(tast, 4000);
		                
		                
					       }
					   }
			        });			
		       }	
	    }  

		 private boolean checkNetwork() {
		        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		      
		        if (connManager.getActiveNetworkInfo() != null) {
		            return connManager.getActiveNetworkInfo().isAvailable();
		        }
		        return false;
		    }
		 
		private void Toastutil(String info){
			Toast toast=Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT);
			 toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
		}
	}