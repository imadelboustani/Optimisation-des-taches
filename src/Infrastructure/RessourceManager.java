package Infrastructure;

import static Infrastructure.Shared.randomWithRange;
import static Infrastructure.Shared.randomWithRange2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class RessourceManager {
	 static Gson gson = new Gson();
	     
	
	/******************************************************************************************
	 * 						***** la fonction qui genere les ressource ***** 				  *
	 ******************************************************************************************/
	public  void generateur(){
		int n=100, m=500;
		 /** Server **/
		int maxProcessingPower = 2,minProcessingPower=10; //server Processing Power range!
		int minRessources = 1000, maxRessources = 100000; //server ressources range!
		int minqtcommunication = 1, maxqtcommunication = 10000;
		int mindebit = 40, maxdebit = 70;
			  /** proces **/
		int minWorkload =50, maxWorkload = 100;
		int minRequiredRessources = 1, maxRequiredRessources = 500;
		int minRequiredcommunication = 1, maxRequiredcommunication = 100;
		
		/*** 						**	generation des servers 	**							***/		
		try {
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/servers.json"));
		for (int i=0; i<n ; i++) {
	            Server rand = new Server(i, randomWithRange(minProcessingPower, maxProcessingPower), randomWithRange(minRessources, maxRessources), randomWithRange(minqtcommunication, maxqtcommunication),randomWithRange(mindebit,maxdebit));
	            sortie.println(gson.toJson(rand));				
			}
			sortie.close();
		} catch (IOException e) {
				e.printStackTrace();
			}
            
	/***						**	generation des Processes 	**									 ***/
	 	try {
	 		PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/processes.json"));
	 		for (int i=0; i<m ; i++) {
		 		Gson gson = new Gson();
		 		Process rand = new Process(i, randomWithRange(minWorkload, maxWorkload), randomWithRange(minRequiredRessources, maxRequiredRessources),randomWithRange(minRequiredcommunication, maxRequiredcommunication));				
				sortie.println(gson.toJson(rand));				
			}
		 	sortie.close();
	 		}
			catch (IOException e) {
				e.printStackTrace();
			}
            
	 	/**                   ***génération des cout***                      **/
	 	HashMap<Integer,Server> servers = GetServers();
		HashMap<Integer,Process> processus = GetProcesses();
	 	
	   /** 						**	generation de cout de communication  **  					**/
		double[][] CCinitiale = new double[n][m];
		for(int i=0; i<n;i++){
			for(int j=0; j<m;j++){
				CCinitiale[i][j] = Double.parseDouble(String.format( "%.2f",servers.get(i).getDebit()*processus.get(j).getRequiredCommunication())); 				
			}
		}
		try {
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/CCinitial.json"));
			sortie.println(gson.toJson(CCinitiale));
			sortie.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 /***                       **	generation des cout d'execution  **       					  ***/

		double[][] CEinitiale = new double[n][m];
		
		for(int i=0; i<n;i++){
			for(int j=0; j<m;j++){
				CEinitiale[i][j] = Double.parseDouble(String.format( "%.2f",servers.get(i).getProcessingPower()*processus.get(j).getWorkLoad())); 				
			}
		}
		try {
			
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/CEinitial.json"));
			sortie.println(gson.toJson(CEinitiale));
			sortie.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public   int ajoutProcessServer(){
		 /** Server **/
		int n=100, m =500;
		HashMap<Integer,Server> servers = GetServers();
		HashMap<Integer,Process> processus = GetProcesses();
		System.out.println(servers.size());
		if(servers.size()>10000){
			System.out.println("vous avez dépassé le max des serveur (10 000)");
			return 0;
		}
		if(processus.size()>50000){
			System.out.println("vous avez dépassé le max des process (50 000)");
			return 0;
		}
		
		int maxProcessingPower = 2,minProcessingPower=10; //server Processing Power range!
		int minRessources = 1000, maxRessources = 100000; //server ressources range!
		int minqtcommunication = 1, maxqtcommunication = 10000;
		int mindebit = 40, maxdebit = 70;
			  /** proces **/
		int minWorkload =50, maxWorkload = 100;
		int minRequiredRessources = 1, maxRequiredRessources = 500;
		int minRequiredcommunication = 1, maxRequiredcommunication = 100;
		
		/*** 						**	generation des servers 	**							***/		
		try {
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/servers.json",true));
		for (int i=servers.size(); i<n+servers.size(); i++) {
	            Server rand = new Server(i, randomWithRange(minProcessingPower, maxProcessingPower), randomWithRange(minRessources, maxRessources), randomWithRange(minqtcommunication, maxqtcommunication),randomWithRange(mindebit,maxdebit));
	            sortie.println(gson.toJson(rand));				
			}
			sortie.close();
		} catch (IOException e) {
				e.printStackTrace();
			}
           
	/***						**	generation des Processes 	**									 ***/
	 	try {
	 		PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/processes.json",true));
	 		for (int i=processus.size(); i<m+processus.size(); i++) {
		 		Gson gson = new Gson();
		 		Process rand = new Process(i, randomWithRange(minWorkload, maxWorkload), randomWithRange(minRequiredRessources, maxRequiredRessources),randomWithRange(minRequiredcommunication, maxRequiredcommunication));				
				sortie.println(gson.toJson(rand));				
			}
		 	sortie.close();
	 		}
			catch (IOException e) {
				e.printStackTrace();
			}
           
	 	/**                   ***génération des cout***                      **/
	 	servers = GetServers();
		processus = GetProcesses();
	 	
	   /** 						**	generation de cout de communication  **  					**/
		double[][] CCinitiale = new double[servers.size()][processus.size()];
		for(int i=0; i<servers.size();i++){
			for(int j=0; j<processus.size();j++){
				CCinitiale[i][j] = Double.parseDouble(String.format( "%.2f",servers.get(i).getDebit()*processus.get(j).getRequiredCommunication())); 				
			}
		}
		try {
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/CCinitial.json"));
			sortie.println(gson.toJson(CCinitiale));
			sortie.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 /***                       **	generation des cout d'execution  **       					  ***/

		double[][] CEinitiale = new double[servers.size()][processus.size()];
		
		for(int i=0; i<servers.size();i++){
			for(int j=0; j<processus.size();j++){
				CEinitiale[i][j] = Double.parseDouble(String.format( "%.2f",servers.get(i).getProcessingPower()*processus.get(j).getWorkLoad())); 				
			}
		}
		try {
			
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/CEinitial.json"));
			sortie.println(gson.toJson(CEinitiale));
			sortie.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 1;
		
	}
	public  void generateur2(){
		int n=100, m=500;
		 /** Server **/
		int maxProcessingPower = 2,minProcessingPower=10; //server Processing Power range!
		int minRessources = 99998, maxRessources = 100000; //server ressources range!
		int minqtcommunication = 1, maxqtcommunication = 10000;
		int mindebit = 40, maxdebit = 70;
			  /** proces **/
		int minWorkload =50, maxWorkload = 100;
		int minRequiredRessources = 49996, maxRequiredRessources = 49998;
		int minRequiredcommunication = 1, maxRequiredcommunication = 100;
		
		/*** 						**	generation des servers 	**							***/		
		try {
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/servers.json"));
		for (int i=0; i<n ; i++) {
	            Server rand = new Server(i, randomWithRange(minProcessingPower, maxProcessingPower), randomWithRange(minRessources, maxRessources), randomWithRange(minqtcommunication, maxqtcommunication),randomWithRange(mindebit,maxdebit));
	            sortie.println(gson.toJson(rand));				
			}
			sortie.close();
		} catch (IOException e) {
				e.printStackTrace();
			}
            
	/***						**	generation des Processes 	**									 ***/
	 	try {
	 		PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/processes.json"));
	 		for (int i=0; i<m ; i++) {
		 		Gson gson = new Gson();
		 		if(i>m*0.5){
		 			minRequiredRessources = 100000;
		 			maxRequiredRessources = 100003;
		 			
		 		}
		 		Process rand = new Process(i, randomWithRange(minWorkload, maxWorkload), randomWithRange(minRequiredRessources, maxRequiredRessources),randomWithRange(minRequiredcommunication, maxRequiredcommunication));				
				sortie.println(gson.toJson(rand));				
			}
		 	sortie.close();
	 		}
			catch (IOException e) {
				e.printStackTrace();
			}
            
	 	/**                   ***génération des cout***                      **/
	 	HashMap<Integer,Server> servers = GetServers();
		HashMap<Integer,Process> processus = GetProcesses();
	 	
	   /** 						**	generation de cout de communication  **  					**/
		double[][] CCinitiale = new double[n][m];
		for(int i=0; i<n;i++){
			for(int j=0; j<m;j++){
				CCinitiale[i][j] = Double.parseDouble(String.format( "%.2f",servers.get(i).getDebit()*processus.get(j).getRequiredCommunication())); 				
			}
		}
		try {
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/CCinitial.json"));
			sortie.println(gson.toJson(CCinitiale));
			sortie.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 /***                       **	generation des cout d'execution  **       					  ***/

		double[][] CEinitiale = new double[n][m];
		
		for(int i=0; i<n;i++){
			for(int j=0; j<m;j++){
				CEinitiale[i][j] = Double.parseDouble(String.format( "%.2f",servers.get(i).getProcessingPower()*processus.get(j).getWorkLoad())); 				
			}
		}
		try {
			
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/CEinitial.json"));
			sortie.println(gson.toJson(CEinitiale));
			sortie.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	

	public  void generateur3(){
		int n=100, m=500;
		 /** Server **/
		int maxProcessingPower = 2,minProcessingPower=10; //server Processing Power range!
		int minRessources = 99998, maxRessources = 100000; //server ressources range!
		int minqtcommunication = 1, maxqtcommunication = 10000;
		int mindebit = 40, maxdebit = 70;
			  /** proces **/
		int minWorkload =50, maxWorkload = 100;
		int minRequiredRessources = 49996, maxRequiredRessources = 49998;
		int minRequiredcommunication = 1, maxRequiredcommunication = 100;
		
		/*** 						**	generation des servers 	**							***/		
		try {
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/servers.json"));
		for (int i=0; i<n ; i++) {
			if(i>n*0.5){
				minRessources = 9; maxRessources = 10;
			}
	            Server rand = new Server(i, randomWithRange(minProcessingPower, maxProcessingPower), randomWithRange(minRessources, maxRessources), randomWithRange(minqtcommunication, maxqtcommunication),randomWithRange(mindebit,maxdebit));
	            sortie.println(gson.toJson(rand));				
			}
			sortie.close();
		} catch (IOException e) {
				e.printStackTrace();
			}
            
	/***						**	generation des Processes 	**									 ***/
	 	try {
	 		PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/processes.json"));
	 		for (int i=0; i<m ; i++) {
		 		Gson gson = new Gson();
		 		if(i>m*0.5){
		 			minRequiredRessources = 100000;
		 			maxRequiredRessources = 100003;
		 			
		 		}
		 		Process rand = new Process(i, randomWithRange(minWorkload, maxWorkload), randomWithRange(minRequiredRessources, maxRequiredRessources),randomWithRange(minRequiredcommunication, maxRequiredcommunication));				
				sortie.println(gson.toJson(rand));				
			}
		 	sortie.close();
	 		}
			catch (IOException e) {
				e.printStackTrace();
			}
            
	 	/**                   ***génération des cout***                      **/
	 	HashMap<Integer,Server> servers = GetServers();
		HashMap<Integer,Process> processus = GetProcesses();
	 	
	   /** 						**	generation de cout de communication  **  					**/
		double[][] CCinitiale = new double[n][m];
		for(int i=0; i<n;i++){
			for(int j=0; j<m;j++){
				CCinitiale[i][j] = Double.parseDouble(String.format( "%.2f",servers.get(i).getDebit()*processus.get(j).getRequiredCommunication())); 				
			}
		}
		try {
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/CCinitial.json"));
			sortie.println(gson.toJson(CCinitiale));
			sortie.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 /***                       **	generation des cout d'execution  **       					  ***/

		double[][] CEinitiale = new double[n][m];
		
		for(int i=0; i<n;i++){
			for(int j=0; j<m;j++){
				CEinitiale[i][j] = Double.parseDouble(String.format( "%.2f",servers.get(i).getProcessingPower()*processus.get(j).getWorkLoad())); 				
			}
		}
		try {
			
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/CEinitial.json"));
			sortie.println(gson.toJson(CEinitiale));
			sortie.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public  void generateur4(){
		int n=100, m=500;
		 /** Server **/
		int maxProcessingPower = 2,minProcessingPower=10; //server Processing Power range!
		int minRessources = 99998, maxRessources = 100000; //server ressources range!
		int minqtcommunication = 100, maxqtcommunication = 10000;
		int mindebit = 40, maxdebit = 70;
			  /** proces **/
		int minWorkload =50, maxWorkload = 100;
		int minRequiredRessources =19997 , maxRequiredRessources = 19999;
		int minRequiredcommunication = 1, maxRequiredcommunication = 100;
		
		/*** 						**	generation des servers 	**							***/		
		try {
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/servers.json"));
		for (int i=0; i<n ; i++) {
	            Server rand = new Server(i, randomWithRange(minProcessingPower, maxProcessingPower), randomWithRange(minRessources, maxRessources), randomWithRange(minqtcommunication, maxqtcommunication),randomWithRange(mindebit,maxdebit));
	            sortie.println(gson.toJson(rand));				
			}
			sortie.close();
		} catch (IOException e) {
				e.printStackTrace();
			}
            
	/***						**	generation des Processes 	**									 ***/
	 	try {
	 		PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/processes.json"));
	 		for (int i=0; i<m ; i++) {
		 		Gson gson = new Gson();
		 		Process rand = new Process(i, randomWithRange(minWorkload, maxWorkload), randomWithRange(minRequiredRessources, maxRequiredRessources),randomWithRange(minRequiredcommunication, maxRequiredcommunication));				
				sortie.println(gson.toJson(rand));				
			}
		 	sortie.close();
	 		}
			catch (IOException e) {
				e.printStackTrace();
			}
            
	 	/**                   ***génération des cout***                      **/
	 	HashMap<Integer,Server> servers = GetServers();
		HashMap<Integer,Process> processus = GetProcesses();
	 	
	   /** 						**	generation de cout de communication  **  					**/
		double[][] CCinitiale = new double[n][m];
		for(int i=0; i<n;i++){
			for(int j=0; j<m;j++){
				CCinitiale[i][j] = Double.parseDouble(String.format( "%.2f",servers.get(i).getDebit()*processus.get(j).getRequiredCommunication())); 				
			}
		}
		try {
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/CCinitial.json"));
			sortie.println(gson.toJson(CCinitiale));
			sortie.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		 /***                       **	generation des cout d'execution  **       					  ***/

		double[][] CEinitiale = new double[n][m];
		
		for(int i=0; i<n;i++){
			for(int j=0; j<m;j++){
				CEinitiale[i][j] = Double.parseDouble(String.format( "%.2f",servers.get(i).getProcessingPower()*processus.get(j).getWorkLoad())); 				
			}
		}
		try {
			
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/CEinitial.json"));
			sortie.println(gson.toJson(CEinitiale));
			sortie.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	/*********************************************************************************************
	 * 						**	Recupération des Server en HashMap	**							 *	
	 *********************************************************************************************/
    public  HashMap<Integer,Server> GetServers(){
    	HashMap<Integer,Server> servers = new HashMap<Integer,Server>();
    	try {
			FileReader file = new FileReader("src/Infrastructure/servers.json");
			BufferedReader br = new BufferedReader(file);
			String line ;
			while((line = br.readLine()) != null){
					servers.put(gson.fromJson(line, Server.class).getId(),gson.fromJson(line, Server.class));					
			}
			br.close();			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return servers;
    }
	/*********************************************************************************************
	 * 						**	Recupération des processes en HashMap	**						 *	
	 *********************************************************************************************/
    public  HashMap<Integer,Process> GetProcesses(){
    	HashMap<Integer,Process> processus = new HashMap<Integer,Process>();
    	try {
			FileReader file = new FileReader("src/Infrastructure/processes.json");
			BufferedReader br = new BufferedReader(file);
			String line;
			while((line = br.readLine()) != null){			
				
					processus.put(gson.fromJson(line, Process.class).getId(),gson.fromJson(line, Process.class));
									
			}
			br.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return processus;
    }
    /*********************************************************************************************
	 * 			 **	generation de cout de communication apartir de gsonfile	**					 *	
	 *********************************************************************************************/
    public static double[][] GetCCinital(){
		 double[][] cout = null ;
		 Gson gson = new Gson();
		 try {
				FileReader file = new FileReader("src/Infrastructure/CCinitial.json");
				BufferedReader br = new BufferedReader(file);
				String line;
				while((line = br.readLine()) != null){			
					cout = gson.fromJson(line, double[][].class);				
				}
				br.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		 
		 return cout;
	 }
    /*********************************************************************************************
	 * 			 **	generation de cout d'execution apartir de gsonfile	**					 *	
	 *********************************************************************************************/
    public static double[][] GetCEinital(){
		 double[][] cout = null ;
		 Gson gson = new Gson();
		 try {
				FileReader file = new FileReader("src/Infrastructure/CEinitial.json");
				BufferedReader br = new BufferedReader(file);
				String line;
				while((line = br.readLine()) != null){			
						cout = gson.fromJson(line, double[][].class);					
				}
				br.close();				
			} catch (Exception e) {
				e.printStackTrace();
			}
		 
		 return cout;
	 }

}
