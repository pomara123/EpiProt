package epiprot.services.phosphosite;

public class PhosphoSitePTM {
	
	private String type;
	private String residueModification;
	private int ltpHits;
	private int htpHits;
	private int cstHtpHits;
	private int litHtpHits;
	
	public PhosphoSitePTM() {
		// TODO Auto-generated constructor stub
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLtpHits() {
		return ltpHits;
	}

	public void setLtpHits(int ltpHits) {
		this.ltpHits = ltpHits;
	}

	public int getHtpHits() {
		return htpHits;
	}

	public void setHtpHits(int htpHits) {
		this.htpHits = htpHits;
	}

	public int getCstHtpHits() {
		return cstHtpHits;
	}

	public void setCstHtpHits(int cstHtpHits) {
		this.cstHtpHits = cstHtpHits;
	}

	public int getLitHtpHits() {
		return litHtpHits;
	}

	public void setLitHtpHits(int litHtpHits) {
		this.litHtpHits = litHtpHits;
	}

	public String getResidueModification() {
		return residueModification;
	}

	public void setResidueModification(String residueModification) {
		this.residueModification = residueModification;
	}
	
	public String toString() {
		return this.type+":h"+this.htpHits+":l"+this.ltpHits;
	}

}
