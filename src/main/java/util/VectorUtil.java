package util;

import java.util.List;
import java.util.stream.IntStream;

public class VectorUtil {
	
	public static int findNearestVector(double[] vector, List<double[]> vectors) {
		return IntStream.range(0, vectors.size()).reduce((a, b) -> distance(vector, vectors.get(a)) < distance(vector, vectors.get(b)) ? a : b).orElse(-1);
	}
	
	public static double distance(double[] v1, double[] v2) {
		return IntStream.range(0, Math.min(v1.length, v2.length)).mapToDouble(i -> Math.pow(v1[i] - v2[i], 2)).sum();
	}
}
