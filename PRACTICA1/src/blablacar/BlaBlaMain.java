package src.blablacar;

import src.blablacar.BlaBlaEstado;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import IA.Comparticion.*;

import java.util.HashSet;
import aima.basic.XYLocation;
import aima.search.framework.HeuristicFunction;
import aima.search.framework.Problem;
import aima.search.framework.Search;
import aima.search.framework.SearchAgent;
import aima.search.informed.HillClimbingSearch;
import aima.search.informed.SimulatedAnnealingSearch;


public class BlaBlaMain {


	public static void main (String[] args) {

		
		Scanner s = new Scanner(System.in);

		System.out.print("Introduce la Semilla: ");
		int seed = s.nextInt();


		System.out.print("Introduce el numero de conductores: ");
		int conductores = s.nextInt();


		System.out.print("Introduce el numero de usuarios: ");
		int usuarios = s.nextInt();

		System.out.println("Que solucion inicial quieres usar?");
		System.out.println("Modo 0: Greedy");
		System.out.println("Modo 1: Random");

		int modo = s.nextInt();

		Usuarios users = new Usuarios(usuarios, conductores, seed);

/*
		for (int i = 0; i < 100; ++i) {
			Usuarios u = new Usuarios(5000, 3000, i*373);
			System.out.println("Solución número: " + Integer.toString(i));
			System.out.println("--------------------------------------");
			for (Usuario us: u) {
				System.out.println("(" + Integer.toString(us.getCoordOrigenX())  + "," + Integer.toString(us.getCoordOrigenY()) + ")" + "(" + Integer.toString(us.getCoordDestinoX())  + "," + Integer.toString(us.getCoordDestinoY()) + ")"  );
			}
			
		}  
*/
		BlaBlaEstado estadoInicial = new BlaBlaEstado(users, modo);

		estadoInicial.escribir_ruta();
		
		BlaBlaHeuristicFunctions heu = new BlaBlaHeuristicFunctions();
		heu.ChangeHeuristicFunction(1);
		double suma_distancias_og = heu.getHeuristicValue(estadoInicial);
	
		Search alg = new HillClimbingSearch();
		Problem p = new Problem(estadoInicial, new BlaBlaSuccessors(), new BlaBlaGoalTest(), heu);
		double start = 0;
		double finish = 0;
		try {
			start = System.currentTimeMillis();
			SearchAgent ag = new SearchAgent(p, alg);
			finish = System.currentTimeMillis();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		
		BlaBlaEstado goal = (BlaBlaEstado) alg.getGoalState();
		System.out.println("NOVA SOLUCIÓ --------------------------------");
		goal.escribir_ruta();
		goal.checkDistances();
		double suma_distancias_def = heu.getHeuristicValue(goal);


		System.out.println("Suma de distancias inicial: " + suma_distancias_og);
		System.out.println("Numero de cars inicial: " + estadoInicial.getNumCars());
		System.out.println("Suma de distancias final: " + suma_distancias_def);
		System.out.println("Numero de cars final: " + goal.getNumCars());
		System.out.println("Tiempo de escalado de monte: " + (finish - start));
		//Ahora magia
		
	}

}

