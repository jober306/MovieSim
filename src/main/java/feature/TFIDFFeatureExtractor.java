package feature;

import model.MovieDictionnary;
import model.MovieDocument;

public class TFIDFFeatureExtractor implements FeatureExtractor{

	@Override
	public double[] extract(MovieDocument doc, MovieDictionnary dict) {
		double[] features = new double[dict.getVocabulary().size()];
		for(String word : doc.getBOW()) {
			features[dict.getIndex(word)] = doc.tfOf(word) * dict.getIdf(word);
		}
		return features;
	}
}
