package com.ntust.cmapp;







import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class CMRegister_Activity extends Activity implements BluetoothAdapter.LeScanCallback{
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
    private static final long SCAN_PERIOD = 5000;
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
        GetPhoto_Fragment getphotofragment =new GetPhoto_Fragment();//建立GetPhoto Fragment
        creatFragment(getphotofragment);//顯示GetPhoto Fragment
        
        navbar[0] =(ImageView)findViewById(R.id.home);
        navbar[1] =(ImageView)findViewById(R.id.picture);
        navbar[2] =(ImageView)findViewById(R.id.search);
        navbar[3] =(ImageView)findViewById(R.id.more);
        navFragment[0]=(Fragment)new Search_Activity();
        navFragment[1]=(Fragment)new GetPhoto_Fragment();
        navFragment[2]=(Fragment)new Home_Fragment();
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
    private void showMAC(){
    	String beaconIDtxt="";
        for (Map.Entry<String,String> entry : beaconMAC.entrySet()) {
        	beaconIDtxt+=entry.getKey();
        	beaconIDtxt+="\n";
        }	
        Toast toast =Toast.makeText(CMRegister_Activity.this,beaconIDtxt, Toast.LENGTH_LONG);
        
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
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
    	FragmentManager fm=getFragmentManager();
		FragmentTransaction ft =fm.beginTransaction();

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
    
}
