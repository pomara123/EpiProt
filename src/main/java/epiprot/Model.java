package epiprot;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import epiprot.services.blast.UniprotBlastService;
import epiprot.services.jabaws.JabawsService;
import uk.ac.ebi.kraken.interfaces.uniprot.UniProtEntry;
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
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.UniProtHit;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.DatabaseOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.DropOffOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.ExpectationOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.FilterOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.MatrixOption;
import uk.ac.ebi.uniprot.dataservice.client.alignment.blast.input.ScoreCutoffOption;

public class Model implements Presenter.Model {
	
	public Model(){}

	@Override
	public Line fetchProteinLine(String proteinAcc) {
		// TODO Auto-generated method stub
		Line line = new Line();
		Protein protein = new Protein(proteinAcc,true);
		line.setHeader(proteinAcc+"|"+protein.getUniprotNameId()+"|"+protein.getDatabase());
		line.setLine(protein.getSequence());
		return line;
	}

	@Override
	public LinkedHashMap<String, String> fetchAlignmentProteinMap(String msa, ArrayList<String> proteinAccs) {
		// TODO Auto-generated method stub
		JabawsService js = new JabawsService(msa,proteinAccs);
		js.run();
		return js.getAlignment();
	}

	@Override
	public ArrayList<UniProtHit> fetchBlastHits(String proteinAcc, DatabaseOption databaseOption,
			ExpectationOption expectationOption, FilterOption filterOption, DropOffOption dropOffOption,
			MatrixOption matrixOption, ScoreCutoffOption scoreCutoffOption, boolean isGapAlign, int gapExt, int gapOpen,
			boolean limitToTargetSpecies, boolean limitToSwissProtDB) {
		// TODO Auto-generated method stub
		UniprotBlastService ubs = new UniprotBlastService(new Protein(proteinAcc,true), databaseOption, expectationOption, filterOption, dropOffOption, matrixOption, scoreCutoffOption, isGapAlign, gapExt, gapOpen, limitToTargetSpecies, limitToSwissProtDB);
		ubs.run();
		return ubs.getBlastResults();
	}

	@Override
	public BlastData<UniProtEntry> fetchBlastData(String proteinAcc, List<DatabaseOption> dbOptions) {
		// TODO Auto-generated method stub
		return null;
	}

}
