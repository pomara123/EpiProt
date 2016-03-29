package epiprot.services.epitopePrediction;

import epiprot.Protein;

public class ChouPredictionService extends IedbEpitopePredictionService {

	public ChouPredictionService(String sequence) {
		// TODO Auto-generated constructor stub
		super(sequence,"Chou-Fasman");
	}
	
	public static void main (String[] args) {
		Protein protein = new Protein("Q99523",true);
		ChouPredictionService cps = new ChouPredictionService(protein.getSequence());
		cps.run();
		System.out.println(cps.getAminoAcids().size());
		for (IedbEpitopePredictionAminoAcid aa : cps.getAminoAcids()) {
			System.out.println(aa.getScore());
		}
	}
}
