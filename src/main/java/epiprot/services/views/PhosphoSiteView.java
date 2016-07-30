package epiprot.services.views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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

public class PhosphoSiteView extends JFrame implements PhosphoSitePresenter.View {
	private JTextField htpPhosphoTextField;
	private JTextField ltpPhosphoTextField;
	private JTextField htpAcetylTextField;
	private JTextField ltpAcetylTextField;
	private JTextField htpMethylTextField;
	private JTextField ltpMethylTextField;
	private JTextField htpOgalnacTextField;
	private JTextField ltpOgalnacTextField;
	private JTextField htpOglcnacTextField;
	private JTextField ltpOglcnacTextField;
	private JTextField htpSumoylTextField;
	private JTextField ltpSumoylTextField;
	private JTextField htpUbiquitinTextField;
	private JTextField ltpUbiquitinTextField;
	private JCheckBox phosphoCheckBox;
	private JCheckBox acetylCheckBox;
	private JCheckBox methylCheckBox;
	private JCheckBox ogalnacCheckBox;
	private JCheckBox oglcnacCheckBox;
	private JCheckBox sumoylCheckBox;
	private JCheckBox ubiquitinCheckBox;
	private JCheckBox uniprotCheckBox;
	private JCheckBox chckbxSelectAll;
	private JButton btnSubmit;

	public PhosphoSiteView() {
		setTitle("Post-Translational Modifications");
		// TODO Auto-generated constructor stub
		setSize(new Dimension(500, 600));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0};
		gbl_panel.columnWeights = new double[]{1.0};
		panel.setLayout(gbl_panel);
		
