package Algorithms;

import Coords.GeoBox;
import Coords.LatLonAlt;
import Robot.Fruit;
import Robot.Game;
import graph.Graph;
import graph.Graph_Algo;
import graph.Node;

import java.util.ArrayList;
import java.util.LinkedList;

public class Ex4Algo
{
    /**
     * Calculate which corner of the box will enter are graph
     * @param box_list the list of boxes in our game
     * @return list of where the node are in the Coordinate
     */
    private static ArrayList<LatLonAlt> CalcNode(ArrayList<GeoBox> box_list)
    {
        ArrayList<LatLonAlt> node_list = new ArrayList<>();
        LatLonAlt[] nodes = new LatLonAlt[4];
        boolean inNode = false;
        for(int i = 0; i < box_list.size(); i++)
        {
            //Enter nodes the 4 corner of the box
            nodes[0] =  box_list.get(i).getMax();
            nodes[1] = box_list.get(i).getMin();
            nodes[2] = new LatLonAlt(box_list.get(i).getMin().lat() , box_list.get(i).getMax().lon() , 0);
            nodes[3] = new LatLonAlt(box_list.get(i).getMax().lat() , box_list.get(i).getMin().lon() , 0);
            //run on the 4 corner
            for (int j = 0; j < 4; j++)
            {
                for(int k = 0; k < box_list.size(); k++)
                {
                    //check if the node is inside a box if it is it will not enter are graph
                    if(isNodeinBox(box_list.get(k),nodes[j]))
                    {
                        inNode = true;
                        break;
                    }
                }
                if (!inNode) //if the node is not in any of the boxes it will enter are graph
                    node_list.add(nodes[j]);
                inNode = false;
            }
        }
        return node_list;
    }


    /**
     * Calculate the edges of each node in our graph
     * @param graph the graph we add edges to
     * @param node_pos_list the list of node in Coordinate
     * @param box_list list of the boxes in our game in order to check that no edge go through any box
     */
    private static void CalcEdegs(Graph graph, LinkedList<LatLonAlt> node_pos_list, ArrayList<GeoBox> box_list)
    {
        boolean NoEdge = false;
        for(int i = 0; i < graph.size(); i++)
        {
            for (int j = 0; j < graph.size(); j++)
            {
                //check if there is already an edge between the node and that we are not putting and edge from a node to himself
                if (!graph.getNodeByIndex(i).hasEdge(graph.getNodeByIndex(j).get_id()) && node_pos_list.get(i).distance2D(node_pos_list.get(j)) != 0)
                {
                    //using getLine we get a sample of points in the Potential edge
                    LinkedList<LatLonAlt> Line = GetLine(node_pos_list.get(i) , node_pos_list.get(j) , 6);
                    for (int k = 0; k < box_list.size(); k++)
                    {
                        for(int s = 0; s < Line.size(); s++)
                        {
                            //we check if any of the points in line are inside any of the boxes if it is we will not create an edge
                            if(isNodeinBox(box_list.get(k) , Line.get(s)))
                            {
                                NoEdge = true;
                                break;
                            }
                        }
                        if(NoEdge)
                            break;
                    }
                    if(!NoEdge) // if non of the point are in a box we will create and edge between the two nodes
                        graph.addEdge(graph.getNodeByIndex(i).get_name() , graph.getNodeByIndex(j).get_name() , node_pos_list.get(i).distance2D(node_pos_list.get(j)));
                    NoEdge = false;
                }
            }
        }
    }


