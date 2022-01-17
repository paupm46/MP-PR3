package dades;

import java.io.*;
import java.util.Scanner;

public class Laberint implements Cloneable {
	private String[][] laberint;
	private int files, columnes, fi, ci, ff, cf;
	
	/**
	 * Contructor amb paràmetres que carrega un fitxer
	 * @param nomFitxer - nom del fitxer a carregar
	 * @throws FileNotFoundException
	 */
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
	 * getFiles
	 * @return nombre de files
	 */
	public int getFiles() {
		return files;
	}

	/**
	 * getColumnes
	 * @return nombre de columnes
	 */
	public int getColumnes() {
		return columnes;
	}

	/**
	 * getFi (getter fila d'inici)
	 * @return fila d'inici
	 */
	public int getFi() {
		return fi;
	}

	/**
	 * getCi (getter columna d'inici)
	 * @return columna d'inici
	 */
	public int getCi() {
		return ci;
	}

	/**
	 * getFf (getter fila fi)
	 * @return fila final
	 */
	public int getFf() {
		return ff;
	}

	/**
	 * getCf (getter columna fi)
	 * @return columna final
	 */
	public int getCf() {
		return cf;
	}
	
	/**
	 * Mètode que retorna el contingut d'una posició
	 * @param f - fila
	 * @param c - columna
	 * @return String amb el contingut de la posició passada per paràmetre
	 */
	public String getPos(int f, int c) {
		return laberint[f][c];
	}

	/**
	 * toString
	 */
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

	/**
	 * 
	 * @param puntsActuals - puntuacio actual
	 * @param fil - fila
	 * @param col - columna
	 * @return la nova puntuació o -puntsActuals en cas de no poder-la calcular
	 */
	public float calcularPuntuacio (float puntsActuals, int fil, int col) {
		float resultat = -puntsActuals;

		if (fil >= 0 && fil < laberint.length && col >= 0 && col < laberint[0].length) {
			if (!(laberint[fil][col].equals("NA"))) {
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
	
	public String getValorCasella(int fAct, int cAct) {
		return (laberint[fAct][cAct]);
	}
	
	/**
	 * Mètode que retorna el valor de la casella d'inici sense tenir en compte l'operand
	 * @return el valor de la casella d'inici
	 */
	public float getValorInici() {
		String casella = laberint[fi][ci];
		return Character.getNumericValue(casella.charAt(1));
	}
}
