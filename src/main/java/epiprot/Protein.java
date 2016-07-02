package epiprot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import epiprot.services.DocumentNullException;
import epiprot.services.uniprot.DBentry;
import epiprot.services.uniprot.DBproperty;
import epiprot.services.uniprot.PDBentry;
import epiprot.services.uniprot.UniProtAminoAcid;
import epiprot.services.uniprot.UniProtPTM;
import epiprot.services.uniprot.UniprotService;

public class Protein {
	
	public String acc;
	public Document uniprotDoc;
	
	public Protein (String acc) {
		this.acc = acc;
	}
	
	public Protein(String acc, boolean initUniprotService) {
		this.acc = acc;
		if (initUniprotService) {
			initUniprotService();
		}
	}
	
	public void initUniprotService() {
		UniprotService uniprotService = new UniprotService(acc);
		uniprotService.run();
		this.uniprotDoc = uniprotService.getDocument();
	}
	
	//returns the NodeList with the tag name
    public NodeList getNodeList(String tag) {
        NodeList list = uniprotDoc.getElementsByTagName(tag);
        return list;
    }
    
    //returns the child nodes from an element
    public NodeList getChildNodeList(Element element) {
        NodeList list = element.getChildNodes();
        return list;
    }
    
    //returns the Uniprot Name Id i.e. SORT_HUMAN
	public String getUniprotNameId() {
		NodeList nameList = getNodeList("name");
		Node nameNode = nameList.item(0);
		Element name = (Element) nameNode; 
		return name.getTextContent();
    }
	
	//converts target name to mouse target name (ex SORT_HUMAN to SORT_MOUSE)
    public String convertNameToMouse () {
        String targetName = getUniprotNameId();        
        for (int i = 0; i < targetName.length(); i++) {
            if (targetName.charAt(i) == '_') {
                targetName = targetName.substring(0,i+1);
            }
        }
        return targetName + "MOUSE";
    }
    
  //converts target name to RAT target name (ex SORT_HUMAN to SORT_RAT)
    public String convertNameToRat () {
        String targetName = getUniprotNameId();
        for (int i = 0; i < targetName.length(); i++) {
            if (targetName.charAt(i) == '_') {
                targetName = targetName.substring(0,i+1);
            }
        }
        return targetName + "RAT";
    }

    //converts target name to human target name (ex SORT_MOUSE to SORT_HUMAN)
    public String convertNameToHuman () {
        String targetName = getUniprotNameId();
        for (int i = 0; i < targetName.length(); i++) {
            if (targetName.charAt(i) == '_') {
                targetName = targetName.substring(0,i+1);
            }
        }
        return targetName + "HUMAN";
    }
    
    //returns the protein amino acid sequence
    public String getSequence() {
		NodeList sequenceList = getNodeList("sequence");
		Node sequenceNode = sequenceList.item(sequenceList.getLength()-1);
		Element sequence = (Element) sequenceNode;
		String seq = sequence.getTextContent();
		seq = seq.replaceAll("[\n\r]", "");
		return seq;
    }
    
    //returns the common name of the protein organism
    public String getOrganism() {
    	String organismName = "";
    	try {
			NodeList organismList = getNodeList("organism");
			for (int j = 0; j < organismList.getLength(); j++) {
		    	Node organismNode = organismList.item(j);	    	
		    	NodeList organismPropertyList = organismNode.getChildNodes();
		    	for (int i = 0; i < organismPropertyList.getLength(); i++) {
			    	Node propertyNode = organismPropertyList.item(i);		        
		            if (propertyNode.getNodeType()==Node.ELEMENT_NODE && propertyNode.getNodeName().equals("name")) {
		            	Element property = (Element) propertyNode;
		            	String type = property.getAttribute("type");
		            	if (type.equals("common")){       	    	 
		            		organismName = property.getTextContent();
		            	}
		            }
		    	}
			}
    	} catch (NullPointerException e) {}
		return organismName;
    }
    
