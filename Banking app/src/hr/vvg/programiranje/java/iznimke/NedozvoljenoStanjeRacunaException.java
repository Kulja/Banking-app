package hr.vvg.programiranje.java.iznimke;

public class NedozvoljenoStanjeRacunaException extends RuntimeException {
	
	// samo da maknemo warning
	private static final long serialVersionUID = 3479129953564570575L;

	// konstruktor koji prima jedan parametar
	public NedozvoljenoStanjeRacunaException (String message) {
		super(message);
	}
	
	// konstruktor koji prima dva parametra
	public NedozvoljenoStanjeRacunaException (String message, Throwable cause) {
		super(message, cause);
	}

}
