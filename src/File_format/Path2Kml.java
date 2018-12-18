package File_format;

import GIS.Fruit;
import GIS.Path;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

public class Path2Kml
{
    private static String Path2Kml(Path path)
    {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        StringBuilder date = new StringBuilder();
        for (Fruit f: path)
        {
            sb.append("<Placemark>" + "\n");
            sb.append("<name>" + i +  " Location"  + " </name>" + "\n");
            sb.append(" <TimeStamp>" + "\n");
            date.append(dateFormat.format(Calendar.getInstance().getTime()));
            date.insert(10 , "T");
            date.append("Z");
            sb.append("<when>" + date.toString() + "</when>" + "\n");
            sb.append("</TimeStamp>" + "\n");
            sb.append("<Point>" + "\n");
            sb.append("<coordinates>" + f.getPosition().y() + "," + f.getPosition().x() + "," + f.getPosition().z() + "</coordinates>");
            sb.append("</Point>" + "\n");
            sb.append("</Placemark>" + "\n");
            i++;
        }

        return sb.toString();
    }

    public static void Paths2kml(LinkedList<Path> LP)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n");
        sb.append("<kml>" + "\n");
        sb.append("<Document>" + "\n");
        for (Path p: LP)
        {
            sb.append(Path2Kml(p));
        }
        sb.append("</Document>" + "\n");
        sb.append("</kml>");

        try
        {
            PrintWriter pw = new PrintWriter(new File("KmlFile" + System.currentTimeMillis() + ".kml"));
            pw.write(sb.toString());
            pw.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}