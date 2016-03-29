package epiprot.services.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;

import epiprot.Presenter;
import epiprot.services.jabaws.JabawsConstants;

public class MSAPresenter {
	
	Presenter presenter;
	
	interface View {
		JRadioButton muscleWSRadioButton();
		JRadioButton clustalOWSRadioButton();
		JRadioButton clustalWSRadioButton();
		JRadioButton tCoffeeWSRadioButton();
		JRadioButton mafftWSRadioButton();
		JRadioButton probconsWSRadioButton();
		JRadioButton msaProbsWSRadioButton();
		JRadioButton glProbsWSRadioButton();
		
		JButton btnSubmit();
		
		ButtonGroup buttonGroup();
	}
	
	View view = new MSAView();
	
	public MSAPresenter(Presenter presenter) {
		// TODO Auto-generated constructor stub
		this.presenter = presenter;
		bindHandlers();
	}
	
	void bindHandlers() {
		view.btnSubmit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Enumeration<AbstractButton> allRadioButton= view.buttonGroup().getElements();  
		        while(allRadioButton.hasMoreElements()) {
		        	JRadioButton temp = (JRadioButton) allRadioButton.nextElement();  
		            if(temp.isSelected()) {  
		            	String msaProgram = JabawsConstants.MUSCLE;
		            	
		            	if(temp.getText().equals("Clustal Omega")) {msaProgram = JabawsConstants.CLUSTALOMEGA;}
		            	else if(temp.getText().equals("Clustal W")) {msaProgram = JabawsConstants.CLUSTAL;}
		            	else if(temp.getText().equals("T-Coffee")) {msaProgram = JabawsConstants.TCOFFEE;}
		            	else if(temp.getText().equals("Mafft")) {msaProgram = JabawsConstants.MAFFT;}
		            	else if(temp.getText().equals("ProbCons")) {msaProgram = JabawsConstants.PROBCONS;}
		            	else if(temp.getText().equals("MSAProbs")) {msaProgram = JabawsConstants.MSAPROBS;}
		            	else if(temp.getText().equals("GLProbs")) {msaProgram = JabawsConstants.GLPROBS;}
		            	
		            	presenter.setAlignment(msaProgram); 
		            	break;
		            }  
		        } 
			}		
		});
	}

}
