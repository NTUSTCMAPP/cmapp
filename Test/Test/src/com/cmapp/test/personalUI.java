package com.cmapp.test;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class personalUI extends Activity {
	
	String[] selectName=new String[]{"AAAA","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD"};
	String[] selectDetail=new String[]{"aaaaaaa","bbbbbbb","ccccccc","ddddddd","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD"};
	int[] srcId=new int[]{R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
	private ListView listView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		//請參閱本檔 及personalui.xml personallistview.xml 
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personalui);//使用設有listview的layout 參照personalui.xml
		
		
		listView=(ListView)findViewById(R.id.personalUI_listview);//抓到畫面上listview的位置
		
		MyAdapeter adapter=new MyAdapeter(this,R.layout.personallistview);//建立新類別物件
		listView.setAdapter(adapter);	//讓listview使用此物件顯示
		
	}
	
	public class MyAdapeter extends BaseAdapter{//建立adapter類別
		private LayoutInflater myInflater;//固定
		
		
		public MyAdapeter(Context c,int layoutResource){
			myInflater =LayoutInflater.from(c);//指定主layout來源
			
		}
		
		@Override
		public Object getItem(int position){
			return selectName[position];//getItem方法傳回的物件 此處可用return null;
		}
		
		@Override
		public long getItemId(int position){
			return position; //傳回項目序數 0,1,2... 固定
		}@Override
		public int getCount() {
			
			return selectName.length;//傳回listview顯示欄位數 此處用return userid.size(); 
		}

		@Override
		public View getView(int position,View converView,ViewGroup parent){
			
			 
			converView =myInflater.inflate(R.layout.personallistview, null);//指定listview的layout 參照personallistview.xml
			
			ImageView imgLogo =(ImageView)converView.findViewById(R.id.imgLogo);//指出顯示項目 例如照片,顯示物件位置,下面icon欄
			TextView txtName=((TextView)converView.findViewById(R.id.txtName));
			TextView txtDetail=((TextView)converView.findViewById(R.id.txtDetail));
			imgLogo.setImageResource(srcId[position]);//指定個位置要顯示的來源 
			txtName.setText(selectName[position]);
			txtDetail.setText(selectDetail[position]);
			
			/*將新增物件放在這邊
				1.以position為key抓取userid
				2.以userid 為key 抓item的arrayList
				3.利用arrayList的大小跑for迴圈新增item到畫面中
				 
			*
			*/
			RelativeLayout thisLayout =(RelativeLayout)converView.findViewById(R.id.mainpersonalLayout);
			TextView textView=new TextView(getApplicationContext());
			RelativeLayout.LayoutParams textLayoutParams =new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,300);
			textView.setLayoutParams(textLayoutParams);
			textLayoutParams.setMargins(0, position*20, 0, 0);
			textView.setText(selectName[position]);
			textView.setTextSize(60);
			textView.setId(1234);
			textView.setTextColor(Color.RED);
			thisLayout.addView(textView);
			
			return converView;//回傳給adapter
		}
		
	}
}
