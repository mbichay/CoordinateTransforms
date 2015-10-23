/*
Name: CoordinatePosition.java
Description: Tag interface for allowing the ability to group the three
conversion metrics in an array if one were to use the library for doing
any other coordinate computation.
*/


public interface CoordinatePosition{
	public LLA asLLA();
	public ECEF asECEF();
	public ENU asENU();
}