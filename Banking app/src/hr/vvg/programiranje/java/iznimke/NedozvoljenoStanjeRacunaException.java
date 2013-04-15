package hr.vvg.programiranje.java.iznimke;

/**
 * Predstavlja iznimku nedozvoljenog stanja na racunu.
 * 
 * @author Kulja
 *
 */
public class NedozvoljenoStanjeRacunaException extends RuntimeException {
	
	private static final long serialVersionUID = 3479129953564570575L;

	/**
	 * Poziva konstrukor nadklase.
	 * 
	 * @param message podatak o poruci koja objasnjava razlog iznimke
	 */
	public NedozvoljenoStanjeRacunaException (String message) {
		super(message);
	}
	
	/**
	 * Poziva konstrukor nadklase.
	 * 
	 * @param message podatak o poruci koja objasnjava razlog iznimke
	 * @param cause podatak o samoj iznimci
	 */
	public NedozvoljenoStanjeRacunaException (String message, Throwable cause) {
		super(message, cause);
	}

}
