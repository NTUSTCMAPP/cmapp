package com.ntust.cmapp;



import eu.janmuller.android.simplecropimage.*;

import com.ntust.cmapp.AndroidMultiPartEntity.ProgressListener;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.FloatMath;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;

public class CMSetting_Activity extends Activity {
	private Map<String, String> photoid= new HashMap<String,String>();//新增物件id
	private Map<String, LayoutParams> photoParams =new HashMap<String, LayoutParams>();//新增物件資訊
	private Map<String, ImageView> photoView =new HashMap<String, ImageView>();//新增物件view
	
	
	private Map<String, ImageView> photoCloth =new HashMap<String, ImageView>();//新增物件種類 上衣cloth、下著pants、鞋子shoes
	private Map<String, String> photoSort =new HashMap<String, String>();//新增物件種類 上衣cloth、下著pants、鞋子shoes
	private Map<String, String> photoKind =new HashMap<String, String>();//新增物件類型 上衣1,上衣2
	
	private Map<String, ImageView> photoColor =new HashMap<String, ImageView>();//新增物件顏色view
	private Map<String, String> photoColorNum =new HashMap<String, String>();//新增顏色物件代號
	
	private Map<String, ImageView> photoBrand =new HashMap<String, ImageView>();//新增物件顏色view
	private Map<String, String> photoBrandNum =new HashMap<String, String>();//新增顏色物件代號
	
	private Map<String, ImageView> photoCatalog =new HashMap<String, ImageView>();//新增物件顏色view
	private Map<String, String> photoCatalogNum =new HashMap<String, String>();//新增顏色物件代號
	
	final int PIC_CROP = 1;
	long totalSize = 0;
	private Boolean dragNow=false;
	private ImageView imageview;//照片ImgView
	private ImageView trashview;//照片ImgView
	private Dialog dlg;
	private ProgressDialog Progressdlg;
	private int viewId=1; //新增物間之ID
	
	private DrawerLayout mainLayout;
	private LinearLayout rightListView;
	String photoURI;//相片的URI
	String photoPath;//相片的URI
	
