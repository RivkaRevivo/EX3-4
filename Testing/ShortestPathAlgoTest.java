import Algorithms.ShortestPathAlgo;
import Factory.MapFactory;
import GIS.*;
import Geom.Point3D;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.LinkedList;

class ShortestPathAlgoTest {

    @Test
    void shortestPath()
    {
        Map m = MapFactory.ArielMap();
        LinkedList<Fruit> FruitList = new LinkedList<>();
        LinkedList<Pacman> PacmanList = new LinkedList<>();
        Game g = new Game(PacmanList , FruitList , m);
        Point3D pos = m.PixelToCoordinate(new Pixel(0,0));
        PacmanList.add(new Pacman(pos,null,1,1,0));
        pos = m.PixelToCoordinate(new Pixel(m.getPmax().getX() / 4, m.getPmax().getY() / 4));
        FruitList.addLast(new Fruit(pos, 0 , 1));
        pos = m.PixelToCoordinate(new Pixel(m.getPmax().getX() / 2, m.getPmax().getY() / 2));
        FruitList.addLast(new Fruit(pos, 1 , 1));
        pos = m.PixelToCoordinate(new Pixel(m.getPmax().getX() * 3/4, m.getPmax().getY() * 3/4));
        FruitList.addLast(new Fruit(pos , 2, 1));
        pos = m.PixelToCoordinate(new Pixel(m.getPmax().getX() , m.getPmax().getY()));
        FruitList.addLast(new Fruit(pos , 2 , 1 ));
        LinkedList<Path> p = ShortestPathAlgo.ShortestPath(g);
        Path f = p.getFirst();
        Iterator<Fruit> IF = f.iterator();
        Fruit fruit;
        Pixel Ppos;
        while (IF.hasNext())
        {
            fruit = IF.next();
            Ppos = m.CoordinateToPixel(fruit.getPosition());
            System.out.println("x: " + Ppos.getX() + "  Y: " + Ppos.getY());
        }
    }

    @Test
    void getmoven()
    {

    }
}