package epiprot.services.views;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import epiprot.Presenter;
import epiprot.Protein;
import epiprot.SelectProteinView;
import epiprot.services.sifts.PdbEntry;
import epiprot.services.sifts.SiftAminoAcid;
import epiprot.services.sifts.SiftService;

public class SiftsPresenter {
	
	interface View {
		JPanel panel();
		JButton btnSubmit();
		
		void insertEntry(SelectPDBView spv);
	}
	
	View view;
	Presenter presenter;

	public SiftsPresenter(Presenter presenter, SiftsView siftsView) {
		// TODO Auto-generated constructor stub
		this.presenter = presenter;
		this.view = siftsView;
		bindHandlers();
	}
	
	public void bindHandlers() {
		view.btnSubmit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.out.println("test submit siftspresenter");
				ArrayList<PdbEntry> pdbEntries = presenter.pdbEntryList;
				for(PdbEntry pdbEntry: pdbEntries) {
					SiftService siftService = new SiftService(pdbEntry.getPdbId(),pdbEntry.getProteinAcc(),pdbEntry.getResolution());
					siftService.run();
					ArrayList<SiftAminoAcid> aaList = siftService.getAminoAcids();
					for (int i = 0; i < aaList.size(); i++) {
						SiftAminoAcid aminoAcid = aaList.get(i);
						System.out.println(aminoAcid.toString());
					}
					String line = "";
					for(int i = 0; i < presenter.protein.getSequence().length(); i++) {
						line = line + "-";
					}
					
					for(SiftAminoAcid aa: aaList) {
						if(aa.getSecondaryStructure() != null) {
							line = line.substring(0,aa.getPosition()-1)+aa.getSecondaryStructure()+line.substring(aa.getPosition());
						}
					}
					
					String mainLine = presenter.getMainLine();
					for(int i = 0; i < mainLine.length(); i++) {
						if(mainLine.charAt(i) == '-') {
							line = new StringBuilder(line).insert(i, ' ').toString();
						}
					}
					
					System.out.println("PDB: "+pdbEntry.getPdbId() +"|"+ line);
					presenter.insertLineAboveTarget("PDB: "+pdbEntry.getPdbId(), line);
				}
			}		
		});
	}
}
