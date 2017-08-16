package similarity;

public class CosineSimilarity implements Similarity{
	
	@Override
	public double between(double[] v1, double[] v2){
		double dot = 0.0d;
		double v1Length = 0.0d;
		double v2Length = 0.0d;
		for(int i = 0; i < Math.min(v1.length, v2.length); i++) {
			dot += v1[i] * v2[i];
			v1Length += v1[i] * v1[i];
			v2Length += v2[i] * v2[i];
		}
		if(v1Length == 0 || v2Length == 0) {
			return 0;
		}
		return dot / (Math.sqrt(v1Length) * Math.sqrt(v2Length));
	}
}
