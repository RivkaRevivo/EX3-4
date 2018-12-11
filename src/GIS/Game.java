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

    /**
     * a basic Constructor
     * @param pacman_list the List of Pacmans
     * @param Fruit_list the List of Fruits
     * @param MyMap the Game Map
     */
    public Game(LinkedList<Pacman> pacman_list, LinkedList<Fruit> Fruit_list, Map MyMap)
    {
        this.pacman_list = pacman_list;
        this.Fruit_list = Fruit_list;
        this.MyMap = MyMap;
    }

    /**
     * Initialized the List of Pacmans and Fruits as empty Lists
     * and The to Null
     */
    public Game()
    {
        this.Fruit_list = new LinkedList<>();
        this.pacman_list = new LinkedList<>();
        MyMap = null;
    }

    /**
     * Initialized The Lists using a Csv File
     * @param path the Locating of the Csv File
     * @param MyMap The Game Map
     */
    public Game(String path, Map MyMap)
    {
        Fruit_list = new LinkedList<>();
        pacman_list = new LinkedList<>();
        this.GameCsvReader(path);
        this.MyMap = MyMap;
    }

    /**
     * Take the Csv file as formatted in the Assignment, and turn it to a list of Pacmans and Fruits
     * @param Path the Location of the Csv File
     * the pacman that added to pacman_list has no azimuth. the Azimuth will be added later
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

    /**
     * Turn the List of Pacmans and Fruits to a Csv file as Formatted in the Assignment
     */
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

    /**
     * Add a Pacman to the Game List
     * @param p the Pacman to add
     */
    public void AddPacman(Pacman p)
    {
        pacman_list.add(p);
    }

    /**
     * Add a Fruit to the Game List
     * @param f the Fruit to add
     */
    public void AddFruit(Fruit f)
    {
        Fruit_list.add(f);
    }

    /**
     * Return an Iterator for The List of Pacmans
     * @return The Iterator
     */
    public Iterator<Pacman> PacmanIterator()
    {
        return pacman_list.iterator();
    }

    /**
     * return an Iterator for the List of Fruits
     * @return The Iterator
     */
    public Iterator<Fruit> FruitIterator()
    {
        return Fruit_list.iterator();
    }

    /**
     * Get for The Game Map
     * @return The Game Map
     */
    public Map getMyMap()
    {
        return MyMap;
    }

    /**
     * Set for The Game Map
     * @param myMap The new Game Map
     */
    public void setMyMap(Map myMap) {
        MyMap = myMap;
    }

    /**
     * return A copy of The Fruit List
     * @return the List of Fruits
     */
    public LinkedList<Fruit> getFruit_listCopy()
    {
        return new LinkedList<>(Fruit_list);
    }

    /**
     * return A copy of The Pacman List
     * @return the List of Pacmans
     */
    public LinkedList<Pacman> getPacman_listCopy()
    {
        return new LinkedList<>(pacman_list);
    }

    public void ResetLists()
    {
        pacman_list = new LinkedList<>();
        Fruit_list = new LinkedList<>();
    }
}
