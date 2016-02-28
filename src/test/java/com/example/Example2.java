package com.example;

public class Example2 {

	private int id;
	private String name;
	
	public static void main(String[] args) {
		Example2 example2 = new Example2();
		System.out.println("Example2 ::"+example2);
		System.out.println(example2.hashCode());
		System.out.println(Integer.toHexString(example2.hashCode()));
		System.out.println(System.identityHashCode(example2));
	}
	
	
	
}
