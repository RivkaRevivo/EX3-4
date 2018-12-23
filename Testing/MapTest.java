import GIS.Map;
import GIS.Pixel;
import Geom.Point3D;
import org.testng.annotations.Test;

import static org.testng.Assert.fail;

public class MapTest {



    @Test
    public void testPixelToCoordinatePixel() {
        Point3D West_North=new Point3D(35.202574,32.106046);
        Point3D East_South=new Point3D(35.212405,32.101658);
        Map m=new Map(East_South,West_North,"Ariel1.png");
        Pixel P0=new Pixel(0,0);
        Point3D NewP0=m.PixelToCoordinate(P0);

        if(NewP0.x()!=35.202574 || NewP0.y()!=32.106046) {
            fail("Not implemented good");
        }
        //cheak in half of resolution-need to get near 716*321

        Pixel P1=new Pixel(716,321);
        Point3D NewP1=m.PixelToCoordinate(P1);
        //the range that need to be in coordinate
        if(NewP1.x()<35.207 ||NewP1.x()>35.208 || NewP1.y()<32.103 ||NewP1.y()>32.104 ) {
            fail("Not implemented good");
        }


    }

    @Test
    public void testPixelToCoordinatePixelIntInt() {
        Point3D West_North=new Point3D(35.202574,32.106046);
        Point3D East_South=new Point3D(35.212405,32.101658);
        Map m=new Map(East_South,West_North,"Ariel1.png");
        Pixel P0=new Pixel(0,0);
        Point3D NewP0=m.PixelToCoordinate(P0,1433,642);

        if(NewP0.x()!=35.202574 || NewP0.y()!=32.106046) {
            fail("Not implemented good");
        }
        //cheak in half of resolution-need to get near 716*321

        Pixel P1=new Pixel(716,321);
        Point3D NewP1=m.PixelToCoordinate(P1,1433,642);
        //the range that need to be in coordinate
        if(NewP1.x()<35.207 ||NewP1.x()>35.208 || NewP1.y()<32.103 ||NewP1.y()>32.104 ) {
            fail("Not implemented good");
        }

    }

    @Test
    public void testCoordinateToPixelPoint3D() {
        Point3D West_North=new Point3D(35.202574,32.106046);
        Point3D East_South=new Point3D(35.212405,32.101658);
        Map m=new Map(East_South,West_North,"Ariel1.png");
        Pixel NewP0=m.CoordinateToPixel(East_South);

        if(NewP0.getX()!=1433 || NewP0.getY()!=642) {
            fail("Not implemented good");
        }
        //cheak in half of resolution-need to get near 716*321
        Point3D Cheakp=new Point3D(35.207374,32.103971);
        Pixel NewP1=m.CoordinateToPixel(Cheakp);
        //the range that need to be in pixel
        if(NewP1.getX()>750 ||NewP1.getX()<650 || NewP1.getY()>350|| NewP1.getY()<250) {
            fail("Not implemented good");
        }


    }

    @Test
    public void testCoordinateToPixelPoint3DIntInt() {
        Point3D West_North=new Point3D(35.202574,32.106046);
        Point3D East_South=new Point3D(35.212405,32.101658);
        Map m=new Map(East_South,West_North,"Ariel1.png");
        Pixel NewP0=m.CoordinateToPixel(East_South,1433,642);

        if(NewP0.getX()!=1433 || NewP0.getY()!=642) {
            fail("Not implemented good");
        }
        //cheak in half of resolution-need to get near 716*321
        Point3D Cheakp=new Point3D(35.207374,32.103971);
        Pixel NewP1=m.CoordinateToPixel(Cheakp,1433,642);
        //the range that need to be in pixel
        if(NewP1.getX()>750 ||NewP1.getX()<650 || NewP1.getY()>350|| NewP1.getY()<250) {
            fail("Not implemented good");
        }


    }



    @Test
    public void testAzimuth() {
        Pixel P0=new Pixel(716,321);
        Pixel P1=new Pixel(477,214);
        Point3D West_North=new Point3D(35.202574,32.106046);
        Point3D East_South=new Point3D(35.212405,32.101658);
        Map m=new Map(East_South,West_North,"Ariel1.png");
        //this is the coordinate that converted from pixels
		/*35.20748606978367
		32.103852
		35.20584642637822
		32.10458333333333*/

        double A=m.Azimuth(P0, P1);
        //range that A need to be in angle
        if(A>2.81 || A<2.78) {
            fail("Not implemented good");
        }

    }





}