package GIS;

import Geom.Point3D;

import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Date;

public class Metadata implements Meta_data{
	
	@Override
	public String toString()
	{
		String s = Instant.ofEpochMilli(getUTC()).atOffset(ZoneOffset.UTC).toString();
		String Time = s.substring(11, 19);
		String Date = s.substring(0, 10);
		String Total = "Date: " + Date + ", Time: " + Time; //+ ", Color: " + this._Color;
		return Total;
	}
	

	
	public Metadata()
	{
		//this._Color = null;
		this._Time = getUTC();
		this.Description += "utc=" + _Time;
	}

	public Metadata(Point3D Orientation)
	{
		this._Time = getUTC();
		this.Orientation = Orientation;
		this.Description += "utc=" + _Time + " Orientation=" + this.Orientation;
	}

	
	private long _Time;
	//private String _Color;
	private Point3D Orientation = null;
	private String Description = "";

	@Override
	public long getUTC() {
		this._Time = new Date().getTime() + 7200000;
		return this._Time;
	}
	

	@Override
	public Point3D get_Orientation() {
		return Orientation;
	}


	public String getDescription() {
		return Description;
	}
}
