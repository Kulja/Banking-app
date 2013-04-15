package hr.vvg.programiranje.java.iznimke;

/**
 * Predstavlja iznimku nepodrzane valute.
 * 
 * @author Kulja
 *
 */
public class NepodrzanaValutaException extends Exception {

	private static final long serialVersionUID = 5563652576202375587L;

	/**
	 * Poziva konstrukor nadklase.
	 * 
	 * @param message podatak o poruci koja objasnjava razlog iznimke
	 */
	public NepodrzanaValutaException (String message) {
		super(message);
	}
	
	/**
	 * Poziva konstrukor nadklase.
	 * 
	 * @param message podatak o poruci koja objasnjava razlog iznimke
	 * @param cause podatak o samoj iznimci
	 */
	public NepodrzanaValutaException (String message, Throwable cause) {
		super(message, cause);
	}

}
