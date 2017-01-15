package fr.limsi.negotiate.distance;

import java.util.*;

import fr.limsi.negotiate.Preference;


public class BinaryTree<T> {
	List<List<Preference<T>>>  result = new ArrayList<List<Preference<T>>>();
//	public static List<Integer> extension (List<Integer> input, List<Integer> previous){
//		List<Integer> ext = new ArrayList<Integer>();
//		
//		if(input.isEmpty())
//			return ext;
//		
//		
//		ext.addAll(previous);
//		ext.add(input.get(input.size()-1));
//		previous = new ArrayList<Integer>(ext);
//		ext.addAll(extension(input.subList(0,input.size()-1), previous));
//		
//		ext.add(-input.get(input.size()-1));
//		ext.addAll(extension(input.subList(0, input.size()-1), previous));
//		
//		return ext;
//		
//	}
	
	public void extension (List<Preference<T>> input, int cmp){
		
		
		if (cmp >= input.size()){
			if (!result.contains(input))
				result.add(input);
		}
		else {
			Preference<T> current_value= input.get(cmp);
			
			List<Preference<T>> possible_value = new ArrayList<Preference<T>>();
			
			possible_value.add(current_value);
			possible_value.add(new Preference<T>(current_value.getMore(), current_value.getLess()));
			
			for (Preference<T> val : possible_value){
				List<Preference<T>> tmp = new ArrayList<>();
				for(Preference<T> a : input){
					tmp.add(a);
				}
				tmp.set(cmp, val);
				extension(tmp, cmp+1);
				
			}
		}
		
	}
	public static void main(String[] args) {
		List<Integer> temp = new ArrayList<Integer>();
		temp.add(1);
		temp.add(2);
		temp.add(3);
		temp.add(4);
		temp.add(5);

		BinaryTree binaryTree = new BinaryTree();
		binaryTree.extension(temp, 0);
		System.out.println(binaryTree.result);
	}

			
}
