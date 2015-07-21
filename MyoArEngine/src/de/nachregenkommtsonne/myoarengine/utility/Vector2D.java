package de.nachregenkommtsonne.myoarengine.utility;

import java.util.Locale;

public class Vector2D
{
  private float[] _values = new float[2];

  public Vector2D(float x, float y)
  {
    _values[0] = x;
    _values[1] = y;
  }

  public Vector2D()
  {
  }

  public Vector2D(Vector2D vector)
  {
    _values = vector._values.clone();
  }

  public Vector2D(float values[])
  {
    if (values.length != 2)
      throw new RuntimeException("Not a 2 dimensional vector");

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

  public Vector2D add(Vector2D vector)
  {
    return new Vector2D(_values[0] + vector._values[0], _values[1] + vector._values[1]);
  }

  public Vector2D sub(Vector2D vector)
  {
    return new Vector2D(_values[0] - vector._values[0], _values[1] - vector._values[1]);
  }

  public float[] getValues()
  {
    return _values;
  }

  public Vector2D mult(float value)
  {
    return new Vector2D(_values[0] * value, _values[1] * value);
  }

  public Vector2D div(float value)
  {
    return new Vector2D(_values[0] / value, _values[1] / value);
  }

  public Vector2D normalize()
  {
    float length = getLength();
    return div(length);
  }

  public float getLength()
  {
    float squareLength = _values[0] * _values[0] + _values[1] * _values[1];
    return (float) Math.sqrt(squareLength);
  }

  public boolean isValid()
  {
    return !(Float.isNaN(_values[0]) || Float.isNaN(_values[1]));
  }

  @Override
  public boolean equals(Object object)
  {
    if (object instanceof Vector2D)
    {
      Vector2D vector = (Vector2D) object;

      return vector.getX() == _values[0] && vector.getY() == _values[1];
    }

    return false;
  }
  
  @Override
  public String toString()
  {
    // TODO Auto-generated method stub
    return String.format(Locale.ENGLISH,"%f, %f", _values[0], _values[1]);
  }
}