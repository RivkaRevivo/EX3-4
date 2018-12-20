package File_format;

import Algorithms.ShortestPathAlgo;
import Factory.MapFactory;
import GIS.*;
import Geom.Point3D;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;


/**
 * A Class That Convert A Gis Project in to an kml filr
 */
public class Path2Kml
{

    /**
     * turn a Path in to its kml Description
     * @param path The path needed to turn into kml
     * @return A StringBuilder contains Representation of the path in a kml format
     */
    private static StringBuilder Path2kml(Path path)
    {
        StringBuilder SB = new StringBuilder();
        SB.append("<name>Path</name>");
        SB.append("<Style id=\"getcolor\">");
        SB.append("<LineStyle>");
        SB.append("<color>" + getColor() + "</color>");
        SB.append("<width>3</width>");
        SB.append("</LineStyle>");
        SB.append("</Style>");
        SB.append("<Placemark>");
        SB.append("<styleUrl>#getcolor</styleUrl>");
        SB.append("<LineString>");
        SB.append("<coordinates>");
        for(Fruit f: path)
            SB.append(f.getPosition().y() + "," + f.getPosition().x() + "," + f.getPosition().z());
        SB.append("</coordinates>");
        SB.append("</LineString>");
        SB.append("</Placemark>");
        return SB;
    }

    /**
     * Return a random color in String aabbggrr format
     * @return random color
     */
    private static String getColor() {
        double random = Math.random();
        random = random * 6;
        String[] color = {"ff0000ff", "ff00ffff", "ffff0000", "ff00ff00", "ff800080", "ff0080ff", "ff336699", "ffff00ff"};
        return color[(int) random];
    }

    /**
     * Create a kml file named ProjectKml.km in the project folder out The gis Project and the List of PAth
     * @param gis_project The Gis Project the needed to be created into kml
     * @param LP the List of paths of Gis Project needed to be inserted into the kml file
     * @throws FileNotFoundException Thrown if file creating failed
     */
    public static void PathProject2kml(GIS_project gis_project , LinkedList<Path> LP) throws FileNotFoundException {
        StringBuilder Builder = new StringBuilder();
        Builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n");
        Builder.append("<kml>" + "\n");
        Builder.append("<Document>" + "\n");
        Builder.append("<Style id=\"pacman\">" + "\n");
        Builder.append("<IconStyle>" + "\n");
        Builder.append("<color>5014F0FF</color>");
        Builder.append("</IconStyle>");
        Builder.append("</Style>");
        Builder.append("<Style id=\"fruit\">" + "\n");
        Builder.append("<IconStyle>" + "\n");
        Builder.append("<color>501400FF</color>");
        Builder.append("</IconStyle>");
        Builder.append("</Style>");

        for (Path path: LP)
            Builder.append(Path2Kml.Path2kml(path));


        for (GIS_layer gis_layer: gis_project)
        {
            Builder.append("<TimeSpan>" + "\n");
            Builder.append("<begin>");
            Date date = new Date(gis_layer.get_Meta_data().getUTC());
            //ateFormat DF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            DateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");
            Builder.append(DF.format(date));
            Builder.append("</begin>");
            Builder.append("<end>");
            date = new Date(gis_layer.get_Meta_data().getUTC() + 3000);
            Builder.append(DF.format(date));
            Builder.append("</end>");
            Builder.append("</TimeSpan>");
            Builder.append(LayerProject2kml(gis_layer ,new StringBuilder()));
        }
        Builder.append("</Document>" + "\n");
        Builder.append("</kml>" + "\n");

        PrintWriter PW = new PrintWriter(new File("ProjecKml.kml"));
        PW.write(Builder.toString());
        PW.close();
    }
    private static StringBuilder LayerProject2kml(GIS_layer gis_layer, StringBuilder Builder)
    {
        for (GIS_element gis_element: gis_layer)
        {
            Builder.append("<Placemark>");
            Builder.append("<name>");
            Builder.append("</name>");
            Builder.append(" <TimeStamp>" + "\n");
            Date date = new Date(gis_element.getData().getUTC());
            //DateFormat DF = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            DateFormat DF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm'Z'");
            Builder.append("<when>" + DF.format(date) + "</when>" + "\n");
            Builder.append("</TimeStamp>" + "\n");
            if (gis_element.getData().get_Orientation() != null)
                Builder.append("<styleUrl>#pacman</styleUrl>");
            else
                Builder.append("<styleUrl>#fruit</styleUrl>");
            Builder.append("<description>");
            Builder.append(gis_element.getData().getDescription());
            Builder.append("Date: <b>" + DF.format(date) + "</b><br/>");
            if (gis_element.getData().get_Orientation() != null)
                Builder.append("Oriantion: <b>" + gis_element.getData().get_Orientation() + "</b>");
            Builder.append("</description>");
            Builder.append("<Point>");
            Builder.append("<coordinates>");
            Builder.append(((Point3D)gis_element.getGeom()).y() + "," + (((Point3D) gis_element.getGeom()).x()) + "," + (((Point3D) gis_element.getGeom()).z()));
            Builder.append("</coordinates>");
            Builder.append("</Point>");
            Builder.append("</Placemark>");
        }
        return Builder;
    }

    public static void main(String[] args)
    {
        Game g = new Game("PacmanGame1545219019271.csv" , MapFactory.ArielMap());
        LinkedList<Path> LP = ShortestPathAlgo.ShortestPath(g);
        GIS_project GP = ShortestPathAlgo.GetPathProject(LP , g);
        try {
            PathProject2kml(GP ,LP);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}