	String howtogetPhoto;//相片的取得方式
	RelativeLayout parentview;//最外層layout
	private int selectState=0; //0:cloth 1:color
	
	
	String[] sort=new String[]{"cloth","pants","shoes","accessory"};
	String[] sortShow=new String[]{"上衣","下著","鞋子","配件"};
	String[] colorName;//顏色陣列名稱
	TypedArray colors ;//抓顏色陣列用的轉換陣列
	int[] color;//抓顏色陣列
	public Drawable[] drawableBrand;
	public Drawable[] drawableCatalog;
	String[] brandName;//顏色陣列名稱
	String[] catalogName;//顏色陣列名稱
	ClothListView_BaeAdater adapter;//服飾listview adapter
	ArrayAdapter<String> adapterSelect;
	ListView kindlistview;//服飾Listview
	ListView sortlistview;
	public ImageView[] navbar=new ImageView[4];
	public TextView[] navbarLine=new TextView[4];
	private String[] kind =new String[]{"cloth","colors","brand","catalog"};
	private int unselectColor,selectColor;
	private Bitmap uploadBMP=null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.cmsetting_layout);
		selectState=0;
		imageview= (ImageView) findViewById(R.id.CMPhoto);//相片位置
		trashview= (ImageView) findViewById(R.id.trash);//垃圾桶位置
		parentview = (RelativeLayout)findViewById(R.id.imageview);//背景位置 新增物件用
		parentview.setOnTouchListener(parentTouchListener);
		mainLayout=(DrawerLayout)findViewById(R.id.cmsetting_parent_layout);
		
		
		
		sortlistview=(ListView)findViewById(R.id.select_sort); //分類ListView 衣 褲 鞋
		kindlistview=(ListView)findViewById(R.id.select_kind);	//類型ListView 衣1 衣2...
		
		adapterSelect= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sortShow);
		//分類ListView adapter 抓sortShow陣列
		sortlistview.setAdapter(adapterSelect);//設定分類ListView
		sortlistview.setOnItemClickListener(sortListener);//分類ListView按鍵偵測
		
		adapter=new ClothListView_BaeAdater(CMSetting_Activity.this,this);//類型ListView adapter
		kindlistview.setAdapter(adapter);//設定類型ListView
		
		
		
		
		rightListView=(LinearLayout)findViewById(R.id.right_drawer);
		rightListView.setOnDragListener(listviewDragListener);
		navbar[0] =(ImageView)findViewById(R.id.shirt);
        navbar[1] =(ImageView)findViewById(R.id.color);
        navbar[2] =(ImageView)findViewById(R.id.brand);
        navbar[3] =(ImageView)findViewById(R.id.catalog);
        
        navbar[0].setOnClickListener(navbarListener);
        navbar[1].setOnClickListener(navbarListener);
        navbar[2].setOnClickListener(navbarListener);
        navbar[3].setOnClickListener(navbarListener);
        
        navbarLine[0] =(TextView)findViewById(R.id.shirtText);
        navbarLine[1] =(TextView)findViewById(R.id.colorText);
        navbarLine[2] =(TextView)findViewById(R.id.brandText);
        navbarLine[3] =(TextView)findViewById(R.id.catalogText);
        unselectColor=getResources().getColor(R.color.unselect);
        selectColor=getResources().getColor(R.color.select);
        
        
        ImageView backButton=(ImageView)findViewById(R.id.back);
        ImageView rightBarButton=(ImageView)findViewById(R.id.rightbar);
        backButton.setOnClickListener(actionBarListener);
        rightBarButton.setOnClickListener(actionBarListener);
        
		Intent intent=this.getIntent();
		Bundle bundle =intent.getExtras();
		photoURI =bundle.getString("data");//圖片來源
		howtogetPhoto=bundle.getString("type");//圖片抓取方式
		photoPath=bundle.getString("path");
		
		
		colorName=new String[getResources().getInteger(R.integer.colorSum)];
		colorName=getResources().getStringArray(R.array.colorName);
		
		TypedArray colors = getResources().obtainTypedArray(R.array.clothColors);//抓顏色陣列
		color = new int[colors.length()];
		for(int i=0;i<colors.length();i++){
			color[i]=colors.getColor(i,0);
			
		}
		brandName=new String[getResources().getInteger(R.integer.brandSum)];
		brandName=getResources().getStringArray(R.array.brandName);
		
		TypedArray brand = getResources().obtainTypedArray(R.array.brandkind_images);//抓顏色陣列
		drawableBrand = new Drawable[brand.length()];
		for(int i=0;i<brand.length();i++){
			drawableBrand[i]=brand.getDrawable(i);
			
		}
		catalogName=new String[getResources().getInteger(R.integer.catalogSum)];
		catalogName=getResources().getStringArray(R.array.catalogName);
		
		TypedArray catalog = getResources().obtainTypedArray(R.array.catalog_images);//抓顏色陣列
		drawableCatalog = new Drawable[catalog.length()];
		for(int i=0;i<catalog.length();i++){
			drawableCatalog[i]=catalog.getDrawable(i);;
			
		}
		
		
		runCropImage();
		
	}
	
	@Override
	public void onBackPressed() {
	    // your code.
		new AlertDialog.Builder(CMSetting_Activity.this)
		.setTitle("離開")
		.setMessage("是否要放棄目前的設定？")
		.setPositiveButton("OK", new OnClickListener(){
			public void onClick(DialogInterface DialogInterface,int i ){
				finish();
			}
		})
		.setNegativeButton("Cancel", new OnClickListener(){
			public void onClick(DialogInterface DialogInterface,int i ){
				
			}
		})
		.show();
	}
	public void showDialog(){
		dlg= new Dialog(CMSetting_Activity.this);
		dlg.setTitle("連接Beacon裝置");
		dlg.setContentView(R.layout.connect_layout);
		dlg.setCancelable(false);
		dlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
		
		Button connectOK=(Button)dlg.findViewById(R.id.connectOK);
		Button connectCancle=(Button)dlg.findViewById(R.id.connectCancle);
		
		connectOK.setOnClickListener(buttonConnect_click);
		connectCancle.setOnClickListener(buttonConnect_click);
		dlg.show();
	}
	public void showProgressDialog(){
		Progressdlg= new ProgressDialog(CMSetting_Activity.this);
		Progressdlg.setTitle("上傳中");
		Progressdlg.setMessage("請稍等....");
		Progressdlg.setCancelable(false);
		Progressdlg.setIndeterminate(true);
	//	Progressdlg.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.WHITE));
		
	
		Progressdlg.show();
	}
	@Override
	 public void onWindowFocusChanged(boolean hasFocus) {//讀取完成後執行的function
	  // TODO Auto-generated method stub
	  super.onWindowFocusChanged(hasFocus);
	  
	  	//imageview.setImageBitmap(showPhoto(photoURI,howtogetPhoto));	//設定顯示之圖片（路徑,來源方式）
	  	
	
		
	 }
	
	
	   
	    
