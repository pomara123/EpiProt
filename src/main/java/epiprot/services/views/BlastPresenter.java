package epiprot.services.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import epiprot.Presenter;
import uk.ac.ebi.kraken.model.blast.parameters.DatabaseOptions;
import uk.ac.ebi.kraken.model.blast.parameters.ExpectedThreshold;
import uk.ac.ebi.kraken.model.blast.parameters.FilterOptions;
import uk.ac.ebi.kraken.model.blast.parameters.FormatOptions;
import uk.ac.ebi.kraken.model.blast.parameters.GapAlign;
import uk.ac.ebi.kraken.model.blast.parameters.MaxNumberResultsOptions;
import uk.ac.ebi.kraken.model.blast.parameters.ScoreOptions;
import uk.ac.ebi.kraken.model.blast.parameters.SensitivityValue;
import uk.ac.ebi.kraken.model.blast.parameters.SimilarityMatrixOptions;
import uk.ac.ebi.kraken.model.blast.parameters.SortOptions;
import uk.ac.ebi.kraken.model.blast.parameters.StatsOptions;
import uk.ac.ebi.kraken.model.blast.parameters.TopcomboN;

public class BlastPresenter {
	
	Presenter presenter;
	
	interface View {
		JComboBox targetDatabaseComboBox();
		JComboBox eThresholdComboBox();
		JComboBox matrixComboBox();
		//JComboBox filteringComboBox();
		JComboBox gappedComboBox();	
		JComboBox hitsComboBox();
		JComboBox gapAlignComboBox();
		JComboBox sensitivityValueComboBox();
		JComboBox scoreOptionscomboBox();
		JComboBox statsOptionsComboBox();
		JComboBox sortOptionsComboBox();
		JComboBox topcomboNComboBox();
		JComboBox formatOptionsComboBox();
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
				DatabaseOptions databaseOptions = (DatabaseOptions) view.targetDatabaseComboBox().getSelectedItem();
				System.out.println(databaseOptions.toString());
				ExpectedThreshold expectedThreshold = (ExpectedThreshold) view.eThresholdComboBox().getSelectedItem(); 
				//FilterOptions filterOptions = (FilterOptions) view.filteringComboBox().getSelectedItem(); 
				MaxNumberResultsOptions maxNumberResultsOptions = (MaxNumberResultsOptions) view.hitsComboBox().getSelectedItem(); 
				ScoreOptions scoreOptions = (ScoreOptions) view.scoreOptionscomboBox().getSelectedItem();
				SensitivityValue sensitivityValue = (SensitivityValue) view.sensitivityValueComboBox().getSelectedItem();
				SortOptions sortOptions = (SortOptions) view.sortOptionsComboBox().getSelectedItem();
				StatsOptions statsOptions = (StatsOptions) view.statsOptionsComboBox().getSelectedItem();
				FormatOptions formatOptions = (FormatOptions) view.formatOptionsComboBox().getSelectedItem();
				TopcomboN topcomboN = (TopcomboN) view.topcomboNComboBox().getSelectedItem();
				GapAlign gapAlign = (GapAlign) view.gapAlignComboBox().getSelectedItem();
				boolean limitToTargetSpecies = view.chckbxLimitToProteins().isSelected();
				boolean limitToSwissProtDB = view.chckbxLimitToSwissProt().isSelected();
				
				Object o = view.matrixComboBox().getSelectedItem();
				if (o instanceof String) {
					presenter.setBlastHits(databaseOptions, expectedThreshold, maxNumberResultsOptions, scoreOptions, sensitivityValue, sortOptions, statsOptions, formatOptions, topcomboN, limitToTargetSpecies, limitToSwissProtDB);
				}
				else {
					SimilarityMatrixOptions similarityMatrixOptions = (SimilarityMatrixOptions) view.matrixComboBox().getSelectedItem(); 
					presenter.setBlastHits(databaseOptions, similarityMatrixOptions, expectedThreshold, maxNumberResultsOptions, scoreOptions, sensitivityValue, sortOptions, statsOptions, formatOptions, topcomboN, limitToTargetSpecies, limitToSwissProtDB);
				}
			}		
		});
	}
	
	private DatabaseOptions getDatabaseOptions(String db) {
		switch(db){
        case "UniProtKB/Swiss-Prot" :
            return DatabaseOptions.SWISSPROT;
        case "UniProtKB" :
            return DatabaseOptions.UNIPROTKB;
        case "Archaea" :
            return DatabaseOptions.UNIPROT_ARCHAEA;
        case "Bacteria" :
            return DatabaseOptions.UNIPROT_BACTERIA;
        case "Eukaryota" :
            return DatabaseOptions.UNIPROT_EUKARYOTA;
        case "Arthropoda" :
            return DatabaseOptions.UNIPROT_ARTHROPODA;
        case "Fungi" :
            return DatabaseOptions.UNIPROT_FUNGI;
        case "Human" :
            return DatabaseOptions.UNIPROT_HUMAN;
        case "Mammals" :
            return DatabaseOptions.UNIPROT_MAMMALS;
        case "Plants" :
            return DatabaseOptions.UNIPROT_VIRIDIPLANTAE;
        case "Rodents" :
            return DatabaseOptions.UNIPROT_RODENTS;
        case "Vertebrates" :
            return DatabaseOptions.UNIPROT_VERTEBRATES;
        case "Viruses" :
            return DatabaseOptions.UNIPROT_VIRUSES;
        case "with 3D structure (PDB)" :
            return DatabaseOptions.UNIPROT_PDB;
        case "Microbial proteomes" :
            return DatabaseOptions.UNIPROT_COMPLETE_MICROBIAL_PROTEOMES;
		case "UniRef100" :
	        return DatabaseOptions.UNIREF_100;
		case "UniRef90" :
		    return DatabaseOptions.UNIREF_90;
		case "UniRef50" :
		    return DatabaseOptions.UNIREF_50;
		case "UniParc" :
		    return DatabaseOptions.UNIPARC;
		case "Trembl" :
		    return DatabaseOptions.TREMBL;
		}
		return null;
	}
	
	public static void main (String[] args) {
		System.out.println(DatabaseOptions.SWISSPROT);
	}

}
