package de.nachregenkommtsonne.myoarengine;

public class VectorAverager {

	private Vector[] values;

	public VectorAverager(int count) {
		values = new Vector[count];

	}

	public Vector getAvg() {
		Vector v = new Vector();
		for (int i = 0; i < values.length; i++) {
			
			v.add(values[i]);
			
		}
		v.div((float)values.length);
		return v;
	}
	
	void add(Vector v){
		for (int i = 0; i < values.length-1; i++) {
			values[i]=values[i+1];
		}
		values[values.length-1]=v;
	}
}
