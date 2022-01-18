package dades;
import java.util.Random;

public class SolLabGenetic {
	
	private int midaPoblacio;
	private boolean[][][] poblacio, poblacio2;
	private Laberint laberint;
	private float[] fitnessPoblacio;
	private float fitnessActual,fitnessAnterior;
	private int iteracionsSenseMillora = 0;
	private int[] ordrePoblacio;
	private int numGeneracions = 0;
	private static final int maxNumGeneracions = 3;
	private static final int maxIteracionsNoMillora = 10;
	private static final double percentatgeMillors = 0.20;
	private static final double percentatgeCreuats = 0.70;
	private static final double percentatgeCreuament = 0.50; // quantitat del cromosoma que creuarem
    Random rd;

	public SolLabGenetic (Laberint laberint, int midaPoblacio) {
		
		this.midaPoblacio = midaPoblacio;
		this.laberint = laberint;
		
		this.rd = new Random();
		
		this.ordrePoblacio = new int[midaPoblacio];
		this.fitnessPoblacio = new float[midaPoblacio];
		this.poblacio = new boolean [midaPoblacio][laberint.getFiles()][laberint.getColumnes()];
		this.poblacio2 = new boolean [midaPoblacio][laberint.getFiles()][laberint.getColumnes()];
		
		poblacio_inicial();
		
		while((numGeneracions < maxNumGeneracions) && (iteracionsSenseMillora < maxIteracionsNoMillora) ) {
			fitnessAnterior = fitnessActual;
			calculaFitness();
			ordenaFitness();
			imprimeixPoblacioFitness();
			fitnessActual = millorFitness();
			
			if(fitnessActual == fitnessAnterior) {
				iteracionsSenseMillora++;
			}
			
			// els millors sobreviuen sense canvis
			copiaPercentatgeMillor();
			
			// creuem una part de la població
			creuarPoblacio();
			
			// mutem la resta
			mutarPoblacio();	
			
			numGeneracions++;
			poblacio = poblacio2;
			System.out.println("Fitness de la generació "+numGeneracions+":"+fitnessActual);
		}
	}
	
	private void copiaPercentatgeMillor() {
		int millorsCromosomes = (int) (midaPoblacio*percentatgeMillors);
		for(int i = 0; i< millorsCromosomes; i++) {
			copiaCromosoma(ordrePoblacio[i],i);
		}
	}
	
	private void copiaCromosoma(int origen, int desti) {
		for(int f=0; f < laberint.getFiles(); f++) {
			for(int c=0; c < laberint.getColumnes(); c++) {
				poblacio2[desti][f][c] = poblacio[origen][f][c];
			}
		}
		
	}

	private float millorFitness() {
		float millorFitness = 0;
		for(int i=0; i < midaPoblacio; i++) {
			if(fitnessPoblacio[i] > millorFitness) {
				millorFitness = fitnessPoblacio[i];
			}
		}
		return millorFitness;
	}

	private void ordenaFitness() {
		float maxFitness;
		int   posMaxFitness = 0;
		float[] fitnessPoblacioAux = new float[midaPoblacio];
		for(int i=0;i<midaPoblacio;i++) {
			fitnessPoblacioAux[i]=fitnessPoblacio[i];
		}
		for(int i=0;i<midaPoblacio;i++) {
			maxFitness=0;
			for(int j=0;j<midaPoblacio;j++) {
				if( fitnessPoblacioAux[j] > maxFitness ) {
					maxFitness = fitnessPoblacioAux[j];
					posMaxFitness = j;
				}
			}
			ordrePoblacio[i]=posMaxFitness;
			// treiem el valor màxim per a la propera iteració
			fitnessPoblacioAux[posMaxFitness]=-1;
		}
		System.out.print("Ordenat per fitness:");
		for(int i = 0;i<midaPoblacio;i++) {
			System.out.print(i+"-"+ordrePoblacio[i]+'-'+fitnessPoblacio[ordrePoblacio[i]]+',');
			System.out.print("\n");
		}
	}
	
	private void creuarPoblacio() {

		int millorsCromosomes = (int) (midaPoblacio*percentatgeMillors);
		int cromosomesCreuats = (int) (midaPoblacio*percentatgeCreuats);
		for(int i = millorsCromosomes; i < millorsCromosomes+cromosomesCreuats; i=i+2) {
			creuaCromosoma(ordrePoblacio[i],ordrePoblacio[i+1]);
			copiaCromosoma(ordrePoblacio[i],i);
			copiaCromosoma(ordrePoblacio[i+1],i+1);
		}
	}
	
	private void creuaCromosoma(int i, int j) {
		
		int iniciCreuament;
		int longitudCromosoma = laberint.getFiles()*laberint.getColumnes();
		int longitudCreuament = (int) percentatgeCreuament*longitudCromosoma;
		boolean aux;
		
		iniciCreuament = rd.nextInt(Integer.MAX_VALUE) % (longitudCromosoma-longitudCreuament);
		
		// hem de calcular fila i columna inicial del creuament,
		// quantes files senceres hem de creuar
		// i quantes columnes queden de "resto"
		
		int fIni, nFiles, colsResto;
		
		fIni = iniciCreuament % laberint.getColumnes();
		nFiles = longitudCreuament % laberint.getColumnes();
		colsResto = longitudCreuament - nFiles*laberint.getFiles();
		
		for(int f=fIni; f<fIni+nFiles; f++) {
			for(int c=0; c<laberint.getColumnes(); c++) {
				aux = poblacio[i][f][c];
				poblacio[i][f][c] = poblacio[j][f][c];
				poblacio[j][f][c] = aux;
			}
		}
		for(int c=0; c<colsResto; c++) {
			aux = poblacio[i][fIni+nFiles][c];
			poblacio[i][fIni+nFiles][c] = poblacio[j][fIni+nFiles][c];
			poblacio[j][fIni+nFiles][c] = aux;
		}
	}

