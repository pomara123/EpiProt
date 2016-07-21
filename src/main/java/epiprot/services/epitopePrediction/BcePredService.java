package epiprot.services.epitopePrediction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;

import epiprot.Protein;
import epiprot.services.Service;

public class BcePredService extends Service {
	
	private String sequence;
	private String hydrophilicity;
	private String accessibility;
	private String exposedSurface;
	private String antegenicPropensity;
	private String flexibility;
	private String turns;
	private String polarity;
	private String combined;
	
	private boolean selectHydro;
	private boolean selectFlexi;
	private boolean selectAccess;
	private boolean selectTurns;
	private boolean selectSurface;
	private boolean selectPolar;
	private boolean selectAntipro;
	private boolean selectAll;
	
	private ArrayList<BcePredAminoAcid> aminoAcids = new ArrayList<BcePredAminoAcid>();

	public BcePredService(String input, String combined, String polarity, String turns, String flexibility, String antegenicPropensity, String exposedSurface, String hydrophilicity, String accessibility) {
		// TODO Auto-generated constructor stub
		this.sequence = getSequence(input);
		this.hydrophilicity = hydrophilicity;
		this.accessibility = accessibility;
		this.exposedSurface = exposedSurface;
		this.antegenicPropensity = antegenicPropensity;
		this.flexibility = flexibility;
		this.turns = turns;
		this.polarity = polarity;
		this.combined = combined;
	}
	public BcePredService(String input) {
		// TODO Auto-generated constructor stub
		this.sequence = getSequence(input);
	} 

	@Override
	public void run() {
		// TODO Auto-generated method stub
		final WebClient webClient = new WebClient();

	    // Get the first page
	    HtmlPage page1;
		try {
			page1 = webClient.getPage("http://www.imtech.res.in/raghava/bcepred/bcepred_submission.html");
			// Get the form that we are dealim ng with and within that form, 
		    // find the submit button and the field that we want to change.
		    HtmlForm form = page1.getFormByName("form");

		    HtmlSubmitInput button = form.getInputByValue("Submit sequence");
		    HtmlTextArea seqTextField = form.getTextAreaByName("SEQ");
		    seqTextField.setText(sequence);
		    
		    List<HtmlInput> thresholds = form.getInputsByName("Threshold");

		    if (hydrophilicity != null) {
		    	thresholds.get(0).setValueAttribute(hydrophilicity);
		    }
		    if (flexibility != null) {
		    	thresholds.get(1).setValueAttribute(flexibility);
		    }
		    if (accessibility != null) {
		    	thresholds.get(2).setValueAttribute(accessibility);
		    }
		    if (turns != null) {
		    	thresholds.get(3).setValueAttribute(turns);
		    }
		    if (exposedSurface != null) {
		    	thresholds.get(4).setValueAttribute(exposedSurface);
	    	}
		    if (polarity != null) {
		    	thresholds.get(5).setValueAttribute(polarity);
		    }
		    if (antegenicPropensity != null) {
		    	thresholds.get(6).setValueAttribute(antegenicPropensity);
		    }
		    if (combined != null) {
		    	thresholds.get(7).setValueAttribute(combined);
		    }
		    
		    HtmlSelect physioChemicalSelect = form.getSelectByName("propno");
		    List<HtmlOption> optionList = physioChemicalSelect.getOptions();
		    //if (selectAll) {
			    for(HtmlOption option: optionList) {
			    	option.setSelected(true);
			    }
		   /* }
		    else {
			    optionList.get(0).setSelected(selectHydro);
			    optionList.get(1).setSelected(selectFlexi);
			    optionList.get(2).setSelected(selectAccess);
			    optionList.get(3).setSelected(selectTurns);
			    optionList.get(4).setSelected(selectSurface);
			    optionList.get(5).setSelected(selectPolar);
			    optionList.get(6).setSelected(selectAntipro);
		    }*/
		    // Now submit the form by clicking the button and get back the second page.
		    HtmlPage page2 = button.click();
		    
		    String[] page2Array = page2.asText().split("\\r?\\n");
		    printArray(page2Array);
		    for(int i = 0; i < page2Array.length; i++) {
		    	String line = page2Array[i]; 
		    	if (line.contains("   Flexi")) {
		    		line = page2Array[i+2];
		    		String[] lineArray = line.split("(?=[A-Z])");
		    		for(int j = 1; j < lineArray.length; j++) {
		    			String[]lineAttrs = lineArray[j].split("\\s+");
	    				BcePredAminoAcid aa = new BcePredAminoAcid();
		    			for (int k = 0; k < lineAttrs.length; k++) {
			    			String attr = lineAttrs[k];
			    			aa.setPosition(j);
			    			if(k == 0) {aa.setResidue(attr);}
			    			else {
			    				double d = Double.parseDouble(attr);
			    				if(k == 1){aa.setHydro(d);}
			    				else if(k == 2){aa.setFlexi(d);}
			    				else if(k == 3){aa.setAccess(d);}
			    				else if(k == 4){aa.setTurns(d);}
			    				else if(k == 5){aa.setSurface(d);}
			    				else if(k == 6){aa.setPolar(d);}
			    				else if(k == 7){aa.setAntiPro(d);}
			    				else if(k == 8){aa.setMax(d);}
			    				else if(k == 9){aa.setMin(d);}
			    				else if(k == 10){aa.setAverage(d);}
			    			}
		    			}
		    			aminoAcids.add(aa);
		    		}
		    		break;
		    	}		    	
		    }
		    calcDisplayedScores();
		    DomElement element = page2.getElementByName("opt2");
		    //System.out.println(element.asXml());
		    webClient.close();
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    		
	}
	
	private void calcDisplayedScores() {
		int tenth = aminoAcids.size()/10;
    	int remainder = aminoAcids.size()%10;
    	
		if(selectHydro) {
			Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
	    	     public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
	    	         if(o1.getHydro() == o2.getHydro())
	    	             return 0;
	    	         return o1.getHydro() < o2.getHydro() ? -1 : 1;
	    	     }
	    	});
	    	
