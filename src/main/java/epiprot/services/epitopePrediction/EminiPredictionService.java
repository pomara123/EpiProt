package epiprot.services.epitopePrediction;

import java.util.ArrayList;

import epiprot.Protein;

public class EminiPredictionService extends IedbEpitopePredictionService {
	
	public EminiPredictionService(String sequence) {
		super(sequence, "Emini");
		// TODO Auto-generated constructor stub
	}
	
	public static void main (String[]args) {
		Protein protein = new Protein("Q99523",true);
		System.out.println(protein.getSequence());
		EminiPredictionService eps = new EminiPredictionService(protein.getSequence());
		eps.run();
		System.out.println(eps.chart);
		ArrayList<IedbEpitopePredictionAminoAcid> aaList = eps.getAminoAcids();
		for(IedbEpitopePredictionAminoAcid aa: aaList) {
			System.out.println(aa.toString());
		}
	}
}
