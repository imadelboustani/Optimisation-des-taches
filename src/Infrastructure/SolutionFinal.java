package Infrastructure;

import java.util.HashMap;

public class SolutionFinal {
	
	
	private double BestCCever;
	private double BestCEever;
	private HashMap<Integer,SolutionVoisin> bests = new HashMap<Integer,SolutionVoisin>();
	private int[][] solinitialeArray;
	private double CCinitial;
	private double CEinitial;
	
	
	public SolutionFinal(double bestCCever, double bestCEever,
			HashMap<Integer, SolutionVoisin> bests, int[][] solinitialeArray) {
		super();
		BestCCever = bestCCever;
		BestCEever = bestCEever;
		this.bests = bests;
		this.solinitialeArray = solinitialeArray;
	}

	public int[][] getSolinitialeArray() {
		return solinitialeArray;
	}

	public void setSolinitialeArray(int[][] solinitialeArray) {
		this.solinitialeArray = solinitialeArray;
	}

	public SolutionFinal(double bestCCever,
			double bestCEever, HashMap<Integer, SolutionVoisin> bests) {
		super();
		BestCCever = bestCCever;
		BestCEever = bestCEever;
		this.bests = bests;
	}

	public SolutionFinal() {
		super();
	}
	
	public double getBestCCever() {
		return BestCCever;
	}

	public void setBestCCever(double bestCCever) {
		BestCCever = bestCCever;
	}

	public double getBestCEever() {
		return BestCEever;
	}

	public void setBestCEever(double bestCEever) {
		BestCEever = bestCEever;
	}

	public HashMap<Integer, SolutionVoisin> getBests() {
		return bests;
	}

	public void setBests(HashMap<Integer, SolutionVoisin> bests) {
		this.bests = bests;
	}

	public double getCCinitial() {
		return CCinitial;
	}

	public void setCCinitial(double cCinitial) {
		CCinitial = cCinitial;
	}

	public double getCEinitial() {
		return CEinitial;
	}

	public void setCEinitial(double cEinitial) {
		CEinitial = cEinitial;
	}
	
}
