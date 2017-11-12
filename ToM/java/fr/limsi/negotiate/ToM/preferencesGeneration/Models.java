package fr.limsi.negotiate.ToM.preferencesGeneration;

import java.util.*;
import java.util.stream.Collectors;

import fr.limsi.negotiate.*;
import fr.limsi.negotiate.distance.PreferenceDistance;
import fr.limsi.negotiate.restaurant.Restaurant;

public class Models<O extends Option> {
	final double DISTANCE = 0.8;
	final int MAX_MODELS = 15;
	
	private List<Tuple<List<Self_Ci<Criterion>>, List<Self_Ci<Criterion>>>> finalList;
	public Models() {
		// TODO Auto-generated constructor stub
		setFinalList(new ArrayList<Tuple<List<Self_Ci<Criterion>>, List<Self_Ci<Criterion>>>> ());

	}

	/**
	 * 
	 * @param elements : list of values needed to generate preferences
	 * @return List<Self_Ci<Criterion>> List of possible preferences generated from elements. factor of elements
	 */
	public List<Self_Ci<Criterion>> createValuesModel(List<Criterion> elements){
		ValuePreferences modelC = new ValuePreferences(elements);

		return modelC.createPreferences();
	}

	public List<Self_C<O>> createCriterionModel(List<Class<? extends Criterion>> elements, Class<O> type){
		CriterionPreferences<O> model = new CriterionPreferences<O>(elements);

		return model.createPreferences(type);

	}


	/**
	 * 
	 * @param currentIndex: index for the recursive call
	 * @param preferences : List of all possible preferences
	 * @return the combination of all the possible set of preferences for a given topic
	 */
	public List<List<Self_Ci<Criterion>>> getCombination(int currentIndex,  List<List<Self_Ci<Criterion>>> preferences) {
	    
		if (currentIndex == preferences.size()) {
	        // Skip the items for the last container
	        List<List<Self_Ci<Criterion>>> combinations = new ArrayList<List<Self_Ci<Criterion>>>();
	        combinations.add(new ArrayList<Self_Ci<Criterion>>());
	        return combinations;
	    }
		
	    List<List<Self_Ci<Criterion>>> combinations = new ArrayList<List<Self_Ci<Criterion>>>();
	    List<Self_Ci<Criterion>> container = preferences.get(currentIndex);
	    List<Self_Ci<Criterion>> containerItemList = container;
	    
	    // Get combination from next index
	    List<List<Self_Ci<Criterion>>> suffixList = getCombination(currentIndex + 1, preferences);
	    int size = containerItemList.size();
	    
	    for (int ii = 0; ii < size; ii++) {
	    	Self_Ci<Criterion> containerItem = containerItemList.get(ii);
	    	
	        if (suffixList != null) {
	        	
	            for (List<Self_Ci<Criterion>> suffix : suffixList) {
	                List<Self_Ci<Criterion>> nextCombination = new ArrayList<Self_Ci<Criterion>>();
	                nextCombination.add(containerItem);
	                nextCombination.addAll(suffix);
	                if (currentIndex == 3) {
						System.out.println(nextCombination.toString());
						List<Tuple<List<Self_Ci<Criterion>>, List<Self_Ci<Criterion>>>> res = getAllCoupleOfModels(nextCombination, combinations);
						finalList.addAll(res);
						System.out.println("finalList size: "+finalList.size());
						if (finalList.size() >= MAX_MODELS)
							System.out.println("");
					}
					combinations.add(nextCombination);
					System.out.println("");
				}

			}
	    }
		if (currentIndex == 3) {
			System.out.println("");
		}
		return combinations;
	}

	public void getCombinationAmine(List<List<Self_Ci<Criterion>>> preferences) {

		List<List<Self_Ci<Criterion>>> visitedCombination= new ArrayList<>();

		while (finalList.size() < MAX_MODELS) {
			List<Self_Ci<Criterion>> current = new ArrayList<>();
			for (List<Self_Ci<Criterion>> preference : preferences) {
				int random = new Random().nextInt(preference.size() - 1);
				current.add(preference.get(random));
			}

			if (visitedCombination.isEmpty()) visitedCombination.add(current);
			else if (!containsCurrent(visitedCombination, current)) {
				finalList.addAll(getAllCoupleOfModels(current, visitedCombination));
				visitedCombination.add(current);
			}
		}
	}

	public boolean containsCurrent(List<List<Self_Ci<Criterion>>> visitedCombination, List<Self_Ci<Criterion>> current){
		boolean ok = false;
		int i =0;
		while (!ok && i< visitedCombination.size()){
			if (visitedCombination.get(i).equals(current))
				ok = true;
			i++;
		}
		return ok;
	}


	public List<Models<O>.Tuple<List<Self_Ci<Criterion>>, List<Self_Ci<Criterion>>>> getAllCoupleOfModels(
			List<Self_Ci<Criterion>> nextCombination, List<List<Self_Ci<Criterion>>> combinations) {
		// TODO Auto-generated method stub
		return combinations.stream().
				filter(x -> new PreferenceDistance(x, nextCombination).distance() >= DISTANCE).
				map(x -> new Tuple<List<Self_Ci<Criterion>>, List<Self_Ci<Criterion>>>
						(x, nextCombination)).
				collect(Collectors.toList());
		
	}

