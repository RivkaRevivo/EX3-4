package GIS;

/**
 * this class represent point of pixel in x and y
 * we have here constructors of pixel and functions of getters and setters
 *
 * @author shai and sela
 */
public class Pixel
{
    private double x;
    private double y;

    /**
     * defultive constructor
     *
     */
    public Pixel()
    {

    }

    /**
     *  constructor
     * @param x we set
     * @param Y we set
     */

    public Pixel(double x, double Y)
    {
        this.x = x;
        this.y = Y;
    }

    /**
     * method get of x
     * @return x
     */

    public double getX() {
        return x;
    }

    /**
     * method get of y
     * @return y
     */

    public double getY() {
        return y;
    }

    /**
     * method to set x
     * @param x to set
     */

    public void setX(double x) {
        this.x = x;
    }

    /**
     * method to set y
     * @param y to set
     */

    public void setY(double y) {
        this.y = y;
    }
}