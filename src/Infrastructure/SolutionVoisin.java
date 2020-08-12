package Infrastructure;

import java.util.HashMap;

public class SolutionVoisin {
	private int[][] solution;
	private int[] tab= new int[6];
	private int[] action = new int[4];
   /*******************************************************************************
	*							  					 							  *	
	*		tab[0] = id server 1 (i)  = action[0]	 							  *
	*		tab[1] = id process de server 1  = action[1] 						  *
	*		tab[2] = position de cette process (1) dans la liste des process (n)  *
	*       tab[3] = id server 2  (j)   = action[2]                               *
	*       tab[4] = id process de server 2		= action[3]						  * 
	*       tab[5] = position de cette process (2) dans la liste des process (m)  *		
	*												 							  *											  
	*******************************************************************************/
	private double CE;
	private double CC;
	
	public double getCE() {
		return CE;
	}
	public void setCE(double cE) {
		CE = cE;
	}
	public double getCC() {
		return CC;
	}
	public void setCC(double cC) {
		CC = cC;
	}
	public int[][] getSolution() {
		return solution;
	}
	public void setSolution(int[][] solution) {
		this.solution = solution;
	}
	public int[] getTab() {
		return tab;
	}
	public void setTab(int[] tab) {
		this.tab = tab;
	}
	public int[] getAction() {
		return action;
	}
	public void setAction(int[] action) {
		this.action = action;
	}
	public SolutionVoisin(int[][] solution, int[] tab) {
		super();
		this.solution = solution;
		this.tab = tab;
		this.action[0] = tab[0];
		this.action[1] = tab[1];
		this.action[2] = tab[3];
		this.action[3] = tab[4];
	}
	public SolutionVoisin() {
		super();
	}
	public  Solution solutionVoisinToSolutionCourant(Solution sol){
		
		HashMap<Integer,Host> hosts = new HashMap<Integer, Host>();
		hosts = (HashMap<Integer, Host>) sol.getHosts();
		
		int i = this.tab[0];
		int n = this.tab[2];
		int j = this.tab[3];
		int m = this.tab[5];
		
		double ressource = hosts.get(i).getQtRessource();
		double a = hosts.get(i).getProcesses().get(n).getRequiredRessources();
		double b = hosts.get(j).getProcesses().get(m).getRequiredRessources();
		hosts.get(i).setQtRessource(ressource+a-b);
		ressource = hosts.get(j).getQtRessource();
		hosts.get(j).setQtRessource(ressource+b-a);
	
		
		hosts.get(i).getProcesses().add(hosts.get(j).getProcesses().get(m));
		hosts.get(j).getProcesses().add(hosts.get(i).getProcesses().get(n));
		hosts.get(j).getProcesses().remove(m);
		hosts.get(i).getProcesses().remove(n);
		
		
		Solution solution = new Solution(hosts,this.solution);
		
		solution.setCCtotale(this.CC);
		solution.setCEtotale(this.CE);
		
		return solution;		
	}
	
	public int[] actionToInverse(){
		int[] invAction = new int[4];
		
		invAction[0] = this.action[0];
		invAction[1] = this.action[3];
		invAction[2] = this.action[2];
		invAction[3] = this.action[1];
		
		return invAction;
	}
	
}
