package nlp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KMeans {
	
	final static double TOLERANCE = 5E-4;
	final static int MAX_ITER = 2000;
	
	public static List<double[]> apply(List<double[]> vectors, int k){
		List<double[]> seeds = selectRandomSeeds(vectors, k);
		List<double[]> centroids = new ArrayList<double[]>(seeds);
		double epsilon = Double.MAX_VALUE;
		int nIter = 0;
		while(epsilon > TOLERANCE || nIter >= MAX_ITER) {
			List<List<double[]>> Ws = initializeWs(k);
			for(int n = 0; n < vectors.size(); n++) {
				int j = findMinCentroid(vectors.get(n), centroids);
				Ws.get(j).add(vectors.get(n));
			}
			List<double[]> updatedCentroids = calculateCentroids(Ws);
			epsilon = calculateEpsilon(centroids, updatedCentroids);
			centroids = updatedCentroids;
		}
		return centroids;
	}
	
	private static List<double[]> selectRandomSeeds(List<double[]> vectors, int k){
		List<double[]> seeds = new ArrayList<double[]>();
		List<Integer> range = IntStream.range(0, 100).boxed().collect(Collectors.toList());
		Collections.shuffle(range);
		range.subList(0, k).forEach(seedIndex -> seeds.add(vectors.get(seedIndex)));
		return seeds;
	}
	
	private static List<List<double[]>> initializeWs(int k){
		List<List<double[]>> Ws = new ArrayList<List<double[]>>();
		IntStream.range(0, k).forEach(i -> Ws.add(new ArrayList<double[]>()));
		return Ws;
	}
	
	private static int findMinCentroid(double[] vector, List<double[]> centroids) {
		return IntStream.range(0, centroids.size()).reduce((a, b) -> distance(vector, centroids.get(a)) < distance(vector, centroids.get(b)) ? a : b).orElse(-1);
	}
	
	private static double distance(double[] v1, double[] v2) {
		return IntStream.range(0, Math.min(v1.length, v2.length)).mapToDouble(i -> Math.pow(v1[i] - v2[i], 2)).sum();
	}
	
	private static List<double[]> calculateCentroids(List<List<double[]>> Ws){
		return Ws.stream().map(wi -> calculateCentroid(wi)).collect(Collectors.toList());
	}
	
	private static double[] calculateCentroid(List<double[]> Wi) {
		return Wi.stream().reduce((arr1, arr2) -> sumArrays(arr1, arr2)).orElse(null);
	}
	
	private static double[] sumArrays(double[] arr1, double[] arr2) {
		int size = Math.min(arr1.length, arr2.length);
		return IntStream.range(0, size).mapToDouble(i -> (arr1[i] + arr2[i])/ (double) size).toArray();
	}
	
	private static double calculateEpsilon(List<double[]> centroids, List<double[]> updatedCentroids) {
		int size = Math.min(centroids.size(), updatedCentroids.size());
		return IntStream.range(0, size).mapToDouble(i -> distance(centroids.get(i), updatedCentroids.get(i))).sum();
	}
}
