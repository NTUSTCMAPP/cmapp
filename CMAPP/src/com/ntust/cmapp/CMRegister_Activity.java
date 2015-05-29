package com.ntust.cmapp;




import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CMRegister_Activity extends Activity {
	private LinearLayout mainLayout;
	public LinearLayout cmregister_parent_layout;
	public Uri mPictureUri; 
	//public LinearLayout navbar;
	public Bitmap bmp;
	public ImageView[] navbar=new ImageView[4];
	public TextView[] navbarLine=new TextView[4];
	private Fragment[] navFragment= new Fragment[4];
	private int unselectColor,selectColor;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.cmregister_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
