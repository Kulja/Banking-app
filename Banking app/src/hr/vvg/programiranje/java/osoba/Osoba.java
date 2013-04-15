package hr.vvg.programiranje.java.osoba;

/**
 * Predstavlja entitet osobe koji je definiran imenom, prezimenom i oibom.
 * 
 * @author Kulja
 *
 */
public class Osoba {
	
	private String ime;
	private String prezime;
	private String oib;
	
	/**
	 * Inicijalizira podatk o imenu, prezimenu i oibu.
	 * 
	 * @param ime podatak o imenu osobe
	 * @param prezime podatak o prezimenu osobe
	 * @param oib podatak o oibu osobe
	 */
	public Osoba (String ime, String prezime, String oib) {
		this.ime = ime;
		this.prezime = prezime;
		this.oib = oib;
	}
	
	public String getIme() {
		return ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public String getOib() {
		return oib;
	}

}
