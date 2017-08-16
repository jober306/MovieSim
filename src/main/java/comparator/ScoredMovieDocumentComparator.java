package comparator;

import java.util.Comparator;

import data.ScoredMovieDocument;

public class ScoredMovieDocumentComparator implements Comparator<ScoredMovieDocument>{
	@Override
	public int compare(ScoredMovieDocument movie1, ScoredMovieDocument movie2) {
		return Double.compare(movie2.getScore(), movie1.getScore());
	}
}
