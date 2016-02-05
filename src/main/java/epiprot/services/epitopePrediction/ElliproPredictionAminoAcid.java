package epiprot.services.epitopePrediction;

import epiprot.AminoAcid;

public class ElliproPredictionAminoAcid extends AminoAcid{
	
	private int number;
	private String pdbId;
	private int start;
	private int end;
	private String peptide;
	private double score;
	private boolean isLinear;
	private String chain;

	public ElliproPredictionAminoAcid() {
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

	public boolean isLinear() {
		return isLinear;
	}

	public void setLinear(boolean isLinear) {
		this.isLinear = isLinear;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}
}
