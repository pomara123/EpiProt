package epiprot.services.epitopePrediction;

public class ABCPredPeptide {
	
	private int rank;
	private String sequence;
	private int start;
	private double score;

	public ABCPredPeptide() {
		// TODO Auto-generated constructor stub
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}
	
	@Override
	public String toString() {
		return "Rank: " + rank + " Sequence: " + sequence + " start: " + start + " score: " + score;
	}

}
