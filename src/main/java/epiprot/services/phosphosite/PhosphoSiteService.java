package epiprot.services.phosphosite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

import epiprot.Protein;
import epiprot.services.Service;

public class PhosphoSiteService extends Service {
	
	//Where the phosphosite data is
	private static final String INPUT_GZIP_FILE = "http://www.phosphosite.org/downloads/";
	
	//PTM constants
	private static final String PHOSPHOYLATED = "phosphoylated";
	private static final String ACETYLATED = "acetylated";
	private static final String METHYLATED = "methylated";
	private static final String OGALNAC = "o-galnac";
	private static final String OGLCNAC = "o-glcnac";
	private static final String SUMOYLATED = "sumoylated";
	private static final String UBIQUITINATED = "ubiquitinated";
	
	public String sequence;
	public String uniprotId;
	public boolean getPhosphorylation;
	public boolean getAcetylation;
	public boolean getMethylation;
	public boolean getOGalNAc;
	public boolean getOGlcNAc;
	public boolean getSumoylation;
	public boolean getUbiquitination;
	public ArrayList<PhosphoSiteAminoAcid> aaList = new ArrayList<PhosphoSiteAminoAcid>();
	
	public File dataFile;
	
	//constructor that sets all the PTMs to true
	public PhosphoSiteService(String sequence, String uniprotId) {
		// TODO Auto-generated constructor stub
		this.sequence = sequence;
		this.uniprotId = uniprotId;
		for (int i = 0; i < sequence.length(); i++) {
			aaList.add(new PhosphoSiteAminoAcid());
    	}
		this.getAcetylation = true;
		this.getMethylation = true;
		this.getOGalNAc = true;
		this.getOGlcNAc = true;
		this.getPhosphorylation = true;
		this.getSumoylation = true;
		this.getUbiquitination = true;
	}
	
	public PhosphoSiteService(String sequence, String uniprotId,boolean getPhosphorylation, boolean getAcetylation, boolean getMethylation, boolean getOGalNAc, boolean getOGlcNAc, boolean getSumoylation, boolean getUbiquitination) {
		this.sequence = sequence;
		this.uniprotId = uniprotId;
		for (int i = 0; i < sequence.length(); i++) {
			aaList.add(new PhosphoSiteAminoAcid());
    	}
		this.getAcetylation = getAcetylation;
		this.getMethylation = getMethylation;
		this.getOGalNAc = getOGalNAc;
		this.getOGlcNAc = getOGlcNAc;
		this.getPhosphorylation = getPhosphorylation;
		this.getSumoylation = getSumoylation;
		this.getUbiquitination = getUbiquitination;
	}
	
	public File getDatabaseTextFile(String databaseString) {
		byte[] buffer = new byte[1024];
		String databaseStringTxt = databaseString.replace("gz", "txt"); 
	    try{
	    	GZIPInputStream gzis = new GZIPInputStream(new URL(INPUT_GZIP_FILE+databaseString).openStream());	    	    	
	    	FileOutputStream out = new FileOutputStream(SERVICEFILEPATH+databaseStringTxt);
	 
	        int len;
	        while ((len = gzis.read(buffer)) > 0) {
	        	out.write(buffer, 0, len);
	        }	 
	        gzis.close();
	    	out.close();    	
	    } catch(IOException ex) {
	       ex.printStackTrace();   
	    }
	    
		return new File(SERVICEFILEPATH+databaseStringTxt);
	}
	