//////////////////////////////////////////////////////////////////////////////////////////////////////////////	
	    
	    private LinearLayout.OnDragListener listviewDragListener = new LinearLayout.OnDragListener(){

			@Override
			public boolean onDrag(View v, DragEvent dragevent) {
				// TODO Auto-generated method stub
				switch (dragevent.getAction()) {
				
				
				case DragEvent.ACTION_DRAG_EXITED:
					 mainLayout.closeDrawer(rightListView);
					break;
				
				case DragEvent.ACTION_DRAG_ENDED:
					 mainLayout.openDrawer(rightListView);
					break;
				case DragEvent.ACTION_DROP:
					 
					break;

				default:
					break;
				}
				return true;
			}
	    	
	    };
	    private ImageView.OnDragListener imgDragListener = new ImageView.OnDragListener(){

			@Override
			public boolean onDrag(View v, DragEvent dragevent) {
				// TODO Auto-generated method stub
				switch (dragevent.getAction()) {
				
				
				case DragEvent.ACTION_DRAG_ENTERED:
					
					break;
				
				case DragEvent.ACTION_DROP:
					ClipData data = dragevent.getClipData();
					for (int j = 0; j < data.getItemCount(); j++) {
						ClipData.Item item = data.getItemAt(j);
						int Index=Integer.parseInt(item.getText().toString());
						int length=0;
						Drawable[] drawablekind;
						String thisViewId=String.valueOf(v.getId());
						if(adapter.getSort().equals("cloth")){
							length=getResources().getInteger(R.integer.clothSum);//抓取clothSum數值 類型個數
							
							
							
							TypedArray images = getResources().obtainTypedArray(R.array.clothkind_images);//抓取圖片陣列
							drawablekind=new Drawable[length];//重置Drawable陣列
							for(int i=0;i<length;i++){
								drawablekind[i]= images.getDrawable(i);//存入drawable陣列
							}
							photoSort.put(thisViewId, String.valueOf(0));
							photoKind.put(thisViewId, String.valueOf(Index));
							photoCloth.get(thisViewId).setImageDrawable(drawablekind[Index]);
						}else if(adapter.getSort().equals("pants")){
							length=getResources().getInteger(R.integer.pantsSum);
							
							TypedArray images = getResources().obtainTypedArray(R.array.pantskind_images);
							
							drawablekind=new Drawable[length];
							for(int i=0;i<length;i++){
								drawablekind[i]= images.getDrawable(i);
							}
							photoSort.put(thisViewId, String.valueOf(1));
							photoKind.put(thisViewId, String.valueOf(Index));
							photoCloth.get(thisViewId).setImageDrawable(drawablekind[Index]);
						}else if(adapter.getSort().equals("shoes")){
							length=getResources().getInteger(R.integer.shoesSum);
							
							TypedArray images = getResources().obtainTypedArray(R.array.shoeskind_images);
							
							drawablekind=new Drawable[length];
							for(int i=0;i<length;i++){
								drawablekind[i]= images.getDrawable(i);
							}
							photoSort.put(thisViewId, String.valueOf(2));
							photoKind.put(thisViewId, String.valueOf(Index));
							photoCloth.get(thisViewId).setImageDrawable(drawablekind[Index]);
							
							
						}else if(adapter.getSort().equals("accessory")){
							length=getResources().getInteger(R.integer.accessorySum);
							
							TypedArray images = getResources().obtainTypedArray(R.array.accessorykind_images);
							
							drawablekind=new Drawable[length];
							for(int i=0;i<length;i++){
								drawablekind[i]= images.getDrawable(i);
							}
							photoSort.put(thisViewId, String.valueOf(3));
							photoKind.put(thisViewId, String.valueOf(Index));
							photoCloth.get(thisViewId).setImageDrawable(drawablekind[Index]);
							
						}else if(adapter.getSort().equals("colors")){
							photoColorNum.put(thisViewId, String.valueOf(Index));
							photoColor.get(thisViewId).setBackgroundColor(color[Index]);
						}else if(adapter.getSort().equals("brand")){
							photoBrandNum.put(thisViewId, String.valueOf(Index));
							photoBrand.get(thisViewId).setImageDrawable(drawableBrand[Index]);
						}else if(adapter.getSort().equals("catalog")){
							photoCatalogNum.put(thisViewId, String.valueOf(Index));
							photoCatalog.get(thisViewId).setImageDrawable(drawableCatalog[Index]);
						}
						
					}
					break;

				default:
					break;
				}
				return true;
			}
	    	
	    };
	    
	private ImageView.OnClickListener navbarListener =new  ImageView.OnClickListener(){

		@Override
		public void onClick(View v) {
			
			for(int i=0;i<4;i++){
				
				navbarLine[i].setBackgroundColor(unselectColor);
				if (v == navbar[i] ){
					navbarLine[i].setBackgroundColor(selectColor);
					adapter.setKind(kind[i]);
					kindlistview.setAdapter(adapter);
					sortlistview.setAdapter(adapterSelect);
					if(i==0){
						sortlistview.setVisibility(View.VISIBLE);
					}else{
						sortlistview.setVisibility(View.GONE);
					}
				}
			}
			mainLayout.openDrawer(rightListView);
			// TODO Auto-generated method stub

			
		}
		
	};
	private void showDetail(){
		
		String txt="";
    	for (Map.Entry<String,String> entry : photoid.entrySet()) {
    			String id= entry.getKey();
			  txt+="ID:"+ id;
			  txt+="\n"+"TopMargin:"+photoParams.get(id).topMargin;
			  txt+="\n"+"LeftMargin:"+photoParams.get(id).leftMargin;
			  txt+="\n"+"Width:"+photoParams.get(id).width;
			  txt+="\n"+"Height:"+photoParams.get(id).height;
			  txt+="\n"+"Rotation:"+photoView.get(id).getRotation();
			  txt+="\n"+"Sort:"+photoSort.get(id);
			  txt+="\n"+"Kind:"+photoKind.get(id);
	          txt+="\n"+"Color:"+colorName[Integer.parseInt(photoColorNum.get(id))];
	          txt+="\n"+"Brand:"+brandName[Integer.parseInt(photoBrandNum.get(id))];
	          txt+="\n"+"Catalog:"+catalogName[Integer.parseInt(photoCatalogNum.get(id))];
			  txt+="\n";
		}	
        
        	new AlertDialog.Builder(CMSetting_Activity.this)
			.setTitle("圖片資訊")
			.setMessage(txt)
			.setPositiveButton("OK", new OnClickListener(){
				public void onClick(DialogInterface DialogInterface,int i ){
					
				}
			})
			.show();
           
        
	}
	private Button.OnClickListener buttonConnect_click =new  Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			
			if(v.getId()==R.id.connectOK){
				new UploadFileToServer().execute();
				
				Toast toast =Toast.makeText(CMSetting_Activity.this,"上傳圖片中", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				dlg.cancel();
				showProgressDialog();
			}else if(v.getId()==R.id.connectCancle){
				dlg.cancel();
			}
		}
		
	};
	
	private ImageView.OnClickListener actionBarListener =new  ImageView.OnClickListener(){

		@Override
		public void onClick(View v) {
			
			if(v.getId()==R.id.back){
				new AlertDialog.Builder(CMSetting_Activity.this)
				.setTitle("離開")
				.setMessage("是否要放棄目前的設定？")
				.setPositiveButton("OK", new OnClickListener(){
					public void onClick(DialogInterface DialogInterface,int i ){
						finish();
					}
				})
				.setNegativeButton("Cancel", new OnClickListener(){
					public void onClick(DialogInterface DialogInterface,int i ){
						
					}
				})
				.show();
			}else if(v.getId()==R.id.rightbar){
				//mainLayout.openDrawer(rightListView);
				showDialog();
			//
			}
			
			// TODO Auto-generated method stub

			
		}
		
	};
	
	private ListView.OnItemClickListener sortListener =new ListView.OnItemClickListener(){//分類按鍵偵測

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			adapter.setKind(sort[position]);//改變類型種類 衣->褲
			kindlistview.setAdapter(adapter);//設定類型listview
		}

		
	};
	
