package de.nachregenkommtsonne.myoarengine.utility;

public class MovementCalculator
{
  public MovementCalculator()
  {
  }
  
  public Vector getMovementDelta(Vector displayVector, Vector xVector, Vector yVector, Vector zVector)
  {
    Vector displayPlaneXVector = new Vector(zVector.getY(), -zVector.getX(), 0.0f);
    Vector displayPlaneYVector = displayPlaneXVector.cross(zVector);

    Vector inWorldDisplayVector = xVector.mult(displayVector.getX()).add(yVector.mult(displayVector.getY()));

    Vector p1 = inWorldDisplayVector.cross(displayPlaneXVector);
    Vector p2 = displayPlaneYVector.cross(displayPlaneXVector);
    Vector p3 = inWorldDisplayVector.cross(displayPlaneYVector);

    float b = p1.getX() / p2.getX();
    float a = p3.getX() / -p2.getX();

    Vector forwardVector = new Vector(-zVector.getX(), -zVector.getY(), -0.0f);
    Vector delta = displayPlaneXVector.mult(a).add(forwardVector.mult(b));
    
    return delta;
  }
}