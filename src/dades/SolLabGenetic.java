package dades;

public class SolLabGenetic {
	
	private boolean[][] solucio;
	private boolean[][][] totesSol;
	private Laberint laberint;
	private boolean teSolucio;
	private int nSol;

	public SolLabGenetic (Laberint laberint) {
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
		solucio[fAct][cAct] = true; // es marca la casella inicial a la solució
		
		boolean teSolucio = solGenetica(fAct, cAct, 0);
		guardaSolActual();
		nSol++;
		
		return totesSol;
	}
	
	private boolean solGenetica(int fAct, int cAct, float puntuacio) {
		float[] valorMoviments = new float[4];
		float valorMaxim = -1;
		int seleccionat = 0;
		String valorActual;

		if (fAct==laberint.getFf() && cAct==laberint.getCf()) {
			solucio[fAct][cAct] = true;
			return true;
		}
		
		puntuacio = laberint.calcularPuntuacio(puntuacio, fAct, cAct);
		
		if (puntuacio<=0) {
			solucio[fAct][cAct] = false;
			return false;
		}
		
		valorActual=laberint.getValorCasella(fAct, cAct);
		laberint.marcarCasellaUtilitzada(fAct, cAct);

		valorMoviments[0]=laberint.calcularPuntuacio(puntuacio, fAct-1, cAct);
		valorMoviments[1]=laberint.calcularPuntuacio(puntuacio, fAct+1, cAct);
		valorMoviments[2]=laberint.calcularPuntuacio(puntuacio, fAct, cAct+1);
		valorMoviments[3]=laberint.calcularPuntuacio(puntuacio, fAct, cAct-1);
		
		// ordenem els possibles moviments en funció del seu valor
		
		while(   valorMoviments[0]!=-1 || valorMoviments[1]!=-1 
              || valorMoviments[2]!=-1 || valorMoviments[3]!=-1)
		{
			valorMaxim = -1;
		
			for(int i=0;i<valorMoviments.length;i++) {
				if(valorMoviments[i] > valorMaxim) {
					valorMaxim = valorMoviments[i];
					 // treiem el valor, per a la propera iteració no entrar-hi
					valorMoviments[i] = -1;
					seleccionat = i;
				}
			}
			if(valorMaxim == -1) {
				solucio[fAct][cAct] = false;
				return false;
			}
		
			switch(seleccionat) {
				case 0:
					if(solGenetica(fAct-1, cAct, puntuacio)) {
						System.out.println("Marco "+fAct+" "+cAct+" com a part de la solucio");
						solucio[fAct][cAct] = true;
						return true;
					}
					break;
				case 1:
					if(solGenetica(fAct+1, cAct, puntuacio)) {
						System.out.println("Marco "+fAct+" "+cAct+" com a part de la solucio");
						solucio[fAct][cAct] = true;
						return true;
					}
					break;
				case 2:
					if(solGenetica(fAct, cAct+1, puntuacio)) {
						System.out.println("Marco "+fAct+" "+cAct+" com a part de la solucio");
						solucio[fAct][cAct] = true;
						return true;
					}
					break;
				case 3:
					if(solGenetica(fAct, cAct-1, puntuacio)) {
						System.out.println("Marco "+fAct+" "+cAct+" com a part de la solucio");
						solucio[fAct][cAct] = true;
						return true;
					}
			}
			laberint.desmarcarCasellaUtilitzada(fAct, cAct,valorActual);
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
	
}
