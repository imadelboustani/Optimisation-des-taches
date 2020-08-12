package Heuristique;

import java.io.ObjectInputStream.GetField;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import Infrastructure.Host;
import Infrastructure.Process;
import Infrastructure.RessourceManager;
import Infrastructure.Server;
import Infrastructure.Solution;

public class GenerateSolutionInitial {
	private static double[][] CCclone = RessourceManager.GetCCinital();
	private static double[][] CEclone = RessourceManager.GetCEinital();

	/********************************************************************************************
	 * 					      ** solution initial version 1 ** 									*
	 ********************************************************************************************/	
	public static Solution SolutionInitialV1(HashMap<Integer,Server> servers, HashMap<Integer,Process> processus){
		
		int[][] solution = new int[servers.size()][processus.size()];
		HashMap<Integer,Host>  hosts = new HashMap<Integer,Host>();
		
		for(Entry<Integer,Server> entry : servers.entrySet()){
			hosts.put(entry.getKey(),new Host(entry.getValue()));
		}
		
		for(Entry<Integer,Process> entry : processus.entrySet()){
			boolean b = true;
			for(Entry<Integer,Host> entryhost : hosts.entrySet()){
				if((entryhost.getValue().getQtRessource() >= entry.getValue().getRequiredRessources())&&(entryhost.getValue().getQtCommunication() >= entry.getValue().getRequiredCommunication())){
					entryhost.getValue().getProcesses().add(entry.getValue());
					
					double c = entryhost.getValue().getQtRessource();
					entryhost.getValue().setQtRessource(c-entry.getValue().getRequiredRessources());
					
					double p = entryhost.getValue().getQtCommunication();
					entryhost.getValue().setQtCommunication(p-entry.getValue().getRequiredCommunication());
					b = false;
					solution[entryhost.getKey()][entry.getKey()] = 1;
					break;
				}
			}
			if(b){
				System.out.println("cette processus "+ entry.getValue().getId() +" n'a pas d host");
			}
		}
		alertServerVide(hosts);
		Solution sol = new Solution(hosts,solution);
		
		double CE = CalculeCEtotale(solution);
		double CC = CalculeCCtotale(solution);
		System.out.println("Le CE de Solution:"+CE);
		System.out.println("Le CC de Solution:"+CC);
		sol.setCCtotale(CC);
		sol.setCEtotale(CE);
		
		return sol;
	}
	
