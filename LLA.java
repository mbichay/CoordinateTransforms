/*
Name: LLA.java
Description:Longitude,Latitude,ALtitude coordinate frame (this assumes one is using the WGS84 standard)

It then views your coordinate from east(some metric), north(some metric), and up(some metric).
This coordinate conversion is good if you're trying to track something above your head, for instance
a airplane or satelite flying over head from your radar
*/

public class LLA implements CoordinatePosition{

	//Degrees for Latitude, Longitude
	private DMS lat;
	private DMS lon;

	//Distance metric for altitude
	private DistanceMetric alt;


	//Basic constructor 0,0,0
	public LLA(){
		this.lat = new DMS();
		this.lon = new DMS();
		this.alt = new DistanceMetric();
	}


	//Basic constructor
	public LLA(DMS lat, DMS lon, DistanceMetric alt){
		this.lat = lat.clone();
		this.lon = lon.clone();
		this.alt = alt.clone();
	}


	//Basic constructor with conversions from Radians to DMS
	public LLA(Radians lat, Radians lon, DistanceMetric alt){
		this.lat = lat.asDMS();
		this.lon = lon.asDMS();
		this.alt = alt.clone();
	}


	//Conversion from ecef to LLA, this will be used for converting from ENU
	//In the future as well.
	public LLA(ECEF earthCenterFixed){

		//This is the 15 step formula for going FROM ECEF back into a
		//WGS84 Standard Lat / Lon / Altitude coordinate system.

		//Note: Every time you go from ECEF to LLA and backwards, you loose some form of
		//precision. Generally somewhere in the billionth of a decimal. There is a better
		//way to convert from ECEF to LLA but that formula is more intensive and usually
		//reserved for the military or expensive equipement/satellites.
		double xPos = earthCenterFixed.getXPosition().asMeters().getValue();
		double yPos = earthCenterFixed.getYPosition().asMeters().getValue();
		double zPos = earthCenterFixed.getZPosition().asMeters().getValue();

		double asq = Math.pow(WGS84.EARTH_SEMIMAJOR_AXIS.getValue(),2);
		double bsq = Math.pow(WGS84.EARTH_SEMIMINOR_AXIS.getValue(),2);

		double ep = Math.sqrt((asq-bsq)/bsq);

		double p = Math.sqrt(Math.pow(xPos,2) + Math.pow(yPos,2));

		double th = Math.atan2(WGS84.EARTH_SEMIMAJOR_AXIS.getValue() *
								zPos, WGS84.EARTH_SEMIMINOR_AXIS.getValue() * p);

		double longitude = Math.atan2(yPos,xPos);

		double latitude = Math.atan2((zPos + Math.pow(ep,2) *
								WGS84.EARTH_SEMIMINOR_AXIS.getValue() * Math.pow(Math.sin(th),3)), 
								(p - WGS84.FIRST_ECCENTRICITY_SQUARED.getValue()*
								WGS84.EARTH_SEMIMAJOR_AXIS.getValue()*
								Math.pow(Math.cos(th),3)));

		double n = WGS84.EARTH_SEMIMAJOR_AXIS.getValue()/ 
								(Math.sqrt(1-WGS84.FIRST_ECCENTRICITY_SQUARED.getValue()*
								Math.pow(Math.sin(latitude),2)));


		double altitude = p / Math.cos(latitude) - n;
		longitude = longitude % (2*Math.PI);

		//Preserves the units which it was created in.
		this.lat = new DMS(latitude * 180 / Math.PI);
		this.lon = new DMS(longitude * 180 / Math.PI);
		this.alt = new DistanceMetric(altitude, DistanceMetric.Unit.METERS).asUnit(earthCenterFixed.getZPosition().getUnit());
	}



	//Getter / setter for latitude, longitude, and altitude components
	public DMS getLongitude(){
		return lon.clone();
	}

	public DMS getLatitude(){
		return lat.clone();
	}

	public void setLongitude(Radians lon){
		this.lon = lon.asDMS();
	}

	public void setLongitude(DMS lon){
		this.lon = lon.clone();
	}

	public void setLatitude(Radians lat){
		this.lat = lat.asDMS();
	}

	public void setLatitude(DMS lat){
		this.lat = lat.clone();
	}

	public DistanceMetric getAltitude(){
		return this.alt.clone();
	}

	public void setAltitude(DistanceMetric alt){
		this.alt = alt.clone();
	}



	//Calls the LLA conversion constructor in ECEF
	//returns a ECEF value
	public ECEF asECEF(){
		return new ECEF(this);
	}


	//Returns a ENU conversion, goes through the ECEF conversion
	//Note requires a reference point.
	public ENU asENU(LLA refPt){
		return new ENU(asECEF(), refPt);
	}


	//returns a copy of itself
	public LLA asLLA(){
		return this.clone();
	}


	public ENU asENU(){
		//Returns a ENU from an arbitrary LLA of 45,45,Earth Surface.
		return asENU(new LLA(new DMS(45),new DMS(45),
							 new DistanceMetric(WGS84.EARTH_SEMIMAJOR_AXIS.getValue(), 
							 DistanceMetric.Unit.METERS)));
	}


	//Essential clone and toString method for LLA
	public LLA clone(){
		return new LLA(this.lat, this.lon, this.alt);
	}

	public String toString(){
		return "[Lat:" + this.lat.toString() + "] [Lon:" + this.lon.toString() + "] [Alt:" + this.alt.toString() + "]";
	}

}