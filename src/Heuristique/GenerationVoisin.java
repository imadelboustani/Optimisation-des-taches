package Heuristique;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import Infrastructure.*;
import Infrastructure.Process;
import static Infrastructure.Shared.randomWithRange;

public class GenerationVoisin {
	private double[][] TabCC = RessourceManager.GetCCinital();
	private double[][] TabCE = RessourceManager.GetCEinital();
	private int hostNombre;
	
	
	
	
	public GenerationVoisin(int hotsNombre) {
		super();
		this.hostNombre = hotsNombre;
	}


	/************************************************************************************
	 * 				*** generation d'un solution voisin ***								*
	 ************************************************************************************/
	public  SolutionVoisin  generateVoisin(Solution sol){
		int i,j,n,m;
		do{
			i = randomWithRange(0, this.hostNombre);
			j = randomWithRange(0, this.hostNombre);
			if(i == j || sol.getHosts().get(i).getProcesses().size()==0 || sol.getHosts().get(j).getProcesses().size()==0)
				continue;
			 n = randomWithRange(0, sol.getHosts().get(i).getProcesses().size());
			 m = randomWithRange(0, sol.getHosts().get(j).getProcesses().size());
			 boolean critere1 =sol.getHosts().get(i).getQtRessource() + sol.getHosts().get(i).getProcesses().get(n).getRequiredRessources() >= sol.getHosts().get(j).getProcesses().get(m).getRequiredRessources();
			 boolean critere2 =sol.getHosts().get(j).getQtRessource() + sol.getHosts().get(j).getProcesses().get(m).getRequiredRessources() >= sol.getHosts().get(i).getProcesses().get(n).getRequiredRessources();
			 boolean critere3 =sol.getHosts().get(i).getQtCommunication() + sol.getHosts().get(i).getProcesses().get(n).getRequiredCommunication() >= sol.getHosts().get(j).getProcesses().get(m).getRequiredCommunication();
			 boolean critere4 =sol.getHosts().get(j).getQtCommunication() + sol.getHosts().get(j).getProcesses().get(m).getRequiredCommunication() >= sol.getHosts().get(i).getProcesses().get(n).getRequiredCommunication();
			if(critere1 && critere2 && critere3 && critere4){
				break;
			}
		}while(true);
		
		int[] tab ={i, /** host id  **/
				sol.getHosts().get(i).getProcesses().get(n).getId(), /** process id  **/
				n,/** la position de cette proce en List **/
				j,/** host id  **/
				sol.getHosts().get(j).getProcesses().get(m).getId(),/** process id  **/
				m /** la position de cette proce en List **/
				};
		
//		int[][] solution = new int[sol.getSolution().length][sol.getSolution()[0].length];
//		for(int k=0;k<solution.length;k++){
//			for(int l=0;l<solution[0].length;l++)
//				solution[k][l] = sol.getSolution()[k][l];
//		}
//		solution[i][sol.getHosts().get(i).getProcesses().get(n).getId()] = 0;
//		solution[i][sol.getHosts().get(j).getProcesses().get(m).getId()] = 1;
//		solution[j][sol.getHosts().get(j).getProcesses().get(m).getId()] = 0;
//		solution[j][sol.getHosts().get(i).getProcesses().get(n).getId()] = 1;
		

		
//		System.out.println("1ere Server : "+tab[0]+" 2eme Server "+tab[2]);
//		
//		for(int p=0;p<solution.length;p++){
//			System.out.println(Arrays.toString(solution[p]));
//		}
	
		
		SolutionVoisin solv = new SolutionVoisin(sol.getSolution(),tab);
		double CEtotaleV = sol.getCEtotale();//-(TabCE[tab[0]][tab[1]] + TabCE[tab[3]][tab[4]])+(TabCE[tab[0]][tab[4]] + TabCE[tab[3]][tab[1]]);
		double CCtotaleV = sol.getCCtotale();//-(TabCC[tab[0]][tab[1]] + TabCC[tab[3]][tab[4]])+(TabCC[tab[0]][tab[4]] + TabCC[tab[3]][tab[1]]);
		solv.setCC(CCtotaleV);
		solv.setCE(CEtotaleV);
//		System.out.println("le CE:"+CEtotaleV);
//		System.out.println("Le CC:"+CCtotaleV);
		return solv;		
						
	}
	
	
	/************************************************************************************
	 * 				*** generation les voisin d'un solution ***							*
	 ************************************************************************************/
	public  HashSet<SolutionVoisin> generateGroupVoisin(Solution sol,int pourcentage){
		HashSet<SolutionVoisin> groupVoisin = new HashSet<SolutionVoisin>();
		int nombreprocess = sol.getSolution()[0].length;
		int nombreVoisin = 	(nombreprocess*(nombreprocess-1)*pourcentage)/(2*100);
		for(int i=0;i<nombreVoisin;i++){
			//System.out.println("voisin :"+ i );
			SolutionVoisin solv = generateVoisin(sol);		
			groupVoisin.add(solv);
		}
		return groupVoisin;
	}
	public void swapValueInArray(SolutionVoisin sol){
		sol.getSolution()[sol.getTab()[0]][sol.getTab()[1]] = 0;
		sol.getSolution()[sol.getTab()[0]][sol.getTab()[4]] = 1;
		sol.getSolution()[sol.getTab()[3]][sol.getTab()[4]] = 0;
		sol.getSolution()[sol.getTab()[3]][sol.getTab()[1]] = 1;
		double CEtotaleV = sol.getCE()-(TabCE[sol.getTab()[0]][sol.getTab()[1]] + TabCE[sol.getTab()[3]][sol.getTab()[4]])+(TabCE[sol.getTab()[0]][sol.getTab()[4]] + TabCE[sol.getTab()[3]][sol.getTab()[1]]);
		double CCtotaleV = sol.getCC()-(TabCC[sol.getTab()[0]][sol.getTab()[1]] + TabCC[sol.getTab()[3]][sol.getTab()[4]])+(TabCC[sol.getTab()[0]][sol.getTab()[4]] + TabCC[sol.getTab()[3]][sol.getTab()[1]]);
		sol.setCC(CCtotaleV);
		sol.setCE(CEtotaleV);
	}
	
}
