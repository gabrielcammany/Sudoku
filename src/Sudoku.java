/**
 * 
 */
public class Sudoku extends Game {

//	private SudokuGUI interficie;
	
	/**
	 * Constructor de Sudoku
	 * @param x tauler
	 */
	public Sudoku(Tauler x) {
		x.marcatgeInicialSudoku();
		//interficie = new SudokuGUI("Sudoku",800,800,x.getValid());
		resoldre(x,0,0,0);
	}
	
	public void resoldre(Tauler x, int k, int t, int s){
		if (x.getValid(k, t)){
			preparaRecorregutNivell(x, k, t);
			while ( hiHaSuccessor(x,k, t)){
				seguentGerma(x, k, t);
				x.marcatge(k,t, x.getNode(k, t)-1);
			//	interficie.updateBoard(x.getConfiguracio());
				if ( hiHaSolucio(x, k, t) ){	
					if ( bona(x, k, t) ){
						 gestionaSolució(x);
					}
				}
				else if ( bona(x, k, t) ) numeroValid(x, k, t,0);		
			x.desmarcatge(k,t, x.getNode(k, t)-1);
			}
			x.setNode(-1,k, t);
		}else{
			if(hiHaSolucio(x, k, t)){	
				 gestionaSolució(x);
			}
			numeroValid(x, k, t, s);

		}
	}
	
}