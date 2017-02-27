import java.util.ArrayList;

public class Samurai extends Game {	

	private ArrayList<Tauler> sudokus= new ArrayList<Tauler>();


	/**
	 * Constructor del Samurai.
	 * Configura una GUI, divideix el taullel en 5 sudokus i comenza a resoldrel.
	 * @param x tauler 21x21
	 */
	public Samurai(Tauler x) {
		//interficie = new SudokuGUI("Sudoku",800,800,x.getValid());
		for (int i = 0; i<5;i++){
			sudokus.add(new Tauler((x.getTamany()-3)/2));
		}
		carregardades(x);
		for (int i = 0; i<5;i++){
			sudokus.get(i).marcatgeInicialSudoku();
		}
		//interficie.updateBoard(x.getConfiguracio());
		resoldre(sudokus.get(0), 0,0,0);
	}

	/**
	 * Funció que ens serveix per saber si tenim solució o no
	 @param x sera el nostre taulell.
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 * @param s parametre de inmersio per saber quin tauler usar.
	 * @return si es solució o no 
	 */
	public boolean hiHaSolucio (Tauler x, int k, int t, int s){
		return ( s==sudokus.size()-1 && super.hiHaSolucio(x, k, t) );
	}

	/**
	 * Funcio encarregada a resoldre el sudoku Samurai 
	 */
	public void resoldre(Tauler x, int k, int t, int s){
		if (x.getValid(k, t)){
			preparaRecorregutNivell(x, k, t);
			//drawTauler();
			while ( hiHaSuccessor(x,k, t )){
				seguentGerma(x, k, t);
				marcatgeSamurai(k,t, x.getNode(k, t)-1, s);
				if ( hiHaSolucio(x, k, t, s) ){	
					if ( bona(x, k, t, s) ){
						 gestionaSolució(restauratauler());
					}
				}
				else if ( bona(x, k, t, s) ) numeroValid(x, k, t, s);		
				desmarcatgeSamurai(k,t, x.getNode(k, t)-1,s);
			}
			x.setNode(-1,k, t);
		}else{
			if(hiHaSolucio(x, k, t, s)){
				 gestionaSolució(restauratauler());
			}
			numeroValid(x, k, t, s);

		}
	}
	/**
	 * Funció que s'ocupa de dividir el taullell en 5 sudokus
	 * @param x taulell 21x21
	 */
	public void carregardades(Tauler x){
		for (int i = 0 ; i<21; i++){
			for(int j = 0 ; j<21; j++){	
				//Sudoku Mig
				if(j>5 && i>5 && j<15  && i<15){
					sudokus.get(0).setNode(x.getNode(i, j), (i-6),(j-6));
					if(x.getNode(i, j)==-1) sudokus.get(0).setValid(Boolean.TRUE,(i-6),(j-6));
					else sudokus.get(0).setValid(Boolean.FALSE,(i-6),(j-6));
				}
				//Sudoku 1
				else if(j<9 && i<9){
					sudokus.get(1).setNode(x.getNode(i, j), i,j);
					if(x.getNode(i, j)==-1) sudokus.get(1).setValid(Boolean.TRUE,(i),(j));
					else sudokus.get(1).setValid(Boolean.FALSE,(i),(j));
				}
				//Sudoku 2
				else if(j>11 && i<9){
					sudokus.get(2).setNode(x.getNode(i, j), i,(j-12));
					if(x.getNode(i, j)==-1) sudokus.get(2).setValid(Boolean.TRUE, i,(j-12));
					else sudokus.get(2).setValid(Boolean.FALSE, i,(j-12));

				}
				//Sudoku 3
				else if(j<9 && i>11){
					sudokus.get(3).setNode(x.getNode(i, j), (i-12),j);
					if(x.getNode(i, j)==-1) sudokus.get(3).setValid(Boolean.TRUE, (i-12),j);
					else sudokus.get(3).setValid(Boolean.FALSE, (i-12),j);
				}
				//Sudoku 4
				else if(j>11 && i>11){
					sudokus.get(4).setNode(x.getNode(i, j), (i-12),(j-12));
					if(x.getNode(i, j)==-1) sudokus.get(4).setValid(Boolean.TRUE, (i-12),(j-12));
					else sudokus.get(4).setValid(Boolean.FALSE, (i-12),(j-12));
				}		
			}
		}
	}
	
	/** Funció encarregada de decidir on continuar la cerca
	 * Un cop la funcio "bona" ens diu que podem introduir un nombre en la nostre cerca
	 * la funcio numeroValid situa el "cursor" sobre la seguent casella a gestionar.
	 * 
	 * @param x sera el nostre taulell.
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 */
	@Override
	public void numeroValid (Tauler x, int k, int t, int s){
		if(t<x.getTamany()-1) resoldre(x,k,t+1,s);
		else if(k<x.getTamany()-1) resoldre(x,k+1,0,s);
		else if  (t==x.getTamany()-1 && k==x.getTamany()-1 
				&& s<sudokus.size()-1)   resoldre(sudokus.get(++s),0,0,s);
	}

