package com.ntust.cmapp;

import java.util.HashMap;
import java.util.Map;

public class Customer {
	public String customerId,itemBrand;
	public int itemWidth,itemHeight,itemTopMargin,itemLeftMargin
	,itemType,itemColor,itemSex;
	
	public Customer(String customerId,int itemWidth,int itemHeight,int itemTopMargin,int itemLeftMargin,
			       String itemBrand,int itemType,int itemColor,int itemSex  ){
		   this.customerId = customerId;
		   this.itemWidth=itemWidth;
		   this.itemHeight=itemHeight;
		   this.itemTopMargin=itemTopMargin;
		   this.itemLeftMargin=itemLeftMargin;
		   this.itemBrand=itemBrand;
		   this.itemType=itemType;
		   this.itemColor=itemColor;
		   this.itemSex=itemSex;
	}
	
}

