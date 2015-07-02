package com.ntust.cmapp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ntust.cmapp.Search_Activity.MyAdapeter;
import com.ntust.cmapp.cmappTest_Activity.CMAdapeter;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class CMBrowse_Fragment extends ListFragment {
	 Map<String,String> customerId = new HashMap<String,String>(); //建立一個Map介面為CustomerId
	    //服飾Item
	    Map<String,String> itemId = new HashMap<String,String>();
	    ArrayList<String> customerItemId = new ArrayList<String>();
	    Map<String,ArrayList<String>> customerItemListMap = new HashMap<String,ArrayList<String>>();
	    Map<String,String> itemWidth = new HashMap<String,String>();
	    Map<String,String> itemHeight= new HashMap<String,String>();
	    Map<String,String> itemTopMargin = new HashMap<String,String>();
	    Map<String,String> itemLeftMargin = new HashMap<String,String>();
	    Map<String,String> itemRotation= new HashMap<String,String>();
	    Map<String,String> itemType = new HashMap<String,String>();
	    Map<String,String> itemStyle = new HashMap<String,String>();
	    Map<String,String> itemColor = new HashMap<String,String>();
	    Map<String,String> itemBrand = new HashMap<String,String>();
	    Map<String,String> itemSex = new HashMap<String,String>();
	    private ListView mListView;
	    int screen_width;
	    int UserID = 0;
	    
	    String Brand ="uniqlo"; //品牌(暫存uniqlo)
	    String[] Type;//種類(上身/下著等)
	    
	    String[] Color;//顏色(十六進位)
	    String[] Sex={"Male","Female"}; //性別(男女)
	    Drawable[] drawableType;
	    CMAdapeter adapter;
	    String customerPhotoID;
	    String CustomerItemId;
	
	 public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		Type=new String[getResources().getInteger(R.integer.clothesSum)];
		Type=getResources().getStringArray(R.array.clothes);
		TypedArray TypeArray = getResources().obtainTypedArray(R.array.clothes_images);//抓顏色陣列
		drawableType = new Drawable[TypeArray.length()];
		for(int i=0;i<TypeArray.length();i++){
			drawableType[i]=TypeArray.getDrawable(i);;
			
		}
		
		Color=new String[getResources().getInteger(R.integer.colorSum)];
		Color=getResources().getStringArray(R.array.colorName);
		
		int customerno = 2;
        for(int i=1;i<=customerno;i++){
            String customerPhotoID = String.valueOf(i);
            customerId.put(customerPhotoID, customerPhotoID); //設定photo的id&值

            int itemID = 1;
            CustomerItemId = customerPhotoID + ( itemID<10 ? ("0"+itemID) : itemID);

            itemId.put(CustomerItemId, "101");
            itemWidth.put(CustomerItemId,"100");
            itemHeight.put(CustomerItemId,"100");
            itemTopMargin.put(CustomerItemId,"0");
            itemLeftMargin.put(CustomerItemId,"280");
            itemRotation.put(CustomerItemId,"0");
            itemColor.put(CustomerItemId,"1");
            itemBrand.put(CustomerItemId,"uniqlo");
            itemType.put(CustomerItemId,"0");
            itemSex.put(CustomerItemId,"0");
            customerItemId.add(CustomerItemId);

            itemID ++;
            CustomerItemId = customerPhotoID +( itemID<10 ? ("0"+itemID) : itemID);

            itemId.put(CustomerItemId, "102");

            itemWidth.put(CustomerItemId,"300");
            itemHeight.put(CustomerItemId,"300");
            itemTopMargin.put(CustomerItemId,"150");
            itemLeftMargin.put(CustomerItemId,"200");
            itemColor.put(CustomerItemId,"3");
            itemBrand.put(CustomerItemId,"uniqlo");
            itemType.put(CustomerItemId,"1");
            itemSex.put(CustomerItemId,"1");
            customerItemId.add(CustomerItemId);
            itemID ++;
            CustomerItemId = customerPhotoID +( itemID<10 ? ("0"+itemID) : itemID);

            itemId.put(CustomerItemId, "103");
            itemWidth.put(CustomerItemId,"400");
            itemHeight.put(CustomerItemId,"400");
            itemTopMargin.put(CustomerItemId,"400");
            itemLeftMargin.put(CustomerItemId,"150");
            itemColor.put(CustomerItemId,"9");
            itemBrand.put(CustomerItemId,"uniqlo");
            itemType.put(CustomerItemId,"2");

            itemSex.put(CustomerItemId,"0");
            customerItemId.add(CustomerItemId);
            
            itemID ++;
            CustomerItemId = customerPhotoID +( itemID<10 ? ("0"+itemID) : itemID);

            itemId.put(CustomerItemId, "104");
            itemWidth.put(CustomerItemId,"400");
            itemHeight.put(CustomerItemId,"400");
            itemTopMargin.put(CustomerItemId,"400");
            itemLeftMargin.put(CustomerItemId,"150");
            itemColor.put(CustomerItemId,"9");
            itemBrand.put(CustomerItemId,"uniqlo");
            itemType.put(CustomerItemId,"2");

            itemSex.put(CustomerItemId,"0");
            customerItemId.add(CustomerItemId);
            
            customerItemListMap.put(customerPhotoID,customerItemId);
            customerItemId = new ArrayList<String>();
            UserID=i;
        }

		CMAdapeter adapter=new CMAdapeter((CMRegister_Activity)getActivity(),0,(CMRegister_Activity)getActivity());//類型ListView adapter
		this.setListAdapter(adapter);//設定類型ListView
	}
	
	  public class CMAdapeter extends BaseAdapter{//建立adapter類別
	        private LayoutInflater CMInflater;//固定
	        private Activity ParentActivity ;

	        public CMAdapeter(Context c,int layoutResource,Activity activity){
	            CMInflater =LayoutInflater.from(c);//指定主layout來源
	            ParentActivity=activity;
	        }

	        @Override
	        public Object getItem(int position){
	            return null;
	        }
	        @Override
	        public long getItemId(int position){
	            return position;
	        }
	        @Override
	         public int getCount() {
	            return customerId.size();
	        }
	        @Override
	        public View getView(int position,View convertView,ViewGroup parent){
	            String customerindex = String.valueOf(position+1);
	            convertView =CMInflater.inflate(R.layout.cmapp_listview, null);//指定listview的layout
	            
	            RelativeLayout main_layout =(RelativeLayout)convertView.findViewById(R.id.main_layout);
	            ImageView mImgPhoto =(ImageView)convertView.findViewById(R.id.photo);//顯示使用者照片
	           mImgPhoto.setImageResource(R.drawable.photo);

	            ArrayList<String> thisList =customerItemListMap.get(customerId.get(customerindex));
	            LinearLayout logo_layout =(LinearLayout)convertView.findViewById(R.id.logo_layout);
	            
	            Display display = getActivity().getWindowManager().getDefaultDisplay(); 
	            
	            int icon_width = (display.getWidth()/4);
	            RelativeLayout.LayoutParams frame_icon = new RelativeLayout.LayoutParams(icon_width,200);

	            for(int i=0;i<thisList.size();i++){
	                final String listitem =thisList.get(i);
	                RelativeLayout.LayoutParams margin_main = new RelativeLayout.LayoutParams(Integer.parseInt(itemWidth.get(listitem)), Integer.parseInt(itemHeight.get(listitem)));  //設定背景長寬
	                ImageView mImgClothes = new ImageView(ParentActivity); //Item
	                margin_main.topMargin=(Integer.parseInt(itemTopMargin.get(listitem)));
	                margin_main.leftMargin=(Integer.parseInt(itemLeftMargin.get(listitem)));
	                mImgClothes.setImageDrawable(getResources().getDrawable(R.drawable.circle));
	                mImgClothes.setLayoutParams(margin_main);
	               // mImgClothes.setImageResource(mImgArr[i]);
	                final Toast item_toast = new Toast(ParentActivity);
	                mImgClothes.setOnClickListener(new View.OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                        item_toast.setGravity(Gravity.CENTER,Integer.parseInt(itemLeftMargin.get(listitem)),Integer.parseInt(itemTopMargin.get(listitem)));
	                        item_toast.makeText(ParentActivity,"Brand:"+itemBrand.get(listitem) +"\n"
	                                                          +"Type:"+ Type[Integer.parseInt(itemType.get(listitem))] +"\n"
	                                                         
	                                                          +"Color:"+ Color[Integer.parseInt(itemColor.get(listitem))]+"\n"
	                                                          +"Classification:" + Sex[Integer.parseInt(itemSex.get(listitem))] ,Toast.LENGTH_SHORT).show();
	                    }
	                });
	                main_layout.addView(mImgClothes);

	                ImageView mLogoIcon =new ImageView(ParentActivity);//LogoIcon
	                 mLogoIcon.setId(Integer.parseInt(itemId.get(listitem)));
	                 mLogoIcon.setRotation(0);
	                 mLogoIcon.setImageDrawable(drawableType[Integer.parseInt(itemType.get(listitem))]);
	                 mLogoIcon.setPadding(5, 0, 5, 10);
	          //      Bitmap logo_icon = BitmapFactory.decodeResource(getResources(), mBrand[Integer.parseInt(ItemBrand.get(listitem))]);
	             //    mLogoIcon.setImageBitmap(getCircleBitmap(logo_icon));
	                 mLogoIcon.setLayoutParams(frame_icon);  //line156
	                
	                 mLogoIcon.setOnClickListener(new View.OnClickListener() {
	                    @Override
	                    public void onClick(View v) {
	                        item_toast.setGravity(Gravity.CENTER,Integer.parseInt(itemLeftMargin.get(listitem)),Integer.parseInt(itemTopMargin.get(listitem)));
	                        item_toast.makeText(ParentActivity,
	                        String.valueOf(customerId.size()),Toast.LENGTH_SHORT).show();
	                                                        /*
	                                "Type:"+ Type[Integer.parseInt(ItemType.get(listitem))] +"\n"
	                                +"Style:"+ Style[Integer.parseInt(ItemStyle.get(listitem))]+"\n"
	                                +"Color:"+ Color[Integer.parseInt(ItemColor.get(listitem))]+"\n"
	                                +"Classification:" + Sex[Integer.parseInt(ItemSex.get(listitem))] */
	                       // addAd();
	                      //  reflashAdapter();
	                    }
	                });
	                 logo_layout.addView(mLogoIcon);
	            }
	            return convertView;//回傳給adapter
	        }
	    }
}
