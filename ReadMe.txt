Coordinate Conversion Tool --- By Matt Bichay

This tool is a simple GUI wrapper around a more powerful LLA, ECEF, and ENU conversion
calculator. This tool assumes that the users enter Lat Lon Alt coordinates in the WGS84
standard system (Most systems are WGS84 now).

Please note that this tool is extremely basic and although it includes ECEF and ENU frames,
it only takes into account the position of the coordinate and not the actual velocity of the
coordinate. These coordinate frames become more useful after ioncorporating velocity because
one can use it to watch objects moving over time in the coordinate space.



LLA - Latitude, Longitude, Altitude:

Both latitude and longitude are stored in either degrees
or degrees, minutes, and seconds. It can also be input using
Radians.

Altitude can be any metric of unit provided in the DistanceMetric class.
Note: I only included the units which I've heard of being used, some of them
are pointless... like the femtometer.



ECEF - Earth-Centered, Earth-Fixed:

ECEF is a rotating coordinate frame of which the center mass of Earth is the
reference point of the coordinate.

It is stored in an X,Y,Z distance value, with 0,0,0 being the center of the earth
and the Z axis pointing towards the north pole. The X axis actually usally points towards
the center of England because they were the first travelers and it only seemed natural for them
to fix the X axis at the center of their home.



ENU - East, North, Up:

East, North, Up is a coordinate conversion frame of which the reference point is dynamic
and coordinates are looked at as distance metrics east,north, then up from the reference point.

ENU is good for calculating the coordinates of an object in reference from where something/someone
is looking.

How do I choose a good reference point? Z axis reference points give completely inaccurate results,
can you figure out why? Its best to choose a point on the surface of the Earth which doesn't align with
the Z axis, preferably a point of where you want to look at.