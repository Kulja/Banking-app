package hr.vvg.programiranje.java.banka;

import hr.vvg.programiranje.java.osoba.Osoba;

import java.math.BigDecimal;

//ova klasa nasljedjuje klasu Racun
public class DevizniRacun extends Racun {
	
	private String iban;
	private String valuta;
	
	// konstruktor koji u sebi poziva konstruktor klase Racun (jer tu klasu ova klasa nasljedjuje)
	public DevizniRacun(Osoba vlasnikRacuna, BigDecimal stanje, String iban, String valuta) {
		super(vlasnikRacuna, stanje);
		this.iban = iban;
		this.valuta = valuta;
	}

	public String getIban() {
		return iban;
	}

	public String getValuta() {
		return valuta;
	}
	
}
