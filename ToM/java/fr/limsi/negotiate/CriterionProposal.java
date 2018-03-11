package fr.limsi.negotiate;

/**
 * 
 * @author Charles rich
 * 	Criterion proposal
 */ 
public class CriterionProposal extends Proposal {

	@Override
	public String toString() {
		return criterion.toString()/*+ " isSelf: "+ isSelf
				+ "Status: "+status*/;
	}

	public final Criterion criterion;

	public CriterionProposal (boolean isSelf, Criterion criterion) {
		super(isSelf);
		this.criterion = criterion;
	}
	
	public CriterionProposal (Criterion criterion) {
		super();
		this.criterion = criterion;
	}

	@Override
	public
	Criterion getValue() {
		return criterion;
	}
	
	@Override
	public boolean equals(Object obj){
		if ( this == obj )
			return true;
		if ( obj == null )
			return false;
		if ( getClass() != obj.getClass() )
			return false;
		CriterionProposal other = (CriterionProposal) obj;
		if ( criterion == null ) {
			if ( other.getValue() != null )
				return false;
		} else if ( !criterion.equals(other.getValue()) )
			return false;

		return true;
	}
	
	@Override
	public int hashCode () {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((criterion == null) ? 0 : criterion.hashCode());
		return result;
	}

	@Override
	public String printValue() {
		// TODO Auto-generated method stub
		return this.toString();
	}

	@Override
	public String printProposal(String topic) {
		String value = getValue().print(topic);
		if("AEIOUaeiou".indexOf(value.charAt(0)) != -1)
			return "an " + value;
		
		return "a " + value;
	}
	
	@Override
	public String printDetailedProposal(String topic) {
		return printProposal(topic);
	}

	

	@Override
	public String afficherProp() {
		// TODO Auto-generated method stub
		String fr = '\u00e0'+" un restaurant " + this.getValue().afficher();
		return StringToUTF8.convertToUTF8(fr);

	}

	@Override
	public String afficherPropDetail() {
		// TODO Auto-generated method stub
		String fr = afficherProp();
		return StringToUTF8.convertToUTF8(fr);

	}
	
	@Override
	public CriterionProposal mirrorProposal() {
		CriterionProposal p =  new CriterionProposal(!this.isSelf, this.getValue());
		p.setStatus(this.getStatus());
		return p;
	}

}
