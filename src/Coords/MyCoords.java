package Coords;

import GIS.Pacman;
import Geom.Point3D;

public class MyCoords {
	/**
	 * This interface represents a basic coordinate system converter, including: 1.
	 * The 3D vector between two lat,lon, alt points 2. Adding a 3D vector in meters
	 * to a global point. 3. convert a 3D vector from meters to polar coordinates
	 * 
	 * @author Boaz Ben-Moshe
	 *
	 */
	final double The_earth_rad = 6378000;
	/**
	 * computes a new point which is the GPS point transformed by a 3D vector (in meters)
	 * i got the information about earth radios from this site>>
	 * https://en.wikipedia.org/wiki/Earth_radius.
	 */

	public Point3D add(Point3D gps, Point3D local_vector_in_meter) {
		double r = local_vector_in_meter.x();
		double phi = local_vector_in_meter.y();
		double tetha = local_vector_in_meter.z();
		double x =  r*Math.sin(tetha)*Math.cos(phi);                                               ///ask
		double y = r*Math.sin(tetha)*Math.sin(phi);
		double z = r*Math.cos(tetha);
		/**
		 * here we creating new point for adding our vectors.
		 */
		Point3D new_Point = new Point3D(gps.x()+x, gps.y()+y, gps.z()+z);

		return new_Point;

	}

	/*public Point3D add2D(Point3D gps, Point3D local_vector_in_meter)
	{
		double r = local_vector_in_meter.x();
		double phi = local_vector_in_meter.y();
		double x =  r*Math.cos(phi);                                               ///ask
		double y = r*Math.sin(phi);

		Point3D new_Point = new Point3D(gps.x()+x, gps.y()+y);

		return new_Point;
	}*/

	 /*
	 * The "Haversine formula" to calculate distance between two points on a sphere.
	 * https://en.wikipedia.org/wiki/Haversine_formula
	 *
	 */
	public double distance3d(Point3D gps0, Point3D gps1) {
		
		
		double longnorm=Math.cos(radian(gps0.x()));
		double pointox=Math.sin((radian(gps1.x())-radian(gps0.x())))*The_earth_rad ;
		double pointoy=Math.sin((radian(gps1.y())-radian(gps0.y())))*The_earth_rad *longnorm;
		double pointoz=gps1.z()-gps0.z();
		
		return Math.sqrt(pointox*pointox+pointoy*pointoy+pointoz*pointoz);
	
		
		

		
		
		/*
		double latitude_1 = gps0.x();//the latitude for x in point 0
		double  longitudes_1 = gps0.y();//the  longitudes for y in point 0
		
		double latitude_2 = gps1.x();//the lattitude for x in point 1
		double longitudes_2 = gps1.y();//the  longitudes for y in point 1
		
		double a = 0, c = 0, distance=0;                                                           ///good
		double R_lat1 = radian(latitude_1);
		double R_lat2 = radian(latitude_2);
		double diff1 = latitude_1-latitude_2;
		double diff2 = longitudes_2-longitudes_1;
		
		a = Math.pow(Math.sin(radian(diff1)/2), 2) + Math.cos(R_lat1)*Math.cos(R_lat2)*Math.pow(Math.sin(radian(diff2)/2), 2);
		c = 2*Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		distance = The_earth_rad*c;//the distance we got from Haversine_formula
				
		return distance;
		*/
	}

	
	

	/**
	 * computes the 3D vector (in meters) between two gps like points
	 */
	public Point3D vector3D(Point3D gps0, Point3D gps1) {
		double longnorm=Math.cos(radian(gps0.x()));
		double pointox=Math.sin((radian(gps1.x())-radian(gps0.x())))*The_earth_rad;
		double pointoy=Math.sin((radian(gps1.y())-radian(gps0.y())))*The_earth_rad*longnorm;
		double pointoz=gps1.z()-gps0.z();
		
		Point3D vectormeter=new Point3D(pointox,pointoy,pointoz);
		return vectormeter;
		/*
		double b        = The_earth_rad + gps1.z();
	    double c        = The_earth_rad + gps0.z();
	    double b2       = b*b;
	    double c2       = c*c;
	    double bc2      = 2*b*c;
	    double alpha    = gps1.y() - gps0.y();
	    double R_alpha = radian(alpha);
	    double cos      = 1 - Math.pow(R_alpha, 2)/2; //Math.cos(alpha);
	    // Use the law of cosines / Al Kashi theorem
	    double x        = Math.sqrt(b2 + c2 - bc2*cos);
	    double delta = gps1.x() - gps0.x();
	    double R_delta = radian(delta);
	    cos = 1 - Math.pow(R_delta, 2)/2; //Math.cos(alpha);
	    double y   = Math.sqrt(b2 + c2 - bc2*cos);

	    // Obtain vertical difference, too
	    double z   = gps1.z() - gps0.z();

	    return new Point3D(x, y, z);
	    */
	}
	

