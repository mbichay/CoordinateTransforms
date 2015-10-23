/*
Name: CoordinateManager.java
Description: Manages the conversion functions between Coordinate frames
*/

public class CoordinateManager{

	private LLA lla;	//Latitude, Longitude, Altitude
	private ECEF ecef;	//Earth-Centered, Earth-Fixed
	private ENU enu;	//East, North, Up
	private LLA refPt;	//Reference Point for ENU Frame shifts


	public CoordinateManager(){
		//Allocate Coordinate datums up-front
		lla = new LLA();
		ecef = new ECEF();
		enu = new ENU();
		refPt = new LLA();
	}


	//Returns the LLA toString method of newly input Lat, Lon, Alt doubles
	public String lla(Double[] llaInput){
		lla.setLatitude(new DMS(llaInput[0]));
		lla.setLongitude(new DMS(llaInput[1]));
		lla.setAltitude(new DistanceMetric(llaInput[2], DistanceMetric.Unit.METERS));
		return lla.toString();
	}


	//Returns the ECEF toString method of newly input X, Y, Z doubles
	public String ecef(Double[] ecefInput){
		ecef.setXPosition(new DistanceMetric(ecefInput[0], DistanceMetric.Unit.METERS));
		ecef.setYPosition(new DistanceMetric(ecefInput[1], DistanceMetric.Unit.METERS));
		ecef.setZPosition(new DistanceMetric(ecefInput[2], DistanceMetric.Unit.METERS));
		return ecef.toString();
	}


	//Returns the ENU toString method of newly input East, North, Up doubles
	public String enu(Double[] enuInput){
		enu.setEPosition(new DistanceMetric(enuInput[0], DistanceMetric.Unit.METERS));
		enu.setNPosition(new DistanceMetric(enuInput[1], DistanceMetric.Unit.METERS));
		enu.setUPosition(new DistanceMetric(enuInput[2], DistanceMetric.Unit.METERS));
		return ecef.toString();
	}


	//Returns the LLA toString method of newly input Lat, Lon, Alt doubles
	//East, North, Up translations require a reference point, typically
	//On the surface of the Earth (Ideally, where you're standing)
	public String refPt(Double[] refInput){
		refPt.setLatitude(new DMS(refInput[0]));
		refPt.setLongitude(new DMS(refInput[1]));
		refPt.setAltitude(new DistanceMetric(refInput[2], DistanceMetric.Unit.METERS));
		return refPt.toString();
	}


	//Returns the ECEF toString method after converting from LLA
	public String lla2ecef(Double[] llaInput){
		lla(llaInput);
		return lla.asECEF().toString();
	}


	//Returns the ENU toString method after converting from LLA
	//Note: Requires a reference point.
	public String lla2enu(Double[] llaInput, Double[] refInput){
		lla(llaInput);
		refPt(refInput);
		return lla.asENU(refPt).toString();
	}


	//Returns the LLA toString method after converting from ECEF
	public String ecef2lla(Double[] ecefInput){
		ecef(ecefInput);
		return ecef.asLLA().toString();
	}


	//Returns the ENU toString method after converting from ECEF
	//Note: Requires a reference point.
	public String ecef2enu(Double[] ecefInput, Double[] refInput){
		ecef(ecefInput);
		refPt(refInput);
		return ecef.asENU(refPt).toString();
	}


	//Returns the LLA toString method after converting from ENU
	//Note: Requires a reference point.
	public String enu2lla(Double[] enuInput, Double[] refInput){
		enu(enuInput);
		refPt(refInput);
		return enu.asLLA(refPt).toString();
	}


	//Returns the ecef toString method after converting from ENU
	//Note: Requires a reference point.
	public String enu2ecef(Double[] enuInput, Double[] refInput){
		enu(enuInput);
		refPt(refInput);
		return enu.asECEF(refPt).toString();
	}
}