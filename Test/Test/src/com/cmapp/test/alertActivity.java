package com.cmapp.test;

import java.security.PublicKey;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class alertActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alert);
		Button toast=(Button)findViewById(R.id.btToast);
		Button alert=(Button)findViewById(R.id.btAlert);
		toast.setOnClickListener(btListener);
		alert.setOnClickListener(btListener);
	}
	
	private Button.OnClickListener btListener=new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btToast:
				Toast toast =Toast.makeText(alertActivity.this,"這是Toast", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				break;
			case R.id.btAlert:
				
				
				new AlertDialog.Builder(alertActivity.this)
				.setTitle("Alert")
				.setMessage("這是AlertDialog \n 是否要離開？")
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
				
				break;
			default:
				break;
			}
			
		}
			
	};
		
	
}