	/**
	 * computes the polar representation of the 3D vector be gps0-->gps1 Note: this
	 * method should return an azimuth (aka yaw), elevation (pitch), and distance
	 * some info about this >> 
	 * http://tchester.org/sgm/analysis/peaks/how_to_get_view_params.html?fbclid=IwAR3y8HcMQrCSTy5AWa3--_PzH-Fw6wYkLQoYdz5ERDhLaKa63yCMRNMgVt4.
	 */
	public double[] azimuth_elevation_dist(Point3D gps0, Point3D gps1) {
		//at first we change them to radian.
		double longitudes_gps1 = radian(gps0.y()); 
		double longitudes_gps2 = radian(gps1.y()); 
		double latgps1 = radian(gps0.x()); 
		double latgps2 = radian(gps1.x()); 
		double alpha = longitudes_gps2 - longitudes_gps1;                                                             ///ask
		double left = Math.sin(alpha)*Math.cos(latgps2);
		double right = Math.cos(latgps1)*Math.sin(latgps2)-Math.sin(latgps1)*Math.cos(latgps2)*Math.cos(alpha);
		double azimut = Math.atan2(left, right);
		//distance//
		double distance = distance3d(gps0,gps1);
		//elevation//
		azimut = degree(azimut);
		if(azimut<0) azimut+=360;
		if(azimut>360) azimut-=360;
		double high = gps1.z() - gps0.z();
		double lambda=(double)Math.asin(high/distance);//lambda = (180/pi) * [ (elev2 - elev1) / d - d / (2*R) ];
		double eleveation = degree(lambda);
	////////////////the result////////////////////////////////
		double arr[] = {azimut,eleveation,distance};
		return arr;

	}
/**
 * 
 * @param x our value that we want to convert.
 * @return the converted value.
 */
	private double radian(double x) {                                    ///good
		return x*(Math.PI)/180;
	}
	/** 
	 * @param x our value that we want to convert.
     * @return the converted value.
	 */
	private double degree(double x) {                                   ///good
		return x/(Math.PI)*180;
	}
	

	/**
	 * return true iff this point is a valid lat, lon , lat coordinate:
	 * [-180,+180],[-90,+90],[-450, +inf]
	 * 
	 * @param p
	 * @return
	 */
	public boolean isValid_GPS_Point(Point3D p) {
		if (p.x() > -180 || p.x() < 180 || p.y() > -90 || p.y() < 90 || p.z() > -450)                 ///ask
			return true;
		else
			return false;

	}

	public double CalcAzimute(Point3D first , Point3D second)
	{
		double Delta_Y=first.y()-second.y();
		double Delta_X=first.x()-second.x();
		double a=Math.atan(Math.abs(Delta_Y/Delta_X));
		if(Delta_X >= 0 && Delta_Y >= 0)
		{
			a=a;

		}
		else if(Delta_X< 0&& Delta_Y >= 0){
			a=Math.PI-a;
		}
		else if(Delta_X< 0&& Delta_Y < 0){
			a=Math.PI+a;
		}
		else if(Delta_X>= 0&& Delta_Y < 0){
			a=2*Math.PI+a;
		}
		return a;
	}

	public Point3D AddToPointAzimutDist(Point3D p , double azimut , double dist)
	{
		double e = dist * Math.sin(azimut);
		Point3D p0 = new Point3D(p);
		p0.add(new Point3D(dist , e));
		return p0;
	}

	private double calVLat(Pacman p , double azimut)
	{
		double speed = p.getSpeed();
		return 90 < azimut && azimut < 270 ? -speed * Math.cos(azimut) : speed * Math.cos(azimut);
	}

	//private double
}