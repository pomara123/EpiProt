package epiprot.services.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import epiprot.Presenter;
import epiprot.services.epitopePrediction.ChouPredictionService;
import epiprot.services.epitopePrediction.EminiPredictionService;
import epiprot.services.epitopePrediction.IedbEpitopePredictionAminoAcid;
import epiprot.services.epitopePrediction.KarplusPredictionService;
import epiprot.services.epitopePrediction.KolaskarPredictionService;
import epiprot.services.epitopePrediction.ParkerPredictionService;

public class IEDBPredPresenter {
	
	Presenter presenter;
	int proteinSeqLength;
	
	interface View {
		JTextField windowSizeTextField();
		//JCheckBox bepipredCheckBox();
		JCheckBox chouCheckBox();
		JCheckBox eminiNewCheckBox();
		JCheckBox karplusCheckBox();
		JCheckBox kolaskarCheckBox();
		JCheckBox parkerCheckBox();
		JButton btnSubmit();

	}

	public IEDBPredPresenter(Presenter presenter) {
		// TODO Auto-generated constructor stub
		this.presenter = presenter;
		this.proteinSeqLength = presenter.protein.getSequence().length();
		bindHandlers();
	}
	
	View view = new IEDBPredView();
	
	void bindHandlers() {
		view.btnSubmit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub/
				/*
				if (view.bepipredCheckBox().isSelected()) {
					BepipredPredictionService ps = new BepipredPredictionService(presenter.protein.getSequence());
					ps.run();
					ArrayList<IedbEpitopePredictionAminoAcid> aminoAcids = ps.getAminoAcids();
					insertPredictionLine("Bepipred Epitope Prediction",presenter.getMainLine(), aminoAcids);
				}*/
				if (view.chouCheckBox().isSelected()) {
					ChouPredictionService ps = new ChouPredictionService(presenter.protein.getSequence());
					ps.run();
					ArrayList<IedbEpitopePredictionAminoAcid> aminoAcids = ps.getAminoAcids();
					insertPredictionLine("Chou Epitope Prediction",presenter.getMainLine(), aminoAcids);
				}
				if (view.eminiNewCheckBox().isSelected()) {
					EminiPredictionService ps = new EminiPredictionService(presenter.protein.getSequence());
					ps.run();
					ArrayList<IedbEpitopePredictionAminoAcid> aminoAcids = ps.getAminoAcids();
					insertPredictionLine("Emini Epitope Prediction",presenter.getMainLine(), aminoAcids);
				}
				if (view.karplusCheckBox().isSelected()) {
					KarplusPredictionService ps = new KarplusPredictionService(presenter.protein.getSequence());
					ps.run();
					ArrayList<IedbEpitopePredictionAminoAcid> aminoAcids = ps.getAminoAcids();
					insertPredictionLine("Karplus Epitope Prediction",presenter.getMainLine(), aminoAcids);
				}
				if (view.kolaskarCheckBox().isSelected()) {
					KolaskarPredictionService ps = new KolaskarPredictionService(presenter.protein.getSequence());
					ps.run();
					ArrayList<IedbEpitopePredictionAminoAcid> aminoAcids = ps.getAminoAcids();
					insertPredictionLine("Kolaskar Epitope Prediction",presenter.getMainLine(), aminoAcids);
				}
				if (view.parkerCheckBox().isSelected()) {
					ParkerPredictionService ps = new ParkerPredictionService(presenter.protein.getSequence());
					ps.run();
					ArrayList<IedbEpitopePredictionAminoAcid> aminoAcids = ps.getAminoAcids();
					insertPredictionLine("Parker Epitope Prediction",presenter.getMainLine(), aminoAcids);
				}
			}
			
			public void insertPredictionLine(String header, String mainLine, ArrayList<IedbEpitopePredictionAminoAcid> aminoAcids) {
				String aaListSequence ="";
				for (int i = 0; i < aminoAcids.size(); i++) {
					aaListSequence = aaListSequence + aminoAcids.get(i).getResidue();
				}
				String insertLine = "";
				int mainIndex = 0;
				for(int i = 0; i < aminoAcids.size(); i++) {
					if(Character.isLetter(mainLine.charAt(mainIndex))) {
						int score = aminoAcids.get(i).getRelativeScore();
						insertLine = insertLine + score;
					}
					else {
						insertLine = insertLine + "-";
						i--;
					}
					mainIndex++;
				}
				presenter.insertLineAboveTarget(header, insertLine);
			}
		});
		
		view.windowSizeTextField().getDocument().addDocumentListener(new DocumentListener() {
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
				String text = view.windowSizeTextField().getText();
				if(isInteger(text) && Integer.parseInt(text) < proteinSeqLength && Integer.parseInt(text) > 0) {
					view.btnSubmit().setEnabled(true);
				}
				else {
					view.btnSubmit().setEnabled(false);
				}
			}
		});
	}
	
	private boolean isInteger(String s) {
		try { 
	        Integer.parseInt(s); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    // only got here if we didn't return false
	    return true;
	}
}
