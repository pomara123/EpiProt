package epiprot.services.views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import epiprot.Presenter;
import epiprot.services.phosphosite.PhosphoSiteAminoAcid;
import epiprot.services.phosphosite.PhosphoSitePTM;
import epiprot.services.phosphosite.PhosphoSiteService;
import epiprot.services.uniprot.UniProtAminoAcid;
import epiprot.services.uniprot.UniProtPTM;

public class PhosphoSitePresenter {
	
	Presenter presenter;
	
	String phosphoLine = "";
	String acetylLine = "";
	String methylLine = "";
	String sumoLine = "";
	String ubiquitLine = "";
	String ogalnacLine = "";
	String oglcnacLine = "";
	
	interface View {
		JTextField htpPhosphoTextField();
		JTextField ltpPhosphoTextField();
		JTextField htpAcetylTextField();
		JTextField ltpAcetylTextField();
		JTextField htpMethylTextField();
		JTextField ltpMethylTextField();
		JTextField htpOgalnacTextField();
		JTextField ltpOgalnacTextField();
		JTextField htpOglcnacTextField();
		JTextField ltpOglcnacTextField();
		JTextField htpSumoylTextField();
		JTextField ltpSumoylTextField();
		JTextField htpUbiquitinTextField();
		JTextField ltpUbiquitinTextField();
		JCheckBox phosphoCheckBox();
		JCheckBox acetylCheckBox();
		JCheckBox methylCheckBox();
		JCheckBox ogalnacCheckBox();
		JCheckBox oglcnacCheckBox();
		JCheckBox sumoylCheckBox();
		JCheckBox ubiquitinCheckBox();
		JCheckBox uniprotCheckBox();
		JCheckBox chckbxSelectAll();
		JButton btnSubmit();
	}
	
	interface SummaryView {
		JTextPane textPane();
		void appendString(String str);
	}

	public PhosphoSitePresenter(Presenter presenter) {
		// TODO Auto-generated constructor stub
		this.presenter = presenter;
		bindHandlers();
		
	}
	
	View view = new PhosphoSiteView();
	SummaryView summaryView = new PhosphoSiteSummaryView();
	
