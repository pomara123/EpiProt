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
	public String getUniprotNameID() {
		NodeList nameList = getNodeList("name");
		Node nameNode = nameList.item(0);
		Element name = (Element) nameNode; 
		return name.getTextContent();
    }
	
	//converts target name to mouse target name (ex SORT_HUMAN to SORT_MOUSE)
    public String convertNameToMouse () {
        String targetName = getUniprotNameID();        
        for (int i = 0; i < targetName.length(); i++) {
            if (targetName.charAt(i) == '_') {
                targetName = targetName.substring(0,i+1);
            }
        }
        return targetName + "MOUSE";
    }
    
  //converts target name to RAT target name (ex SORT_HUMAN to SORT_RAT)
    public String convertNameToRat () {
        String targetName = getUniprotNameID();
        for (int i = 0; i < targetName.length(); i++) {
            if (targetName.charAt(i) == '_') {
                targetName = targetName.substring(0,i+1);
            }
        }
        return targetName + "RAT";
    }

    //converts target name to human target name (ex SORT_MOUSE to SORT_HUMAN)
    public String convertNameToHuman () {
        String targetName = getUniprotNameID();
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
	        }
    	}
    	return dbEntryList;
    }
    
    public ArrayList<PDBentry> getPDBentries() {
    	ArrayList<PDBentry> pdbEntryList = new ArrayList<PDBentry>();
    	ArrayList<DBentry> dbEntryList = getDBentryList();
    	for (DBentry dbEntry: dbEntryList) {
    		if (dbEntry.getType().equals("PDB")) {
    			String method = null;
    			double resolution = 0;
    			String positions = null;
    			for (DBproperty dbProperty: dbEntry.getDbPropertiesList()) {
        			if (dbProperty.getType().equals("method")) {
        				method = dbProperty.getValue();
        			}
        			else if (dbProperty.getType().equals("resolution")) {
        				resolution = Double.parseDouble(dbProperty.getValue());
        			}
        			else if (dbProperty.getType().equals("chain")) {
        				positions = dbProperty.getValue();
        			}
    			}
				PDBentry pdbEntry = new PDBentry(dbEntry.getId(),dbEntry.getType(),method,resolution,positions);
				pdbEntryList.add(pdbEntry);
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
    
    public File getFastaFile() {
    	UniprotService us = new UniprotService(acc);
    	return us.fetchFastaFile();
    }
    
    public static void main (String [] args) {
    	Protein protein = new Protein ("Q99523");
    	protein.initUniprotService();
    	System.out.println(protein.getSequence());
    }
}
