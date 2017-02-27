import java.util.ArrayList;

public class Logica {
	private Game joc;
	private TractaFitxer tf;
	private Tauler tauler;
	private Time temps;
	private ArrayList<SudokuGUI> gui = new ArrayList<SudokuGUI>();;
	private ArrayList<Tauler> solucions= new ArrayList<Tauler>();
	String fitxerEntrada, fitxerSortida;
	int sortida;
    /**
     * Default constructor, inicialitzem i agafem els parametres de entrada
     */
    public Logica(String[] args) {	
    	if(args.length!=3){
    		System.out.println("Nombre de parametres incorrectes");
    		System.exit(1);
    	}
    	else{
    		fitxerEntrada=args[0];
    		sortida=Integer.parseInt(args[1]);
    		fitxerSortida=args[2];	
    		temps = new Time();
    		creaJoc();
    	}
    	
    }
    /** Creem i gestionem el joc i la seva finalització
     * 
     */
    public void creaJoc() {
    	System.out.println(temps.humanData(temps.getTemps_inici()));
    	tf = new TractaFitxer(fitxerEntrada);
    	tauler = tf.getTauler();
    	int tamany = tauler.getTamany();
		if (tamany == 21 ) 					      joc = new Samurai(tauler);
		else if ( tamany == 9 || tamany == 16)    joc = new Sudoku(tauler);	
    	temps.setTemps_final(); 
    	System.out.println(temps.humanData(temps.getTemps_final()));
    	System.out.println(temps.getDurationBreakdown(temps.gettempsExecucio()));	
    	System.out.println("El número de solucions trobades es de " +  joc.getNumero_solucions());
    	solucions = joc.getSolucions();

    	for(int r = 0 ; r<solucions.size();r++){
    		switch (sortida){
    		case 0:
    			System.exit(0);
    			break;
    		case 1:
    			System.out.println("\t\t Solucion "+r);
    			for (int i = 0 ; i<solucions.get(r).getTamany(); i++){
    				for(int j = 0 ; j<solucions.get(r).getTamany(); j++){
    					if (solucions.get(r).getNode(i, j) == 0) System.out.print("* ");
    					else System.out.print(solucions.get(r).getNode(i, j)+" ");
    				}
    				System.out.println("");
    			}
    			break;
    		case 2:
    			gui.add( new SudokuGUI("Solucio_"+r,800,800,tauler.getValid() ));
    			gui.get(r).updateBoard(solucions.get(r).getConfiguracio());
    			break;
    		case 3:
    			tf.desaSudoku(solucions.get(r),fitxerSortida+"_"+r);
    			break;

    		}

    	}
    }
    
}