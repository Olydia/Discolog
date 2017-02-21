package fr.limsi.negotiate.restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatOptions {
	ArrayList<String> italian = new ArrayList<>(Arrays.asList("A_Citadella","Andiamo_Pizzeria","Angolo_L'italia","Anteprima","Arcadius_Ristorante","Arlecchino","Au_Soleil_Italien","Barocco","Bel'italie","Bella_Italia","Bella_Napoli","Blue_Pasta","Buona_Pasta","Caffe_d'italia","Caffe_Di_Pasta","Capri_Pizza","Casa_28","Casa_Da_Vinci","Casa_Del_Zio","Casa_Italia","Casa_Italiana","Casa_Pastas","Chalet_du_Grand_arc","Chez_Giovanni_Trattoria","Ciao_Pasta","Corner_Pasta","Cost'a_Costa","Côte_Italie","Croissan_Italien","Da_Franco","Del_Arte","Del_Gladiatore","Delices_d'italie","Delizie","Delizie_d'uggiano","Della_Nonna","Dino_Ristorante","Dolce_Italia","Dolce_Pasta","Dolce_Vita","Don_Camillo","Elefante","Elio's_Ristorante","Ferretti_Ristorante","Findi","Finzi_1","Foggia_Ristorante","Gabriele_Ristorante_Italiano","Giovany's_Ristorante","Il_Carpaccio","Il_Coccodrillo","Il_Gusta_Pasta","Il_Pinocchio","Il_Primo_Bacio","Il_Ristorante","Il_Tranello","Italia_Pizza","Italia_Trattoria","Italian_Cafe","Italian_Caffe","Italian's_Cafe","Contini","L_Garbino","L'esterina","Le_Visconti","Les_Picholines","Les_Serenades","Mancini","Mezzo_Di_Pasta","Milano","Pastappetit","Pastarito","Pastavino"));
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int i = 1;
		for(Cuisine cuisine: Cuisine.values()){
			for(Cost cost: Cost.values()){
				for (Atmosphere ambiance: Atmosphere.values()){
					for (Arrondissement arr : Arrondissement.values()){
						System.out.println("Restaurant"+i+"("+cuisine.name()+ ", " + cost.name()+ ", " +
					ambiance.name() + ", " + arr.name()+")");
						i++;
					}
				}
			}
		}
	}

}