	private void mutarPoblacio() {

		int millorsCromosomes = (int) (midaPoblacio*percentatgeMillors);
		int cromosomesCreuats = (int) (midaPoblacio*percentatgeCreuats);
		int iniCromosomesMutats = millorsCromosomes+cromosomesCreuats;
		for(int i = iniCromosomesMutats; i < midaPoblacio; i++) {
			mutaCromosoma(ordrePoblacio[i]);
			copiaCromosoma(ordrePoblacio[i],i);
		}
	}

	private void mutaCromosoma(int cromosoma) {

		int tamanyTauler,nMutacions;
		int fila,columna;
		
		tamanyTauler = laberint.getFiles()*laberint.getColumnes();
		nMutacions = tamanyTauler/10;
		int i = 0;
		
		while(i<nMutacions) {
			fila = rd.nextInt(Integer.MAX_VALUE) % laberint.getFiles();
			columna = rd.nextInt(Integer.MAX_VALUE) % laberint.getColumnes();
			
			if(!laberint.getPos(fila, columna).equals("NA")) {
				poblacio[cromosoma][fila][columna] = !poblacio[cromosoma][fila][columna];
				i++;
			}
		}
	}

	void poblacio_inicial() {
		boolean[][] cromosoma;

		for(int d = 0; d < midaPoblacio; d++) {
			cromosoma = poblacio[d];
			for (int f = 0; f < cromosoma.length; f++) {
				for (int c = 0; c < cromosoma[0].length; c++) {
					if(!laberint.getPos(f, c).equals("NA")) {
						cromosoma[f][c] = rd.nextBoolean();
					}
					else {
						cromosoma[f][c] = false;
					}
				}
			}
		}
	}
	
	void calculaFitness() {
		
		boolean[][] cromosoma;
		float puntuacioCami;
		
		
		for(int i = 0; i < midaPoblacio; i++) {
			
			cromosoma = poblacio[i];

			// si la casella inicial està seleccionada, partim de bon fitness
			if( cromosoma[laberint.getFi()][laberint.getCi()]) {
				fitnessPoblacio[i] = laberint.getValorInici() * 10;
			}
			else {
				// si la casella inicial no està dins la solució, no és vàlida. Donem un valor molt petit
				// per a donar possibilitat a mutacions de camins potencialment correctes.
				fitnessPoblacio[i] = (float) 0.05;
			}

			// si la casella final està seleccionada, és un bon candidat; li augmentem el fitness
			if( cromosoma[laberint.getFf()][laberint.getCf()] ){
				fitnessPoblacio[i] = fitnessPoblacio[i] * (float) 5.0;
			}
			
			// comprovem que sigui una solucio vàlida (tota casella certa tingui una veïna també certa)
			// i rebaixem el fitness en cas que no ho sigui
			if (!solucioValida(cromosoma)) {
				fitnessPoblacio[i]=fitnessPoblacio[i]/5;
			}
			
			// calculem el valor de totes les caselles per on passa
			// augmentem el fitness dels camins amb mes puntuacio 
			puntuacioCami = 0;
			for(int f=0; f < laberint.getFiles(); f++) {
				for(int c=0; c < laberint.getColumnes(); c++) {
					if( cromosoma[f][c] &&  (f != laberint.getFi() && c != laberint.getCi()) ) {
						puntuacioCami = laberint.calcularPuntuacio(puntuacioCami, f, c);
					}
				}
			}
			System.out.println("puntuacio cami cromosoma"+i+":"+puntuacioCami);
			if(puntuacioCami<0) puntuacioCami = (float) 0.01;
			fitnessPoblacio[i] = fitnessPoblacio[i]*(puntuacioCami/10);
			System.out.println("fitness de "+i+":"+fitnessPoblacio[i]);
		}
	}
	
	private boolean solucioValida(boolean[][] cromosoma) {

		boolean solucioValida = true;
		boolean adalt,abaix,dreta,esquerra;
		int i,j;
		
		i=0;
		while( i < laberint.getFiles() && solucioValida ) {
			j=0;
			while( j < laberint.getColumnes() && solucioValida ) {
				if(cromosoma[i][j] == true) {

					adalt = false;
					abaix = false;
					dreta = false;
					esquerra = false;
					
					if ( i > 0 )
						adalt = cromosoma[i-1][j];
					if ( i < laberint.getFiles()-1 )
						abaix = cromosoma[i+1][j];
					if ( j > 0 )
						dreta = cromosoma[i][j-1];
					if ( j < laberint.getColumnes()-1 )
						esquerra = cromosoma[i][j+1];
					
					solucioValida = ( adalt || abaix || dreta || esquerra );
				}
				j++;
			}
			i++;
		}
		
		return solucioValida;
	}

	public boolean[][] millorSol() {
		return poblacio[ordrePoblacio[0]];
	}
	
	private void imprimeixPoblacioFitness() {
		for(int i=0;i<midaPoblacio;i++) {
			System.out.println("Cromosoma"+i);
			for(int f=0;f<laberint.getFiles();f++) {
				for(int c=0;c<laberint.getColumnes();c++) {
					if (poblacio[i][f][c] ==  true) {
						System.out.print(" -> ");
					} else {
						System.out.print(" -- ");
					}
				}
				System.out.println("");
			}
			System.out.println("Fitness"+fitnessPoblacio[i]);
		}
	}
	
}