////////////////////////////////////////////////////////////////////////////////////////////////////////
	 public ImageView.OnTouchListener parentTouchListener =new ImageView.OnTouchListener(){
		Boolean isDrawerOpen =true;
		 int pointX=0;
		 int pointY=0;
		 ImageView newview= null;//新增Image物件
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			 if(mainLayout.isDrawerOpen(rightListView)) {
				  //drawer is open
				 isDrawerOpen=true;
				
			 }else{
				 isDrawerOpen=false;
			 }
			 
			// TODO Auto-generated method stub
			final int x = (int)event.getX();
			final int y = (int)event.getY();
		   
		    int oldTopMargin=0;
		    int oldLeftMargin=0;
			  switch (event.getAction() & MotionEvent.ACTION_MASK) {
	            case MotionEvent.ACTION_DOWN:
	            	
	            	RelativeLayout.LayoutParams touchParams = new RelativeLayout.LayoutParams(100,100);//新增Cloth物件之顯示資訊（寬,高）
	            	pointX=x;
	            	pointY=y;
	            	if(isDrawerOpen){
	            		return true;
	            	}
	            	if(pointX>=parentview.getRight()-100 || pointY>=parentview.getBottom()-100 || pointY<=60 ||pointX<=100){
	            		
	            		return true;
	            	
	            	}
	            	
	            	newview = new ImageView(CMSetting_Activity.this);//新增Image物件
					oldTopMargin=y - 50;
					oldLeftMargin=x - 50;
					touchParams.leftMargin =oldLeftMargin;//物件新增位置左右
					touchParams.topMargin = oldTopMargin;//物件新增位置上下
					newview.setLayoutParams(touchParams);//設定Image顯示資訊
				    newview.setImageDrawable(getResources().getDrawable(R.drawable.circle));//設定Image顯示圖片				    
				    newview.setId(viewId);
				    parentview.addView(newview);//新增Image物件到畫面上
				    
				    
				  
				  	
		          	
	                Log.d("mylog","parentTouch X:"+x+"Y:"+y);
	                break;
	            case MotionEvent.ACTION_UP:
	            	//while(newview==null);
	            	if(isDrawerOpen){
	            		return true;
	            	}
	            	if(pointX>=parentview.getRight()-100 || pointY>=parentview.getBottom()-100 || pointY<=60 ||pointX<=100){
	            		
	            		return true;
	            	
	            	}
	            	newview=(ImageView)findViewById(viewId);
	            	RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) newview.getLayoutParams();
	            	photoid.put(String.valueOf(viewId), String.valueOf(viewId-1));//將新增之Image ID新增至Map
				    photoParams.put(String.valueOf(viewId), layoutParams);//將新增之Image顯示資訊 新增至Map
				    photoView.put(String.valueOf(viewId),newview);//將新增之Image物件新增至Map
				    
				    
				   
				    
				    RelativeLayout.LayoutParams clothParams = new RelativeLayout.LayoutParams(50,50);//新增Color物件之顯示資訊（寬,高）
				    clothParams.topMargin=layoutParams.topMargin;//物件新增位置上下 與Cloth同高
				    clothParams.leftMargin=layoutParams.leftMargin-50;//物件新增位置左右 在Cloth左邊
				    ImageView clothview = new ImageView(CMSetting_Activity.this);//新增Color物件
				    //colorview.setBackgroundColor(color[viewId%3]);//設定Color顏色
				    clothview.setLayoutParams(clothParams);//設定Color顯示資訊
				    parentview.addView(clothview);//新增Color到換面上
				    photoCloth.put(String.valueOf(viewId),clothview);
				    photoKind.put(String.valueOf(viewId),String.valueOf(0));//將新增之Image分類新增至Map
				    photoSort.put(String.valueOf(viewId),String.valueOf(0));//將新增之Image類型新增至Map
				    
				   
				    RelativeLayout.LayoutParams colorParams = new RelativeLayout.LayoutParams(50,50);//新增Color物件之顯示資訊（寬,高）
				    colorParams.topMargin=layoutParams.topMargin+60;//物件新增位置上下 與Cloth同高
				    colorParams.leftMargin=layoutParams.leftMargin-50;//物件新增位置左右 在Cloth左邊
				    ImageView colorview = new ImageView(CMSetting_Activity.this);//新增Color物件
				    //colorview.setBackgroundColor(color[viewId%3]);//設定Color顏色
				    colorview.setLayoutParams(colorParams);//設定Color顯示資訊
				    parentview.addView(colorview);//新增Color到換面上
				    photoColor.put(String.valueOf(viewId),colorview);//將Colorview新增到Map
				    photoColorNum.put(String.valueOf(viewId),String.valueOf(0));//將color代號新增到Map
				    
				    RelativeLayout.LayoutParams brandParams = new RelativeLayout.LayoutParams(50,50);//新增Color物件之顯示資訊（寬,高）
				    brandParams.topMargin=layoutParams.topMargin+120;//物件新增位置上下 與Cloth同高
				    brandParams.leftMargin=layoutParams.leftMargin-50;//物件新增位置左右 在Cloth左邊
				    ImageView brandview = new ImageView(CMSetting_Activity.this);//新增Color物件
				    //colorview.setBackgroundColor(color[viewId%3]);//設定Color顏色
				    brandview.setLayoutParams(brandParams);//設定Color顯0示資訊
				    parentview.addView(brandview);//新增Color到換面上
				    photoBrand.put(String.valueOf(viewId),brandview);//將Colorview新增到Map
				    photoBrandNum.put(String.valueOf(viewId),String.valueOf(0));//將color代號新增到Map
				    
				    RelativeLayout.LayoutParams catalogParams = new RelativeLayout.LayoutParams(50,50);//新增Color物件之顯示資訊（寬,高）
				    catalogParams.topMargin=layoutParams.topMargin+180;//物件新增位置上下 與Cloth同高
				    catalogParams.leftMargin=layoutParams.leftMargin-50;//物件新增位置左右 在Cloth左邊
				    ImageView catalogview = new ImageView(CMSetting_Activity.this);//新增Color物件
				    //colorview.setBackgroundColor(color[viewId%3]);//設定Color顏色
				    catalogview.setLayoutParams(catalogParams);//設定Color顯示資訊
				    parentview.addView(catalogview);//新增Color到換面上
				    photoCatalog.put(String.valueOf(viewId),catalogview);//將Colorview新增到Map
				    photoCatalogNum.put(String.valueOf(viewId),String.valueOf(0));//將color代號新增到Map
	          	
	          	
				    newview.setOnTouchListener(touchListener);//Image觸碰偵測
		          	newview.setOnDragListener(imgDragListener);
	          
	            	
	            	
	            	viewId++;
	            	Log.d("mylog","ActionUP X:"+x+"Y:"+y);
	                break;
	            
	            case MotionEvent.ACTION_MOVE:
	            	 Log.d("myLog", "move");
		            	if(isDrawerOpen){
		            		return true;
		            	}
	            	 if(pointX>=parentview.getRight()-100 || pointY>=parentview.getBottom()-100 || pointY<=60 ||pointX<=100){
		            		
		            		return true;
		            	
		            	}
	            	float destanceNow= FloatMath.sqrt( (event.getX()-pointX)*(event.getX()-pointX) + (event.getY()-pointY)*(event.getY()-pointY));
                    if (destanceNow>=30f && event.getPointerCount() == 1) {
                        
                    	newview=(ImageView)findViewById(viewId);
    	            	//newview.setOnTouchListener(touchListener);//Image觸碰偵測
                        
    	              	 layoutParams = (RelativeLayout.LayoutParams) newview.getLayoutParams();
                        
	                        layoutParams.width=(int) (100* destanceNow/30);
	                        layoutParams.height=(int) (100* destanceNow/30);
	                        layoutParams.topMargin= (int)((pointY- 50) - (((100 * destanceNow/30)-100)/2));
	                        layoutParams.leftMargin=(int)((pointX- 50) - (((100 * destanceNow/30)-100)/2));
	                        Log.d("myLog", "W:"+ layoutParams.width +"H:"+ layoutParams.height+"Des:"+destanceNow);
	                        newview.setLayoutParams(layoutParams);
                        
	                        photoParams.put(String.valueOf(viewId), layoutParams);//將新增之Image顯示資訊 新增至Map
                      
                        
                    }
	                break;
	        
			  }
			  	
				return true;
		}
		 
		 
	 };
	 public ImageView.OnTouchListener touchListener =new ImageView.OnTouchListener(){
		 	public int xPadding;
		 	public int yPadding;
		    // these matrices will be used to move and zoom image
		 	public Matrix matrix = new Matrix();
		 	public Matrix savedMatrix = new Matrix();
		    // we can be in one of these 3 states
		    private static final int NONE = 0;
		    private static final int DRAG = 1;
		    private static final int ZOOM = 2;
		    private int mode = NONE;
		    // remember some things for zooming
		    private PointF start = new PointF();
		    private PointF mid = new PointF();
		    private float oldDist = 1f;
		    private float d = 0f;
		    private float newRot = 0f;
		    private float[] lastEvent = null;

			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				final int X = (int) event.getRawX();
			    final int Y = (int) event.getRawY();
				 ImageView view = (ImageView) v;
				 String thisId =String.valueOf(v.getId());
			        switch (event.getAction() & MotionEvent.ACTION_MASK) {
			            case MotionEvent.ACTION_DOWN:
			            	RelativeLayout.LayoutParams lParams =  (RelativeLayout.LayoutParams) view.getLayoutParams();
				            xPadding = X - lParams.leftMargin;
				            yPadding = Y - lParams.topMargin;
			                savedMatrix.set(matrix);
			                start.set(event.getX(), event.getY());
			                mode = DRAG;
			                lastEvent = null;
			                Log.d("mylog","imgTouch");
			                break;
			            case MotionEvent.ACTION_POINTER_DOWN:
			            	
			            	
			                oldDist = spacing(event);
			                if (oldDist > 10f) {
			                    savedMatrix.set(matrix);
			                    midPoint(mid, event);
			                    mode = ZOOM;
			                }
			                lastEvent = new float[4];
			                lastEvent[0] = event.getX(0);
			                lastEvent[1] = event.getX(1);
			                lastEvent[2] = event.getY(0);
			                lastEvent[3] = event.getY(1);
			                d = rotation(event);
			                break;
			            case MotionEvent.ACTION_UP:
			            	int trashLeft    = trashview.getLeft() + trashview.getWidth()/2;
			                int trashTop     = trashview.getTop()  + trashview.getHeight()/2;
			                int targetRight  = v.getLeft() + v.getWidth()*2/3;
			                int targetBottom = v.getTop() + v.getHeight()*2/3;
			             
			                if (targetRight > trashLeft && targetBottom > trashTop) {
			                	String thisViewId =String.valueOf(v.getId());
			                	photoParams.remove(thisViewId);
			                	//
			                	photoSort.remove(thisViewId);
			                	photoKind.remove(thisViewId);
			                	//
			                	photoColorNum.remove(thisViewId);
			                	//
			                	photoBrandNum.remove(thisViewId);	
			                	//
			                	photoCatalogNum.remove(thisViewId);
			                	photoid.remove(thisViewId);
			                	parentview.removeView(v);
			                	
			                	parentview.removeView(photoCloth.get(thisViewId));
			                	parentview.removeView(photoColor.get(thisViewId));
			                	parentview.removeView(photoBrand.get(thisViewId));
			                	parentview.removeView(photoCatalog.get(thisViewId));
			                	photoView.remove(thisViewId);
			                	photoCloth.remove(thisViewId);
			                	photoColor.remove(thisViewId);
			                	photoBrand.remove(thisViewId);
			                	photoCatalog.remove(thisViewId);
			                	  return true;
			                }
			                break;
			            case MotionEvent.ACTION_POINTER_UP:
			                mode = NONE;
			                lastEvent = null;
			                break;
			            case MotionEvent.ACTION_MOVE:
			                if (mode == DRAG) {
			                	
			                	RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			                	
			                	layoutParams.leftMargin = X - xPadding;
					            layoutParams.topMargin = Y - yPadding;
					            layoutParams.rightMargin = -250;
					            layoutParams.bottomMargin = -250;
					            view.setLayoutParams(layoutParams);
			                    
			                } else if (mode == ZOOM) {
			                    float newDist = spacing(event);
			                    if (newDist > 30f && event.getPointerCount() == 2) {
			                        float scale = (newDist / oldDist);
			                   
			                        
			                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			                        if(scale>=1){
				                        layoutParams.width=(int) (layoutParams.width+ scale);
				                        layoutParams.height=(int) (layoutParams.height +  scale);
				                        layoutParams.topMargin= (int)(layoutParams.topMargin-scale/2);
				                        layoutParams.leftMargin=(int)( layoutParams.leftMargin-scale/2);
				                        Log.d("myLog", "W:"+ layoutParams.width +"H:"+ layoutParams.height);
				                        view.setLayoutParams(layoutParams);
			                        
			                       }else if(scale<1 && layoutParams.width>=150 && layoutParams.height>=150){
			                    	   layoutParams.width=(int) (layoutParams.width- scale);
			                    	   layoutParams.height=(int) (layoutParams.height - scale);
			                    	   layoutParams.topMargin=(int)( layoutParams.topMargin+scale/2);
				                        layoutParams.leftMargin=(int)( layoutParams.leftMargin+scale/2);
			                    	   Log.d("myLog", "W:"+ layoutParams.width +"H:"+ layoutParams.height);
			                    	   view.setLayoutParams(layoutParams);
			                    	   
			                       }
			                      
			                        
			                    }
			                    if (lastEvent != null && event.getPointerCount() == 2) {
			                        newRot = rotation(event);
			                        float r= (newRot - d)/5;
			                        float rotation= view.getRotation();
			                     
			                        view.setRotation(rotation+r);
			               
			                    }
			                }
			                break;
			        }

			        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
			        
			        RelativeLayout.LayoutParams clothParams = (RelativeLayout.LayoutParams) (photoCloth.get(thisId)).getLayoutParams();
			        clothParams.leftMargin=layoutParams.leftMargin-50; //變更顏色物件位置
			        clothParams.topMargin=layoutParams.topMargin;//變更顏色物件位置
			        photoCloth.get(thisId).setLayoutParams(clothParams);//變更顏色物件位置
			        
			        RelativeLayout.LayoutParams colorParams = (RelativeLayout.LayoutParams) (photoColor.get(thisId)).getLayoutParams();
			        colorParams.leftMargin=layoutParams.leftMargin-50; //變更顏色物件位置
			        colorParams.topMargin=layoutParams.topMargin+60;//變更顏色物件位置
			        photoColor.get(thisId).setLayoutParams(colorParams);//變更顏色物件位置
			        
			        RelativeLayout.LayoutParams brandParams = (RelativeLayout.LayoutParams) (photoBrand.get(thisId)).getLayoutParams();
			        brandParams.leftMargin=layoutParams.leftMargin-50; //變更顏色物件位置
			        brandParams.topMargin=layoutParams.topMargin+120;//變更顏色物件位置
			        photoBrand.get(thisId).setLayoutParams(brandParams);//變更顏色物件位置
			        
			        RelativeLayout.LayoutParams catalogParams = (RelativeLayout.LayoutParams) (photoCatalog.get(thisId)).getLayoutParams();
			        catalogParams.leftMargin=layoutParams.leftMargin-50; //變更顏色物件位置
			        catalogParams.topMargin=layoutParams.topMargin+180;//變更顏色物件位置
			        photoCatalog.get(thisId).setLayoutParams(catalogParams);//變更顏色物件位置
			        
			        photoParams.put(thisId,layoutParams);//update變更後的物件資訊
			        photoView.put(thisId,view);//update變更後的物件資訊
			      
			        return true;
			}
	    	
	    	
	    };

	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////
	private class UploadFileToServer extends AsyncTask<Void, Integer, String> {
		@Override
		protected void onPreExecute() {
			// setting progress bar to zero
			
			super.onPreExecute();
		}

		

		@Override
		protected String doInBackground(Void... params) {
			return uploadFile();
		}

		@SuppressWarnings("deprecation")
		private String uploadFile() {
			if (uploadBMP == null)
				return null;
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			uploadBMP.compress(Bitmap.CompressFormat.JPEG, 50, stream); // convert Bitmap to ByteArrayOutputStream
			InputStream in = new ByteArrayInputStream(stream.toByteArray()); // convert ByteArrayOutputStream to ByteArrayInputStream
			
			
			String responseString = null;
			InputStream out=null;
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost("http://cmapp.nado.tw/android_connect_user/uploadimg.php");

			try {
				MultipartEntity reqEntity = new MultipartEntity();
				reqEntity.addPart("image",
						"1", in);
				
				httppost.setEntity(reqEntity);
				
				/*AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
						new ProgressListener() {

							@Override
							public void transferred(long num) {
								publishProgress((int) ((num / (float) totalSize) * 100));
							}
						});*/
				
				File sourceFile = new File(photoPath);

			
				// Making server call
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity r_entity = response.getEntity();

				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode == 200) {
					// Server response
					responseString = EntityUtils.toString(r_entity);
				} else {
					responseString = "Error occurred! Http Status Code: "
							+ statusCode;
				}
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			} catch (ClientProtocolException e) {
				responseString = e.toString();
			} catch (IOException e) {
				responseString = e.toString();
			}

			return responseString;

		}

		@Override
		protected void onPostExecute(String result) {
			Log.d("mylog", "Response from server: " + result);
			Toast toast =Toast.makeText(CMSetting_Activity.this,"圖片上傳成功"+"\n"+"Response from server: " + result, Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			// showing the server response in an alert dialog
			
			Progressdlg.cancel();
			showDetail();
			super.onPostExecute(result);
		}

	}

	
	
	
	
	
	
	
	
	//////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	private void runCropImage() {

	    // create explicit intent
	    Intent intent = new Intent(this, CropImage.class);
	    if(howtogetPhoto.equals("Gallery")){
		    String[] projection = { MediaColumns.DATA };
		    Uri mPictureUri= Uri.parse(photoURI);
	  		Cursor cursor = managedQuery(mPictureUri, projection, null, null,
	  				null);
	  		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
	  		cursor.moveToFirst();
	
	  		String selectedImagePath = cursor.getString(column_index);
	  		photoPath=selectedImagePath;
  		}
	    // tell CropImage activity to look for image to crop 
	    String filePath = photoPath;
	    
	    intent.putExtra(CropImage.IMAGE_PATH, filePath);

	    // allow CropImage activity to rescale image
	    intent.putExtra(CropImage.SCALE, true);

	    // if the aspect ratio is fixed to ratio 3/2
	    intent.putExtra(CropImage.ASPECT_X, 5);
	    intent.putExtra(CropImage.ASPECT_Y, 7);

	    // start activity CropImage with certain request code and listen
	    // for result
	    startActivityForResult(intent, PIC_CROP);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	    if (resultCode != RESULT_OK) {

	        return;
	    }  

	    switch (requestCode) {

	        case PIC_CROP:

	            String path = data.getStringExtra(CropImage.IMAGE_PATH);

	            // if nothing received
	            if (path == null) {

	                return;
	            }

	            // cropped bitmap
	            Bitmap bitmap = BitmapFactory.decodeFile(path);
	            uploadBMP=bitmap;
	            imageview.setImageBitmap(bitmap);
	            break;
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	}	
	
	  /**
     * Determine the space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    /**
     * Calculate the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /**
     * Calculate the degree to be rotated by.
     *
     * @param event
     * @return Degrees
     */
    private float rotation(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}
