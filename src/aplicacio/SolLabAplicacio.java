package aplicacio;

import java.io.*;

import dades.Laberint;
import dades.SolLabAvid;
import dades.SolLabCercaEx;

public class SolLabAplicacio {

	public static void main(String[] args) throws FileNotFoundException {
		
		Laberint laberint = new Laberint("exemple");
		
		System.out.println(laberint.toString());
		
		boolean[][] solucio;		
		
		SolLabAvid solAvid = new SolLabAvid(laberint);
		solucio = solAvid.trobarCamiSol();
		if (solucio != null) {
			System.out.println("La solució es la següent:");
			for (int i = 0; i < solucio.length; i++) {
				for (int j = 0; j < solucio[0].length; j++){
					if (solucio[i][j] ==  true) {
						System.out.print(" -> ");
					} else {
						System.out.print(" -- ");
					}
				}
				System.out.println();
			}
			System.out.println("Puntuació obtinguda: " + solAvid.getPuntuacio());
		} else {
			System.out.println("No te solució");
		}
		
		/*
		SolLabCercaEx solCercaEx = new SolLabCercaEx(laberint);
		solucio = solCercaEx.trobarCamiSol();
		*/	
	}
}
