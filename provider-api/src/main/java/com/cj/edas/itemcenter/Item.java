package com.cj.edas.itemcenter;

public class Item {

	private int itemId;
	private String itemName;
	private double itemPrice;

	public int getItemId() {
		return itemId;
	}

	public void setItemId( int itemId ) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName( String itemName ) {
		this.itemName = itemName;
	}
	
 
	public double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(double d) {
		this.itemPrice = d;
	}

	@Override
	public String toString() {
		return "Item[id: " + itemId + ", name: " + itemName + ",price:"+itemPrice+ "!]";
	}

}
