/*
Name: WGS84.java
Description: Static constants used in the World Geodetic System (1984)

Wikipedia: The World Geodetic System (WGS) is a standard for use in cartography,
geodesy, and navigation including by GPS. It comprises a standard coordinate system
for the Earth, a standard spheroidal reference surface (the datum or reference ellipsoid)
for raw altitude data, and a gravitational equipotential surface (the geoid) that defines the nominal sea level.

The latest revision is WGS 84 (aka WGS 1984, EPSG:4326), established in 1984 and last revised in 2004.[1]
Earlier schemes included WGS 72, WGS 66, and WGS 60. WGS 84 is the reference coordinate system used by
the Global Positioning System.
*/

import java.lang.Math;

public class WGS84{

	/*
	As we know, the earth is not perfectly round, thus the WGS84 System
	tries to solve this by giving us some constants for the measurement
	of the semimajor, semiminor, and eccentricity(how not round something is)

	Earth is wider than it is long, can you guess why? The earth spins
	on its North/South polar axis, thus centrifugal force causes the
	the Earth to take on a disk(ish) shape.
	*/
	public static final DistanceMetric EARTH_SEMIMAJOR_AXIS = new 
														DistanceMetric(6378137.0,
															DistanceMetric.Unit.METERS);


	public static final DistanceMetric EARTH_RECIPROCAL_FLATTENING = new 
														DistanceMetric(1.0/298.257223563,				
															DistanceMetric.Unit.METERS);


	public static final DistanceMetric EARTH_SEMIMINOR_AXIS = new 
														DistanceMetric(EARTH_SEMIMAJOR_AXIS.getValue()*
															(1.0-EARTH_RECIPROCAL_FLATTENING.getValue()),
															DistanceMetric.Unit.METERS);


	public static final DistanceMetric FIRST_ECCENTRICITY_SQUARED = new 
														DistanceMetric(2*EARTH_RECIPROCAL_FLATTENING.getValue()-
															Math.pow(EARTH_RECIPROCAL_FLATTENING.getValue(), 2),
															DistanceMetric.Unit.METERS);


	public static final DistanceMetric SECOND_ECCENTRICITY_SQUARED = new 
														DistanceMetric(EARTH_RECIPROCAL_FLATTENING.getValue()*
														(2-EARTH_RECIPROCAL_FLATTENING.getValue())/
														(Math.pow(1.0-EARTH_RECIPROCAL_FLATTENING.getValue(),2)),
														DistanceMetric.Unit.METERS);
}