package epiprot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;

import org.jsoup.Jsoup;	

import epiprot.services.sifts.PdbEntry;
import epiprot.services.ssp.JPredAminoAcid;
import epiprot.services.ssp.JPredSequenceTooLongException;
import epiprot.services.ssp.JPredService;
import epiprot.services.ssp.PsiPredAminoAcid;
import epiprot.services.ssp.PsiPredService;
import epiprot.services.uniprot.PDBentry;
import epiprot.services.views.BlastPresenter;
import epiprot.services.views.IEDBPredPresenter;
import epiprot.services.views.MSAPresenter;
import epiprot.services.views.PhosphoSitePresenter;
import epiprot.services.views.SelectPDBPresenter;
import epiprot.services.views.SelectPDBView;
import epiprot.services.views.SiftsPresenter;
import epiprot.services.views.SiftsView;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastData;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.UniProtHit;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.DatabaseOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.DropOffOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.ExpectationOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.FilterOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.MatrixOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.ScoreCutoffOption;

public class Presenter {
	
	public static ArrayList<String> msaProteinList = new ArrayList<String>(); 
	public static ArrayList<PdbEntry> pdbEntryList = new ArrayList<PdbEntry>();
	public String proteinAcc;
	public String proteinHeader;
	public Protein protein;
		
	public interface View {
	    JEditorPane editorPane();        
	    JEditorPane headerPane();
	    JPanel proteinPanel();
	    JTextField searchField();
	    JButton searchButton();
		JButton msaButton();
	    JButton blastButton();
	    
	    JMenuItem blast();
	    JMenuItem msa();
	    JMenuItem iedbPrediction();
	    JMenuItem abcpred();
	    JMenuItem bcepred();
	    JMenuItem discotopePred();
	    JMenuItem elliproPred();
	    JMenuItem psipred();
	    JMenuItem jpred();
	    JMenuItem pdbStructure();
	    JMenuItem ptms();
	   
	    JMenuItem foregroundColor();
	    JMenuItem backgroundColor();
	    
	    JMenuItem clearPanes();
	    
	    void insertLine(String id, String header, String line, int headerPosition, int editorPosition);
	    void insertLineMiddle(String header, String line);
	    void insertLineAboveTarget(String header, String line);
	    void insertLineBelowTarget(String header, String line);
	    void deleteLineByHeader(String header);
	    void deleteLineByProteinAcc(String proteinAcc);
	    
	    void addProteinSelecter(SelectProteinView selectProtein);
		
		void createUI();
		void proteinAcc(String proteinAcc);	
		
		void reloadDocument();
	}
	
	public interface Model {
		Line fetchProteinLine(String proteinId);
		ArrayList<UniProtHit> fetchBlastHits(String proteinAcc, DatabaseOption databaseOption, ExpectationOption expectationOption, FilterOption filterOption, DropOffOption dropOffOption, MatrixOption matrixOption, ScoreCutoffOption scoreCutoffOption, boolean isGapAlign, int gapExt, int gapOpen, boolean limitToTargetSpecies,boolean limitToSwissProtDB);
		BlastData<UniProtEntry> fetchBlastData(String proteinAcc, List<DatabaseOption> dbOptions);
		LinkedHashMap<String,String> fetchAlignmentProteinMap(String msa, ArrayList<String> proteinAccs);
	}
	
	View view;
	Model model;
	
	public Presenter(View view, Model model) {
		this.view = view;
		this.model = model;
		bindHandlers();
	}
	
