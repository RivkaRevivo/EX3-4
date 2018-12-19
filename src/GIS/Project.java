package GIS;

import java.util.*;

public class Project extends LinkedList<GIS_layer> implements GIS_project
{

	private Meta_data _M;

	public Project()
	{
		this._M = new Metadata();
	}

	public Project(Meta_data m)
	{
		this._M = m;
	}

	@Override
	public Meta_data get_Meta_data() {
		return _M;
	}

}
