package GIS;

import java.util.*;

public class Project extends LinkedList<GIS_layer> implements GIS_project
{

	private Metadata _M;

	public Project()
	{
		this._M = new Metadata();
	}

	@Override
	public Meta_data get_Meta_data() {
		return _M;
	}

}
