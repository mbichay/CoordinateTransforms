/*
Name: DistanceMetric.java
Description: Simple class for managing distance metric units of different types
*/

import java.lang.Math;

public class DistanceMetric{

	//Different metric units I decided to support.
	public static enum Unit {
		GIGAMETERS(9),
		MEGAMETERS(6),
		KILOMETERS(3),
		METERS(0),
		DECIMETERS(-1),
		CENTIMETERS(-2),
		MILLIMETERS(-3),
		MICROMETERS(-6),
		NANOMETERS(-9),
		PICOMETERS(-12),
		FEMTOMETERS(-15);

		public final int exponent;

		Unit(int exponent){
			this.exponent = exponent;
		}

		public int asExponent(){
			return this.exponent;
		}

		public String toString(){
			switch(this.exponent){
				case 9: return "Gigameters";
				case 6: return "Megameters";
				case 3: return "Kilometers";
				case -1: return "Decimeters";
				case -2: return "Centimeters";
				case -3: return "Milimeters";
				case -6: return "Micrometers";
				case -9: return "Nanometers";
				case -12: return "Picometers";
				case -15: return "Feptometers";
				default: return "Meters";
			}
		}

	}


	private double value; //Value
	private Unit unit; // Type of unit


	//Default Constructor, 0 meters
	public DistanceMetric(){
		this.value = 0;
		this.unit = Unit.METERS;
	}


	//User selectable constructor
	public DistanceMetric(double value, Unit unit){
		this.value = value;
		this.unit = unit;
	}



	//Conversions between the different metrics
	public DistanceMetric asGigameters(){
		return new DistanceMetric(this.value / Math.pow(10,(Unit.GIGAMETERS.asExponent()-
								this.unit.asExponent())), Unit.GIGAMETERS);
	}

	public DistanceMetric asMegameters(){
		return new DistanceMetric(this.value / Math.pow(10,(Unit.MEGAMETERS.asExponent()-
								this.unit.asExponent())), Unit.MEGAMETERS);
	}

	public DistanceMetric asKilometers(){
		return new DistanceMetric(this.value / Math.pow(10,(Unit.KILOMETERS.asExponent()-
								this.unit.asExponent())), Unit.KILOMETERS);
	}

	public DistanceMetric asMeters(){
		return new DistanceMetric(this.value / Math.pow(10,(Unit.METERS.asExponent()-
								this.unit.asExponent())), Unit.METERS);
	}

	public DistanceMetric asDecimeters(){
		return new DistanceMetric(this.value / Math.pow(10,(Unit.DECIMETERS.asExponent()-
								this.unit.asExponent())), Unit.DECIMETERS);
	}

	public DistanceMetric asCentimeters(){
		return new DistanceMetric(this.value / Math.pow(10,(Unit.CENTIMETERS.asExponent()-
								this.unit.asExponent())), Unit.CENTIMETERS);
	}

	public DistanceMetric asMillimeters(){
		return new DistanceMetric(this.value / Math.pow(10,(Unit.MILLIMETERS.asExponent()-
								this.unit.asExponent())), Unit.MILLIMETERS);
	}

	public DistanceMetric asMicrometers(){
		return new DistanceMetric(this.value / Math.pow(10,(Unit.MICROMETERS.asExponent()-
								this.unit.asExponent())), Unit.MICROMETERS);
	}

	public DistanceMetric asNanometers(){
		return new DistanceMetric(this.value / Math.pow(10,(Unit.NANOMETERS.asExponent()-
								this.unit.asExponent())), Unit.NANOMETERS);
	}

	public DistanceMetric asPicometers(){
		return new DistanceMetric(this.value / Math.pow(10,(Unit.PICOMETERS.asExponent()-
								this.unit.asExponent())), Unit.PICOMETERS);
	}

	public DistanceMetric asFemtometers(){
		return new DistanceMetric(this.value / Math.pow(10,(Unit.FEMTOMETERS.asExponent()-
								this.unit.asExponent())), Unit.FEMTOMETERS);
	}

	public DistanceMetric asUnit(Unit unit){
		switch(unit.asExponent()){
			case 9: return asGigameters();
			case 6: return asMegameters();
			case 3: return asKilometers();
			case -1: return asDecimeters();
			case -2: return asCentimeters();
			case -3: return asMillimeters();
			case -6: return asMicrometers();
			case -9: return asNanometers();
			case -12: return asPicometers();
			case -15: return asFemtometers();
			default: return asMeters();
		}
	}



	//Getter / setter for values and Unit
	public void setValue(double value){
		this.value = value;
	}

	public double getValue(){
		return this.value;
	}

	public void setUnit(Unit unit){
		this.value = (this.value / Math.pow(10,(unit.asExponent()-this.unit.asExponent())));
		this.unit = unit;
	}

	public Unit getUnit(){
		return this.unit;
	}



	//toString and Clone Methods
	public String toString(){
		return this.value + " " + this.unit.toString();
	}

	public DistanceMetric clone(){
		return new DistanceMetric(this.value, this.unit);
	}
}