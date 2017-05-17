package fr.limsi.negotiate.movie;
import java.util.ArrayList;
import java.util.List;

import fr.limsi.negotiate.*;



public enum Movie implements Option{
	MOVIE1(Category.ANIMATION, Year.THE_SEXTIES, Country.FRANCE),
	MOVIE2(Category.ANIMATION, Year.THE_SEXTIES, Country.US),
	MOVIE3(Category.ANIMATION, Year.THE_SEXTIES, Country.CANADA),
	MOVIE4(Category.ANIMATION, Year.THE_SEXTIES, Country.BRITAIN),
	MOVIE5(Category.ANIMATION, Year.THE_SEXTIES, Country.SPAIN),
	MOVIE6(Category.ANIMATION, Year.THE_SEVENTIES, Country.FRANCE),
	MOVIE7(Category.ANIMATION, Year.THE_SEVENTIES, Country.US),
	MOVIE8(Category.ANIMATION, Year.THE_SEVENTIES, Country.CANADA),
	MOVIE9(Category.ANIMATION, Year.THE_SEVENTIES, Country.BRITAIN),
	MOVIE10(Category.ANIMATION, Year.THE_SEVENTIES, Country.SPAIN),
	MOVIE11(Category.ANIMATION, Year.THE_EIGHTIES, Country.FRANCE),
	MOVIE12(Category.ANIMATION, Year.THE_EIGHTIES, Country.US),
	MOVIE13(Category.ANIMATION, Year.THE_EIGHTIES, Country.CANADA),
	MOVIE14(Category.ANIMATION, Year.THE_EIGHTIES, Country.BRITAIN),
	MOVIE15(Category.ANIMATION, Year.THE_EIGHTIES, Country.SPAIN),
	MOVIE16(Category.ANIMATION, Year.THE_NINETIES, Country.FRANCE),
	MOVIE17(Category.ANIMATION, Year.THE_NINETIES, Country.US),
	MOVIE18(Category.ANIMATION, Year.THE_NINETIES, Country.CANADA),
	MOVIE19(Category.ANIMATION, Year.THE_NINETIES, Country.BRITAIN),
	MOVIE20(Category.ANIMATION, Year.THE_NINETIES, Country.SPAIN),
	MOVIE21(Category.ANIMATION, Year.TWENTY, Country.FRANCE),
	MOVIE22(Category.ANIMATION, Year.TWENTY, Country.US),
	MOVIE23(Category.ANIMATION, Year.TWENTY, Country.CANADA),
	MOVIE24(Category.ANIMATION, Year.TWENTY, Country.BRITAIN),
	MOVIE25(Category.ANIMATION, Year.TWENTY, Country.SPAIN),
	MOVIE26(Category.COMEDY, Year.THE_SEXTIES, Country.FRANCE),
	MOVIE27(Category.COMEDY, Year.THE_SEXTIES, Country.US),
	MOVIE28(Category.COMEDY, Year.THE_SEXTIES, Country.CANADA),
	MOVIE29(Category.COMEDY, Year.THE_SEXTIES, Country.BRITAIN),
	MOVIE30(Category.COMEDY, Year.THE_SEXTIES, Country.SPAIN),
	MOVIE31(Category.COMEDY, Year.THE_SEVENTIES, Country.FRANCE),
	MOVIE32(Category.COMEDY, Year.THE_SEVENTIES, Country.US),
	MOVIE33(Category.COMEDY, Year.THE_SEVENTIES, Country.CANADA),
	MOVIE34(Category.COMEDY, Year.THE_SEVENTIES, Country.BRITAIN),
	MOVIE35(Category.COMEDY, Year.THE_SEVENTIES, Country.SPAIN),
	MOVIE36(Category.COMEDY, Year.THE_EIGHTIES, Country.FRANCE),
	MOVIE37(Category.COMEDY, Year.THE_EIGHTIES, Country.US),
	MOVIE38(Category.COMEDY, Year.THE_EIGHTIES, Country.CANADA),
	MOVIE39(Category.COMEDY, Year.THE_EIGHTIES, Country.BRITAIN),
	MOVIE40(Category.COMEDY, Year.THE_EIGHTIES, Country.SPAIN),
	MOVIE41(Category.COMEDY, Year.THE_NINETIES, Country.FRANCE),
	MOVIE42(Category.COMEDY, Year.THE_NINETIES, Country.US),
	MOVIE43(Category.COMEDY, Year.THE_NINETIES, Country.CANADA),
	MOVIE44(Category.COMEDY, Year.THE_NINETIES, Country.BRITAIN),
	MOVIE45(Category.COMEDY, Year.THE_NINETIES, Country.SPAIN),
	MOVIE46(Category.COMEDY, Year.TWENTY, Country.FRANCE),
	MOVIE47(Category.COMEDY, Year.TWENTY, Country.US),
	MOVIE48(Category.COMEDY, Year.TWENTY, Country.CANADA),
	MOVIE49(Category.COMEDY, Year.TWENTY, Country.BRITAIN),
	MOVIE50(Category.COMEDY, Year.TWENTY, Country.SPAIN),
	MOVIE51(Category.HORROR, Year.THE_SEXTIES, Country.FRANCE),
	MOVIE52(Category.HORROR, Year.THE_SEXTIES, Country.US),
	MOVIE53(Category.HORROR, Year.THE_SEXTIES, Country.CANADA),
	MOVIE54(Category.HORROR, Year.THE_SEXTIES, Country.BRITAIN),
	MOVIE55(Category.HORROR, Year.THE_SEXTIES, Country.SPAIN),
	MOVIE56(Category.HORROR, Year.THE_SEVENTIES, Country.FRANCE),
	MOVIE57(Category.HORROR, Year.THE_SEVENTIES, Country.US),
	MOVIE58(Category.HORROR, Year.THE_SEVENTIES, Country.CANADA),
	MOVIE59(Category.HORROR, Year.THE_SEVENTIES, Country.BRITAIN),
	MOVIE60(Category.HORROR, Year.THE_SEVENTIES, Country.SPAIN),
	MOVIE61(Category.HORROR, Year.THE_EIGHTIES, Country.FRANCE),
	MOVIE62(Category.HORROR, Year.THE_EIGHTIES, Country.US),
	MOVIE63(Category.HORROR, Year.THE_EIGHTIES, Country.CANADA),
	MOVIE64(Category.HORROR, Year.THE_EIGHTIES, Country.BRITAIN),
	MOVIE65(Category.HORROR, Year.THE_EIGHTIES, Country.SPAIN),
	MOVIE66(Category.HORROR, Year.THE_NINETIES, Country.FRANCE),
	MOVIE67(Category.HORROR, Year.THE_NINETIES, Country.US),
	MOVIE68(Category.HORROR, Year.THE_NINETIES, Country.CANADA),
	MOVIE69(Category.HORROR, Year.THE_NINETIES, Country.BRITAIN),
	MOVIE70(Category.HORROR, Year.THE_NINETIES, Country.SPAIN),
	MOVIE71(Category.HORROR, Year.TWENTY, Country.FRANCE),
	MOVIE72(Category.HORROR, Year.TWENTY, Country.US),
	MOVIE73(Category.HORROR, Year.TWENTY, Country.CANADA),
	MOVIE74(Category.HORROR, Year.TWENTY, Country.BRITAIN),
	MOVIE75(Category.HORROR, Year.TWENTY, Country.SPAIN),
	MOVIE76(Category.THRILLER, Year.THE_SEXTIES, Country.FRANCE),
	MOVIE77(Category.THRILLER, Year.THE_SEXTIES, Country.US),
	MOVIE78(Category.THRILLER, Year.THE_SEXTIES, Country.CANADA),
	MOVIE79(Category.THRILLER, Year.THE_SEXTIES, Country.BRITAIN),
	MOVIE80(Category.THRILLER, Year.THE_SEXTIES, Country.SPAIN),
	MOVIE81(Category.THRILLER, Year.THE_SEVENTIES, Country.FRANCE),
	MOVIE82(Category.THRILLER, Year.THE_SEVENTIES, Country.US),
	MOVIE83(Category.THRILLER, Year.THE_SEVENTIES, Country.CANADA),
	MOVIE84(Category.THRILLER, Year.THE_SEVENTIES, Country.BRITAIN),
	MOVIE85(Category.THRILLER, Year.THE_SEVENTIES, Country.SPAIN),
	MOVIE86(Category.THRILLER, Year.THE_EIGHTIES, Country.FRANCE),
	MOVIE87(Category.THRILLER, Year.THE_EIGHTIES, Country.US),
	MOVIE88(Category.THRILLER, Year.THE_EIGHTIES, Country.CANADA),
	MOVIE89(Category.THRILLER, Year.THE_EIGHTIES, Country.BRITAIN),
	MOVIE90(Category.THRILLER, Year.THE_EIGHTIES, Country.SPAIN),
	MOVIE91(Category.THRILLER, Year.THE_NINETIES, Country.FRANCE),
	MOVIE92(Category.THRILLER, Year.THE_NINETIES, Country.US),
	MOVIE93(Category.THRILLER, Year.THE_NINETIES, Country.CANADA),
	MOVIE94(Category.THRILLER, Year.THE_NINETIES, Country.BRITAIN),
	MOVIE95(Category.THRILLER, Year.THE_NINETIES, Country.SPAIN),
	MOVIE96(Category.THRILLER, Year.TWENTY, Country.FRANCE),
	MOVIE97(Category.THRILLER, Year.TWENTY, Country.US),
	MOVIE98(Category.THRILLER, Year.TWENTY, Country.CANADA),
	MOVIE99(Category.THRILLER, Year.TWENTY, Country.BRITAIN),
	MOVIE100(Category.THRILLER, Year.TWENTY, Country.SPAIN),
	MOVIE101(Category.ADVENTURE, Year.THE_SEXTIES, Country.FRANCE),
	MOVIE102(Category.ADVENTURE, Year.THE_SEXTIES, Country.US),
	MOVIE103(Category.ADVENTURE, Year.THE_SEXTIES, Country.CANADA),
	MOVIE104(Category.ADVENTURE, Year.THE_SEXTIES, Country.BRITAIN),
	MOVIE105(Category.ADVENTURE, Year.THE_SEXTIES, Country.SPAIN),
	MOVIE106(Category.ADVENTURE, Year.THE_SEVENTIES, Country.FRANCE),
	MOVIE107(Category.ADVENTURE, Year.THE_SEVENTIES, Country.US),
	MOVIE108(Category.ADVENTURE, Year.THE_SEVENTIES, Country.CANADA),
	MOVIE109(Category.ADVENTURE, Year.THE_SEVENTIES, Country.BRITAIN),
	MOVIE110(Category.ADVENTURE, Year.THE_SEVENTIES, Country.SPAIN),
	MOVIE111(Category.ADVENTURE, Year.THE_EIGHTIES, Country.FRANCE),
	MOVIE112(Category.ADVENTURE, Year.THE_EIGHTIES, Country.US),
	MOVIE113(Category.ADVENTURE, Year.THE_EIGHTIES, Country.CANADA),
	MOVIE114(Category.ADVENTURE, Year.THE_EIGHTIES, Country.BRITAIN),
	MOVIE115(Category.ADVENTURE, Year.THE_EIGHTIES, Country.SPAIN),
	MOVIE116(Category.ADVENTURE, Year.THE_NINETIES, Country.FRANCE),
	MOVIE117(Category.ADVENTURE, Year.THE_NINETIES, Country.US),
	MOVIE118(Category.ADVENTURE, Year.THE_NINETIES, Country.CANADA),
	MOVIE119(Category.ADVENTURE, Year.THE_NINETIES, Country.BRITAIN),
	MOVIE120(Category.ADVENTURE, Year.THE_NINETIES, Country.SPAIN),
	MOVIE121(Category.ADVENTURE, Year.TWENTY, Country.FRANCE),
	MOVIE122(Category.ADVENTURE, Year.TWENTY, Country.US),
	MOVIE123(Category.ADVENTURE, Year.TWENTY, Country.CANADA),
	MOVIE124(Category.ADVENTURE, Year.TWENTY, Country.BRITAIN),
	MOVIE125(Category.ADVENTURE, Year.TWENTY, Country.SPAIN),
	MOVIE126(Category.DRAMA, Year.THE_SEXTIES, Country.FRANCE),
	MOVIE127(Category.DRAMA, Year.THE_SEXTIES, Country.US),
	MOVIE128(Category.DRAMA, Year.THE_SEXTIES, Country.CANADA),
	MOVIE129(Category.DRAMA, Year.THE_SEXTIES, Country.BRITAIN),
	MOVIE130(Category.DRAMA, Year.THE_SEXTIES, Country.SPAIN),
	MOVIE131(Category.DRAMA, Year.THE_SEVENTIES, Country.FRANCE),
	MOVIE132(Category.DRAMA, Year.THE_SEVENTIES, Country.US),
	MOVIE133(Category.DRAMA, Year.THE_SEVENTIES, Country.CANADA),
	MOVIE134(Category.DRAMA, Year.THE_SEVENTIES, Country.BRITAIN),
	MOVIE135(Category.DRAMA, Year.THE_SEVENTIES, Country.SPAIN),
	MOVIE136(Category.DRAMA, Year.THE_EIGHTIES, Country.FRANCE),
	MOVIE137(Category.DRAMA, Year.THE_EIGHTIES, Country.US),
	MOVIE138(Category.DRAMA, Year.THE_EIGHTIES, Country.CANADA),
	MOVIE139(Category.DRAMA, Year.THE_EIGHTIES, Country.BRITAIN),
	MOVIE140(Category.DRAMA, Year.THE_EIGHTIES, Country.SPAIN),
	MOVIE141(Category.DRAMA, Year.THE_NINETIES, Country.FRANCE),
	MOVIE142(Category.DRAMA, Year.THE_NINETIES, Country.US),
	MOVIE143(Category.DRAMA, Year.THE_NINETIES, Country.CANADA),
	MOVIE144(Category.DRAMA, Year.THE_NINETIES, Country.BRITAIN),
	MOVIE145(Category.DRAMA, Year.THE_NINETIES, Country.SPAIN),
	MOVIE146(Category.DRAMA, Year.TWENTY, Country.FRANCE),
	MOVIE147(Category.DRAMA, Year.TWENTY, Country.US),
	MOVIE148(Category.DRAMA, Year.TWENTY, Country.CANADA),
	MOVIE149(Category.DRAMA, Year.TWENTY, Country.BRITAIN),
	MOVIE150(Category.DRAMA, Year.TWENTY, Country.SPAIN);
	
	public final Category category;
	public final Year year;
	public final Country country;

	@SuppressWarnings("serial")
	private static final List<Class<? extends Criterion>> CRITERIA = 
			new ArrayList<Class<? extends Criterion>> () {{ 
						add(Category.class); add(Year.class); add(Country.class); }};
	
	Movie (Category category, Year year, Country country) {
		this.category = category;
		this.year = year;
		this.country = country;
	}

	@Override
	public Criterion getValue (Class<? extends Criterion> c) {
		return c == Category.class ? category :
			c == Year.class ? year : c == Country.class? country : null; // throw error? 
	}

	@Override
	public List<Class<? extends Criterion>> getCriteria() {

		return CRITERIA;
	}

	@Override
	public String getFrVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	public String capitalize(String input){
		String output = input.substring(0, 1).toUpperCase() + input.substring(1).toLowerCase();
		return output;

	}
	
	@Override
	public String print() {
		// TODO Auto-generated method stub
		  return "It's a "+ this.country+", " +this.category+" movie produced in  "+ this.year ;
	}
	
	@Override
	public String toString(){
		return this.capitalize(this.name());

	}
	
}


