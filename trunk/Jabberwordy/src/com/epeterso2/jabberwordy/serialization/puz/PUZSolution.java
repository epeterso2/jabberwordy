package com.epeterso2.jabberwordy.serialization.puz;

public class PUZSolution {
	
	private char letter = 0;
	
	private String rebus = null;
	
	public PUZSolution()
	{
		;
	}
	
	public PUZSolution( char letter )
	{
		this.setLetter(letter);
	}
	
	public PUZSolution( String rebus )
	{
		this.setRebus(rebus);
	}
	
	public PUZSolution( char letter, String rebus )
	{
		this.setLetter(letter);
		this.setRebus(rebus);
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public char getLetter() {
		return letter;
	}

	public void setRebus(String rebus) {
		this.rebus = rebus;
	}

	public String getRebus() {
		return rebus;
	}
	
	public char getSingleLetter()
	{
		char single = 0;
	
		if ( letter >= 'A' && letter <= 'Z' )
		{
			single = letter;
		}
		
		else if ( rebus != null && rebus.length() > 0 && rebus.charAt( 0 ) >= 'A' && rebus.charAt( 0 ) <= 'Z' )
		{
			single = rebus.charAt( 0 );
		}
		
		return single;
	}
	
	public String toString()
	{
		return new StringBuilder().append( "(" ).append( letter ).append( "," ).append( rebus ).append( ")" ).toString();
	}

}
