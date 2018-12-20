package Factory;

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
}