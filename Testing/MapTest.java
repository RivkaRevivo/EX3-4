import Factory.MapFactory;
import GIS.Map;
import GIS.Pixel;
import Geom.Point3D;
import org.testng.annotations.Test;

public class MapTest {

    @Test
    public void testMap()
    {
        //fail("Not yet implemented");
    }

    @Test
    public void testPixelToCoordinate()
    {
        /*
        Point3D max= new Point3D(220190,667650);
        Point3D min = new Point3D(219287, 668008);
        String img = "Ariel1.png";
        Map m = new Map(max,min,img);
        Pixel p = new Pixel(612,264);
        Point3D po = m.PixelToCoordinate(p);
        System.out.println("x: " + po.x() + " y: " + po.y());
        */
    }

    @Test
    public void testCoordinateToPixel()
    {

        Map m = MapFactory.ArielMap();
        Point3D po = new Point3D(32.11317315828321 , 35.21093728088261);
        Pixel p = m.CoordinateToPixel(po);
        System.out.println("x: " + p.getX() + " y:" + p.getY());


    }



    @Test
    public void testHaversine()
    {
        /*Point3D p1 = new Point3D(32.103315, 35.209039 , 670);
        Point3D p2 = new Point3D(32.106352, 35.205225, 650);
        Point3D max= new Point3D(220190,667650);
        Point3D min = new Point3D(219287, 668008);
        String img = "Ariel1.png";
        Map m = new Map(max,min,img);
        Pixel pi1 = m.CoordinateToPixel(p1);
        Pixel pi2  = m.CoordinateToPixel(p2);
        System.out.println(m.haversine(pi1,pi2));*/
    }

    @Test
    public void testAzimuth()
    {
        //fail("Not yet implemented");
    }

    @Test
    public void testSizeOfPixleInMeter()
    {
        Map m = MapFactory.ArielMap();
        System.out.println("Size Of Pixle In Meter:  "+m.SizeOfPixleInMeter());
    }
}
