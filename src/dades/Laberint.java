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
	
	/**
	 * Mètode que retorna el valor de la casella d'inici sense tenir en compte l'operand
	 * @return el valor de la casella d'inici
	 */
	public float getValorInici() {
		String casella = laberint[fi][ci];
		return Character.getNumericValue(casella.charAt(1));
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

	public float calcularPuntuacio (float puntsActuals, int fil, int col) {
		float resultat = -1;

		if (fil >= 0 && fil < laberint.length && col >= 0 && col < laberint[0].length) {
			if (!(laberint[fil][col].equals("NA")) && !(laberint[fil][col].equals("0"))) {
				String casella = laberint[fil][col];
				char operador = casella.charAt(0);
				int valor = Character.getNumericValue(casella.charAt(1));
				switch (operador) {
				case 42: // multiplicació
					resultat = puntsActuals * valor;
					break;

				case 43: // suma
					resultat = puntsActuals + valor;
					break;

				case 45: // resta
					resultat = puntsActuals - valor;
					break;

				case 47: // divisió (el denominador mai podra ser negatiu ni 0)
					resultat = puntsActuals / valor;
					break;	
				}
			}
		}
		return resultat;
	}

	/**
	 * Mètode per marca la posició passada per paràmetre com utilitzada
	 * @param fAct
	 * @param cAct
	 */
	public void marcarCasellaUtilitzada(int fAct, int cAct) {
		laberint[fAct][cAct] = "0";
	}
}
