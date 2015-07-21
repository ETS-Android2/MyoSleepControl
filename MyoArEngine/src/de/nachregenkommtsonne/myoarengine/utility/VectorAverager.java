package de.nachregenkommtsonne.myoarengine.utility;

public class VectorAverager
{
  private Vector3D[] _values;

  public VectorAverager(int count)
  {
    _values = new Vector3D[count];
  }

  public Vector3D getAverage()
  {
    Vector3D vector = new Vector3D();
    for (int i = 0; i < _values.length; i++)
    {
      if (_values[i] != null)
        vector = vector.add(_values[i]);
    }
    return vector.div((float) _values.length);
  }

  public void add(Vector3D vector)
  {
    for (int i = 0; i < _values.length - 1; i++)
    {
      _values[i] = _values[i + 1];
    }
    _values[_values.length - 1] = vector;
  }
}