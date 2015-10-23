/*
Name: Radians.java
Description: Radian conversions between DMS and Radians, useful for some calculations
*/

import java.lang.Math;


public class Radians{

	private double radians; // Radian value
	

	//Default constructor 0 radians
	public Radians(){
		this.radians = 0;
	}


	//Conversion between Radians and DMS
	public Radians(DMS dms){
		this.radians = dms.asDecimal() * Math.PI / 180;

	}


	//Default constructor for radians
	public Radians(double radians){
		this.radians = radians;
	}


	//DMS conversion of Radians
	public DMS asDMS(){
		return new DMS(this);
	}


	//Decimal value of Radians
	public double asDecimal(){
		return this.radians;
	}


	//Setter for Radians
	public void setRadians(double radians){
		this.radians = radians;
	}


	//To String and Clone methods
	public String toString(){
		return Double.toString(this.radians);
	}

	public Radians clone(){
		return new Radians(this.radians);
	}
}