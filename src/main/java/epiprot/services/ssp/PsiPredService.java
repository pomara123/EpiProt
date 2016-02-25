package epiprot.services.ssp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import epiprot.Protein;
import epiprot.services.Service;

public class PsiPredService extends Service {
	
	private File fastaFile;
	
	final private String runpsipredplusDir = "/usr/local/psipred/blast+/";

	public PsiPredService(File file) {
		// TODO Auto-generated constructor stub
		this.fastaFile = file;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			String cmd = runpsipredplusDir+"runpsipredplus "+fastaFile.getAbsolutePath();
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
		} catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
        }
	}
	
	public ArrayList<PsiPredAminoAcid> getAminoAcids() {
		ArrayList<PsiPredAminoAcid> aaList =  new ArrayList<PsiPredAminoAcid>();
		File file = getPredictionFile();
		System.out.println(file.getAbsolutePath());
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			Stream<String> lineStream = br.lines();
			Iterator<String> it = lineStream.iterator();
			StringBuilder confLineSb = new StringBuilder();
			StringBuilder predLineSb = new StringBuilder();
			StringBuilder residueLineSb = new StringBuilder();

		    while(it.hasNext()) {
		    	String line = it.next();
		        if(line.contains("Conf:")) {
		        	confLineSb.append(line.substring(6));
		        }
		        else if(line.contains("Pred:")) {
		        	predLineSb.append(line.substring(6));
		        }
		        else if(line.contains("AA:")) {
		        	residueLineSb.append(line.substring(6));
		        }
		    }
		    br.close();
		    
		    String conLine = confLineSb.toString();
		    String predLine = predLineSb.toString();
		    String residueLine = residueLineSb.toString();
		    for(int i = 0; i < conLine.length(); i++) {
		    	PsiPredAminoAcid aa = new PsiPredAminoAcid();
		    	aa.setConfidenceScore(Integer.parseInt(conLine.substring(i, i+1)));
		    	aa.setPosition(i+1);
		    	aa.setPrediction(predLine.substring(i, i+1));
		    	aa.setResidue(residueLine.substring(i, i+1));
		    	aaList.add(aa);
		    }
		    
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		//delete the files
		deleteFiles();
		return aaList;
	}
	
	public File getPredictionFile() {
		String fileName = fastaFile.getName().replace(".fasta", ".horiz");
		return new File(SERVICEFILEPATH+fileName);
	}
	
	private void deleteFiles() {
		File directory = new File(SERVICEFILEPATH);
		File[] files = directory.listFiles();
		for(File file: files) {
			if(file.getName().contains("psitmp") || file.getName().contains(fastaFile.getName().replace(".fasta", ""))) {
				file.delete();
			}
		}	
	}
	
	public static void main(String[]args) {
		Protein protein = new Protein("Q99523");
		System.out.println("test1");
		PsiPredService pps = new PsiPredService(protein.getFastaFile());
		pps.run();
		System.out.println("test2");
		ArrayList<PsiPredAminoAcid> aaList = pps.getAminoAcids();
		System.out.println(aaList.size());
		for (PsiPredAminoAcid aa : aaList) {
			System.out.println(aa.toString());
		}
	}
}
