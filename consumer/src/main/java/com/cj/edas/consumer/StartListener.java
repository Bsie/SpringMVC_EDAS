package com.cj.edas.consumer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cj.edas.itemcenter.ItemService;

public class StartListener implements ServletContextListener{

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		ApplicationContext ap = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
		final ItemService itemService = (ItemService) ap.getBean("item");
		Thread thread = new Thread( new Runnable() {
			@Override
			public void run() {
				while ( true ) {
					try {
						Thread.sleep( 500l );
						System.out.println( itemService.getItemById( 2 ) );
						System.out.println( itemService.getItemByName( "myname" ) );
						System.out.println( itemService.getItemByPrice( 200 ) );
					} catch ( Throwable e ) {
						e.printStackTrace();
					}
				}
			}
		} );
		thread.start();
	}

}
