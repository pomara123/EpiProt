package epiprot.services.uniprot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import epiprot.services.DocumentNullException;
import epiprot.services.Service;

public class UniprotService extends Service {
	
	public String id;
	public Document doc;
	
	public UniprotService (String id) throws DocumentNullException {
		this.id = id;
		this.doc = getDocument();
		if (doc == null) {
			throw new DocumentNullException("UniprotService.java returning null.");
		}
	}

	public File fetchFastaFile() {
		File file = null;
    	try {
			URL url = new URL("http://www.uniprot.org/uniprot/"+id+".fasta");
			file = new File(FASTAFILEPATH + id + ".fasta");
			PrintWriter writer = new PrintWriter(file);
			writer.close();
			FileUtils.copyURLToFile(url, file);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
    	return file;
	}
	
	public Document getDocument() {
		Document doc = null;
    	try {
	        URL url = new URL("http://www.uniprot.org/uniprot/"+id+".xml");
	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        doc = db.parse(url.openStream());
    	} catch (MalformedURLException e) {
    		e.printStackTrace();
    	} catch (ParserConfigurationException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	} catch (SAXException e) {
    		e.printStackTrace();
    	}
        return doc;
	}
	
	public static void main (String[]args) {
		UniprotService uniprot;
		try {
			uniprot = new UniprotService("Q99523");
			System.out.println(uniprot.FASTAFILEPATH);

		} catch (DocumentNullException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
