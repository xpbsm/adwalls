package com.xpt.activity.adwalls;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import com.example.adwalls.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;


import com.xpt.activity.adwalls.OverlayActivity.MyThread;
import com.xpt.util.bean.AdInform;
import com.xpt.util.bean.wallinfo;

import com.xpt.util.web.JsonUtil;
import com.xpt.util.web.WebService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
public class ListViewActivity extends  Activity{

	private ListView         mListImageLv;
	private DisplayImageOptions options; // 设置图片显示相关参数
	private String[]         imageUrls; // 图片路径
    private ItemListAdapter  itemad;
    private ImageLoader      imageLoader=null;
    private List<AdInform>   infoli=null;
    private int              wallid;
    private ProgressDialog   dialog;
    private  static  Handler handler = new  Handler();
    private InitListViewTask   initListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.imageloader_activity_list);
		imageLoader=imageLoader.getInstance();
		mListImageLv=(ListView)findViewById(R.id.lv_image);
		//得到广告墙的Id
		initListView =new InitListViewTask();
		 initData();
		
		mListImageLv.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Log.v("ItemClick","you click on item"+arg2);
				
				 AdInform tempwallinfo = new AdInform();
				                   tempwallinfo = infoli.get(arg2);
				  wallinfo   wif=new wallinfo();
				                   wif.setId(tempwallinfo.getId());
				                   wif.setImageurl(tempwallinfo.getImage());
				                   wif.setInfo(tempwallinfo.getText());
				                   wif.setTitle(tempwallinfo.getTitle());
				                   wif.setUrl(tempwallinfo.getAssociated_url());
				                   wif.setUserid(tempwallinfo.getCreateUserId());
				                   wif.setLocid(wallid);
			    Log.v("tempwallinfo",tempwallinfo.toString());
			    Log.v("wif",wif.toString());
			    //得到一个广告墙信息对象 通过序列化传给下一个activity
			    Intent it=new Intent(ListViewActivity.this,DetailsActivity.class);
			           Bundle bundle = new  Bundle();
			           bundle.putSerializable("wallinfo", wif);
			           it.putExtras(bundle);
			           startActivity(it);
			} 
		
		});
		
	}
	/**
	 * 得到imagetarget传来的参数
	 * 广告墙的id
	 * 开启线程查询相关信息
	 */
	public void  initData(){
		itemad=new ItemListAdapter();
		int id=getIntent().getIntExtra("img",-1);
		if(id != -1){
			wallid =id;
			dialog = new ProgressDialog(this);
			dialog.setTitle("提示");
			dialog.setMessage("正在加载,请稍后..") ;
			dialog.setCancelable(false);
			dialog.show();
			//new Thread(new MyThread()).start();
			initListView.execute(wallid+"");
			 Log.v("getExtra","getExtra success!!"+id);
		}
		else{
			 Log.v("getExtra","getExtra lose!!");
		}
		
		
	}
	


private class InitListViewTask extends AsyncTask<String, Integer, String> {
    //doInBackground方法内部执行后台任务,不能在里面更新UI，否则有异常。
    @Override  
    protected String doInBackground(String... params) 
    {  
    	 String info = WebService .executeHttpGet(Integer.parseInt(params[0]));
         infoli = new ArrayList<AdInform>();
         infoli = JsonUtil.getObjectList(info, AdInform.class);
         Log.v("infoli","赋值操作"+infoli.get(1).getImage());
		return info;  
    }  
      
    //onPostExecute用于doInBackground执行完后，更新界面UI。
    //result是doInBackground返回的结果
    @Override  
    protected void onPostExecute(String result) 
    {  
    
    	Log.i("result",result);
    	initListViewItem();
    	
        dialog.dismiss();
        Toast toast = Toast.makeText(ListViewActivity.this, "初始化列表成功", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }     
}




	
	/**
	 * http request  findById(id) obain list<info>
	 * @param wid
	 */
	  
	  public  void initListViewItem(){
		   imageUrls = new String[infoli.size()];
		   
		for(int i = 0;i < infoli.size();i++)
		{
			imageUrls[i] = infoli.get(i).getImage();
			Log.v("imageurlString",infoli.get(i).getImage());	
		}
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.imageloader_ic_stub) // 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.imageloader_ic_empty) // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.imageloader_error) // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
		.displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
		.build(); // 构建完成

        mListImageLv.setAdapter(itemad);
		
	
	  }
	  
	
	class ItemListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return imageUrls.length;
		}
		@Override
		public Object getItem(int position) {
			return imageUrls[position];
		}
		@Override
		public long getItemId(int position) {
			return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(R.layout.imageloader_item_list,
						null);
				viewHolder = new ViewHolder();
				viewHolder.image = (ImageView) convertView
						.findViewById(R.id.iv_image);
				viewHolder.text = (TextView) convertView
						.findViewById(R.id.tv_introduce);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			/**
			 * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
			 * DisplayImageOptions配置文件
			 */
			imageLoader.displayImage(imageUrls[position],
					viewHolder.image, options);
			viewHolder.text.setText(infoli.get(position).getTitle()); // TextView设置文本
			return convertView;		
		}

		public class ViewHolder {
			public ImageView image;
			public TextView text;
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
	

}