	public void bindHandlers() {
		view.btnSubmit().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(presenter.proteinAcc);
				System.out.println(presenter.protein.getSequence());
				System.out.println(view.phosphoCheckBox().isSelected());
				
				String text = String.format("%1$-20s %2$10s %3$10s", "PTM", "HTP","LTP");
				summaryView.appendString(text+"\n");
				
				
				for(int i = 0; i < presenter.protein.getSequence().length(); i++) {
					addDashToAllLines();
				}
				PhosphoSiteService phosphoSiteService = new PhosphoSiteService(presenter.protein.getSequence(),presenter.proteinAcc,
						view.phosphoCheckBox().isSelected(),view.acetylCheckBox().isSelected(),view.methylCheckBox().isSelected(),
						view.ogalnacCheckBox().isSelected(),view.oglcnacCheckBox().isSelected(),view.sumoylCheckBox().isSelected(),
						view.ubiquitinCheckBox().isSelected());
				
				phosphoSiteService.run();
				ArrayList<PhosphoSiteAminoAcid> aaList = phosphoSiteService.getAminoAcids();
				
				for(int i = 0; i < aaList.size(); i++) {
					PhosphoSiteAminoAcid aa = aaList.get(i);
					if(aa.isPTM() && aa.getPhosphoSitePTMs().size()>0) {
						for (PhosphoSitePTM ptm: aa.getPhosphoSitePTMs()) {
							String ptmSummary = aa.getResidue()+aa.getPosition()+" ";
							if(ptm.getType().equals(PhosphoSiteService.PHOSPHOYLATED)) {
								ptmSummary = ptmSummary + "-p";
								if (ptm.getHtpHits()>=Integer.parseInt(view.htpPhosphoTextField().getText()) || ptm.getLtpHits()>=Integer.parseInt(view.ltpPhosphoTextField().getText())){
									StringBuilder sb = new StringBuilder(phosphoLine);
									phosphoLine = sb.replace(i, i+1, "p").toString();
								}
							}
							else if(ptm.getType().equals(PhosphoSiteService.ACETYLATED)) {
								ptmSummary = ptmSummary + "-a";
								if (ptm.getHtpHits()>=Integer.parseInt(view.htpAcetylTextField().getText()) || ptm.getLtpHits()>=Integer.parseInt(view.ltpAcetylTextField().getText())) {
									StringBuilder sb = new StringBuilder(acetylLine);
									acetylLine = sb.replace(i, i+1, "a").toString();
								}
							}
							else if(ptm.getType().equals(PhosphoSiteService.METHYLATED)) {
								ptmSummary = ptmSummary + "-m";
								if (ptm.getHtpHits()>=Integer.parseInt(view.htpMethylTextField().getText()) || ptm.getLtpHits()>=Integer.parseInt(view.ltpMethylTextField().getText())) {
									StringBuilder sb = new StringBuilder(methylLine);
									methylLine = sb.replace(i, i+1, "m").toString();
								}
							}
							else if(ptm.getType().equals(PhosphoSiteService.SUMOYLATED)) {
								ptmSummary = ptmSummary + "-s";
								if(ptm.getHtpHits()>=Integer.parseInt(view.htpSumoylTextField().getText()) || ptm.getLtpHits()>=Integer.parseInt(view.ltpSumoylTextField().getText())) {
									StringBuilder sb = new StringBuilder(sumoLine);
									sumoLine = sb.replace(i, i+1, "s").toString();
								}
							}
							else if(ptm.getType().equals(PhosphoSiteService.UBIQUITINATED)) {
								ptmSummary = ptmSummary + "-u";
								if  (ptm.getHtpHits()>=Integer.parseInt(view.htpUbiquitinTextField().getText()) || ptm.getLtpHits()>=Integer.parseInt(view.ltpUbiquitinTextField().getText())) {
									StringBuilder sb = new StringBuilder(ubiquitLine);
									ubiquitLine = sb.replace(i, i+1, "u").toString();
								}
							}
							else if(ptm.getType().equals(PhosphoSiteService.OGALNAC)) {
								ptmSummary = ptmSummary + "-O gal";
								if(ptm.getHtpHits()>=Integer.parseInt(view.htpOgalnacTextField().getText()) || ptm.getLtpHits()>=Integer.parseInt(view.ltpOglcnacTextField().getText())) {
									StringBuilder sb = new StringBuilder(ogalnacLine);
									ogalnacLine = sb.replace(i, i+1, "o").toString();
								}
							}
							else if(ptm.getType().equals(PhosphoSiteService.OGLCNAC)) {
								ptmSummary = ptmSummary + "-O glc";
								if(ptm.getHtpHits()>=Integer.parseInt(view.htpOglcnacTextField().getText()) || ptm.getLtpHits()>=Integer.parseInt(view.ltpOglcnacTextField().getText())) {
									StringBuilder sb = new StringBuilder(oglcnacLine);
									oglcnacLine = sb.replace(i, i+1, String.valueOf('\u00F6')).toString();
								}
							}
							
							//ptmSummary = ptmSummary + ": HTP:" + ptm.getHtpHits() + " LTP:" + ptm.getLtpHits()+"\n";
							text = String.format("%1$-20s %2$10s %3$10s", ptmSummary+":", ptm.getHtpHits(),ptm.getLtpHits());
							summaryView.appendString(text+"\n");
						}							
					}
				}
				
				
				if (phosphoLine.contains("p")) {
					presenter.insertLineAboveTarget("Phosphorylations", getInsertLine(phosphoLine));
				}
				if (acetylLine.contains("a")) {
					presenter.insertLineAboveTarget("Acetylations", getInsertLine(acetylLine));
				}
				if (methylLine.contains("m")) {
					presenter.insertLineAboveTarget("Methylations", getInsertLine(methylLine));
				}
				if (sumoLine.contains("s")) {
					presenter.insertLineAboveTarget("Sumoylations", getInsertLine(sumoLine));
				}
				if (ubiquitLine.contains("u")) {
					presenter.insertLineAboveTarget("Ubiquitinations", getInsertLine(ubiquitLine));
				}
				if (ogalnacLine.contains("o")) {
					presenter.insertLineAboveTarget("O-GalNAc", getInsertLine(ogalnacLine));
				}
				if (oglcnacLine.contains("\u00F6")) {
					presenter.insertLineAboveTarget("O-GlcNAc", getInsertLine(oglcnacLine));
				}
				
				//uniprot ptms
				if(view.uniprotCheckBox().isSelected()) {
					summaryView.appendString("\nUniProt PTMs:\n");
					ArrayList<UniProtPTM> ptmList = presenter.protein.getPTMs();
					ArrayList<UniProtAminoAcid> uniAAlist = getAminoAcids();
					System.out.println("&&&"+ptmList.size());
					LinkedHashMap<Integer,Integer> map = new LinkedHashMap<Integer,Integer>();
					LinkedHashMap<Integer,UniProtAminoAcid> ptmMap = new LinkedHashMap<Integer,UniProtAminoAcid>();
					int disulfideNumber = 0;
					for(UniProtPTM ptm: ptmList) {
						if (ptm.getPosition() != 0) {
							uniAAlist.get(ptm.getPosition()).addPTM(ptm);
						}
						else {
							disulfideNumber++;
							ptm.setDisulfideNumber(disulfideNumber);
							uniAAlist.get(ptm.getBeginPosition()).addPTM(ptm);
							uniAAlist.get(ptm.getEndPosition()).addPTM(ptm);
						}
					}
					
					//make enough lines for ptms
					int numberOfLines = 0;
					for(int i = 0; i < uniAAlist.size(); i++) {
						if(uniAAlist.get(i).getUniProtPTMs().size()>numberOfLines) {
							numberOfLines = uniAAlist.get(i).getUniProtPTMs().size();
						}
					}
					
					System.out.println("numberOfLines"+numberOfLines);
					
					String line = getUniProtLine();
					ArrayList<String> lines = new ArrayList<String>();
					for(int i = 0; i < numberOfLines; i++) {
						String s = new String(line);
						lines.add(s);
					}

					
					for(int i = 0; i < uniAAlist.size(); i++) {
						UniProtAminoAcid aa = uniAAlist.get(i);
						ArrayList<UniProtPTM> aaPTMs = aa.getUniProtPTMs();
						System.out.println("@@@"+aaPTMs.size());
						for(int j = 0;  j < aaPTMs.size(); j++) {
							UniProtPTM ptm = aaPTMs.get(j);
							String ptmSummary = "";

							String insertLine = lines.get(j);
							StringBuilder sb = new StringBuilder(insertLine);
							
							//replace amino acid
							if (ptm.getUniprotType().toLowerCase().equals("modified residue")) {
								ptmSummary = presenter.protein.getSequence().charAt(ptm.getPosition()-1)+""+ptm.getPosition();
								if(ptm.getPtmType().equals(UniProtPTM.ACETYLATED)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('a')).toString();
									ptmSummary = ptmSummary + "-a";
								}
								else if(ptm.getPtmType().equals(UniProtPTM.AMIDATION)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('d')).toString();
									ptmSummary = ptmSummary + "-d";
								}
								else if(ptm.getPtmType().equals(UniProtPTM.FLAVIN)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('f')).toString();
									ptmSummary = ptmSummary + "-f";
								}
								else if(ptm.getPtmType().equals(UniProtPTM.HYDROXYLATION)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('h')).toString();
									ptmSummary = ptmSummary + "-h";
								}
								else if(ptm.getPtmType().equals(UniProtPTM.ISOMERIZATION)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('i')).toString();
									ptmSummary = ptmSummary + "-i";
								}
								else if(ptm.getPtmType().equals(UniProtPTM.METHYLATED)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('m')).toString();
									ptmSummary = ptmSummary + "-m";
								}
								else if(ptm.getPtmType().equals(UniProtPTM.PHOSPHOYLATED)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('p')).toString();
									ptmSummary = ptmSummary + "-p";
								}
								else if(ptm.getPtmType().equals(UniProtPTM.PYRROLIDONE)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('z')).toString();
									ptmSummary = ptmSummary + "-z";
								}
								else if(ptm.getPtmType().equals(UniProtPTM.SULFATION)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('$')).toString();
									ptmSummary = ptmSummary + "-$";
								}
								else if(ptm.getPtmType().equals(UniProtPTM.SUMOYLATED)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('s')).toString();
									ptmSummary = ptmSummary + "-s";
								}
								else if(ptm.getPtmType().equals(UniProtPTM.UBIQUITINATED)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('u')).toString();
									ptmSummary = ptmSummary + "-u";
								}
								ptmSummary = ptmSummary +": "+ ptm.getDescription();
								summaryView.appendString(ptmSummary+"\n");
							}
							else if(ptm.getUniprotType().toLowerCase().equals("glycosylation site")) {
								ptmSummary = presenter.protein.getSequence().charAt(ptm.getPosition()-1)+""+ptm.getPosition();
								System.out.println("!@#" + ptm.getPtmType());
								if(ptm.getPtmType().equals(UniProtPTM.GLYCOSYLATED)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('g')).toString();
									ptmSummary = ptmSummary + "-g";
								}
								else if(ptm.getLinkage().equals(UniProtPTM.OLINKED) && ptm.getPtmType().equals(UniProtPTM.GALNAC)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('o')).toString();
									ptmSummary = ptmSummary + "-O gal";
								}
								else if(ptm.getLinkage().equals(UniProtPTM.OLINKED) && ptm.getPtmType().equals(UniProtPTM.GLCNAC)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('\u00F6')).toString();
									ptmSummary = ptmSummary + "-O glc";
								}
								else if(ptm.getLinkage().equals(UniProtPTM.NLINKED) && ptm.getPtmType().equals(UniProtPTM.GALNAC)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('n')).toString();
									ptmSummary = ptmSummary + "-N gal";
								}
								else if(ptm.getLinkage().equals(UniProtPTM.NLINKED) && ptm.getPtmType().equals(UniProtPTM.GLCNAC)) {
									insertLine = sb.replace(ptm.getPosition()-1, ptm.getPosition(), String.valueOf('\u00F1')).toString();
									ptmSummary = ptmSummary + "-N glc";
								}
								ptmSummary = ptmSummary +": "+ ptm.getDescription();
								summaryView.appendString(ptmSummary+"\n");
							}
							else if(ptm.getUniprotType().toLowerCase().equals("disulfide bond") && insertLine.charAt(ptm.getBeginPosition()-1)=='-') {
								insertLine = sb.replace(ptm.getBeginPosition()-1, ptm.getBeginPosition(), String.valueOf(ptm.getDisulfideNumber())).toString();
								insertLine = sb.replace(ptm.getEndPosition()-1, ptm.getEndPosition(), String.valueOf(ptm.getDisulfideNumber())).toString();
								ptmSummary = presenter.protein.getSequence().charAt(ptm.getBeginPosition()-1)+""+ptm.getBeginPosition() + "---" + presenter.protein.getSequence().charAt(ptm.getEndPosition()-1) + ptm.getEndPosition()+": "+ "disulfide bond";
								summaryView.appendString(ptmSummary+"\n");
							}
							lines.set(j, insertLine);
						}
					}
					for(int i = lines.size()-1; i >= 0 ; i--) {
						int lineNumber = i +1;
						presenter.insertLineAboveTarget("UniProt PTM Line "+lineNumber, getInsertLine(lines.get(i)));
					}
				}
			}
		});
		
		view.htpPhosphoTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.htpPhosphoTextField()));
		view.ltpPhosphoTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.ltpPhosphoTextField()));
		
		view.htpAcetylTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.htpAcetylTextField()));
		view.ltpAcetylTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.ltpAcetylTextField()));
		
		view.htpMethylTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.htpMethylTextField()));
		view.ltpMethylTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.ltpMethylTextField()));
		
		view.htpOgalnacTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.htpOgalnacTextField()));
		view.ltpOgalnacTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.ltpOgalnacTextField()));
		
		view.htpOglcnacTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.htpOglcnacTextField()));
		view.ltpOglcnacTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.ltpOglcnacTextField()));
		
		view.htpSumoylTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.htpSumoylTextField()));
		view.ltpSumoylTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.ltpSumoylTextField()));
		
		view.htpUbiquitinTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.htpUbiquitinTextField()));
		view.ltpUbiquitinTextField().getDocument().addDocumentListener(new SubmitButtonListener(view.ltpUbiquitinTextField()));
		
		view.acetylCheckBox().addActionListener(new CheckBoxListener(view));
		view.phosphoCheckBox().addActionListener(new CheckBoxListener(view));
		view.methylCheckBox().addActionListener(new CheckBoxListener(view));
		view.ogalnacCheckBox().addActionListener(new CheckBoxListener(view));
		view.oglcnacCheckBox().addActionListener(new CheckBoxListener(view));
		view.sumoylCheckBox().addActionListener(new CheckBoxListener(view));
		view.ubiquitinCheckBox().addActionListener(new CheckBoxListener(view));
		view.uniprotCheckBox().addActionListener(new CheckBoxListener(view));
		
		view.chckbxSelectAll().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (view.chckbxSelectAll().isSelected()) {
					view.phosphoCheckBox().setSelected(true);
					view.acetylCheckBox().setSelected(true);
					view.methylCheckBox().setSelected(true);
					view.ogalnacCheckBox().setSelected(true);
					view.oglcnacCheckBox().setSelected(true);
					view.sumoylCheckBox().setSelected(true);
					view.ubiquitinCheckBox().setSelected(true);
					view.uniprotCheckBox().setSelected(true);
					view.btnSubmit().setEnabled(true);
				}
				else {
					view.phosphoCheckBox().setSelected(false);
					view.acetylCheckBox().setSelected(false);
					view.methylCheckBox().setSelected(false);
					view.ogalnacCheckBox().setSelected(false);
					view.oglcnacCheckBox().setSelected(false);
					view.sumoylCheckBox().setSelected(false);
					view.ubiquitinCheckBox().setSelected(false);
					view.uniprotCheckBox().setSelected(false);
					view.btnSubmit().setEnabled(false);
				}
			}
			
		});
	}
	
	private String getUniProtLine() {
		String line = "";
		for(int i = 0; i < presenter.protein.getSequence().length(); i++) {
			line = line + "-";
		}
		return line;
	}
	
	private void addDashToAllLines() {
		oglcnacLine = oglcnacLine + "-";
		phosphoLine = phosphoLine + "-";
		acetylLine = acetylLine + "-";
		methylLine = methylLine + "-";
		sumoLine = sumoLine + "-";
		ubiquitLine = ubiquitLine + "-";
		ogalnacLine = ogalnacLine + "-";
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
	
	public void checkTextField(String text) {
		if(isInteger(text) && Integer.parseInt(text) >= 0) {
			view.btnSubmit().setEnabled(true);
		}
		else {
			view.btnSubmit().setEnabled(false);
		}
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
	
	public ArrayList<UniProtAminoAcid> getAminoAcids() {
		ArrayList<UniProtAminoAcid> aaList = new ArrayList<UniProtAminoAcid>();
		for (int i = 0; i< presenter.protein.getSequence().length(); i++) {
			aaList.add(new UniProtAminoAcid());			
		}
		return aaList;
	}
	
	public class SubmitButtonListener implements DocumentListener {
		
		JTextField textField;
		
		public SubmitButtonListener(JTextField textField){
			this.textField = textField;
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			checkTextField(textField.getText());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			checkTextField(textField.getText());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			checkTextField(textField.getText());
		}
		
	}
	
	public class CheckBoxListener implements ActionListener {
		
		View phosphoSiteView;
		
		public CheckBoxListener(View phosphoSiteView) {
			this.phosphoSiteView = phosphoSiteView;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (phosphoSiteView.acetylCheckBox().isSelected() || phosphoSiteView.methylCheckBox().isSelected() || phosphoSiteView.phosphoCheckBox().isSelected() ||
					phosphoSiteView.ogalnacCheckBox().isSelected() || phosphoSiteView.oglcnacCheckBox().isSelected() ||
					phosphoSiteView.sumoylCheckBox().isSelected() || phosphoSiteView.ubiquitinCheckBox().isSelected() || phosphoSiteView.uniprotCheckBox().isSelected()) {
				view.btnSubmit().setEnabled(true);
			}
			else {
				view.btnSubmit().setEnabled(false);
			}
		}
		
	}

}
