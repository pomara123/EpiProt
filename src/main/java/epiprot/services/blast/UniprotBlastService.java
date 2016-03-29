package epiprot.services.blast;

import java.util.ArrayList;
import java.util.List;

import epiprot.Protein;
import epiprot.services.ProgressWindow;
import epiprot.services.Service;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.model.blast.JobStatus;
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
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryService;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastData;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastHit;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastInput;

public class UniprotBlastService extends Service {	
	
	private String sequence;
	private String species;
	private String database;
	private List<DatabaseOptions> databaseOptionsList;
	private DatabaseOptions databaseOptions;
	BlastData<UniProtEntry> blastResult;
	private SimilarityMatrixOptions similarityMatrixOptions;
	private ExpectedThreshold expectedThreshold;
	private FilterOptions filterOptions;
	private MaxNumberResultsOptions maxNumberResultsOptions;
	private ScoreOptions scoreOptions;
	private SensitivityValue sensitivityValue;
	private SortOptions sortOptions;
	private StatsOptions statsOptions;
	private FormatOptions formatOptions; 
	private TopcomboN topcomboN;
	private boolean limitToTargetSpecies;
	private boolean limitToSwissProtDB;
	
	public UniprotBlastService(String sequence, List<DatabaseOptions> databaseOptionsList) {
		// TODO Auto-generated constructor stub
		this.sequence = sequence;
		this.databaseOptionsList = databaseOptionsList;		
	}
	
	public UniprotBlastService(Protein protein, List<DatabaseOptions> databaseOptionsList) {
		// TODO Auto-generated constructor stub
		this.sequence = protein.getSequence();
		this.species = protein.getOrganism();
		this.database = protein.getDatabase();
		this.databaseOptionsList = databaseOptionsList;
	}
	
	public UniprotBlastService(Protein protein, DatabaseOptions databaseOptions, SimilarityMatrixOptions similarityMatrixOptions, ExpectedThreshold expectedThreshold, MaxNumberResultsOptions maxNumberResultsOptions, ScoreOptions scoreOptions, SensitivityValue sensitivityValue, SortOptions sortOptions, StatsOptions statsOptions, FormatOptions formatOptions, TopcomboN topcomboN, boolean limitToTargetSpecies, boolean limitToSwissProtDB) {
		// TODO Auto-generated constructor stub
		this.sequence = protein.getSequence();
		this.species = protein.getOrganism();
		this.database = protein.getDatabase();
		this.databaseOptions = databaseOptions;
		this.similarityMatrixOptions = similarityMatrixOptions;
		this.expectedThreshold = expectedThreshold;
		this.filterOptions = filterOptions;
		this.maxNumberResultsOptions = maxNumberResultsOptions;
		this.scoreOptions = scoreOptions;
		this.sensitivityValue = sensitivityValue;
		this.sortOptions = sortOptions;
		this.statsOptions = statsOptions;
		this.formatOptions = formatOptions;
		this.topcomboN = topcomboN;
		this.limitToTargetSpecies = limitToTargetSpecies;
		this.limitToSwissProtDB = limitToSwissProtDB;
	}
	
	public UniprotBlastService(Protein protein, DatabaseOptions databaseOptions, ExpectedThreshold expectedThreshold, MaxNumberResultsOptions maxNumberResultsOptions, ScoreOptions scoreOptions, SensitivityValue sensitivityValue, SortOptions sortOptions, StatsOptions statsOptions, FormatOptions formatOptions, TopcomboN topcomboN, boolean limitToTargetSpecies, boolean limitToSwissProtDB) {
		// TODO Auto-generated constructor stub
		this.sequence = protein.getSequence();
		this.species = protein.getOrganism();
		this.database = protein.getDatabase();
		this.databaseOptions = databaseOptions;
		this.expectedThreshold = expectedThreshold;
		this.filterOptions = filterOptions;
		this.maxNumberResultsOptions = maxNumberResultsOptions;
		this.scoreOptions = scoreOptions;
		this.sensitivityValue = sensitivityValue;
		this.sortOptions = sortOptions;
		this.statsOptions = statsOptions;
		this.formatOptions = formatOptions;
		this.topcomboN = topcomboN;
		this.limitToTargetSpecies = limitToTargetSpecies;
		this.limitToSwissProtDB = limitToSwissProtDB;
	}

