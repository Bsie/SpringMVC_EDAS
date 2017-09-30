package com.cj.test.services;

public class SayHelloService {
	 public String sayHello(String user) {
	        return "Hello "+user+" ，Time is "+System.currentTimeMillis()+"(ms)";
	      }
}
