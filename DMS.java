/*
Name: DMS.java
Description: Degrees, Minutes, Seconds class for input into LLA, useful for some calculations
*/


import java.lang.Math;

public class DMS{

	//D,M,S Values
	private int degrees;
	private int minutes;
	private double seconds;


	//Default constructors
	//Converts decimal degrees into D,M,S
	public DMS(double degrees){
		this.degrees = (int)degrees;
		this.minutes = (int)((Math.abs(degrees) - Math.abs(this.degrees)) * 60);
		this.seconds = ((double)Math.abs(degrees) - (double)Math.abs(this.degrees)-
						(double)this.minutes/60) * 3600;
	}


	//Basic constructor
	public DMS(int degrees, int minutes, double seconds){
		this.degrees = degrees;
		this.minutes = Math.abs(minutes);
		this.seconds = Math.abs(seconds);
	}


	//Radians conversion into DMS
	public DMS(Radians radians){
		// do the math here for radians to DMs conversion
		this(radians.asDecimal() * (180 / Math.PI));
	}


	//Default Constructor 0,0,0
	public DMS(){
		this.degrees = 0;
		this.minutes = 0;
		this.seconds = 0;
	}


	//Accessor / Mutators for D,M,S
	public void setDegrees(int degrees){
		this.degrees = degrees;
	}

	public int getDegrees(){
		return this.degrees;
	}

	public void setMinutes(int minutes){
		this.minutes = Math.abs(minutes);
	}

	public int getMinutes(){
		return this.minutes;
	}

	public void setSeconds(double seconds){
		this.seconds = Math.abs(seconds);
	}

	public double getSeconds(){
		return this.seconds;
	}


	//Returns double value of the full D,M,S decimal
	public double asDecimal(){
		if (this.degrees < 0)
			return ((double)Math.abs(this.degrees) + (double)this.minutes/60 + this.seconds/3600) * -1;
		return (double)Math.abs(this.degrees) + (double)this.minutes/60 + this.seconds/3600;
	}


	//Returns radian value of the D,M,S Decimal (conversion into radians)
	public Radians asRadians(){
		return new Radians(this);
	}


	//toString and Clone methods for DMS
	public String toString(){
		return this.degrees + "\u00b0" + " " + this.minutes + "\'" + " " + this.seconds + "\"";
	}

	public DMS clone(){
		return new DMS(this.degrees, this.minutes, this.seconds);
	}
}