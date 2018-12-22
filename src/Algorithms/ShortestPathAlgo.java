package Algorithms;

import Coords.MyCoords;
import GIS.*;
import Geom.Geom_element;
import Geom.Point3D;

import java.util.LinkedList;

public class ShortestPathAlgo {

    /**
     * Calculate Path of fruit for each pacman
     * @param g The Game To Calculate on
     * @return A List of Path in the size of number of Pacman. each path correspond with its pacman in the same position in the pacman list
     */
    public static LinkedList<Path> ShortestPath(Game g) {
        LinkedList<Path> Paths = new LinkedList<>();
        LinkedList<Fruit> FruitList = g.getFruit_listCopy();
        LinkedList<Pacman> PacmanList = g.getPacman_listCopy();

        int tempFruitPos = 0;
        int tempPacmanPos = 0;
        Path p;
        double Nearmove = Double.MAX_VALUE;

        for (int i = 0; i < PacmanList.size(); i++) {
            p = new Path();
            p.add(new Fruit(PacmanList.get(i).getPosition(), -1, 1));
            Paths.add(p);
        }

        while (!FruitList.isEmpty()) {
            for (int i = 0; i < PacmanList.size(); i++) {
                for (int j = 0; j < FruitList.size(); j++) {
                    if(Nearmove>getmoven(PacmanList.get(i),FruitList.get(j))){
                        Nearmove=getmoven(PacmanList.get(i),FruitList.get(j));
                        tempFruitPos=j;
                        tempPacmanPos=i;
                    }
                }

                if(!FruitList.isEmpty())
                {
                    Paths.get(tempPacmanPos).addLast(FruitList.get(tempFruitPos));
                    PacmanList.get(tempPacmanPos).setPosition(FruitList.get(tempFruitPos).getPosition());
                    FruitList.remove(tempFruitPos);
                    Nearmove = Double.MAX_VALUE;

                }

            }

        }
        return Paths;
    }

    /**
     * calculate the time for the pacman to reach the fruit
     * @param p pacman
     * @param f fruit
     * @return the time in second it take the pacman to reach the fruit
     */
    private static double getmoven(Pacman p, Fruit f) {
        MyCoords c = new MyCoords();

        double distance = c.distance3d(p.getPosition(), f.getPosition());
        double move = (distance - p.getRadius()) / p.getSpeed();
        return move;
    }

    /**
     * return a deep copy of LP
     * @param LP the List needed to be copy
     * @return a new list where every item is a copy of the item in LP
     */
    public static LinkedList<Path> GetCopyPaths(LinkedList<Path> LP) {
        LinkedList<Path> PA = new LinkedList<>();
        for (int i = 0; i < LP.size(); i++) {
            PA.add(LP.get(i).getCopyPath());
        }
        return PA;
    }


    /**
     * Create a Gis Project out of List of Paths
     * @param LP the List of Path that will be created as project
     * @param g the game where the List
     * @return A Gis Project Of the List of Path
     */
    public static GIS_project GetPathProject(LinkedList<Path> LP, Game g) {
        Meta_data data = new Metadata();
        GIS_project Game_Porject = new Project(data);



        int MaxPathSize = GetLongestPathsize(LP);

        for (int i = 0; i < MaxPathSize; i++) {

            Meta_data Layer_data = new Metadata();
            GIS_layer layer = new Layer(Layer_data);

            for (int j = 0; j < g.getFruit_listCopy().size(); j++) {
                Fruit f = g.getFruit_listCopy().get(j);
                Geom_element Geom = f.getPosition();
                Meta_data GisData = new Metadata();
                GIS_element FruitEl = new Element(Geom, GisData);
                layer.add(FruitEl);
            }
            if (i == 0) {
                for (int j = 0; j < (g.getPacman_listCopy().size()); j++) {
                    Pacman p = g.getPacman_listCopy().get(j);
                    Geom_element Geom = p.getPosition();
                    Meta_data GisData = new Metadata(LP.get(i).get(0).getPosition());
                    GIS_element PacEl = new Element(Geom, GisData);
                    layer.add(PacEl);

                }

            }
            if (i != 0) {
                for (Path currnetPath : LP) {
                    Fruit f = currnetPath.get(i - 1);
                    Geom_element geom_element = f.getPosition();
                    Point3D Ori = currnetPath.get(i).getPosition();
                    data = new Metadata(Ori);
                    GIS_element gis_element = new Element(geom_element, data);
                    layer.add(gis_element);
                }
            }
            Game_Porject.add(layer);
        }

        return Game_Porject;
    }

    /**
     * return The Largest Path in a List of Path based on number of items in each path
     * @param lp The List of Path
     * @return The Path with the most fruits
     */
    private static int GetLongestPathsize(LinkedList<Path> lp)
    {
        int Max = -1;
        for (Path p : lp)
        {
            if(p.size() > Max)
                Max = p.size();
        }
        return Max;
    }

    /**
     * Calculate the Number of second needed the finish the game, based on the longest path and the speed of the pacmans
     * @param lp the Linked List of paths. path to each pacman
     * @param game the game where LP is played on
     * @return the time it will take for the game to finish in the real world.
     */
    public static int GetGameTime(LinkedList<Path> lp , Game game)
    {
        Path Max = lp.get(0);
        Pacman pacman = new Pacman(game.getPacman_listCopy().get(0));
        for (int i = 0; i < lp.size(); i++)
        {
            if(lp.get(i).size() > Max.size()) {
                Max = lp.get(i);
                pacman = new Pacman(game.getPacman_listCopy().get(i));
            }
        }
        int GameTime = 0;
        GameTime += getmoven(pacman, Max.get(0));
        pacman.setPosition(Max.get(0).getPosition());
        for (int i = 0; i < Max.size() - 1; i++)
        {
            GameTime += getmoven(pacman , Max.get(i+1));
        }

        return GameTime;
    }

}