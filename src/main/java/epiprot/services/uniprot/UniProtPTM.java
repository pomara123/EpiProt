package epiprot.services.uniprot;

import epiprot.services.phosphosite.PhosphoSiteService;

public class UniProtPTM extends UniProtFeature{
	
	private String ptmType;
	private String linkage;
	private int disulfideNumber;
	
	//PTM constants
	public static final String PHOSPHOYLATED = "phosphoylated";  //p
	public static final String ACETYLATED = "acetylated";  //a
	public static final String AMIDATION = "amidation";  //d
	public static final String METHYLATED = "methylated";  //m
	public static final String SUMOYLATED = "sumoylated"; //s
	public static final String UBIQUITINATED = "ubiquitinated"; //u
	public static final String PYRROLIDONE = "pyrrolidone"; //z
	public static final String ISOMERIZATION = "isomerization"; //i
	public static final String HYDROXYLATION = "hydroxylation"; //h
	public static final String SULFATION = "sulfation"; //$
	public static final String FLAVIN = "flavin"; //f

	//glycosylation uniprot
	public static final String GLYCOSYLATED = "glycosylated"; //g when not 'o' or 'n' linked
	public static final String NLINKED = "n-linked";
	public static final String OLINKED = "o-linked";
	public static final String OTHERLINKED = "other linked";
	public static final String GALNAC = "galnac";  //o or n
	public static final String GLCNAC = "glcnac"; //\u00F6 or \u00F1
	
	//disulfide
	public static final String DISULFIDEBOND = "disulfide"; //number
	
	public UniProtPTM(){
		 super();
	}

	public String getPtmType() {
		return ptmType;
	}

	public void setPtmType(String ptmType) {
		this.ptmType = ptmType;
	}
	
	public void setType() {
		//System.out.println("++++"+this.getUniprotType().toLowerCase().equals("modified residue"));
		if(this.getUniprotType().toLowerCase().equals("modified residue")) {
			//p
			if(this.getDescription().toLowerCase().contains("phospho")) {
				this.setPtmType(UniProtPTM.PHOSPHOYLATED);
			}
			//m
			else if(this.getDescription().toLowerCase().contains("methyl")) {
				this.setPtmType(UniProtPTM.METHYLATED);
			}
			//a
			else if(this.getDescription().toLowerCase().contains("acetyl")) {
				this.setPtmType(UniProtPTM.ACETYLATED);
			}
			//d
			else if(this.getDescription().toLowerCase().contains("amide")) {
				this.setPtmType(UniProtPTM.AMIDATION);
			}
			//z
			else if(this.getDescription().toLowerCase().contains("pyrrolidone")) {
				this.setPtmType(UniProtPTM.PYRROLIDONE);
			}
			//i
			else if(this.getDescription().toLowerCase().contains("isomerization")) {
				this.setPtmType(UniProtPTM.ISOMERIZATION);
			}
			//h
			else if(this.getDescription().toLowerCase().contains("hydroxy")) {
				this.setPtmType(UniProtPTM.HYDROXYLATION);
			}
			//$
			else if(this.getDescription().toLowerCase().contains("sulfo")) {
				this.setPtmType(UniProtPTM.SULFATION);
			}
			//f
			else if(this.getDescription().toLowerCase().contains("-fad")||super.getDescription().toLowerCase().contains("fmn")) {
				this.setPtmType(UniProtPTM.FLAVIN);
			}
		}
		else if(this.getUniprotType().toLowerCase().equals("glycosylation site")){
			this.setPtmType(UniProtPTM.GLYCOSYLATED);
			if(this.getDescription().toLowerCase().contains("n-linked")) {
				this.setLinkage(UniProtPTM.NLINKED);
			}
			else if (this.getDescription().toLowerCase().contains("o-linked")) {
				this.setLinkage(UniProtPTM.OLINKED);
			}
			else {
				this.setLinkage(UniProtPTM.OTHERLINKED);
			}
			if (this.getDescription().toLowerCase().contains("glcnac")) {
				this.setPtmType(UniProtPTM.GLCNAC);
			}
			else if (this.getDescription().toLowerCase().contains("galnac")) {
				this.setPtmType(UniProtPTM.GALNAC);
			}
		}
		else if(this.getUniprotType().toLowerCase().equals("disulfide bond")){
			this.setPtmType(UniProtPTM.DISULFIDEBOND);
		}
		//System.out.println(this.toString());
	}

	public String getLinkage() {
		return linkage;
	}

	public void setLinkage(String linkage) {
		this.linkage = linkage;
	}
	
	public String toString() {
		return this.getPosition()+"|"+this.getBeginPosition()+"|"+this.getEndPosition()+"|"+this.getPtmType()+"|"+this.getLinkage();
	}

	public int getDisulfideNumber() {
		return disulfideNumber;
	}

	public void setDisulfideNumber(int disulfideNumber) {
		this.disulfideNumber = disulfideNumber;
	}

}
