package epiprot.services.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JCheckBox;

import epiprot.Presenter;
import epiprot.SelectProteinPresenter.View;
import epiprot.services.sifts.PdbEntry;
import epiprot.services.sifts.SiftAminoAcid;

public class SelectPDBPresenter {
	
	interface View {
		JCheckBox checkBox();
		String getCheckBoxText();
		void createUI();
	}

	View view;
	
	String proteinAcc;
	
	public SelectPDBPresenter(View view, String proteinAcc) {
		// TODO Auto-generated constructor stub
		this.view = view;
		this.proteinAcc = proteinAcc;
		bindHandler();
	}
		
	void bindHandler() {
		view.checkBox().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String text = view.getCheckBoxText();					
				String[] textArray = text.split("\\s+");
				String pdbId = textArray[0];
				String resolution = textArray[2];
				if (view.checkBox().isSelected()) {
					PdbEntry pdbEntry = new PdbEntry();
					pdbEntry.setProteinAcc(proteinAcc);
					pdbEntry.setPdbId(pdbId);
					pdbEntry.setResolution(Double.parseDouble(resolution));
					Presenter.pdbEntryList.add(pdbEntry);
				}
				else {
					ArrayList<PdbEntry> entryList = Presenter.pdbEntryList;
					for(PdbEntry pdbEntry: entryList) {
						if(pdbEntry.getPdbId().equals(pdbId)) {
							entryList.remove(pdbEntry);
						}
					}
				}
			}		
		});
	}
}
