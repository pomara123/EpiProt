package epiprot;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;

public class SelectProteinView extends JPanel implements SelectProteinPresenter.View {
	
	private String proteinName;
	private JCheckBox checkBox;

	public SelectProteinView(String proteinName) {
		this.proteinName = proteinName;
		createUI();
	}
	
	@Override
	public void createUI() {
		setPreferredSize(new Dimension(200, 30));
		setMaximumSize(new Dimension(32767, 30));
		setMinimumSize(new Dimension(10, 30));
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 5, 5);
		setLayout(flowLayout);
		
		checkBox = new JCheckBox(proteinName);
		add(checkBox);
	}

	@Override
	public JCheckBox checkBox() {
		// TODO Auto-generated method stub
		return checkBox;
	}
	
	@Override
	public String getUniProtAcc() {
		return proteinName.split("\\s+")[0];
	}
	
}
