import java.util.ArrayList;

/**
 * Aquesta classe sera la encarregada de fer les operacions y comprovacions nesesaries per 
 * solucionar qualsevol dels tipus de sudokus.
 */
public abstract class Game {
	private ArrayList<Tauler> solucions= new ArrayList<Tauler>();
	public int numero_solucions;
	//NO USAR
	public final Boolean Marcatge = Boolean.TRUE;

	/**
	 * Funcio principal del nostre problema, es la encarregada de resoldre el sudoku com indica el seu propi nom
	 * @param x sera el nostre taulell.
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 * @param s parametre de inmersio per gestionar quin taulell resoldre
	 */
	public abstract void resoldre(Tauler x, int k, int t, int s);
	
	/** Inicialitza el nivell 'k' del recorregut.
	 * 
	 * @param x sera el nostre taulell.
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 */
	public void preparaRecorregutNivell(Tauler x, int k, int t){
		x.setNode( 0 , k, t);
	}
	
	/** Funció encarregada de decidir on continuar la cerca
	 * Un cop la funcio "bona" ens diu que podem introduir un nombre en la nostre cerca
	 * la funcio numeroValid situa el "cursor" sobre la seguent casella a gestionar.
	 * 
	 * @param x sera el nostre taulell.
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 * @param s sera el parametre d'inmersió per canviar de sudoku
	 */
	 public void numeroValid (Tauler x, int k, int t, int s){
		 if ( t < x.getTamany()-1)	resoldre( x, k, t+1, s);
		else if (k<x.getTamany()-1)	resoldre( x, k+1,0, s);
			
	 }
	
	/**Comprova si hi ha més valors Xk, o valors en aquell nivell k.
	 *  
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 * @return Si existeix Successor tornem cert (1).
	 */
	public boolean hiHaSuccessor (Tauler x, int k, int t){
		if (x.getNode(k, t)< x.getTamany())  return true;
		else								 return false;
	}

	/**Aquesta Funció assigna a Xk el següent valor possible.
	 * 
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 */
	public void seguentGerma(Tauler x, int k, int t)	{
		x.setNode(x.getNode(k, t)+1, k, t);
	}
	
	/**
	 * Funcio que comprova si el sudoku a finalitzat
	 * @param x sera el nostre taulell.
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 *
	 */
	public boolean hiHaSolucio (Tauler x, int k, int t){
		return (k==x.getTamany()-1 && t==x.getTamany()-1);
	}
	
	/**
	 * Funcio per comprovar el grill, aquesta funció es usada quant no fem servir marcatge
	 * @param x sera el nostre taulell.
	 * @param k sera el numero de la fila.
	 * @param t sera el numero de la columna.
	 * 
	 */
	public boolean comprovaBOX(Tauler x, int k, int t){
		int fila=0, colum=0;
		//obtenim a quina fila i columna esta el nostre cursor.
		for(int i = 0; i<(x.getH());i++){
			if(k>=(x.getH())*(i+1)) fila++;
			if(t>=(x.getH())*(i+1)) colum++;
		}
		//busquem nombres respetits
		for (int i= x.getH()*(colum); i<(x.getH())*(colum+1); i++){
			for (int j=(x.getH())*(fila); j<(x.getH())*(fila+1); j++){
				if ( ( x.getNode(k, t) == x.getNode(j, i) ) && (j!=k || i!=t) ) return true;
			}
		}		
		return false;
	}
	
	/**
	 * funció que ens diu si el nombre  es correcte.
	 * @param x
	 * @param k
	 * @param t
	 * @return
	 */
	public boolean bona (Tauler x, int k, int t){
		if (Marcatge){
			int grid = (k/x.getH())*x.getH() + (t)/x.getH();
			if ( x.getNumBox(grid, x.getNode(k, t)-1) > 1) return false;
			if ( x.getNumFila(k, x.getNode(k, t)-1) > 1) return false;
			if ( x.getNumCol(t, x.getNode(k, t)-1) > 1) return false;
		}else { //no implementat x samurai
			if ( comprovaBOX(x,k,t)) return false;
			for (int i=0; i<x.getTamany(); i++){
				if (x.getNode(k, t)==x.getNode(k, i) && i!=t)return false;
				if (x.getNode(k, t)==x.getNode(i, t) && i!=k)return false;
			}
		}
		return true;
	}	
	public ArrayList<Tauler> getSolucions() {
		return solucions;
	}

	public void setSolucions(ArrayList<Tauler> solucions) {
		this.solucions = solucions;
	}

	public int getNumero_solucions() {
		return numero_solucions;
	}

	public void setNumero_solucions(int numero_solucions) {
		this.numero_solucions = numero_solucions;
	}

	public void gestionaSolució(Tauler x){
		numero_solucions++;
		Tauler t = new Tauler(x.getTamany());
		for (int i = 0; i < x.getTamany(); i++) {
			for (int j = 0; j < x.getTamany(); j++) {
				t.setNode(x.getNode(i, j), i, j);
			}
		}
		solucions.add(t);
		
	}
		
}