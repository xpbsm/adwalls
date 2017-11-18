package com.xpt.activity.adwalls;

import com.example.adwalls.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebDisplayActivity extends Activity {
	private WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_web);
		/*
		 * 代码直接创建webview控件
		 * wv = new WebView(this);
		 * */
		wv = (WebView) this.findViewById(R.id.myWeb);//从XML文件中获取WebView控件
		WebSettings ws = wv.getSettings();
		ws.setJavaScriptEnabled(true);//设定WebView可以执行JavaScript脚本
		wv.loadUrl("http://www.baidu.com/");//加载需要显示的网页
		
		/*
		 * 如果希望点击链接继续在当前browser中响应，
		 * 而不是新开Android的系统browser中响应该链接，
		 * 必须覆盖 webview的WebViewClient对象。
		 * */
		wv.setWebViewClient(new WebViewClient(){
				@Override
		        public boolean shouldOverrideUrlLoading(WebView view, String url) { 
		            view.loadUrl(url); 
		            return true; 
		        } 
		});
		
	}
	
	  @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	        if (keyCode == KeyEvent.KEYCODE_BACK && wv.canGoBack()) {
	            wv.goBack();
	            return true;
	        }

	        return super.onKeyDown(keyCode, event);
	    }

	    //销毁Webview
	    @Override
	    protected void onDestroy() {
	        if (wv != null) {
	            wv.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
	            wv.clearHistory();

	            ((ViewGroup) wv.getParent()).removeView(wv);
	            wv.destroy();
	            wv = null;
	        }
	        super.onDestroy();
	    }
	

}
