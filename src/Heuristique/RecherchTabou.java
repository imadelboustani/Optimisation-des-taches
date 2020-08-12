package Heuristique;

import java.util.HashMap;
import java.util.HashSet;

import Infrastructure.Process;
import Infrastructure.RessourceManager;
import Infrastructure.Server;
import Infrastructure.Solution;
import Infrastructure.SolutionFinal;
import Infrastructure.SolutionVoisin;

public class RecherchTabou {
	private RessourceManager rm = new RessourceManager();
	private HashMap<Integer,Server> servers = rm.GetServers();
	private HashMap<Integer,Process> processus = rm.GetProcesses();
	
	
	private Solution SolutionCourant = GenerateSolutionInitial.SolutionInitialV2(servers, processus);
	private GenerationVoisin generationVoisin = new GenerationVoisin(servers.size());
	private double minCCtotale = SolutionCourant.getCCtotale();
	private double minCEtotale = SolutionCourant.getCEtotale();
	private HashMap<Integer,int[]>  listTabou = new HashMap<Integer,int[]>();
	private HashMap<Integer,SolutionVoisin> bests = new HashMap<Integer,SolutionVoisin>();
	private int[][] bestCE = new int[servers.size()][processus.size()];
	private int[][] bestCC = new int[servers.size()][processus.size()];
	private int[][] solinitial = new int[servers.size()][processus.size()];
	private double ccinitial = SolutionCourant.getCCtotale();
	private double ceinitial = SolutionCourant.getCEtotale();
	
	
	
	public  SolutionFinal recherchTabou(int nbrIteration,int nbIteSansAmelioraion,int longueurListTabou){
		int k = 0; //key list tabou!
		int n=0;
		//int c = 0; //key of best 3 of communication
		//int e = 0; //key of best 3 of execution
		
		//double coutMin = SolutionCourant.getCCtotale() + SolutionCourant.getCEtotale(); 
		//double  coutVoisin;
	//	double  coutLastSolution = coutMin; /** si le cout in dans la 1ere iteration **/
		/**    ** initialisation **  */
		initialisation(SolutionCourant, bests, solinitial);
		
		/***   génération de premier voisin    ***/
		SolutionVoisin solutionVoisin = this.generationVoisin.generateVoisin(SolutionCourant);
		generationVoisin.swapValueInArray(solutionVoisin);
		double lastCC = solutionVoisin.getCC();
		double lastCE = solutionVoisin.getCE();
		for(int i=0; i<nbrIteration;i++,n++){
			
			
			//coutVoisin = solutionVoisin.getCC() + solutionVoisin.getCE();
			
			if(isdegrading(minCCtotale, minCEtotale, solutionVoisin.getCC(), solutionVoisin.getCE())){ /** critère d'aspiration**/
				
				SolutionCourant = solutionVoisin.solutionVoisinToSolutionCourant(SolutionCourant);
				listTabou.put(k++%(longueurListTabou*2),solutionVoisin.getAction());
				listTabou.put(k++%(longueurListTabou*2),solutionVoisin.actionToInverse());
				//coutMin = coutVoisin;
				n=0;
				
			}else{
				if(!listTabou.containsValue(solutionVoisin.getAction())){
					SolutionCourant = solutionVoisin.solutionVoisinToSolutionCourant(SolutionCourant);
					if(isdegrading(lastCC, lastCE, solutionVoisin.getCC(), solutionVoisin.getCE())){
						listTabou.put(k++%(longueurListTabou*2),solutionVoisin.getAction());
						listTabou.put(k++%(longueurListTabou*2),solutionVoisin.actionToInverse());
						n=0;
					}
				}				
			}
			
			/** save best CC and best CE **/
			if(solutionVoisin.getCC()<minCCtotale){
				minCCtotale = solutionVoisin.getCC();
				for(int s=0;s<solutionVoisin.getSolution().length;s++){
					for(int r=0;r<solutionVoisin.getSolution()[s].length;r++){
						bestCC[s][r] = solutionVoisin.getSolution()[s][r];
					}
				}
				solutionVoisin.setSolution(bestCC);
				bests.put(0, solutionVoisin);						
			}
			
			if(solutionVoisin.getCE()<minCEtotale){
				minCEtotale = solutionVoisin.getCE();
				for(int s=0;s<solutionVoisin.getSolution().length;s++){
					for(int r=0;r<solutionVoisin.getSolution()[s].length;r++){
						bestCE[s][r] = solutionVoisin.getSolution()[s][r];
					}
				}
				solutionVoisin.setSolution(bestCE);
				bests.put(1, solutionVoisin);
			}		
			
			//coutLastSolution = coutVoisin;
			lastCC = solutionVoisin.getCC();
			lastCE = solutionVoisin.getCE();
			solutionVoisin = this.generationVoisin.generateVoisin(SolutionCourant);	
			if(!listTabou.containsValue(solutionVoisin.getAction())){
				generationVoisin.swapValueInArray(solutionVoisin);
			}
			if(n == nbIteSansAmelioraion){
				System.out.println("vous avez dépassée le nombre d'iteration sans amélioration!!");
				break;
			}
			
		}
		/*****  ***en instancier la classe Solution finale et  retourner  *****/
		SolutionFinal solutionfinal = new SolutionFinal(minCCtotale, minCEtotale,bests,solinitial);
		solutionfinal.setCCinitial(ccinitial);
		solutionfinal.setCEinitial(ceinitial);
		
		return solutionfinal;
		
	}
	public boolean isdegrading(double lastCC,double lastCE, double CC,double CE){
		if(lastCC >= CC || lastCE >= CE)
			return true;
		else
			return false;
	}
	public void initialisation(Solution SolutionCourant,HashMap<Integer,SolutionVoisin> bests,int[][] solinitial){
		for(int s=0;s<SolutionCourant.getSolution().length;s++){
			for(int r=0;r<SolutionCourant.getSolution()[s].length;r++){
//				bestCE[s][r] = SolutionCourant.getSolution()[s][r];
//				bestCC[s][r] = SolutionCourant.getSolution()[s][r];
				solinitial[s][r] = SolutionCourant.getSolution()[s][r];
			}
		}
		SolutionVoisin sv = new SolutionVoisin();
		sv.setSolution(solinitial);
		sv.setCC(SolutionCourant.getCCtotale());
		sv.setCE(SolutionCourant.getCEtotale());
		bests.put(0, sv);
		bests.put(1, sv);
		
	}
	
}
