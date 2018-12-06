package GIS;

import Geom.Point3D;

import static org.junit.jupiter.api.Assertions.*;

class MapTest {

    @org.junit.jupiter.api.Test
    void pixelToCoordinate()
    {
    }

    @org.junit.jupiter.api.Test
    void coordinateToPixel()
    {
        Point3D max= new Point3D(220190,667650);
        Point3D min = new Point3D(219287, 668008);
        String img = "Ariel1.png";
        Map m = new Map(max,min,img);
        Pixel p = m.CoordinateToPixel(max);
        System.out.println("x: " + p.getX() + " y:" + p.getY());
    }
}