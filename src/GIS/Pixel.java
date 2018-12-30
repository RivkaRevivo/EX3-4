package GIS;

/**
 * A class Use To Represent a Pixel on The Map
 */
public class Pixel
{
    private int x;
    private int y;


    /**
     * Basic Constructor
     */
    public Pixel()
    {

    }

    /**
     * Put the x int the x coordinate and the y in the y coordinate
     * @param x the x coordinate
     * @param Y the Y coordinate
     */
    public Pixel(int x, int Y)
    {
        this.x = x;
        this.y = Y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Check if the p coordinate is equal to this pixel coordinate
     * @param p The pixel the compare to
     * @return true if p is equal to this. false if otherwise
     */
    public boolean equals(Pixel p)
    {
        return (this.getX() == p.getX()) && (this.getY() == p.getY());
    }
}