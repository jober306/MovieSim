package feature;

import model.MovieDictionnary;
import model.MovieDocument;

public class CategoryFeatureExtractor implements FeatureExtractor{

	@Override
	public double[] extract(MovieDocument doc, MovieDictionnary dict) {
		return doc.categoriesScore().stream().mapToDouble(Double::doubleValue).toArray();
	}

}
