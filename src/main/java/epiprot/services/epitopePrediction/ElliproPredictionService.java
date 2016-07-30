/**
 * Ellipro.java
 * 
 * This service uses an executable file, Ellipro.jar, to predict
 * epitopes. It uses a PDB entry, i.e. 3F6K, to predict epitopes.
 * It produces two tables in a txt file, one table contains continuous 
 * epitopes while the other is discontinuous.
 * 
 * To start pass the PDB entry id and chain to the constructor, create
 * the file then retrieve the results.
 */
package epiprot.services.epitopePrediction;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import epiprot.AminoAcid;
import epiprot.services.Service;

public class ElliproPredictionService extends Service{
	
	private String pdbId;
	private String chain;
	
	public ElliproPredictionService(String pdbId, String chain) {
		// TODO Auto-generated constructor stub
		this.pdbId = pdbId;
		this.chain = chain;
	}
	
	//starts the jar file
    @Override
	public void run() {
    	
        try {
            // using the Runtime exec method:
        	String cmd = "java -jar " + SERVICEFILEPATH + "lib/Ellipro.jar -i " + pdbId + " -c " + chain;
        	System.out.println(cmd);
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();           
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public ArrayList<ElliproPredictionEpitope> getEpitopeList() {
    	ArrayList<ElliproPredictionEpitope> epitopeList = new ArrayList<ElliproPredictionEpitope>();
    	try (Scanner scanner = new Scanner(new File(SERVICEFILEPATH+"output.txt"))) {
			while (scanner.hasNext()){
				String line = scanner.nextLine();
				System.out.println(line);
				if (!line.contains("Structure") && line.length() > 1) {
					line.replaceAll("\\s","");
					String[] lineArray = line.split(",");
					ElliproPredictionEpitope epe = new ElliproPredictionEpitope();
					epe.setNumber(Integer.parseInt(lineArray[0]));
					epe.setPdbId(lineArray[1]);
					ArrayList<AminoAcid> aaList = new ArrayList<AminoAcid>();
					if (lineArray[lineArray.length-1].equals("Linear")) {	
						String residues = lineArray[5];
						int start = Integer.parseInt(lineArray[3]);
						epe.setChain(lineArray[2]);
						epe.setStart(start);
						epe.setEnd(Integer.parseInt(lineArray[4]));
						epe.setResidues(residues);
						epe.setScore(Double.parseDouble(lineArray[6]));
						epe.setLinear(true);
						for (int i = 0; i < residues.length(); i++) {
							ElliproPredictionAminoAcid aa = new ElliproPredictionAminoAcid();
							aa.setResidue(residues.substring(i,i+1));
							aa.setPosition(start+i);
							aa.setChain(lineArray[2]);
							aa.setScore(Double.parseDouble(lineArray[6]));
							aa.setLinear(true);
							aa.setNumber(Integer.parseInt(lineArray[0]));
							aaList.add(aa);
						}
					}
					else if (lineArray[lineArray.length-1].equals("Discontinous")) {
						epe.setLinear(false);
						epe.setScore(Double.parseDouble(lineArray[lineArray.length-2]));
						String residues = "";
						for (int i = 2; i < lineArray.length-3; i++) {
							String residueArray = lineArray[i];
							int colonPos = residueArray.indexOf(":");
							String residue = residueArray.substring(colonPos+1,colonPos+2);
							residues = residues + residue;
							ElliproPredictionAminoAcid aa = new ElliproPredictionAminoAcid();
							aa.setChain(residueArray.substring(0, colonPos));
							aa.setResidue(residue);
							aa.setPosition(Integer.parseInt(residueArray.substring(colonPos+2)));
							aa.setScore(Double.parseDouble(lineArray[lineArray.length-2]));
							aa.setLinear(false);
							aa.setNumber(Integer.parseInt(lineArray[0]));
							aaList.add(aa);
						}
						epe.setResidues(residues);
					}
					epe.setAminoAcidList(aaList);
					epitopeList.add(epe);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return epitopeList;
    }
    
    public static void main (String []args) {
    	ElliproPredictionService eps = new ElliproPredictionService("3OW4","A");
    	System.out.println(Service.SERVICEFILEPATH);
    	eps.run();
    	ArrayList<ElliproPredictionEpitope> epitopeList = eps.getEpitopeList();
    	for (ElliproPredictionEpitope epitope : epitopeList) {
    		System.out.println(epitope.getNumber() + ". " + epitope.toString());
    	}
    }
}
