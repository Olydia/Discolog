package fr.limsi.negotiate.distance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.limsi.negotiate.*;

public class Distance {
	ArrayList<Preference<Criterion>> firstModel;
	ArrayList<Preference<Criterion>> secondModel;
	List<Criterion> values;

	
	public Distance (ArrayList<Preference<Criterion>> firstModel, ArrayList<Preference<Criterion>> secondModel, List<Criterion> value){
		this.firstModel = firstModel;
		this.secondModel = secondModel;
		this.values = value;
	}

	
	public List<PreferenceMatrix<Criterion>> generateExtension( ArrayList<Preference<Criterion>> model, List<Criterion> value){
		PreferenceMatrix<Criterion> matrix= new PreferenceMatrix<Criterion>(value, model);
		matrix.builtPreferences();
		Extension<Criterion> ext = new Extension<Criterion>(matrix);
		return ext.extensions();
	}
	
	/**
	 * calculate the kendall distance between two total ordered set of preferences
	 * @param firstModel model of preferences
	 * @param secondModel model of preferences
	 * @return kendall tau distance
	 */
	public double kendallTau(PreferenceMatrix<Criterion> firstModel, PreferenceMatrix<Criterion> secondModel){
		double discordant = 0;

		//System.out.println("model 1" + firstModel.getSelfPreferences());
//		
		//System.out.println("model 2" + secondModel.getSelfPreferences());

		for(int i = 0; i<values.size(); i++){
			for(int j = i; j<values.size(); j++){
				if(i!=j){
					if(firstModel.getPreferences()[i][j] != secondModel.getPreferences()[i][j])
						discordant ++;
				}
			}
		}

		//double result =(concordant - discordant)/ normalize;
		System.out.println(discordant);
		return  discordant;
	}

	/**
	 * 
	 * Compute the kendall distance between two partial ordered set of preferences
	 * @return Knn distance
	 */
	public void Knn(){
		double min = 0;

		List<PreferenceMatrix<Criterion>> first = generateExtension(firstModel, values);
		List<PreferenceMatrix<Criterion>> second = generateExtension(secondModel, values);

		for(PreferenceMatrix<Criterion> firstElem : first){
			for(PreferenceMatrix<Criterion> secondElem : second){
				double kendal = kendallTau(firstElem, secondElem);
				if(kendal >= min)
					min = kendal;	
			}
		}
		
	}
	
	public double K_H(List<PreferenceMatrix<Criterion>> first, List<PreferenceMatrix<Criterion>> second){
		
		List<Double> maximum = new ArrayList<Double>();
		
		for(PreferenceMatrix<Criterion> firstElem : first){
			double min = firstElem.getSelfPreferences().size();
			for(PreferenceMatrix<Criterion> secondElem : second){
				double kendal = kendallTau(firstElem, secondElem);
				if(kendal <= min)
					min = kendal;	
			}
			maximum.add(min);
		}
		
		System.out.println("first model" + maximum);

		Collections.sort(maximum);
		Collections.reverse(maximum);

		return maximum.get(0);

	}
	
	/**
	 * Compute the Hausdorff distance
	 * @return
	 */
	public double distance (){
		List<PreferenceMatrix<Criterion>> first = generateExtension(firstModel, values);
		List<PreferenceMatrix<Criterion>> second = generateExtension(secondModel, values);
		
		double K1 = K_H(first, second);
		double K2 = K_H(second, first);
		return Math.max(K1, K2);

		
	}

}
