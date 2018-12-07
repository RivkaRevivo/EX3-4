package GIS;

import Geom.Point3D;

public class Fruit
{
    private Point3D position;
    private int id;
    private int weight;

    public Fruit(Point3D position,int id, int weight)
    {
        this.position = position;
        this.id = id;
        this.weight = weight;
    }

    public Fruit()
    {
        this.position = null;
        this.id = -1;
        this.weight = 1;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
