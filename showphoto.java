
kindlistview.setOnItemClickListener(kindlistener);//類型ListView按鍵偵測

private ListView.OnItemClickListener kindlistener =new ListView.OnItemClickListener(){//類型按鍵偵測

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			if(selectState==0){
				RelativeLayout.LayoutParams clothParams = new RelativeLayout.LayoutParams(300,300);//新增Cloth物件之顯示資訊（寬,高）
				
				ImageView newview = new ImageView(CMSetting_Activity.this);//新增Image物件
				clothParams.leftMargin = (imageview.getWidth()/2)-150;//物件新增位置左右
				clothParams.topMargin = (imageview.getHeight()/2)-150;//物件新增位置上下
				newview.setLayoutParams(clothParams);//設定Image顯示資訊
			    newview.setImageDrawable((Drawable)parent.getItemAtPosition(position));//設定Image顯示圖片
			    newview.setId(viewId);//設定Image ID
			    
			    photoid.put(String.valueOf(viewId), String.valueOf(viewId));//將新增之Image ID新增至Map
			    photoParams.put(String.valueOf(viewId), clothParams);//將新增之Image顯示資訊 新增至Map
			    photoView.put(String.valueOf(viewId),newview);//將新增之Image物件新增至Map
			    photoKind.put(String.valueOf(viewId),String.valueOf(position));//將新增之Image分類新增至Map
			    photoSort.put(String.valueOf(viewId),adapter.getSort());//將新增之Image類型新增至Map
			    
			    parentview.addView(newview);//新增Image物件到畫面上
			    
			    RelativeLayout.LayoutParams colorParams = new RelativeLayout.LayoutParams(50,50);//新增Color物件之顯示資訊（寬,高）
			    colorParams.topMargin=clothParams.topMargin;//物件新增位置上下 與Cloth同高
			    colorParams.leftMargin=clothParams.leftMargin-50;//物件新增位置左右 在Cloth左邊
			    ImageView colorview = new ImageView(CMSetting_Activity.this);//新增Color物件
			    //colorview.setBackgroundColor(color[viewId%3]);//設定Color顏色
			    colorview.setLayoutParams(colorParams);//設定Color顯示資訊
			    parentview.addView(colorview);//新增Color到換面上
			    photoColor.put(String.valueOf(viewId),colorview);//將Colorview新增到Map
			    photoColorNum.put(String.valueOf(viewId),String.valueOf(0));//將color代號新增到Map
			    
			    RelativeLayout.LayoutParams brandParams = new RelativeLayout.LayoutParams(50,50);//新增Color物件之顯示資訊（寬,高）
			    brandParams.topMargin=clothParams.topMargin+60;//物件新增位置上下 與Cloth同高
			    brandParams.leftMargin=clothParams.leftMargin-50;//物件新增位置左右 在Cloth左邊
			    ImageView brandview = new ImageView(CMSetting_Activity.this);//新增Color物件
			    //colorview.setBackgroundColor(color[viewId%3]);//設定Color顏色
			    brandview.setLayoutParams(brandParams);//設定Color顯0示資訊
			    parentview.addView(brandview);//新增Color到換面上
			    photoBrand.put(String.valueOf(viewId),brandview);//將Colorview新增到Map
			    photoBrandNum.put(String.valueOf(viewId),String.valueOf(0));//將color代號新增到Map
			    
			    RelativeLayout.LayoutParams catalogParams = new RelativeLayout.LayoutParams(50,50);//新增Color物件之顯示資訊（寬,高）
			    catalogParams.topMargin=clothParams.topMargin+120;//物件新增位置上下 與Cloth同高
			    catalogParams.leftMargin=clothParams.leftMargin-50;//物件新增位置左右 在Cloth左邊
			    ImageView catalogview = new ImageView(CMSetting_Activity.this);//新增Color物件
			    //colorview.setBackgroundColor(color[viewId%3]);//設定Color顏色
			    catalogview.setLayoutParams(catalogParams);//設定Color顯示資訊
			    parentview.addView(catalogview);//新增Color到換面上
			    photoCatalog.put(String.valueOf(viewId),catalogview);//將Colorview新增到Map
			    photoCatalogNum.put(String.valueOf(viewId),String.valueOf(0));//將color代號新增到Map
			    
			    
			    
			    newview.setOnTouchListener(touchListener);//Image觸碰偵測	
			    newview.setOnDragListener(imgDragListener);
			    viewId++;//Image ID+1
			    
			    mainLayout.closeDrawer(rightListView);
			}else if(selectState==1){
				//view.startDrag(null, new DragShadowBuilder(view), view, 0);
				
			}
			
		}

		
	};
	

	
