package com.xpt.activity.repwd;
import java.util.Timer;
import java.util.TimerTask;
import com.example.adwalls.R;
import com.google.gson.Gson;
import com.xpt.util.bean.ResponseDTO;
import com.xpt.util.web.WebJsonService;
import com.xpt.util.web.WebServicePost;
import com.xpt.util.web.WebServicePostPwd;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	private EditText       username;
	private EditText       apwd;
	private EditText       newpwd;
	private Button         reset;
	private Button         register;
	private ProgressDialog dialog;
	private String         info;
    private boolean       ischecked = false;
	private static Handler handler=new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_repwd);
        username    = (EditText) findViewById(R.id.repwduser);
        newpwd      = (EditText) findViewById(R.id.newpwd1);
        apwd        = (EditText) findViewById(R.id.repwdpassword2);
        reset       = (Button)   findViewById(R.id.repwdreset);
        register    = (Button)   findViewById(R.id.repwdupdate);
	    reset.setOnClickListener(this);
	    register.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.repwdreset:
			  username.setText("");
			  newpwd.setText("");
	          apwd.setText("");         
			  Log.v("reset","sucess");
			    break;
		case R.id.repwdupdate:
			if (!checkNetwork()) {
				Toast toast = Toast.makeText(MainActivity.this,"网络未连接", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
			    toast.show();
			    break;
		      }
			
			//judge
			   String uname  = username.getText().toString().trim();
	           String unpwd  = newpwd.getText().toString().trim();
	           String uapwd  = apwd.getText().toString().trim();
	           
	         if(  uname == null || uname.length() <= 0 )
				{
				
					username.requestFocus();  
		            username.setError("对不起，用户名不能为空"); 
					break;
				}
			 
			   else if(unpwd == null|| unpwd.length()<=0)
			   {
				    newpwd.requestFocus();  
			        newpwd.setError("新密码不能为空"); 
					 break;
			   }
				else if(uapwd == null || uapwd.length() <=0)
				{
					 apwd.requestFocus();  
			         apwd.setError("请重复新密码"); 
					 break;
				}
				else{
					
					if(!unpwd.equals(uapwd))
					{
						Log.v("uname",(unpwd.equals(uapwd))+"");
						apwd.requestFocus();  
				        apwd.setError("重复密码错误"); 
						break;
					}
				
			}
			 dialog = new ProgressDialog(this);
			 dialog.setTitle("提示");
			 dialog.setMessage("正在修改，请稍等。。。");
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
				Log.v("user","username="+username.getText().toString()+"apassword="+newpwd.getText().toString());
				info = WebServicePostPwd.executeHttpPost(username.getText().toString().trim(),newpwd.getText().toString().trim());		
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
						Toast toast=Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT);
						toast.setGravity(Gravity.CENTER, 0, 0);
		                toast.show();
		                if(ischecked){
	                        ischecked = false;
	                        final Intent logItn = new Intent(MainActivity.this, com.xpt.activity.login.MainActivity.class);
	        	   			 Timer timer=new Timer();
	        	   			 TimerTask tast=new TimerTask(){
	        	   				@Override
	        	   				public void run() {
	        	   					// TODO Auto-generated method stub
	        	   					startActivity(logItn);
	        	   				}
	        	   				 
	        	   			 };
	        	   			 timer.schedule(tast, 3000);
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