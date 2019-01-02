package Algorithms;

import Coords.GeoBox;
import Coords.LatLonAlt;
import Geom.Line;
import graph.Graph;
import graph.Node;
import Robot.Fruit;


import java.util.ArrayList;
import java.util.LinkedList;

public class Ex4Algo
{
    private static ArrayList<LatLonAlt> CalcNode(ArrayList<GeoBox> box_list)
    {
        ArrayList<LatLonAlt> node_list = new ArrayList<>();
        //GeoBox Nodes;
        LatLonAlt[] nodes = new LatLonAlt[4];
        boolean inNode = false;
        for(int i = 0; i < box_list.size(); i++)
        {
            nodes[0] =  box_list.get(i).getMax();
            nodes[1] = box_list.get(i).getMin();
            nodes[2] = new LatLonAlt(box_list.get(i).getMin().lat() , box_list.get(i).getMax().lon() , 0);
            nodes[3] = new LatLonAlt(box_list.get(i).getMax().lat() , box_list.get(i).getMin().lon() , 0);
            for (int j = 0; j < 4; j++)
            {
                for(int k = 0; k < box_list.size(); k++)
                {
                    if(box_list.get(k).isIn2D(nodes[j]))
                    {
                        inNode = true;
                        break;
                    }
                }
                if (!inNode)
                    node_list.add(nodes[j]);
                inNode = false;
            }
        }
        return node_list;
    }

    private static void CalcEdegs(LinkedList<Node> nodes_list , LinkedList<LatLonAlt> node_pos_list , LinkedList<Line> line_list)
    {
        boolean NoEdge = false;
        for(int i = 0; i < nodes_list.size(); i++)
        {
            for(int j = 0; j < nodes_list.size(); j++)
            {
                if (!nodes_list.get(i).hasEdge(nodes_list.get(j).get_id()))
                {
                    for(int k = 0; k < line_list.size(); k++)
                    {
                        if(doIntersect(node_pos_list.get(i),node_pos_list.get(j),line_list.get(k).get_FirstPoint(),line_list.get(k).get_SecondPoint()))
                        {
                            NoEdge = true;
                        }
                    }
                }

                if(!NoEdge)
                    nodes_list.get(i).add(nodes_list.get(j) , node_pos_list.get(i).distance2D(node_pos_list.get(j)));
                NoEdge = false;
            }
        }
    }

    public static LinkedList<> Path(LatLonAlt player , ArrayList<Fruit> fruit_list, ArrayList<GeoBox> box_list)
    {
        Graph G = new Graph();
        ArrayList<LatLonAlt> box_nodes = CalcNode(box_list);
        G.add(new Node("player"));
        for(int i = 0; i < box_nodes.size(); i++)
        {
            G.add(new Node(""+i));
        }
        for (int i = 0; i < fruit_list.size(); i++)
        {
            G.add(new Node(""+(i+box_nodes.size())));
        }

    }

    private static boolean onSegment(LatLonAlt p, LatLonAlt q, LatLonAlt r)
    {
        if (q.x() <= Math.max(p.x(), r.x()) && q.x() >= Math.min(p.x(), r.x()) &&
                q.y() <= Math.max(p.y(), r.y()) && q.y() >= Math.min(p.y(), r.y()))
            return true;

        return false;
    }

    private static int orientation(LatLonAlt p, LatLonAlt q, LatLonAlt r)
    {
        double val = (q.y() - p.y()) * (r.x() - q.x()) -
                (q.x() - p.x()) * (r.y() - q.y());

        if (val == 0) return 0;

        return (val > 0)? 1: 2;
    }

    private static boolean doIntersect(LatLonAlt p1, LatLonAlt q1, LatLonAlt p2, LatLonAlt q2)
    {
        int o1 = orientation(p1, q1, p2);
        int o2 = orientation(p1, q1, q2);
        int o3 = orientation(p2, q2, p1);
        int o4 = orientation(p2, q2, q1);

        if (o1 != o2 && o3 != o4)
            return true;

        if (o1 == 0 && onSegment(p1, p2, q1)) return true;
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false;
    }
}