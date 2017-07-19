package fr.limsi.negotiate.restaurant;

import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Statement.Satisfiable;

public class GenerateModel {
	
	public   Negotiation<Restaurant>  emptyModel(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Atmosphere.class, Cost.class);
		d1_criteria.addPreference(Cost.class, Cuisine.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);

		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Atmosphere> d1_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);

		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d1_atmosphere);


		Self_Ci<Cost> d1_cost = new Self_Ci<Cost>(Cost.class);

		CriterionNegotiation<Cost> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d1_criteria, Restaurant.class);
		return model1;

	}

	
	public   Negotiation<Restaurant>  model1(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Atmosphere.class, Cost.class);
		d1_criteria.addPreference(Cost.class, Cuisine.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.CHINESE);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.CHINESE);
		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.KOREAN);
		d1_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.TURKISH, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.MEXICAN, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.MEXICAN, Cuisine.FRENCH);
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Atmosphere> d1_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);
		d1_atmosphere.addPreference(Atmosphere.LIVELY, Atmosphere.QUIET);
		d1_atmosphere.addPreference(Atmosphere.LIVELY, Atmosphere.ROMANTIC);
		d1_atmosphere.addPreference(Atmosphere.ROMANTIC, Atmosphere.FAMILY);
		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d1_atmosphere);


		Self_Ci<Cost> d1_cost = new Self_Ci<Cost>(Cost.class);
		d1_cost.addPreference(Cost.EXPENSIVE, Cost.AFFORDABLE);
		d1_cost.addPreference(Cost.AFFORDABLE, Cost.CHEAP);
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d1_criteria, Restaurant.class);
		return model1;

	}

	public   Negotiation<Restaurant>  model3(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Cuisine.class, Cost.class);
		d1_criteria.addPreference(Cost.class, Atmosphere.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.KOREAN);
		d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.MEXICAN);
		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.ITALIAN);
		d1_cuisine.addPreference(Cuisine.TURKISH, Cuisine.MEXICAN);
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Atmosphere> d1_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);
		d1_atmosphere.addPreference(Atmosphere.FAMILY, Atmosphere.ROMANTIC);
		d1_atmosphere.addPreference(Atmosphere.QUIET, Atmosphere.LIVELY);
		d1_atmosphere.addPreference(Atmosphere.ROMANTIC, Atmosphere.LIVELY);
		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d1_atmosphere);


		Self_Ci<Cost> d1_cost = new Self_Ci<Cost>(Cost.class);
		d1_cost.addPreference(Cost.CHEAP, Cost.AFFORDABLE);
		d1_cost.addPreference(Cost.AFFORDABLE, Cost.EXPENSIVE);
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d1_criteria, Restaurant.class);
		return model1;

	}
	
	
	public   Negotiation<Restaurant>  model2(){


		Self_C<Restaurant>  d2_criteria = new Self_C<Restaurant> (Restaurant.class);
		d2_criteria.addPreference(Cost.class, Cuisine.class);
		d2_criteria.addPreference(Cuisine.class,Atmosphere.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.setType(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.ITALIAN, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.ITALIAN);
		//d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.TURKISH));
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.MEXICAN);
		d1_cuisine.addPreference(Cuisine.MEXICAN, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.TURKISH);

		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Atmosphere> d2_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);
		d2_atmosphere.addPreference(Atmosphere.QUIET, Atmosphere.LIVELY);
		d2_atmosphere.addPreference(Atmosphere.ROMANTIC, Atmosphere.LIVELY);
		d2_atmosphere.addPreference(Atmosphere.FAMILY, Atmosphere.ROMANTIC);
		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d2_atmosphere);


		Self_Ci<Cost> d2_cost = new Self_Ci<Cost>(Cost.class);
		d2_cost.addPreference(Cost.CHEAP, Cost.AFFORDABLE);
		d2_cost.addPreference(Cost.AFFORDABLE, Cost.EXPENSIVE);
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<>(d2_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model2 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d2_criteria, Restaurant.class);
		return model2;

	}
	
	
	public static void printPreferences(Negotiation<Restaurant> m){
		for(CriterionNegotiation<Criterion> v: m.getValuesNegotiation()){
			System.out.println("\t *********** " + v.getType().getSimpleName()+ " *********** ");
			for(Criterion value: v.getElements())	
				System.out.println(value.toString() + "  "+v.getSelf().satisfaction(value)+ " | is Satifiable: "+ v.getSelf().isSatisfiable(value, m.getDominance()));
		}
	}

	public static void finalCalcul(Negotiation<Restaurant> m){
		for(CriterionNegotiation<Criterion> v: m.getValuesNegotiation()){
			for(Criterion value: v.getElements())
				calculateElem(value, m);
		}
	}
	
	public static void calculateElem(Criterion value, Negotiation<Restaurant> m1){
		Satisfiable[]other ={Satisfiable.FALSE, Satisfiable.UNKOWN, Satisfiable.TRUE}; 
		double[]dt ={0.8, 0.5, 0.4, 0.2}; 
		CriterionNegotiation<Criterion> c = m1.getValueNegotiation(value.getClass());
		for(double d : dt){
			m1.setDominance(d);
			for(Satisfiable o: other){
				c.getOther().addPreference(value,o);
				System.out.println(value+","+d +"," +c.getSelf().satisfaction(value)+","+ c.getOther().other(value) +","+ c.tolerable(value, m1.self()));
				c.getOther().getPreferences().clear();

			}
		}
		

	}
	
	public static void main (String[] args)  {
		
		totalOrderedModels m = new totalOrderedModels();
		Negotiation<Restaurant> m1 = m.model1();
		Negotiation<Restaurant> m3 = m.model3();
		ArrayList<Criterion> values = new ArrayList<Criterion>();
		values.add(Cuisine.CHINESE); values.add(Atmosphere.FAMILY);
		values.add(Cost.CHEAP);
		List<Option> p= m3.getOptionWithValues(values);
		for(Option o : p)
		System.out.println(o.print());
//		m1.setDominance(0.7);
//		m3.setDominance(0.4);
//		for(int t =0; t<8; t++){
//			System.out.println(m1.selfTest(t));
	//	}
//		m1.addStatement(new Statement<Criterion>(Atmosphere.QUIET, Satisfiable.TRUE), true);
//		m1.addStatement(new Statement<Criterion>(Cuisine.CHINESE, Satisfiable.TRUE), true);
//		System.out.println(m1.getValueNegotiation(Cuisine.class).ask());


		//test Ask 
		
	}

}
