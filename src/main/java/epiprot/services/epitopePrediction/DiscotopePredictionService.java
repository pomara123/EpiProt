package epiprot.services.epitopePrediction;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import epiprot.services.Service;

public class DiscotopePredictionService extends Service{
	
	private String pdbId;
	private String chain;
	
	private final String DISCOTOPE_PATH = "/Users/Patrick/Documents/discotope-1.1/";
	
	private File dataFile;
	
	public DiscotopePredictionService(String pdbId, String chain) {
		// TODO Auto-generated constructor stub
		this.pdbId = pdbId;
		this.chain = chain;
	}
	
	public void run() {
		try {
			URL url = new URL("http://www.rcsb.org/pdb/files/"+pdbId+".pdb");
			dataFile = new File(SERVICEFILEPATH + pdbId + ".pdb");
			PrintWriter writer = new PrintWriter(dataFile);
			writer.close();
			FileUtils.copyURLToFile(url, dataFile);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
	}
	
	public String getOutput() {
        String output = "";

		try {
            // using the Runtime exec method:
        	String cmd = DISCOTOPE_PATH + "discotope -f " + dataFile + " -chain " + chain;
        	System.out.println(cmd);
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor(); 
            BufferedReader stdInput = new BufferedReader(new
                    InputStreamReader(p.getInputStream()));
    
           BufferedReader stdError = new BufferedReader(new
                InputStreamReader(p.getErrorStream()));

           // read the output from the command
           //System.out.println("Here is the standard output of the command:\n");
           String line = "";
           while ((line = stdInput.readLine()) != null) {
        	   output = output + line + "\n";
           }
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return output;
	}
	
	public ArrayList<DiscotopePredictionAminoAcid> getAminoAcids() {
		ArrayList<DiscotopePredictionAminoAcid> aaList = new ArrayList<DiscotopePredictionAminoAcid>();
		String predictionString = getOutput();
		String[] lines = predictionString.split("\n");
		for (String line: lines) {
			String [] lineArray = line.split("\t");
			if (lineArray.length > 6) {
				DiscotopePredictionAminoAcid aa = new DiscotopePredictionAminoAcid();
				aa.setPredictedEpitope(lineArray.length == 7d);
				aa.setChain(lineArray[0]);
				aa.setResidueNo(Integer.parseInt(lineArray[1]));
				aa.setResidue(lineArray[2]);
				aa.setContactNo(Integer.parseInt(lineArray[3]));
				aa.setPropensityScore(Double.parseDouble(lineArray[4]));
				aa.setScore(Double.parseDouble(lineArray[5]));
				aaList.add(aa);
			}
		}
		return aaList;
	}
	
	public static void main (String[]args) {
		DiscotopePredictionService dps = new DiscotopePredictionService("4hhb","A");
		dps.run();
		System.out.println(dps.getOutput());
		dps.getAminoAcids();
	}
}
