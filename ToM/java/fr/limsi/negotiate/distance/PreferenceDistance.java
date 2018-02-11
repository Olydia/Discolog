package fr.limsi.negotiate.distance;
import  fr.limsi.negotiate.*;
import fr.limsi.negotiate.restaurant.totalOrderedModels;

import java.util.List;

public class PreferenceDistance {
	private List<Self_Ci<Criterion>> model1;
	private  List<Self_Ci<Criterion>>  model2;

	public PreferenceDistance( List<Self_Ci<Criterion>> model, List<Self_Ci<Criterion>> model2) {
		// TODO : exception if type of model1 1 different from mode 2
		this.model1 =model;
		this.model2=model2;
	}

	public int diffMostPrf (){
		int nb = 0;
		for(Self_Ci<Criterion> cr1: model1){

			Self_Ci<Criterion> cr2 = this.getCriterionModel(cr1.getType(), model2);
			
			if(!cr1.mostPreffered().equals(cr2.mostPreffered()))
				nb ++;
			
		}
		//Distance criteria = new Distance(model1.getCriteria().getPreferences(),model2.getCriteria().getPreferences(),model1.getCriteria().getElements());


		return nb;

	}

	public double distance (){
		double sum = 0;
		int cmp =0;
		for(Self_Ci<Criterion> cr1: model1){

			Self_Ci<Criterion> cr2 = this.getCriterionModel(cr1.getType(), model2);

			Distance current = new Distance (cr1.getSelfPreferences(), cr2.getSelfPreferences(), cr1.getElements());
			sum+= current.distance();
			cmp ++;
		}
		//Distance criteria = new Distance(model1.getCriteria().getPreferences(),model2.getCriteria().getPreferences(),model1.getCriteria().getElements());


		return sum/cmp ;

	}

	public Self_Ci<Criterion> getCriterionModel(Class<? extends Criterion> c, List<Self_Ci<Criterion>> model){
		for(Self_Ci<Criterion> m : model){
			if(m.getType().equals(c))
				return m;
		}
		return  null;
	}

	/*public static void main(String[] args) {

		totalOrderedModels tm = new totalOrderedModels();



//		System.out.println( "1, 2: " + distance12.distance());
//		System.out.println( "1, 3: " + distance13.distance());
//		System.out.println("1, 4: " + distance14.distance());
//		
//		System.out.println( "2, 3: " +distance23.distance());
//		System.out.println("2, 4: " +distance24.distance());
//		
//		System.out.println("3, 4: " +distance34.distance());


	}*/

}
