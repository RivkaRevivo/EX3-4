
package GIS;

import static org.junit.Assert.*;

import org.junit.Test;

import Geom.Point3D;

public class MapTest {

	@Test
	public void testMap() {
		fail("Not yet implemented");
	}

	@Test
	public void testPixelToCoordinate() {
		fail("Not yet implemented");
	}

	@Test
	public void testCoordinateToPixel() 
	{
	  
	        Point3D max= new Point3D(220190,667650);
	        Point3D min = new Point3D(219287, 668008);
	        System.out.println(max);
	        String img = "Ariel1.png";
	        Map m = new Map(max,min,img);
	        Pixel p = m.CoordinateToPixel(max);
	        System.out.println("x: " + p.getX() + " y:" + p.getY());
	        
	 }
	
	

	@Test
	public void testHaversine() {
		fail("Not yet implemented");
	}

	@Test
	public void testAzimuth() {
		fail("Not yet implemented");
	}

}
