package epiprot.services.epitopePrediction;

public class KarplusPredictionService extends IedbEpitopePredictionService{

	public KarplusPredictionService(String sequence) {
		// TODO Auto-generated constructor stub
		super(sequence,"Karplus-Schulz");
	}
}
