package de.nachregenkommtsonne.myoarengine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.hardware.SensorManager;
import android.opengl.GLU;
import android.opengl.GLSurfaceView.Renderer;
import de.nachregenkommtsonne.myoarengine.utility.Vector;
import de.nachregenkommtsonne.myoarengine.utility.VectorAverager;

public class MyoArRenderer implements Renderer
{
	private DummyWorldRenderer dummyWorldRenderer = new DummyWorldRenderer();
	private int _width = 0;
	private int _height = 0;
	private float[] _rotationMatrix = new float[16];
	private Vector _position;
	private Vector _movementVector;

	private VectorAverager _gravitationalVector;
	private VectorAverager _magneticVector;

	public MyoArRenderer(VectorAverager gravitationalVector,
			VectorAverager magneticVector)
	{
		_gravitationalVector = gravitationalVector;
		_magneticVector = magneticVector;
		_position = new Vector();
		_movementVector = new Vector(0.0f, 0.0f, 0.0f);
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		_width = width;
		_height = height;

		gl.glViewport(0, 0, width, height);

		gl.glEnable(GL10.GL_POINT_SMOOTH);
		gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_NICEST);
	}

	public void onDrawFrame(GL10 gl)
	{
		if (_width == 0 || _height == 0)
			return;

		Vector gavitationalVector = _gravitationalVector.getAverage();
		gavitationalVector.normalize();

		Vector magneticVector = _magneticVector.getAverage();
		magneticVector.normalize();

		SensorManager.getRotationMatrix(_rotationMatrix, null,
				gavitationalVector.getValues(), magneticVector.getValues());

		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		GLU.gluPerspective(gl, 90.0f, (float) _width / _height, 0.1f, 200.0f);

		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		gl.glLoadMatrixf(_rotationMatrix, 0);

		float movementX = _movementVector.getX();
		float movementY = _movementVector.getY();
		
		Vector upVector = new Vector(0.0f, 0.0f, 1.0f);

		Vector xVector = new Vector(_rotationMatrix[0], _rotationMatrix[4], _rotationMatrix[8]);
    Vector yVector = new Vector(_rotationMatrix[1], _rotationMatrix[5], _rotationMatrix[9]);
    Vector zVector = new Vector(_rotationMatrix[2], _rotationMatrix[6], _rotationMatrix[10]);
    
    
    Vector ebenenSchnittpunkt = zVector.cross(upVector); // x
    Vector ebenenSchnittpunktUp = ebenenSchnittpunkt.cross(zVector); // y

    Vector inWorldDisplayMovementVector = xVector.mult(movementX).add(yVector.mult(movementY)); // v = a*x + b*y
    
    Vector p1 = inWorldDisplayMovementVector.cross(ebenenSchnittpunkt);
    Vector p2 = ebenenSchnittpunktUp.cross(ebenenSchnittpunkt);
    
    float b = p1.getX() / p2.getX();

    Vector p3 = inWorldDisplayMovementVector.cross(ebenenSchnittpunktUp);
    Vector p4 = ebenenSchnittpunkt.cross(ebenenSchnittpunktUp);
    
    float a = p3.getX() / p4.getX();

    
    
    Vector forwardVector = ebenenSchnittpunkt.cross(upVector);
    Vector inWorldMovementVector = ebenenSchnittpunkt.mult(a).add(forwardVector.mult(b));
    
    
    

    
    
    

    Vector left = inWorldMovementVector.normalize();
    left = left.mult(_movementVector.getLength());
    
    
    
    /*Vector inWorldMovementVector = xVector.mult(movementX).add(yVector.mult(movementY));

		Vector left = inWorldMovementVector.cross(upVector);
		left = left.cross(upVector);
		
		left = left.normalize();
		left = left.mult(_movementVector.getLength());*/
		
		if (left.isValid())
		  _position = _position.add(left);
		
		gl.glTranslatef(_position.getX(), _position.getY(), _position.getZ());

		dummyWorldRenderer.render(gl, inWorldMovementVector);
	}
	
	public void setMovementVector(Vector vector)
	{
	  _movementVector = vector;
	}
}