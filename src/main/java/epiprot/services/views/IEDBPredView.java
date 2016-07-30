package epiprot.services.views;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;

public class IEDBPredView extends JFrame implements IEDBPredPresenter.View {
	
	JTextField windowSizeTextField = new JTextField("6");
	//JCheckBox bepipredCheckBox = new JCheckBox("Bepipred Linear Epitope Prediction");
	JCheckBox chouCheckBox = new JCheckBox("Chou & Fasman Beta-Turn Prediction");
	JCheckBox eminiNewCheckBox = new JCheckBox("Emini Surface Accessibility Prediction");
	JCheckBox karplusCheckBox = new JCheckBox("Karplus & Schulz Flexibility Prediction");
	JCheckBox kolaskarCheckBox = new JCheckBox("Kolaskar & Tongaonkar Antigenicity");
	JCheckBox parkerCheckBox = new JCheckBox("Parker Hydrophilicity Prediction");
	JButton btnSubmit = new JButton("Submit");
	private final JLabel lblNewLabel = new JLabel("Enter a number between 0 and the length of the protein");

	public IEDBPredView() {
		setSize(new Dimension(700, 300));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new GridBagLayout());
		/*
		bepipredCheckBox.setHorizontalAlignment(SwingConstants.TRAILING);
		GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
		gbc_chckbxNewCheckBox.insets = new Insets(5, 5, 5, 5);
		gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox.weightx = 1.0;
		gbc_chckbxNewCheckBox.weighty = 1.0;
		gbc_chckbxNewCheckBox.gridx = 0;
		gbc_chckbxNewCheckBox.gridy = 0;
		panel.add(bepipredCheckBox, gbc_chckbxNewCheckBox);
		*/
		GridBagConstraints gbc_chckbxNewCheckBox_1 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_1.insets = new Insets(5, 5, 5, 0);
		gbc_chckbxNewCheckBox_1.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_1.weightx = 1.0;
		gbc_chckbxNewCheckBox_1.weighty = 1.0;
		gbc_chckbxNewCheckBox_1.gridx = 1;
		gbc_chckbxNewCheckBox_1.gridy = 0;
		panel.add(chouCheckBox, gbc_chckbxNewCheckBox_1);
		
		GridBagConstraints gbc_chckbxNewCheckBox_2 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_2.insets = new Insets(5, 5, 5, 5);
		gbc_chckbxNewCheckBox_2.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_2.weightx = 1.0;
		gbc_chckbxNewCheckBox_2.weighty = 1.0;
		gbc_chckbxNewCheckBox_2.gridx = 0;
		gbc_chckbxNewCheckBox_2.gridy = 1;
		panel.add(eminiNewCheckBox, gbc_chckbxNewCheckBox_2);
		
		GridBagConstraints gbc_chckbxNewCheckBox_3 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_3.insets = new Insets(5, 5, 5, 0);
		gbc_chckbxNewCheckBox_3.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_2.weightx = 1.0;
		gbc_chckbxNewCheckBox_2.weighty = 1.0;
		gbc_chckbxNewCheckBox_3.gridx = 1;
		gbc_chckbxNewCheckBox_3.gridy = 1;
		panel.add(karplusCheckBox, gbc_chckbxNewCheckBox_3);
		
		GridBagConstraints gbc_chckbxNewCheckBox_4 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_4.insets = new Insets(5, 5, 5, 5);
		gbc_chckbxNewCheckBox_4.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_4.weightx = 1.0;
		gbc_chckbxNewCheckBox_4.weighty = 1.0;
		gbc_chckbxNewCheckBox_4.gridx = 0;
		gbc_chckbxNewCheckBox_4.gridy = 2;
		panel.add(kolaskarCheckBox, gbc_chckbxNewCheckBox_4);
		
		GridBagConstraints gbc_chckbxNewCheckBox_5 = new GridBagConstraints();
		gbc_chckbxNewCheckBox_5.insets = new Insets(5, 5, 5, 0);
		gbc_chckbxNewCheckBox_5.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBox_5.weightx = 1.0;
		gbc_chckbxNewCheckBox_5.weighty = 1.0;
		gbc_chckbxNewCheckBox_5.gridx = 1;
		gbc_chckbxNewCheckBox_5.gridy = 2;
		panel.add(parkerCheckBox, gbc_chckbxNewCheckBox_5);
		
		JPanel windowPanel = new JPanel();
		windowPanel.setOpaque(true);
		//windowPanel.setBackground(Color.WHITE);
		windowPanel.setBorder(
            BorderFactory.createTitledBorder("Window Size"));
        GridBagConstraints gbc_windowSizePanel = new GridBagConstraints();
        gbc_windowSizePanel.fill = GridBagConstraints.BOTH;
        gbc_windowSizePanel.insets = new Insets(5, 5, 5, 5);
        gbc_windowSizePanel.anchor = GridBagConstraints.WEST;
        gbc_windowSizePanel.weightx = 1.0;
        gbc_windowSizePanel.weighty = 1.0;
        gbc_windowSizePanel.gridx = 0;
        gbc_windowSizePanel.gridy = 3;
        gbc_windowSizePanel.gridheight = 2;
		panel.add(windowPanel, gbc_windowSizePanel);
		windowPanel.setLayout(new BorderLayout(0, 0));
		
		windowPanel.add(windowSizeTextField, BorderLayout.CENTER);
		windowSizeTextField.setColumns(10);
		
		GridBagConstraints gbc_notificationLabel = new GridBagConstraints();
		gbc_notificationLabel.insets = new Insets(0, 0, 5, 0);
		gbc_notificationLabel.gridx = 1;
		gbc_notificationLabel.gridy = 4;
		panel.add(lblNewLabel, gbc_notificationLabel);
		
		GridBagConstraints gbc_btnSubmit = new GridBagConstraints();
		gbc_btnSubmit.gridx = 1;
		gbc_btnSubmit.gridy = 5;
		panel.add(btnSubmit, gbc_btnSubmit);
        
		
		// TODO Auto-generated constructor stub
		setVisible(true);
		
	}

	@Override
	public JTextField windowSizeTextField() {
		// TODO Auto-generated method stub
		return windowSizeTextField;
	}
/*
	@Override
	public JCheckBox bepipredCheckBox() {
		// TODO Auto-generated method stub
		return bepipredCheckBox;
	}
*/
	@Override
	public JCheckBox chouCheckBox() {
		// TODO Auto-generated method stub
		return chouCheckBox;
	}

	@Override
	public JCheckBox eminiNewCheckBox() {
		// TODO Auto-generated method stub
		return eminiNewCheckBox;
	}

	@Override
	public JCheckBox karplusCheckBox() {
		// TODO Auto-generated method stub
		return karplusCheckBox;
	}

	@Override
	public JCheckBox kolaskarCheckBox() {
		// TODO Auto-generated method stub
		return kolaskarCheckBox;
	}

	@Override
	public JCheckBox parkerCheckBox() {
		// TODO Auto-generated method stub
		return parkerCheckBox;
	}

	@Override
	public JButton btnSubmit() {
		// TODO Auto-generated method stub
		return btnSubmit;
	}

}