	/********************************************************************************************
	 * 					      ** solution initial version 2 ** 									*
	 ********************************************************************************************/
	public static Solution SolutionInitialV2(HashMap<Integer,Server> servers, HashMap<Integer,Process> processus){
		
		int[][] solution = new int[servers.size()][processus.size()];
		HashMap<Integer,Host>  hosts = new HashMap<Integer,Host>();
		
		for(Entry<Integer,Server> entry : servers.entrySet()){
			hosts.put(entry.getKey(),new Host(entry.getValue()));
		}
		
		
		for(Entry<Integer,Process> entry:processus.entrySet()){
			boolean b = true;
			int k = 0;
			double minCout = 0;
			// ici ge travaille avec CC: look!!!!!!!!!!
			//double[][] CCclone = CCclone;
			
			for(Entry<Integer,Host> entryhost:hosts.entrySet()){
				if((entryhost.getValue().getQtRessource() >= entry.getValue().getRequiredRessources())&&(entryhost.getValue().getQtCommunication() >= entry.getValue().getRequiredCommunication())){
					 minCout = CCclone[entryhost.getKey()][entry.getKey()];
					 k = entryhost.getKey();
					 b = false;
					 break;
				}
			}
			if(b){
				System.out.println("cette processus "+ entry.getValue().getId() +" n'a pas d host");
			}else{
				for(Entry<Integer,Host> entryhost:hosts.entrySet()){
					if((entryhost.getValue().getQtRessource() >= entry.getValue().getRequiredRessources())&&(entryhost.getValue().getQtCommunication() >= entry.getValue().getRequiredCommunication()) && ( CCclone[entryhost.getKey()][entry.getKey()] < minCout ) )  {
						 minCout = CCclone[entryhost.getKey()][entry.getKey()];
						 k=entryhost.getKey();
						 
					}
				}
				//affectation de process au meilleur place et en fais la mise a jour de ressource de host!!
				hosts.get(k).getProcesses().add(entry.getValue());
				
				double c = hosts.get(k).getQtRessource();
				hosts.get(k).setQtRessource(c-entry.getValue().getRequiredRessources());
				double p = hosts.get(k).getQtCommunication();
				hosts.get(k).setQtCommunication(p-entry.getValue().getRequiredCommunication());
				solution[k][entry.getKey()] = 1;
				
				
				
			}
			
		}
		
		Solution sol = new Solution(hosts,solution);
		
		double CE = CalculeCEtotale(solution);
		double CC = CalculeCCtotale(solution);
		System.out.println("Le CE de Solution:"+CE);
		System.out.println("Le CC de Solution:"+CC);
		sol.setCCtotale(CC);
		sol.setCEtotale(CE);
		
		return sol;
	}
	/********************************************************************************************
	 * 					      ** solution initial version 3 ** 									*
	 ********************************************************************************************/
	public static Solution SolutionInitialV3(HashMap<Integer,Server> servers, HashMap<Integer,Process> processus){
		
		int[][] solution = new int[servers.size()][processus.size()];
		HashMap<Integer,Host>  hosts = new HashMap<Integer,Host>();
		
		for(Entry<Integer,Server> entry : servers.entrySet()){
			hosts.put(entry.getKey(),new Host(entry.getValue()));
		}
		
		for(Entry<Integer,Process> entry:processus.entrySet()){
			boolean b = true;
			int k = 0;
			double minCout = 0;
			//double[][] CEclone = CEclone;
			
			for(Entry<Integer,Host> entryhost:hosts.entrySet()){
				if(entryhost.getValue().getQtRessource() >= entry.getValue().getRequiredRessources()&&(entryhost.getValue().getQtCommunication() >= entry.getValue().getRequiredCommunication()) ){
					 minCout = CEclone[entryhost.getKey()][entry.getKey()];
					 k = entryhost.getKey();
					 b = false;
					 break;
				}
			}
			if(b){
				System.out.println("cette processus "+ entry.getValue().getId() +" n'a pas d host");
			}else{
				for(Entry<Integer,Host> entryhost:hosts.entrySet()){
					if((entryhost.getValue().getQtRessource() >= entry.getValue().getRequiredRessources())&&(entryhost.getValue().getQtCommunication() >= entry.getValue().getRequiredCommunication()) && ( CEclone[entryhost.getKey()][entry.getKey()] < minCout ) )  {
						 minCout = CEclone[entryhost.getKey()][entry.getKey()];
						 k=entryhost.getKey();						 
					}
				}
				//affectation de process au meilleur place et en fais la mise a jour de ressource de host!!
				hosts.get(k).getProcesses().add(entry.getValue());
				
				double c = hosts.get(k).getQtRessource();
				hosts.get(k).setQtRessource(c-entry.getValue().getRequiredRessources());
				
				double p = hosts.get(k).getQtCommunication();
				hosts.get(k).setQtCommunication(p-entry.getValue().getRequiredCommunication());
				
				solution[k][entry.getKey()] = 1;				
			}			
		}
		Solution sol = new Solution(hosts,solution);
		double CE = CalculeCEtotale(solution);
		double CC = CalculeCCtotale(solution);
		System.out.println("Le CE de Solution:"+CE);
		System.out.println("Le CC de Solution:"+CC);
		sol.setCCtotale(CC);
		sol.setCEtotale(CE);
		return sol;
	}
	
	/********************************************************************************************
	 * 					      ** calcule de cout de communication totale 3 ** 					*
	 ********************************************************************************************/
	public static double CalculeCCtotale(int[][] solution){
		double[][] tabCout = RessourceManager.GetCCinital();
		double coutTotale=0;
		for(int i=0;i<solution.length;i++){
			for(int j=0;j<solution[i].length;j++){
				if(solution[i][j]==1)
					coutTotale += tabCout[i][j];
				
			}
		}
		return coutTotale;
	}
	
	
	/********************************************************************************************
	 * 					      ** calcule de cout d'execution  totale 3 ** 					    *
	 ********************************************************************************************/

	public static double CalculeCEtotale(int[][] solution){
		double[][] tabCout = RessourceManager.GetCEinital();
		double coutTotale=0;
		for(int i=0;i<solution.length;i++){
			for(int j=0;j<solution[i].length;j++){
				if(solution[i][j]==1)
					coutTotale += tabCout[i][j];
				
			}
		}
		return coutTotale;
	}
	/********************************************************************************************
	 * 					          ** affichage des serveur vide ** 					            *
	 ********************************************************************************************/
	
	public static void alertServerVide(HashMap<Integer,Host> hosts){
		for(Entry<Integer,Host> entry : hosts.entrySet()){
			if(entry.getValue().getProcesses().size()==0){
				System.out.println("le server id="+entry.getKey()+" est vide");
			}
		}
	}
}
