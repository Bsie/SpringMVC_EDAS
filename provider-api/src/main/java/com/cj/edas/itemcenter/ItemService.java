package com.cj.edas.itemcenter;

public interface ItemService {
	//Item Interface
	public Item getItemById( int id );
	
	public Item getItemByName( String name );
	
	public Item getItemByPrice( double price );
}
