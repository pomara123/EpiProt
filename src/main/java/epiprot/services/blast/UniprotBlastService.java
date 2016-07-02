package epiprot.services.blast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import epiprot.Protein;
import epiprot.services.ProgressWindow;
import epiprot.services.Service;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
import uk.ac.ebi.kraken.model.blast.JobStatus;
import uk.ac.ebi.uniprot.dataservice.client.Client;
import uk.ac.ebi.uniprot.dataservice.client.ServiceFactory;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.BlastInput;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.BlastResult;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.UniProtBlastService;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.UniProtHit;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.AlignmentCutoffOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.DatabaseOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.DropOffOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.ExpectationOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.FilterOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.MatrixOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.ScoreCutoffOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.SequenceTypeOption;

public class UniprotBlastService extends Service {	
	
	private String sequence;
	private String species;
	private String database;
	private DatabaseOption databaseOption;
	private ExpectationOption expectationOption;
	private FilterOption filterOption;
	private DropOffOption dropOffOption;
    private boolean isGapAlign;
    private int gapExt;
    private int gapOpen;
    private MatrixOption matrixOption;
    private AlignmentCutoffOption alignmentCutoffOption;
    private ScoreCutoffOption scoreCutoffOption;
	private boolean limitToTargetSpecies;
	private boolean limitToSwissProtDB;
	
	ArrayList<UniProtHit> uniProtHitList = new ArrayList<UniProtHit>();
	
	public UniprotBlastService(String sequence, DatabaseOption databaseOption) {
		// TODO Auto-generated constructor stub
		this.sequence = sequence;
		this.databaseOption = databaseOption;		
	}
	
	public UniprotBlastService(Protein protein, DatabaseOption databaseOption) {
		// TODO Auto-generated constructor stub
		this.sequence = protein.getSequence();
		this.species = protein.getOrganism();
		this.database = protein.getDatabase();
		this.databaseOption = databaseOption;
	}
	
	public UniprotBlastService(Protein protein, DatabaseOption databaseOption, ExpectationOption expectationOption, FilterOption filterOption, DropOffOption dropOffOption, MatrixOption matrixOption, ScoreCutoffOption scoreCutoffOption, boolean isGapAlign, int gapExt, int gapOpen, boolean limitToTargetSpecies, boolean limitToSwissProtDB) {
		// TODO Auto-generated constructor stub
		this.sequence = protein.getSequence();
		this.species = protein.getOrganism();
		this.database = protein.getDatabase();
		this.databaseOption = databaseOption;
		this.expectationOption = expectationOption;
		this.filterOption = filterOption;
		this.dropOffOption = dropOffOption;
	    this.isGapAlign = isGapAlign;
	    this.gapExt = gapExt;
	    this.gapOpen = gapOpen;
	    this.matrixOption = matrixOption;
	    this.scoreCutoffOption = scoreCutoffOption;
		this.limitToTargetSpecies = limitToTargetSpecies;
		this.limitToSwissProtDB = limitToSwissProtDB;
	}

	@Override
	public void run() {
		ProgressWindow progressWindow = new ProgressWindow();	
		progressWindow.setTitleName("UniProt BLAST");
		//Get the UniProt Service. This is how to access the blast service
		// Create UniProt blast service
		ServiceFactory serviceFactoryInstance = Client.getServiceFactoryInstance();
		UniProtBlastService uniProtBlastService = serviceFactoryInstance.getUniProtBlastService();
		uniProtBlastService.start();
	    //Create a blast input with a Database and sequence
	    BlastInput.Builder builder = new BlastInput.Builder(databaseOption,sequence);
	    if(expectationOption!=null) {builder.withExpectation(expectationOption);}
	    if(filterOption!=null) {builder.withFilter(filterOption);}
	    if(dropOffOption!=null) {builder.withDropOff(dropOffOption);}
	    builder.withGapAlign(isGapAlign);
	    if(gapExt!=0) {builder.withGapExt(gapExt);}
	    if(gapOpen!=0) {builder.withGapOpen(gapOpen);}
	    if(matrixOption!=null) {builder.withMatrix(matrixOption);}
	    if(alignmentCutoffOption!=null) {builder.withMaximumNumberOfAlignments(alignmentCutoffOption);}
	    if(scoreCutoffOption!=null) {builder.withMaximumNumberOfScores(scoreCutoffOption);}
	    builder.withSequenceType(SequenceTypeOption.PROTEIN);
	    
	    BlastInput input = builder.build();
	    
	    CompletableFuture <BlastResult<UniProtHit>> resultFuture = uniProtBlastService.runBlast(input);
	    
	    try {
	    	BlastResult blastResult = resultFuture.get();
	    	
	    	Iterator it = blastResult.hits().iterator();
	    	
	    	while(it.hasNext()) {
	    		UniProtHit hit = (UniProtHit) it.next();
	    		//System.out.println(hit.getEntry().getPrimaryUniProtAccession().getValue());
	    		uniProtHitList.add(hit);
	    	}

        } catch (ExecutionException e) {
        } catch (InterruptedException e) {
        } finally {
            uniProtBlastService.stop();
        }
	}
	
	public ArrayList<UniProtHit> getBlastResults() {
		ArrayList<UniProtHit> returnedBlastResult = new ArrayList<UniProtHit>();
		
		for(UniProtHit uniProtHit: uniProtHitList) {
			Protein hitProtein = new Protein(uniProtHit.getSummary().getEntryAc(),true);
			System.out.println(hitProtein.getOrganism()+" "+species);
			System.out.println(hitProtein.getDatabase()+" "+"Swiss-Prot");
			if(limitToTargetSpecies && limitToSwissProtDB) {
				if(hitProtein.getOrganism().equals(species) && hitProtein.getDatabase().equals("Swiss-Prot")) {
					returnedBlastResult.add(uniProtHit);
				}
			}
			else if(limitToTargetSpecies) {
				if(hitProtein.getOrganism().equals(species)) {
					returnedBlastResult.add(uniProtHit);
				}
			}
			else if(limitToSwissProtDB) {
				if(hitProtein.getDatabase().equals("Swiss-Prot")) {
					returnedBlastResult.add(uniProtHit);
				}
			}
			else {
				returnedBlastResult.add(uniProtHit);
			}
		}	
		return returnedBlastResult;
	}
	
	public void setMatrixOption(MatrixOption matrixOption) {
		this.matrixOption = matrixOption;
	}
	/*
	public BlastData<UniProtEntry> getBlastData() {
		return blastResult;
	}
	*/
	public static void main (String[]args) {
		UniprotBlastService ubs = new UniprotBlastService(new Protein("Q99523",true),DatabaseOption.UNIPROT_HUMAN);
		//UniprotBlastService ubs = new UniprotBlastService("TSDDRGIVYSKSLD",DatabaseOption.UNIPROT_HUMAN);
		ubs.setMatrixOption(MatrixOption.BLOSUM_62);
		ubs.run();
		ArrayList<UniProtHit> returnedBlastResult = ubs.getBlastResults();
		for(UniProtHit uniProtHit: returnedBlastResult) {
			System.out.println(uniProtHit.getSummary().getEntryId()+"|"+uniProtHit.getSummary().getDatabase()+"|"+uniProtHit.getSummary().getHitNumber());
		}
	}

}
