package GIS;

import java.util.LinkedList;

public class Layer extends LinkedList<GIS_element> implements  GIS_layer
{

	private Metadata _M;

	public Layer()
	{
		this._M = new Metadata();
	}

	@Override
	public Meta_data get_Meta_data() {
		return _M;
	}
}