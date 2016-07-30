package epiprot.services.epitopePrediction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.stream.Stream;

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
		System.out.println(sequence);
		this.windowSize = windowSize;
	}
	
	//wrapper for getPredictionChart
	@Override
	public void run() {
    	String s = null;
    	chart = epitopePredictionMethod + " Prediction Chart\n";
    	 
        try {
            // using the Runtime exec method:
        	String cmd = "";
        	
        	cmd = "curl http://tools-api.iedb.org/tools_api/bcell/ --data method=" + epitopePredictionMethod + "&sequence_text=" + sequence +"&window_size=" + windowSize;
        	
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
             
            BufferedReader stdInput = new BufferedReader(new
                 InputStreamReader(p.getInputStream()));
 
            BufferedReader stdError = new BufferedReader(new
                 InputStreamReader(p.getErrorStream()));
            
            
            Stream<String> lines = stdInput.lines();
            Iterator it = lines.iterator();
            while(it.hasNext()) {
            	//System.out.println(it.next());
            	chart = chart + it.next() + "\n";
            }
            System.out.println(chart);
             
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
    	return calcDisplayedScores(aaList);
    }
    
    private ArrayList<IedbEpitopePredictionAminoAcid> calcDisplayedScores(ArrayList<IedbEpitopePredictionAminoAcid> aaList) {
    	ArrayList<IedbEpitopePredictionAminoAcid> sortedAAList = new ArrayList<IedbEpitopePredictionAminoAcid>(aaList);
    	Collections.sort(sortedAAList, new Comparator<IedbEpitopePredictionAminoAcid>(){
    	     @Override
			public int compare(IedbEpitopePredictionAminoAcid o1, IedbEpitopePredictionAminoAcid o2){
    	         if(o1.getScore() == o2.getScore())
    	             return 0;
    	         return o1.getScore() < o2.getScore() ? -1 : 1;
    	     }
    	});
    	
    	int tenth = aaList.size()/10;
    	int remainder = aaList.size()%10;
    	
    	for(int i = 0; i < 10; i++) {
    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
    			sortedAAList.get(j).setRelativeScore(i);
    		}
    	}
    	
    	for (int i = aaList.size()-1; i >= aaList.size()-remainder; i--) {
    		sortedAAList.get(i).setRelativeScore(9);
    	}
    	
    	Collections.sort(sortedAAList, new Comparator<IedbEpitopePredictionAminoAcid>(){
   	     @Override
		public int compare(IedbEpitopePredictionAminoAcid o1, IedbEpitopePredictionAminoAcid o2){
   	         if(o1.getPosition() == o2.getPosition())
   	             return 0;
   	         return o1.getPosition() < o2.getPosition() ? -1 : 1;
   	     }
    	});
    	
    	return sortedAAList; 
    }
    
    public static void main (String[]args) {
    	Protein protein = new Protein("Q99523", true);
    	IedbEpitopePredictionService ieps = new IedbEpitopePredictionService(protein.getSequence(),"Emini");
    	ieps.run();
    	ArrayList<IedbEpitopePredictionAminoAcid> aminoAcids = ieps.getAminoAcids();
    	for (IedbEpitopePredictionAminoAcid aa: aminoAcids) {
    		System.out.println(aa.toString()+"|"+aa.getRelativeScore()+"|"+aa.getScore());
    	}
    }
}
