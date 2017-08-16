package data;

import model.MovieDocument;

public class ScoredMovieDocument {
	
	MovieDocument doc;
	double score;
	
	public ScoredMovieDocument(MovieDocument doc, double score) {
		this.doc =doc;
		this.score =score;
	}
	
	public MovieDocument getMovieDocument(){
		return doc;
	}
	
	public double getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(getMovieDocument().getName());
		sb.append("\" : ");
		sb.append(getScore());
		return sb.toString();
	}
}
