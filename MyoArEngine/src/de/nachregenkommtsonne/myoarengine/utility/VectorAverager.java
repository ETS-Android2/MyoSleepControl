package de.nachregenkommtsonne.myoarengine.utility;

public class VectorAverager
{
	private Vector[] _values;

	public VectorAverager(int count)
	{
		_values = new Vector[count];
	}

	public Vector getAverage()
	{
		Vector v = new Vector();
		for (int i = 0; i < _values.length; i++)
		{
			v.add(_values[i]);
		}
		v.div((float) _values.length);
		return v;
	}

	public void add(Vector vector)
	{
		for (int i = 0; i < _values.length - 1; i++)
		{
			_values[i] = _values[i + 1];
		}
		_values[_values.length - 1] = vector;
	}
}