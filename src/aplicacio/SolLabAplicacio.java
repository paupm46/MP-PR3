package aplicacio;

import java.io.*;
import java.util.Scanner;

import dades.Laberint;
import dades.SolLabAvid;
import dades.SolLabCercaEx;

/**
 * Programa principal Camins en un laberint
 * 
 * @author Pau Piñol
 * @author Javier Quiles
 * @author Maria Richer
 * @version 1.0
 */

public class SolLabAplicacio {
	
	static Scanner teclat = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		
		int opcio;
		boolean sortir=false;
		
		do {
			do {
				mostraMenu();
				opcio = Integer.parseInt(teclat.nextLine());
			}while(opcio!=1 && opcio!=2 && opcio!=3 && opcio!=4 && opcio!=5);

			switch(opcio) {
			case 1:
				mostrarSol();
				break;
			case 2:
				jocDeProves();
				break;
			case 3:
				testAutomaticTemps();
				break;
			case 4:
				sortir = true;
				break;
			}
		} while(!sortir);
	} 
	
	private static void mostraMenu() {
		System.out.println("\nMenú:");
		System.out.println("1 - Mostrar solucions possibles indicant l'estrategia i fitxer");
		System.out.println("2 - Joc de proves: mostrar una solució (si n'hi ha) amb totes les estrategies i fitxers preparats");
		System.out.println("3 - Valoració cost/temps: mesurar i mostrar el temps que es tarda a trobar les solucions amb totes les estrategies i fitxers preparats");
		System.out.println("4 - Sortir");
	}
	
	private static void jocDeProves() throws FileNotFoundException {
		SolLabAvid solAvid;
		SolLabCercaEx solCercaEx;
		Laberint laberint;
		
		System.out.println("Joc de proves");
		
		for(int num=1; num<=7; num++) {
			laberint = new Laberint("exemple"+num);
			System.out.println("----------");
			System.out.println("Exemple " + num);
			System.out.println("----------");
			// Àvid
			System.out.println("Àvid");
			solAvid = new SolLabAvid(laberint);
			int[][] solucio = solAvid.trobarCamiSol();
			if (solucio != null) {
				System.out.println("La solució es la següent:");
				System.out.println("Puntuació obtinguda: " + solAvid.getPuntuacio());
				for (int i = 0; i < solucio.length; i++) {
					for (int j = 0; j < solucio[0].length; j++){
						System.out.print(solucio[i][j] + "\t");				
					}
					System.out.print("\n\n");
				}
			} else {
				System.out.println("No te solució");
			}
			
			// Cerca exhaustiva
			System.out.println("\nCerca exhaustiva");
			solCercaEx = new SolLabCercaEx(laberint);
			int [][][] solucionsCercaEx = solCercaEx.trobarCamiSol();
			if (solCercaEx.teSolucio()) {
				System.out.println("En total ha trobat " + solCercaEx.getnSol() + " solucions");
				
				System.out.println("La primera solució es la següent:");
				System.out.println("Puntuació obtinguda: " + solCercaEx.getPuntuacio(0));
				for (int i = 0; i < solucionsCercaEx[0].length; i++) {
					for (int j = 0; j < solucionsCercaEx[0][0].length; j++){							
						System.out.print(solucionsCercaEx[0][i][j] + "\t");
					}
					System.out.print("\n\n");
				}
				System.out.println();
			} else {
				System.out.println("No te solució");
			}
		}
	}
	
	private static void testAutomaticTemps() throws FileNotFoundException {
		SolLabAvid solAvid;
		SolLabCercaEx solCercaEx;
		Laberint laberint;
		long tempsi, tempsf;
		
		System.out.println("Estratègia utilitzant un algorisme àvid");
		
		for(int i=1; i<=5; i++) {
			laberint = new Laberint("exemple"+i);
			solAvid = new SolLabAvid(laberint);
			tempsi=System.nanoTime();
			solAvid.trobarCamiSol();
			tempsf=System.nanoTime();
			System.out.println("Algorisme àvid amb fitxer exemple"+i+" ha tardat "+(tempsf-tempsi)+" ns");
		}
		
		
		System.out.println("\nEstratègia utilitzant un algorisme de cerca exhaustiva amb ramificació i poda");
		
		for(int i=1; i<=5; i++) {
			laberint = new Laberint("exemple"+i);
			solCercaEx = new SolLabCercaEx(laberint);
			tempsi=System.nanoTime();
			solCercaEx.trobarCamiSol();
			tempsf=System.nanoTime();
			System.out.println("Algorisme cerca exhaustiva amb fitxer exemple"+i+" ha tardat "+(tempsf-tempsi)+" ns");
		}
	}
	
	private static void mostrarSol() throws FileNotFoundException {
		String fitxer;
		int opcio;
		Laberint laberint;
		
		do {
			System.out.println("Quina estratègia vols utilitzar?");
			System.out.println("1 - Algorisme àvid");
			System.out.println("2 - Algorisme cerca exhaustiva");
			opcio = Integer.parseInt(teclat.nextLine());
		}while(opcio!=1 && opcio!=2);
		
		System.out.println("Introdueix el fitxer a utilitzar:");
		fitxer = teclat.nextLine();
		
		laberint = new Laberint(fitxer);
		
		switch(opcio) {
		case 1:
			SolLabAvid solAvid = new SolLabAvid(laberint);
			int[][] solucio = solAvid.trobarCamiSol();
			if (solucio != null) {
				System.out.println("La solució es la següent:");
				System.out.println("Puntuació obtinguda: " + solAvid.getPuntuacio());
				for (int i = 0; i < solucio.length; i++) {
					for (int j = 0; j < solucio[0].length; j++){
						System.out.print(solucio[i][j] + "\t");				
					}
					System.out.print("\n\n");
				}
			} else {
				System.out.println("No te solució");
			}
			break;
		
		case 2:
			SolLabCercaEx solCercaEx = new SolLabCercaEx(laberint);
			int[][][] solucionsCercaEx = solCercaEx.trobarCamiSol();
			if (solCercaEx.teSolucio()) {
				System.out.println("Solucions possibles:");
				for (int sol=0; sol<solCercaEx.getnSol(); sol++) {
					System.out.println("Solució "+(sol+1)+":");
					System.out.println("Puntuació obtinguda: " + solCercaEx.getPuntuacio(sol));
					for (int i = 0; i < solucionsCercaEx[0].length; i++) {
						for (int j = 0; j < solucionsCercaEx[0][0].length; j++){
							System.out.print(solucionsCercaEx[sol][i][j] + "\t");
						}
						System.out.print("\n\n");
					}
					System.out.println();
				}
			} else {
				System.out.println("No te solució");
			}
			break;
		}
	}
	
	
	
	
	
	
	
	
	
}










