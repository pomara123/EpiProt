package epiprot.services.views;

import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class MSAView extends JFrame implements MSAPresenter.View {
	
	JRadioButton muscleWSRadioButton = new JRadioButton("MUSCLE");
	JRadioButton clustalOWSRadioButton = new JRadioButton("Clustal Omega");
	JRadioButton clustalWSRadioButton = new JRadioButton("Clustal W");
	JRadioButton tCoffeeWSRadioButton = new JRadioButton("T-Coffee");
	JRadioButton mafftWSRadioButton = new JRadioButton("Mafft");
	JRadioButton probconsWSRadioButton = new JRadioButton("ProbCons");
	JRadioButton msaProbsWSRadioButton = new JRadioButton("MSAProbs");
	JRadioButton glProbsWSRadioButton = new JRadioButton("GLProbs");
	
	JButton btnSubmit = new JButton("Submit");
	
	ButtonGroup buttonGroup = new ButtonGroup();

	public MSAView() {
		setSize(new Dimension(300, 200));
		// TODO Auto-generated constructor stub
		setTitle("Multiple Sequence Alignment");
		getContentPane().setPreferredSize(new Dimension(400, 400));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(6, 2, 0, 0));		
		panel.add(muscleWSRadioButton);	
		muscleWSRadioButton.setSelected(true);
		panel.add(clustalOWSRadioButton);		
		panel.add(clustalWSRadioButton);		
		panel.add(tCoffeeWSRadioButton);		
		panel.add(mafftWSRadioButton);		
		panel.add(probconsWSRadioButton);		
		panel.add(msaProbsWSRadioButton);		
		panel.add(glProbsWSRadioButton);
		
		buttonGroup.add(muscleWSRadioButton);
		buttonGroup.add(glProbsWSRadioButton);;
		buttonGroup.add(msaProbsWSRadioButton);
		buttonGroup.add(probconsWSRadioButton);
		buttonGroup.add(mafftWSRadioButton);
		buttonGroup.add(tCoffeeWSRadioButton);
		buttonGroup.add(clustalWSRadioButton);
		buttonGroup.add(clustalOWSRadioButton);
			
		panel.add(btnSubmit);
		setVisible(true);
	}

	@Override
	public JRadioButton muscleWSRadioButton() {
		// TODO Auto-generated method stub
		return muscleWSRadioButton;
	}

	@Override
	public JRadioButton clustalOWSRadioButton() {
		// TODO Auto-generated method stub
		return clustalOWSRadioButton;
	}

	@Override
	public JRadioButton clustalWSRadioButton() {
		// TODO Auto-generated method stub
		return clustalWSRadioButton;
	}

	@Override
	public JRadioButton tCoffeeWSRadioButton() {
		// TODO Auto-generated method stub
		return tCoffeeWSRadioButton;
	}

	@Override
	public JRadioButton mafftWSRadioButton() {
		// TODO Auto-generated method stub
		return mafftWSRadioButton;
	}

	@Override
	public JRadioButton probconsWSRadioButton() {
		// TODO Auto-generated method stub
		return probconsWSRadioButton;
	}

	@Override
	public JRadioButton msaProbsWSRadioButton() {
		// TODO Auto-generated method stub
		return msaProbsWSRadioButton;
	}

	@Override
	public JRadioButton glProbsWSRadioButton() {
		// TODO Auto-generated method stub
		return glProbsWSRadioButton;
	}

	@Override
	public JButton btnSubmit() {
		// TODO Auto-generated method stub
		return btnSubmit;
	}

	@Override
	public ButtonGroup buttonGroup() {
		// TODO Auto-generated method stub
		return buttonGroup;
	}

}
