package com.cmapp.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//選擇ＵＩ檔
        
        Button editText_bt=(Button)findViewById(R.id.editText);
        Button alert_bt=(Button)findViewById(R.id.Alert);
        Button check_bt=(Button)findViewById(R.id.CheckBox);
        Button radio_bt=(Button)findViewById(R.id.RadioGroup);
        Button spinner_bt=(Button)findViewById(R.id.Spinner);
        Button listView_bt=(Button)findViewById(R.id.ListView);
        Button listViewMulti_bt=(Button)findViewById(R.id.ListViewMulti);
        Button veriableExchange_bt=(Button)findViewById(R.id.VeriableExchange);
        Button personalUI_bt=(Button)findViewById(R.id.personalUI);
        Button pop_bt=(Button)findViewById(R.id.pop);
        Button ingFromUrl_bt=(Button)findViewById(R.id.imgFormUrl);
        Button camera_bt=(Button)findViewById(R.id.Camera);
        Button fragment_bt=(Button)findViewById(R.id.fragment);
        Button fragmentReplace_bt=(Button)findViewById(R.id.Fragment2);
        Button sliderDrawer_bt=(Button)findViewById(R.id.sliderDrawer);
        Button dragdrop_bt=(Button)findViewById(R.id.DragDrop);
        Button touch_bt=(Button)findViewById(R.id.Touch);
        Button multiTouch_bt=(Button)findViewById(R.id.MultiTouch);
        
        
        editText_bt.setOnClickListener(btListener);
        alert_bt.setOnClickListener(btListener);
        check_bt.setOnClickListener(btListener);
        radio_bt.setOnClickListener(btListener);
        spinner_bt.setOnClickListener(btListener);
        listView_bt.setOnClickListener(btListener);
        listViewMulti_bt.setOnClickListener(btListener);
        veriableExchange_bt.setOnClickListener(btListener);
        personalUI_bt.setOnClickListener(btListener);
        pop_bt.setOnClickListener(btListener);
        ingFromUrl_bt.setOnClickListener(btListener);
        camera_bt.setOnClickListener(btListener);
        fragment_bt.setOnClickListener(btListener);
        fragmentReplace_bt.setOnClickListener(btListener);
        sliderDrawer_bt.setOnClickListener(btListener);
        dragdrop_bt.setOnClickListener(btListener);
        touch_bt.setOnClickListener(btListener);
        multiTouch_bt.setOnClickListener(btListener);
        
    }
   private Button.OnClickListener btListener=new Button.OnClickListener(){

	@Override
	public void onClick(View v) {
		
		Intent intent=new Intent();
		
		switch (v.getId()) {
		case R.id.editText:
			intent.setClass(MainActivity.this,editTextActivity.class);
				
			break;
		case R.id.Alert:
			intent.setClass(MainActivity.this,alertActivity.class);
			
			
			break;
		case R.id.CheckBox:
			intent.setClass(MainActivity.this,checkBoxActivity.class);
			
			
			break;
		case R.id.RadioGroup:
			intent.setClass(MainActivity.this,RadioGroupActivity.class);
			
			
			break;
		case R.id.Spinner:
			intent.setClass(MainActivity.this,spinnerActivity.class);
			
			
			break;
		case R.id.ListView:
			intent.setClass(MainActivity.this,listViewActivity.class);
			
			
			break;
		case R.id.ListViewMulti:
			intent.setClass(MainActivity.this,listViewMultiActivity.class);
			
			
			break;
		case R.id.VeriableExchange:
			intent.setClass(MainActivity.this,veriableExchange.class);
			
			
			break;
		case R.id.personalUI:
			intent.setClass(MainActivity.this,personalUI.class);
			
			
			break;
		case R.id.pop:
			intent.setClass(MainActivity.this,popObject.class);
			
			
			break;
		case R.id.imgFormUrl:
			intent.setClass(MainActivity.this,imgFromUrlActivity.class);
			
			
			break;
		case R.id.Camera:
			intent.setClass(MainActivity.this,cameraActivity.class);
			
			
			break;
		case R.id.fragment:
			intent.setClass(MainActivity.this,fragmentActivity.class);
			
			
			break;
		case R.id.Fragment2:
			intent.setClass(MainActivity.this,fragmentReplaceActivity.class);
			
			
			break;
		case R.id.sliderDrawer:
			intent.setClass(MainActivity.this,SliderDrawerActivity.class);
			
			
			break;
		case R.id.DragDrop:
			intent.setClass(MainActivity.this,dragAndDropActivity.class);
			
			
			break;
		case R.id.Touch:
			intent.setClass(MainActivity.this,touchActivity.class);
			
			
			break;
		case R.id.MultiTouch:
			intent.setClass(MainActivity.this,multiTouch.class);
			
			
			break;

		default:
			break;
		}
		
		
		startActivity(intent);
		
	}
	   
   };

    

}
