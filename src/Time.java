import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * clase encarregada de la gestió del recursos del temps
 */
public class Time {
	
	/**
	 * Variables on guardarem el temps inicial i final
	 */
	private long temps_inici, temps_final;
	
	/**
	 * Constructor, setejem el temps inicial
	 */
	public Time() {
		setTemps_inici(System.currentTimeMillis());
	}
	
	/**
	 * obtenir el Temps de inici (creació del objecte)
	 * @return long temps inicial
	 */
    public long getTemps_inici() {
		return temps_inici;
	}

    /**
     * Seteja el Temps inicial
     * @param temps_inici
     */
	public void setTemps_inici(long temps_inici) {
		this.temps_inici = temps_inici;
	}

	/**
	 * Obte el temps final
	 * @return long amb temps final
	 */
	public long getTemps_final() {
		return temps_final;
	}
	/**
	 * Setejem el temps final
	 * @param temps_final
	 */
	public void setTemps_final() {
		this.temps_final = System.currentTimeMillis();;
	}
	
	/**
	 *  Retorna el temps total d'execucio
	 * 
	 */
    public long gettempsExecucio(){
    	return getTemps_final()-getTemps_inici();
    }
    
    /**
     * Mostra per Pantalla dates en format huma
     */

    public String humanData(long millis)   {
  		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSSSSS");    
  		Date resultdate = new Date(millis);    
          return(sdf.format(resultdate));
      }
    
    /**
     * Funcio que transforma el temps recoregut en format llegible
     * @param millis
     * @return
     */
    public String getDurationBreakdown(long millis)
    {
        if(millis < 0)  throw new IllegalArgumentException("Duration must be greater than zero!");
        
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= hours*60*60*1000;
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= minutes*60*1000;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
        millis -=  seconds*1000;

        StringBuilder sb = new StringBuilder(64);
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds ");
        sb.append(millis);
        sb.append(" Miliseconds");

        return(sb.toString());
    }
}