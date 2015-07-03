package com.ntust.cmapp;


import android.app.ListFragment;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Search_Activity extends ListFragment {
	String[] selectName=new String[]{"AAAA","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD"};
	String[] selectDetail=new String[]{"aaaaaaa","bbbbbbb","ccccccc","ddddddd","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD","BBBB","CCCC","DDDD"};
	int[] srcId=new int[]{R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher,R.drawable.ic_launcher};
	private ListView listView;
	
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		//ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, People.NAME);
		//ArrayAdapter<CharSequence> adapterSelect= ArrayAdapter.createFromResource(getActivity().getBaseContext(),R.array.Navbar_Selection,android.R.layout.simple_list_item_1);	
		//this.setListAdapter(adapterSelect);
		
		MyAdapeter adapter=new MyAdapeter((CMRegister_Activity)getActivity(),0);//類型ListView adapter
		this.setListAdapter(adapter);//設定類型ListView
		
	
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
			
			 
			converView =myInflater.inflate(R.layout.search_listview_fragment_layout, null);//指定listview的layout 參照personallistview.xml
			
		
			
			/*將新增物件放在這邊
				1.以position為key抓取userid
				2.以userid 為key 抓item的arrayList
				3.利用arrayList的大小跑for迴圈新增item到畫面中
				 
			*
			*/
			LinearLayout thisLayout =(LinearLayout)converView.findViewById(R.id.serach_mainLayout);
			TextView textView=new TextView((CMRegister_Activity)getActivity());
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
