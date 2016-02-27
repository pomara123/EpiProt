package epiprot.services.blast;

import java.util.ArrayList;
import java.util.List;

import epiprot.Protein;
import epiprot.services.Service;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.model.blast.JobStatus;
import uk.ac.ebi.kraken.model.blast.parameters.DatabaseOptions;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtJAPI;
import uk.ac.ebi.kraken.uuw.services.remoting.UniProtQueryService;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastData;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastHit;
import uk.ac.ebi.kraken.uuw.services.remoting.blast.BlastInput;

public class UniprotBlastService extends Service {	
	
	private String sequence;
	private String species;
	private List<DatabaseOptions> databaseOptions;
	BlastData<UniProtEntry> blastResult;
	
	public UniprotBlastService(Protein protein, List<DatabaseOptions> databaseOptions) {
		// TODO Auto-generated constructor stub
		this.sequence = protein.getSequence();
		this.species = protein.getOrganism();
		this.databaseOptions = databaseOptions;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		//Get the UniProt Service. This is how to access the blast service
	    UniProtQueryService service = UniProtJAPI.factory.getUniProtQueryService();
	    //Create a blast input with a Database and sequence
	    BlastInput input = new BlastInput(databaseOptions.get(0),sequence);
	    for(int i = 1; i < databaseOptions.size(); i++) {
	    	input.setDatabase(databaseOptions.get(i));
	    }
	    //Submitting the input to the service will return a job id
	    String jobid = service.submitBlast(input);
	    //Use this jobid to check the service to see if the job is complete
	    while (!(service.checkStatus(jobid) == JobStatus.FINISHED)) {
	        try {
	        //Sleep a bit before the next request
	            Thread.sleep(5000);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    //The blast data contains the job information and the hits with entries
	    blastResult = service.getResults(jobid);	
	}
	
	public BlastData<UniProtEntry> getBlastResults() {
		return blastResult;
	}
	
	public static void main (String[]args) {
		List<DatabaseOptions> databaseOptions = new ArrayList<DatabaseOptions>();
		databaseOptions.add(DatabaseOptions.UNIPROT_HUMAN);
		databaseOptions.add(DatabaseOptions.SWISSPROT);
		UniprotBlastService ubs = new UniprotBlastService(new Protein("Q99523",true),databaseOptions);
		ubs.run();
		BlastData<UniProtEntry> blastResults = ubs.getBlastResults();
		List<BlastHit<UniProtEntry>> blastHits = blastResults.getBlastHits();
		for(BlastHit<UniProtEntry> hit: blastHits) {
			System.out.println(hit.getHit().getAc()+"|"+hit.getHit().getDatabase());
		}
	}

}
