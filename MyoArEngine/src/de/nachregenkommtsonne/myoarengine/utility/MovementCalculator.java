package de.nachregenkommtsonne.myoarengine.utility;

public class MovementCalculator
{
  public MovementCalculator()
  {
  }
  
  public Vector3D getMovementDelta(Vector3D displayVector, Vector3D xVector, Vector3D yVector, Vector3D zVector)
  {
    Vector3D inWorldDisplayVector = xVector.mult(displayVector.getX()).add(yVector.mult(displayVector.getY()));
    
    Vector3D displayPlaneXVector = new Vector3D(zVector.getY(), -zVector.getX(), 0.0f);
    Vector3D displayPlaneYVector = displayPlaneXVector.cross(zVector);

    Vector3D p1 = inWorldDisplayVector.cross(displayPlaneXVector);
    Vector3D p2 = displayPlaneYVector.cross(displayPlaneXVector);
    Vector3D p3 = inWorldDisplayVector.cross(displayPlaneYVector);

    float b = p1.getX() / p2.getX();
    float a = p3.getX() / -p2.getX();

    Vector3D forwardVector = new Vector3D(-zVector.getX(), -zVector.getY(), -0.0f);
    Vector3D delta = displayPlaneXVector.mult(a).add(forwardVector.mult(b));
    
    return delta;
  }
}