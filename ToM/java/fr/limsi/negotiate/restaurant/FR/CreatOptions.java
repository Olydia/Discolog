package fr.limsi.negotiate.restaurant.FR;

import fr.limsi.negotiate.toyExample.ToyAtmosphere;
import fr.limsi.negotiate.toyExample.*;

import java.util.HashMap;
import java.util.Map;


public class CreatOptions {
	static String mexican [] = {"Arriba_Mexico","Atlantic_Oak","Ay_Mexico","Azteca","BOCAMEXA",
			"Cactus_Tex_mex","Cadillac","Candelaria","Charly_Tex_Mex","Davidos","Chipotle",
			"Chido_Tacos","Kristina","Marti","Chicanos","Canos_Tex_Mex","CHIDO","CHIPO_MEX",
			"Dona","Eagle_Den_Tex_Mex","ElBurrito_Mexicano","ElCamino_DElSol","ElMexicano","ElPaso_DElNorte","ElPopoca","ElSombrero","ElTex_Mex2","ELNOPAL","ELRANCHO","FAJITAS","Hollywood_CafE","Houston_Tex_mex","ElCantina_Mexicaine","Mexicana","Parrilla","Sandia","ElJohn","ElMexico","ElShip_Tex_Mex","ElTampico_Tex_Mex","ElTex_Mex",
			"ElTijuana_Tex_Mex","Los_Mexicanos","Luna","Mexi_And_Co","Mexicano","Mexicanos","Mexico","Mexico_city","Mexico_Lindo","Mexico_loco","Mexico_Magico",
			"Mexicor","Modelo","Mucho_Mex","O_Mexico","OTACOS","PAPPASITOS","Ranch_River","Salsa_Mexicana","SoloTex_Mex","Tacomex","Tex_Mex","ElPachuca","ElRosario",
			"key_West","Mex_South","Texas_City","The_Westerner_Tex_Mex","Trading_Post_Tex_Mex","Viva_Mexico","Wet_Willie", 			"Dona1","Eagle_Den_Tex_Mex1","ElBurrito_Mexicano1","ElCamino_DElSol1","ElMexicano1","ElPaso_DElNorte1","ElPopoca1","ElSombrero1","ElTex_Mex1","ELNOPAL1","ELRANCHO1","FAJITAS1","Hollywood_CafE1",
			"Houston_Tex_mex1","ElCantina_Mexicaine1","Mexicana1","Parrilla1","Sandia1","ElJohn1","ElMexico1","ElShip_Tex_Mex1",
			"ElTampico_Tex_Mex1","ElTex_Mex1"};

	static String[]  turkish = {"AYAYA", "ABA_Turkish","A_La_Turka","Agora","Akdeniz","Aksaray",
			"Ali_Baba_of_West_Side","Anatolia","Anatolian_Gyro2","Anatolya_Corner","AnTalia_Med","Antepliler","Antique_Garage","Ayza_Wine","babiji","Babylon_Hookah_Lounge","barak","Bayridge_Cafe","Bella_Luna","Bereket","Bodrum","CAPPADOCE","Caucasus_Garden","Deniz","Dervish","derya","Drom","Efendi","efes","ekin","El_Sultan","Ella","Fez","Gulluoglu_Baklava","Hanci","Hazar","Horus","Hummus_Kitchen","Ishtar","Iznik","Kazan","Kebab_Garden","Kebab_House_II","KIBELLE","kokorec","kuzu_kitchen","Limon","Mavi_Meze_Grill","Mekan","Mmm_Enfes","Pera_Soho","Pescatore","Roka","Rumi_Cafe","sarastro","Savann","Simit+Smith","Sizin","Smyrna_","sofra","Taksim_Square","Tarabia","Tas_pide","The_Country_Kebab","Troy_Grill","Turkish_Cuisine","TurKiss","Turks_and_Frogs","Turkuaz","Uskudar2","Uskudar1","Zeytinz"
			,"AYAYA1", "ABA_Turkish1","A_La_Turka1","Agora1","Akdeniz1","Aksaray1",
			"Ali_Baba_of_West_Side1","Anatolia1","Anatolian_Gyro1","Anatolya_Corner1","AnTalia_Med1","Antepliler1","Antique_Garage1","Ayza_Wine1","babiji1","Babylon_Hookah_Lounge1","barak1","Bayridge_Cafe1","Bella_Luna1","Bereket1","Bodrum1","CAPPADOCE1","Caucasus_Garden1","Deniz1","Dervish1","derya1","Drom1","Efendi1","efes1","ekin1","El_Sultan1","Ella1","Fez1","Gulluoglu_Baklava1","Hanci1","Hazar1","Horus1","Hummus_Kitchen1","Ishtar1","Iznik1","Kazan1","Kebab_Garden1","Kebab_House_II1","KIBELLE1","kokorec1","kuzu_kitchen1","Limon1","Mavi_Meze_Grill1","Mekan1","Mmm_Enfes1","Pera_Soho1","Pescatore1","Roka1","Rumi_Cafe1","sarastro1","Savann1","SimitandSmith1","Sizin1","Smyrna_1","sofra1","Taksim_Square1","Tarabia1","Tas_pide1","The_Country_Kebab1","Troy_Grill1","Turkish_Cuisine1","TurKiss1","Turks_and_Frogs1","Turkuaz1","Uskudar1","Uskudar1","Zeytinz"};
	
