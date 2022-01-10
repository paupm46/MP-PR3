package dades;

public class SolLabCercaEx {
	private boolean[][] solucio;
	private boolean[][][] totesSol;
	private int nSol;
	private Laberint laberint;
	private boolean teSolucio;
	
	/**
	 * Constructor SolLabCercaEx
	 * @param laberint - laberint de la partida
	 */
	public SolLabCercaEx(Laberint laberint) {
		solucio = new boolean [laberint.getFiles()][laberint.getColumnes()];
		for (int f = 0; f < solucio.length; f++) {
			for (int c = 0; c < solucio[0].length; c++) {
				solucio[f][c] = false;
			}
		}
		this.laberint = laberint;
		teSolucio = false; // inicialment s'assigna que no té solucio
	}
	
	public boolean [][][] trobarCamiSol () {
		nSol = 0;
		totesSol = new boolean[10][laberint.getFiles()][laberint.getColumnes()];
		
		int fAct, cAct;
		int[] casella = new int [2];

		// assignem la columna i fila inicial
		fAct = laberint.getFi();
		cAct = laberint.getCi();
		float puntuacio = 0; //laberint.getValorInici(); // assumim que a la primera casella no importa l'operand??
		solucio[fAct][cAct] = true; // es marca la casella inicial a la solució
		
		boolean teSolucio = solRecursiva(fAct, cAct, puntuacio);
		guardaSolActual();
		nSol++;
		
		return totesSol;
	}
	
	private boolean solRecursiva(int fAct, int cAct, float puntuacio) {
		if (fAct==laberint.getFf() && cAct==laberint.getCf()) {
			solucio[fAct][cAct] = true;
			return true;
		}
		puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct);
		if (puntuacio<=0) return false;
		
		laberint.marcarCasellaUtilitzada(fAct, cAct);
		
		if(solRecursiva(fAct-1, cAct, puntuacio)) {
			solucio[fAct][cAct] = true;
			return true;
		}
		if(solRecursiva(fAct+1, cAct, puntuacio)) {
			solucio[fAct][cAct] = true;
			return true;
		}
		if(solRecursiva(fAct, cAct-1, puntuacio)) {
			solucio[fAct][cAct] = true;
			return true;
		}
		if(solRecursiva(fAct, cAct+1, puntuacio)) {
			solucio[fAct][cAct] = true;
			return true;
		}
		return false;
	}
	
	private void guardaSolActual() {
		if(nSol<10) {
			for(int i=0; i<laberint.getFiles(); i++) {
				for(int j=0; j<laberint.getColumnes(); j++) {
					totesSol[nSol][i][j] = solucio[i][j];
				}
			}
		}
	}
	
	/*private boolean solRecursiva(int fAct, int cAct, float puntuacio) {
		if (fAct==laberint.getFf() && cAct==laberint.getCf()) {
			solucio[fAct][cAct] = true;
			guardaSolActual();
			nSol++;
			return true;
		}
		
		puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct);
		if (puntuacio>0) {
			solucio[fAct][cAct] = true;
			laberint.marcarCasellaUtilitzada(fAct, cAct);
			solRecursiva(fAct-1, cAct, puntuacio);
			solRecursiva(fAct+1, cAct, puntuacio);
			solRecursiva(fAct, cAct-1, puntuacio);
			solRecursiva(fAct, cAct+1, puntuacio);
		}
		
		return false;
	}*/
	
}







