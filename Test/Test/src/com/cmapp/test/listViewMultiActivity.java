package com.cmapp.test;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class listViewMultiActivity extends Activity {
	private String[] select=new String[]{"A","B","C","D"};
	private ListView listView;
	private TextView lvText;
	private Button btListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listviewmulti);
		listView=(ListView)findViewById(R.id.btListViewMulti);
		lvText=(TextView)findViewById(R.id.listViewMultiText);
		btListView=(Button)findViewById(R.id.ListViewMultiOk);
		
		ArrayAdapter<String> adapterSelect= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_multiple_choice,select);
		
		listView.setAdapter(adapterSelect);
		listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		btListView.setOnClickListener(btListener);
		listView.setOnItemClickListener(lvListener);
	}
	

	private Button.OnClickListener btListener =new Button.OnClickListener(){

		@Override
		public void onClick(View v) {
			String showtext="";
			// TODO Auto-generated method stub
			for(int i=0;i<select.length;i++){
				if(listView.isItemChecked(i)){
					showtext+=select[i]+" ";
				}
			}
			lvText.setText(showtext);
		}
		
		
	};
	private ListView.OnItemClickListener lvListener =new ListView.OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
	
		}

		
	};
}