	static String italian [] = {"A_Citadella","Andiamo_Pizzeria","Angolo_italia","Anteprima",
			"Arcadius_Ristorante","Arlecchino","Au_Soleil_Italien","Barocco","Beitalie","Bella_Italia","Bella_Napoli","Blue_Pasta",
			"Buona_Pasta","Caffe_ditalia","Caffe_Di_Pasta","Capri_Pizza","Casa_28","Casa_Da_Vinci","Casa_Del_Zio","Casa_Italia","Casa_Italiana",
			"Casa_Pastas","Chalet_du_Grand_arc","Chez_Giovanni_Trattoria","Ciao_Pasta","Corner_Pasta","Costa_Costa","Cite_Italie","Croissan_Italien",
			"Da_Franco","Del_Arte","Del_Gladiatore","Delices_ditalie","Delizie","Delizie_duggiano","Della_Nonna","Dino_Ristorante","Dolce_Italia",
			"Dolce_Pasta","Dolce_Vita","Don_Camillo","Elefante","Elio_Ristorante","Ferretti_Ristorante","Findi","Finzi_1","Foggia_Ristorante","Gabriele_Ristorante_Italiano","Giovany_Ristorante","Il_Carpaccio","Il_Coccodrillo","Il_Gusta_Pasta","Il_Pinocchio","Il_Primo_Bacio","Il_Ristorante","Il_Tranello","Italia_Pizza","Italia_Trattoria","Italian_Cafe","Italian_Caffe","Italian_Cafe2","Contini","L_Garbino","esterina","Le_Visconti","Les_Picholines","Les_Serenades","Mancini","Mezzo_Di_Pasta","Milano","Pastappetit","Pastarito","Pastavino",
			"A_Citadella1","Andiamo_Pizzeria1","Angolo_italia1","Anteprima1",
			"Arcadius_Ristorante1","Arlecchino1","Au_Soleil_Italien1","Barocco1","Beitalie1","Bella_Italia1","Bella_Napoli1","Blue_Pasta1",
			"Buona_Pasta1","Caffe_ditalia1","Caffe_Di_Pasta1","Capri_Pizza1","Casa_281","Casa_Da_Vinci1","Casa_Del_Zio1","Casa_Italia1","Casa_Italiana1",
			"Casa_Pastas1","Chalet_du_Grand_arc1","Chez_Giovanni_Trattoria1","Ciao_Pasta1","Corner_Pasta1","Costa_Costa1","Cite_Italie1","Croissan_Italien1",
			"Da_Franco1","Del_Arte1","Del_Gladiatore1","Delices_ditalie1","Delizie1","Delizie_duggiano1","Della_Nonna1","Dino_Ristorante1","Dolce_Italia1",
			"Dolce_Pasta1","Dolce_Vita1","Don_Camillo1","Elefante1","Elio_Ristorante1","Ferretti_Ristorante1","Findi"};
	
