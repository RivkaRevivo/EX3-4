package GIS;

import Geom.Point3D;

/**
 * this class represent data of object of kind of fruit
 * we have here constructors of fruit and method of setters and getters
 *
 * @author shai and sela
 */



public class Fruit
{
    private Point3D position;
    private int id;
    private int weight;


    /**
     * constructor
     *
     *
     * @param position to set
     * @param id to set
     * @param weight to set
     */
    public Fruit(Point3D position,int id, int weight)
    {
        this.position = position;
        this.id = id;
        this.weight = weight;
    }

    /**
     * defaultןive constructor
     *
     */

    public Fruit()
    {
        this.position = null;
        this.id = -1;
        this.weight = 1;
    }

    /**
     *
     *method get position
     * @return position
     */

    public Point3D getPosition() {
        return position;
    }

    /**
     * method set position
     * @param position we get to function
     */

    public void setPosition(Point3D position) {
        this.position = position;
    }

    /**
     *
     *method get id
     * @return id
     */

    public int getId() {
        return id;
    }

    /**
     * method set id
     * @param id we get to function
     */

    public void setId(int id) {
        this.id = id;
    }
    /**
     *
     *method get weight
     * @return weight
     */

    public int getWeight() {
        return weight;
    }

    /**
     * method set weight
     * @param weight we get to function
     */

    public void setWeight(int weight) {
        this.weight = weight;
    }
}