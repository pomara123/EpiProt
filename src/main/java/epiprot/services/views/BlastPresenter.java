package epiprot.services.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import epiprot.Presenter;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.AlignmentCutoffOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.DatabaseOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.DropOffOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.ExpectationOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.FilterOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.MatrixOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.ScoreCutoffOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.SequenceTypeOption;

public class BlastPresenter {
	
	Presenter presenter;
	
	interface View {
		JComboBox databaseOptionComboBox();
		JComboBox expectationOptionComboBox();
		JComboBox filterOptionComboBox();
		JComboBox dropOffOptionComboBox();
		JComboBox isGapAlignComboBox();
		JTextField gapExtTextField();
		JTextField gapOpenTextField();
		JComboBox matrixOptionComboBox();
		//JComboBox alignmentCutoffOption = new JComboBox();
		JComboBox scoreCutoffOptionComboBox();
		JCheckBox chckbxLimitToProteins();
		JCheckBox chckbxLimitToSwissProt();

		JButton submitButton();
		
	}
	
	View view = new BlastView();

	public BlastPresenter(Presenter presenter) {
		// TODO Auto-generated constructor stub
		this.presenter= presenter;
		bindHandlers();
	}
	
	void bindHandlers() {
		view.submitButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub	
				//System.out.println("BlastPresenter bindHandlers");
				DatabaseOption databaseOption = (DatabaseOption) view.databaseOptionComboBox().getSelectedItem();
				System.out.println(databaseOption.toString());
				
				ExpectationOption expectationOption = (ExpectationOption) view.expectationOptionComboBox().getSelectedItem(); 
				FilterOption filterOption = (FilterOption) view.filterOptionComboBox().getSelectedItem();
				DropOffOption dropOffOption = (DropOffOption) view.dropOffOptionComboBox().getSelectedItem();
			    MatrixOption matrixOption = (MatrixOption) view.matrixOptionComboBox().getSelectedItem(); 
				ScoreCutoffOption scoreCutoffOption = (ScoreCutoffOption) view.scoreCutoffOptionComboBox().getSelectedItem();
				
				boolean isGapAlign = (boolean) view.isGapAlignComboBox().getSelectedItem();
			    int gapExt = Integer.parseInt(view.gapExtTextField().getText());
			    int gapOpen = Integer.parseInt(view.gapOpenTextField().getText());
				
				boolean limitToTargetSpecies = view.chckbxLimitToProteins().isSelected();
				boolean limitToSwissProtDB = view.chckbxLimitToSwissProt().isSelected();
				
								
				presenter.setBlastHits(databaseOption, expectationOption, filterOption, dropOffOption, matrixOption, scoreCutoffOption, isGapAlign, gapExt, gapOpen, limitToTargetSpecies, limitToSwissProtDB);
	
			}		
		});
		
		view.gapExtTextField().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				checkTextField();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				checkTextField();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				checkTextField();
			}
			public void checkTextField() {
				String text = view.gapExtTextField().getText();
				if(isInteger(text) && (Integer.parseInt(text) == -1 || (Integer.parseInt(text) > 0 && Integer.parseInt(text) < 5))) {
					view.submitButton().setEnabled(true);
				}
				else {
					view.submitButton().setEnabled(false);
				}
			}
		});
		
		view.gapOpenTextField().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				checkTextField();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				checkTextField();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				checkTextField();
			}
			public void checkTextField() {
				String text = view.gapOpenTextField().getText();
				if(isInteger(text) && (Integer.parseInt(text) == -1 || (Integer.parseInt(text) > 0 && Integer.parseInt(text) < 5))) {
					view.submitButton().setEnabled(true);
				}
				else {
					view.submitButton().setEnabled(false);
				}
			}
		});
	}
	
	private DatabaseOption getDatabaseOption(String db) {
		switch(db){
        case "UniProtKB/Swiss-Prot" :
            return DatabaseOption.SWISSPROT;
        case "UniProtKB" :
            return DatabaseOption.UNIPROTKB;
        case "Archaea" :
            return DatabaseOption.UNIPROT_ARCHAEA;
        case "Bacteria" :
            return DatabaseOption.UNIPROT_BACTERIA;
        case "Eukaryota" :
            return DatabaseOption.UNIPROT_EUKARYOTA;
        case "Arthropoda" :
            return DatabaseOption.UNIPROT_ARTHROPODA;
        case "Fungi" :
            return DatabaseOption.UNIPROT_FUNGI;
        case "Human" :
            return DatabaseOption.UNIPROT_HUMAN;
        case "Mammals" :
            return DatabaseOption.UNIPROT_MAMMALS;
        case "Plants" :
            return DatabaseOption.UNIPROT_VIRIDIPLANTAE;
        case "Rodents" :
            return DatabaseOption.UNIPROT_RODENTS;
        case "Vertebrates" :
            return DatabaseOption.UNIPROT_VERTEBRATES;
        case "Viruses" :
            return DatabaseOption.UNIPROT_VIRUSES;
        case "with 3D structure (PDB)" :
            return DatabaseOption.UNIPROT_PDB;
        case "Microbial proteomes" :
            return DatabaseOption.UNIPROT_COMPLETE_MICROBIAL_PROTEOMES;
		case "UniRef100" :
	        return DatabaseOption.UNIREF_100;
		case "UniRef90" :
		    return DatabaseOption.UNIREF_90;
		case "UniRef50" :
		    return DatabaseOption.UNIREF_50;
		case "UniParc" :
		    return DatabaseOption.UNIPARC;
		case "Trembl" :
		    return DatabaseOption.TREMBL;
		}
		return null;
	}
	
	private boolean isInteger(String s) {
		try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
	
	public static void main (String[] args) {
		System.out.println(DatabaseOption.SWISSPROT);
	}

}
