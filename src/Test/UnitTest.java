package Test;

import Infrastructure.*;
import Infrastructure.Process;

import com.google.gson.Gson;

import Heuristique.*;
import static Infrastructure.Shared.randomWithRange;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;


public class UnitTest {
    public static void main(String[] args){
    	long debut = System.currentTimeMillis();
    	RecherchTabou RT = new RecherchTabou();
    	/***            nbr_iteration   nbr_iteration_sans amélioration   tabou.length         ***/
    	SolutionFinal sf = RT.recherchTabou(10000,100,100);
    	long fin = System.currentTimeMillis();
    	
    	/** affichage **/
    	System.out.println();
    	System.out.println("cout d'éxécution         ecart CE        cout de communication         ecart CC         le temps d'exécution");
    	System.out.println(sf.getBestCEever()+"                  "+(sf.getCEinitial()-sf.getBestCEever())+"               "+sf.getBestCCever()+"               "+(sf.getCCinitial()-sf.getBestCCever())+"                 "+Long.toString(fin - debut));
//    	System.out.println("le CE de dernier solution:"+sf.getSolutionFinal().getCEtotale());
//    	System.out.println("le CC de dernier solution:"+sf.getSolutionFinal().getCCtotale());
    	
    	/** l'écriture de soluiton dans fchier .json **/
    	try {
			PrintWriter sortie = new PrintWriter(new FileWriter("src/Infrastructure/Cas_5.json",true));
			Gson gson = new Gson(); 
			sortie.print(gson.toJson(sf.getBests().get(0)));
			sortie.print(gson.toJson(sf.getBests().get(1)));
			sortie.println();
			sortie.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
