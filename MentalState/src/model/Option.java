package model;

import java.util.ArrayList;

public abstract class Option {
	
	String name;

	public Option(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return name;
	}
	
	public static ArrayList criteria ; 
	
	public  abstract  ArrayList<Criterion> getCriteria();
	
	
}
