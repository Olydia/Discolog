package fr.limsi.negotiate.restaurant.FR;

import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.Statement.Satisfiable;

public class GenerateModel {

	public   Negotiation<Restaurant>  emptyModel(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Ambiance.class, Prix.class);
		d1_criteria.addPreference(Prix.class, Cuisine.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);

		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Ambiance> d1_atmosphere = new Self_Ci<Ambiance>(Ambiance.class);

		CriterionNegotiation<Ambiance> atmospher = new CriterionNegotiation<>(d1_atmosphere);


		Self_Ci<Prix> d1_cost = new Self_Ci<Prix>(Prix.class);

		CriterionNegotiation<Prix> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d1_criteria, Restaurant.class);
		return model1;

	}


	public   Negotiation<Restaurant>  model1(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Ambiance.class, Prix.class);
		d1_criteria.addPreference(Prix.class, Cuisine.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.COREEN, Cuisine.CHINOIS);
		d1_cuisine.addPreference(Cuisine.JAPONAIS, Cuisine.CHINOIS);
		d1_cuisine.addPreference(Cuisine.FRANÇAIS, Cuisine.COREEN);
		d1_cuisine.addPreference(Cuisine.ITALIEN, Cuisine.FRANÇAIS);
		d1_cuisine.addPreference(Cuisine.TURC, Cuisine.JAPONAIS);
		d1_cuisine.addPreference(Cuisine.MEXICAIN, Cuisine.TURC);
		d1_cuisine.addPreference(Cuisine.MEXICAIN, Cuisine.FRANÇAIS);
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Ambiance> d1_atmosphere = new Self_Ci<Ambiance>(Ambiance.class);
		d1_atmosphere.addPreference(Ambiance.ANIME, Ambiance.CALME);
		d1_atmosphere.addPreference(Ambiance.ANIME, Ambiance.ROMANTIQUE);
		d1_atmosphere.addPreference(Ambiance.ROMANTIQUE, Ambiance.FAMILIAL);
		CriterionNegotiation<Ambiance> atmospher = new CriterionNegotiation<>(d1_atmosphere);


		Self_Ci<Prix> d1_cost = new Self_Ci<Prix>(Prix.class);
		d1_cost.addPreference(Prix.CHIC, Prix.ABORDABLE);
		d1_cost.addPreference(Prix.ABORDABLE, Prix.BAS_PRIX);
		CriterionNegotiation<Prix> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d1_criteria, Restaurant.class);
		return model1;

	}

	public   Negotiation<Restaurant>  model3(){


		Self_C<Restaurant>  d1_criteria = new Self_C<Restaurant> (Restaurant.class);
		d1_criteria.addPreference(Cuisine.class, Prix.class);
		d1_criteria.addPreference(Prix.class, Ambiance.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.CHINOIS, Cuisine.COREEN);
		d1_cuisine.addPreference(Cuisine.CHINOIS, Cuisine.JAPONAIS);
		d1_cuisine.addPreference(Cuisine.COREEN, Cuisine.FRANÇAIS);
		d1_cuisine.addPreference(Cuisine.JAPONAIS, Cuisine.TURC);
		d1_cuisine.addPreference(Cuisine.FRANÇAIS, Cuisine.MEXICAIN);
		d1_cuisine.addPreference(Cuisine.FRANÇAIS, Cuisine.ITALIEN);
		d1_cuisine.addPreference(Cuisine.TURC, Cuisine.MEXICAIN);
		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Ambiance> d1_atmosphere = new Self_Ci<Ambiance>(Ambiance.class);
		d1_atmosphere.addPreference(Ambiance.FAMILIAL, Ambiance.ROMANTIQUE);
		d1_atmosphere.addPreference(Ambiance.CALME, Ambiance.ANIME);
		d1_atmosphere.addPreference(Ambiance.ROMANTIQUE, Ambiance.ANIME);
		CriterionNegotiation<Ambiance> atmospher = new CriterionNegotiation<>(d1_atmosphere);


		Self_Ci<Prix> d1_cost = new Self_Ci<Prix>(Prix.class);
		d1_cost.addPreference(Prix.BAS_PRIX, Prix.ABORDABLE);
		d1_cost.addPreference(Prix.ABORDABLE, Prix.CHIC);
		CriterionNegotiation<Prix> cost = new CriterionNegotiation<>(d1_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model1 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d1_criteria, Restaurant.class);
		return model1;

	}


	public   Negotiation<Restaurant>  model2(){


		Self_C<Restaurant>  d2_criteria = new Self_C<Restaurant> (Restaurant.class);
		d2_criteria.addPreference(Prix.class, Cuisine.class);
		d2_criteria.addPreference(Cuisine.class,Ambiance.class);

		Self_Ci<Cuisine> d1_cuisine = new Self_Ci <Cuisine>(Cuisine.class);
		d1_cuisine.setType(Cuisine.class);
		d1_cuisine.addPreference(Cuisine.ITALIEN, Cuisine.FRANÇAIS);
		d1_cuisine.addPreference(Cuisine.JAPONAIS, Cuisine.FRANÇAIS);
		d1_cuisine.addPreference(Cuisine.CHINOIS, Cuisine.JAPONAIS);
		d1_cuisine.addPreference(Cuisine.COREEN, Cuisine.ITALIEN);
		//d1_cuisine.addPreference(Cuisine.CHINESE, Cuisine.TURKISH));
		d1_cuisine.addPreference(Cuisine.COREEN, Cuisine.MEXICAIN);
		d1_cuisine.addPreference(Cuisine.MEXICAIN, Cuisine.TURC);
		d1_cuisine.addPreference(Cuisine.JAPONAIS, Cuisine.TURC);

		CriterionNegotiation<Cuisine> cuisine = new CriterionNegotiation<>(d1_cuisine);

		Self_Ci<Ambiance> d2_atmosphere = new Self_Ci<Ambiance>(Ambiance.class);
		d2_atmosphere.addPreference(Ambiance.CALME, Ambiance.ANIME);
		d2_atmosphere.addPreference(Ambiance.ROMANTIQUE, Ambiance.ANIME);
		d2_atmosphere.addPreference(Ambiance.FAMILIAL, Ambiance.ROMANTIQUE);
		CriterionNegotiation<Ambiance> atmospher = new CriterionNegotiation<>(d2_atmosphere);


		Self_Ci<Prix> d2_cost = new Self_Ci<Prix>(Prix.class);
		d2_cost.addPreference(Prix.BAS_PRIX, Prix.ABORDABLE);
		d2_cost.addPreference(Prix.ABORDABLE, Prix.CHIC);
		CriterionNegotiation<Prix> cost = new CriterionNegotiation<>(d2_cost);

		@SuppressWarnings("unchecked")
		Negotiation<Restaurant> model2 = new Negotiation<Restaurant> 
		(new CriterionNegotiation[] {cost, cuisine, atmospher}, d2_criteria, Restaurant.class);
		return model2;

	}


	public static void printPreferences(Negotiation<Restaurant> m){
		for(CriterionNegotiation<Criterion> v: m.getValuesNegotiation()){
			System.out.println("\t *********** " + v.getType().getSimpleName()+ " *********** ");
			for(Criterion value: v.getElements())	
				System.out.println(value.toString() + "  "+v.getSelf().satisfaction(value)+
						" | is Satifiable: "+ v.getSelf().isSatisfiable(value, m.getDominance()));
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
				System.out.println(value+","+d +"," +c.getSelf().satisfaction(value)+","+ 
						c.getOther().other(value) +","+ c.tolerable(value, m1.self()));
				c.getOther().getPreferences().clear();

			}
		}


	}

	public static void main (String[] args)  {
		Cuisine[] c = Cuisine.values();
		
		System.out.println("");		
		for(Cuisine elem : c)
			System.out.print(elem + ", ");
		//		totalOrderedModels m = new totalOrderedModels();
		//		Negotiation<Restaurant> m1 = m.model1();
		//		System.out.println(m1.getValueNegotiation(Atmosphere.class).getSelf().getSelfPreferences());
		//		for(Cuisine e: Cuisine.values()){
		//			System.out.println( e + " " +m1.getValueNegotiation(e.getClass()).getSelf().satisfaction(e));
		//		}
		//		
	}

}
