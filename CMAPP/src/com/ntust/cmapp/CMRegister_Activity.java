package com.ntust.cmapp;







import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ntust.cmapp.CMSetting_Activity.EnterBeaconID;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CMRegister_Activity extends Activity implements BluetoothAdapter.LeScanCallback{
	public Map<String, String> customerID = new HashMap<String, String>();
	public Map<String, Customer> itemInfo = new HashMap<String,Customer>();
	public Map<String,Bitmap> customerphoto = new HashMap<String,Bitmap>();
	public ArrayList<String> customerItemID = new ArrayList<String>();
	public Map<String, ArrayList<String>> customerItemListMap = new HashMap<String, ArrayList<String>>();
	public String beaconInfo[];
	public HashMap<String, Map> allMap =new HashMap<String,Map>();
	private LinearLayout mainLayout;
	public LinearLayout cmregister_parent_layout;
	public Uri mPictureUri; 
	//public LinearLayout navbar;
	public Bitmap bmp;
	public ImageView[] navbar=new ImageView[4];
	public TextView[] navbarLine=new TextView[4];
	private Fragment[] navFragment= new Fragment[4];
	private int unselectColor,selectColor;
	private Map<String, String> beaconMAC =new HashMap<String, String>();
	/*BLE*///////////
	
	
	 /** BLE 機器スキャンタイムアウト (ミリ秒) */
    private static final long SCAN_PERIOD = 1000;
    /** 検索機器の機器名 */
    private static final String DEVICE_NAME = "SensorTag";
    /** 対象のサービスUUID */
    private static final String DEVICE_BUTTON_SENSOR_SERVICE_UUID = "0000ffe0-0000-1000-8000-00805f9b34fb";
    /** 対象のキャラクタリスティックUUID */
    private static final String DEVICE_BUTTON_SENSOR_CHARACTERISTIC_UUID = "0000ffe1-0000-1000-8000-00805f9b34fb";
    /** キャラクタリスティック設定UUID */
    private static final String CLIENT_CHARACTERISTIC_CONFIG = "00002902-0000-1000-8000-00805f9b34fb";
 
    private static final String TAG = "BLESample";
    private BleStatus mStatus = BleStatus.DISCONNECTED;
    private Handler mHandler;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothManager mBluetoothManager;
    private BluetoothGatt mBluetoothGatt;
    private TextView mStatusText;
    JSONObject ReturnInfoJsonobject = new JSONObject();
    String[] customerphotoName ;
    

	
	
	
	/*BLE*///////////
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cmregister_layout);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);//禁止畫面翻轉
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//禁止畫面翻轉
        
        mainLayout =(LinearLayout)findViewById(R.id.main_layout);//抓取主要畫面位置
      //  navbar = (LinearLayout) findViewById(R.id.left_drawer);//抓取側邊欄位置
        cmregister_parent_layout=(LinearLayout)findViewById(R.id.cmregister_parent_layout);//最外層顯示位置
       
        
        navbar[0] =(ImageView)findViewById(R.id.home);
        navbar[1] =(ImageView)findViewById(R.id.picture);
        navbar[2] =(ImageView)findViewById(R.id.search);
        navbar[3] =(ImageView)findViewById(R.id.more);
        navFragment[0]=(Fragment)new CMBrowse_Fragment();
        navFragment[1]=(Fragment)new GetPhoto_Fragment();
        navFragment[2]=(Fragment)new Search_Activity();
        navFragment[3]=(Fragment)new Home_Fragment();
        navbar[0].setOnClickListener(navbarListener);
        navbar[1].setOnClickListener(navbarListener);
        navbar[2].setOnClickListener(navbarListener);
        navbar[3].setOnClickListener(navbarListener);
        navbarLine[0] =(TextView)findViewById(R.id.homeText);
        navbarLine[1] =(TextView)findViewById(R.id.pictureText);
        navbarLine[2] =(TextView)findViewById(R.id.searchText);
        navbarLine[3] =(TextView)findViewById(R.id.moreText);
        unselectColor=getResources().getColor(R.color.unselect);
        selectColor=getResources().getColor(R.color.select);
        
        
        mBluetoothManager = (BluetoothManager)getSystemService(BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
 
       
        
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mStatusText.setText(((BleStatus) msg.obj).name());
            }
        };
        connect();
        
    }
    public  Map<String, String> getCustomerID(){
    	return customerID;
    }
    private void inputCustomer(){
    	int customerno = 2;
        for(int i=1;i<=customerno;i++){
            String customerPhotoID = String.valueOf(i);
            customerID.put(customerPhotoID, customerPhotoID); //設定photo的id&值

            int itemID = 1;
            String CustomerItemID = customerPhotoID + ( itemID<10 ? ("0"+itemID) : itemID);
            Customer customerItem = new Customer(customerPhotoID, 100, 100, 0, 280, "uniqlo", 0, 1, 0);
            itemInfo.put(CustomerItemID, customerItem);
            customerItemID.add(CustomerItemID);
            itemID ++;
            
            CustomerItemID = customerPhotoID +( itemID<10 ? ("0"+itemID) : itemID);
            customerItem = new Customer(customerPhotoID, 300, 300, 150, 200, "uniqlo", 1, 3, 1);
            customerItemID.add(CustomerItemID);
            itemInfo.put(CustomerItemID, customerItem);
            itemID ++;
            
            CustomerItemID = customerPhotoID +( itemID<10 ? ("0"+itemID) : itemID);
            customerItem = new Customer(customerPhotoID, 400, 400, 400, 150, "uniqlo",2,9,0);
            customerItemID.add(CustomerItemID);
            itemInfo.put(CustomerItemID, customerItem);
           
            customerItemListMap.put(customerPhotoID,customerItemID);
            customerItemID = new ArrayList<String>();
        }
    }
    private void showMAC(){
    	String beaconIDtxt="";
        for (Map.Entry<String,String> entry : beaconMAC.entrySet()) {
        	beaconIDtxt+=entry.getKey();
        	beaconIDtxt+="\n";
        }	
        Toast toast =Toast.makeText(CMRegister_Activity.this,beaconIDtxt, Toast.LENGTH_LONG);
        
        new GetItemInfo().execute();//傳出BeaconID收回Item資訊
        
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
		//inputCustomer();
		
    }
    private void connect() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
            	showMAC();
            	
                mBluetoothAdapter.stopLeScan(CMRegister_Activity.this);
                if (BleStatus.SCANNING.equals(mStatus)) {
                    setStatus(BleStatus.SCAN_FAILED);
                }
            }
        }, SCAN_PERIOD);
        
      
        mBluetoothAdapter.stopLeScan(this);
        mBluetoothAdapter.startLeScan(this);
        
        //setStatus(BleStatus.SCANNING);
    }
    
	@Override
	public void onBackPressed() {
	    // your code.
		new AlertDialog.Builder(CMRegister_Activity.this)
		.setTitle("離開")
		.setMessage("是否要離開行動廣告達人？")
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
	
    private ImageView.OnClickListener navbarListener =new ImageView.OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String beaconIDtxt="";
	        for (Map.Entry<String,String> entry : beaconMAC.entrySet()) {
	        	beaconIDtxt+=entry.getKey();
	        	beaconIDtxt+="\n";
	        }	
	        Toast toast =Toast.makeText(CMRegister_Activity.this,beaconIDtxt, Toast.LENGTH_LONG);
	        
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
			for(int i=0;i<4;i++){
				
				navbarLine[i].setBackgroundColor(unselectColor);
				if (v ==navbar[i]){
					navbarLine[i].setBackgroundColor(selectColor);
					replaceFragment(navFragment[i]);
				}
			}
		}
    	
    	
    };

    
    private void creatFragment(Fragment fragment){//顯示Fragment
    	
    	Bundle extras=new Bundle();
    	allMap.put("customerID", customerID);
    	allMap.put("customerItemListMap", customerItemListMap);
    	allMap.put("itemInfo", itemInfo);
    	allMap.put("customerphoto", customerphoto);
    	extras.putSerializable("allMap", allMap);
    	//Intent intent=new Intent();
    	//intent.putExtra("allMap", extras);
    	
    	FragmentManager fm=getFragmentManager();
		FragmentTransaction ft =fm.beginTransaction();
		fragment.setArguments(extras);
		ft.add(R.id.main_layout, fragment);//顯示GetPhoto Fragment
		ft.commit();

    }
    
    public void replaceFragment(Fragment fragment){//替換Fragment
    	FragmentManager fm=getFragmentManager();
		FragmentTransaction ft =fm.beginTransaction();
		
		ft.replace(R.id.main_layout, fragment);//替換GetPhoto Fragment
		ft.commit();
    }
    
    
   

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        Log.d(TAG, "device found: " + device.getName());
        beaconMAC.put(device.getAddress(), device.getAddress());
       
        //TextView mac=(TextView)findViewById(R.id.mac);
       // mac.setText(mac.getText()+device.getAddress());
       /* if (DEVICE_NAME.equals(device.getName())) {
            setStatus(BleStatus.DEVICE_FOUND);
            
            mac.setText(device.getAddress());
            // 省電力のためスキャンを停止する
            mBluetoothAdapter.stopLeScan(this);
 
            // GATT接続を試みる
            mBluetoothGatt = device.connectGatt(this, false, mBluetoothGattCallback);
        }*/
    }
    private void setStatus(BleStatus status) {
        mStatus = status;
        mHandler.sendMessage(status.message());
    }
    private enum BleStatus {
        DISCONNECTED,
        SCANNING,
        SCAN_FAILED,
        DEVICE_FOUND,
        SERVICE_NOT_FOUND,
        SERVICE_FOUND,
        CHARACTERISTIC_NOT_FOUND,
        NOTIFICATION_REGISTERED,
        NOTIFICATION_REGISTER_FAILED,
        CLOSED
        ;
        public Message message() {
            Message message = new Message();
            message.obj = this;
            return message;
        }
    }
    
    
    class GetItemInfo extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		
		}

		/**
		 * Entering BeaconID
		 * */
		@Override
		protected String doInBackground(String... args) {

			JSONObject UploadBeaconIDObject = new JSONObject();
			
			String FakeBeaconID = "00-00-00-00-00-00";
			String FakeBeaconID2 = "22-33-22-00-00-55";
			JSONArray BeaconJsonarray = new JSONArray();
			ArrayList<NameValuePair> jsonarray = new ArrayList<NameValuePair>();
			BeaconJsonarray.put(FakeBeaconID);
			BeaconJsonarray.put(FakeBeaconID2);
			
			try {
				UploadBeaconIDObject.put("Beacon", BeaconJsonarray);//格式
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			
			jsonarray.add(new BasicNameValuePair("Beacon", UploadBeaconIDObject.toString()));// 重要！！
			try {
				// Note that create product url accepts POST method

				HttpClient httpClient = new DefaultHttpClient();
				HttpPost httpPost = new HttpPost("http://cmapp.nado.tw/android_connect_user/get_user_Ad.php");
				httpPost.setEntity(new UrlEncodedFormEntity(jsonarray,HTTP.UTF_8));
				HttpResponse httpResponse = httpClient.execute(httpPost); 
				HttpEntity httpEntity = httpResponse.getEntity();
				Log.d("mylog", "ENTER");
				try {
					String json = EntityUtils.toString(httpEntity);
					
					ReturnInfoJsonobject = new JSONObject(json);
					Log.d("mylog", "RRRRR");
					Log.d("mylog", ReturnInfoJsonobject.toString());
				} catch (JSONException ex) {
				}
			} catch (Exception e) {
				e.printStackTrace();
				Log.d("mylog", "error");
			}
			return null;
			
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			
			String ReturnInfo = ReturnInfoJsonobject.toString();
			
			try {
				JSONArray jsonarray = ReturnInfoJsonobject.getJSONArray("AllAd");
				JSONObject AllItemjsonobject = new JSONObject(); 
				int length1 = jsonarray.length(); 
				int customerSize=customerID.size();
				customerphotoName=new String[length1];
				for(int i=0;i<length1;i++){
					AllItemjsonobject=jsonarray.getJSONObject(i);//找第i條
					
					String echoUserID = AllItemjsonobject.getString("UserID");
					String echoAdID = AllItemjsonobject.getString("AdID");//抓userID, AdID
					customerphotoName[i]=echoUserID+echoAdID;
					JSONArray jsonarray2 = AllItemjsonobject.getJSONArray("Item");
					JSONObject PartItemjsonobject = new JSONObject(); 
					int length2 = jsonarray2.length(); 
					customerID.put(String.valueOf(customerSize+i+1), echoUserID+echoAdID);
					
					for(int j=0;j<length2;j++){
						PartItemjsonobject=jsonarray2.getJSONObject(j);//找第j條
						Log.d("mylog", PartItemjsonobject.toString());
						
						//String UserID = PartItemjsonobject.getString("UserID");
						//String AdID = PartItemjsonobject.getString("AdID");
						int ItemIndex = Integer.parseInt(PartItemjsonobject.getString("ItemIndex"));
						int ItemWidth = Integer.parseInt(PartItemjsonobject.getString("ItemWidth"));
						
						int ItemHeight = Integer.parseInt(PartItemjsonobject.getString("ItemHeight"));
						int ItemTop = Integer.parseInt(PartItemjsonobject.getString("ItemTop"));
						int ItemLeft = Integer.parseInt(PartItemjsonobject.getString("ItemLeft"));
						int ItemType = Integer.parseInt(PartItemjsonobject.getString("ItemType"));
						int ItemColor = Integer.parseInt(PartItemjsonobject.getString("ItemColor"));
						String ItemBrand = PartItemjsonobject.getString("ItemBrand");
						int ItemSex = Integer.parseInt(PartItemjsonobject.getString("ItemSex"));
						
						
						Log.d("mylog", echoUserID);
						Log.d("mylog", echoAdID);
						Log.d("mylog", "_____");
						String CustomerItemID = echoUserID + ( ItemIndex+1<10 ? ("0"+ItemIndex+1) : ItemIndex+1);
						Customer customerItem = new Customer(echoUserID, ItemWidth, ItemHeight, ItemTop, ItemLeft, ItemBrand, ItemType, ItemColor, ItemSex);
						
						customerItemID.add(CustomerItemID);
			            itemInfo.put(CustomerItemID, customerItem);
			            
			            
					}
					
					customerItemListMap.put(echoUserID+echoAdID,customerItemID);
		            customerItemID = new ArrayList<String>();
				}
				imgDownLoader id =new imgDownLoader(CMRegister_Activity.this);
				id.execute(customerphotoName);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			 
			//showDetail(UploadSuccessInput);
			
		}

	}
    
    public class imgDownLoader extends AsyncTask<String, Integer, Bitmap[]> {
		private ProgressDialog progressDialog_;
		private Activity uiActivity_;
		 public imgDownLoader(Activity activity) {
		        super();
		        uiActivity_ = activity;
		        
		    }
		protected void onPreExecute() {
//			progressDialog_ = new ProgressDialog(uiActivity_);
//	        progressDialog_.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//	        progressDialog_.setIndeterminate(false);
//	        progressDialog_.show();
		}
		@Override
		protected Bitmap[] doInBackground(String... params) {
			// TODO Auto-generated method stub
			try {
				Bitmap[] outBitmap = new Bitmap[customerphotoName.length]; 
				for(int i=0;i<customerphotoName.length;i++){
					Log.d("mylog","photoName:"+customerphotoName[i]);
					URL url=new URL("http://cmapp.nado.tw/photo/"+customerphotoName[i]+".jpg");
					HttpURLConnection httpCon =(HttpURLConnection) url.openConnection();
					Log.d("mylog","photoName01:"+customerphotoName[i]);
					httpCon.connect();
					Log.d("mylog","photoName02:"+customerphotoName[i]);
					if(httpCon.getResponseCode()!=200){
						throw new Exception("Failed to Connect!");
					}
					InputStream is =httpCon.getInputStream();
					Log.d("mylog","photoName03:"+customerphotoName[i]);
					outBitmap[i] = BitmapFactory.decodeStream(is);   
					Log.d("mylog","photoName04:"+customerphotoName[i]);
					 
				      /*  int width_  = outBitmap[i].getWidth();
				        int height_ = outBitmap[i].getHeight();
				        int totalPixcel = width_ * height_;
				        //progressDialog_.setMax(totalPixcel);
				 
				        int k,j;
				        for(k = 0; k < width_; k++) {
				            for(j = 0; j < height_; j++) {
				                int pixelColor = outBitmap[i].getPixel(k, j);
				              // outBitmap.setPixel(i, j, Color.argb(60, Color.red(pixelColor), Color.green(pixelColor), Color.blue(pixelColor)));
				            }
				            //publishProgressメソッドを呼ぶことで
				            //onProgressUpdateメソッドが呼ばれ、進捗状況がUIスレッドで表示されます。
				            //publishProgress(k+j);
				        }
				       */
				}
				
				
				return outBitmap;
				
			} catch (Exception e) {
				Log.d("mylog","Faild to load Img!");
				// TODO: handle exception
			}
			return null;
		}
		 @Override
		    protected void onProgressUpdate(Integer... progress) {
		        //progressDialog_.incrementProgressBy(progress[0]);
		    }
		
		protected void onPostExecute(Bitmap[] img) {
			Log.d("mylog","onPostExecute");
			if(img!=null){
				for(int i=0;i<img.length;i++){
					customerphoto.put(customerphotoName[i], img[i]);
				}
				
				
				
			}
			CMBrowse_Fragment getphotofragment =new CMBrowse_Fragment();//建立GetPhoto Fragment
	         creatFragment(getphotofragment);//顯示GetPhoto Fragment
			// progressDialog_.dismiss();
		}

	}
}
