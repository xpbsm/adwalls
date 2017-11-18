package com.xpt.activity.login;
import java.util.Timer;
import java.util.TimerTask;


import com.example.adwalls.R;
import com.google.gson.Gson;
import com.xpt.util.bean.ResponseDTO;
import com.xpt.util.web.WebJsonService;
import com.xpt.util.web.WebService;
import com.xpt.util.web.WebServicePost;


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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

    // 登陆按钮
    private Button               logbtn;
    // 调试文本，注册文本
    private TextView             regtv;
    // 显示用户名和密码
    EditText                      username, password;
    // 创建等待框
    private ProgressDialog       dialog;
    // 返回的数据
    private String               info;
    // 返回主线程更新数据
    private TextView             repwd;
    private String               checkuser=null;
    private  String              checkpwd=null;
    private static Handler      handler = new Handler();
    private boolean ischecked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        // 获取控件
        username = (EditText) findViewById(R.id.accout);
        password = (EditText) findViewById(R.id.pwd);
        repwd    = (TextView) findViewById(R.id.textView2);
        logbtn   = (Button)   findViewById(R.id.login);
        regtv    = (TextView) findViewById(R.id.register);
     
        // 设置按钮监听器
        logbtn.setOnClickListener(this);
        regtv.setOnClickListener(this);
        repwd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                // 检测网络 这里我用的Wlan测试，但此方法只允许网络流量，只能先禁掉。
		if (!checkNetwork()) {
				Toast toast = Toast.makeText(MainActivity.this,"网络未连接", Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
			    toast.show();
			    break;
		      }
		//judge
		
		     checkuser=username.getText().toString().trim();  
         if (checkuser==null||checkuser.length()<=0)   
         {         
             username.requestFocus();  
             username.setError("对不起，用户名不能为空");  
             return;  
         }  
         else   
         {  
             checkuser=username.getText().toString().trim();  
         }  
         
             checkpwd=password.getText().toString().trim(); 
         
         if (checkpwd==null||checkpwd.length()<=0)   
         {         
             password.requestFocus();  
             password.setError("对不起，密码不能为空");  
             return;  
         }  
         
         else   
         {  
        	 checkpwd=password.getText().toString().trim();  
         } 
		
	     // 提示框
                dialog = new ProgressDialog(this);
                dialog.setTitle("提示");
                dialog.setMessage("正在登陆，请稍后...");
                dialog.setCancelable(false);
                dialog.show();
                // 创建子线程，分别进行Get和Post传输
                new Thread(new MyThread()).start();
                
          
                break;
            case R.id.register:
                Intent regItn = new Intent(MainActivity.this, com.xpt.activity.register.MainActivity.class);
                startActivity(regItn);
                break;
            case  R.id.textView2:
            	Intent reItn=new Intent(MainActivity.this,com.xpt.activity.repwd.MainActivity.class);
            	startActivity(reItn);
        }
        ;
    }

    // 子线程接收数据，主线程修改数据
    public class MyThread implements Runnable {
        @Override
        public void run() {
        	Log.v("user","checkuser="+checkuser+"password="+checkpwd);
            info = WebServicePost.executeHttpPost(checkuser, checkpwd);
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
                
            handler.post(new Runnable() {
                @Override
                public void run() {
                    // 最好返回一个固定键值，根据键值判断是否登陆成功，有键值就保存该info跳转，没键值就是错误信息直接toast
                    dialog.dismiss();
                    Toast toast = Toast.makeText(MainActivity.this, info, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    if(ischecked){
                        ischecked = false;
                        final Intent logItn = new Intent(MainActivity.this, com.xpt.activity.navigation.MainActivity.class);
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

    // 检测网络
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
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
    
}
