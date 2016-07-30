package epiprot.services.views;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class SiftsView extends JFrame implements SiftsPresenter.View {

	JPanel panel = new JPanel();
	JButton btnSubmit = new JButton("Submit");
	
	public SiftsView() {
		// TODO Auto-generated constructor stub
		setTitle("SIFTS Secondary Structure");
		setSize(new Dimension(500, 700));
		
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel submitPanel = new JPanel();
		submitPanel.setPreferredSize(new Dimension(10, 50));
		submitPanel.setMinimumSize(new Dimension(30, 30));
		submitPanel.setSize(new Dimension(0, 30));
		getContentPane().add(submitPanel, BorderLayout.SOUTH);
		
		submitPanel.add(btnSubmit);
		setVisible(true);
	}

	@Override
	public JPanel panel() {
		// TODO Auto-generated method stub
		return panel;
	}

	@Override
	public JButton btnSubmit() {
		// TODO Auto-generated method stub
		return btnSubmit;
	}

	@Override
	public void insertEntry(SelectPDBView spv) {
		// TODO Auto-generated method stub
		panel.add(spv);
	}
	
	public static void main(String[]args) {
		SiftsView siftsView = new SiftsView();
		SelectPDBView spv = new SelectPDBView("P31749","3OCB","X-ray",2.70,"A/B","144-480");
		
	}

}
