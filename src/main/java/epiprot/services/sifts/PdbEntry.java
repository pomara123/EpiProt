package epiprot.services.sifts;

import javax.swing.JCheckBox;

public class PdbEntry {
	
	private String proteinAcc;
	private String pdbId;
	private double resolution;
	private String method;
	private String chain;
	private JCheckBox checkBox;
	private String positions; 

	public PdbEntry() {
		// TODO Auto-generated constructor stub
	}

	public String getProteinAcc() {
		return proteinAcc;
	}

	public void setProteinAcc(String proteinAcc) {
		this.proteinAcc = proteinAcc;
	}

	public String getPdbId() {
		return pdbId;
	}

	public void setPdbId(String pdbId) {
		this.pdbId = pdbId;
	}

	public double getResolution() {
		return resolution;
	}

	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getChain() {
		return chain;
	}

	public void setChain(String chain) {
		this.chain = chain;
	}

	public JCheckBox getCheckBox() {
		return checkBox;
	}

	public void setCheckBox(JCheckBox checkBox) {
		this.checkBox = checkBox;
	}

	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}

}