	@Override
	public void run() {
		if (getPhosphorylation) {
			dataFile = getDatabaseTextFile("Phosphorylation_site_dataset.gz");
			setAminoAcids(dataFile,1);
			//System.out.println(PHOSPHOYLATED);
		}
		if (getAcetylation) {
			dataFile = getDatabaseTextFile("Acetylation_site_dataset.gz");
			setAminoAcids(dataFile,2);
			//System.out.println(ACETYLATED);
		}
		if (getMethylation) {
			dataFile = getDatabaseTextFile("Methylation_site_dataset.gz");
			setAminoAcids(dataFile,3);
			//System.out.println(METHYLATED);
		}
		if (getOGalNAc) {
			dataFile = getDatabaseTextFile("O-GalNAc_site_dataset.gz");
			setAminoAcids(dataFile,4);
			//System.out.println(OGALNAC);
		}
		if(getOGlcNAc) {
			dataFile = getDatabaseTextFile("O-GlcNAc_site_dataset.gz");
			setAminoAcids(dataFile,5);
			//System.out.println(OGLCNAC);
		}
		if(getSumoylation) {
			dataFile = getDatabaseTextFile("Sumoylation_site_dataset.gz");
			setAminoAcids(dataFile,6);
			//System.out.println(SUMOYLATED);
		}
		if(getUbiquitination) {
			dataFile = getDatabaseTextFile("Ubiquitination_site_dataset.gz");
			setAminoAcids(dataFile,7);
			//System.out.println(UBIQUITINATED);
		}		
	}
	
	/**
     * setAminoAcids()
     * 
     * Each line is split into an array which is placed in an arraylist
     * array information:
     * 0: Protein name
     * 1: Accession id
     * 2: Gene
     * 3: Human chromosome location
     * 4: Modified residue
     * 5: Site group id <-- very important in order to locate equivalent location in mouse and rat
     * 6: organism
     * 7: molecular weight
     * 8: Domain
     * 9: Site peptide <-- very important, matches site to protein sequence
     * 10: Low Throughput Literature (ltp) hits
     * 11: High Throughput Literature (htp1) hits
     * 12: High Throughput CST (htp2) hits
     * 13: CST catalog #
     */
    public void setAminoAcids(File file, int ptmType) {
    			
		//get each line as an String[] separated by tab
		ArrayList <String[]> lineList = getLines(file,uniprotId,uniprotId+"-");
		
		/*
		 * Once we get all the amino acids with phosphosites, we 
		 * can now make an AminoAcid, fill in the fields and transfer the
		 * information to the ArrayList<AminoAcid> list at the proper index.
		 * If the site peptide is not found in protein sequence, add AminoAcid
		 * to end of ArrayList<AminoAcid> list. MuscleAlignment later checks
		 * if the Phosphosite ArrayList is longer in length then the protein seq.
		 * If so it will note the non-matched AminoAcid(s) at the end of the 
		 * alignment file.
		 */
		for (String[] array : lineList) {
			String peptideSeq = array[9].toUpperCase();
			int dashCount = 0;
			loop:
			for (int i = 0; i < peptideSeq.length(); i++) {
				if (Character.isLetter(peptideSeq.charAt(i))) {
					break loop;
				}
				dashCount++;
			}
			peptideSeq = peptideSeq.replaceAll("_", "");
			
			//get the position of the phosphosite to match site to arraylist
			int sitePosition = sequence.indexOf(peptideSeq)+7-dashCount;
			if (sitePosition != -1) {
				PhosphoSiteAminoAcid aa = aaList.get(sitePosition);
				aa.setResidue(sequence.substring(sitePosition, sitePosition+1));
				aa.setPosition(sitePosition+1);
				PhosphoSitePTM ptm = new PhosphoSitePTM();

				switch(ptmType) {
					case 1: aa.setPhosphorylated(true); ptm.setType(PHOSPHOYLATED);break;
					case 2: aa.setAcetylated(true); ptm.setType(ACETYLATED);break;
					case 3: aa.setMethylated(true); ptm.setType(METHYLATED);break;
					case 4: aa.setOGalNAc(true); aa.setOGlycosylated(true); ptm.setType(OGALNAC); break;
					case 5: aa.setOGlcNAc(true); aa.setOGlycosylated(true); ptm.setType(OGLCNAC);break;
					case 6: aa.setSumoylated(true); ptm.setType(SUMOYLATED);break;
					case 7: aa.setUbiquitinated(true); ptm.setType(UBIQUITINATED);break;
				}
				aa.setPTM(true);
				//set phospho peptide
				aa.setPeptide(array[9]);
				
				/*
				 * An amino acid can potentially have more than one PTM. 
				 * In order to account for this PhosphoSiteService creates
				 * a PhosphoSitePTM, places the info in the ptm object, and
				 * that is added to the PhosphoSiteAminoAcid. 
				 */
				
				//set ltp hits
				int ltpHits = getTPNumber(array,10);
				//set htp hits, have to check lit and CST hits separately and add them together
				int litHtpHits = getTPNumber(array,11);
				int cstHtphits = getTPNumber(array,12);
				
				/*
				 * The number of ltp and htp hits for a site on PhosphoSite includes hits
				 * in other similar proteins. For example, human and mouse Akt1 are very 
				 * similar. A phospho-site throughput numbers on PhosphoSite combines the 
				 * throughput numbers for human, mouse, rat (and other species if available).
				 * An unique id, "Site group id", is associated with every site. Therefore 
				 * to get all the hit numbers we have to look for the id number and gather
				 * the results.
				 */
				
				//get the id number for the site
				String siteId = array[5];
				
				//get all the lines with that site id, but not the current target protein sites
				ArrayList <String[]> siteList = getLines(file,siteId,uniprotId);
				
				//add in hits for that site id
				for (String[] siteArray : siteList) {
					ltpHits = ltpHits + getTPNumber(siteArray,10);
					litHtpHits = litHtpHits + getTPNumber(siteArray,11);
					cstHtphits = cstHtphits + getTPNumber(siteArray,12);
				}
				
				//set Through put numbers
				ptm.setHtpHits(litHtpHits+cstHtphits);
				ptm.setLtpHits(ltpHits);
				
				//set residue string info
				ptm.setResidueModification(array[4]);
				
				aa.addPTM(ptm);
			}
		}
    }
    
