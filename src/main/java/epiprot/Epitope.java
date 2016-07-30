package epiprot;

import java.util.ArrayList;

public class Epitope {

	private int start;
	private int end;
	private String residues;
	private ArrayList<AminoAcid> aminoAcidList;

	public Epitope() {
		// TODO Auto-generated constructor stub
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

	public String getResidues() {
		return residues;
	}

	public void setResidues(String residues) {
		this.residues = residues;
	}

	public ArrayList<AminoAcid> getAminoAcidList() {
		return aminoAcidList;
	}

	public void setAminoAcidList(ArrayList<AminoAcid> aminoAcidList) {
		this.aminoAcidList = aminoAcidList;
	}

	@Override
	public String toString() {
		return aminoAcidList.toString();
	}

}
