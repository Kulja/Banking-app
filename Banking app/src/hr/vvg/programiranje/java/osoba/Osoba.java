package hr.vvg.programiranje.java.osoba;

public class Osoba {
	
	private String ime;
	private String prezime;
	private String oib;
	
	// konstruktor
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