    /**
     * create a path between the player to nearest fruit considering the boxes in the way
     * @param player the position of our player
     * @param fruit_list the list of fruit in our game
     * @param box_list the boxes in our game
     * @return the path needed to go from our playe to the nearest fruit
     */
    public static LinkedList<LatLonAlt> Path(LatLonAlt player , ArrayList<Fruit> fruit_list, ArrayList<GeoBox> box_list)
    {
        Graph G = new Graph();
        ArrayList<LatLonAlt> box_nodes = CalcNode(box_list); //we calculate the corner of the boxes that will enter our graph
        LinkedList<LatLonAlt> node_pos_list = new LinkedList<>();
        Node temp;
        temp = new Node("player"); // we create a node to our player
        temp.set_id(0);
        G.add(temp);//we add the player to our graph
        node_pos_list.add(player);// we add the player to our list of positions
        for(int i = 0; i < box_nodes.size(); i++) // we add each box node (the box corner that we approved that belong to our graph) to our graph
        {
            temp = new Node(""+i);
            temp.set_id(i+1);
            G.add(temp);
            node_pos_list.add(box_nodes.get(i));
        }
        for (int i = 0; i < fruit_list.size(); i++) // we add all the fruit the our grpah
        {
            temp = new Node(""+(i+box_nodes.size()));
            temp.set_id(i+box_nodes.size()+1);
            G.add(temp);
            node_pos_list.add(fruit_list.get(i).getLocation());
        }


        CalcEdegs(G, node_pos_list , box_list);//we calculate the edges of our graph

        Graph_Algo.dijkstra(G,"player");//we calculate the shortest path from our player to any in our graph using the dijkstra algorithm
        double Mindist = Integer.MAX_VALUE;
        Node b , Min_dist_Node = G.getNodeByName("player");
        for (int i = 0; i < fruit_list.size(); i++) //we run on the fruits in our graph and look for the closest fruit to our player
        {
            b = G.getNodeByName("" + (i+box_nodes.size()));
            if(b.getDist() < Mindist)
            {
                Mindist = b.getDist();
                Min_dist_Node = b;
            }
        }

        ArrayList<String> ShortPath = Min_dist_Node.getPath();//we receive the shortest path from our player to the fruit (not including the fruit)
        LinkedList<LatLonAlt> ShortestPath = new LinkedList<>();
        int j;
        for(int i = 1; i < ShortPath.size(); i++) // we create a list of coordinate of the shortest path
        {
            j = Integer.parseInt(ShortPath.get(i));
            ShortestPath.add(modifiedPoint(box_nodes.get(j) , box_list));
        }

        ShortestPath.add(node_pos_list.get(Min_dist_Node.get_id())); // we add the fruit to our path

        Graph_Algo.clearGraphData(G); // we clear our graph for the next use

        return ShortestPath;
    }

    /**
     * Create a list of geoBox
     * @param game the game from where we take to GeoBox for our list
     * @return The List of GeoBox that appear in our game
     */
    public static ArrayList<GeoBox> CalcGeoBox(Game game)
    {
        ArrayList<GeoBox> box_list = new ArrayList<>();
        for (int i = 0; i < game.sizeB(); i++)
        {
            box_list.add(game.getBox(i));
        }

        return box_list;
    }

    /**
     * Check if a point is inside a GeoBox (Not including on the edge of the box)
     * @param box the box we check
     * @param q the point we check
     * @return True if q is in box, false otherwise
     */
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

    public static boolean FruitExsit(LatLonAlt fruit , ArrayList<Fruit> fruit_list)
    {
        for(int i = 0; i < fruit_list.size(); i++)
        {
            if(fruit_list.get(i).getLocation().lat() == fruit.lat() && fruit_list.get(i).getLocation().lon() == fruit.lon())
                return true;
        }
        return false;
    }

    public static LatLonAlt modifiedPoint(LatLonAlt box_point , ArrayList<GeoBox> box_list)
    {
        GeoBox tmp = box_list.get(0);
        for(int  i = 0; i < box_list.size(); i++)
        {
            if(box_point.lon() == box_list.get(i).getMin().lon() || box_point.lon() == box_list.get(i).getMax().lon())
            {
                tmp = box_list.get(i);
                break;
            }
        }
        if(box_point.lon() == tmp.getMin().lon() && box_point.lat() == tmp.getMax().lat())
        {
            return new LatLonAlt(box_point.lat()+0.0002 , box_point.lon() - 0.0002 , 0);
        }
        if(box_point.lon() == tmp.getMax().lon() && box_point.lat() == tmp.getMin().lat())
        {
            return new LatLonAlt(box_point.lat()-0.0002 , box_point.lon() + 0.0002 , 0);
        }
        if(box_point.lon() == tmp.getMax().lon() && box_point.lat() == tmp.getMax().lat())
        {
            return new LatLonAlt(box_point.lat()+0.0002 , box_point.lon() + 0.0002 , 0);
        }
        else {
            return new LatLonAlt(box_point.lat()-0.0002 , box_point.lon() - 0.0002 , 0);
        }
    }

}