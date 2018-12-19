import Algorithms.ShortestPathAlgo;
import Factory.MapFactory;
import GIS.GIS_project;
import GIS.Game;
import GIS.Path;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

class ShortestPathAlgoTest {

    @Test
    void shortestPath()
    {
       /* Map m = MapFactory.ArielMap();
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
        }*/
    }

    @Test
    void getmoven()
    {

    }

    @Test
    void  getGetPathProject()
    {
        Game g = new Game("C:\\Users\\Owner\\EX3-4\\PacmanGame1545219019271.csv" , MapFactory.ArielMap());
        LinkedList<Path> LP = ShortestPathAlgo.ShortestPath(g);
        GIS_project pro = ShortestPathAlgo.GetPathProject(LP ,g);
        System.out.println("FFF");
    }
}