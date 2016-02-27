package epiprot;

public class Model implements Presenter.Model {
	
	public Model(){}

	@Override
	public Line fetchProteinLine(String proteinId) {
		// TODO Auto-generated method stub
		Line line = new Line();
		Protein protein = new Protein("Q99523",true);
		line.setHeader(proteinId+"|"+protein.getUniprotNameID()+"|"+protein.getDatabase());
		line.setLine(protein.getSequence());
		return line;
	}

}