	public List<PrefNegotiation<O>> computeModels(List<Class<? extends Criterion>> criteria,
			Class<O> class1){
		
		List<PrefNegotiation<O>> finalModel = new ArrayList<PrefNegotiation<O>>();
		List<List<Self_Ci<Criterion>>> models = new ArrayList<List<Self_Ci<Criterion>>>();
		for( Class<? extends Criterion> e: criteria) {

			models.add(createValuesModel(Arrays.asList(e.getEnumConstants())));
		}

		List<List<Self_Ci<Criterion>>> valuesModel = getCombination(0, models);

		for(List<Self_Ci<Criterion>> value: valuesModel){
			// <-- Ignore the crireria in the preference model -->	finalModel.add(new PrefNegotiation<O>(value, self));
			Self_C<O> self = new Self_C<O> (class1);
			finalModel.add(new PrefNegotiation<O>(value, self));
		}
		// <-- Ignore the crireria in the preference model --> }
		return finalModel;


	}
	
	public void generateSelfs(List<Class<? extends Criterion>> criteria,
			Class<O> class1){
		
		List<List<Self_Ci<Criterion>>> models = new ArrayList<List<Self_Ci<Criterion>>>();
		for( Class<? extends Criterion> e: criteria) {
			List<Self_Ci<Criterion>> crt = createValuesModel(Arrays.asList(e.getEnumConstants()));
			String kaka = crt.toString();
			models.add(crt);
		}

		getCombinationAmine(models);

		System.out.println("zzz");

	}
	
	public Negotiation<? extends Option> createNegotiation(List<Self_Ci<Criterion>>preferences, 
				Class<?extends Option> topic){
		
		Self_C<? extends Option>  criteria = 
				new Self_C (topic);
		
		List<CriterionNegotiation<Criterion>> valueNegotiation = 
					new ArrayList<CriterionNegotiation<Criterion>> ();

		for(Self_Ci<Criterion> pref: preferences){
			valueNegotiation.add(new CriterionNegotiation<>(pref));
		}
		
		Negotiation<? extends Option> nego = new Negotiation
		(valueNegotiation, criteria, topic); // = new Negotiation<>(negotiation)
		
		return nego;
	}

	public static void main (String[] args) {
		
		Models<Restaurant> m = new Models<Restaurant>();
		long startTime = System.currentTimeMillis();

		m.generateSelfs(Restaurant.A_CITADELLA.getCriteria(), Restaurant.class);
		long endTime = System.currentTimeMillis();
		System.out.println("That took " + (endTime - startTime) + " milliseconds");
		
		for (Models<Restaurant>.Tuple<List<Self_Ci<Criterion>>,
				List<Self_Ci<Criterion>> > e:m.getFinalList()){
			
			m.createNegotiation(e.b, Restaurant.class);
			System.out.println( new PreferenceDistance(e.a, e.b).distance());
		}
//		System.out.println("size" 
//				
//				+ m.finalList.size());


	}

	/**
	 * Combines several collections of elements and create permutations of all of them, taking one element from each
	 * collection, and keeping the same order in resultant lists as the one in original list of collections.
	 *
	 * <ul>Example
	 * <li>Input  = { {a,b,c} , {1,2,3,4} }</li>
	 * <li>Output = { {a,1} , {a,2} , {a,3} , {a,4} , {b,1} , {b,2} , {b,3} , {b,4} , {c,1} , {c,2} , {c,3} , {c,4} }</li>
	 * </ul>
	 *
	 * @param collections Original list of collections which elements have to be combined.
	 * @return Resultant collection of lists with all permutations of original list.
	 */
	public static <T> Collection<List<T>> permutations(List<List<T>> collections) {
		if (collections == null || collections.isEmpty()) {
			return Collections.emptyList();
		} else {
			Collection<List<T>> res = new LinkedList<>();
			permutationsImpl(collections, res, 0, new LinkedList<T>());
			return res;
		}
	}

	/** Recursive implementation for {@link #(List, Collection)} */
	private static <T> void permutationsImpl(List<List<T>> ori, Collection<List<T>> res, int d, List<T> current) {
		// if depth equals number of original collections, final reached, add and return
		if (d == ori.size()) {
			res.add(current);
			return;
		}

		// iterate from current collection and copy 'current' element N times, one for each element
		Collection<T> currentCollection = ori.get(d);
		for (T element : currentCollection) {
			List<T> copy = new LinkedList<T>(current);
			copy.add(element);
			permutationsImpl(ori, res, d + 1, copy);
		}
	}


	public List<Tuple<List<Self_Ci<Criterion>>, List<Self_Ci<Criterion>>>> getFinalList() {
		return finalList;
	}

	public void setFinalList(List<Tuple<List<Self_Ci<Criterion>>, List<Self_Ci<Criterion>>>> finalList) {
		this.finalList = finalList;
	}




	public class Tuple<A, B> {

		public  A a;
		public  B b;

		public Tuple(A a, B b) {
			this.a = a;
			this.b = b;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Tuple<?, ?> tuple = (Tuple<?, ?>) o;
			if (!a.equals(tuple.a)) return false;
			return b.equals(tuple.b);
		}

		@Override
		public int hashCode() {
			int result = a.hashCode();
			result = 31 * result + b.hashCode();
			return result;
		}
	}
}