	@Override
	public void run() {
		ProgressWindow progressWindow = new ProgressWindow();	
		progressWindow.setTitleName("UniProt BLAST");
		//Get the UniProt Service. This is how to access the blast service
	    UniProtQueryService service = UniProtJAPI.factory.getUniProtQueryService();
	    //Create a blast input with a Database and sequence
	    BlastInput input;
	    if(similarityMatrixOptions != null) {
	    	input = new BlastInput(databaseOptions,sequence,similarityMatrixOptions,expectedThreshold,maxNumberResultsOptions, scoreOptions, sensitivityValue, sortOptions, statsOptions, formatOptions, topcomboN);
	    }
	    else {
		    input = new BlastInput(databaseOptions,sequence,expectedThreshold,maxNumberResultsOptions, scoreOptions, sensitivityValue, sortOptions, statsOptions, formatOptions, topcomboN);
	    }
	    //Submitting the input to the service will return a job id
	    String jobid = service.submitBlast(input);
	    
	    //System.out.println("jobid: " +jobid);
	    //Use this jobid to check the service to see if the job is complete
	    progressWindow.setProgressText("Fetching job..."); 
		progressWindow.createUI();
	    while (!(service.checkStatus(jobid) == JobStatus.FINISHED)) {
	        try {
	        //Sleep a bit before the next request
	            Thread.sleep(5000);	
	            //System.out.println("run uniprotblastservice");
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    progressWindow.setProgressText("Job status: "+service.checkStatus(jobid)); 
	    progressWindow.dispose();
	    //The blast data contains the job information and the hits with entries
	    //System.out.println(jobid);
	    blastResult = service.getResults(jobid);
	}
	
	public List<BlastHit<UniProtEntry>> getBlastResults() {
		List<BlastHit<UniProtEntry>> blastResults = blastResult.getBlastHits();
		List<BlastHit<UniProtEntry>> returnedBlastResult = new ArrayList<BlastHit<UniProtEntry>>();
		
		for(BlastHit<UniProtEntry> blastHit: blastResults) {
			Protein hitProtein = new Protein(blastHit.getHit().getAc(),true);
			System.out.println(hitProtein.getOrganism()+" "+species);
			System.out.println(hitProtein.getDatabase()+" "+"Swiss-Prot");
			if(limitToTargetSpecies && limitToSwissProtDB) {
				if(hitProtein.getOrganism().equals(species) && hitProtein.getDatabase().equals("Swiss-Prot")) {
					returnedBlastResult.add(blastHit);
				}
			}
			else if(limitToTargetSpecies) {
				if(hitProtein.getOrganism().equals(species)) {
					returnedBlastResult.add(blastHit);
				}
			}
			else if(limitToSwissProtDB) {
				if(hitProtein.getDatabase().equals("Swiss-Prot")) {
					returnedBlastResult.add(blastHit);
				}
			}
			else {
				returnedBlastResult.add(blastHit);
			}
		}	
		return returnedBlastResult;
	}
	
	public BlastData<UniProtEntry> getBlastData() {
		return blastResult;
	}
	
	public static void main (String[]args) {
		List<DatabaseOptions> databaseOptions = new ArrayList<DatabaseOptions>();
		databaseOptions.add(DatabaseOptions.UNIPROT_HUMAN);
		//databaseOptions.add(DatabaseOptions.SWISSPROT);
		//UniprotBlastService ubs = new UniprotBlastService(new Protein("Q99523",true),databaseOptions);
		UniprotBlastService ubs = new UniprotBlastService("TSDDRGIVYSKSLD",databaseOptions);
		
		ubs.run();
		List<BlastHit<UniProtEntry>> blastHits = ubs.getBlastResults();
		for(BlastHit<UniProtEntry> hit: blastHits) {
			System.out.println(hit.getHit().getAc()+"|"+hit.getHit().getDatabase()+"|"+hit.getHit().getHitId());
		}
	}

}
