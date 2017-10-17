package fr.limsi.negotiate.ToM.ProbalisticModel;

public class Combination {

//	//  Method to compute combination of k in the set of size n
//
//	

	
	
	public static int fact(int n) {
	      if (n <= 1)
	            return 1;
	      else
	            return n * fact(n - 1);
	}

	public static double combination(int k, int n){
		int total = fact(n);
		int sub = fact(k) * fact(n-k);
		return (total/sub);
	}

}
