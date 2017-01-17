package fr.limsi.negotiate.restaurant;

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
		d1_cuisine.addPreference(Cuisine.MIXICAN, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.MIXICAN, Cuisine.FRENCH);
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Atmosphere> d1_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);
		d1_atmosphere.addPreference(Atmosphere.LIVELY, Atmosphere.QUIET);
		d1_atmosphere.addPreference(Atmosphere.LIVELY, Atmosphere.ROMANTIC);
		d1_atmosphere.addPreference(Atmosphere.ROMANTIC, Atmosphere.FAMILY);
		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d1_atmosphere);


		Self_Ci<Cost> d1_cost = new Self_Ci<Cost>(Cost.class);
		d1_cost.addPreference(Cost.EXPENSIVE, Cost.AFFRODABLE);
		d1_cost.addPreference(Cost.AFFRODABLE, Cost.CHEAP);
		CriterionNegotiation<Cost> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d1_criteria, Restaurant.class);
		return model1;

	}

	public   Negotiation<Restaurant>  inverseModel1(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Cuisine.class, Cost.class);
		d1_criteria.addPreference(Cost.class, Atmosphere.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.KOREAN);
		d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.JAPANESE);
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.FRENCH);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.MIXICAN);
		d1_cuisine.addPreference(Cuisine.FRENCH, Cuisine.ITALIAN);
		d1_cuisine.addPreference(Cuisine.TURKISH, Cuisine.MIXICAN);
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Atmosphere> d1_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);
		d1_atmosphere.addPreference(Atmosphere.FAMILY, Atmosphere.ROMANTIC);
		d1_atmosphere.addPreference(Atmosphere.QUIET, Atmosphere.LIVELY);
		d1_atmosphere.addPreference(Atmosphere.ROMANTIC, Atmosphere.LIVELY);
		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d1_atmosphere);


		Self_Ci<Cost> d1_cost = new Self_Ci<Cost>(Cost.class);
		d1_cost.addPreference(Cost.CHEAP, Cost.AFFRODABLE);
		d1_cost.addPreference(Cost.AFFRODABLE, Cost.EXPENSIVE);
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
		d1_cuisine.addPreference(Cuisine.KOREAN, Cuisine.MIXICAN);
		d1_cuisine.addPreference(Cuisine.MIXICAN, Cuisine.TURKISH);
		d1_cuisine.addPreference(Cuisine.JAPANESE, Cuisine.TURKISH);

		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Atmosphere> d2_atmosphere = new Self_Ci<Atmosphere>(Atmosphere.class);
		d2_atmosphere.addPreference(Atmosphere.QUIET, Atmosphere.LIVELY);
		d2_atmosphere.addPreference(Atmosphere.ROMANTIC, Atmosphere.LIVELY);
		d2_atmosphere.addPreference(Atmosphere.FAMILY, Atmosphere.ROMANTIC);
		CriterionNegotiation<Atmosphere> atmospher = new CriterionNegotiation<>(d2_atmosphere);


		Self_Ci<Cost> d2_cost = new Self_Ci<Cost>(Cost.class);
		d2_cost.addPreference(Cost.CHEAP, Cost.AFFRODABLE);
		d2_cost.addPreference(Cost.AFFRODABLE, Cost.EXPENSIVE);
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
				System.out.println(value.toString() + "  "+v.getSelf().satisfaction(value)+ " | is Satifiable: "+ v.getSelf().isSatisfiable(value));
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
				System.out.println(value+","+d +"," +c.getSelf().satisfaction(value)+","+ c.getOther().other(value) +","+ c.acceptability(value, m1.self()));
				c.getOther().getPreferences().clear();

			}
		}
		

	}
	
	public static void main (String[] args)  {
		
		GenerateModel m = new GenerateModel();
		Negotiation<Restaurant> m1 = m.model1();
		Negotiation<Restaurant> m2 = m.model2();
//		for(Restaurant r :Restaurant.values()){
//			System.out.println(r.toString() + ", "+ m1.satisfiability(r) + ", "+m2.satisfiability(r));
//		}

		
		//finalCalcul(m1);
//		
//		m1.setDominance(0.8);
//		m2.setDominance(0.4);
//		System.out.println(m1.self());
//		System.out.println(m2.self());
//		
//		printPreferences(m1);
//		System.out.println("--------------------------- m2 ------------------------------");
//		printPreferences(m2);
		//calculateElem(Cuisine.CHINESE, m1);

//		double thre = 0.6;
//		for(Cost elem: Cost.values()){
//			//System.out.println(" M1: " +elem.toString() +" "+ m1.getValueNegotiation(elem.getClass()).acceptability(elem, m1.self()));
//			//m2.getValueNegotiation(elem.getClass()).addInOther(elem, Satisfiable.TRUE);
//			boolean v = 	m2.getValueNegotiation(elem.getClass()).acceptability(elem, m2.self())>=thre ;
//			System.out.println(elem.toString() +" "+ v);
//		}
//		for(Cuisine elem: Cuisine.values()){
//			//m2.getValueNegotiation(elem.getClass()).addInOther(elem, Satisfiable.TRUE);
//			boolean v = 	m2.getValueNegotiation(elem.getClass()).acceptability(elem, m2.self())>= thre;
//			System.out.println(elem.toString() +" "+ v);
//		}
//		for(Atmosphere elem: Atmosphere.values()){
//			//m2.getValueNegotiation(elem.getClass()).addInOther(elem, Satisfiable.TRUE);
//			boolean v = 	m2.getValueNegotiation(elem.getClass()).acceptability(elem, m2.self())>= thre;
//			System.out.println(elem.toString() +" "+ v);
//		}
		//System.out.println(m.model1().getCriteria().sortValues());
	}

}
