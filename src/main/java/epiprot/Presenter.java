package epiprot;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import epiprot.services.jabaws.JabawsConstants;
import epiprot.services.ssp.JPredAminoAcid;
import epiprot.services.ssp.JPredSequenceTooLongException;
import epiprot.services.ssp.JPredService;
import epiprot.services.ssp.PsiPredAminoAcid;
import epiprot.services.ssp.PsiPredService;
import epiprot.services.views.BlastPresenter;
import epiprot.services.views.IEDBPredPresenter;
import epiprot.services.views.MSAPresenter;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.model.blast.columns.BlastSortingColumnType;
import uk.ac.ebi.kraken.model.blast.parameters.DatabaseOptions;
import uk.ac.ebi.kraken.model.blast.parameters.ExpectedThreshold;
import uk.ac.ebi.kraken.model.blast.parameters.FilterOptions;
import uk.ac.ebi.kraken.model.blast.parameters.FormatOptions;
import uk.ac.ebi.kraken.model.blast.parameters.MaxNumberResultsOptions;
import uk.ac.ebi.kraken.model.blast.parameters.ScoreOptions;
import uk.ac.ebi.kraken.model.blast.parameters.SensitivityValue;
import uk.ac.ebi.kraken.model.blast.parameters.SimilarityMatrixOptions;
import uk.ac.ebi.kraken.model.blast.parameters.SortOptions;
import uk.ac.ebi.kraken.model.blast.parameters.StatsOptions;
import uk.ac.ebi.kraken.model.blast.parameters.TopcomboN;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastData;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastHit;

public class Presenter {
	
	public static ArrayList<String> msaProteinList = new ArrayList<String>(); 
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
	    	    
	    void insertLine(String header, String line, int headerPosition, int editorPosition);
	    void insertLineMiddle(String header, String line);
	    void insertLineAboveTarget(String header, String line);
	    void insertLineBelowTarget(String header, String line);
	    void deleteLineByHeader(String header);
	    void deleteLineByProteinAcc(String proteinAcc);
	    
	    void addProteinSelecter(SelectProteinView selectProtein);
		
