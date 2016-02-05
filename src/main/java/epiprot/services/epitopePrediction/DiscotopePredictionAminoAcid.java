package epiprot.services.epitopePrediction;

import epiprot.AminoAcid;

public class DiscotopePredictionAminoAcid extends AminoAcid{
	
	private String chain;
	private int residueNo;
	private int contactNo;
	private double propensityScore;
	private double score;
	private boolean isPredictedEpitope;

	public DiscotopePredictionAminoAcid() {
		// TODO Auto-generated constructor stub
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public int getResidueNo() {
		return residueNo;
	}

	public void setResidueNo(int residueNo) {
		this.residueNo = residueNo;
	}

	public int getContactNo() {
		return contactNo;
	}

	public void setContactNo(int contactNo) {
		this.contactNo = contactNo;
	}

	public double getPropensityScore() {
		return propensityScore;
	}

	public void setPropensityScore(double propensityScore) {
		this.propensityScore = propensityScore;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public boolean isPredictedEpitope() {
		return isPredictedEpitope;
	}

	public void setPredictedEpitope(boolean isPredictedEpitope) {
		this.isPredictedEpitope = isPredictedEpitope;
	}

}