		JPanel panel_1 = new JPanel();
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(5, 5, 5, 0);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.weightx = 1.0;
		gbc_panel_1.weighty = 1.0;
		gbc_panel_1.gridx = 0;
		gbc_panel_1.gridy = 0;
		panel.add(panel_1, gbc_panel_1);
		TitledBorder phosphoTitle = new TitledBorder("Phosphorylation");
		panel_1.setBorder(phosphoTitle);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0};
		gbl_panel_1.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		JLabel lblHtp = new JLabel("HTP");
		GridBagConstraints gbc_lblHtp = new GridBagConstraints();
		gbc_lblHtp.fill = GridBagConstraints.BOTH;
		gbc_lblHtp.insets = new Insets(0, 0, 5, 5);
		gbc_lblHtp.gridx = 0;
		gbc_lblHtp.gridy = 0;
		panel_1.add(lblHtp, gbc_lblHtp);
		
		htpPhosphoTextField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.BOTH;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		panel_1.add(htpPhosphoTextField, gbc_textField);
		htpPhosphoTextField.setColumns(10);
		
		JLabel lblLtp = new JLabel("LTP");
		GridBagConstraints gbc_lblLtp = new GridBagConstraints();
		gbc_lblLtp.fill = GridBagConstraints.BOTH;
		gbc_lblLtp.insets = new Insets(0, 0, 5, 5);
		gbc_lblLtp.anchor = GridBagConstraints.EAST;
		gbc_lblLtp.gridx = 2;
		gbc_lblLtp.gridy = 0;
		panel_1.add(lblLtp, gbc_lblLtp);
		
		ltpPhosphoTextField = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.BOTH;
		gbc_textField_1.gridx = 3;
		gbc_textField_1.gridy = 0;
		panel_1.add(ltpPhosphoTextField, gbc_textField_1);
		ltpPhosphoTextField.setColumns(10);
		
		phosphoCheckBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox = new GridBagConstraints();
		gbc_checkBox.insets = new Insets(0, 0, 5, 0);
		gbc_checkBox.fill = GridBagConstraints.BOTH;
		gbc_checkBox.gridx = 4;
		gbc_checkBox.gridy = 0;
		panel_1.add(phosphoCheckBox, gbc_checkBox);
		
		JPanel panel_2 = new JPanel();
		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.insets = new Insets(5, 5, 5, 0);
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.weightx = 1.0;
		gbc_panel_2.weighty = 1.0;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel.add(panel_2, gbc_panel_2);
		TitledBorder acetylationTitle = new TitledBorder("Acetylation");
		panel_2.setBorder(acetylationTitle);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_2.rowHeights = new int[]{0, 0};
		gbl_panel_2.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_2.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_2.setLayout(gbl_panel_2);
		
		JLabel lblHtp_1 = new JLabel("HTP");
		GridBagConstraints gbc_lblHtp_1 = new GridBagConstraints();
		gbc_lblHtp_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblHtp_1.anchor = GridBagConstraints.EAST;
		gbc_lblHtp_1.gridx = 0;
		gbc_lblHtp_1.gridy = 0;
		panel_2.add(lblHtp_1, gbc_lblHtp_1);
		
		htpAcetylTextField = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.insets = new Insets(0, 0, 0, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 1;
		gbc_textField_2.gridy = 0;
		panel_2.add(htpAcetylTextField, gbc_textField_2);
		htpAcetylTextField.setColumns(10);
		
		JLabel lblLtp_1 = new JLabel("LTP");
		GridBagConstraints gbc_lblLtp_1 = new GridBagConstraints();
		gbc_lblLtp_1.insets = new Insets(0, 0, 0, 5);
		gbc_lblLtp_1.anchor = GridBagConstraints.EAST;
		gbc_lblLtp_1.gridx = 2;
		gbc_lblLtp_1.gridy = 0;
		panel_2.add(lblLtp_1, gbc_lblLtp_1);
		
		ltpAcetylTextField = new JTextField();
		GridBagConstraints gbc_textField_3 = new GridBagConstraints();
		gbc_textField_3.insets = new Insets(0, 0, 0, 5);
		gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_3.gridx = 3;
		gbc_textField_3.gridy = 0;
		panel_2.add(ltpAcetylTextField, gbc_textField_3);
		ltpAcetylTextField.setColumns(10);
		
		acetylCheckBox = new JCheckBox();
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.gridx = 4;
		gbc_chckbxNewCheckBox.gridy = 0;
		panel_2.add(acetylCheckBox, gbc_chckbxNewCheckBox);
		
		JPanel panel_3 = new JPanel();
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(5, 5, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.weightx = 1.0;
		gbc_panel_3.weighty = 1.0;
		gbc_panel_3.gridx = 0;
		gbc_panel_3.gridy = 2;
		panel.add(panel_3, gbc_panel_3);
		TitledBorder methylationTitle = new TitledBorder("Methylation");
		panel_3.setBorder(methylationTitle);
		GridBagLayout gbl_panel_3 = new GridBagLayout();
		gbl_panel_3.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_3.rowHeights = new int[]{0, 0};
		gbl_panel_3.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_3.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_3.setLayout(gbl_panel_3);
		
		JLabel lblHtp_2 = new JLabel("HTP");
		GridBagConstraints gbc_lblHtp_2 = new GridBagConstraints();
		gbc_lblHtp_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblHtp_2.anchor = GridBagConstraints.EAST;
		gbc_lblHtp_2.gridx = 0;
		gbc_lblHtp_2.gridy = 0;
		panel_3.add(lblHtp_2, gbc_lblHtp_2);
		
		htpMethylTextField = new JTextField();
		GridBagConstraints gbc_textField_4 = new GridBagConstraints();
		gbc_textField_4.insets = new Insets(0, 0, 0, 5);
		gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_4.gridx = 1;
		gbc_textField_4.gridy = 0;
		panel_3.add(htpMethylTextField, gbc_textField_4);
		htpMethylTextField.setColumns(10);
		
		JLabel lblLtp_2 = new JLabel("LTP");
		GridBagConstraints gbc_lblLtp_2 = new GridBagConstraints();
		gbc_lblLtp_2.insets = new Insets(0, 0, 0, 5);
		gbc_lblLtp_2.anchor = GridBagConstraints.EAST;
		gbc_lblLtp_2.gridx = 2;
		gbc_lblLtp_2.gridy = 0;
		panel_3.add(lblLtp_2, gbc_lblLtp_2);
		
		ltpMethylTextField = new JTextField();
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.insets = new Insets(0, 0, 0, 5);
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.gridx = 3;
		gbc_textField_5.gridy = 0;
		panel_3.add(ltpMethylTextField, gbc_textField_5);
		ltpMethylTextField.setColumns(10);
		
		methylCheckBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
		gbc_checkBox_1.gridx = 4;
		gbc_checkBox_1.gridy = 0;
		panel_3.add(methylCheckBox, gbc_checkBox_1);
		
		JPanel panel_4 = new JPanel();
		GridBagConstraints gbc_panel_4 = new GridBagConstraints();
		gbc_panel_4.insets = new Insets(5, 5, 5, 0);
		gbc_panel_4.fill = GridBagConstraints.BOTH;
		gbc_panel_4.weightx = 1.0;
		gbc_panel_4.weighty = 1.0;
		gbc_panel_4.gridx = 0;
		gbc_panel_4.gridy = 3;
		panel.add(panel_4, gbc_panel_4);
		TitledBorder oGalnacTitle = new TitledBorder("O-Galnac");
		panel_4.setBorder(oGalnacTitle);
		GridBagLayout gbl_panel_4 = new GridBagLayout();
		gbl_panel_4.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_4.rowHeights = new int[]{0, 0};
		gbl_panel_4.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_4.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_4.setLayout(gbl_panel_4);
		
		JLabel lblHtp_3 = new JLabel("HTP");
		GridBagConstraints gbc_lblHtp_3 = new GridBagConstraints();
		gbc_lblHtp_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblHtp_3.anchor = GridBagConstraints.EAST;
		gbc_lblHtp_3.gridx = 0;
		gbc_lblHtp_3.gridy = 0;
		panel_4.add(lblHtp_3, gbc_lblHtp_3);
		
		htpOgalnacTextField = new JTextField();
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.insets = new Insets(0, 0, 0, 5);
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.gridx = 1;
		gbc_textField_6.gridy = 0;
		panel_4.add(htpOgalnacTextField, gbc_textField_6);
		htpOgalnacTextField.setColumns(10);
		
		JLabel lblLtp_3 = new JLabel("LTP");
		GridBagConstraints gbc_lblLtp_3 = new GridBagConstraints();
		gbc_lblLtp_3.insets = new Insets(0, 0, 0, 5);
		gbc_lblLtp_3.anchor = GridBagConstraints.EAST;
		gbc_lblLtp_3.gridx = 2;
		gbc_lblLtp_3.gridy = 0;
		panel_4.add(lblLtp_3, gbc_lblLtp_3);
		
		ltpOgalnacTextField = new JTextField();
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.insets = new Insets(0, 0, 0, 5);
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.gridx = 3;
		gbc_textField_7.gridy = 0;
		panel_4.add(ltpOgalnacTextField, gbc_textField_7);
		ltpOgalnacTextField.setColumns(10);
		
		ogalnacCheckBox = new JCheckBox();
		GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_1.gridx = 4;
		gbc_chckbxNewCheckBox_1.gridy = 0;
		panel_4.add(ogalnacCheckBox, gbc_chckbxNewCheckBox_1);
		
		JPanel panel_5 = new JPanel();
		GridBagConstraints gbc_panel_5 = new GridBagConstraints();
		gbc_panel_5.insets = new Insets(5, 5, 5, 0);
		gbc_panel_5.fill = GridBagConstraints.BOTH;
		gbc_panel_5.weightx = 1.0;
		gbc_panel_5.weighty = 1.0;
		gbc_panel_5.gridx = 0;
		gbc_panel_5.gridy = 4;
		panel.add(panel_5, gbc_panel_5);
		TitledBorder oGlcnacTitle = new TitledBorder("O-Glcnac");
		panel_5.setBorder(oGlcnacTitle);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_5.rowHeights = new int[]{0, 0};
		gbl_panel_5.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		JLabel lblHtp_4 = new JLabel("HTP");
		GridBagConstraints gbc_lblHtp_4 = new GridBagConstraints();
		gbc_lblHtp_4.insets = new Insets(0, 0, 0, 5);
		gbc_lblHtp_4.anchor = GridBagConstraints.EAST;
		gbc_lblHtp_4.gridx = 0;
		gbc_lblHtp_4.gridy = 0;
		panel_5.add(lblHtp_4, gbc_lblHtp_4);
		
		htpOglcnacTextField = new JTextField();
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.insets = new Insets(0, 0, 0, 5);
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.gridx = 1;
		gbc_textField_8.gridy = 0;
		panel_5.add(htpOglcnacTextField, gbc_textField_8);
		htpOglcnacTextField.setColumns(10);
		
		JLabel lblLtp_4 = new JLabel("LTP");
		GridBagConstraints gbc_lblLtp_4 = new GridBagConstraints();
		gbc_lblLtp_4.insets = new Insets(0, 0, 0, 5);
		gbc_lblLtp_4.anchor = GridBagConstraints.EAST;
		gbc_lblLtp_4.gridx = 2;
		gbc_lblLtp_4.gridy = 0;
		panel_5.add(lblLtp_4, gbc_lblLtp_4);
		
		ltpOglcnacTextField = new JTextField();
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.insets = new Insets(0, 0, 0, 5);
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.gridx = 3;
		gbc_textField_9.gridy = 0;
		panel_5.add(ltpOglcnacTextField, gbc_textField_9);
		ltpOglcnacTextField.setColumns(10);
		
		oglcnacCheckBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox_2 = new GridBagConstraints();
		gbc_checkBox_2.gridx = 4;
		gbc_checkBox_2.gridy = 0;
		panel_5.add(oglcnacCheckBox, gbc_checkBox_2);
		
		JPanel panel_6 = new JPanel();
		GridBagConstraints gbc_panel_6 = new GridBagConstraints();
		gbc_panel_6.insets = new Insets(5, 5, 5, 0);
		gbc_panel_6.fill = GridBagConstraints.BOTH;
		gbc_panel_6.weightx = 1.0;
		gbc_panel_6.weighty = 1.0;
		gbc_panel_6.gridx = 0;
		gbc_panel_6.gridy = 5;
		panel.add(panel_6, gbc_panel_6);
		TitledBorder sumoylationTitle = new TitledBorder("Sumoylation");
		panel_6.setBorder(sumoylationTitle);
		GridBagLayout gbl_panel_6 = new GridBagLayout();
		gbl_panel_6.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_6.rowHeights = new int[]{0, 0};
		gbl_panel_6.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_6.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_6.setLayout(gbl_panel_6);
		
		JLabel lblHtp_5 = new JLabel("HTP");
		GridBagConstraints gbc_lblHtp_5 = new GridBagConstraints();
		gbc_lblHtp_5.insets = new Insets(0, 0, 0, 5);
		gbc_lblHtp_5.anchor = GridBagConstraints.EAST;
		gbc_lblHtp_5.gridx = 0;
		gbc_lblHtp_5.gridy = 0;
		panel_6.add(lblHtp_5, gbc_lblHtp_5);
		
		htpSumoylTextField = new JTextField();
		GridBagConstraints gbc_textField_10 = new GridBagConstraints();
		gbc_textField_10.insets = new Insets(0, 0, 0, 5);
		gbc_textField_10.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_10.gridx = 1;
		gbc_textField_10.gridy = 0;
		panel_6.add(htpSumoylTextField, gbc_textField_10);
		htpSumoylTextField.setColumns(10);
		
		JLabel lblLtp_5 = new JLabel("LTP");
		GridBagConstraints gbc_lblLtp_5 = new GridBagConstraints();
		gbc_lblLtp_5.insets = new Insets(0, 0, 0, 5);
		gbc_lblLtp_5.anchor = GridBagConstraints.EAST;
		gbc_lblLtp_5.gridx = 2;
		gbc_lblLtp_5.gridy = 0;
		panel_6.add(lblLtp_5, gbc_lblLtp_5);
		
		ltpSumoylTextField = new JTextField();
		GridBagConstraints gbc_textField_11 = new GridBagConstraints();
		gbc_textField_11.insets = new Insets(0, 0, 0, 5);
		gbc_textField_11.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_11.gridx = 3;
		gbc_textField_11.gridy = 0;
		panel_6.add(ltpSumoylTextField, gbc_textField_11);
		ltpSumoylTextField.setColumns(10);
		
		sumoylCheckBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox_3 = new GridBagConstraints();
		gbc_checkBox_3.gridx = 4;
		gbc_checkBox_3.gridy = 0;
		panel_6.add(sumoylCheckBox, gbc_checkBox_3);
		
		JPanel panel_7 = new JPanel();
		GridBagConstraints gbc_panel_7 = new GridBagConstraints();
		gbc_panel_7.insets = new Insets(5, 5, 5, 0);
		gbc_panel_7.fill = GridBagConstraints.BOTH;
		gbc_panel_7.weightx = 1.0;
		gbc_panel_7.weighty = 1.0;
		gbc_panel_7.gridx = 0;
		gbc_panel_7.gridy = 6;
		panel.add(panel_7, gbc_panel_7);
		TitledBorder ubiquitinationTitle = new TitledBorder("Ubiquitination");
		panel_7.setBorder(ubiquitinationTitle);
		GridBagLayout gbl_panel_7 = new GridBagLayout();
		gbl_panel_7.columnWidths = new int[]{0, 0, 0, 0, 0, 0};
		gbl_panel_7.rowHeights = new int[]{0, 0};
		gbl_panel_7.columnWeights = new double[]{0.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_panel_7.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		panel_7.setLayout(gbl_panel_7);
		
		JLabel lblHtp_6 = new JLabel("HTP");
		GridBagConstraints gbc_lblHtp_6 = new GridBagConstraints();
		gbc_lblHtp_6.insets = new Insets(0, 0, 0, 5);
		gbc_lblHtp_6.anchor = GridBagConstraints.EAST;
		gbc_lblHtp_6.gridx = 0;
		gbc_lblHtp_6.gridy = 0;
		panel_7.add(lblHtp_6, gbc_lblHtp_6);
		
		htpUbiquitinTextField = new JTextField();
		GridBagConstraints gbc_textField_12 = new GridBagConstraints();
		gbc_textField_12.insets = new Insets(0, 0, 0, 5);
		gbc_textField_12.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_12.gridx = 1;
		gbc_textField_12.gridy = 0;
		panel_7.add(htpUbiquitinTextField, gbc_textField_12);
		htpUbiquitinTextField.setColumns(10);
		
		JLabel lblLtp_6 = new JLabel("LTP");
		GridBagConstraints gbc_lblLtp_6 = new GridBagConstraints();
		gbc_lblLtp_6.insets = new Insets(0, 0, 0, 5);
		gbc_lblLtp_6.anchor = GridBagConstraints.EAST;
		gbc_lblLtp_6.gridx = 2;
		gbc_lblLtp_6.gridy = 0;
		panel_7.add(lblLtp_6, gbc_lblLtp_6);
		
		ltpUbiquitinTextField = new JTextField();
		GridBagConstraints gbc_textField_13 = new GridBagConstraints();
		gbc_textField_13.insets = new Insets(0, 0, 0, 5);
		gbc_textField_13.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_13.gridx = 3;
		gbc_textField_13.gridy = 0;
		panel_7.add(ltpUbiquitinTextField, gbc_textField_13);
		ltpUbiquitinTextField.setColumns(10);
		
		ubiquitinCheckBox = new JCheckBox("");
		GridBagConstraints gbc_checkBox_4 = new GridBagConstraints();
		gbc_checkBox_4.gridx = 4;
		gbc_checkBox_4.gridy = 0;
		panel_7.add(ubiquitinCheckBox, gbc_checkBox_4);
		
		JPanel panel_8 = new JPanel();
		GridBagConstraints gbc_panel_8 = new GridBagConstraints();
		gbc_panel_8.insets = new Insets(0, 0, 5, 0);
		gbc_panel_8.fill = GridBagConstraints.BOTH;
		gbc_panel_8.gridx = 0;
		gbc_panel_8.gridy = 7;
		panel.add(panel_8, gbc_panel_8);
		
		uniprotCheckBox = new JCheckBox("UniProt PTMs");
		panel_8.add(uniprotCheckBox);
		
		chckbxSelectAll = new JCheckBox("Select All");
		panel_8.add(chckbxSelectAll);
		
		btnSubmit = new JButton("Submit");
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.insets = new Insets(0, 0, 5, 0);
		gbc_btnSubmit.gridx = 0;
		gbc_btnSubmit.gridy = 8;
		panel.add(btnSubmit, gbc_btnSubmit);
		btnSubmit.setEnabled(false);
		
		JLabel lblHtpHigh = new JLabel("HTP = High Throughput : LTP = Low Throughput");
		GridBagConstraints gbc_lblHtpHigh = new GridBagConstraints();
		gbc_lblHtpHigh.gridx = 0;
		gbc_lblHtpHigh.gridy = 9;
		panel.add(lblHtpHigh, gbc_lblHtpHigh);
		
		htpPhosphoTextField.setText("5");
		ltpPhosphoTextField.setText("1");
		htpAcetylTextField.setText("1");
		ltpAcetylTextField.setText("1");
		htpMethylTextField.setText("1");
		ltpMethylTextField.setText("1");
		htpOgalnacTextField.setText("1");
		ltpOgalnacTextField.setText("1");
		htpOglcnacTextField.setText("1");
		ltpOglcnacTextField.setText("1");
		htpSumoylTextField.setText("1");
		ltpSumoylTextField.setText("1");
		htpUbiquitinTextField.setText("1");
		ltpUbiquitinTextField.setText("1");
		
		setVisible(true);
	}

	@Override
	public JTextField htpPhosphoTextField() {
		// TODO Auto-generated method stub
		return htpPhosphoTextField;
	}

	@Override
	public JTextField ltpPhosphoTextField() {
		// TODO Auto-generated method stub
		return ltpPhosphoTextField;
	}

	@Override
	public JTextField htpAcetylTextField() {
		// TODO Auto-generated method stub
		return htpAcetylTextField;
	}

	@Override
	public JTextField ltpAcetylTextField() {
		// TODO Auto-generated method stub
		return ltpAcetylTextField;
	}

	@Override
	public JTextField htpMethylTextField() {
		// TODO Auto-generated method stub
		return htpMethylTextField;
	}

	@Override
	public JTextField ltpMethylTextField() {
		// TODO Auto-generated method stub
		return ltpMethylTextField;
	}

	@Override
	public JTextField htpOgalnacTextField() {
		// TODO Auto-generated method stub
		return htpOgalnacTextField;
	}

	@Override
	public JTextField ltpOgalnacTextField() {
		// TODO Auto-generated method stub
		return ltpOgalnacTextField;
	}

	@Override
	public JTextField htpOglcnacTextField() {
		// TODO Auto-generated method stub
		return htpOglcnacTextField;
	}

	@Override
	public JTextField ltpOglcnacTextField() {
		// TODO Auto-generated method stub
		return ltpOglcnacTextField;
	}

	@Override
	public JTextField htpSumoylTextField() {
		// TODO Auto-generated method stub
		return htpSumoylTextField;
	}

	@Override
	public JTextField ltpSumoylTextField() {
		// TODO Auto-generated method stub
		return ltpSumoylTextField;
	}

	@Override
	public JTextField htpUbiquitinTextField() {
		// TODO Auto-generated method stub
		return htpUbiquitinTextField;
	}

	@Override
	public JTextField ltpUbiquitinTextField() {
		// TODO Auto-generated method stub
		return ltpUbiquitinTextField;
	}

	@Override
	public JCheckBox phosphoCheckBox() {
		// TODO Auto-generated method stub
		return phosphoCheckBox;
	}

	@Override
	public JCheckBox acetylCheckBox() {
		// TODO Auto-generated method stub
		return acetylCheckBox;
	}

	@Override
	public JCheckBox methylCheckBox() {
		// TODO Auto-generated method stub
		return methylCheckBox;
	}

	@Override
	public JCheckBox ogalnacCheckBox() {
		// TODO Auto-generated method stub
		return ogalnacCheckBox;
	}

	@Override
	public JCheckBox oglcnacCheckBox() {
		// TODO Auto-generated method stub
		return oglcnacCheckBox;
	}

	@Override
	public JCheckBox sumoylCheckBox() {
		// TODO Auto-generated method stub
		return sumoylCheckBox;
	}

	@Override
	public JCheckBox ubiquitinCheckBox() {
		// TODO Auto-generated method stub
		return ubiquitinCheckBox;
	}

	@Override
	public JCheckBox uniprotCheckBox() {
		// TODO Auto-generated method stub
		return uniprotCheckBox;
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
}
