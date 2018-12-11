package Algorithms;

import Coords.MyCoords;
import GIS.Fruit;
import GIS.Game;
import GIS.Pacman;
import GIS.Path;

import java.util.LinkedList;

public class ShortestPathAlgo
{
    public static LinkedList<Path> ShortestPath(Game g) {
        LinkedList<Path> Paths = new LinkedList<>();
        LinkedList<Fruit> FruitList = g.getFruit_listCopy();
        LinkedList<Pacman> PacmanList = g.getPacman_listCopy();

        int temp = 0;
        double Nearmove = 0;
        Fruit fr = null;

        for (int i = 0; i < PacmanList.size(); i++)
        {
            Paths.add(new Path());
        }


        while (!FruitList.isEmpty()) {
            for (int i = 0; i < PacmanList.size(); i++) {
                for (int j = 0; j < FruitList.size(); j++) {
                    if (Nearmove < getmoven(PacmanList.get(i), FruitList.get(j)))
                    {
                        Nearmove = getmoven(PacmanList.get(i), FruitList.get(j));
                        fr = FruitList.get(j);
                        temp = j;
                    }
                }
                Paths.get(i).add(fr);
                FruitList.remove(temp);
                Nearmove = 0;
            }
        }
        return Paths;
    }

    public static double getmoven(Pacman p,Fruit f)
    {
        MyCoords  c=new MyCoords();

        double distance=c.distance3d(p.getPosition(),f.getPosition());
        double move=(distance-p.getRadius())/p.getSpeed();
        return move;
    }

}
