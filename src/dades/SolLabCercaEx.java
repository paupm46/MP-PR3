package dades;

/**
 * Classe SolLabCercaEx per resoldre un laberint amb un algorisme de cerca exhaustiva
 * @version 1.0
 *
 */
public class SolLabCercaEx {
	private int[][] solucio;
	private int[][][] totesSol;
	private float[] puntuacions;
	private int nSol;
	private Laberint laberint;
	private static final int NSOL = 100000;
	
	/**
	 * Constructor SolLabCercaEx
	 * @param laberint - laberint de la partida
	 */
	public SolLabCercaEx(Laberint laberint) {
		solucio = new int [laberint.getFiles()][laberint.getColumnes()];
		for (int f = 0; f < solucio.length; f++) {
			for (int c = 0; c < solucio[0].length; c++) {
				solucio[f][c] = 0;
			}
		}
		this.laberint = laberint;
	}
	
	/**
	 * Mètode que resol el laberint amb un algorisme de cerca exhaustiva
	 * 
	 * @return array d'una taula de dues dimensions d'enters amb les solucions o null si no s'ha trobat
	 */
	public int[][][] trobarCamiSol () {
		nSol = 0;
		totesSol = new int[NSOL][laberint.getFiles()][laberint.getColumnes()];
		puntuacions = new float[NSOL];
		
		int fAct, cAct;

		// assignem la columna i fila inicial
		fAct = laberint.getFi();
		cAct = laberint.getCi();
		float puntuacio = 0; 
		int numPos = 0;
		
		solRecursiva(fAct, cAct, puntuacio, numPos);
		
		if (nSol == 0)
			return null;
		else
			return totesSol;
	}

	private void guardaSolActual() {
		if(nSol<NSOL) {
			for(int i=0; i<laberint.getFiles(); i++) {
				for(int j=0; j<laberint.getColumnes(); j++) {
					totesSol[nSol][i][j] = solucio[i][j];
				}
			}
		}
	}
	
	private boolean solRecursiva(int fAct, int cAct, float puntuacio, int numPos) {
		
		if (fAct >= 0 && fAct < laberint.getFiles() && cAct >= 0 && cAct < laberint.getColumnes()) {
			if(solucio[fAct][cAct]!=0)
				return false;
		}
		
		
		if (fAct==laberint.getFf() && cAct==laberint.getCf()) {
			solucio[fAct][cAct] = ++numPos;
			guardaSolActual();
			if(nSol<NSOL) {
				puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct);
				puntuacions[nSol] = puntuacio;
			}
			nSol++;
			solucio[fAct][cAct] = 0;
			return true;
		}
		
		puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct);
		if (puntuacio>0) {
			
			solucio[fAct][cAct] = ++numPos;
			
			solRecursiva(fAct-1, cAct, puntuacio, numPos);
			
			solRecursiva(fAct+1, cAct, puntuacio, numPos);
			
			solRecursiva(fAct, cAct-1, puntuacio, numPos);
			
			solRecursiva(fAct, cAct+1, puntuacio, numPos);
		}
		
		if (fAct >= 0 && fAct < laberint.getFiles() && cAct >= 0 && cAct < laberint.getColumnes())
		solucio[fAct][cAct] = 0;
		
		return false;
	}
	
	/**
	 * Mètode que retorna la puntuació obtinguda
	 * 
	 * @return puntuacio
	 */
	public float getPuntuacio(int index) {
		if (index<NSOL) {
			return puntuacions[index];
		}
		return 0;
	}

	/**
	 * Mètode que retorna el nombre de solucions
	 * 
	 * @return nombre de solucions
	 */
	public int getnSol() {
		return nSol;
	}
	
	/**
	 * Mètode que retorna si el laberint té solució o no
	 * 
	 *  @return té solució o no
	 */
	public boolean teSolucio() {
		return nSol>0;
	}
}







