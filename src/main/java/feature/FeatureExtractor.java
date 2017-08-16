package feature;

import model.MovieDictionnary;
import model.MovieDocument;

public interface FeatureExtractor {
	
	public double[] extract(MovieDocument doc, MovieDictionnary dict);
}
