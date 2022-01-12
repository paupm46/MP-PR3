package dades;

public class SolLabCercaEx {
	private boolean[][] solucio;
	private boolean[][][] totesSol;
	private int nSol;
	private Laberint laberint;
	private boolean teSolucio;
	private static final int NSOL = 20;
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
		totesSol = new boolean[NSOL][laberint.getFiles()][laberint.getColumnes()];
		
		int fAct, cAct;

		// assignem la columna i fila inicial
		fAct = laberint.getFi();
		cAct = laberint.getCi();
		float puntuacio = 0; //laberint.getValorInici(); // assumim que a la primera casella no importa l'operand??

		
		boolean teSolucio = solRecursiva(fAct, cAct, puntuacio);
		//guardaSolActual();
		//nSol++;
		
		return totesSol;
	}
	
	/*private boolean solRecursiva(int fAct, int cAct, float puntuacio) {
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
	}*/
	
	private void guardaSolActual() {
		if(nSol<NSOL) {
			for(int i=0; i<laberint.getFiles(); i++) {
				for(int j=0; j<laberint.getColumnes(); j++) {
					totesSol[nSol][i][j] = solucio[i][j];
				}
			}
		}
	}
	
	private boolean solRecursiva(int fAct, int cAct, float puntuacio) {
		
		/*for (int i = 0; i < solucio.length; i++) {
			for (int j = 0; j < solucio[0].length; j++){
				if (i==fAct && j==cAct) {
					System.out.print(" ? ");
				}
				else {
					if (solucio[i][j] ==  true) {
						System.out.print(" -> ");
					} else {
						System.out.print(" -- ");
					}
				}
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();*/
		
		if (fAct==laberint.getFf() && cAct==laberint.getCf()) {
			solucio[fAct][cAct] = true;
			guardaSolActual();
			nSol++;
			solucio[fAct][cAct] = false;
			return true;
		}
		
		puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct);
		if (puntuacio>0) {
			
			if(solucio[fAct][cAct])
				return false;
			
			solucio[fAct][cAct] = true;
			
			solRecursiva(fAct-1, cAct, puntuacio);
			
			solRecursiva(fAct+1, cAct, puntuacio);
			
			solRecursiva(fAct, cAct-1, puntuacio);
			
			solRecursiva(fAct, cAct+1, puntuacio);
		}
		
		if (fAct >= 0 && fAct < laberint.getFiles() && cAct >= 0 && cAct < laberint.getColumnes())
		solucio[fAct][cAct] = false;
		
		return false;
	}
	
}







