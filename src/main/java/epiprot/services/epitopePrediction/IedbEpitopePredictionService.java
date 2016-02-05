package epiprot.services.epitopePrediction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import epiprot.Protein;
import epiprot.services.Service;

public class IedbEpitopePredictionService extends Service {
	
	private String sequence;
	private String epitopePredictionMethod;
	private int windowSize;
	
	public String chart;
	
	//sets windowSize automatically to 7
	public IedbEpitopePredictionService(String sequence, String epitopePredictionMethod) {
		this.sequence = sequence;
		this.epitopePredictionMethod = epitopePredictionMethod;
		this.windowSize = 7;
	}
	
	public IedbEpitopePredictionService(String sequence, String epitopePredictionMethod, int windowSize) {
		this.sequence = sequence;
		this.epitopePredictionMethod = epitopePredictionMethod;
		this.windowSize = windowSize;
	}
	
	//wrapper for getPredictionChart
	public void run() {
    	String s = null;
    	chart = epitopePredictionMethod + " Prediction Chart\n";
    	 
        try {
            // using the Runtime exec method:
        	String cmd = "curl http://tools-api.iedb.org/tools_api/bcell/ --data method=" + epitopePredictionMethod + "&sequence_text=" + sequence +"&window_size=" + windowSize;
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
             
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
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    //returns an arraylist of IedbEpiprotPredictionAminoAcid
    public ArrayList<IedbEpitopePredictionAminoAcid> getAminoAcids() {
    	ArrayList<IedbEpitopePredictionAminoAcid> aaList = new ArrayList<IedbEpitopePredictionAminoAcid>();
    	String[] chartArray = chart.split("\n");
    	boolean isLine = false;
    	for (String chartLine: chartArray) {
    		if(isLine) {
    			if (chartLine != null && !chartLine.equals("") && chartLine.length() > 1) {
    				String [] chartLineArray = chartLine.split("\\s+");
    				IedbEpitopePredictionAminoAcid aa = new IedbEpitopePredictionAminoAcid();
    	    		if (chartLineArray != null && chartLineArray[1] != null) {
    		    		aa.setPosition(Integer.parseInt(chartLineArray[0]));
    		    		aa.setResidue(chartLineArray[1]);
    		    		if (epitopePredictionMethod.equals("Bepipred")) {
    		    			aa.setScore(Double.parseDouble(chartLineArray[3]));
    		    		}
    		    		else {	    		
    			    		aa.setStart(Integer.parseInt(chartLineArray[2]));
    			    		aa.setEnd(Integer.parseInt(chartLineArray[3]));
    			    		aa.setPeptide(chartLineArray[4]);
    			    		aa.setScore(Double.parseDouble(chartLineArray[5]));
    		    		}
    	    		}
    	    		aaList.add(aa);
    			}
    		}
    		if(chartLine.contains("Position")) {
    			isLine = true;
    		}    		
    	}
    	return aaList;
    }
    
    public static void main (String[]args) {
    	Protein protein = new Protein("Q99523", true);
    	//IedbEpitopePredictionService ieps = new IedbEpitopePredictionService(protein.getSequence(),)
    }
}
