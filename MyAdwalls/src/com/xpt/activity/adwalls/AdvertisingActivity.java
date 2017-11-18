package com.xpt.activity.adwalls;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.example.adwalls.R;
import com.google.gson.Gson;
import com.xpt.activity.register.MainActivity;
import com.xpt.uploadimage.utils.Base64Coder;
import com.xpt.uploadimage.utils.ZoomBitmap;
import com.xpt.util.bean.AdInform;
import com.xpt.util.bean.AdInformDTO;
import com.xpt.util.bean.ResponseDTO;
import com.xpt.util.bean.wallinfo;
import com.xpt.util.web.JsonUtil;
import com.xpt.util.web.WebInfoJson;
import com.xpt.util.web.WebJsonService;
import com.xpt.util.web.WebService;
import com.xpt.util.web.WebServicePost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AdvertisingActivity extends Activity implements OnClickListener {
	// 显示图片
	private ImageView image;
	// 两个but
	private Button take;
	private Button selete;
	// 记录文件名
	private String filename;
	// 上传的bitmap
	private Bitmap upbitmap;
	private Button up;

	// 多线程通信
	private Handler myHandler;
	private ProgressDialog myDialog;
	// 输入字符串
	private EditText ad_title;
	private EditText ad_info;
	private EditText ad_url;
	private EditText ad_days;
	private String ad_locid;
	private String ad_userid;
	private wallinfo wif = new wallinfo();
	private ProgressDialog dialog;
	private UpLoadImageTask uploadimage;
	private UpLoadWallInfoTask uploadWif;
	private boolean ischecked = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_advertising);
		image   = (ImageView) this.findViewById(R.id.ading_imageView1);
		take    = (Button) this.findViewById(R.id.take);
		selete  = (Button) this.findViewById(R.id.selete);
		up      = (Button) this.findViewById(R.id.ading_register);

		ad_title = (EditText) this.findViewById(R.id.ading_et_title1);
		ad_info  = (EditText) this.findViewById(R.id.ading_et_info1);
		ad_url   = (EditText) this.findViewById(R.id.ading_et_url);
		ad_days  = (EditText) this.findViewById(R.id.ading_et_days);

		uploadimage = new UpLoadImageTask();
		uploadWif   = new UpLoadWallInfoTask();

		initData();
		take.setOnClickListener(this);
		selete.setOnClickListener(this);
		up.setOnClickListener(this);
		// myHandler = new MyHandler();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		switch (v.getId()) {
		case R.id.take:
			intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			filename = "xpt" + System.currentTimeMillis() + ".jpg";
			System.out.println(filename);
			// 下面这句指定调用相机拍照后的照片存储的路径
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
					Environment.getExternalStorageDirectory(), filename)));
			startActivityForResult(intent, 1);
			break;
		case R.id.selete:
			intent = new Intent(Intent.ACTION_PICK, null);
			intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
					"image/*");
			startActivityForResult(intent, 2);
			break;
		case R.id.ading_register:
			dialog = new ProgressDialog(this);
			dialog.setTitle("提示");
			dialog.setMessage("正在发布，请稍后...");
			dialog.setCancelable(false);
			dialog.show();
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			upbitmap.compress(Bitmap.CompressFormat.JPEG, 60, stream);
			byte[] b = stream.toByteArray();
			// 将图片流以字符串形式存储下来
			String file = new String(Base64Coder.encodeLines(b));
			uploadimage.execute(file);
			break;
		default:
			break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		switch (requestCode) {
		case 1:
			// 解成bitmap,方便裁剪
			Bitmap bitmap = BitmapFactory.decodeFile(Environment
					.getExternalStorageDirectory().getPath() + "/" + filename);
			float wight = bitmap.getWidth();
			float height = bitmap.getHeight();
			// ZoomBitmap.zoomImage(bitmap, wight/8, height/8);
			image.setImageBitmap(ZoomBitmap.zoomImage(bitmap, wight / 8,
					height / 8));
			upbitmap = ZoomBitmap.zoomImage(bitmap, wight / 8, height / 8);
			break;
		case 2:
			if (data != null) {
				image.setImageURI(data.getData());
				System.out.println(getAbsoluteImagePath(data.getData()));
				upbitmap = BitmapFactory.decodeFile(getAbsoluteImagePath(data
						.getData()));
				// 剪一下，防止测试的时候上传的文件太大
				upbitmap = ZoomBitmap.zoomImage(upbitmap,
						upbitmap.getWidth() / 8, upbitmap.getHeight() / 8);
			}
			break;
		default:
			break;
		}
	}

	// 取到绝对路径
	@SuppressWarnings("deprecation")
	protected String getAbsoluteImagePath(Uri uri) {
		// can post image
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(uri, proj, // Which columns to return
				null, // WHERE clause; which rows to return (all rows)
				null, // WHERE clause selection arguments (none)
				null); // Order-by clause (ascending by name)

		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	public void initData() {
		wif = (wallinfo) this.getIntent().getSerializableExtra("wallinfo");
		if (null == wif) {
			Log.v("detailgetwallinfo", "get arg lose!!!");
		} else {
			Log.v("detailgetwallinfo", wif.toString());

		}

	}

	private class UpLoadImageTask extends AsyncTask<String, Integer, String> {

		// onPreExecute方法在execute()后执行
		@Override
		protected void onPreExecute() {

			// mShowLogTextView.setText("onPreExecute。。。begin downLoad");
		}

		// doInBackground方法内部执行后台任务,不能在里面更新UI，否则有异常。
		@Override
		protected String doInBackground(String... params) {
			// Log.i(TAG, "doInBackground(String... params) enter");

			String info = WebServicePost.executeHttpPost(params[0]);

			return info;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Integer... progresses) {

		}

		// onPostExecute用于doInBackground执行完后，更新界面UI。
		// result是doInBackground返回的结果
		@Override
		protected void onPostExecute(String result) {
			// 图片上传成功得到图片的服务器地址
			// 在上传广告json对象
			Log.i("result", result);
			String imageurl = "http://" + WebServicePost.getIP() + result;
			AdInformDTO adinf = new AdInformDTO();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			String currentTime = (new SimpleDateFormat("yyyy-MM-dd"))
					.format(calendar.getTime());
			Log.v("currentTime", currentTime);
			calendar.add(Calendar.DATE,
					Integer.parseInt(ad_days.getText().toString().trim()));
			String endTime = (new SimpleDateFormat("yyyy-MM-dd"))
					.format(calendar.getTime());
			String asurl = ad_url.getText().toString().trim();
			String title = ad_title.getText().toString().trim();
			String text = ad_info.getText().toString().trim();
			Log.v("endTime", endTime);
			adinf.setCreateUserId(wif.getUserid());
			adinf.setTitle(title);
			adinf.setText(text);
			adinf.setImage(imageurl);
			adinf.setVideo("video");
			adinf.setAssociated_url(asurl);
			adinf.setCreateTime(currentTime.trim());
			adinf.setEndTime(endTime.trim());
			adinf.setAdWallId(wif.getLocid());
			Log.v("adinf", adinf.toString());
			Gson gson = new Gson();// 利用google提供的gson将一个list集合写成json形式的字符串
			String jsonString = gson.toJson(adinf);
			uploadWif.execute(jsonString);

		}

		// onCancelled方法用于取消Task执行，更新UI
		@Override
		protected void onCancelled() {
			// Log.i(TAG, "onCancelled() called");
			// mShowLogTextView.setText("onCancelled");
		}
	}

	private class UpLoadWallInfoTask extends AsyncTask<String, Integer, String> {

		// onPreExecute方法在execute()后执行
		@Override
		protected void onPreExecute() {

		}

		// doInBackground方法内部执行后台任务,不能在里面更新UI，否则有异常。
		@Override
		protected String doInBackground(String... JsonString) {
			// 传递广告墙jsonString 和广告墙id
			Log.v("jsonstring", JsonString[0]);
			String info = WebInfoJson.executeHttpPost(JsonString[0]);
			Log.v("info", info.toString());
			return info;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Integer... progresses) {

		}

		// onPostExecute用于doInBackground执行完后，更新界面UI。
		// result是doInBackground返回的结果
		@Override
		protected void onPostExecute(String result) {
			// 图片上传成功得到图片的服务器地址
			// 在上传广告json对象
			Log.i("result", result);

			Gson gson = new Gson();
			ResponseDTO response = new ResponseDTO();
			response = gson.fromJson(result, ResponseDTO.class);
			if (response.getStatus() == 0) {
				result = response.getMessage();
			} else if (response.getStatus() == 1) {
				result = response.getMessage();
				ischecked = true;
			}

			dialog.dismiss();

			Toast toast = Toast.makeText(AdvertisingActivity.this, result,
					Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();

			if (ischecked) {
				ischecked = false;
				final Intent it = new Intent(AdvertisingActivity.this,
						com.xpt.activity.adwalls.ListViewActivity.class);
				Timer timer = new Timer();
				TimerTask tast = new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						it.putExtra("img", wif.getLocid());
						startActivity(it);
					}

				};
				timer.schedule(tast, 3000);
			}
		}

		// onCancelled方法用于取消Task执行，更新UI
		@Override
		protected void onCancelled() {
			// Log.i(TAG, "onCancelled() called");
			// mShowLogTextView.setText("onCancelled");
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
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
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
