package aplicacio;

import java.io.*;
import java.util.Scanner;

import dades.Laberint;
import dades.SolLabAvid;
import dades.SolLabCercaEx;

public class SolLabAplicacio {
	
	static Scanner teclat = new Scanner(System.in);

	public static void main(String[] args) throws FileNotFoundException {
		
		/*Laberint laberint = new Laberint("exemple5");
		
		System.out.println(laberint.toString());
		
		int[][] solucio;
		int[][][] solucionsCercaEx;*/
		
		int opcio;
		boolean sortir=false;
		
		/*SolLabAvid solAvid = new SolLabAvid(laberint);
		solucio = solAvid.trobarCamiSol();
		if (solucio != null) {
			System.out.println("La solució es la següent:");
			for (int i = 0; i < solucio.length; i++) {
				for (int j = 0; j < solucio[0].length; j++){
					System.out.print(solucio[i][j] + "\t");				
				}
				System.out.print("\n\n");
			}
			System.out.println("Puntuació obtinguda: " + solAvid.getPuntuacio());
		} else {
			System.out.println("No te solució");
		}*/
		
		
		/*SolLabCercaEx solCercaEx = new SolLabCercaEx(laberint);
		solucionsCercaEx = solCercaEx.trobarCamiSol();
		System.out.println("Solucions possibles:"+solCercaEx.getnSol());
		if (solucionsCercaEx != null) {
			System.out.println("Solucions possibles:");
			for (int sol=0; sol<50; sol++) {
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
		}*/
		
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
	
	private static void testAutomaticTemps() throws FileNotFoundException {
		SolLabAvid solAvid;
		SolLabCercaEx solCercaEx;
		Laberint laberint;
		int[][] solucio;
		int[][][] solucionsCercaEx;
		long tempsi, tempsf;
		
		System.out.println("Estratègia utilitzant un algorisme àvid");
		
		for(int i=1; i<=5; i++) {
			laberint = new Laberint("exemple"+i);
			solAvid = new SolLabAvid(laberint);
			tempsi=System.nanoTime();
			solucio = solAvid.trobarCamiSol();
			tempsf=System.nanoTime();
			System.out.println("Algorisme àvid amb fitxer exemple"+i+" ha tardat "+(tempsf-tempsi)+" ns");
		}
		
		
		System.out.println("Estratègia utilitzant un algorisme de cerca exhaustiva amb ramificació i poda");
		
		for(int i=1; i<=5; i++) {
			laberint = new Laberint("exemple"+i);
			solCercaEx = new SolLabCercaEx(laberint);
			tempsi=System.nanoTime();
			solucionsCercaEx = solCercaEx.trobarCamiSol();
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
			System.out.println("3 - Algorisme genètic");
			opcio = Integer.parseInt(teclat.nextLine());
		}while(opcio!=1 && opcio!=2 && opcio!=3);
		
		System.out.println("Introdueix el fitxer a utilitzar:");
		fitxer = teclat.nextLine();
		
		laberint = new Laberint(fitxer);
		
		switch(opcio) {
		case 1:
			SolLabAvid solAvid = new SolLabAvid(laberint);
			int[][] solucio = solAvid.trobarCamiSol();
			if (solucio != null) {
				System.out.println("La solució es la següent:");
				for (int i = 0; i < solucio.length; i++) {
					for (int j = 0; j < solucio[0].length; j++){
						System.out.print(solucio[i][j] + "\t");				
					}
					System.out.print("\n\n");
				}
				System.out.println("Puntuació obtinguda: " + solAvid.getPuntuacio());
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










