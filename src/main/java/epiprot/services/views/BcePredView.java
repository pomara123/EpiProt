package epiprot.services.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.Font;

public class BcePredView extends JFrame implements BcePredPresenter.View {
	private JTextField hydrophilicityTextField;
	private JTextField accessibilityTextField;
	private JTextField exposedSurfaceTextField;
	private JTextField antegenicPropensityTextField;
	private JTextField flexibilityTextField;
	private JTextField turnsTextField;
	private JTextField polarityTextField;
	private JTextField combinedTextField;
	private JCheckBox hydrophilicityCheckBox;
	private JCheckBox accessibilityCheckBox;
	private JCheckBox exposedSurfaceCheckBox;
	private JCheckBox antegenicPropensityCheckBox;
	private JCheckBox flexibilityCheckBox;
	private JCheckBox turnsCheckBox;
	private JCheckBox polarityCheckBox;
	private JCheckBox combinedCheckBox;
	private JCheckBox chckbxSelectAll;
	private JButton btnSubmit;
	private JLabel lblNumbersMustBe;

	public BcePredView() {
		setTitle("BcePred");
		// TODO Auto-generated constructor stub
		setSize(new Dimension(610, 400));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {260, 286};
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gbl_panel.columnWeights = new double[]{1.0,1.0};
		panel.setLayout(gbl_panel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(5, 5, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.weightx = 1.0;
		gbc_panel_1.weighty = 1.0;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		TitledBorder phosphoTitle = new TitledBorder("Hydrophilicity");
		panel_1.setBorder(phosphoTitle);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		hydrophilicityTextField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel_1.add(hydrophilicityTextField, gbc_textField);
		hydrophilicityTextField.setColumns(10);
		
		hydrophilicityCheckBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox.fill = GridBagConstraints.BOTH;
		gbc_checkBox.gridx = 4;
		gbc_checkBox.gridy = 0;
		panel_1.add(hydrophilicityCheckBox, gbc_checkBox);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(5, 5, 5, 5);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.weightx = 1.0;
		gbc_panel_2.weighty = 1.0;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		TitledBorder acetylationTitle = new TitledBorder("Accessibility");
		panel_2.setBorder(acetylationTitle);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		accessibilityTextField = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 0, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 0;
		panel_2.add(accessibilityTextField, gbc_textField_2);
		accessibilityTextField.setColumns(10);
		
		accessibilityCheckBox = new JCheckBox();
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.gridx = 4;
		gbc_chckbxNewCheckBox.gridy = 0;
		panel_2.add(accessibilityCheckBox, gbc_chckbxNewCheckBox);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(5, 5, 5, 5);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.weightx = 1.0;
		gbc_panel_3.weighty = 1.0;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		TitledBorder methylationTitle = new TitledBorder("Exposed Surface");
		panel_3.setBorder(methylationTitle);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		exposedSurfaceTextField = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 0, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 0;
		panel_3.add(exposedSurfaceTextField, gbc_textField_4);
		exposedSurfaceTextField.setColumns(10);
		
		exposedSurfaceCheckBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
		gbc_checkBox_1.gridx = 4;
		gbc_checkBox_1.gridy = 0;
		panel_3.add(exposedSurfaceCheckBox, gbc_checkBox_1);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(5, 5, 5, 5);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.weightx = 1.0;
		gbc_panel_4.weighty = 1.0;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 3;
		panel.add(panel_4, gbc_panel_4);
		TitledBorder oGalnacTitle = new TitledBorder("Antigenic Propensity");
		panel_4.setBorder(oGalnacTitle);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_4.rowHeights = new int[]{0, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		antegenicPropensityTextField = new JTextField();
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 0, 5);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 1;
		gbc_textField_6.gridy = 0;
		panel_4.add(antegenicPropensityTextField, gbc_textField_6);
		antegenicPropensityTextField.setColumns(10);
		
		antegenicPropensityCheckBox = new JCheckBox();
		GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_1.gridx = 4;
		gbc_chckbxNewCheckBox_1.gridy = 0;
		panel_4.add(antegenicPropensityCheckBox, gbc_chckbxNewCheckBox_1);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(5, 5, 5, 0);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.weightx = 1.0;
		gbc_panel_5.weighty = 1.0;
		gbc_panel_5.gridx = 1;
		gbc_panel_5.gridy = 0;
		panel.add(panel_5, gbc_panel_5);
		TitledBorder oGlcnacTitle = new TitledBorder("Flexibility");
		panel_5.setBorder(oGlcnacTitle);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_5.rowHeights = new int[]{0, 0};
		gbl_panel_5.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		flexibilityTextField = new JTextField();
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.insets = new Insets(0, 0, 0, 5);
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.gridx = 1;
		gbc_textField_8.gridy = 0;
		panel_5.add(flexibilityTextField, gbc_textField_8);
		flexibilityTextField.setColumns(10);
		
		flexibilityCheckBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox_2 = new GridBagConstraints();
		gbc_checkBox_2.gridx = 4;
		gbc_checkBox_2.gridy = 0;
		panel_5.add(flexibilityCheckBox, gbc_checkBox_2);
		
		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(5, 5, 5, 0);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.weightx = 1.0;
		gbc_panel_6.weighty = 1.0;
		gbc_panel_6.gridx = 1;
		gbc_panel_6.gridy = 1;
		panel.add(panel_6, gbc_panel_6);
		TitledBorder sumoylationTitle = new TitledBorder("Turns");
		panel_6.setBorder(sumoylationTitle);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_6.rowHeights = new int[]{0, 0};
		gbl_panel_6.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_6.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_6.setLayout(gbl_panel_6);
		
		turnsTextField = new JTextField();
		GridBagConstraints gbc_textField_10 = new GridBagConstraints();
		gbc_textField_10.insets = new Insets(0, 0, 0, 5);
		gbc_textField_10.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_10.gridx = 1;
		gbc_textField_10.gridy = 0;
		panel_6.add(turnsTextField, gbc_textField_10);
		turnsTextField.setColumns(10);
		
		turnsCheckBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox_3 = new GridBagConstraints();
		gbc_checkBox_3.gridx = 4;
		gbc_checkBox_3.gridy = 0;
		panel_6.add(turnsCheckBox, gbc_checkBox_3);
		
		JPanel panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.insets = new Insets(5, 5, 5, 0);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.weightx = 1.0;
		gbc_panel_7.weighty = 1.0;
		gbc_panel_7.gridx = 1;
		gbc_panel_7.gridy = 2;
		panel.add(panel_7, gbc_panel_7);
		TitledBorder ubiquitinationTitle = new TitledBorder("Polarity");
		panel_7.setBorder(ubiquitinationTitle);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_7.rowHeights = new int[]{0, 0};
		gbl_panel_7.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_7.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_7.setLayout(gbl_panel_7);
		
		polarityTextField = new JTextField();
		GridBagConstraints gbc_textField_12 = new GridBagConstraints();
		gbc_textField_12.insets = new Insets(0, 0, 0, 5);
		gbc_textField_12.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_12.gridx = 1;
		gbc_textField_12.gridy = 0;
		panel_7.add(polarityTextField, gbc_textField_12);
		polarityTextField.setColumns(10);
		
		polarityCheckBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox_4 = new GridBagConstraints();
		gbc_checkBox_4.gridx = 4;
		gbc_checkBox_4.gridy = 0;
		panel_7.add(polarityCheckBox, gbc_checkBox_4);
		
		JPanel panel_8 = new JPanel();
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.insets = new Insets(5, 5, 5, 0);
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.weightx = 1.0;
		gbc_panel_8.weighty = 1.0;
		gbc_panel_8.gridx = 1;
		gbc_panel_8.gridy = 3;
		panel.add(panel_8, gbc_panel_8);
		panel_8.setBorder(new TitledBorder("Combined"));
		GridBagLayout gbl_panel_8 = new GridBagLayout();
		gbl_panel_8.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_8.rowHeights = new int[]{0, 0};
		gbl_panel_8.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_8.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_8.setLayout(gbl_panel_8);
		
		combinedTextField = new JTextField();
		GridBagConstraints gbc_textField_13 = new GridBagConstraints();
		gbc_textField_13.insets = new Insets(0, 0, 0, 5);
		gbc_textField_13.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_13.gridx = 1;
		gbc_textField_13.gridy = 0;
		panel_8.add(combinedTextField, gbc_textField_13);
		combinedTextField.setColumns(10);
		combinedTextField.setEnabled(false);
		
		combinedCheckBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox_5 = new GridBagConstraints();
		gbc_checkBox_5.gridx = 4;
		gbc_checkBox_5.gridy = 0;
		panel_8.add(combinedCheckBox, gbc_checkBox_5);
		combinedCheckBox.setEnabled(false);
		
		chckbxSelectAll = new JCheckBox("Select All");
		GridBagConstraints gbc_chckbxSelectAll = new GridBagConstraints();
		gbc_chckbxSelectAll.insets = new Insets(0, 0, 5, 5);
		gbc_chckbxSelectAll.gridx = 0;
		gbc_chckbxSelectAll.gridy = 4;
		gbc_chckbxSelectAll.gridwidth = 2;
		panel.add(chckbxSelectAll, gbc_chckbxSelectAll);
		
		JPanel panel_9 = new JPanel();
		GridBagConstraints gbc_panel_9 = new GridBagConstraints();
		gbc_panel_9.insets = new Insets(0, 0, 5, 5);
		gbc_panel_9.fill = GridBagConstraints.BOTH;
		gbc_panel_9.gridx = 0;
		gbc_panel_9.gridy = 5;
		gbc_panel_9.gridwidth = 2;
		panel.add(panel_9, gbc_panel_9);
		
		lblNumbersMustBe = new JLabel("Numbers must be between -3 and 3. At least two checkboxes must be selected in order to select \"Combined\".");
		lblNumbersMustBe.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		panel_9.add(lblNumbersMustBe);
		
		btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 5);
		gbc_btnSubmit.gridx = 0;
		gbc_btnSubmit.gridy = 6;
		gbc_btnSubmit.gridwidth = 2;
		panel.add(btnSubmit, gbc_btnSubmit);
		btnSubmit.setEnabled(false);
		
		hydrophilicityTextField.setText("2");
		accessibilityTextField.setText("2");
		exposedSurfaceTextField.setText("2.4");
		antegenicPropensityTextField.setText("1.8");
		flexibilityTextField.setText("1.9");
		turnsTextField.setText("1.9");
		polarityTextField.setText("2.3");
		combinedTextField.setText("1.9");
		
		setVisible(true);
	}

	@Override
	public JTextField hydrophilicityTextField() {
		// TODO Auto-generated method stub
		return hydrophilicityTextField;
	}

	@Override
	public JTextField accessibilityTextField() {
		// TODO Auto-generated method stub
		return accessibilityTextField;
	}

	@Override
	public JTextField exposedSurfaceTextField() {
		// TODO Auto-generated method stub
		return exposedSurfaceTextField;
	}

	@Override
	public JTextField antegenicPropensityTextField() {
		// TODO Auto-generated method stub
		return antegenicPropensityTextField;
	}

	@Override
	public JTextField flexibilityTextField() {
		// TODO Auto-generated method stub
		return flexibilityTextField;
	}

	@Override
	public JTextField turnsTextField() {
		// TODO Auto-generated method stub
		return turnsTextField;
	}

	@Override
	public JTextField polarityTextField() {
		// TODO Auto-generated method stub
		return polarityTextField;
	}

	@Override
	public JCheckBox hydrophilicityCheckBox() {
		// TODO Auto-generated method stub
		return hydrophilicityCheckBox;
	}

	@Override
	public JCheckBox accessibilityCheckBox() {
		// TODO Auto-generated method stub
		return accessibilityCheckBox;
	}

	@Override
	public JCheckBox exposedSurfaceCheckBox() {
		// TODO Auto-generated method stub
		return exposedSurfaceCheckBox;
	}

	@Override
	public JCheckBox antegenicPropensityCheckBox() {
		// TODO Auto-generated method stub
		return antegenicPropensityCheckBox;
	}

	@Override
	public JCheckBox flexibilityCheckBox() {
		// TODO Auto-generated method stub
		return flexibilityCheckBox;
	}

	@Override
	public JCheckBox turnsCheckBox() {
		// TODO Auto-generated method stub
		return turnsCheckBox;
	}

	@Override
	public JCheckBox polarityCheckBox() {
		// TODO Auto-generated method stub
		return polarityCheckBox;
	}

	@Override
	public JButton btnSubmit() {
		// TODO Auto-generated method stub
		return btnSubmit;
	}

	@Override
	public JCheckBox chckbxSelectAll() {
		// TODO Auto-generated method stub
		return chckbxSelectAll;
	}

	@Override
	public JTextField combinedTextField() {
		// TODO Auto-generated method stub
		return combinedTextField;
	}

	@Override
	public JCheckBox combinedCheckBox() {
		// TODO Auto-generated method stub
		return combinedCheckBox;
	}
}
