package com.cmapp.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class editTextActivity extends Activity {
	private TextView textName;
	private EditText edName;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		   setContentView(R.layout.edit_text);//選擇ＵＩ檔
		   
		   	textName=(TextView)findViewById(R.id.tName);
		   	edName=(EditText)findViewById(R.id.edName);
		    Button btBack= (Button)findViewById(R.id.btBack);
		    Button btOk= (Button)findViewById(R.id.btOk);
		     btBack.setOnClickListener(btListener);
		     btOk.setOnClickListener(btListener);
	}
	private Button.OnClickListener btListener=new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			if(v.getId()==R.id.btBack){
				finish();
			}
			if(v.getId()==R.id.btOk){
				if(!edName.getText().toString().equals(""))
				textName.setText(textName.getText().toString() + edName.getText().toString());
			}
			
		}
		
	};
}
