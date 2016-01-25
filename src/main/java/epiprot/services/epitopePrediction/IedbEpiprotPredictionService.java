package epiprot.services.epitopePrediction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import epiprot.services.Service;

public class IedbEpiprotPredictionService extends Service {
	
	private String sequence;
	private String epitopePredictionMethod;
	
	public IedbEpiprotPredictionService(String sequence, String epitopePredictionMethod) {
		this.sequence = sequence;
		this.epitopePredictionMethod = epitopePredictionMethod;
	}
	
	//wrapper for getPredictionChart
	public String getPredictionChart() {
		return getPredictionChart(8);
	}
		
	//returns data chart using Emini Surface Accessibility Prediction found on IEDB website
    public String getPredictionChart(int windowSize) {
    	String s = null;
    	String chart = "Emini Prediction Chart\n";
    	 
        try {
            // using the Runtime exec method:
        	String cmd = "curl http://tools-api.iedb.org/tools_api/bcell/ --data method=" + epitopePredictionMethod + "&sequence_text=" + sequence +"&window_size=" + windowSize;
            Process p = Runtime.getRuntime().exec(cmd);
            //p.waitFor();
             
            BufferedReader stdInput = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));
 
            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));
            
            while ((s = stdInput.readLine()) != null) {
                chart = chart + s + "\n";
            }
             
            /* read any errors from the attempted command
            System.out.println("Here is the standard error of the command (if any):\n");
            while ((s = stdError.readLine()) != null) {
                System.out.println(s);
            }*/
        }
        catch (IOException e) {
            System.out.println("exception happened - here's what I know: ");
            e.printStackTrace();
        }
        return chart;
    }
    
    //gets an array representation of the Emini values
    public String[][] getEminiValuesAsArray(String chart) {
    	String[] tempLineArray = chart.split("\n");
    	int size = tempLineArray.length;
    	String[][] lineArray = new String [size][];
    	for (int i = 0; i < size; i++) {
    		String[] line = tempLineArray[i].split("	");
    		lineArray[i] = line;	
    	}
    	return lineArray;
    }
    
    //returns an arraylist of IedbEpiprotPredictionAminoAcid
    public ArrayList<IedbEpiprotPredictionAminoAcid> getAminoAcids() {
    	ArrayList<IedbEpiprotPredictionAminoAcid> aaList = new ArrayList<IedbEpiprotPredictionAminoAcid>();
    	String[][] eminiValuesAsArray = getEminiValuesAsArray(getPredictionChart());
    	return aaList;
    }

}
