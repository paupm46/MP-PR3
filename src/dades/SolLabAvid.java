package dades;

/**
 * Classe SolLabAvid per resoldre un laberint amb un algorisme àvid
 * @version 1.0
 */
public class SolLabAvid {

	private float puntuacio;
	private int[][] solucio;
	private Laberint laberint;
	private boolean teSolucio;

	/**
	 * Constructor SolLabAvid
	 * 
	 * @param laberint - laberint de la partida
	 */
	public SolLabAvid(Laberint laberint) {
		puntuacio = 0; // inicialment la puntuacio es 0
		solucio = new int[laberint.getFiles()][laberint.getColumnes()];
		for (int f = 0; f < solucio.length; f++) {
			for (int c = 0; c < solucio[0].length; c++) {
				solucio[f][c] = 0; // la matriu amb la solució s'inicialitza a 0
			}
		}
		this.laberint = laberint; // s'assigna el laberint
		teSolucio = false; // inicialmen s'assigna que teSolucio a false
	}

	/**
	 * Mètode que resol el laberint amb un algorisme àvid
	 * 
	 * @return array d'enters amb la solució o null si no s'ha trobat
	 */
	public int[][] trobarCamiSol() {
		// assignem la columna i fila inicial
		int fAct = laberint.getFi();
		int cAct = laberint.getCi();
		
		puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct); // puntuació és el valor de la primera casella
		int comptador = 1; // comptador s'inicialitza a 1 
		solucio[fAct][cAct] = comptador; // es marca la casella inicial amb 1 a la solució
		
		int[] moviment = new int[2]; 
		
		while (!teSolucio && (puntuacio > 0)) {
			moviment = millorOpcio(laberint, fAct, cAct); // es crida a millorOpcio que escull el desplaçament cap a la millor casella per avançar
			if (moviment != null) { // si moviment es diferent de null pot avançar
				// actualitza fila/columna actual
				fAct += moviment[0];
				cAct += moviment[1];
				// actualitza la puntuació
				puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct);
				// marca la casella a la solució
				solucio[fAct][cAct] = ++comptador;
				// comprova si ha arribat al final
				if (fAct == laberint.getFf() && cAct == laberint.getCf()) {
					teSolucio = true; // si ha arribat al final s'assigna teSolucio a true
				} 
			} else { // si moviment és null no hi ha més opcions per avançar i el mètode retorna null
				return null;
			}
		}
		if (teSolucio) {
			return solucio; // si arriba al final del laberint retorna la solució
		} else { 
			return null; // en qualsevol altre cas retorna null
		}
	}

	/**
	 * Mètode per calcular la direcció que otorga més punts
	 * 
	 * @param laberint - laberint de la partida
	 * @param fil
	 * @param col
	 * @return la direcció que otorga més punts
	 */
	private int[] millorOpcio(Laberint laberint, int fil, int col) {
		float[] puntuacions = new float[4];
		int[] moviment = new int[2];

		// Calcula les puntuacions que obtindria si avança cap a:
		puntuacions[0] = laberint.calcularPuntuacio(puntuacio, fil, col - 1); // la esquerra
		puntuacions[1] = laberint.calcularPuntuacio(puntuacio, fil, col + 1); // la dreta
		puntuacions[2] = laberint.calcularPuntuacio(puntuacio, fil - 1, col); // dalt
		puntuacions[3] = laberint.calcularPuntuacio(puntuacio, fil + 1, col); // baix

		float max = -puntuacio;
		for (int i = 0; i < 4; i++) { // bucle per determinar quina direcció otorga més punts
			if (max < puntuacions[i]) { // actualitza el valor max si és troba un nou màxim i es accesible
				switch (i) {
				case 0:
					if (noVisitada(fil, col - 1)) { // comprova si no s'ha visitat
						max = puntuacions[i];
						// desplaçament cap a l'esquerra
						moviment[0] = 0;
						moviment[1] = -1;
					}
					break;
				case 1:
					if (noVisitada(fil, col + 1)) { // comprova si no s'ha visitat
						max = puntuacions[i];
						// desplaçament cap a la dreta
						moviment[0] = 0;
						moviment[1] = +1;
					}
					break;
				case 2:
					if (noVisitada(fil - 1, col)) { // comprova si no s'ha visitat
						max = puntuacions[i];
						// desplaçament cap a dalt
						moviment[0] = -1;
						moviment[1] = 0;
					}
					break;
				case 3:
					if (noVisitada(fil + 1, col)) { // comprova si no s'ha visitat
						max = puntuacions[i];
						// desplaçament cap a baix
						moviment[0] = +1;
						moviment[1] = 0;
					}
					break;
				}
			}
		}
		if (max == -puntuacio) {
			return null; // retorna null si no pot avançar
		}
		return moviment; // retorna la direcció cap a la casella que dona més punts
	}

	/**
	 * Mètode per comprobar si la casella passada per paràmetre s'ha visitat
	 * 
	 * @param fil - fila
	 * @param col - columna
	 * @return true si no s'ha visitat la casella o false si s'ha visitat o està fora de rang
	 */
	private boolean noVisitada(int fil, int col) {
		// Comprova si fil i col està dins del rang de la matriu
		if (fil >= 0 && fil < solucio.length && col >= 0 && col < solucio[0].length) {
			if (solucio[fil][col] == 0) { // si la posició conte 0 retorna true perque no s'ha visitat
				return true;
			}
		}
		return false; // en qualsevol altre cas retorna false
	}

	/**
	 * Mètode que retorna la puntuació obtinguda
	 * 
	 * @return puntuació
	 */
	public float getPuntuacio() {
		if (teSolucio) { // comprova si el laberint té solució
			return puntuacio; // si en té retorna la puntuació
		}
		return 0; // si no retorna 0
	}

}
