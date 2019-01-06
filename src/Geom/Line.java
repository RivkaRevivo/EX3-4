package Geom;

import Coords.LatLonAlt;

public class Line
{
    private LatLonAlt _FirstPoint;
    private LatLonAlt _SecondPoint;
    public Line(LatLonAlt FirstPoint,LatLonAlt SecondPoint)
    {
        this._FirstPoint = new LatLonAlt (FirstPoint);
        this._SecondPoint = new LatLonAlt(SecondPoint);
    }

    public LatLonAlt get_FirstPoint() {
        return _FirstPoint;
    }

    public void set_FirstPoint(LatLonAlt _FirstPoint) {
        this._FirstPoint = _FirstPoint;
    }

    public LatLonAlt get_SecondPoint() {
        return _SecondPoint;
    }

    public void set_SecondPoint(LatLonAlt _SecondPoint) {
        this._SecondPoint = _SecondPoint;
    }
}
