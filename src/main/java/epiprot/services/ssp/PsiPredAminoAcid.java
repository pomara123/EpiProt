package epiprot.services.ssp;

import epiprot.AminoAcid;

public class PsiPredAminoAcid extends AminoAcid {
	
	private int confidenceScore;
	private String prediction;

	public PsiPredAminoAcid() {
		// TODO Auto-generated constructor stub
	}

	public int getConfidenceScore() {
		return confidenceScore;
	}

	public void setConfidenceScore(int confidenceScore) {
		this.confidenceScore = confidenceScore;
	}

	public String getPrediction() {
		return prediction;
	}

	public void setPrediction(String prediction) {
		this.prediction = prediction;
	}
	
	@Override
	public String toString() {
		return "Conf: " + confidenceScore + " Pred: " + prediction + " AA: " + super.getResidue();
	}

}
