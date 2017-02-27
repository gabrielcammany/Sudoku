import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;

/**
 * Referent	al	format	del	fitxer	d’entrada,	aquest	presenta el	valor	de	cada	cel·la	del	tauler	separada	per	un	espai	en	blanc,	
 * essent	els	caràcters	‘-‘	i	‘*’	utilitzats	per	a	indicar	una	casella	a	descobrir	i	una	casella	fora	del	taulell	respectivament.	
 * A	continuació	es	mostren	els	fitxers	d’entrada	dels	exemples	del	Sudoku	de	9x9	i	Samurai	anteriors
 */
public class TractaFitxer {

	private BufferedReader reader;
	private BufferedReader reader2;
	private Tauler t;
	private String ruta;
	/**
	 * Obre el fitxer y el mirar.. falta acabar
	 */	

	private int tamany;
	int error = 0;

	/**
	 * Constructor
	 * @param file
	 */
	public TractaFitxer (String file){
		this.ruta = file;
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(ruta),Charset.forName("UTF-8")));
			reader2 = new BufferedReader(new InputStreamReader(new FileInputStream(ruta),Charset.forName("UTF-8")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Obte un taulell
	 * @return
	 */
	public Tauler getTauler(){
		tamany = determinaSudoku();
		if (tamany != 21 ) 		 System.out.println("Hem detectat que es un "+tamany+"X"+tamany);
		else if ( tamany == 21 ) System.out.println("Hem detectat que es un Samurai");
		else{//TODO
			System.out.println("El format no es valid, comprova el document/*");
		}
		t = new Tauler(tamany);
		carregaSudoku(t);
		return t;
	}

	/**
	 *  La funcio ens retorna el tamany del sudoku.
	 * @param filename
	 * @return
	 */

	public int determinaSudoku(){
		int c;
		int count=0, compt = 0;
		try {
			while((c = reader2.read()) != -1) {
				// si es espai o salt de linea o - o * o es un numero/lletra (mayus/min)
				if ( c == 32 ||  c == 10 || c==45 || c==42 || c==13 || ( c>=48 && c<=57 )){
					if ( c != 32 && c!= 10){
						count++; compt++;
						if(compt == 2) count--;
					}else compt = 0;
				}
				else return 0;
			}
		} catch (FileNotFoundException e) {	e.printStackTrace();} 
		catch (IOException e) { e.printStackTrace(); }
		//cerramos el reader
		try {	reader2.close(); } 
		catch (IOException e) {	e.printStackTrace();
		}
		return (int) Math.sqrt(count);
	}

	/**
	 * Carrega les dades en el taulell
	 * @param t
	 */
	public void carregaSudoku(Tauler t){
		int c,compt = 0, temp = 0;	
		try {
			int i = 0, j = 0;
			while((c = reader.read()) != -1) {  
				switch ( c ) {
				case 32:
					compt = 0;
					j++;
					break;
				case 10:
					compt = 0;
					i++;
					j=0;
					break;
				case 45:
					t.setNode( -1,i,j);
					t.setValid(Boolean.TRUE,i,j);
					break;
				case 42:
					break;
				default:
					if ( (c >= 48) && (c <= 57) ){
						t.setValid(Boolean.FALSE,i,j);
						compt++;
						if(compt == 1){
							temp = Character.getNumericValue(c);
							t.setNode( temp,i,j);
							//t.marcatge(i,  j, temp-1);
						}else if (compt == 2){
							//t.desmarcatge(i,  j, temp-1);
							temp = (temp*10) + Character.getNumericValue(c);
							t.setNode( temp,i,j);
							//t.marcatge(i,  j, temp-1);
							compt = 0;
						}
					}
				}
			}	
		} catch (FileNotFoundException e) {	e.printStackTrace();
		} catch (IOException e) { e.printStackTrace();
		}

	}
	/**
	 * Transforma el taulell en un fitxer
	 * @param t tauler
	 * @param file nom del nou fitxer
	 */
	public void desaSudoku(Tauler t, String file){
		try {
			PrintWriter pr = new PrintWriter(file);
			for (int i = 0; i < tamany; i++) {
				for (int j = 0; j < tamany; j++) {
					if(t.getNode(i, j)==0)  pr.print("*");
					else pr.print(t.getNode(i, j));
					if(j<tamany-1) pr.print(" ");
				}	
				if(i<tamany-1)pr.println();
			}
			pr.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			System.out.println("No such file exists.");
		}
	}	
}