    /*
     * helper method for getPhosphosites()
     * reads the file and returns the lines containing the given string
     * as an ArrayList <String[]> where each String [] is a line
     * split 
     */
    private ArrayList<String []> getLines(File file, String match, String notMatch) {
    	ArrayList <String[]> lineList = new ArrayList<String[]>();
    	try {
			BufferedReader br = new BufferedReader(new FileReader (file));
			String line = null;			
			while ((line = br.readLine()) != null) {
				String [] array = line.split("\t");
				for (int i = 0; i < array.length; i++) {
					if (array[i].equals("")) {
						array[i] = 0+"";
					}
				}
				if (Arrays.asList(array).contains(match)) {
					if (!Arrays.asList(array).contains(notMatch)) {	
						lineList.add(array);
					}
					else if (Arrays.asList(array).contains(notMatch+"-")) {
						lineList.add(array);
					}
				}
			}		 
			br.close();			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return lineList;
    }
    
    /*
     * returns the throughput number (high and low) for a given site
     * index will equal 10 for low throughput
     * 11 =  literature high throughput
     * 12 = CST high throughput
     */
    private int getTPNumber(String[]array, int index) {
    	int tpNumber = 0;
    	if (array.length > index && isNumeric(array[index])) {
    		tpNumber = Integer.parseInt(array[index]);
		}
    	return tpNumber;
    }
     
    //checks to see if string is a number.
    public boolean isNumeric(String s) {
    	try {
    		double d = Double.parseDouble(s);
    	} catch(NumberFormatException nfe) {  
    		return false;
    		}
    	return true;
    }
    
    public ArrayList<PhosphoSiteAminoAcid> getAminoAcids() {
    	return aaList;
    }
    
    public static void main (String[]args) {
    	Protein protein = new Protein("P31749", true);
    	PhosphoSiteService service = new PhosphoSiteService(protein.getSequence(),protein.acc);
    	service.run();
    	ArrayList<PhosphoSiteAminoAcid> aaList = service.getAminoAcids();
    	for(PhosphoSiteAminoAcid aa: aaList) {
    		System.out.println(aa.toString());
    	}   	
    }
}