	static String [] korean = {"Agujjim","Ahsi","Anju","Bada","Balwoo_Gongyang","Bibimbap",
			"Bingsu","Bobea","Bong","Bossam","Bulgogi","Darai","Doenjang","Dotorimuk","Dubukimchi","EDa","Galbi","Gamjatang","Gaon","Gari","Gaya","Gopchang","Guibine","Gwonais","Gyeranjjim","Haemul_Pajeon","HangLiA","HanLimA","Hansung","Hero","Hobakjuk","Jantchi","Japchae","Jjambbong","Jules","Jungsik","Kalguksu","Kongguksu","Kookil_Kwan","Korean_Barbecue","KoredamA","Kwon_Sook_Soo","La_Yeon","Lab","Little_Seou","Matsuba","Midam","Mingles","Mudfish","Nabi","Naengmyeon","Namou","Odori","Ossek_Garden","Ppeongtwigi","Samgyetang","Samo","Seoul","Shim","ShinganE","Sikzack","Sobane","Soon","Soura","Sundae","Tteokguk","WabossoA","Woo_Jung", "matsuba", "dolaji", "GinMin","NiNaNo",
			"Agujjim1","Ahsi1","Anju1","Bada1","Balwoo_Gongyang1","Bibimbap1",
			"Bingsu1","Bobea1","Bong1","Bossam1","Bulgogi1","Darai1","Doenjang1","Dotorimuk1","Dubukimchi1","EDa1","Galbi1","Gamjatang1","Gaon1","Gari1","Gaya1","Gopchang1","Guibine1","Gwonais1","Gyeranjjim1","Haemul_Pajeon1","HangLiA1","HanLimA1","Hansung1","Hero1","Hobakjuk1","Jantchi1","Japchae1","Jjambbong1","Jules1","Jungsik1","Kalguksu1","Kongguksu1","Kookil_Kwan1","Korean_Barbecue1","KoredamA1","Kwon_Sook_Soo1","La_Yeon1","Lab1","Little_Seou1","Matsuba1","Midam1","Mingles1","Mudfish1","Nabi1","Naengmyeon1","Namou1","Odori1","Ossek_Garden1","Ppeongtwigi1","Samgyetang1","Samo1","Seoul1","Shim1","ShinganE1","Sikzack1","Sobane1","Soon1","Soura1","Sundae1","Tteokguk1","WabossoA1","Woo_Jung1", "matsuba1", "dolaji1", "GinMin1","NiNaNo1"};
	
	static String [] french = {"Moulin","orangerie_du_Chateau","Couture","Papillon","Marine",
			"Glycines","etape","Couronne","air_du_Temps","Adelita","Tripot","Vieux_Pressoir","Pasha","Gentilliere","Roseraie","Boudor","Platanes","Annees_Folles","Oliviers","Au_Bon_Vivant","Fleuray","Poidras_Thierry","Anatolian_Gyro","Pichet","Caillere","Arpege","Patisson","Chateau_Bellevue","Val_de_Save","Dryades","Eldorado","Grande_Tour","Pavillon_Limere","Restaurant_alpha"
			,"En_Bonne_Compagnie","Relais_Vermeer","Lion_dor","Moulin_Fleuri","Oceanis","Delyse","Amarante","Bistro","Val_damby","Cocagnou","Deuvaliere","odeon","Mistral",
			"Tocqueville","Tilleuls","Voidor","Trait_dunion","Cerdagne","Grange","Moulin2","Maison_Blanche","Moulin_de_Planche","Saint_Honore","Chateau_de_Bouesse",
			"oree_de_Chambord","Clemence_Estaminet","Taj_Mahal","oree_du_Bois","BelSaisons","Chateau_de_Bagnols","Cocina_Mundi","Relais_Fleuri","Petit_Bonneval",
			"Casino_de_Royat","Chateau_de_Verie", "Chez_Felix", "Monstre", "Chers_Voisins","Moulin1","orangerie_du_Chateau1","Couture1","Papillon1","Marine1",
			"Glycines1","etape1","Couronne1","air_du_Temps1","Adelita1","Tripot1","Vieux_Pressoir1","Pasha1","Gentilliere1","Roseraie1","Boudor1","Platanes1",
			"Maison_Blanche1","Moulin_de_Planche1","Saint_Honore1","Chateau_de_Bouesse1",
			"oree_de_Chambord1","Clemence_Estaminet1","Taj_Mahal1","oree_du_Bois1","BelSaisons1","Chateau_de_Bagnols1","Cocina_Mundi1","Relais_Fleuri1","Petit_Bonneval1",
			"Casino_de_Royat1","Chateau_de_Verie1", "Chez_Felix1", "Monstre1", "Chers_Voisins1"};
	
