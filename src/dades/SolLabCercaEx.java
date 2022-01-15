package dades;

public class SolLabCercaEx {
	private int[][] solucio;
	private int[][][] totesSol;
	private float[] puntuacions;
	private int nSol;
	private Laberint laberint;
	private static final int NSOL = 2000;
	//int numPos;
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
		//numPos = 0;
	}
	
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
		
		/*solRecursiva(fAct, cAct, puntuacio);*/
		if (nSol == 0)
			return null;
		else
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
	
	public float getPuntuacio(int index) {
		return puntuacions[index];
	}

	/**
	 * @return the nSol
	 */
	public int getnSol() {
		return nSol;
	}
	
	public boolean teSolucio() {
		return nSol>0;
	}
	
	/*private boolean solRecursiva(int fAct, int cAct, float puntuacio) {
		
		if (fAct==laberint.getFf() && cAct==laberint.getCf()) {
			solucio[fAct][cAct] = ++numPos;
			guardaSolActual();
			if(nSol<NSOL) {
				puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct);
				puntuacions[nSol] = puntuacio;
			}
			nSol++;
			--numPos;
			solucio[fAct][cAct] = 0;
			return true;
		}
		
		puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct);
		if (puntuacio>0) {
			
			if(solucio[fAct][cAct]!=0) {
				//--numPos;
				return false;
			}
				
			
			solucio[fAct][cAct] = ++numPos;
			
			solRecursiva(fAct-1, cAct, puntuacio);
			
			solRecursiva(fAct+1, cAct, puntuacio);
			
			solRecursiva(fAct, cAct-1, puntuacio);
			
			solRecursiva(fAct, cAct+1, puntuacio);
			--numPos;
		}
		
		if (fAct >= 0 && fAct < laberint.getFiles() && cAct >= 0 && cAct < laberint.getColumnes())
		solucio[fAct][cAct] = 0;
		
		
		
		return false;
	}*/
	

}







