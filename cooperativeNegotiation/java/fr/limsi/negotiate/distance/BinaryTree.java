package fr.limsi.negotiate.distance;

import java.util.*;

public class BinaryTree {

	public static List<Integer> extension (List<Integer> input, List<Integer> previous){
		List<Integer> ext = new ArrayList<Integer>();
		
		if(input.isEmpty())
			return ext;
		
		
		ext.addAll(previous);
		ext.add(input.get(input.size()-1));
		previous = new ArrayList<Integer>(ext);
		ext.addAll(extension(input.subList(0,input.size()-1), previous));
		
		ext.add(-input.get(input.size()-1));
		ext.addAll(extension(input.subList(0, input.size()-1), previous));
		
		return ext;
		
	}
	public static void main(String[] args) {
		List<Integer> temp = new ArrayList<Integer>(Arrays.asList(1, 2,3));
		List<Integer> ext = new ArrayList<Integer>();
		System.out.println(extension(temp, ext));
	}

			
}
