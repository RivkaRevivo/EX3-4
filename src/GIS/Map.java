package GIS;


import Geom.Point3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Map
{

	Image map;
	Point3D max,min;
	Pixel Pmax,Pmin;

	public Map(Point3D max , Point3D min, String img)
	{
		this.max = new Point3D(max);
		this.min = new Point3D(min);
		this.Pmax = new Pixel();
		this.Pmin = new Pixel();

		try
		{
			map = ImageIO.read(new File(img));
			this.Pmax.setX(((BufferedImage) map).getWidth());
			this.Pmax.setY(((BufferedImage) map).getHeight());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		this.Pmin.setX(0);
		this.Pmin.setY(0);



	}

	public Point3D PixelToCoordinate(Pixel p)
	{
		int x;
		double MinMaxdiff_X = max.x() - min.x();
		double MinMaxdiff_Y = max.y() - min.y();

		double diffCoord_X=(MinMaxdiff_X* p.getX())/this.Pmax.getX();
		double diffCoord_Y=(MinMaxdiff_Y*p.getY())/this.Pmax.getY();

		double FullCoord_X=diffCoord_X+this.min.x();
		double FullCoord_Y=diffCoord_Y+this.min.y();

		Point3D ThisPoint=new Point3D(FullCoord_X,FullCoord_Y);
		return ThisPoint;
	}

	public Pixel CoordinateToPixel(Point3D cords)
	{
		double diffx = cords.x() - min.x();
		double diffy = cords.y() - min.y();

		double MinMaxdiff_X = max.x() - min.x();
		double MinMaxdiff_Y = max.y() - min.y();

		double new_x = (diffx * Pmax.getX()) / MinMaxdiff_X;
		double new_y = (diffy * Pmax.getY()) / MinMaxdiff_Y;

		return new Pixel(new_x,new_y);

	}

	public Pixel CoordinateToPixel(Point3D cords , int width, int height)
	{
        double diffx = cords.x() - min.x();
        double diffy = cords.y() - min.y();

        double MinMaxdiff_X = max.x() - min.x();
        double MinMaxdiff_Y = max.y() - min.y();

        double new_x = (diffx * width) / MinMaxdiff_X;
        double new_y = (diffy * height) / MinMaxdiff_Y;

        return new Pixel(new_x,new_y);
	}



	//https://rosettacode.org/wiki/Haversine_formula#Java.
	public double haversine(Pixel p1, Pixel p2) {

		Point3D Pcoord1= new Point3D(this.PixelToCoordinate( p1));
		Point3D Pcoord2= new Point3D(this.PixelToCoordinate( p2));

		double R = 6372800;      //Earth

		double dLat = Math.toRadians(Pcoord2.x() - Pcoord1.x());
		double dLon = Math.toRadians(Pcoord2.y() - Pcoord1.y());
		double  lat1 = Math.toRadians(Pcoord1.x());
		double lat2 = Math.toRadians(Pcoord2.x());

		double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
		double c = 2 * Math.asin(Math.sqrt(a));
		return R * c;
	}

	//https://www.igismap.com/formula-to-find-bearing-or-heading-angle-between-two-points-latitude-longitude/

	public double Azimuth(Pixel p1, Pixel p2) {

		Point3D Pcoord1= new Point3D(this.PixelToCoordinate( p1));
		Point3D Pcoord2= new Point3D(this.PixelToCoordinate( p2));

		double x=Math.cos(Pcoord2.x())*Math.sin(Pcoord2.y()-Pcoord1.y());
		double y=(Math.cos(Pcoord1.x())*Math.sin(Pcoord2.x()))
				-(Math.sin(Pcoord1.x())*Math.cos(Pcoord2.x()*Math.cos(Pcoord2.y()-Pcoord1.y())));

		double angle=Math.atan2(x, y);
		return angle;



	}
}

	