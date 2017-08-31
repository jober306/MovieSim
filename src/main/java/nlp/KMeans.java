package nlp;

import static util.VectorUtil.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class KMeans {
	
	final static double TOLERANCE = 5E-7;
	final static int MAX_ITER = 1000;
	
	public static List<double[]> apply(List<double[]> vectors, List<double[]> seeds){
		int k = seeds.size();
		List<double[]> centroids = new ArrayList<double[]>(seeds);
		double epsilon = Double.MAX_VALUE;
		int nIter = 0;
		while(epsilon > TOLERANCE && nIter < MAX_ITER) {
			List<List<double[]>> Ws = initializeWs(k);
			final List<double[]> finalCentroids = centroids;
			vectors.stream().forEach(vector -> Ws.get(findNearestVector(vector, finalCentroids)).add(vector));
			List<double[]> updatedCentroids = calculateCentroids(Ws, vectors);
			epsilon = calculateEpsilon(centroids, updatedCentroids);
			centroids = updatedCentroids;
			nIter++;
		}
		return centroids;
	}
	
	public static List<double[]> apply(List<double[]> vectors, int k){
		List<double[]> seeds = selectRandomSeeds(vectors, k);
		return apply(vectors, seeds);
	}
	
	private static List<double[]> selectRandomSeeds(List<double[]> vectors, int k){
		List<double[]> seeds = new ArrayList<double[]>();
		List<Integer> range = IntStream.range(0, vectors.size()).boxed().collect(Collectors.toList());
		Collections.shuffle(range);
		range.subList(0, k).forEach(seedIndex -> seeds.add(vectors.get(seedIndex)));
		return seeds;
	}
	
	private static double[] selectRandomSeed(List<double[]> vectors) {
		return vectors.get(new Random().nextInt(vectors.size()));
	}
	
	private static List<List<double[]>> initializeWs(int k){
		List<List<double[]>> Ws = new ArrayList<List<double[]>>();
		IntStream.range(0, k).forEach(i -> Ws.add(new ArrayList<double[]>()));
		return Ws;
	}
	
	private static List<double[]> calculateCentroids(List<List<double[]>> Ws, List<double[]> vectors){
		return Ws.stream().map(wi -> calculateCentroid(wi, vectors)).collect(Collectors.toList());
	}
	
	private static double[] calculateCentroid(List<double[]> Wi, List<double[]> vectors) {
		double[] centroid = Wi.stream().reduce((arr1, arr2) -> sumArrays(arr1, arr2)).orElse(selectRandomSeed(vectors));
		return centroid;
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
