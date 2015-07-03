package com.ntust.cmapp;


import java.io.File;
import java.util.Calendar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GetPhoto_Fragment extends Fragment {
	private static final int IMAGE_CAMERA = 100;//使用相機代號
	private static final int IMAGE_FILE = 200;//使用相簿代號
	public Uri mPictureUri; //相片Uri
	private File image;//相片檔
	public Bitmap bmp;//相片顯示物件
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState){
		View view =inflater.inflate(R.layout.getphoto_fragment_layout,container,false);//設定Layout
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Button btCamera=(Button)getActivity().findViewById(R.id.getFromCamera);//使用相機按鍵
		Button btGallery=(Button)getActivity().findViewById(R.id.getFromGallery);//使用相簿按鍵
		btCamera.setOnClickListener(btListener);//使用相機按鍵偵測
		btGallery.setOnClickListener(btListener);//使用相簿按鍵偵測
		
	}
	
	private Button.OnClickListener btListener =new  Button.OnClickListener(){//按鍵偵測

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v.getId()==R.id.getFromCamera){//按相機
				launchChooser("Camera");
			}
			if(v.getId()==R.id.getFromGallery){//按相簿
				launchChooser("Gallery");
			}
		}
		
		
	};
	
	private void launchChooser(String type) {  //案件處理
		   
	    if(type.equals("Camera")){//按相機
	    	Intent i2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE );  //新增相機Intent
	    	String text = (String) DateFormat.format("yyyyMMddkkmmss", Calendar.getInstance());//設定相片檔案名稱 
	    	image=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),text+".jpg");//設定相片路徑
	    	i2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));  //將相片路徑加到Intent
	    	
	    	mPictureUri=Uri.fromFile(image);//儲存路徑
	    	startActivityForResult(i2, IMAGE_CAMERA);//執行相機  
	    }
	    if(type.equals("Gallery")){//按相簿
	    	Intent intent = new Intent(//新增相簿Intent
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
			intent.setType("image/*");//抓取圖片
			startActivityForResult(//執行相簿  
					Intent.createChooser(intent, "Select File"),
					IMAGE_FILE);
	    }
	}  
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) { 
		Intent intent = new Intent();//準備切換到CMSetting Activity 的Intent
		Bundle bundle = new Bundle();//準備傳給CMSetting Activity 的Bundle
		intent.setClass(getActivity(), CMSetting_Activity.class);//設定要開啟的Activity
			
	    if (requestCode == IMAGE_CAMERA  && resultCode == Activity.RESULT_OK) {  //相機
	    	bundle.putString("type", "Camera");//存入方式 相機
	    	bundle.putString("data", mPictureUri.toString());//存入路徑
	    	bundle.putString("path", mPictureUri.getPath());
	    	intent.putExtras(bundle);
			startActivity(intent);//執行Activity
			
	    }else if (requestCode == IMAGE_FILE && resultCode == Activity.RESULT_OK) {  //相簿
	    	bundle.putString("type", "Gallery");//存入方式 相簿
	    	Uri selectedImageUri = data.getData();//抓取傳回的路徑
	  		bundle.putString("data", selectedImageUri.toString());//存入路徑
	  		bundle.putString("path", selectedImageUri.getPath());//存入路徑
	  		intent.putExtras(bundle);
			startActivity(intent);//執行Activity
	    }  
	    return;
	    
	    
	} 
	
	
}
