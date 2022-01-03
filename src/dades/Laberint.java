package dades;

import java.io.*;
import java.util.Scanner;

public class Laberint {
	private String[][] laberint;
	private int files, columnes, fi, ci, ff, cf;
	
	public Laberint (String nomFitxer) throws FileNotFoundException {
		carregaFitxer(nomFitxer);
	}
	
	private void carregaFitxer (String nomFitxer) throws FileNotFoundException {
		Scanner f = new Scanner(new File(nomFitxer + ".txt"));
		String[] aux;
		
		aux = f.nextLine().split(",");
		files = Integer.parseInt(aux[0]);
		columnes = Integer.parseInt(aux[1]);
		fi = Integer.parseInt(aux[2]);	//Fila inicial
		ci = Integer.parseInt(aux[3]);	//Columna inicial
		ff = Integer.parseInt(aux[4]);	//Fila final
		cf = Integer.parseInt(aux[5]);	//Columna final
		
		laberint = new String[files][columnes];
		for(int i=0; i<files; i++) {
			aux = f.nextLine().split(",");
			for(int j=0; j<columnes; j++) {
				laberint[i][j] = aux[j];
			}
		}
	}

	/**
	 * @return the files
	 */
	public int getFiles() {
		return files;
	}

	/**
	 * @return the columnes
	 */
	public int getColumnes() {
		return columnes;
	}

	/**
	 * @return the fi
	 */
	public int getFi() {
		return fi;
	}

	/**
	 * @return the ci
	 */
	public int getCi() {
		return ci;
	}

	/**
	 * @return the ff
	 */
	public int getFf() {
		return ff;
	}

	/**
	 * @return the cf
	 */
	public int getCf() {
		return cf;
	}
	
	public String getPos(int f, int c) {
		return laberint[f][c];
	}
	
	public String toString() {
		String aux = "Laberint:\n";
		for(int i=0; i<files; i++) {
			for(int j=0; j<columnes; j++) {
				aux += (laberint[i][j]+" ");
			}
			aux += "\n";
		}
		return aux;
	}
	
}
