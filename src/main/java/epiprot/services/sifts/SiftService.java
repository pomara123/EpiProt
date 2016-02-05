package epiprot.services.sifts;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.TimerTask;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import epiprot.services.Service;

public class SiftService extends Service {

	private String pdbId;
	private String acc;
	private double structureResolution;
	private Document doc = null;
	
	public SiftService (String pdbId, String acc, double structureResolution) {
		this.acc = acc;
		this.pdbId = pdbId;
		this.structureResolution = structureResolution;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
	        URL url = new URL("http://www.rcsb.org/pdb/files/"+pdbId+".sifts.xml");
	        URLConnection connection = url.openConnection();
	        connection.setConnectTimeout(10000);
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        dbf.setNamespaceAware(false);
	        dbf.setValidating(false);
	        dbf.setFeature("http://xml.org/sax/features/namespaces", false);
	        dbf.setFeature("http://xml.org/sax/features/validation", false);
	        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
	        dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        InputStream inputStream = connection.getInputStream();
    		doc = db.parse(inputStream);   			
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	} catch (ParserConfigurationException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (SAXException e) {
    		e.printStackTrace();
    	}
	}
	
	public ArrayList<SiftAminoAcid> getAminoAcids() {
		this.run();
		ArrayList<SiftAminoAcid> aminoAcidList = new ArrayList<SiftAminoAcid>();
		NodeList list = doc.getElementsByTagName("residue");
		for (int i = 0; i < list.getLength(); i++) {
			Element resElement = (Element) list.item(i);
			if (resElement.getAttribute("dbSource").equals("PDBe")) {
				SiftAminoAcid aminoAcid = new SiftAminoAcid();
				String pdbResidue = resElement.getAttribute("dbResName");
				NodeList childNodes1 = resElement.getChildNodes();
				aminoAcid.setPdbResidue(pdbResidue);
				aminoAcid.setResolution(structureResolution);
				for (int j = 0; j < childNodes1.getLength(); j++) {
					Node childNode = childNodes1.item(j);
					if (childNode.getNodeType()==Node.ELEMENT_NODE) {
						Element childElement = (Element) childNode;
						if (childElement.getAttribute("dbSource").equals("PDB")) {
							String pdbPosString = getNumber(childElement.getAttribute("dbResNum"));
							int pdbPosition = Integer.parseInt(pdbPosString);
							String chain = childElement.getAttribute("dbChainId");
							String pdbId = childElement.getAttribute("dbAccessionId");
							aminoAcid.setPdbPosition(pdbPosition);
							aminoAcid.setChain(chain);
							aminoAcid.setPdbId(pdbId);
						}
						else if (childElement.getAttribute("dbSource").equals("UniProt")) {
							int uniprotPosition = Integer.parseInt(childElement.getAttribute("dbResNum"));
							String uniprotId = childElement.getAttribute("dbAccessionId");
							String uniprotResidue = childElement.getAttribute("dbResName");
							aminoAcid.setPosition(uniprotPosition);
							aminoAcid.setUniprotId(uniprotId);
							aminoAcid.setResidue(uniprotResidue);
						}
						else if (childElement.getAttribute("dbSource").equals("PDBe") && childElement.getAttribute("property").equals("codeSecondaryStructure")) {
							String secondaryStructure = childElement.getTextContent();
							aminoAcid.setSecondaryStructure(secondaryStructure);
						}
					}
				}
				if (aminoAcid.getUniprotId() != null && aminoAcid.getUniprotId().equals(acc)) {
					aminoAcidList.add(aminoAcid);
				}
			}
		}
		return aminoAcidList;
	}
	
	private String getNumber(String s) {
		String number = "";
		for (int i = 0; i < s.length(); i++) {
			if (Character.isDigit(s.charAt(i))) {
				number += s.charAt(i);
			}
		}
		return number;
	}
	
	public static void main (String [] args) {
		SiftService siftReader = new SiftService("3f6k","Q99523",2.70);
		ArrayList<SiftAminoAcid> aminoAcidList = siftReader.getAminoAcids();
		for (int i = 0; i < aminoAcidList.size(); i++) {
			SiftAminoAcid aminoAcid = aminoAcidList.get(i);
			System.out.println(aminoAcid.toString());
		}
	}

}
