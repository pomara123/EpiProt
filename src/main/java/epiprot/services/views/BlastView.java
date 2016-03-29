package epiprot.services.views;

import javax.swing.JFrame;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

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

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class BlastView extends JFrame implements BlastPresenter.View{
	
	JComboBox targetDatabaseComboBox = new JComboBox();
	JComboBox eThresholdComboBox = new JComboBox();
	JComboBox matrixComboBox = new JComboBox();
	JComboBox gappedComboBox = new JComboBox();	
	JComboBox hitsComboBox = new JComboBox();
	JComboBox gapAlignComboBox = new JComboBox();
	JComboBox sensitivityValueComboBox = new JComboBox();
	JComboBox scoreOptionscomboBox = new JComboBox();
	JComboBox statsOptionsComboBox = new JComboBox();
	JComboBox sortOptionsComboBox = new JComboBox();
	JComboBox topcomboNComboBox = new JComboBox();
	JComboBox formatOptionsComboBox = new JComboBox();
	JCheckBox chckbxLimitToProteins = new JCheckBox("Limit to proteins of the target protein species");
	JCheckBox chckbxLimitToSwissProt = new JCheckBox("Limit to proteins from Swiss-Prot Database");
	JButton submitButton = new JButton("Submit");

	public BlastView() {
		getContentPane().setPreferredSize(new Dimension(400, 500));
		setTitle("BLAST");
		setPreferredSize(new Dimension(400, 500));
		pack();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setPreferredSize(new Dimension(400, 500));
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(105dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(105dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel targetDatabaseLabel = new JLabel("Target Database");
		panel.add(targetDatabaseLabel, "2, 2");
		
		JLabel eThresholdLabel = new JLabel("E-Threshold");
		panel.add(eThresholdLabel, "4, 2");		
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROTKB);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_ARCHAEA);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_BACTERIA);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_EUKARYOTA);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_ARTHROPODA);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_FUNGI);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_HUMAN);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_MAMMALS);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_VIRIDIPLANTAE);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_RODENTS);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_VERTEBRATES);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_VIRUSES);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_PDB);
		//targetDatabaseComboBox.addItem("Reference proteomes");
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPROT_COMPLETE_MICROBIAL_PROTEOMES);
		targetDatabaseComboBox.addItem(DatabaseOptions.SWISSPROT);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIREF_100);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIREF_90);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIREF_50);
		targetDatabaseComboBox.addItem(DatabaseOptions.UNIPARC);
		targetDatabaseComboBox.addItem(DatabaseOptions.TREMBL);
		panel.add(targetDatabaseComboBox, "2, 4, fill, default");
		
		eThresholdComboBox.addItem(ExpectedThreshold.ONE);
		eThresholdComboBox.addItem(ExpectedThreshold.TEN);
		eThresholdComboBox.addItem(ExpectedThreshold.ONEHUNDRED);
		eThresholdComboBox.addItem(ExpectedThreshold.ONETHOUSAND);
		eThresholdComboBox.setSelectedItem(ExpectedThreshold.TEN);
		panel.add(eThresholdComboBox, "4, 4, fill, default");
		
		JLabel matrixLabel = new JLabel("Matrix");
		panel.add(matrixLabel, "2, 6");
		matrixComboBox.addItem("Auto");
		matrixComboBox.addItem(SimilarityMatrixOptions.BLOSUM_45);
		matrixComboBox.addItem(SimilarityMatrixOptions.BLOSUM_62);
		matrixComboBox.addItem(SimilarityMatrixOptions.BLOSUM_80);
		matrixComboBox.addItem(SimilarityMatrixOptions.PAM_70);
		matrixComboBox.addItem(SimilarityMatrixOptions.PAM_30);
		matrixComboBox.setSelectedItem("Auto");
		panel.add(matrixComboBox, "2, 8, fill, default");
		
		JLabel gapAlign = new JLabel("Gapped");
		panel.add(gapAlign, "4, 6");
		gapAlignComboBox.addItem(GapAlign.FALSE);
		gapAlignComboBox.addItem(GapAlign.TRUE);
		gapAlignComboBox.setSelectedItem(GapAlign.TRUE);
		panel.add(gapAlignComboBox, "4, 8, fill, default");

		JLabel hitsLabel = new JLabel("Hits");
		panel.add(hitsLabel, "2, 10");
		
		JLabel scoreOptions = new JLabel("Score Options");
		panel.add(scoreOptions, "4, 10");
			
		hitsComboBox.addItem(MaxNumberResultsOptions.ONE);
		hitsComboBox.addItem(MaxNumberResultsOptions.FIVE);
		hitsComboBox.addItem(MaxNumberResultsOptions.TEN);
		hitsComboBox.addItem(MaxNumberResultsOptions.TWENTY);
		hitsComboBox.addItem(MaxNumberResultsOptions.FIFTY);
		hitsComboBox.addItem(MaxNumberResultsOptions.ONE_HUNDRED_FIFTY);
		hitsComboBox.addItem(MaxNumberResultsOptions.TWO_HUNDRED);
		hitsComboBox.addItem(MaxNumberResultsOptions.TWO_HUNDRED_FIFTY);
		hitsComboBox.addItem(MaxNumberResultsOptions.THREE_HUNDRED);
		hitsComboBox.addItem(MaxNumberResultsOptions.THREE_HUNDRED_FIFTY);
		hitsComboBox.addItem(MaxNumberResultsOptions.FIVE_HUNDRED);
		hitsComboBox.setSelectedItem(MaxNumberResultsOptions.TWO_HUNDRED_FIFTY);
		panel.add(hitsComboBox, "2, 12, fill, default");
		
		scoreOptionscomboBox.addItem(ScoreOptions.DEFAULT);
		scoreOptionscomboBox.addItem(ScoreOptions.FIVE);
		scoreOptionscomboBox.addItem(ScoreOptions.TEN);
		scoreOptionscomboBox.addItem(ScoreOptions.TWENTY);
		scoreOptionscomboBox.addItem(ScoreOptions.FIFTY);
		scoreOptionscomboBox.addItem(ScoreOptions.ONE_HUNDRED);
		scoreOptionscomboBox.addItem(ScoreOptions.ONE_HUNDRED_FIFTY);
		scoreOptionscomboBox.addItem(ScoreOptions.TWO_HUNDRED);
		scoreOptionscomboBox.addItem(ScoreOptions.TWO_HUNDRED_FIFTY);
		scoreOptionscomboBox.addItem(ScoreOptions.THREE_HUNDRED);
		scoreOptionscomboBox.addItem(ScoreOptions.THREE_HUNDRED_FIFTY);
		scoreOptionscomboBox.addItem(ScoreOptions.FOUR_HUNDRED);
		scoreOptionscomboBox.addItem(ScoreOptions.FOUR_HUNDRED_FIFTY);
		scoreOptionscomboBox.addItem(ScoreOptions.FIVE_HUNDRED);
		scoreOptionscomboBox.setSelectedItem(ScoreOptions.DEFAULT);
		panel.add(scoreOptionscomboBox, "4, 12, fill, default");
		
		JLabel sensitivityValue = new JLabel("Sensitivity Value");
		panel.add(sensitivityValue, "2, 14");		
		
		JLabel sortOptions = new JLabel("Sort Options");
		panel.add(sortOptions, "4, 14");		
		sensitivityValueComboBox.addItem(SensitivityValue.NORMAL);
		sensitivityValueComboBox.addItem(SensitivityValue.VLOW);
		sensitivityValueComboBox.addItem(SensitivityValue.LOW);
		sensitivityValueComboBox.addItem(SensitivityValue.MEDIUM);
		sensitivityValueComboBox.addItem(SensitivityValue.HIGH);
		sensitivityValueComboBox.setSelectedItem(SensitivityValue.NORMAL);
		panel.add(sensitivityValueComboBox, "2, 16, fill, default");
		sortOptionsComboBox.addItem(SortOptions.COUNT);
		sortOptionsComboBox.addItem(SortOptions.HIGHSCORE);
		sortOptionsComboBox.addItem(SortOptions.PVALUE);
		sortOptionsComboBox.addItem(SortOptions.TOTALSCORE);
		sortOptionsComboBox.setSelectedItem(SortOptions.PVALUE);
		panel.add(sortOptionsComboBox, "4, 16, fill, default");
		
		JLabel statsOptions = new JLabel("Stats Options");
		panel.add(statsOptions, "2, 18");
		
		JLabel formatOptions = new JLabel("Format Options");
		panel.add(formatOptions, "4, 18");		
		statsOptionsComboBox.addItem(StatsOptions.KAP);
		statsOptionsComboBox.addItem(StatsOptions.POISSON);
		statsOptionsComboBox.addItem(StatsOptions.SUMP);
		statsOptionsComboBox.setSelectedItem(StatsOptions.KAP);
		panel.add(statsOptionsComboBox, "2, 20, fill, default");
		
		formatOptionsComboBox.addItem(FormatOptions.DEFAULT);
		formatOptionsComboBox.addItem(FormatOptions.BLASTXML);
		formatOptionsComboBox.setSelectedItem(FormatOptions.DEFAULT);
		panel.add(formatOptionsComboBox, "4, 20, fill, default");
		
		JLabel topcomboN = new JLabel("Top Combo Number");
		panel.add(topcomboN, "2, 22");		
		topcomboNComboBox.addItem(TopcomboN.DEFAULT);
		topcomboNComboBox.addItem(TopcomboN.ONE);
		topcomboNComboBox.addItem(TopcomboN.TWO);
		topcomboNComboBox.addItem(TopcomboN.THREE);
		topcomboNComboBox.addItem(TopcomboN.FOUR);
		topcomboNComboBox.addItem(TopcomboN.FIVE);
		topcomboNComboBox.addItem(TopcomboN.FIFTY);
		topcomboNComboBox.addItem(TopcomboN.ONEHUNDRED);
		topcomboNComboBox.addItem(TopcomboN.ONETHOUSAND);
		topcomboNComboBox.setSelectedItem(TopcomboN.DEFAULT);
		panel.add(topcomboNComboBox, "2, 24, fill, default");
		
		panel.add(chckbxLimitToProteins, "2, 26");
		
		panel.add(chckbxLimitToSwissProt, "2, 28");
		
		panel.add(submitButton, "2, 46, 3, 1");	
		
		JLabel lblNewLabel = new JLabel("");
		panel.add(lblNewLabel, "2, 48");
		
		JLabel lblNewLabel_1 = new JLabel("");
		panel.add(lblNewLabel_1, "2, 50");
		
		setVisible(true);
	}

	@Override
	public JComboBox targetDatabaseComboBox() {
		// TODO Auto-generated method stub
		return targetDatabaseComboBox;
	}

	@Override
	public JComboBox eThresholdComboBox() {
		// TODO Auto-generated method stub
		return eThresholdComboBox;
	}

	@Override
	public JComboBox matrixComboBox() {
		// TODO Auto-generated method stub
		return matrixComboBox;
	}

	@Override
	public JComboBox gappedComboBox() {
		// TODO Auto-generated method stub
		return gappedComboBox;
	}

	@Override
	public JComboBox hitsComboBox() {
		// TODO Auto-generated method stub
		return hitsComboBox;
	}

	@Override
	public JButton submitButton() {
		// TODO Auto-generated method stub
		return submitButton;
	}

	@Override
	public JComboBox gapAlignComboBox() {
		// TODO Auto-generated method stub
		return gapAlignComboBox;
	}

	@Override
	public JComboBox sensitivityValueComboBox() {
		// TODO Auto-generated method stub
		return sensitivityValueComboBox;
	}

	@Override
	public JComboBox scoreOptionscomboBox() {
		// TODO Auto-generated method stub
		return scoreOptionscomboBox;
	}

	@Override
	public JComboBox statsOptionsComboBox() {
		// TODO Auto-generated method stub
		return statsOptionsComboBox;
	}

	@Override
	public JComboBox sortOptionsComboBox() {
		// TODO Auto-generated method stub
		return sortOptionsComboBox;
	}

	@Override
	public JComboBox topcomboNComboBox() {
		// TODO Auto-generated method stub
		return topcomboNComboBox;
	}

	@Override
	public JComboBox formatOptionsComboBox() {
		// TODO Auto-generated method stub
		return formatOptionsComboBox;
	}
	
	public static void main (String[]args) {
		BlastView bv = new BlastView();
	}

	@Override
	public JCheckBox chckbxLimitToProteins() {
		// TODO Auto-generated method stub
		return chckbxLimitToProteins;
	}

	@Override
	public JCheckBox chckbxLimitToSwissProt() {
		// TODO Auto-generated method stub
		return chckbxLimitToSwissProt;
	}
}
