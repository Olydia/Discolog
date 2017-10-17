package fr.limsi.negotiate.restaurant;

import java.util.HashMap;
import java.util.Map;

import fr.limsi.negotiate.toyExample.*;

public class CreatOptions {
	static String mexican [] = {"Arriba_Mexico","Atlantic_Oak","Ay_Mexico","Azteca","BOCAMEXA","Cactus_Tex_mex","Cadillac","Candelaria","Charly_Tex_Mex","Davidos","Chipotle","Chido_Tacos","Kristina","Marti","Chicanos","Canos_Tex_Mex","CHIDO","CHIPO_MEX","Dona","Eagle_Den_Tex_Mex","ElBurrito_Mexicano","ElCamino_DElSol","ElMexicano","ElPaso_DElNorte","ElPopoca","ElSombrero","ElTex_Mex","ELNOPAL","ELRANCHO","FAJITAS","Hollywood_Café","Houston_Tex_mex","ElCantina_Mexicaine","Mexicana","Parrilla","Sandia","ElJohn","ElMexico","ElShip_Tex_Mex","ElTampico_Tex_Mex","ElTex_Mex","ElTijuana_Tex_Mex","Los_Mexicanos","Luna","Mexi_And_Co","Mexicano","Mexicanos","Mexico","Mexico_city","Mexico_Lindo","Mexico_loco","Mexico_Magico","Mexicor","Modelo","Mucho_Mex","O_Mexico","OTACOS","PAPPASITOS","Ranch_River","Salsa_Mexicana","SoloTex_Mex","Tacomex","Tex_Mex","ElPachuca","ElRosario","key_West","Mex_South","Texas_City","The_Westerner_Tex_Mex","Trading_Post_Tex_Mex","Viva_Mexico","Wet_Willie"};
	static String[]  turkish = {"AYÇA", "ABA_Turkish","A_La_Turka","Agora","Akdeniz","Aksaray","Ali_Baba_of_West_Side","Anatolia","Anatolian_Gyro","Anatolya_Corner","AnTalia_Med","Antepliler","Antique_Garage","Ayza_Wine","babiji","Babylon_Hookah_Lounge","barak","Bayridge_Cafe","Bella_Luna","Bereket","Bodrum","CAPPADOCE","Caucasus_Garden","Deniz","Dervish","derya","Drom","Efendi","efes","ekin","El_Sultan","Ella","Fez","Gulluoglu_Baklava","Hanci","Hazar","Horus","Hummus_Kitchen","Ishtar","Iznik","Kazan","Kebab_Garden","Kebab_House_II","KIBELLE","kokorec","kuzu_kitchen","Limon","Mavi_Meze_Grill","Mekan","Mmm_Enfes","Pera_Soho","Pescatore","Roka","Rumi_Cafe","sarastro","Savann","Simit+Smith","Sizin","Smyrna_","sofra","Taksim_Square","Tarabia","Tas_pide","The_Country_Kebab","Troy_Grill","Turkish_Cuisine","TurKiss","Turks_&_Frogs","Turkuaz","Uskudar","Uskudar","Zeytinz"};
	static String italian [] = {"A_Citadella","Andiamo_Pizzeria","Angolo_italia","Anteprima","Arcadius_Ristorante","Arlecchino","Au_Soleil_Italien","Barocco","Beitalie","Bella_Italia","Bella_Napoli","Blue_Pasta","Buona_Pasta","Caffe_ditalia","Caffe_Di_Pasta","Capri_Pizza","Casa_28","Casa_Da_Vinci","Casa_Del_Zio","Casa_Italia","Casa_Italiana","Casa_Pastas","Chalet_du_Grand_arc","Chez_Giovanni_Trattoria","Ciao_Pasta","Corner_Pasta","Costa_Costa","Cite_Italie","Croissan_Italien","Da_Franco","Del_Arte","Del_Gladiatore","Delices_ditalie","Delizie","Delizie_duggiano","Della_Nonna","Dino_Ristorante","Dolce_Italia","Dolce_Pasta","Dolce_Vita","Don_Camillo","Elefante","Elio_Ristorante","Ferretti_Ristorante","Findi","Finzi_1","Foggia_Ristorante","Gabriele_Ristorante_Italiano","Giovany_Ristorante","Il_Carpaccio","Il_Coccodrillo","Il_Gusta_Pasta","Il_Pinocchio","Il_Primo_Bacio","Il_Ristorante","Il_Tranello","Italia_Pizza","Italia_Trattoria","Italian_Cafe","Italian_Caffe","Italian_Cafe","Contini","L_Garbino","esterina","Le_Visconti","Les_Picholines","Les_Serenades","Mancini","Mezzo_Di_Pasta","Milano","Pastappetit","Pastarito","Pastavino"};
	static String [] korean = {"Agujjim","Ahsi","Anju","Bada","Balwoo_Gongyang","Bibimbap","Bingsu","Bobea","Bong","Bossam","Bulgogi","Darai","Doenjang","Dotorimuk","Dubukimchi","EDa","Galbi","Gamjatang","Gaon","Gari","Gaya","Gopchang","Guibine","Gwon’s","Gyeranjjim","Haemul_Pajeon","HangLi ","HanLim ","Hansung","Hero","Hobakjuk","Jantchi","Japchae","Jjambbong","Jules","Jungsik","Kalguksu","Kongguksu","Kookil_Kwan","Korean_Barbecue","Koredam ","Kwon_Sook_Soo","La_Yeon","Lab","Little_Seou","Matsuba","Midam","Mingles","Mudfish","Nabi","Naengmyeon","Namou","Odori","Ossek_Garden","Ppeongtwigi","Samgyetang","Samo","Seoul","Shim","Shingané","Sikzack","Sobane","Soon","Soura","Sundae","Tteokguk","Wabosso ","Woo_Jung", "matsuba", "dolaji", "GinMin","NiNaNo"};
	static String [] french = {"Moulin","orangerie_du_Chateau","Couture","Papillon","Marine","Glycines","etape","Couronne","air_du_Temps","Adelita","Tripot","Vieux_Pressoir","Pasha","Gentilliere","Roseraie","Boudor","Platanes","Annees_Folles","Oliviers","Au_Bon_Vivant","Fleuray","Poidras_Thierry","Anatolian_Gyro","Pichet","Caillere","Arpege","Patisson","Chateau_Bellevue","Val_de_Save","Dryades","Eldorado","Grande_Tour","Pavillon_Limere","Restaurant","En_Bonne_Compagnie","Relais_Vermeer","Lion_dor","Moulin_Fleuri","Oceanis","Delyse","Amarante","Bistro","Val_damby","Cocagnou","Deuvaliere","odeon","Mistral","Tocqueville","Tilleuls","Voidor","Trait_dunion","Cerdagne","Grange","Moulin","Maison_Blanche","Moulin_de_Planche","Saint_Honore","Chateau_de_Bouesse","oree_de_Chambord","Clemence_Estaminet","Taj_Mahal","oree_du_Bois","BelSaisons","Chateau_de_Bagnols","Cocina_Mundi","Relais_Fleuri","Petit_Bonneval","Casino_de_Royat","Chateau_de_Verie", "Chez_Felix", "Monstre", "Chers_Voisins"};
	static String  [] chinese = {"Asia_parfum","Ancian_Dragon","Asia_place","Asia_star","Beijing","Chang’s","Chieng_Mai","Chim","China_club","China_Express","China_star","Chinatown","Chinese_wall_","Cho_Gao","Dai_Duong","dong","Dragon_","Dragon_dor","Escale_de_Chine","Etoile_de_Chine","Gold_Dragon","Hao_Hao","Heng_lay","Indochine","Jade","JILIYa","jujube","Kanci","kawloon","Ken","Kinga","Le_Dragon_dor","Le_Lac_de_Chine","Le_Saïgon","Le_Shanghai","Le_Yang_Tse","lido","Lin","Lion_","Lotus_Flower","Lune_Rouge","Lys","Mandarin","Meyliwa","Mizushi","Mongolian","New_China","New_China","New_China","New_Fuli","Noodle_King","Oasis_","Pekin","Perle_de_Chine","Phenix_","phou_khet","Phu_do","Royal_dasie","Royal_orchid","Saigon","Satsuki","Shan_Gout","Shangaï_Palace","Soctrang","Song_hoat","TAOkan","Toku","Tsou","Vong","Yang","Yong","Yun"};
	static String [] japanese = {"AKI","EBIS","EDOKKO","FOUJITA","SUSHI_PLACE","HIGUMA","KILALA","KINUGAWA","KOESTU","MATSUDA","SAKURA","MIYOSHI","MIDORY","NARITAKE","NODAIWA","SANUKIA","SAPPORO","TAKARA","YASUBE","YAKINIKU","AI","HOKKAIDO","TOKYO","FUKISHIMA","AKITA","BIZAN","JUJI","KINTARO","KOFUKU","OGOURA","KOBA","OKINAWA","RAMEN_PLACE","SUSHI_WASABI","SAPPORO","YAMATO","YOKOSUNA","TAKEO","ICHO","KICCHO","SUN","TAKIMI","AKIDA","INAGIKU","MATSUYA","MAKI_CORNER","SUSHIKO","TSURU","YASAMI","SAMURA","SAYUKI","YASAMI","AZABU","HANAFOUSA","JAPOTORI","JAPORAMA","MATSUDO","NIPON","NIPONYAKI","NAGOYA","YEN","TSUKIZI","YOOKOSO","AKITO","YYOTAN","KYOTO","KONOHAWA","MIKADO","SUCHIKU","BARAMAKI","HUTARU","KAMADO"};

	public static void main(String[] args) {
		Map<String, String[]> restau = new HashMap<String, String[]>();
		restau.put("ITALIAN", italian);
	//	restau.put("TURKISH", turkish);
		restau.put("MEXICAN", mexican);
		restau.put("FRENCH", french );
//		restau.put("CHINESE", chinese);
//		restau.put("KOREAN", korean);
		restau.put("JAPANESE", japanese);



		int i =0;
		for(String cuisine: restau.keySet()){

			for(ToyCost cost: ToyCost.values()){
				for (ToyAtmosphere ambiance: ToyAtmosphere.values()){
					for (ToyLocation arr : ToyLocation.values()){
							System.out.println(restau.get(cuisine)[i].toUpperCase()+"(ToyCuisine."+cuisine+ ", ToyCost." + cost.name()+ ", ToyAtmosphere." +
									ambiance.name() + ", ToyLocation." + arr.name()+"),");
							i++;
						}

					}
				}
			i=0;
			}

//		System.out.println(mexican.length);
//		System.out.println(italian.length);
//		System.out.println(turkish.length);
//		System.out.println(french.length);
//		System.out.println(korean.length);
//		System.out.println(japanese.length);
//		System.out.println(chinese.length);




	}

}
