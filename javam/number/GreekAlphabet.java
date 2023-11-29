package javam.number;

public enum GreekAlphabet {
		
	ALPHA( "¦¡", "¦Á" ),
	BETA( "¦¢", "¦Â" ),
	GAMMA( "¦£", "¦Ã" ),
	DELTA( "¦¤", "¦Ä" ),
	EPSILON( "¦¥", "¦Å" ),
	ZETA( "¦¦","¦Æ" ),
	ETA( "¦§", "¦Ç" ),
	THETA( "¦¨", "¦È" ),
	IOTA( "¦©", "¦É" ),
	KAPPA( "¦ª", "¦Ê" ),
	LAMBDA( "¦«", "¦Ë" ),
	MU( "¦¬", "¦Ì" ),
	NU( "¦­", "¦Í" ),
	XI( "¦®", "¦Î" ),
	OMICRON( "¦¯", "¦Ï" ),
	PI( "¦°", "¦Ð" ),
	RHO( "¦±", "¦Ñ" ),
	SIGMA( "¦²", "¦Ò" ),
	TAU( "¦³", "¦Ó" ),
	UPSILON( "¦´", "¦Ô" ),
	PHI( "¦µ", "¦Õ" ),
	CHI( "¦¶", "¦Ö" ),
	PSI( "¦·", "¦×" ),
	OMEGA( "¦¸", "¦Ø" );
	
	public String blockletter;
	
	public String lowercase;
	
	private GreekAlphabet( String blockletter, String lowercase ) {
		this.blockletter = blockletter;
		this.lowercase = lowercase;
	}
	
}
