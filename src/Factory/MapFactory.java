package Factory;

import Coords.LatLonAlt;
import GIS.Map;
import Geom.Point3D;


/**
 * A Factory Class Designed to sent Different Maps In to The Game
 */
public class MapFactory
{
    /**
     * Return The Basic Map We Were Given in the Assignment
     * @return Map Of Ariel
     */
    public static Map ArielMap()
    {
        return new Map(new Point3D( 32.106046, 35.202574) , new Point3D(32.101858,   35.212405) , "Ariel1.png");
    }

    public static Coords.Map BoazArielMap()
    {
        return new Coords.Map(new LatLonAlt(32.103813D,35.207357D,0.0D) , 955.5D , 421.0D , "Ariel1.png");
    }
}