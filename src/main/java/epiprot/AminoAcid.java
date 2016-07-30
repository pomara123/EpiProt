package epiprot;

public class AminoAcid {

	private String residue;
	private int position;

	public AminoAcid() {
		// TODO Auto-generated constructor stub
	}

	public String getResidue() {
		return residue;
	}

	public void setResidue(String residue) {
		this.residue = residue;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	@Override
	public String toString() {
		return residue + position;
	}
}
