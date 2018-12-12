package GIS;

import Geom.Point3D;

public class Pacman
{
    private Point3D position;
    private double[] azimuth;
    private double Speed;
    private double Radius;
    private int id;


    public Pacman(Point3D position, double[] azimuth,double Speed,double Radius , int id)
    {
        this.azimuth = azimuth;
        this.position = position;
        this.Radius = Radius;
        this.Speed = Speed;
        this.id = id;
    }

    public Pacman()
    {
        this.position = null;
        this.azimuth = new double[3];
        this.Speed = 0;
        this.Radius = 0;
        this.id = -1;
    }

    public Point3D getPosition() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position = position;
    }

    public double[] getAzimuth() {
        return azimuth;
    }

    public void setAzimuth(double[] azimuth) {
        this.azimuth = azimuth;
    }

    public double getSpeed() {
        return Speed;
    }

    public void setSpeed(double speed) {
        Speed = speed;
    }

    public double getRadius() {
        return Radius;
    }

    public void setRadius(double radius) {
        Radius = radius;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
