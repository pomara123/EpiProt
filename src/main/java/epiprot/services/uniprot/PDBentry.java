package epiprot.services.uniprot;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

public class PDBentry extends DBentry {
	private String method;
	private double resolution;
	private String positions;
	
	public PDBentry(String id, String type, String method, double resolution, String positions){
		super(id,type);
		this.method = method;
		this.resolution = resolution;
		this.positions = positions;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public double getResolution() {
		return resolution;
	}

	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

	public String getPositions() {
		return positions;
	}

	public void setPositions(String positions) {
		this.positions = positions;
	}
	
	public ArrayList<PDBchain> getPdbChainList() {
		ArrayList<PDBchain> pdbChainList = new ArrayList<PDBchain>();
		String positions = getPositions();
		int numCommas = StringUtils.countOccurrencesOf(positions, ",");
		int count = 0;
		do {
			PDBchain pdbChain = new PDBchain();
			int equalPos = 0;
	    	int dashPos = 0;
	    	int commaPos = 0;
	    	    	
	    	for (int i = 0; i < positions.length(); i++) {
	    		if (positions.charAt(i) == '=') {
	    			equalPos = i; 
	    		}
	    		else if (positions.charAt(i) == '-') {
	    			dashPos = i;
	    		}
	    		else if (positions.charAt(i) == ',') {
	    			commaPos = i;
	    			break;
	    		}
	    	}
			pdbChain.setChain(positions.substring(0,equalPos));
			pdbChain.setBegin(Integer.parseInt(positions.substring(equalPos+1,dashPos)));
			pdbChain.setEnd(Integer.parseInt(positions.substring(dashPos+1,commaPos)));
			pdbChainList.add(pdbChain);
			if (commaPos != 0) {
				positions = positions.substring(commaPos+2);
			}
			count++;
		} while (count <= numCommas);
		return pdbChainList;
	}
}
