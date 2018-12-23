package GIS;


import Coords.MyCoords;
import Geom.Point3D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * this class represent convertions of coordinated to pixels and in the oppistie pixels to coordinates
 * parts of the functions getting of coordinate or pixel only and part of them with the width and height of the map
 *
 * @authors shai and sela
 */
public class Map
{



    Image map;            //image
    Point3D max,min;      //coordinates in degrees
    private Pixel Pmax,Pmin; //pixels

    /**
     * construct of path of the map and points of max and min of degrees.
     * in the constructs we get the max pixel and min pixel(0,0) from the image
     * @param max of coordinate in degree in axis of x and y(in right dowen of the map)
     * @param min of coordinate in degree in axis of x and y(in left up of the map)
     * @param img is the path string that we get in the function
     */
    public Map(Point3D max , Point3D min, String img)
    {
        this.max = new Point3D(max);
        this.min = new Point3D(min);
        this.Pmax = new Pixel();
        this.Pmin = new Pixel();

        try
        {
            map = ImageIO.read(new File(img));
            this.Pmax.setX(((BufferedImage) map).getWidth());       //take the point max from the map(in pixel-width)
            this.Pmax.setY(((BufferedImage) map).getHeight());      //take the point max from the map(in pixel-height)
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        this.Pmin.setX(0);                //set point min (0,0)
        this.Pmin.setY(0);



    }

    /**
     *function that convert pixel(point of x and y) to coordinate
     * @param p of pixel
     * @return ThisPoint-the new point in degree
     */

    public Point3D PixelToCoordinate(Pixel p)
    {
        double MinMaxdiff_X = max.x() - min.x();
        double MinMaxdiff_Y = max.y() - min.y();

        double diffCoord_X=(MinMaxdiff_X* p.getX())/this.Pmax.getX();
        double diffCoord_Y=(MinMaxdiff_Y*p.getY())/this.Pmax.getY();

        double FullCoord_X=diffCoord_X+this.min.x();
        double FullCoord_Y=diffCoord_Y+this.min.y();

        Point3D ThisPoint=new Point3D(FullCoord_X,FullCoord_Y);
        return ThisPoint;
    }

    /**
     * function that convert pixel(point of x and y) to coordinate
     * @param p of pixel
     * @param width of the map that we get from the width of the map
     * @param height of the map that we get from the height of the map
     * @return point in degree(x,y)
     */
    public Point3D PixelToCoordinate(Pixel p, int width, int height)
    {
        double MinMaxdiff_X = max.x() - min.x();
        double MinMaxdiff_Y = max.y() - min.y();

        double diffCoord_X=(MinMaxdiff_X* p.getX())/width;
        double diffCoord_Y=(MinMaxdiff_Y*p.getY())/height;

        double FullCoord_X=diffCoord_X+this.min.x();
        double FullCoord_Y=diffCoord_Y+this.min.y();

        Point3D ThisPoint=new Point3D(FullCoord_X,FullCoord_Y);
        return ThisPoint;
    }

    /**
     *function that convert coordinate(point of x and y) to pixel
     * @param cords point in degree(x,y)
     * @return point pixel
     */

    public Pixel CoordinateToPixel(Point3D cords)
    {
        double diffx = Math.abs(cords.x() - min.x());
        double diffy = Math.abs(cords.y() - min.y());


        double MinMaxdiff_X = Math.abs(max.x() - min.x());
        double MinMaxdiff_Y = Math.abs(max.y() - min.y());


        double new_x = Math.abs((diffx * Pmax.getX()) / MinMaxdiff_X);
        double new_y = Math.abs ((diffy * Pmax.getY()) / MinMaxdiff_Y);


        new_x = Math.round(new_x);
        new_y = Math.round(new_y);

        return new Pixel((int)new_x,(int)new_y);
    }

    /**
     *function that convert coordinate(point of x and y) to pixel
     * @param cords point in degree(x,y)
     * @param width of the map that we get from the width of the map
     * @param height of the map that we get from the height of the map
     * @return pixel of x and y
     */
    public Pixel CoordinateToPixel(Point3D cords , int width, int height)
    {
        double diffx = cords.x() - min.x();
        double diffy = cords.y() - min.y();

        double MinMaxdiff_X = max.x() - min.x();
        double MinMaxdiff_Y = max.y() - min.y();

        double new_x = Math.abs((diffx * width) / MinMaxdiff_X);
        double new_y = Math.abs((diffy * height) / MinMaxdiff_Y);

        new_x = Math.round(new_x);
        new_y = Math.round(new_y);

        return new Pixel((int)new_x,(int)new_y);
    }


    /**
     * haversine formula that
     * @param p1
     * @param p2
     * @return
     */
    //https://rosettacode.org/wiki/Haversine_formula#Java.
	/*
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
	*/
    /**
     * this method return the angle between two points of pixel
     * @param p1 in pixel
     * @param p2 in pixel
     * @return the angle in radian
     */

    //https://www.igismap.com/formula-to-find-bearing-or-heading-angle-between-two-points-latitude-longitude/

    public double Azimuth(Pixel p1, Pixel p2) {

        Point3D Pcoord1= new Point3D(this.PixelToCoordinate( p1));
        Point3D Pcoord2= new Point3D(this.PixelToCoordinate( p2));

        double x=Math.cos(Pcoord2.x())*Math.sin(Pcoord2.y()-Pcoord1.y());
        double y=(Math.cos(Pcoord1.x())*Math.sin(Pcoord2.x()))
                -(Math.sin(Pcoord1.x())*Math.cos(Pcoord2.x()*Math.cos(Pcoord2.y()-Pcoord1.y())));

        double angle=Math.abs(Math.atan2(x, y));
        return angle;
    }

    public Pixel getPmax()
    {
        return this.Pmax;
    }

    /**
     * caliculate how much pixel is in meter
     * @return size of 1 pixel in meters
     */
    public double SizeOfPixleInMeter()
    {
        MyCoords c = new MyCoords();
        Point3D firstP = PixelToCoordinate(new Pixel(642 , 0));
        Point3D SecondP = PixelToCoordinate(new Pixel(642 , 1));
        return c.distance3d(firstP , SecondP);
    }
}