    //returns the database the protein is in i.e. Swiss-Prot
    public String getDatabase() {
    	String database = "";
    	try {
	    	NodeList entryList = getNodeList("entry");
			Node entryNode = entryList.item(0);
	        if (entryNode.getNodeType() == Node.ELEMENT_NODE) {
	            Element entry = (Element) entryNode;
	            database = entry.getAttribute("dataset");
	        }
    	} catch (NullPointerException e){}
		return database;
    }
    
    public ArrayList<DBentry> getDBentryList() {
    	ArrayList<DBentry> dbEntryList = new ArrayList<DBentry>();
    	NodeList dbRefList = getNodeList("dbReference");
    	for (int i = 0; i < dbRefList.getLength(); i++) {
    		Node dbRefNode = dbRefList.item(i);
	        if (dbRefNode.getNodeType()==Node.ELEMENT_NODE) {
	            Element dbRefElement = (Element) dbRefNode;
	            DBentry dbEntry = new DBentry(dbRefElement.getAttribute("id"),dbRefElement.getAttribute("type"));
	            //System.out.println("getDBentryList "+dbRefElement.getAttribute("id")+" "+dbRefElement.getAttribute("type"));
	            NodeList propertyList = dbRefNode.getChildNodes();
	            ArrayList<DBproperty> dbPropertyList = new ArrayList<DBproperty>();
	            for (int j = 0; j < propertyList.getLength(); j++) {
	                Node propertyNode = propertyList.item(j);
	                if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
	                    Element propertyElement = (Element) propertyNode;
	                    DBproperty dbProperty = new DBproperty(propertyElement.getAttribute("type"),propertyElement.getAttribute("value"));
	                    dbPropertyList.add(dbProperty);
	                }
	            }
	            dbEntry.setDbPropertiesList(dbPropertyList);
	            dbEntryList.add(dbEntry);
	        }
    	}
    	return dbEntryList;
    }
    
    public ArrayList<PDBentry> getPDBentries() {
		System.out.println("Test");
    	ArrayList<PDBentry> pdbEntryList = new ArrayList<PDBentry>();
    	ArrayList<DBentry> dbEntryList = getDBentryList();
    	for (DBentry dbEntry: dbEntryList) {
    		if (dbEntry.getType().equals("PDB")) {
    			System.out.println("getPDBentries "+dbEntry.getId()+" "+dbEntry.getType());
    			String method = null;
    			double resolution = 0;
    			String positions = null;
    			if (dbEntry.getDbPropertiesList() != null && dbEntry.getDbPropertiesList().size() > 0) {
	    			for (DBproperty dbProperty: dbEntry.getDbPropertiesList()) {
	    				System.out.println(dbProperty.getType());
	        			if (dbProperty.getType().equals("method")) {
	        				method = dbProperty.getValue();
	        			}
	        			else if (dbProperty.getType().equals("resolution")) {
	        				resolution = Double.parseDouble(dbProperty.getValue());
	        			}
	        			else if (dbProperty.getType().equals("chains")) {
	        				positions = dbProperty.getValue();
	        			}
	    			}
	    			System.out.println();
					PDBentry pdbEntry = new PDBentry(dbEntry.getId(),dbEntry.getType(),method,resolution,positions);
					pdbEntryList.add(pdbEntry);
    			}
    		}
    	}
    	return pdbEntryList;
    }
    
    //returns the isoform sequence of a target protein.
    public String getIsoformSequence(String number) {
    	UniprotService uniprotService;
    	String isoformSequence = "";
		try {
			uniprotService = new UniprotService(acc+"-"+number);
			File file = uniprotService.fetchFastaFile();
	    	BufferedReader br = new BufferedReader(new FileReader(file));
	    	String line;
		    while ((line = br.readLine()) != null) {
		       if (!line.contains(">")) {
		    	   isoformSequence = isoformSequence + line;
		       }
		    }
		    br.close();
		    isoformSequence = isoformSequence.replaceAll("[\\r\\n]", "");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	   	
    	return isoformSequence;
    }
    
    //returns primary name, ex. SORT1, used by getIsoformSeq() to help get the sequence
    public String getPrimaryName () {
    	NodeList nameList = getNodeList("name");
    	for (int i = 0; i < nameList.getLength(); i++) {
			Node nameNode = nameList.item(i);
	        if (nameNode.getNodeType()==Node.ELEMENT_NODE) {
	        	Element name = (Element) nameNode;
	        	String nameType = name.getAttribute("type");
	        	if (nameType.equals("primary")) {
	        		return name.getTextContent();
	        	}
	        }
    	}
		return "";
    }
    
    public ArrayList<UniProtAminoAcid> getAminoAcids() {
    	ArrayList<UniProtAminoAcid> aaList = new ArrayList<UniProtAminoAcid>();
    	for(int i = 0; i < getSequence().length(); i++) {
    		aaList.add(new UniProtAminoAcid());
    	}
    	
    	ArrayList<UniProtPTM> ptmList = getPTMs();
    	for(UniProtPTM ptm: ptmList) {
    		
    	}
    	return aaList;
    }
    
    public ArrayList<UniProtPTM> getPTMs() {
    	ArrayList<UniProtPTM> ptmList = new ArrayList<UniProtPTM>();
    	NodeList featureNodeList = getNodeList("feature");
    	for(int i = 0; i < featureNodeList.getLength(); i++) {
    		Node featureNode = featureNodeList.item(i);
    		if(featureNode.getNodeType() == Node.ELEMENT_NODE) {
    			Element featureElement = (Element) featureNode;
    			if(featureElement.hasAttribute("type")) {
    				String featureType = featureElement.getAttribute("type");
    				if(featureType.equals("modified residue") || featureType.equals("glycosylation site") || 
    						featureType.equals("disulfide bond") || featureType.equals("cross-link")){
    					UniProtPTM uniProtPTM = new UniProtPTM();
    					uniProtPTM.setUniprotType((featureElement.getAttribute("type")).toLowerCase());
    					uniProtPTM.setDescription((featureElement.getAttribute("description")).toLowerCase());    					
    					uniProtPTM.setEvidence(getEvidenceArray(featureElement.getAttribute("evidence")));
    					/*
    					 * after uniprottype and description have been set, you can set the type.
    					 * UniProtPTM will pull info from the description and uniprottype in order
    					 * to set the type and linkage (if applicable)
    					 */
    					if(featureType.equals("disulfide bond")) {
    						uniProtPTM.setBeginPosition(Integer.parseInt(((Element) featureElement.getElementsByTagName("begin").item(0)).getAttribute("position")));
    						uniProtPTM.setEndPosition(Integer.parseInt(((Element) featureElement.getElementsByTagName("end").item(0)).getAttribute("position")));
    					}
    					else {
    						System.out.println("**********"+Integer.parseInt(((Element) featureElement.getElementsByTagName("position").item(0)).getAttribute("position")));
    						uniProtPTM.setPosition(Integer.parseInt(((Element) featureElement.getElementsByTagName("position").item(0)).getAttribute("position")));
    					}
    					//System.out.println(uniProtPTM.getPosition()+":"+uniProtPTM.getBeginPosition()+":"+uniProtPTM.getEndPosition()+":"+uniProtPTM.getUniprotType()+":"+uniProtPTM.getDescription()+":"+uniProtPTM.getEvidence());
    					uniProtPTM.setType(); 
    					ptmList.add(uniProtPTM);
    				}
    			}    	    		
    		}    		
    	}
    	return ptmList;
    }
    
    private int [] getEvidenceArray(String evidence) {
    	String [] stringEvidenceArray = evidence.split("\\s+");
    	int [] evidenceArray = new int [stringEvidenceArray.length];
    	
    	for(int i = 0; i < stringEvidenceArray.length; i++) {
    		if(isNumeric(stringEvidenceArray[i])) {
    			evidenceArray[i] = Integer.parseInt(stringEvidenceArray[i]);
    		}
    	}
    	return evidenceArray;
    }
    
    public static boolean isNumeric(String str)  
    {  
      try  
      {  
        double d = Double.parseDouble(str);  
      }  
      catch(NumberFormatException nfe)  
      {  
        return false;  
      }  
      return true;  
    }
    
    public File getFastaFile() {
    	UniprotService us = new UniprotService(acc);
    	return us.fetchFastaFile();
    }
    
    public static void main (String [] args) {
    	Protein protein = new Protein ("Q99523",true);
    	protein.getPDBentries();
    }
}