	/**
	 * Funcio que ens permet saber si el nombre introduit es valid.
	 * Mira el marcatge per sudoku i per el Samurai.
	 * @param x sera el nostre taulell.
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 * @param s parametre de inmersio per saber quin tauler usar.
	 * @return si el parametre esta correctament colocat o no
	 */
	public boolean bona (Tauler x, int k, int t, int s){
		if(s==0){
			if(k<3 && t<3){
				if (sudokus.get(1).getNumFila(k+6, x.getNode(k, t)-1)  > 1) return false;
				if (sudokus.get(1).getNumCol(t+6, x.getNode(k, t)-1) > 1)  return false;	
				if (!super.bona(x, k, t)) return false;
			}

			if(k<3 && t>5){

				if (sudokus.get(2).getNumFila(k+6, x.getNode(k, t)-1)  > 1) return false;
				if (sudokus.get(2).getNumCol(t-6, x.getNode(k, t)-1) > 1)  return false;	
				if (!super.bona(x, k, t)) return false;
			}			
			if(k>5 && t<3){
				if (sudokus.get(3).getNumFila(k-6, x.getNode(k, t)-1)  > 1) return false;
				if (sudokus.get(3).getNumCol(t+6, x.getNode(k, t)-1) > 1)  return false;	
				if (!super.bona(x, k, t)) return false;
			}
			if(k>5 && t>5){
				if (sudokus.get(4).getNumFila(k-6, x.getNode(k, t)-1)  > 1) return false;
				if (sudokus.get(4).getNumCol(t-6, x.getNode(k, t)-1) > 1)  return false;	
				if (!super.bona(x, k, t)) return false;
			}
		}return super.bona(x, k, t);
	}	

	/**
	 * Funció de marcatge per a Samurai
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 * @param valor a marcar
	 * @param s parametre de inmersio per saber quin tauler marcar.
	 */
	public void marcatgeSamurai(int k, int t, int valor, int s){
		sudokus.get(s).marcatge(k, t, valor);
		tracteNode(k, t, valor, s, Boolean.FALSE);
	}
	
	/**
	 * Funció de desmarcatge per a Samurai
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 * @param valor a marcar
	 * @param s parametre de inmersio per saber quin tauler desmarcar.
	 */
	public void desmarcatgeSamurai(int k, int t, int valor, int s){
		sudokus.get(s).desmarcatge(k, t, valor);
		tracteNode(k, t, valor, s, Boolean.TRUE);

	}
	
	/**
	 * Funció que s'ocupa de introduir els valors ens els taulells veins.
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 * @param valor sera el valor introduit
	 * @param s parametre de inmersio per gestionar quin taulell resoldre
	 * @param desmarca Si es cert, desmarcara, en cas contrari, Marcara
	 */
	public void tracteNode(int k, int t, int valor, int s, boolean desmarca){
		if(s==0){
			if(k<3 && t<3){
				sudokus.get(1).setValid(desmarca, k+6, t+6);
				if (!desmarca){
					sudokus.get(1).setNode(valor, k+6, t+6);
					sudokus.get(1).marcatge(k+6, t+6, valor);
				}
				else sudokus.get(1).desmarcatge(k+6, t+6, valor);
			}

			else if(k<3 && t>5){
				sudokus.get(2).setValid(desmarca, k+6, t-6);
				if (!desmarca){
					sudokus.get(2).setNode(valor, k+6, t-6);
					sudokus.get(2).marcatge(k+6, t-6, valor);
				}else sudokus.get(2).desmarcatge(k+6, t-6, valor);
			}			
			else if(k>5 && t<3){
				sudokus.get(3).setValid(desmarca, k-6, t+6);
				if (!desmarca){
					sudokus.get(3).setNode(valor, k-6, t+6);
					sudokus.get(3).marcatge(k-6, t+6, valor);
				}else sudokus.get(3).desmarcatge(k-6, t+6, valor);		
			}
			else if(k>5 && t>5){

				sudokus.get(4).setValid(desmarca, k-6, t-6);
				if (!desmarca){
					sudokus.get(4).setNode(valor, k-6, t-6);
					sudokus.get(4).marcatge(k-6, t-6, valor);
				}else sudokus.get(4).desmarcatge(k-6, t-6, valor);	
			}
		}
	}
	
	/**
	 * Funció encarragada de actualitzar el taulell un cop recontruit
	 */
	//public void drawTauler(){
	//	interficie.updateBoard(restauratauler().getConfiguracio());
	//}
	
	
	/**
	 * Funció que transforma els 5 sudokus en un tauler de 21x21
	 * @return tauler 21x21
	 */
	public Tauler restauratauler(){
		Tauler t = new Tauler(21);
		for (int i = 0 ; i<21; i++){
			for(int j = 0 ; j<21; j++){	
				//Sudoku Mig
				if(j>5 && i>5 && j<15  && i<15){
					t.setNode(sudokus.get(0).getNode(i-6, j-6), (i),(j));
				}
				//Sudoku 1
				else if(j<9 && i<9){
					t.setNode(sudokus.get(1).getNode(i, j), i,j);
				}
				//Sudoku 2
				else if(j>11 && i<9){
					t.setNode((sudokus.get(2).getNode(i, j-12)) , i,(j));
				}
				//Sudoku 3
				else if(j<9 && i>11){
					t.setNode(sudokus.get(3).getNode(i-12, j), (i),j);
				}
				//Sudoku 4
				else if(j>11 && i>11){
					t.setNode(sudokus.get(4).getNode(i-12, j-12), (i),(j));
				}		
			}
		}
		return t;

	}



}