private Bitmap showPhoto(String uri,String type){
		   boolean needrotate=false;
		   Uri mPictureUri= Uri.parse(uri);; 
		   Bitmap bmp=null;	
			
	    if (type.equals("Camera") ) {  
	    	
	    	 
	    	Log.d("mylog", "camera");
	    	BitmapFactory.Options option=new BitmapFactory.Options();
	    	option.inJustDecodeBounds =true;
	    	BitmapFactory.decodeFile(mPictureUri.getPath(),option);
	    	int rotation=neededRotation(mPictureUri);
	    	int iw=option.outWidth;
	    	int ih=option.outHeight;
	    	int vw=imageview.getWidth();
	    	int vh=imageview.getHeight();
	    	int scale=0;
	    	if(rotation==0){
	    		needrotate=false;
	    		scale=Math.min(iw/vw, ih/vh);
	    		
	    	}else{
	    		needrotate=true;
	    		scale=Math.min(ih/vw, iw/vh);
	    	}
	    	option.inSampleSize=scale;
	    	option.inPurgeable=true;
	    	option.inJustDecodeBounds=false;
	    	bmp=BitmapFactory.decodeFile(mPictureUri.getPath(),option);
	    	if(needrotate){
	    		Matrix  matrix =new Matrix();
	    		matrix.postRotate(rotation);
	    		bmp=Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),matrix,true);
	    		
	    	}
	    	
	    	
	    }else if (type.equals("Gallery")) {  

	        
	  		String[] projection = { MediaColumns.DATA };
	  		Cursor cursor = managedQuery(mPictureUri, projection, null, null,
	  				null);
	  		int column_index = cursor.getColumnIndexOrThrow(MediaColumns.DATA);
	  		cursor.moveToFirst();

	  		String selectedImagePath = cursor.getString(column_index);
	  		photoPath=selectedImagePath;
	  		
	  		BitmapFactory.Options options = new BitmapFactory.Options();
	  		options.inJustDecodeBounds = true;
	  		BitmapFactory.decodeFile(selectedImagePath, options);
	  		int rotation =neededRotation(selectedImagePath);
	  		int iw=options.outWidth;
	    	int ih=options.outHeight;
	    	int vw=imageview.getWidth();
	    	int vh=imageview.getHeight();
	    	int scale=0;
	    	
	    	if(rotation==0){
	    		needrotate=false;
	    		scale=Math.min(iw/vw, ih/vh);
	    		
	    	}else{
	    		needrotate=true;
	    		scale=Math.min(ih/vw, iw/vh);
	    	}
	  		options.inSampleSize = scale;
	  		options.inJustDecodeBounds = false;
	  		bmp = BitmapFactory.decodeFile(selectedImagePath, options);
	  		if(needrotate){
	    		Matrix  matrix =new Matrix();
	    		matrix.postRotate(rotation);
	    		bmp=Bitmap.createBitmap(bmp,0,0,bmp.getWidth(),bmp.getHeight(),matrix,true);
	    		
	    	}
	    }  
	    
	    uploadBMP=bmp;
		return bmp;
		
	}

		public static int neededRotation(Uri ff)
    {
    try
        {
        ExifInterface exif = new ExifInterface(ff.getPath());
        int orientation = exif.getAttributeInt(
           ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            { return 270; }
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            { return 180; }
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
            { return 90; }
        return 0;

        } catch (FileNotFoundException e)
        {
        e.printStackTrace();
        } catch (IOException e)
        {
        e.printStackTrace();
        }
    return 0;
    }
	
	public static int neededRotation(String ff)
    {
    try
        {
        ExifInterface exif = new ExifInterface(ff);
        int orientation = exif.getAttributeInt(
           ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

        if (orientation == ExifInterface.ORIENTATION_ROTATE_270)
            { return 270; }
        if (orientation == ExifInterface.ORIENTATION_ROTATE_180)
            { return 180; }
        if (orientation == ExifInterface.ORIENTATION_ROTATE_90)
            { return 90; }
        return 0;

        } catch (FileNotFoundException e)
        {
        e.printStackTrace();
        } catch (IOException e)
        {
        e.printStackTrace();
        }
    return 0;
    }v