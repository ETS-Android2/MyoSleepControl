package de.nachregenkommtsonne.myoarengine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.thalmic.myo.Quaternion;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.SensorManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import de.nachregenkommtsonne.myoarengine.utility.MovementCalculator;
import de.nachregenkommtsonne.myoarengine.utility.Vector3D;
import de.nachregenkommtsonne.myoarengine.utility.VectorAverager;

public class MyoArRenderer implements Renderer
{
	private int _width = 0;
	private int _height = 0;
	private float[] _rotationMatrix = new float[16];
	private Vector3D _position;
	private Vector3D _displayVector;
	private Context _context;
	private MyoArRenderBridge _nativeMyoArRenderer;

	private VectorAverager _gravitationalVector;
	private VectorAverager _magneticVector;
	private Quaternion _quaternion;

	public MyoArRenderer(VectorAverager gravitationalVector,
			VectorAverager magneticVector, Context context)
	{
		_gravitationalVector = gravitationalVector;
		_magneticVector = magneticVector;
		_position = new Vector3D();
		_displayVector = new Vector3D(0.0f, 0.0f, 0.0f);
		_context = context;
		_nativeMyoArRenderer = new MyoArRenderBridge();
		_quaternion = new Quaternion();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		int texIDAscii = loadTexture(gl, R.drawable.font);
		int texIDRasen = loadTexture(gl, R.drawable.rasen);
		int texIDSky = loadTexture(gl, R.drawable.sky);
		
		String script = _context.getResources().getString(R.string.script);
		
		_nativeMyoArRenderer.onSurfaceCreated(script, texIDAscii, texIDRasen, texIDSky);
	}
	
	private int loadTexture(GL10 gl, int resID)
	{
		int[] textur = new int[1];
		gl.glGenTextures(1, textur, 0);

		final Bitmap bitmap = BitmapFactory.decodeResource(_context.getResources(),	resID);

		gl.glBindTexture(GL10.GL_TEXTURE_2D, textur[0]);

		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);

		GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

		bitmap.recycle();
		
		return textur[0];
	}

	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		_width = width;
		_height = height;

		_nativeMyoArRenderer.onSurfaceChanged(width, height);
	}

	public void onDrawFrame(GL10 gl)
	{
		if (_width == 0 || _height == 0)
			return;

		Vector3D gavitationalVector = _gravitationalVector.getAverage();
		gavitationalVector.normalize();

		Vector3D magneticVector = _magneticVector.getAverage();
		magneticVector.normalize();

		SensorManager.getRotationMatrix(_rotationMatrix, null,
				gavitationalVector.getValues(), magneticVector.getValues());

		Vector3D xVector = new Vector3D(_rotationMatrix[0], _rotationMatrix[4], _rotationMatrix[8]);
		Vector3D yVector = new Vector3D(_rotationMatrix[1], _rotationMatrix[5], _rotationMatrix[9]);
		Vector3D zVector = new Vector3D(_rotationMatrix[2], _rotationMatrix[6], _rotationMatrix[10]);

		Vector3D delta = new MovementCalculator().getMovementDelta(_displayVector,
				xVector, yVector, zVector);

		if (delta.isValid())
			_position = _position.sub(delta);

		_nativeMyoArRenderer.draw(
				_position.getX(), _position.getY(), _position.getZ(),
				_rotationMatrix[0], _rotationMatrix[1], _rotationMatrix[2], _rotationMatrix[3],
				_rotationMatrix[4], _rotationMatrix[5], _rotationMatrix[6], _rotationMatrix[7],
				_rotationMatrix[8], _rotationMatrix[9], _rotationMatrix[10], _rotationMatrix[11],
				_rotationMatrix[12], _rotationMatrix[13], _rotationMatrix[14], _rotationMatrix[15],
				(float)_quaternion.x(), (float)_quaternion.y(), (float)_quaternion.z(), (float)_quaternion.w());

	}

	public void setMovementVector(Vector3D vector)
	{
		_displayVector = vector;
	}



	public void orientationData(Quaternion quaternion)
	{
		_quaternion = quaternion;
	}
}