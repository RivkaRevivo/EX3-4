package GIS;

import java.util.LinkedList;

public class Layer extends LinkedList<GIS_element> implements  GIS_layer
{

	private Meta_data _M;

	public Layer()
	{
		this._M = new Metadata();
	}

	public Layer(Meta_data m)
	{
		this._M = m;
	}

	@Override
	public Meta_data get_Meta_data() {
		return _M;
	}
}