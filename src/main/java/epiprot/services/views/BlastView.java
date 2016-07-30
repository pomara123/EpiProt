package epiprot.services.views;

import javax.swing.JFrame;
import java.awt.Dimension;

import javax.swing.JComboBox;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.DatabaseOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.DropOffOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.ExpectationOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.FilterOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.MatrixOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.ScoreCutoffOption;

import javax.swing.JButton;
import javax.swing.JCheckBox;

public class BlastView extends JFrame implements BlastPresenter.View{
	
	JComboBox databaseOptionComboBox = new JComboBox();
	JComboBox expectationOptionComboBox = new JComboBox();
	JComboBox filterOptionComboBox = new JComboBox();
	JComboBox dropOffOptionComboBox = new JComboBox();
	JComboBox isGapAlignComboBox = new JComboBox();
	JTextField gapExtTextField = new JTextField("-1");
	JTextField gapOpenTextField = new JTextField("-1");
	JComboBox matrixOptionComboBox = new JComboBox();
	//JComboBox alignmentCutoffOption = new JComboBox();
	JComboBox scoreCutoffOptionComboBox = new JComboBox();
	JCheckBox chckbxLimitToProteins = new JCheckBox("Limit to proteins of the target protein species");
	JCheckBox chckbxLimitToSwissProt = new JCheckBox("Limit to proteins from Swiss-Prot Database");
	JButton submitButton = new JButton("Submit");

