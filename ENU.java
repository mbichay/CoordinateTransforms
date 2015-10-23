/*
Name: ENU.java
Description: East, North, Up is a coordinate frame which is centered
from a chosen reference point (usually, where you are looking from on the center of the earth).

It then views your coordinate from east(some metric), north(some metric), and up(some metric).
This coordinate conversion is good if you're trying to track something above your head, for instance
a airplane or satelite flying over head from your radar
*/


public class ENU implements CoordinatePosition{
	
	//East, north, up positions
	private DistanceMetric ePos;
	private DistanceMetric nPos;
	private DistanceMetric uPos;


	//Basic constructor 0,0,0 meters
	public ENU(){
		ePos = new DistanceMetric(0, DistanceMetric.Unit.METERS);
		nPos = new DistanceMetric(0, DistanceMetric.Unit.METERS);
		uPos = new DistanceMetric(0, DistanceMetric.Unit.METERS);
	}


	//Basic constructor
	public ENU(DistanceMetric e, DistanceMetric n, DistanceMetric u){
		this.ePos = e.clone();
		this.nPos = n.clone();
		this.uPos = u.clone();
	}


	//Conversion to ENU u from ECEF constructor
	//Note: requires a referecnce point
	public ENU(ECEF earthCenterFixed, LLA refPt){

		//Retrieves the ECEF coordinate frame of a reference point
		ECEF refECEF = new ECEF(refPt);

		//Formula for converting to ENU with an ECEF reference point
		//Reference point should be somewhere on the surface of the earth
		//preferably where you're standing or looking from
		this.ePos = new DistanceMetric(	-1*Math.sin(refPt.getLongitude().asDecimal()) *
										(earthCenterFixed.getXPosition().asMeters().getValue() -
										refECEF.getXPosition().asMeters().getValue()) +
										Math.cos(refPt.getLongitude().asDecimal()) *
										(earthCenterFixed.getYPosition().asMeters().getValue() - 
										refECEF.getYPosition().asMeters().getValue()),
										DistanceMetric.Unit.METERS).asUnit(earthCenterFixed.getZPosition().getUnit());

		this.nPos = new DistanceMetric(	-1*Math.sin(refPt.getLatitude().asDecimal()) *
										Math.cos(refPt.getLongitude().asDecimal()) *
										(earthCenterFixed.getXPosition().asMeters().getValue() - 
										refECEF.getXPosition().asMeters().getValue()) -
										Math.sin(refPt.getLatitude().asDecimal()) *
										Math.sin(refPt.getLongitude().asDecimal()) *
										(earthCenterFixed.getYPosition().asMeters().getValue() - 
										refECEF.getYPosition().asMeters().getValue()) +
										Math.cos(refPt.getLatitude().asDecimal()) *
										(earthCenterFixed.getZPosition().asMeters().getValue() - 
										refECEF.getZPosition().asMeters().getValue()),
										DistanceMetric.Unit.METERS).asUnit(earthCenterFixed.getZPosition().getUnit());

		this.uPos = new DistanceMetric(	Math.cos(refPt.getLatitude().asDecimal()) *
										Math.cos(refPt.getLongitude().asDecimal()) *
										(earthCenterFixed.getXPosition().asMeters().getValue() - 
										refECEF.getXPosition().asMeters().getValue()) +
										Math.cos(refPt.getLatitude().asDecimal()) *
										Math.sin(refPt.getLongitude().asDecimal()) *
										(earthCenterFixed.getYPosition().asMeters().getValue() - 
										refECEF.getYPosition().asMeters().getValue()) +
										Math.sin(refPt.getLatitude().asDecimal()) *
										(earthCenterFixed.getZPosition().asMeters().getValue() - 
										refECEF.getZPosition().asMeters().getValue()),
										DistanceMetric.Unit.METERS).asUnit(earthCenterFixed.getZPosition().getUnit());
	}


	//Calls the ENU to ECEF conversion constructor
	//Note requires a reference point
	public ECEF asECEF(LLA refPt){
		return new ECEF(this,refPt);
	}


	//Returns a copy of itself.
	public ENU asENU(){
		return clone();
	}


	//Calls the ECEF then to LLA constructor of an ENU coordinate
	//To go to LLA from ENU, you must go through ECEF first with
	//a reference point
	public LLA asLLA(LLA refPt){
		return new LLA(asECEF(refPt));
	}


	public LLA asLLA(){
		//Returns LLA using arbirtrary 45,45, Earth surface reference point
		return asLLA(new LLA(new DMS(45),new DMS(45),
							 new DistanceMetric(WGS84.EARTH_SEMIMAJOR_AXIS.getValue(), 
							 DistanceMetric.Unit.METERS)));
	}


	public ECEF asECEF(){
		//Returns ECEF using arbirtrary 45,45, Earth surface reference point
		return asECEF(new LLA(new DMS(45),new DMS(45),
							 new DistanceMetric(WGS84.EARTH_SEMIMAJOR_AXIS.getValue(), 
							 DistanceMetric.Unit.METERS)));
	}


	// getters and setters for E,N,U positions
	public void setEPosition(DistanceMetric e){
		this.ePos = e.clone();
	}

	public void setNPosition(DistanceMetric n ){
		this.nPos = n.clone();
	}

	public void setUPosition(DistanceMetric u){
		this.uPos = u.clone();
	}

	public DistanceMetric getEPosition(){
		return this.ePos.clone();
	}

	public DistanceMetric getNPosition(){
		return this.nPos.clone();
	}

	public DistanceMetric getUPosition(){
		return this.uPos.clone();
	}



	//essential clone and toString methods
	public ENU clone(){
		return new ENU(this.ePos, this.nPos, this.uPos);
	}

	public String toString(){
		return "[East:" + this.ePos.toString() + "] [North:" + this.nPos.toString() + "] [Up:" + this.uPos.toString() + "]";
	}
}