package epiprot.services.epitopePrediction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlRadioButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextArea;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import epiprot.Protein;
import epiprot.services.Service;

public class ABCPredService extends Service {
	
	private String sequence;
	private String windowSize;
	private String threshold;
	private boolean isOverLapping;
	
	private ArrayList<ABCPredPeptide> peptideList = new ArrayList<ABCPredPeptide>();

	public ABCPredService(String input, String windowSize, String threshold, boolean isOverLapping) {
		// TODO Auto-generated constructor stub
		this.sequence = getSequence(input);
		this.windowSize = windowSize;
		this.threshold = threshold;
		this.isOverLapping = isOverLapping;		
	}
	public ABCPredService(String input) {
		// TODO Auto-generated constructor stub
		this.sequence = getSequence(input);
		this.isOverLapping = true;
	} 

	@Override
	public void run() {
		// TODO Auto-generated method stub
		final WebClient webClient = new WebClient();

	    // Get the first page
	    HtmlPage page1;
		try {
			page1 = webClient.getPage("http://www.imtech.res.in/raghava/abcpred/ABC_submission.html");
			// Get the form that we are dealim ng with and within that form, 
		    // find the submit button and the field that we want to change.
		    HtmlForm form = page1.getFormByName("form");

		    HtmlSubmitInput button = form.getInputByValue("Submit sequence");
		    HtmlTextArea seqTextField = form.getTextAreaByName("SEQ");
		    seqTextField.setText(sequence);

		    if (windowSize != null) {
			    HtmlSelect windowSelect = form.getSelectByName("window");
			    HtmlOption option = windowSelect.getOptionByValue(windowSize);
			    windowSelect.setSelectedAttribute(option, true);
		    }
		    if (threshold != null) {
		    	HtmlTextInput thresholdTextField = form.getInputByName("Threshold");
		    	thresholdTextField.setValueAttribute(threshold);
		    }
		    
		    if (!isOverLapping) {
		    	HtmlRadioButtonInput overLappingSelect = form.getCheckedRadioButton("filter");
		    	overLappingSelect.setValueAttribute("off");
		    	overLappingSelect.click();
	    	}

		    // Now submit the form by clicking the button and get back the second page.
		    HtmlPage page2 = button.click();
		    List<DomElement> tables = page2.getElementsByTagName("table");
	    	Iterable<DomElement> tableElement = tables.get(1).getChildElements();
	    	java.util.Iterator<DomElement> it = tableElement.iterator();
	    	while(it.hasNext()) {
	    		DomElement peptideElement = it.next();
	    		java.util.Iterator<DomElement> it2 = peptideElement.getChildElements().iterator();
	    		while(it2.hasNext()) {
	    			DomElement peptideAttrsElement = it2.next();
	    			Iterable<DomElement> peptides = peptideAttrsElement.getChildElements();
	    			java.util.Iterator<DomElement> it3 = peptides.iterator();
	    			ABCPredPeptide peptide = new ABCPredPeptide();
	    			int count = 0;
	    			while(it3.hasNext()) {
	    				String attr = it3.next().asText();
	    				if(!attr.equals("Rank") && count == 0) {
	    					peptide.setRank(Integer.parseInt(attr));
	    				} 
	    				else if(!attr.equals("Sequence") && count == 1) {
	    					peptide.setSequence(attr);
	    				}
	    				else if(!attr.equals("Start position") && count == 2) {
	    					peptide.setStart(Integer.parseInt(attr));
	    				}
	    				else if(!attr.equals("Score") && count == 3) {
	    					peptide.setScore(Double.parseDouble(attr));
	    				}
	    				count++;
	    			}
	    			if (peptide.getSequence() != null) {
	    				peptideList.add(peptide);
	    			}
	    		}
	    		
	    	}
		    webClient.close();
		} catch (FailingHttpStatusCodeException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    		
	}
	
	private String getSequence(String input) {
		if (input.length() == 6) {
			Protein protein = new Protein(input,true);
			return protein.getSequence();
		}
		return input;
	}
	
	private ArrayList<ABCPredPeptide> getPeptides() {
		return peptideList;
	}
	
	public static void main (String[]args) {
		ABCPredService abcPredService = new ABCPredService("P31749","16","0.7",true);
		abcPredService.run();
		ArrayList<ABCPredPeptide> peptideList = abcPredService.getPeptides();
		for(ABCPredPeptide p: peptideList) {
			System.out.println(p.toString());
		}
	}
}
