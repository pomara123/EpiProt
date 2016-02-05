package epiprot.services.epitopePrediction;

import epiprot.Epitope;

public class ElliproPredictionEpitope extends Epitope{
	
	private int number;
	private String pdbId;
	private String chain;
	private double score;
	private boolean isLinear;

	public ElliproPredictionEpitope() {
		// TODO Auto-generated constructor stub
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getPdbId() {
		return pdbId;
	}

	public void setPdbId(String pdbId) {
		this.pdbId = pdbId;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public boolean isLinear() {
		return isLinear;
	}

	public void setLinear(boolean isLinear) {
		this.isLinear = isLinear;
	}
}
