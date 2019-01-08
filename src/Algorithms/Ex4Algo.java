package Algorithms;

import Coords.GeoBox;
import Coords.LatLonAlt;
import Geom.Line;

import Robot.Fruit;
import Robot.Game;
import graph.Graph;
import graph.Graph_Algo;
import graph.Node;


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
                    //if(box_list.get(k).isIn2D(nodes[j]))
                    if(isNodeinBox(box_list.get(k),nodes[j]))
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


    /*private static void CalcEdegs(Graph graph, LinkedList<LatLonAlt> node_pos_list, LinkedList<Line> line_list)
    {
        boolean NoEdge = false;
        for(int i = 0; i < graph.size(); i++)
        {
            for(int j = 0; j < graph.size(); j++)
            {
                if(!graph.getNodeByIndex(i).hasEdge(graph.getNodeByIndex(j).get_id()))
                {
                    for(int k = 0; k < line_list.size(); k++)
                    {
                        if(i == j) {
                            NoEdge = true;
                            break;
                        }
                        if(doIntersect(node_pos_list.get(i),node_pos_list.get(j),line_list.get(k).get_FirstPoint(),line_list.get(k).get_SecondPoint()))
                        {
                            NoEdge = true;
                            break;
                        }
                    }
                }

                if(!NoEdge)
                    graph.addEdge(graph.getNodeByIndex(i).get_name() , graph.getNodeByIndex(j).get_name() , node_pos_list.get(i).distance2D(node_pos_list.get(j)));
                NoEdge = false;
            }
        }

    }

    }*/


    private static void CalcEdegs(Graph graph, LinkedList<LatLonAlt> node_pos_list, ArrayList<GeoBox> box_list)
    {
        boolean NoEdge = false;
        for(int i = 0; i < graph.size(); i++)
        {
            for (int j = 0; j < graph.size(); j++)
            {
                if (!graph.getNodeByIndex(i).hasEdge(graph.getNodeByIndex(j).get_id()))
                {
                    LinkedList<LatLonAlt> Line = GetLine(node_pos_list.get(i) , node_pos_list.get(j) , 5);
                    for (int k = 0; k < box_list.size(); k++)
                    {
                        for(int s = 0; s < Line.size(); s++)
                        {
                            if(isNodeinBox(box_list.get(k) , Line.get(s)))
                            {
                                NoEdge = true;
                                break;
                            }
                        }
                        if(NoEdge)
                            break;
                    }
                    if(!NoEdge)
                        graph.addEdge(graph.getNodeByIndex(i).get_name() , graph.getNodeByIndex(j).get_name() , node_pos_list.get(i).distance2D(node_pos_list.get(j)));
                    NoEdge = false;
                }
            }
        }
    }










    public static LinkedList<LatLonAlt> Path(LatLonAlt player , ArrayList<Fruit> fruit_list, ArrayList<GeoBox> box_list)
    {
        Graph G = new Graph();
        ArrayList<LatLonAlt> box_nodes = CalcNode(box_list);
        LinkedList<LatLonAlt> node_pos_list = new LinkedList<>();
        Node temp;
        temp = new Node("player");
        temp.set_id(0);
        G.add(temp);
        node_pos_list.add(player);
        for(int i = 0; i < box_nodes.size(); i++)
        {
            temp = new Node(""+i);
            temp.set_id(i+1);
            G.add(temp);
            node_pos_list.add(box_nodes.get(i));
        }
        for (int i = 0; i < fruit_list.size(); i++)
        {
            temp = new Node(""+(i+box_nodes.size()));
            temp.set_id(i+box_nodes.size()+1);
            G.add(temp);
            node_pos_list.add(fruit_list.get(i).getLocation());
        }

        LinkedList<Line> line_list = new LinkedList<Line>();
        for (int i = 0; i < box_list.size(); i++)
        {
            line_list.add(new Line(box_list.get(i).getMax() , new LatLonAlt(box_list.get(i).getMax().lat() , box_list.get(i).getMin().lon() , 0)));
            line_list.add(new Line(box_list.get(i).getMax() , new LatLonAlt(box_list.get(i).getMin().lat() , box_list.get(i).getMax().lon() , 0)));
            line_list.add(new Line(box_list.get(i).getMin() , new LatLonAlt(box_list.get(i).getMin().lat() , box_list.get(i).getMax().lon() , 0)));
            line_list.add(new Line(box_list.get(i).getMin() , new LatLonAlt(box_list.get(i).getMax().lat() , box_list.get(i).getMin().lon() , 0)));

        }


        //CalcEdegs(G,node_pos_list,line_list);
        CalcEdegs(G, node_pos_list , box_list);

        Graph_Algo.dijkstra(G,"player");
        double Mindist = Integer.MAX_VALUE;
        Node b , Min_dist_Node = G.getNodeByName("player");
        for (int i = 0; i < fruit_list.size(); i++)
        {
            b = G.getNodeByName("" + (i+box_nodes.size()));
            if(b.getDist() < Mindist)
            {
                Mindist = b.getDist();
                Min_dist_Node = b;
            }
        }

        ArrayList<String> ShortPath = Min_dist_Node.getPath();
        LinkedList<LatLonAlt> ShortestPath = new LinkedList<>();
        int j;
        for(int i = 1; i < ShortPath.size(); i++)
        {
            j = Integer.parseInt(ShortPath.get(i));
            ShortestPath.add(box_nodes.get(j));
        }
        //j = Integer.parseInt(Min_dist_Node.get_name());
        ShortestPath.add(node_pos_list.get(Min_dist_Node.get_id()));

        Graph_Algo.clearGraphData(G);

        return ShortestPath;
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

    public static ArrayList<GeoBox> CalcGeoBox(Game game)
    {
        ArrayList<GeoBox> box_list = new ArrayList<>();
        for (int i = 0; i < game.sizeB(); i++)
        {
            box_list.add(game.getBox(i));
        }

        return box_list;
    }

    public static boolean isNodeinBox(GeoBox box,LatLonAlt q) {
        boolean ans = false;
        if (q.lat() > box.getMin().lat() && q.lon() > box.getMin().lon() && q.lat() < box.getMax().lat() && q.lon() < box.getMax().lon()) {
            ans = true;
        }

        return ans;
    }


    public static LinkedList<LatLonAlt> GetLine(LatLonAlt p1 , LatLonAlt p2 , int size)
    {
        LinkedList<LatLonAlt> line = new LinkedList<>();
        if(size > 0)
        {
            LatLonAlt mid = new LatLonAlt((p1.lat() + p2.lat()) / 2 , (p1.lon() + p2.lon()) / 2 , 0 );
            line.add(mid);
            LinkedList<LatLonAlt> newLine1 = new LinkedList<>();
            newLine1 = GetLine(p1 , mid , size - 1);
            LinkedList<LatLonAlt> newLine2 = new LinkedList<>();
            newLine2 = GetLine(mid , p2 , size - 1);
            for (int i = 0; i < newLine1.size(); i++)
            {
                line.add(newLine1.get(i));
            }
            for (int i = 0; i < newLine2.size(); i++)
            {
                line.add(newLine2.get(i));
            }
        }

        return line;
    }

}