	static String  [] chinese = {"Asia_parfum","Ancian_Dragon","Asia_place","Asia_star","Beijing",
			"Changais","Chieng_Mai","Chim","China_club","China_Express","China_star","Chinatown","Chinese_wall_","Cho_Gao","Dai_Duong","dong","Dragon_",
			"Dragon_dor","Escale_de_Chine","Etoile_de_Chine","Gold_Dragon","Hao_Hao","Heng_lay","Indochine","Jade","JILIYa","jujube","Kanci","kawloon",
			"Ken","Kinga","Le_Dragon_dor","Le_Lac_de_Chine","Le_Saïgon","Le_Shanghai","Le_Yang_Tse","lido","Lin","Lion_","Lotus_Flower","Lune_Rouge",
			"Lys","Mandarin","Meyliwa","Mizushi","Mongolian","New_China","New_China1","New_China11","New_Fuli","Noodle_King","Oasis_","Pekin","Perle_de_Chine",
			"Phenix_","phou_khet","Phu_do","Royal_dasie","Royal_orchid","Saigon","Satsuki","Shan_Gout","Shangaï_Palace","Soctrang","Song_hoat","TAOkan","Toku",
			"Tsou","Vong","Yang","Yong","Yun","Asia_parfum1","Ancian_Dragon1","Asia_place1","Asia_star1","Beijing1",
			"Changais1","Chieng_Mai1","Chim1","China_club1","China_Express1","China_star1","Chinatown1","Chinese_wall_1","Cho_Gao1","Dai_Duong1","dong1","Dragon_1",
			"Dragon_dor1","Escale_de_Chine1","Etoile_de_Chine1","Gold_Dragon1","Hao_Hao1","Heng_lay1","Indochine1","Jade1","JILIYa1","jujube1","Kanci1","kawloon1",
			"Ken1","Kinga1","Le_Dragon_dor1","Le_Lac_de_Chine1","Le_Saïgon1","Le_Shanghai1","Le_Yang_Tse1","lido1","Lin1","Lion_1","Lotus_Flower1","Lune_Rouge1",
			"Lys1","Mandarin1","Meyliwa1","Mizushi1","Mongolian1","New_China1","New_China1","New_China1","New_Fuli1","Noodle_King1","Oasis_1","Pekin1","Perle_de_Chine1",
			"Phenix_1","phou_khet1","Phu_do1","Royal_dasie"};
	
	static String [] japanese = {"AKI","EBIS","EDOKKO","FOUJITA","SUSHI_PLACE","HIGUMA","KILALA","KINUGAWA","KOESTU","MATSUDA","SAKURA","MIYOSHI","MIDORY",
			"NARITAKE","NODAIWA","SANUKIA","SAPPORO","TAKARA","YASUBE","YAKINIKU","AI","HOKKAIDO","TOKYO","FUKISHIMA","AKITA","BIZAN","JUJI","KINTARO",
			"KOFUKU","OGOURA","KOBA","OKINAWA","RAMEN_PLACE","SUSHI_WASABI","SAPPORO","YAMATO","YOKOSUNA","TAKEO","ICHO","KICCHO","SUN","TAKIMI","AKIDA",
			"INAGIKU","MATSUYA","MAKI_CORNER","SUSHIKO","TSURU","YASAMI","SAMURA","SAYUKI",
			"YASAMI1","AZABU","HANAFOUSA","JAPOTORI","JAPORAMA","MATSUDO","NIPON","NIPONYAKI","NAGOYA","YEN","TSUKIZI","YOOKOSO","AKITO","YYOTAN","KYOTO",
			"KONOHAWA","MIKADO","SUCHIKU","BARAMAKI","HUTARU","KAMADO","AKI1","EBIS1","EDOKKO1","FOUJITA1","SUSHI_PLACE1","HIGUMA1","KILALA1","KINUGAWA1","KOESTU1","MATSUDA1","SAKURA1","MIYOSHI1","MIDORY1",
			"NARITAKE1","NODAIWA1","SANUKIA1","SAPPORO1","TAKARA1","YASUBE1","YAKINIKU1","AI1","HOKKAIDO1","TOKYO1","FUKISHIMA1","AKITA1","BIZAN1","JUJI1","KINTARO1",
			"KOFUKU1","OGOURA1","KOBA1","OKINAWA1","RAMEN_PLACE1","SUSHI_WASABI1","SAPPORO1","YAMATO1","YOKOSUNA1","TAKEO1","ICHO1","KICCHO1","SUN1","TAKIMI1","AKIDA1",
			"INAGIKU1","MATSUYA1","MAKI_CORNER1","SUSHIKO1","TSURU1","YASAMI1","SAMURA1","SAYUKI1",
			"YASAMI1","AZABU1","HANAFOUSA1","JAPOTORI1","JAPORAMA1","MATSUDO1","NIPON1",};

	public static void main(String[] args) {
		Map<String, String[]> restau = new HashMap<String, String[]>();
		restau.put("ITALIAN", italian);
		//restau.put("TURKISH", turkish);
		restau.put("MEXICAN", mexican);
		restau.put("FRENCH", french );
		restau.put("CHINESE", chinese);
	//	restau.put("KOREAN", korean);
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
