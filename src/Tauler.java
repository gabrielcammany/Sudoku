import java.util.Arrays;

public class Tauler {
	private int[][] configuracio;
	private boolean[][] valid;
	private int[][] numfila;
	private int[][] numcol;
	private int[][] numbox;
	private int tamany;
	private int h;
	
	//FILA 
	public void numFilaPlus (int x, int y){
		this.numfila[x][y]=(getNumFila(x,y))+1;
	}
	public void numFilaLess (int x, int y){
		this.numfila[x][y]=(getNumFila(x,y))-1;
	}
	public int getNumFila( int x, int y){
		return numfila[x][y];
	}
	public void deleteMarcatge(){
		for (int i = 0 ; i<tamany;i++){
			Arrays.fill( numfila[i], 0);
			Arrays.fill( numcol[i], 0);
			Arrays.fill( numbox[i], 0);
		}
	}
	//COL
	public void numColPlus (int x, int y){
		this.numcol[x][y]=(getNumCol(x,y))+1;
	}
	public void numColLess (int x, int y){
		this.numcol[x][y]=(getNumCol(x,y))-1;
	}
	public int getNumCol( int x, int y){
		return numcol[x][y];
	}
	//BOX
	public void numBoxPlus (int x, int y){
		this.numbox[x][y]=(getNumBox(x,y))+1;
	}
	public void numBoxLess (int x, int y){
		this.numbox[x][y]=(getNumBox(x,y))-1;
	}
	public int getNumBox( int x, int y){
		return numbox[x][y];
	}
	public void marcatge(int fila, int columna, int valor){
		int grid = (fila/getH())*getH() + (columna)/getH();
		numBoxPlus(grid, valor);
		numFilaPlus(fila, valor);
		numColPlus(columna, valor);
	}
	public void actualitzaMarcatge(int fila, int columna, int valor){
		
		numFilaPlus(fila, valor);
		numColPlus(columna, valor);
	}
	public void desmarcatge(int fila, int columna, int valor){
		int grid = (fila/getH())*getH() + (columna)/getH();
		numBoxLess(grid, valor);
		numFilaLess(fila, valor);	
		numColLess(columna, valor);
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public int getTamany() {
		return tamany;
	}
	
	public Tauler(int tamany) {
		this.tamany=tamany;
		configuracio = new int[tamany][tamany];
		valid = new boolean[tamany][tamany];
		numfila = new int[tamany][tamany];
		numcol = new int[tamany][tamany];
		numbox = new int[tamany][tamany];
		this.h = (int) Math.sqrt(tamany);
	}
	public int getNode (int x, int y){
		return configuracio[x][y];
	}
	public void setNode (int germa, int x, int y){
		this.configuracio[x][y]=germa;
	}
	public void setValid (boolean b, int x, int y){
		this.valid[x][y]=b;
	}
	public boolean getValid (int x, int y){
		return valid[x][y];
	}
	public int[][] getConfiguracio() {
		return configuracio;
	}
	public void setConfiguracio(int[][] configuracio) {
		this.configuracio = configuracio;
	}
	public boolean[][] getValid() {
		return valid;
	}
	public void setValid(boolean[][] valid) {
		this.valid = valid;
	}	
	public void marcatgeInicialSudoku(){	
		for (int i = 0; i<getTamany();i++){
			for (int j = 0; j<getTamany();j++){
				if(getNode(i, j) > 0){
					marcatge(i,  j, getNode(i, j)-1);
				}
			}
		}
	}
}
