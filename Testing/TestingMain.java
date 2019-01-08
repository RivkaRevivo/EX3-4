import Algorithms.Ex4Algo;
import Coords.LatLonAlt;

import java.util.LinkedList;

public class TestingMain
{
    public static void main(String[] args)
    {
        LatLonAlt p1 = new LatLonAlt(32.103155, 35.203224, 0);
        LatLonAlt p2 = new LatLonAlt(32.105729, 35.201551, 0);
        LinkedList<LatLonAlt> line = Ex4Algo.GetLine(p1,p2,5);
        for(int i = 0; i < line.size(); i++)
        {
            System.out.println(i+".  " + line.get(i).toString());
        }
    }
}
