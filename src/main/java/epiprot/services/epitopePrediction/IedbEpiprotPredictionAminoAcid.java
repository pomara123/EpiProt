package epiprot.services.epitopePrediction;

public class IedbEpiprotPredictionAminoAcid {
	
	private int position;
	private String residue;
	private int start;
	private int end;
	private String peptide;
	private double score;
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getResidue() {
		return residue;
	}
	public void setResidue(String residue) {
		this.residue = residue;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getEnd() {
		return end;
	}
	public void setEnd(int end) {
		this.end = end;
	}
	public String getPeptide() {
		return peptide;
	}
	public void setPeptide(String peptide) {
		this.peptide = peptide;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}
