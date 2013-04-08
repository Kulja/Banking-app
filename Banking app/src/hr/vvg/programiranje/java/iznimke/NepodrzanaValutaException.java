package hr.vvg.programiranje.java.iznimke;

public class NepodrzanaValutaException extends Exception {

	// samo da maknemo warning
	private static final long serialVersionUID = 5563652576202375587L;

	// konstruktor koji prima jedan parametar
	public NepodrzanaValutaException (String message) {
		super(message);
	}
	
	// konstruktor koji prima dva parametra
	public NepodrzanaValutaException (String message, Throwable cause) {
		super(message, cause);
	}

}
