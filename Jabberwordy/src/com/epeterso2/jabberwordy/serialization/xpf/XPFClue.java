package com.epeterso2.jabberwordy.serialization.xpf;

import com.epeterso2.jabberwordy.util.Coordinate;

public class XPFClue {
	
	private Coordinate coordinate = new Coordinate( 0, 0 );
	
	private String number = null;
	
	private String direction = null;
	
	private String text = null;
	
	private String answer = null;
	
	public XPFClue()
	{
		;
	}
	
	public XPFClue( String text, String answer )
	{
		this.text = text;
		this.answer = answer;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public XPFClue setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
		return this;
	}

	public String getNumber() {
		return number;
	}

	public XPFClue setNumber(String number) {
		this.number = number;
		return this;
	}

	public String getDirection() {
		return direction;
	}

	public XPFClue setDirection(String direction) {
		this.direction = direction;
		return this;
	}

	public String getText() {
		return text;
	}

	public XPFClue setText(String text) {
		this.text = text;
		return this;
	}
	
	public XPFClue setAnswer(String answer) {
		this.answer = answer;
		return this;
	}

	public String getAnswer() {
		return answer;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 37;
		
		hash = hash * 37 + hashCode( getNumber() );
		hash = hash * 37 + hashCode( getCoordinate() );
		hash = hash * 37 + hashCode( getDirection() );
		hash = hash * 37 + hashCode( getText() );
		hash = hash * 37 + hashCode( getAnswer() );
		
		return hash;
	}
	
	private int hashCode( Object object )
	{
		return object == null ? 37 : object.hashCode();
	}
	
	public boolean equals( Object object )
	{
		if ( object instanceof XPFClue )
		{
			XPFClue that = (XPFClue) object;
			
			return
				equal( this.getCoordinate(), that.getCoordinate() ) &&
				equal( this.getDirection(), that.getDirection() ) &&
				equal( this.getText(), that.getText() ) &&
				equal( this.getNumber(), that.getNumber() ) &&
				equal( this.getAnswer(), that.getAnswer() );
		}
		
		else
		{
			return false;
		}
	}
	
	private boolean equal( Object one, Object two )
	{
		return one == null ? two == null : one.equals( two );
	}
	
	public boolean isLocated()
	{
		return coordinate != null && number != null && direction != null;
	}

}
