package model;

import java.io.Serializable;

public class ScoredMovieDocument implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
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
	
	/**
	 * This is used to write this class in Json with ObjectMapper.
	 * @return
	 */
	public ScoredMovieDocumentDAO toDAO() {
		return new ScoredMovieDocumentDAO(doc.name(), score);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\"");
		sb.append(getMovieDocument().name());
		sb.append("\" : ");
		sb.append(getScore());
		return sb.toString();
	}
	
	public class ScoredMovieDocumentDAO implements Serializable{

		private static final long serialVersionUID = 1L;
		
		String name;
		double score;
		
		public ScoredMovieDocumentDAO(String name, double score) {
			this.name = name;
			this.score = score;
		}
		
		public String getName() {
			return this.name;
		}
		
		public double getScore() {
			return this.score;
		}
	}
}
