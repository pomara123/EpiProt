package epiprot.services.ssp;

import epiprot.AminoAcid;

public class JPredAminoAcid extends AminoAcid {
	
	private String prediction;
	private int confidenceScore;

	public JPredAminoAcid() {
		// TODO Auto-generated constructor stub
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}

	public int getConfidenceScore() {
		return confidenceScore;
	}

	public void setConfidenceScore(int confidenceScore) {
		this.confidenceScore = confidenceScore;
	}
	
	public String toString() {
		return "Conf: " + confidenceScore + " Pred: " + prediction + " AA: " + super.getResidue();
	}

}
