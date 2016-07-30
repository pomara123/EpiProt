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
	
	private int hydroRelative;
	private int flexiRelative;
	private int accessRelative;
	private int turnsRelative;
	private int surfaceRelative;
	private int polarRelative;
	private int antiProRelative;
	private int maxRelative;
	private int minRelative;
	private int averageRelative;

	public BcePredAminoAcid() {
		// TODO Auto-generated constructor stub
		hydroRelative = -1;
		flexiRelative = -1;
		accessRelative = -1;
		turnsRelative = -1;
		surfaceRelative = -1;
		polarRelative = -1;
		antiProRelative = -1;
		maxRelative = -1;
		minRelative = -1;
		averageRelative = -1;
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
	
	@Override
	public String toString() {
		return "Res: "+super.getResidue()+super.getPosition()+" H: "+hydro+" F: "+ flexi+" A: "+ access+" T: "+ turns+" S: "+ surface+" P: "+ polar+" AP: "+ 
				antiPro+" Max: "+ max+" Min: "+ min+" Avg: "+ average;
	}

	public int getHydroRelative() {
		return hydroRelative;
	}

	public void setHydroRelative(int hydroRelative) {
		this.hydroRelative = hydroRelative;
	}

	public int getFlexiRelative() {
		return flexiRelative;
	}

	public void setFlexiRelative(int flexiRelative) {
		this.flexiRelative = flexiRelative;
	}

	public int getAccessRelative() {
		return accessRelative;
	}

	public void setAccessRelative(int accessRelative) {
		this.accessRelative = accessRelative;
	}

	public int getTurnsRelative() {
		return turnsRelative;
	}

	public void setTurnsRelative(int turnsRelative) {
		this.turnsRelative = turnsRelative;
	}

	public int getSurfaceRelative() {
		return surfaceRelative;
	}

	public void setSurfaceRelative(int surfaceRelative) {
		this.surfaceRelative = surfaceRelative;
	}

	public int getPolarRelative() {
		return polarRelative;
	}

	public void setPolarRelative(int polarRelative) {
		this.polarRelative = polarRelative;
	}

	public int getAntiProRelative() {
		return antiProRelative;
	}

	public void setAntiProRelative(int antiProRelative) {
		this.antiProRelative = antiProRelative;
	}

	public int getMaxRelative() {
		return maxRelative;
	}

	public void setMaxRelative(int maxRelative) {
		this.maxRelative = maxRelative;
	}

	public int getMinRelative() {
		return minRelative;
	}

	public void setMinRelative(int minRelative) {
		this.minRelative = minRelative;
	}

	public int getAverageRelative() {
		return averageRelative;
	}

	public void setAverageRelative(int averageRelative) {
		this.averageRelative = averageRelative;
	}
}
