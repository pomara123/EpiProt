package epiprot.services.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import epiprot.Presenter;
import epiprot.services.epitopePrediction.ABCPredPeptide;
import epiprot.services.epitopePrediction.ABCPredService;

public class ABCPredPresenter {
	
	private Presenter presenter;
	
	public interface View {
		JTextField textField();
		JComboBox comboBox();
		JCheckBox chckbxOverlappingFilter();
		JButton btnSubmit();
	}
	
	public interface SummaryView {
		JTextPane textPane();
		void appendString(String str);
	}

	View view = new ABCPredView();
	SummaryView summaryView = new ABCPredSummaryView();
	
	public ABCPredPresenter(Presenter presenter) {
		// TODO Auto-generated constructor stub
		this.presenter = presenter;
		bindHandlers();
	}
	
	void bindHandlers() {
		view.textField().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				checkTextField();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				checkTextField();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				checkTextField();
			}
			public void checkTextField() {
				String text = view.textField().getText();
				if(isDouble(text) && Double.parseDouble(text) < 1 && Double.parseDouble(text) >= 0.1) {
					view.btnSubmit().setEnabled(true);
				}
				else {
					view.btnSubmit().setEnabled(false);
				}
			}
		});
		
		view.btnSubmit().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ABCPredService abcPredService = new ABCPredService(presenter.protein.getSequence(),view.comboBox().getSelectedItem().toString(),
						view.textField().getText(),view.chckbxOverlappingFilter().isSelected());
				
				abcPredService.run();
				ArrayList<ABCPredPeptide> peptideList = abcPredService.getPeptides();
				String line = "";
				for(int i = 0; i < presenter.protein.getSequence().length(); i++) {
					line = line + " ";
				}
				for(ABCPredPeptide p: peptideList) {
					String s = String.format("%1$-10s %2$-25s %3$-5d %4$.2f10\n", p.getRank(), p.getSequence(), p.getStart(), p.getScore());
					System.out.println(s);
					summaryView.appendString(s);
					for(int i = p.getStart()-1; i < (p.getStart()+p.getSequence().length())-1; i++) {
						line = line.substring(0,i)+"+"+line.substring(i+1);
					}
				}
				
				presenter.insertLineAboveTarget("ABCPred", getInsertLine(line));
				
			}
			
		});
		
	}
	
	public String getInsertLine(String inputLine) {
		String mainLine = presenter.getMainLine();
		for(int i = 0; i < mainLine.length(); i++) {
			if(mainLine.charAt(i) == '-') {
				inputLine = new StringBuilder(inputLine).insert(i, " ").toString();
			}
		}
		return inputLine;
	}
	
	private boolean isDouble(String s) {
		try { 
			Double.parseDouble(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}

}
