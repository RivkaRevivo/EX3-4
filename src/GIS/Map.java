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
        return null;
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

}
