package GIS;

/**
 * A class Use To Represent a Pixel on The Map
 */
public class Pixel
{
    private double x;
    private double y;

    public Pixel()
    {

    }

    public Pixel(double x, double Y)
    {
        this.x = x;
        this.y = Y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
