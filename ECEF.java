/*
Name: ECEF.java
Description: Earth-Centered, Earth-Fixed is a rotating coordinate frame where
the center of the frame is always referenced from Earth's center of mass
*/

public class ECEF implements CoordinatePosition{
	
	//X,Y,Z positions
	DistanceMetric xPos;
	DistanceMetric yPos;
	DistanceMetric zPos;


	//Basic empty 0,0,0 Meters constructor
	public ECEF(){
		this.xPos = new DistanceMetric();
		this.yPos = new DistanceMetric();
		this.zPos = new DistanceMetric();
	}


	//Basic constructor
	public ECEF(DistanceMetric x, DistanceMetric y, DistanceMetric z){
		this.xPos = x.clone();
		this.yPos = y.clone();
		this.zPos = z.clone();
	}


	//LLA to ECEF Conversion Constructor
	public ECEF(LLA latLonAlt){
		//Gets double values in Radians from the LLA Coordinate frame
		Double lat = latLonAlt.getLatitude().asRadians().asDecimal();
		Double lon = latLonAlt.getLongitude().asRadians().asDecimal();
		Double alt = latLonAlt.getAltitude().asMeters().getValue();

		//Gets the type of Unit to preserve
		DistanceMetric.Unit unit = latLonAlt.getAltitude().getUnit();


		//LLA to XYZ formula, modified to adjust for the asymetrical
		//shape of Earth using the Semimajor,Semiminor, and Eccentricity
		//of the Earth

		//Conversion retains the LLA distance metric of altitude
		double chi = Math.sqrt(1-WGS84.FIRST_ECCENTRICITY_SQUARED.getValue()*
							Math.pow(Math.sin(lat),2));

		this.xPos = new DistanceMetric((WGS84.EARTH_SEMIMAJOR_AXIS.getValue()/chi + alt)*
									 Math.cos(lat) * Math.cos(lon),
									 DistanceMetric.Unit.METERS).asUnit(unit);

		this.yPos = new DistanceMetric((WGS84.EARTH_SEMIMAJOR_AXIS.getValue()/chi + alt)* 
									Math.cos(lat) * Math.sin(lon),
									DistanceMetric.Unit.METERS).asUnit(unit);

		this.zPos = new DistanceMetric((WGS84.EARTH_SEMIMAJOR_AXIS.getValue()*
									(1-WGS84.FIRST_ECCENTRICITY_SQUARED.getValue())/chi + alt)* 
									Math.sin(lat), DistanceMetric.Unit.METERS).asUnit(unit);
	}


	//ENU to ECEF Conversion Constructor
	public ECEF(ENU eastNorthUp, LLA refPt){

		//ENU translation requires a reference point in the
		//Earth-Centered, Earth-Fixed frame
		ECEF refECEF = new ECEF(refPt);


		//Formula for shifting frames between ENU and ECEF
		//Requires a reference point (typically a point on the surface of the earth)

		//Conversion retains the ENU metric for distance
		this.xPos = new DistanceMetric(	-1*Math.sin(refPt.getLongitude().asDecimal()) *
										eastNorthUp.getEPosition().asMeters().getValue() -
										Math.cos(refPt.getLongitude().asDecimal()) *
										Math.sin(refPt.getLatitude().asDecimal()) *
										eastNorthUp.getNPosition().asMeters().getValue() +
										Math.cos(refPt.getLongitude().asDecimal()) *
										Math.cos(refPt.getLatitude().asDecimal()) *
										eastNorthUp.getUPosition().asMeters().getValue() +
										refECEF.getXPosition().asMeters().getValue(),
										DistanceMetric.Unit.METERS).asUnit(eastNorthUp.getUPosition().getUnit());

		this.yPos = new DistanceMetric(	Math.cos(refPt.getLongitude().asDecimal()) *
										eastNorthUp.getEPosition().asMeters().getValue() -
										Math.sin(refPt.getLongitude().asDecimal()) *
										Math.sin(refPt.getLatitude().asDecimal()) *
										eastNorthUp.getNPosition().asMeters().getValue() +
										Math.cos(refPt.getLatitude().asDecimal()) *
										Math.sin(refPt.getLongitude().asDecimal()) *
										eastNorthUp.getUPosition().asMeters().getValue() +
										refECEF.getYPosition().asMeters().getValue(),
										DistanceMetric.Unit.METERS).asUnit(eastNorthUp.getUPosition().getUnit());

		this.zPos = new DistanceMetric(	Math.cos(refPt.getLatitude().asDecimal()) *
										eastNorthUp.getNPosition().asMeters().getValue() +
										Math.sin(refPt.getLatitude().asDecimal()) *
										eastNorthUp.getUPosition().asMeters().getValue() +
										refECEF.getZPosition().asMeters().getValue(),
										DistanceMetric.Unit.METERS).asUnit(eastNorthUp.getUPosition().getUnit());
	}


	//Returns copy of itself
	public ECEF asECEF(){
		return this.clone();
	}


	//Calls the LLA conversion method of ECEF
	//Returns the LLA of the converted ECEF coordinate frame
	public LLA asLLA(){
		return new LLA(this);
	}


	//Calls the ENU conversion method of ECEF
	//Returns the ENU of the converted ECEF coordinate frame
	//Note: requires a reference point
	public ENU asENU(LLA refPt){
		return new ENU(this, refPt);
	}


	public ENU asENU(){
		//Returns a ENU from an arbitrary LLA of 45,45,Earth Surface.
		return asENU(new LLA(new DMS(45),new DMS(45),
							 new DistanceMetric(WGS84.EARTH_SEMIMAJOR_AXIS.getValue(), 
							 DistanceMetric.Unit.METERS)));
	}



	//getters and setters for X,Y,Z positions
	public DistanceMetric getXPosition(){
		return this.xPos.clone();
	}

	public DistanceMetric getYPosition(){
		return this.yPos.clone();
	}

	public DistanceMetric getZPosition(){
		return this.zPos.clone();
	}

	public void setXPosition(DistanceMetric x){
		this.xPos = x.clone();
	}

	public void setYPosition(DistanceMetric y){
		this.yPos = y.clone();
	}

	public void setZPosition(DistanceMetric z){
		this.zPos = z.clone();
	}


	//toString and clone essentials for ECEF
	public String toString(){
		return "[X:" + this.xPos.toString() + "] [Y:" + this.yPos.toString() + "] [Z:" + this.zPos.toString() +"]";
	}

	public ECEF clone(){
		return new ECEF(this.xPos, this.yPos, this.zPos);
	}

}