	public void bindHandlers() {
		
		//toolbar buttons
		view.searchButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				proteinAcc = view.searchField().getText();
				if (proteinAcc != null && !proteinAcc.equals("")) {
					protein = new Protein(proteinAcc,true);
					clearViews();
					view.proteinAcc(proteinAcc);
					insertLineMiddle(proteinAcc);
				}
			}		
		});
		
		view.blastButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BlastPresenter blastPresenter = new BlastPresenter(Presenter.this);
			}		
		});
		
		view.msaButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(msaProteinList.size() > 0) {
					MSAPresenter msaPresenter = new MSAPresenter(Presenter.this);
				}
			}	
		});
		
		//menu buttons
		view.blast().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				BlastPresenter blastPresenter = new BlastPresenter(Presenter.this);
			}		
		});
		
		view.msa().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(msaProteinList.size() > 1) {
					MSAPresenter msaPresenter = new MSAPresenter(Presenter.this);
				}
				else {
					//message
				}
			}	
		});
		
		view.abcpred().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}	
		});
		
		view.bcepred().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}	
		});
		
		view.discotopePred().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}	
		});
		
		view.elliproPred().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}	
		});
		
		view.iedbPrediction().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				IEDBPredPresenter iedbPredPresenter = new IEDBPredPresenter(Presenter.this);
			}	
		});
		
		view.psipred().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				PsiPredService psiPredService = new PsiPredService(protein.getFastaFile());
				psiPredService.run();
				ArrayList<PsiPredAminoAcid> aaList = psiPredService.getAminoAcids();
				
				String sspLine = "";
				int mainIndex = 0;
				for(int i = 0; i < aaList.size(); i++) {
					if(Character.isLetter(getMainLine().charAt(mainIndex))) {
						sspLine = sspLine + aaList.get(i).getPrediction();
					}
					else {
						sspLine = sspLine + "-";
						i--;
					}
					mainIndex++;
				}
				view.insertLineAboveTarget("PsiPred SSP", sspLine);
			}	
		});
		
		view.jpred().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JPredService jPredService;
				try {
					jPredService = new JPredService(protein.getFastaFile());
					jPredService.run();
					ArrayList<JPredAminoAcid> aaList = jPredService.getAminoAcids();
					
					String sspLine = "";
					int mainIndex = 0;
					for(int i = 0; i < aaList.size(); i++) {
						if(Character.isLetter(getMainLine().charAt(mainIndex))) {
							sspLine = sspLine + aaList.get(i).getPrediction();
						}
						else {
							sspLine = sspLine + "-";
							i--;
						}
						mainIndex++;
					}
					view.insertLineAboveTarget("JPred SSP", sspLine);
				} catch (JPredSequenceTooLongException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Protein must be <800 amino acid long to perform JPred SSP.");
				}
				
			}	
		});
		
		view.pdbStructure().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				pdbEntryList.clear();
				SiftsView siftsView = new SiftsView();
				SiftsPresenter siftsPresenter = new SiftsPresenter(Presenter.this,siftsView);
				ArrayList<PDBentry>pdbEntries = protein.getPDBentries();
				for(PDBentry pdbEntry: pdbEntries) {
					SelectPDBView spv = new SelectPDBView(protein.acc, pdbEntry.getId(), pdbEntry.getMethod(), pdbEntry.getResolution(), pdbEntry.getPositions());
					SelectPDBPresenter spp = new SelectPDBPresenter(spv,protein.acc);
					siftsView.insertEntry(spv);
				}
			}
		});
		
		view.ptms().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PhosphoSitePresenter phosphoSitePresenter = new PhosphoSitePresenter(Presenter.this);
			}
		});
		
		view.clearPanes().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearViews();
			}
		});
			
	}
	
	public void display() {
		view.createUI();		
	}
	
	public void insertLineMiddle(String proteinId) {
		Line line = model.fetchProteinLine(proteinId);
		proteinHeader = line.getHeader();
		view.insertLineMiddle(line.getHeader(), line.getLine());
		view.reloadDocument();
	}
	
	public void setBlastHits(DatabaseOption databaseOption, ExpectationOption expectationOption, FilterOption filterOption, DropOffOption dropOffOption, MatrixOption matrixOption, ScoreCutoffOption scoreCutoffOption, boolean isGapAlign, int gapExt, int gapOpen, boolean limitToTargetSpecies, boolean limitToSwissProtDB) {
		clearBlastPanel();
		ArrayList<UniProtHit> blastHits = model.fetchBlastHits(proteinAcc, databaseOption, expectationOption, filterOption, dropOffOption, matrixOption, scoreCutoffOption, isGapAlign, gapExt, gapOpen, limitToTargetSpecies, limitToSwissProtDB);
		
		for(UniProtHit hit: blastHits) {
			if (!hit.getSummary().getEntryId().equals(protein.getUniprotNameId()) && !hit.getSummary().getEntryAc().equals(proteinAcc)) {
				SelectProteinView spv = new SelectProteinView(hit.getSummary().getEntryAc()+" "+hit.getSummary().getEntryId());
				SelectProteinPresenter spp = new SelectProteinPresenter(spv);
				view.proteinPanel().add(spv);
			}
		}
	}
	
	public void clearBlastPanel() {
		for(int i = view.proteinPanel().getComponentCount()-1; i > 0 ; i--) {
			msaProteinList.clear();
			view.proteinPanel().remove(i);
		}
	}
	
	public void setAlignment(String msaProgram) {
		ArrayList<String> proteinMSAarrayList = new ArrayList<String>(msaProteinList);
		proteinMSAarrayList.add(0, proteinAcc);
		LinkedHashMap<String, String> proteinAlignmentMap = model.fetchAlignmentProteinMap(msaProgram, proteinMSAarrayList);
		
		ArrayList<String> sequences = new ArrayList<String>(proteinAlignmentMap.values());
		ArrayList<String> headers = new ArrayList<String>(proteinAlignmentMap.keySet());
		for(int i = 0; i < headers.size(); i++) {
			if(headers.get(i).contains(proteinAcc+"|")) {
				proteinHeader = headers.get(i);
				Collections.swap(headers, 0, i);
				Collections.swap(sequences, 0, i);
			}
		}
		String comparisonLine = "";
		String firstSequence = sequences.get(0);
		for (int i = 0; i < firstSequence.length(); i++) {
			boolean aaAreEqual = true;
			innerLoop:
			for(int j = 1; j < sequences.size(); j++) {
				String comparisonSequence = sequences.get(j);
				if(firstSequence.charAt(i) != comparisonSequence.charAt(i)) {
					aaAreEqual = false;
					break innerLoop;
				}
			}
			if(aaAreEqual) {
				comparisonLine = comparisonLine + "*";
			}
			else {
				comparisonLine = comparisonLine + " ";
			}
		}
		clearViews();
		view.insertLineMiddle(headers.get(0), sequences.get(0));
		view.insertLineBelowTarget("Comparison Line",comparisonLine);
		for(int i = 1; i < sequences.size(); i++) {
			view.insertLineBelowTarget(headers.get(i), sequences.get(i));
		}
		view.reloadDocument();
	}
	
	public String getMainLine() {
		String line = "";
    	try {
    		HTMLDocument editorDoc = (HTMLDocument) view.editorPane().getDocument();
        	StringWriter writer = new StringWriter();
        	HTMLEditorKit kit = new HTMLEditorKit();
        	kit.write(writer, editorDoc, 0, editorDoc.getLength());
        	String html = writer.toString();
        	
        	org.jsoup.nodes.Document doc = Jsoup.parse(html);
        	org.jsoup.nodes.Element element = doc.getElementById(proteinAcc);
        	line = element.text();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("getMainLine: " +line);
    	return line;
	}
	
	private void clearViews() {
		view.editorPane().setText("");
		view.headerPane().setText("");
	}
	
    public void insertLine(String header, String line, int headerPosition, int editorPosition) {
    	view.insertLine(header,header, line, headerPosition, editorPosition);
    }
    public void insertLineMiddle(String header, String line) {
    	view.insertLineMiddle(header, line);
    }
    public void insertLineAboveTarget(String header, String line) {
    	view.insertLineAboveTarget(header, line);
    }
    public void insertLineBelowTarget(String header, String line) {
    	view.insertLineBelowTarget(header, line);
    }
    public void deleteLineByHeader(String header) {
    	view.deleteLineByHeader(header);
    }
    public void deleteLineByProteinAcc(String proteinAcc) {
    	view.deleteLineByProteinAcc(proteinAcc);
    }

}