	    	for(int i = 0; i < 10; i++) {
	    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
	    			aminoAcids.get(j).setHydroRelative(i);
	    		}
	    	}
	    	
	    	for (int i = aminoAcids.size()-1; i >= aminoAcids.size()-remainder; i--) {
	    		aminoAcids.get(i).setHydroRelative(9);
	    	}
		}
		
		if(selectFlexi) {
			Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
	    	     public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
	    	         if(o1.getFlexi() == o2.getFlexi())
	    	             return 0;
	    	         return o1.getFlexi() < o2.getFlexi() ? -1 : 1;
	    	     }
	    	});
	    	
	    	for(int i = 0; i < 10; i++) {
	    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
	    			aminoAcids.get(j).setFlexiRelative(i);
	    		}
	    	}
	    	
	    	for (int i = aminoAcids.size()-1; i >= aminoAcids.size()-remainder; i--) {
	    		aminoAcids.get(i).setFlexiRelative(9);
	    	}
		}
		
		if(selectAccess) {
			Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
	    	     public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
	    	         if(o1.getAccess() == o2.getAccess())
	    	             return 0;
	    	         return o1.getAccess() < o2.getAccess() ? -1 : 1;
	    	     }
	    	});
	    	
	    	for(int i = 0; i < 10; i++) {
	    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
	    			aminoAcids.get(j).setAccessRelative(i);
	    		}
	    	}
	    	
	    	for (int i = aminoAcids.size()-1; i >= aminoAcids.size()-remainder; i--) {
	    		aminoAcids.get(i).setAccessRelative(9);
	    	}
		}
		
		if(selectTurns) {
			Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
	    	     public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
	    	         if(o1.getTurns() == o2.getTurns())
	    	             return 0;
	    	         return o1.getTurns() < o2.getTurns() ? -1 : 1;
	    	     }
	    	});
	    	
	    	for(int i = 0; i < 10; i++) {
	    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
	    			aminoAcids.get(j).setTurnsRelative(i);
	    		}
	    	}
	    	
	    	for (int i = aminoAcids.size()-1; i >= aminoAcids.size()-remainder; i--) {
	    		aminoAcids.get(i).setTurnsRelative(9);
	    	}
		}
    	
		if(selectSurface) {
			Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
	    	     public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
	    	         if(o1.getSurface() == o2.getSurface())
	    	             return 0;
	    	         return o1.getSurface() < o2.getSurface() ? -1 : 1;
	    	     }
	    	});
			
	    	for(int i = 0; i < 10; i++) {
	    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
	    			aminoAcids.get(j).setSurfaceRelative(i);
	    		}
	    	}
	    	
	    	for (int i = aminoAcids.size()-1; i >= aminoAcids.size()-remainder; i--) {
	    		aminoAcids.get(i).setSurfaceRelative(9);
	    	}
		}
		
		if(selectPolar) {
			Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
	    	     public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
	    	         if(o1.getPolar() == o2.getPolar())
	    	             return 0;
	    	         return o1.getPolar() < o2.getPolar() ? -1 : 1;
	    	     }
	    	});
	    	
	    	for(int i = 0; i < 10; i++) {
	    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
	    			aminoAcids.get(j).setPolarRelative(i);
	    		}
	    	}
	    	
	    	for (int i = aminoAcids.size()-1; i >= aminoAcids.size()-remainder; i--) {
	    		aminoAcids.get(i).setPolarRelative(9);
	    	}
		}
		
		if(selectAntipro) {
			Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
	    	     public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
	    	         if(o1.getAntiPro() == o2.getAntiPro())
	    	             return 0;
	    	         return o1.getAntiPro() < o2.getAntiPro() ? -1 : 1;
	    	     }
	    	});
	    	
	    	for(int i = 0; i < 10; i++) {
	    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
	    			aminoAcids.get(j).setAntiProRelative(i);
	    		}
	    	}
	    	
	    	for (int i = aminoAcids.size()-1; i >= aminoAcids.size()-remainder; i--) {
	    		aminoAcids.get(i).setAntiProRelative(9);
	    	}
		}
		
		if(selectAntipro) {
			Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
	    	     public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
	    	         if(o1.getAntiPro() == o2.getAntiPro())
	    	             return 0;
	    	         return o1.getAntiPro() < o2.getAntiPro() ? -1 : 1;
	    	     }
	    	});
			
	    	for(int i = 0; i < 10; i++) {
	    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
	    			aminoAcids.get(j).setAntiProRelative(i);
	    		}
	    	}
	    	
	    	for (int i = aminoAcids.size()-1; i >= aminoAcids.size()-remainder; i--) {
	    		aminoAcids.get(i).setAntiProRelative(9);
	    	}
		}
		
		if(toInt(selectHydro)+toInt(selectFlexi)+toInt(selectAccess)+toInt(selectTurns)+toInt(selectSurface)+toInt(selectPolar)+toInt(selectAntipro) >= 2) {
			Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
	    	     public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
	    	         if(o1.getMax() == o2.getMax())
	    	             return 0;
	    	         return o1.getMax() < o2.getMax() ? -1 : 1;
	    	     }
	    	});
	    	
	    	for(int i = 0; i < 10; i++) {
	    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
	    			aminoAcids.get(j).setMaxRelative(i);
	    		}
	    	}
	    	
	    	for (int i = aminoAcids.size()-1; i >= aminoAcids.size()-remainder; i--) {
	    		aminoAcids.get(i).setMaxRelative(9);
	    	}
	    	
	    	Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
	    	     public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
	    	         if(o1.getMin() == o2.getMin())
	    	             return 0;
	    	         return o1.getMin() < o2.getMin() ? -1 : 1;
	    	     }
	    	});
	    	
	    	for(int i = 0; i < 10; i++) {
	    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
	    			aminoAcids.get(j).setMinRelative(i);
	    		}
	    	}
	    	
	    	for (int i = aminoAcids.size()-1; i >= aminoAcids.size()-remainder; i--) {
	    		aminoAcids.get(i).setMinRelative(9);
	    	}
	    	
	    	Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
	    	     public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
	    	         if(o1.getAverage() == o2.getAverage())
	    	             return 0;
	    	         return o1.getAverage() < o2.getAverage() ? -1 : 1;
	    	     }
	    	});
	    	
	    	for(int i = 0; i < 10; i++) {
	    		for (int j = i*tenth; j < (i*tenth)+tenth; j++) {
	    			aminoAcids.get(j).setAverageRelative(i);
	    		}
	    	}
	    	
	    	for (int i = aminoAcids.size()-1; i >= aminoAcids.size()-remainder; i--) {
	    		aminoAcids.get(i).setAverageRelative(9);
	    	}
		}
    	
    	Collections.sort(aminoAcids, new Comparator<BcePredAminoAcid>(){
			public int compare(BcePredAminoAcid o1, BcePredAminoAcid o2){
			    if(o1.getPosition() == o2.getPosition())
			        return 0;
			    return o1.getPosition() < o2.getPosition() ? -1 : 1;
			}
    	});
    }
	
	private int toInt(boolean bool) {
		return bool ? 1 : 0;
	}
		
	private String getSequence(String input) {
		if (input.length() == 6) {
			Protein protein = new Protein(input,true);
			return protein.getSequence();
		}
		return input;
	}
	
	public ArrayList<BcePredAminoAcid> getAminoAcids() {
		return aminoAcids;
	}
	
	public boolean isSelectHydro() {
		return selectHydro;
	}
	public void setSelectHydro(boolean selectHydro) {
		this.selectHydro = selectHydro;
	}
	public boolean isSelectFlexi() {
		return selectFlexi;
	}
	public void setSelectFlexi(boolean selectFlexi) {
		this.selectFlexi = selectFlexi;
	}
	public boolean isSelectAccess() {
		return selectAccess;
	}
	public void setSelectAccess(boolean selectAccess) {
		this.selectAccess = selectAccess;
	}
	public boolean isSelectTurns() {
		return selectTurns;
	}
	public void setSelectTurns(boolean selectTurns) {
		this.selectTurns = selectTurns;
	}
	public boolean isSelectSurface() {
		return selectSurface;
	}
	public void setSelectSurface(boolean selectSurface) {
		this.selectSurface = selectSurface;
	}
	public boolean isSelectPolar() {
		return selectPolar;
	}
	public void setSelectPolar(boolean selectPolar) {
		this.selectPolar = selectPolar;
	}
	public boolean isSelectAntipro() {
		return selectAntipro;
	}
	public void setSelectAntipro(boolean selectAntipro) {
		this.selectAntipro = selectAntipro;
	}
	public boolean isSelectAll() {
		return selectAll;
	}
	public void setSelectAll() {
		this.selectAll = true;
	}
	
	public static void printArray(String[]array) {
		for(String s: array) {
			System.out.println(s);
		}
	}
	
	public static void main (String[]args) {
		BcePredService bps = new BcePredService("Q99523","1.9","2.3","1.9","1.9","1.8","2.4","2","2");
				
		//bps.setSelectAccess(true);
		bps.setSelectAll();
		bps.run();
		ArrayList<BcePredAminoAcid> aaList = bps.getAminoAcids();
		for(BcePredAminoAcid aa : aaList) {
			System.out.println(aa.toString());
		}
	}
	
}
