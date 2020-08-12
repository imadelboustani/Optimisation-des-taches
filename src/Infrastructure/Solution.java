package Infrastructure;

import java.util.HashMap;

public class Solution {
	
	private  HashMap<Integer,Host> hosts = new HashMap<Integer,Host>();
	private int[][] solution;
	private double CEtotale;
	private double CCtotale;
	
	
	public HashMap<Integer, Host> getHosts() {
		return hosts;
	}
	public void setHosts(HashMap<Integer, Host> hosts) {
		this.hosts = hosts;
	}
	public int[][] getSolution() {
		return solution;
	}
	public void setSolution(int[][] solution) {
		this.solution = solution;
	}
	public double getCEtotale() {
		return CEtotale;
	}
	public void setCEtotale(double cEtotale) {
		CEtotale = cEtotale;
	}
	public double getCCtotale() {
		return CCtotale;
	}
	public void setCCtotale(double cCtotale) {
		CCtotale = cCtotale;
	}
	public Solution(HashMap<Integer, Host> hosts, int[][] solution) {
		super();
		this.hosts = hosts;
		this.solution = solution;
	}
	public Solution() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
