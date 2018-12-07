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
        //fail("Not yet implemented");
    }

    @Test
    public void testCoordinateToPixel()
    {

        Point3D max= new Point3D(220190,667650);
        Point3D min = new Point3D(219287, 668008);
        String img = "Ariel1.png";
        Map m = new Map(max,min,img);
        Pixel p = m.CoordinateToPixel(max);
        System.out.println("Max: x: " + p.getX() + " y:" + p.getY());
        p = m.CoordinateToPixel(min);
        System.out.println("Min: x:" + p.getX() +  " y:" + p.getY());

    }



    @Test
    public void testHaversine()
    {
        //fail("Not yet implemented");
    }

    @Test
    public void testAzimuth()
    {
        //fail("Not yet implemented");
    }

}
