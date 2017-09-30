package com.cj.edas.itemcenter;

public class ItemServiceImpl implements ItemService{
	Item i1 = new Item();
	Item i2 = new Item();

	@Override
	public Item getItemById( int id ) {
		// TODO Auto-generated method stub
		i1.setItemId( 1 );
		i1.setItemName( "clot" );
		i1.setItemPrice(520.2);
		i2.setItemId( 2 );
		i2.setItemName( "nike" );
		i2.setItemPrice(240.2);
		switch(id){
			case 1:i1.toString(); return i1;
			case 2:i2.toString(); return i2;
		}
		return null;
			
	}
	@Override
	public Item getItemByName( String name ) {
		// TODO Auto-generated method stub
		i1.setItemId( 1 );
		i1.setItemName( "clot" );
		i1.setItemPrice(520.2);
		return i1;
	}

	@Override
	public Item getItemByPrice(double price) {
		// TODO Auto-generated method stub
		i2.setItemId( 2 );
		i2.setItemName( "nike" );
		i2.setItemPrice(240.2);
		return i2;
	}
}