		void createUI();
		void proteinAcc(String proteinAcc);	
	}
	
	public interface Model {
		Line fetchProteinLine(String proteinId);
		List<BlastHit<UniProtEntry>> fetchBlastHits(String proteinAcc, DatabaseOptions databaseOptions, SimilarityMatrixOptions similarityMatrixOptions, ExpectedThreshold expectedThreshold, MaxNumberResultsOptions maxNumberResultsOptions, ScoreOptions scoreOptions, SensitivityValue sensitivityValue, SortOptions sortOptions, StatsOptions statsOptions, FormatOptions formatOptions, TopcomboN topcomboN, boolean limitToTargetSpecies,boolean limitToSwissProtDB);
		List<BlastHit<UniProtEntry>> fetchBlastHits(String proteinAcc, DatabaseOptions databaseOptions, ExpectedThreshold expectedThreshold, MaxNumberResultsOptions maxNumberResultsOptions, ScoreOptions scoreOptions, SensitivityValue sensitivityValue, SortOptions sortOptions, StatsOptions statsOptions, FormatOptions formatOptions, TopcomboN topcomboN, boolean limitToTargetSpecies, boolean limitToSwissProtDB);
		BlastData<UniProtEntry> fetchBlastData(String proteinAcc, List<DatabaseOptions> dbOptions);
		LinkedHashMap<String,String> fetchAlignmentProteinMap(String msa, ArrayList<String> proteinAccs);
		int fetchProteinLength(String proteinAcc);
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
				//setBlastHits(dbOption);
			}		
		});
		
		view.msaButton().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(msaProteinList.size() > 0) {
					MSAPresenter msaPresenter = new MSAPresenter(Presenter.this);
					//setAlignment(JabawsConstants.MUSCLE);
					//System.out.println(proteinHeader);
					//view.deleteLineByHeader(proteinHeader);
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
					//setAlignment(JabawsConstants.MUSCLE);
					//System.out.println(proteinHeader);
					//view.deleteLineByHeader(proteinHeader);
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
				IEDBPredPresenter iedbPredPresenter = new IEDBPredPresenter(Presenter.this,model.fetchProteinLength(proteinAcc));
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
	}
	
	public void display() {
		view.createUI();		
	}
	
	public void insertLineMiddle(String proteinId) {
		Line line = model.fetchProteinLine(proteinId);
		proteinHeader = line.getHeader();
		view.insertLineMiddle(line.getHeader(), line.getLine());
	}
	
	public void setBlastHits(DatabaseOptions databaseOptions, ExpectedThreshold expectedThreshold, MaxNumberResultsOptions maxNumberResultsOptions, ScoreOptions scoreOptions, SensitivityValue sensitivityValue, SortOptions sortOptions, StatsOptions statsOptions, FormatOptions formatOptions, TopcomboN topcomboN, boolean limitToTargetSpecies, boolean limitToSwissProtDB) {
		clearBlastPanel();
		List<BlastHit<UniProtEntry>> blastHits = model.fetchBlastHits(proteinAcc, databaseOptions, expectedThreshold, maxNumberResultsOptions, scoreOptions, sensitivityValue, sortOptions, statsOptions, formatOptions, topcomboN, limitToTargetSpecies, limitToSwissProtDB);
		//BlastData<UniProtEntry> blastData = model.fetchBlastData(proteinAcc, dbOptions);
		for(BlastHit<UniProtEntry> hit: blastHits) {
			SelectProteinView spv = new SelectProteinView(hit.getHit().getAc()+" "+hit.getHit().getHitId());
			SelectProteinPresenter spp = new SelectProteinPresenter(spv);
			view.proteinPanel().add(spv);
		}
	}
	
	public void setBlastHits(DatabaseOptions databaseOptions, SimilarityMatrixOptions similarityMatrixOptions, ExpectedThreshold expectedThreshold, MaxNumberResultsOptions maxNumberResultsOptions, ScoreOptions scoreOptions, SensitivityValue sensitivityValue, SortOptions sortOptions, StatsOptions statsOptions, FormatOptions formatOptions, TopcomboN topcomboN, boolean limitToTargetSpecies, boolean limitToSwissProtDB) {
		clearBlastPanel();
		List<BlastHit<UniProtEntry>> blastHits = model.fetchBlastHits(proteinAcc, databaseOptions, similarityMatrixOptions, expectedThreshold, maxNumberResultsOptions, scoreOptions, sensitivityValue, sortOptions, statsOptions, formatOptions, topcomboN, limitToTargetSpecies, limitToSwissProtDB);
		//BlastData<UniProtEntry> blastData = model.fetchBlastData(proteinAcc, dbOptions);
		for(BlastHit<UniProtEntry> hit: blastHits) {
			SelectProteinView spv = new SelectProteinView(hit.getHit().getAc()+" "+hit.getHit().getHitId());
			SelectProteinPresenter spp = new SelectProteinPresenter(spv);
			view.proteinPanel().add(spv);
		}
	}
	
	private void clearBlastPanel() {
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
	}
	
	public String getMainLine() {
		String line = "";
		String [] headers = view.headerPane().getText().split("\n");
    	String [] lines = view.editorPane().getText().split("\n");
    	
    	loop:
    	for (int i = 0; i < headers.length; i++) {
    		if(headers[i].contains(proteinAcc+"|")) {
    			line = lines[i];
    			break loop;
    		}
    	}
    	return line;
	}
	
	private void clearViews() {
		view.editorPane().setText("");
		view.headerPane().setText("");
	}
	
    public void insertLine(String header, String line, int headerPosition, int editorPosition) {
    	view.insertLine(header, line, headerPosition, editorPosition);
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
