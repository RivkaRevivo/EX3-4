package Algorithms;

import Coords.MyCoords;
import GIS.*;
import Geom.Geom_element;
import Geom.Point3D;

import java.util.LinkedList;

public class ShortestPathAlgo {
    public static LinkedList<Path> ShortestPath(Game g) {
        LinkedList<Path> Paths = new LinkedList<>();
        LinkedList<Fruit> FruitList = g.getFruit_listCopy();
        LinkedList<Pacman> PacmanList = g.getPacman_listCopy();

        int tempFruitPos = 0;
        int tempPacmanPos = 0;
        Path p;
        double Nearmove = Double.MAX_VALUE;
        Fruit fr = null;

        for (int i = 0; i < PacmanList.size(); i++) {
            p = new Path();
            p.add(new Fruit(PacmanList.get(i).getPosition(), -1, 1));
            Paths.add(p);
        }

/*
        while (!FruitList.isEmpty()) {
            for (int i = 0; i < PacmanList.size(); i++) {
                for (int j = 0; j < FruitList.size(); j++) {
                    if (Nearmove > getmoven(PacmanList.get(i), FruitList.get(j))) {
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

*/

//	the method 1 sela


        double[] Sum_Move_Pacman = new double[PacmanList.size()];
        while (!FruitList.isEmpty()) {
            for (int i = 0; i < PacmanList.size(); i++) {
                for (int j = 0; j < FruitList.size(); j++) {
                    if (Nearmove > getmoven(PacmanList.get(i), FruitList.get(j))) {

                        if (getmoven(PacmanList.get(tempPacmanPos), FruitList.get(j)) < getmoven(PacmanList.get(i), FruitList.get(j))) {
                            if (Sum_Move_Pacman[tempPacmanPos] + getmoven(PacmanList.get(tempPacmanPos), FruitList.get(j))<= Sum_Move_Pacman[i] + getmoven(PacmanList.get(i), FruitList.get(j))) {
                                Nearmove = getmoven(PacmanList.get(tempPacmanPos), FruitList.get(j));
                                fr = FruitList.get(j);
                                tempFruitPos = j;
                                //tempPacmanPos = tempPacmanPos;

                            } else {
                                Nearmove = getmoven(PacmanList.get(i), FruitList.get(j));
                                fr = FruitList.get(j);
                                tempFruitPos = j;
                                tempPacmanPos = i;

                            }
                        } else {
                            if (Sum_Move_Pacman[tempPacmanPos] + getmoven(PacmanList.get(tempPacmanPos), FruitList.get(j)) < Sum_Move_Pacman[i] + getmoven(PacmanList.get(i), FruitList.get(j))) {
                                Nearmove = getmoven(PacmanList.get(tempPacmanPos), FruitList.get(j));
                                fr = FruitList.get(j);
                                tempFruitPos = j;
                                //tempPacmanPos = i;


                            } else {
                                Nearmove = getmoven(PacmanList.get(i), FruitList.get(j));
                                fr = FruitList.get(j);
                                tempFruitPos = j;
                                tempPacmanPos = i;

                            }
                        }

                    }
                }
            }
            Sum_Move_Pacman[tempPacmanPos] = Sum_Move_Pacman[tempPacmanPos] + Nearmove;
            Paths.get(tempPacmanPos).addLast(fr);
            PacmanList.get(tempPacmanPos).setPosition(fr.getPosition());
            FruitList.remove(tempFruitPos);
            Nearmove = Double.MAX_VALUE;

        }

        return Paths;
    }





/*
//the method 2 shai


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
                if(FruitList.size()!=0) {
                    Paths.get(tempPacmanPos).addLast(fr);
                    PacmanList.get(tempPacmanPos).setPosition(fr.getPosition());
                    FruitList.remove(tempFruitPos);
                    Nearmove = Double.MAX_VALUE;
                }
            }


        }

        return Paths;

    }




*/





    private static double getmoven(Pacman p, Fruit f) {
        MyCoords c = new MyCoords();

        double distance = c.distance3d(p.getPosition(), f.getPosition());
        double move = (distance - p.getRadius()) / p.getSpeed();
        return move;
    }

    public static LinkedList<Path> GetCopyPaths(LinkedList<Path> LP) {
        LinkedList<Path> PA = new LinkedList<>();
        for (int i = 0; i < LP.size(); i++) {
            PA.add(LP.get(i).getCopyPath());
        }
        return PA;
    }

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

    /*private static Path GetLongestPath(LinkedList<Path> lp)
    {
        Path Max = lp.get(0);
        for (Path p : lp)
        {
            if(p.size() > Max.size())
                Max = p;
        }
        return Max;
    }*/

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