package GIS;

/**
 * A class Use To Represent a Pixel on The Map
 */
public class Pixel
{
    private int x;
    private int y;
    //private int ix;
    //private int iy;

    public Pixel()
    {

    }

    public Pixel(int x, int Y)
    {
        this.x = x;
        this.y = Y;
        //this.ix = (int)Math.round(x);
        //this.iy = (int)Math.round(y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Pixel p)
    {
        return (this.getX() == p.getX()) && (this.getY() == p.getY());
    }

    /*public int getIx() {
        return ix;
    }

    public void setIx(int ix) {
        this.ix = ix;
    }

    public int getIy() {
        return iy;
    }

    public void setIy(int iy) {
        this.iy = iy;
    }*/
}
