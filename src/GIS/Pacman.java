package GIS;



import Geom.Point3D;

/**
 * this class represent the datas of the pacman.
 * we have in this class the pacman constructor and the defultive contructor
 * the rest of the class is the getters and setters of the datas
 * @author shai and sela
 *
 */

public class Pacman

{

    private Point3D position;
    private double[] azimuth;    //datas
    private double Speed;
    private double Radius;
    private int id;



    /**
     * constructor that we get datas of pacman and set every datas of the pacman
     * @param position to the position of pacman
     * @param azimuth array of azimuth in every pacman
     * @param Speed -the speed of every pacman
     * @param Radius of every pacman
     * @param id of every pacman
     */

    public Pacman(Point3D position, double[] azimuth,double Speed,double Radius , int id)
    {
        this.azimuth = azimuth;
        this.position = position;
        this.Radius = Radius;
        this.Speed = Speed;
        this.id = id;
    }

    /**
     * A copy constructor. we copy every data from p to the new constructor
     * @param p pacman to copy from
     */
    public Pacman(Pacman p)
    {
        this.azimuth = p.getAzimuth();
        this.position = p.getPosition();
        this.Radius = p.getRadius();
        this.Speed = p.getSpeed();
        this.id = -1;
    }

    /**
     * defaultive of constructor of pacman
     * sets all datas of pancman to the  defaultive
     */

    public Pacman()
    {
        this.position = null;
        this.azimuth = new double[3];
        this.Speed = 0;
        this.Radius = 0;
        this.id = -1;

    }

    /**
     *  get Position
     * @return position
     */
    public Point3D getPosition() {
        return position;
    }

    /**
     * set the position of the Pacman
     * @param position
     */

    public void setPosition(Point3D position) {
        this.position = position;
    }


    /**
     * get array of azimuth
     * @return array of azimuth
     */
    public double[] getAzimuth() {
        return azimuth;

    }

    /**
     * set the array of azimuth of the Pacman
     * @param azimuth
     */

    public void setAzimuth(double[] azimuth) {
        this.azimuth = azimuth;

    }


    /**
     * get the speed of the Pacman
     * @ return speed
     */
    public double getSpeed() {
        return Speed;

    }

    /**
     * set the speed of the pacman
     * @param speed to set speed
     */

    public void setSpeed(double speed) {
        Speed = speed;

    }

    /**
     *  get the radius of the pacman
     * @return radius
     */



    public double getRadius() {
        return Radius;
    }


    /**
     * set the raduis of the pacman
     * @param radius to set raduis
     */
    public void setRadius(double radius) {

        Radius = radius;

    }

    /**
     * get the id of the pacman
     * @return id
     */

    public int getId() {
        return id;

    }


    /**
     * set the id of the pacman
     * @param id to set id
     */
    public void setId(int id) {
        this.id = id;

    }


}