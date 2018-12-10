package GIS;

import Geom.Point3D;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;

public class Game
{
    private LinkedList<Pacman> pacman_list;
    private LinkedList<Fruit> Fruit_list;
    private Map MyMap;
    public Game(LinkedList<Pacman> pacman_list, LinkedList<Fruit> Fruit_list, Map MyMap)
    {
        this.pacman_list = pacman_list;
        this.Fruit_list = Fruit_list;
        this.MyMap = MyMap;
    }

    public Game()
    {
        this.Fruit_list = new LinkedList<>();
        this.pacman_list = new LinkedList<>();
        MyMap = null;
    }

    public Game(String path, Map MyMap)
    {
        Fruit_list = new LinkedList<>();
        pacman_list = new LinkedList<>();
        this.GameCsvReader(path);
        this.MyMap = MyMap;
    }

    /**
     *
     * @param Path
     * the pacman that added to pacman_list has no azimuth
     */
    public void GameCsvReader(String Path)
    {
        BufferedReader br = null;
        String line;
        String[] CsLine;
        Pacman p;
        Fruit f;

        try
        {
            br = new BufferedReader(new FileReader(Path));
            br.readLine();
            while ((line = br.readLine()) != null)
            {
                CsLine = line.split(",");

                if (CsLine[0].equals("P"))
                {
                    p = new Pacman(new Point3D(Double.parseDouble(CsLine[2]),Double.parseDouble(CsLine[3]) , Double.parseDouble(CsLine[4])) , new double[3] , Double.parseDouble(CsLine[5]) , Double.parseDouble(CsLine[6]), Integer.parseInt(CsLine[1]));
                    pacman_list.add(p);
                }

                else if (CsLine[0].equals("F"))
                {
                    f = new Fruit(new Point3D(Double.parseDouble(CsLine[2]),Double.parseDouble(CsLine[3]) , Double.parseDouble(CsLine[4])), Integer.parseInt(CsLine[1]) , Integer.parseInt(CsLine[5]));
                    Fruit_list.add(f);
                }

                else
                {
                    throw new Exception("wrong csv file");
                }

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void GameCsvWriter()
    {
        PrintWriter pr = null;
        StringBuilder sb;
        try
        {
            pr = new PrintWriter(new File("PacmanGame" + System.currentTimeMillis()) + ".csv");
            sb = new StringBuilder();
            sb.append("Type,");
            sb.append("id,");
            sb.append("Lat,");
            sb.append("Lon,");
            sb.append("Alt,");
            sb.append("Speed/Weight,");
            sb.append("Radius");
            sb.append("\n");

            for (Pacman p : pacman_list)
            {
                sb.append("P,");
                sb.append(p.getId() + ",");
                sb.append(p.getPosition().x() + ",");
                sb.append(p.getPosition().y() + ",");
                sb.append(p.getPosition().z() + ",");
                sb.append(p.getSpeed() + ",");
                sb.append(p.getRadius() + ",");
                sb.append("\n");
            }
            for (Fruit f : Fruit_list)
            {
                sb.append("F,");
                sb.append(f.getId() + ",");
                sb.append(f.getPosition().x() + ",");
                sb.append(f.getPosition().y() + ",");
                sb.append(f.getPosition().z() + ",");
                sb.append(f.getWeight() + ",");
                sb.append("\n");
            }

            pr.write(sb.toString());
            pr.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void AddPacman(Pacman p)
    {
        pacman_list.add(p);
    }
    public void AddFruit(Fruit f)
    {
        Fruit_list.add(f);
    }
    public Iterator<Pacman> PacmanIterator()
    {
        return pacman_list.iterator();
    }
    public Iterator<Fruit> FruitIterator()
    {
        return Fruit_list.iterator();
    }

    public Map getMyMap() {
        return MyMap;
    }

    public void setMyMap(Map myMap) {
        MyMap = myMap;
    }
}
