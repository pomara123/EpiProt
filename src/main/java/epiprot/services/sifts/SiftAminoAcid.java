package epiprot.services.sifts;

import epiprot.AminoAcid;

public class SiftAminoAcid extends AminoAcid{
	
	private String pdbResidue;
	private double resolution;
	private String chain;
	private String pdbId;
	private int pdbPosition;
	private String uniprotId;
	private String secondaryStructure;

	public SiftAminoAcid() {
		// TODO Auto-generated constructor stub
	}

	public String getPdbResidue() {
		return pdbResidue;
	}

	public void setPdbResidue(String pdbResidue) {
		this.pdbResidue = pdbResidue;
	}

	public double getResolution() {
		return resolution;
	}

	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public String getPdbId() {
		return pdbId;
	}

	public void setPdbId(String pdbId) {
		this.pdbId = pdbId;
	}

	public int getPdbPosition() {
		return pdbPosition;
	}

	public void setPdbPosition(int pdbPosition) {
		this.pdbPosition = pdbPosition;
	}

	public String getUniprotId() {
		return uniprotId;
	}

	public void setUniprotId(String uniprotId) {
		this.uniprotId = uniprotId;
	}

	public String getSecondaryStructure() {
		return secondaryStructure;
	}

	public void setSecondaryStructure(String secondaryStructure) {
		this.secondaryStructure = secondaryStructure;
	}
	
	public String toString() {
		return getResidue()+"|"+getPosition()+"|"+secondaryStructure;
	}
	

}
