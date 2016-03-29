package epiprot.services.jabaws;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import org.apache.commons.io.FileUtils;

import epiprot.Protein;
import epiprot.services.Service;

public class JabawsService extends Service implements JabawsConstants {

	private String msa;
	private File msaInputFile;
	private ArrayList<String> proteinAccs;
	
	private String cmd = "java -jar "+SERVICEFILEPATH+"lib/Jabaws.jar -h=http://www.compbio.dundee.ac.uk/jabaws -s=";
	private LinkedHashMap<String,String> proteinMap = new LinkedHashMap<String,String>();
	
	public JabawsService(String msa, File inputFile) {
		// TODO Auto-generated constructor stub
		this.msa = msa;
		this.msaInputFile = msaInputFile;
		//setProteinMap();
	}
	
	public JabawsService(String msa, ArrayList<String> proteinAccs) {
		// TODO Auto-generated constructor stub
		this.msa = msa;
		this.proteinAccs = proteinAccs;
		setFile();
		//setProteinMap();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			cmd = cmd + msa + " -i=" + msaInputFile.getAbsolutePath();
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            String line = "";
            while ((line = stdInput.readLine()) != null) {
            	if (line.contains("|")) {
            		System.out.println(line);
            		String[] a1 = line.split("\\s+");
            		//String[] a2 = a1[0].split("\\|");
            		String key = a1[0];
            		
            		String value = "";
            		if (proteinMap.containsKey(key)) {
            			value = proteinMap.get(key);
            		}
            		value = value + a1[1];
            		proteinMap.put(key, value);
            	}            	
            } 
            msaInputFile.delete();
		} catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();		
        }
	}
	
	public LinkedHashMap<String,String> getAlignment() {
		return proteinMap;
	}
	
	private void setProteinMap() {
		try {
			BufferedReader br = new BufferedReader(new FileReader(msaInputFile)); 
			String line;
			 while ((line = br.readLine()) != null) {
				 if (line.contains(">")) {
					 String[] a1 = line.split("\\|");
					 proteinMap.put(a1[1],"");					 
				 }
			 }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setFile() {
		for(String acc: proteinAccs) {
			msaInputFile = new File(SERVICEFILEPATH+"msaInputFile.txt");
			Protein protein = new Protein(acc,true);	
			try {
				File proteinFile = protein.getFastaFile();
				String str = FileUtils.readFileToString(proteinFile);
				PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(msaInputFile, true)));
				out.println(str);
				proteinFile.delete();
			    out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		    
		}
	}
	
	public static void main (String[]args) {
		ArrayList<String> proteinAccs = new ArrayList<String>();
		proteinAccs.add("P31749");
		proteinAccs.add("P31751");
		proteinAccs.add("Q9Y243");
		proteinAccs.add("Q9Y243-2");
		JabawsService js = new JabawsService(JabawsConstants.CLUSTALOMEGA,proteinAccs);
		js.run();
		LinkedHashMap<String,String> proteinMap = js.getAlignment();
		List<String> sequences = new ArrayList<String>(proteinMap.values());
		List<String> headers = new ArrayList<String>(proteinMap.keySet());
		for(int i = 0; i < sequences.size(); i++) {
			System.out.println(headers.get(i)+" "+sequences.get(i));
		}
	}
}
