package epiprot.services.epitopePrediction;

import epiprot.AminoAcid;

public class BcePredAminoAcid extends AminoAcid {
	
	private double hydro;
	private double flexi;
	private double access;
	private double turns;
	private double surface;
	private double polar;
	private double antiPro;
	private double max;
	private double min;
	private double average;

	public BcePredAminoAcid() {
		// TODO Auto-generated constructor stub
	}

	public double getHydro() {
		return hydro;
	}

	public void setHydro(double hydro) {
		this.hydro = hydro;
	}

	public double getFlexi() {
		return flexi;
	}

	public void setFlexi(double flexi) {
		this.flexi = flexi;
	}

	public double getAccess() {
		return access;
	}

	public void setAccess(double access) {
		this.access = access;
	}

	public double getTurns() {
		return turns;
	}

	public void setTurns(double turns) {
		this.turns = turns;
	}

	public double getSurface() {
		return surface;
	}

	public void setSurface(double surface) {
		this.surface = surface;
	}

	public double getPolar() {
		return polar;
	}

	public void setPolar(double polar) {
		this.polar = polar;
	}

	public double getAntiPro() {
		return antiPro;
	}

	public void setAntiPro(double antiPro) {
		this.antiPro = antiPro;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getAverage() {
		return average;
	}

	public void setAverage(double average) {
		this.average = average;
	}
	
	public String toString() {
		return "Res: "+super.getResidue()+super.getPosition()+" H: "+hydro+" F: "+ flexi+" A: "+ access+" T: "+ turns+" S: "+ surface+" P: "+ polar+" AP: "+ 
				antiPro+" Max: "+ max+" Min: "+ min+" Avg: "+ average;
	}
}
