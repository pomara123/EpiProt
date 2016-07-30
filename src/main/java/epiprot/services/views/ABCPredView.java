package epiprot.services.views;

import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Dimension;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class ABCPredView extends JFrame implements ABCPredPresenter.View  {
	
	private JTextField textField;
	private JComboBox comboBox;
	private JCheckBox chckbxOverlappingFilter;
	private JButton btnSubmit;

	public ABCPredView() {
		setTitle("ABCPred");
		setSize(new Dimension(400, 200));
		
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,},
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
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JLabel lblNewLabel = new JLabel("Window Size");
		getContentPane().add(lblNewLabel, "4, 4");
		
		String[] windowSizes = {"10","12","14","16","18","20"};
		comboBox = new JComboBox(windowSizes);
		comboBox.setSelectedItem("16");
		getContentPane().add(comboBox, "4, 6, fill, default");
		
		JLabel lblThreshold = new JLabel("Threshold [0.1 to 1.0]");
		getContentPane().add(lblThreshold, "4, 8");
		
		textField = new JTextField();
		getContentPane().add(textField, "4, 10, fill, default");
		textField.setText("0.85");
		textField.setColumns(10);
		
		chckbxOverlappingFilter = new JCheckBox("Overlapping filter? (check = on, uncheck = off)");
		chckbxOverlappingFilter.setSelected(true);
		getContentPane().add(chckbxOverlappingFilter, "4, 12");
		
		
		
		btnSubmit = new JButton("Submit");
		getContentPane().add(btnSubmit, "4, 16");
		// TODO Auto-generated constructor stub
		
		setVisible(true);
		
	}

	@Override
	public JTextField textField() {
		// TODO Auto-generated method stub
		return textField;
	}

	@Override
	public JComboBox comboBox() {
		// TODO Auto-generated method stub
		return comboBox;
	}

	@Override
	public JCheckBox chckbxOverlappingFilter() {
		// TODO Auto-generated method stub
		return chckbxOverlappingFilter;
	}

	@Override
	public JButton btnSubmit() {
		// TODO Auto-generated method stub
		return btnSubmit;
	}

}
