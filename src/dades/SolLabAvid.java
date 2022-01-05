package dades;

public class SolLabAvid {

	private float puntuacio;
	private boolean[][] solucio;
	private Laberint laberint;
	private boolean teSolucio;
	
	/**
	 * Constructor Laberint
	 * @param laberint - laberint de la partida
	 */
	public SolLabAvid(Laberint laberint) {
		solucio = new boolean [laberint.getFiles()][laberint.getColumnes()];
		for (int f = 0; f < solucio.length; f++) {
			for (int c = 0; c < solucio[0].length; c++) {
				solucio[f][c] = false;
			}
		}
		puntuacio = 0;
		this.laberint = laberint;
		teSolucio = false; // inicialmen s'assigna que no te solucio
	}

	public boolean [][] trobarCamiSol () {
		int fAct, cAct;
		int[] casella = new int [2];

		// assignem la columna i fila inicial
		fAct = laberint.getFi();
		cAct = laberint.getCi();
		puntuacio = laberint.getValorInici(); // assumim que a la primera casella no importa l'operand??
		solucio[fAct][cAct] = true; // es marca la casella inicial a la solució
		laberint.marcarCasellaUtilitzada(fAct, cAct); // es marca la casella inicial com utilitzada
		
		while(!teSolucio && (puntuacio > 0)) {
			
			casella = millorOpcio (laberint, fAct, cAct); // escull l'opció amb mes punts per avançar
			if (casella != null) { 
				// actualitza fila/columna actual
				fAct += casella[0];
				cAct += casella[1];
				puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct); // actualitza la puntuació
				solucio[fAct][cAct] = true; // marca la casella a la solució
				laberint.marcarCasellaUtilitzada(fAct, cAct); // marca la calsella com usada al tauler
				
				// comprova si ha arribat al final
				if (fAct == laberint.getFf() && cAct == laberint.getCf()){
					teSolucio = true;
				}
			} else { // si no escull cap casella es retrona null (s'ha quedan sense opcions per avançar)
				return null;
			}
		}	
		return solucio; // si arriba al final retorna la solució
	}

	/**
	 * Mètode per calcular la direcció que otorga més punts
	 * @param laberint - laberint de la partida
	 * @param fil
	 * @param col
	 * @return la direcció que otorga més punts
	 */
	private int [] millorOpcio (Laberint laberint, int fil, int col) {
		float[] puntuacions = new float[4]; 
		int[] casella = new int [2];
		
		// Calcula les puntuacions que obtindria si avança cap a:
		puntuacions[0] = laberint.calcularPuntuacio(puntuacio, fil, col-1); //la esquerra
		puntuacions[1] = laberint.calcularPuntuacio(puntuacio, fil, col+1); //la dreta
		puntuacions[2] = laberint.calcularPuntuacio(puntuacio, fil-1, col); //dalt
		puntuacions[3] = laberint.calcularPuntuacio(puntuacio, fil+1, col); //baix
	
		float max = -1;
		for (int i = 0; i < 4; i++) { // bucle per determinar quina direcció otorga més punts
			if (max < puntuacions[i]) { // actualitza el valor max si és troba un nou màxim
				max = puntuacions[i];
				switch (i) {
				case 0:
					casella[0] = 0; casella[1] = -1;
					break;
				case 1:
					casella[0] = 0; casella[1] = +1;
					break;
				case 2:
					casella[0] = -1; casella[1] = 0;
					break;
				case 3:
					casella[0] = +1; casella[1] = 0;
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
	 * Metodè que retorna la puntuació obtinguda
	 * @return puntuació
	 */
	public float getPuntuacio() {
		if (teSolucio) {
			return puntuacio;
		}
		return 0;
	}

}
