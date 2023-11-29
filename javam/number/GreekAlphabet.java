package javam.number;

public enum GreekAlphabet {
		
	ALPHA( "��", "��" ),
	BETA( "��", "��" ),
	GAMMA( "��", "��" ),
	DELTA( "��", "��" ),
	EPSILON( "��", "��" ),
	ZETA( "��","��" ),
	ETA( "��", "��" ),
	THETA( "��", "��" ),
	IOTA( "��", "��" ),
	KAPPA( "��", "��" ),
	LAMBDA( "��", "��" ),
	MU( "��", "��" ),
	NU( "��", "��" ),
	XI( "��", "��" ),
	OMICRON( "��", "��" ),
	PI( "��", "��" ),
	RHO( "��", "��" ),
	SIGMA( "��", "��" ),
	TAU( "��", "��" ),
	UPSILON( "��", "��" ),
	PHI( "��", "��" ),
	CHI( "��", "��" ),
	PSI( "��", "��" ),
	OMEGA( "��", "��" );
	
	public String blockletter;
	
	public String lowercase;
	
	private GreekAlphabet( String blockletter, String lowercase ) {
		this.blockletter = blockletter;
		this.lowercase = lowercase;
	}
	
}
