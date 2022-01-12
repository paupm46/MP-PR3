package dades;

public class SolLabAvid {

	private float puntuacio;
	private int[][] solucio;
	private Laberint laberint;
	private boolean teSolucio;

	/**
	 * Constructor Laberint
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
		this.laberint = laberint.clone(); // es copia el laberint (TODO pot ser no fa falta)
		teSolucio = false; // inicialmen s'assigna que no te solucio
	}

	public int[][] trobarCamiSol() {
		int fAct, cAct;
		int[] casella = new int[2];
		int comptador = 1;

		// assignem la columna i fila inicial
		fAct = laberint.getFi();
		cAct = laberint.getCi();
		puntuacio = laberint.getValorInici(); // TODO assumim que a la primera casella no importa l'operand??
		solucio[fAct][cAct] = comptador; // es marca la casella inicial amb 1 a la solució

		while (!teSolucio && (puntuacio > 0)) {

			casella = millorOpcio(laberint, fAct, cAct); // escull l'opció amb mes punts per avançar
			if (casella != null) { // si es diferent de null pot avaçar
				// actualitza fila/columna actual
				fAct += casella[0];
				cAct += casella[1];
				puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct); // actualitza la puntuació
				solucio[fAct][cAct] = ++comptador; // marca la casella a la solució
				// comprova si ha arribat al final
				if (fAct == laberint.getFf() && cAct == laberint.getCf()) {
					teSolucio = true;
				}
			} else { // si casella es null no hi ha mes opcions per avançar i el mètode retorna null
				return solucio;
			}
		}
		return solucio; // si arriba al final retorna la solució
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
		int[] casella = new int[2];

		// Calcula les puntuacions que obtindria si avança cap a:
		puntuacions[0] = laberint.calcularPuntuacio(puntuacio, fil, col - 1); // esquerra
		puntuacions[1] = laberint.calcularPuntuacio(puntuacio, fil, col + 1); // dreta
		puntuacions[2] = laberint.calcularPuntuacio(puntuacio, fil - 1, col); // dalt
		puntuacions[3] = laberint.calcularPuntuacio(puntuacio, fil + 1, col); // baix

		float max = -puntuacio;
		for (int i = 0; i < 4; i++) { // bucle per determinar quina direcció otorga més punts
			if (max < puntuacions[i]) { // actualitza el valor max si és troba un nou màxim i es accesible
				switch (i) {
				case 0:
					if (!visitada(fil, col - 1)) { // comprova si es accesible
						max = puntuacions[i];
						casella[0] = 0;
						casella[1] = -1;
					}
					break;
				case 1:
					if (!visitada(fil, col + 1)) { // comprova si es accesible
						max = puntuacions[i];
						casella[0] = 0;
						casella[1] = +1;
					}
					break;
				case 2:
					if (!visitada(fil - 1, col)) { // comprova si es accesible
						max = puntuacions[i];
						casella[0] = -1;
						casella[1] = 0;
					}
					break;
				case 3:
					if (!visitada(fil + 1, col)) { // comprova si es accesible
						max = puntuacions[i];
						casella[0] = +1;
						casella[1] = 0;
					}
					break;
				}
			}
		}
		if (max == -1) {
			return null; // retorna null si no pot avançar
		}
		return casella; // retorna la direcció cap a la casella que dona més punts
	}

	/**
	 * Mètode per comprobar si s'ha visitat una casella
	 * 
	 * @param fil
	 * @param col
	 * @return true si s'ha visitat la casella o false si no
	 */
	private boolean visitada(int fil, int col) {
		if (fil >= 0 && fil < solucio.length && col >= 0 && col < solucio[0].length) {
			if (solucio[fil][col] > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodè que retorna la puntuació obtinguda
	 * 
	 * @return puntuació
	 */
	public float getPuntuacio() {
		if (teSolucio) {
			return puntuacio;
		}
		return 0;
	}

}
