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

public class Model implements Presenter.Model {
	
	public Model(){}

	@Override
	public Line fetchProteinLine(String proteinAcc) {
		// TODO Auto-generated method stub
		Line line = new Line();
		Protein protein = new Protein(proteinAcc,true);
		line.setHeader(proteinAcc+"|"+protein.getUniprotNameID()+"|"+protein.getDatabase());
		line.setLine(protein.getSequence());
		return line;
	}

	@Override
	public List<BlastHit<UniProtEntry>> fetchBlastHits(String proteinAcc, DatabaseOptions databaseOptions, SimilarityMatrixOptions similarityMatrixOptions, ExpectedThreshold expectedThreshold, MaxNumberResultsOptions maxNumberResultsOptions, ScoreOptions scoreOptions, SensitivityValue sensitivityValue, SortOptions sortOptions, StatsOptions statsOptions, FormatOptions formatOptions, TopcomboN topcomboN, boolean limitToTargetSpecies, boolean limitToSwissProtDB) {
		// TODO Auto-generated method stub
		UniprotBlastService ubs = new UniprotBlastService(new Protein(proteinAcc,true),databaseOptions, similarityMatrixOptions, expectedThreshold, maxNumberResultsOptions, scoreOptions, sensitivityValue, sortOptions, statsOptions, formatOptions, topcomboN, limitToTargetSpecies, limitToSwissProtDB);
		ubs.run();
		return ubs.getBlastResults();
	}
	
	@Override
	public List<BlastHit<UniProtEntry>> fetchBlastHits(String proteinAcc, DatabaseOptions databaseOptions, ExpectedThreshold expectedThreshold, MaxNumberResultsOptions maxNumberResultsOptions, ScoreOptions scoreOptions, SensitivityValue sensitivityValue, SortOptions sortOptions, StatsOptions statsOptions, FormatOptions formatOptions, TopcomboN topcomboN, boolean limitToTargetSpecies, boolean limitToSwissProtDB) {
		// TODO Auto-generated method stub
		UniprotBlastService ubs = new UniprotBlastService(new Protein(proteinAcc,true),databaseOptions, expectedThreshold, maxNumberResultsOptions, scoreOptions, sensitivityValue, sortOptions, statsOptions, formatOptions, topcomboN, limitToTargetSpecies, limitToSwissProtDB);
		ubs.run();
		return ubs.getBlastResults();
	}

	@Override
	public BlastData<UniProtEntry> fetchBlastData(String proteinAcc, List<DatabaseOptions> dbOptions) {
		// TODO Auto-generated method stub
		UniprotBlastService ubs = new UniprotBlastService(new Protein(proteinAcc,true),dbOptions);
		ubs.run();
		return ubs.getBlastData();
	}

	@Override
	public LinkedHashMap<String, String> fetchAlignmentProteinMap(String msa, ArrayList<String> proteinAccs) {
		// TODO Auto-generated method stub
		JabawsService js = new JabawsService(msa,proteinAccs);
		js.run();
		return js.getAlignment();
	}

	@Override
	public int fetchProteinLength(String proteinAcc) {
		// TODO Auto-generated method stub
		Protein protein = new Protein(proteinAcc,true);
		return protein.getSequence().length();
	}

}
