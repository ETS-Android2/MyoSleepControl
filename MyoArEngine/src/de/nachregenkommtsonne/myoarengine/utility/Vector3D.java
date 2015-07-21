package de.nachregenkommtsonne.myoarengine.utility;

import java.util.Locale;

public class Vector3D
{
  private float[] _values = new float[3];

  public Vector3D(float x, float y, float z)
  {
    _values[0] = x;
    _values[1] = y;
    _values[2] = z;
  }

  public Vector3D()
  {
  }

  public Vector3D(Vector3D vector)
  {
    _values = vector._values.clone();
  }

  public Vector3D(float values[])
  {
    if (values.length != 3)
      throw new RuntimeException("Not a 3 dimensional vector");

    _values = values.clone();
  }

  public float getX()
  {
    return _values[0];
  }

  public float getY()
  {
    return _values[1];
  }

  public float getZ()
  {
    return _values[2];
  }

  public Vector3D add(Vector3D vector)
  {
    return new Vector3D(_values[0] + vector._values[0], _values[1] + vector._values[1], _values[2] + vector._values[2]);
  }

  public Vector3D sub(Vector3D vector)
  {
    return new Vector3D(_values[0] - vector._values[0], _values[1] - vector._values[1], _values[2] - vector._values[2]);
  }

  public Vector3D cross(Vector3D vector)
  {
    Vector3D ret = new Vector3D(0, 0, 0);
    ret._values[0] = _values[1] * vector._values[2] - _values[2] * vector._values[1];
    ret._values[1] = _values[2] * vector._values[0] - _values[0] * vector._values[2];
    ret._values[2] = _values[0] * vector._values[1] - _values[1] * vector._values[0];
    return ret;
  }

  public float[] getValues()
  {
    return _values;
  }

  public Vector3D mult(float value)
  {
    return new Vector3D(_values[0] * value, _values[1] * value, _values[2] * value);
  }

  public Vector3D div(float value)
  {
    return new Vector3D(_values[0] / value, _values[1] / value, _values[2] / value);
  }

  public Vector3D normalize()
  {
    float length = getLength();
    return div(length);
  }

  public float getLength()
  {
    float squareLength = _values[0] * _values[0] + _values[1] * _values[1] + _values[2] * _values[2];
    return (float) Math.sqrt(squareLength);
  }

  public boolean isValid()
  {
    return !(Float.isNaN(_values[0]) || Float.isNaN(_values[1]) || Float.isNaN(_values[2]));
  }

  @Override
  public boolean equals(Object object)
  {
    if (object instanceof Vector3D)
    {
      Vector3D vector = (Vector3D) object;

      return vector.getX() == _values[0] && vector.getY() == _values[1] && vector.getZ() == _values[2];
    }

    return false;
  }
  
  @Override
  public String toString()
  {
    // TODO Auto-generated method stub
    return String.format(Locale.ENGLISH,"%f, %f, %f", _values[0], _values[1], _values[2]);
  }
}