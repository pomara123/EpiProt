package epiprot.services.epitopePrediction;

import epiprot.AminoAcid;

public class IedbEpitopePredictionAminoAcid extends AminoAcid {
	
	private int start;
	private int end;
	private String peptide;
	private double score;
	
	public IedbEpitopePredictionAminoAcid(){}

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
