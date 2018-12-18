package Algorithms;

import Coords.MyCoords;
import GIS.Fruit;
import GIS.Game;
import GIS.Pacman;
import GIS.Path;

import java.util.LinkedList;

public class ShortestPathAlgo
{
    public static LinkedList<Path> ShortestPath(Game g)
    {
        LinkedList<Path> Paths = new LinkedList<>();
        LinkedList<Fruit> FruitList = g.getFruit_listCopy();
        LinkedList<Pacman> PacmanList = g.getPacman_listCopy();

        int tempFruitPos = 0;
        int tempPacmanPos = 0;
        Path p;
        double Nearmove = Double.MAX_VALUE;
        Fruit fr = null;

        for (int i = 0; i < PacmanList.size(); i++)
        {
            p = new Path();
            /*p.add(new Fruit(PacmanList.get(i).getPosition() , -1 , 1 ));*/
            Paths.add(p);
        }


        while (!FruitList.isEmpty())
        {
            for (int i = 0; i < PacmanList.size(); i++)
            {
                for (int j = 0; j < FruitList.size(); j++)
                {
                    if (Nearmove > getmoven(PacmanList.get(i), FruitList.get(j)))
                    {
                        Nearmove = getmoven(PacmanList.get(i), FruitList.get(j));
                        fr = FruitList.get(j);
                        tempFruitPos = j;
                        tempPacmanPos = i;
                    }
                }
            }
            Paths.get(tempPacmanPos).addLast(fr);
            PacmanList.get(tempPacmanPos).setPosition(fr.getPosition());
            FruitList.remove(tempFruitPos);
            Nearmove = Double.MAX_VALUE;
        }
        return Paths;
    }

    private static double getmoven(Pacman p,Fruit f)
    {
        MyCoords c = new MyCoords();

        double distance=c.distance3d(p.getPosition(),f.getPosition());
        double move=(distance-p.getRadius())/p.getSpeed();
        return move;
    }

    public static LinkedList<Path> GetCopyPaths(LinkedList<Path> LP)
    {
        LinkedList<Path> PA = new LinkedList<>();
        for(int i=0;i<LP.size();i++){
            PA.add(LP.get(i).getCopyPath());
        }
        return PA;
    }
}