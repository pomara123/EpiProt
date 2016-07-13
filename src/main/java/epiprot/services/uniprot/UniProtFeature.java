package epiprot.services.uniprot;

public class UniProtFeature {
	
	private String uniprotType;
	private String description;
	private int []evidence;
	private int position;
	private int beginPosition;
	private int endPosition;	
	
	//subcellular location and protein processing
	//unprot types
	public static final String TOPOLOGICAL_DOMAIN = "topological domain";
	public static final String TRANSMEMBRANE_REGION = "transmembrane region";
	public static final String INTRAMEMBRANE_REGION = "intramembrane region";
	public static final String SIGNAL_PEPTIDE = "signal peptide";
	public static final String PROPEPTIDE = "propeptide";
	public static final String CHAIN = "chain";
	//types of topological domains, in description
	public static final String EXTRACELLULAR = "extracellular";
	public static final String CYTOPLASMIC = "cytoplasmic";
	
	public UniProtFeature() {
		// TODO Auto-generated constructor stub
	}

	public String getUniprotType() {
		return uniprotType;
	}

	public void setUniprotType(String uniprotType) {
		this.uniprotType = uniprotType;
	}

	
	
	public String toString() {
		return this.uniprotType+":position:"+this.position+":beginPosition:"+this.beginPosition+":endPosition:"+this.endPosition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int []getEvidence() {
		return evidence;
	}

	public void setEvidence(int []evidence) {
		this.evidence = evidence;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public int getBeginPosition() {
		return beginPosition;
	}

	public void setBeginPosition(int beingPosition) {
		this.beginPosition = beingPosition;
	}

	public int getEndPosition() {
		return endPosition;
	}

	public void setEndPosition(int endPosition) {
		this.endPosition = endPosition;
	}
	
	public boolean hasMultiplePositions() {
		return position == 0;
	}

}
