package aplicacio;

import java.io.*;

import dades.Laberint;
import dades.SolLabAvid;
import dades.SolLabCercaEx;

public class SolLabAplicacio {

	public static void main(String[] args) throws FileNotFoundException {
		
		Laberint laberint = new Laberint("exemple");
		
		System.out.println(laberint.toString());
		
		//boolean[][] solucio;
		
		/*SolLabAvid solAvid = new SolLabAvid(laberint);
		solucio = solAvid.trobarCamiSol();
		
		SolLabCercaEx solCercaEx = new SolLabCercaEx(laberint);
		solucio = solCercaEx.trobarCamiSol();*/
		
		
	}
}