	public BlastView() {
		getContentPane().setPreferredSize(new Dimension(400, 500));
		setTitle("BLAST");
		setPreferredSize(new Dimension(700, 500));
		pack();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(75dlu;default):grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(75dlu;default):grow"),
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
		
		JLabel databaseOptionLabel = new JLabel("Target Database");
		panel.add(databaseOptionLabel, "2, 2");
			
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROTKB);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_ARCHAEA);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_BACTERIA);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_EUKARYOTA);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_ARTHROPODA);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_FUNGI);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_HUMAN);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_MAMMALS);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_VIRIDIPLANTAE);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_RODENTS);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_VERTEBRATES);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_VIRUSES);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_PDB);
		//databaseOptionComboBox.addItem("Reference proteomes");
		databaseOptionComboBox.addItem(DatabaseOption.UNIPROT_COMPLETE_MICROBIAL_PROTEOMES);
		databaseOptionComboBox.addItem(DatabaseOption.SWISSPROT);
		databaseOptionComboBox.addItem(DatabaseOption.UNIREF_100);
		databaseOptionComboBox.addItem(DatabaseOption.UNIREF_90);
		databaseOptionComboBox.addItem(DatabaseOption.UNIREF_50);
		databaseOptionComboBox.addItem(DatabaseOption.UNIPARC);
		databaseOptionComboBox.addItem(DatabaseOption.TREMBL);
		databaseOptionComboBox.setSelectedItem(DatabaseOption.SWISSPROT);
		panel.add(databaseOptionComboBox, "2, 4, fill, default");
		
		JLabel expectationOptionLabel = new JLabel("E-Threshold");
		panel.add(expectationOptionLabel, "4, 2");	
		
		expectationOptionComboBox.addItem(ExpectationOption.ONE_EXPONENT_MINUS_ONE_HUNDRED);
		expectationOptionComboBox.addItem(ExpectationOption.ONE_EXPONENT_MINUS_TEN);
		expectationOptionComboBox.addItem(ExpectationOption.ONE_EXPONENT_MINUS_ONE);
		expectationOptionComboBox.addItem(ExpectationOption.ONE);
		expectationOptionComboBox.addItem(ExpectationOption.TEN);
		expectationOptionComboBox.addItem(ExpectationOption.ONE_HUNDRED);
		expectationOptionComboBox.addItem(ExpectationOption.ONE_THOUSAND);
		expectationOptionComboBox.setSelectedItem(ExpectationOption.TEN);
		panel.add(expectationOptionComboBox, "4, 4, fill, default");
		
		JLabel matrixLabel = new JLabel("Matrix");
		panel.add(matrixLabel, "2, 6");
		matrixOptionComboBox.addItem(MatrixOption.BLOSUM_45);
		matrixOptionComboBox.addItem(MatrixOption.BLOSUM_62);
		matrixOptionComboBox.addItem(MatrixOption.BLOSUM_80);
		matrixOptionComboBox.addItem(MatrixOption.PAM_70);
		matrixOptionComboBox.addItem(MatrixOption.PAM_30);
		matrixOptionComboBox.setSelectedItem(MatrixOption.BLOSUM_62);
		panel.add(matrixOptionComboBox, "2, 8, fill, default");
		
		JLabel isGapAlignLabel = new JLabel("Gapped");
		panel.add(isGapAlignLabel, "4, 6");
		isGapAlignComboBox.addItem(true);
		isGapAlignComboBox.addItem(false);
		isGapAlignComboBox.setSelectedItem(true);
		panel.add(isGapAlignComboBox, "4, 8, fill, default");
		
		JLabel scoreCutoffeOptionsLabel = new JLabel("Number of hits");
		panel.add(scoreCutoffeOptionsLabel, "2, 10");
			
		scoreCutoffOptionComboBox.addItem(ScoreCutoffOption.FIVE);
		scoreCutoffOptionComboBox.addItem(ScoreCutoffOption.TEN);
		scoreCutoffOptionComboBox.addItem(ScoreCutoffOption.TWENTY);
		scoreCutoffOptionComboBox.addItem(ScoreCutoffOption.FIFTY);
		scoreCutoffOptionComboBox.addItem(ScoreCutoffOption.ONE_HUNDRED_FIFTY);
		scoreCutoffOptionComboBox.addItem(ScoreCutoffOption.TWO_HUNDRED);
		scoreCutoffOptionComboBox.addItem(ScoreCutoffOption.TWO_HUNDRED_FIFTY);
		scoreCutoffOptionComboBox.addItem(ScoreCutoffOption.FIVE_HUNDRED);
		scoreCutoffOptionComboBox.setSelectedItem(ScoreCutoffOption.TWO_HUNDRED_FIFTY);
		panel.add(scoreCutoffOptionComboBox, "2, 12, fill, default");
		
		JLabel filterOptionLabel = new JLabel("Filter Options");
		panel.add(filterOptionLabel, "4, 10");		
				
		filterOptionComboBox.addItem(FilterOption.YES);
		filterOptionComboBox.addItem(FilterOption.NO);
		filterOptionComboBox.setSelectedItem(FilterOption.NO);
		panel.add(filterOptionComboBox, "4, 12, fill, default");
		
		JLabel dropOffOptionLabel = new JLabel("Drop Off Option");
		panel.add(dropOffOptionLabel, "2, 14");
		
		dropOffOptionComboBox.addItem(DropOffOption.ZERO);
		dropOffOptionComboBox.addItem(DropOffOption.TWO);
		dropOffOptionComboBox.addItem(DropOffOption.FOUR);
		dropOffOptionComboBox.addItem(DropOffOption.SIX);
		dropOffOptionComboBox.addItem(DropOffOption.EIGHT);
		dropOffOptionComboBox.addItem(DropOffOption.TEN);
		dropOffOptionComboBox.setSelectedItem(DropOffOption.ZERO);
		panel.add(dropOffOptionComboBox, "2, 16, fill, default");
		
		JLabel gapOpenLabel = new JLabel("Gap Open. Default = -1, pick # 1-4");
		panel.add(gapOpenLabel, "2, 18");		
		panel.add(gapOpenTextField, "2, 20, fill, default");
		
		JLabel gapExtLabel = new JLabel("Gap Extend. Default = -1, pick # 1-4");
		panel.add(gapExtLabel, "4, 18");		
		panel.add(gapExtTextField, "4, 20, fill, default");
		
		panel.add(chckbxLimitToProteins, "2, 26");
		
		panel.add(chckbxLimitToSwissProt, "2, 28");
		
		panel.add(submitButton, "2, 30, 3, 1");	
		
		JLabel lblNewLabel = new JLabel("");
		panel.add(lblNewLabel, "2, 48");
		
		JLabel lblNewLabel_1 = new JLabel("");
		panel.add(lblNewLabel_1, "2, 50");
		
		setVisible(true);
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



	@Override
	public JComboBox databaseOptionComboBox() {
		// TODO Auto-generated method stub
		return databaseOptionComboBox;
	}



	@Override
	public JComboBox expectationOptionComboBox() {
		// TODO Auto-generated method stub
		return expectationOptionComboBox;
	}



	@Override
	public JComboBox filterOptionComboBox() {
		// TODO Auto-generated method stub
		return filterOptionComboBox;
	}



	@Override
	public JComboBox dropOffOptionComboBox() {
		// TODO Auto-generated method stub
		return dropOffOptionComboBox;
	}



	@Override
	public JComboBox isGapAlignComboBox() {
		// TODO Auto-generated method stub
		return isGapAlignComboBox;
	}



	@Override
	public JTextField gapExtTextField() {
		// TODO Auto-generated method stub
		return gapExtTextField;
	}



	@Override
	public JTextField gapOpenTextField() {
		// TODO Auto-generated method stub
		return gapOpenTextField;
	}



	@Override
	public JComboBox matrixOptionComboBox() {
		// TODO Auto-generated method stub
		return matrixOptionComboBox;
	}



	@Override
	public JComboBox scoreCutoffOptionComboBox() {
		// TODO Auto-generated method stub
		return scoreCutoffOptionComboBox;
	}



	@Override
	public JButton submitButton() {
		// TODO Auto-generated method stub
		return submitButton;
	}
}
