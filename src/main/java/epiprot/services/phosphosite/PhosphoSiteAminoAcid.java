package epiprot.services.phosphosite;

import java.util.ArrayList;

import epiprot.AminoAcid;

public class PhosphoSiteAminoAcid extends AminoAcid{
	
	private boolean isPhosphorylated;
	private boolean isAcetylated;
	private boolean isMethylated;
	private boolean isOGlycosylated;
	private boolean isOGalNAc;
	private boolean isOGlcNAc;
	private boolean isSumoylated;
	private boolean isUbiquitinated;
	private boolean isPTM;
	
	private String peptide;
	
	private ArrayList<PhosphoSitePTM> ptmList = new ArrayList<PhosphoSitePTM>();

	public PhosphoSiteAminoAcid() {
		// TODO Auto-generated constructor stub
	}

	public boolean isAcetylated() {
		return isAcetylated;
	}

	public void setAcetylated(boolean isAcetylated) {
		this.isAcetylated = isAcetylated;
	}

	public boolean isPhosphorylated() {
		return isPhosphorylated;
	}

	public void setPhosphorylated(boolean isPhosphorylated) {
		this.isPhosphorylated = isPhosphorylated;
	}

	public boolean isMethylated() {
		return isMethylated;
	}

	public void setMethylated(boolean isMethylated) {
		this.isMethylated = isMethylated;
	}

	public boolean isOGlycosylated() {
		return isOGlycosylated;
	}

	public void setOGlycosylated(boolean isOGlycosylated) {
		this.isOGlycosylated = isOGlycosylated;
	}

	public boolean isOGalNAc() {
		return isOGalNAc;
	}

	public void setOGalNAc(boolean isOGalNAc) {
		this.isOGalNAc = isOGalNAc;
	}

	public boolean isOGlcNAc() {
		return isOGlcNAc;
	}

	public void setOGlcNAc(boolean isOGlcNAc) {
		this.isOGlcNAc = isOGlcNAc;
	}

	public boolean isSumoylated() {
		return isSumoylated;
	}

	public void setSumoylated(boolean isSumoylated) {
		this.isSumoylated = isSumoylated;
	}

	public boolean isUbiquitinated() {
		return isUbiquitinated;
	}

	public void setUbiquitinated(boolean isUbiquitinated) {
		this.isUbiquitinated = isUbiquitinated;
	}

	public String getPeptide() {
		return peptide;
	}

	public void setPeptide(String peptide) {
		this.peptide = peptide;
	}
	
	public PhosphoSiteAminoAcid(PhosphoSiteAminoAcid aminoAcidToCopy) {
		this.isAcetylated = aminoAcidToCopy.isAcetylated;
		this.isMethylated = aminoAcidToCopy.isMethylated;
		this.isOGalNAc = aminoAcidToCopy.isOGalNAc;
		this.isOGlcNAc = aminoAcidToCopy.isOGlcNAc;
		this.isOGlycosylated = aminoAcidToCopy.isOGlycosylated;
		this.isPhosphorylated = aminoAcidToCopy.isPhosphorylated;
		this.isSumoylated = aminoAcidToCopy.isSumoylated;
		this.isUbiquitinated = aminoAcidToCopy.isUbiquitinated;
		this.isPTM = aminoAcidToCopy.isPTM;
		this.peptide = aminoAcidToCopy.peptide;
		aminoAcidToCopy.setPosition(super.getPosition());
		aminoAcidToCopy.setResidue(super.getResidue());
	}

	public boolean isPTM() {
		return isPTM;
	}

	public void setPTM(boolean isPTM) {
		this.isPTM = isPTM;
	}
	
	public void addPTM(PhosphoSitePTM phosphoSitePLM) {
		ptmList.add(phosphoSitePLM);
	}
	
	public ArrayList<PhosphoSitePTM> getPhosphoSitePTMs() {
		return ptmList;
	}
	
	public String toString() {
		String aa = this.getResidue()+"|"+this.getPosition();
		for(PhosphoSitePTM ptm : this.ptmList) {
			aa = aa + "|" + ptm.toString();
		}
		return aa;
	}
}
