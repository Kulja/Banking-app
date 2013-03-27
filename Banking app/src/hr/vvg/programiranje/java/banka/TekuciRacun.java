package hr.vvg.programiranje.java.banka;

import hr.vvg.programiranje.java.osoba.Osoba;

import java.math.BigDecimal;

// ova klasa nasljedjuje klasu Racun
public class TekuciRacun extends Racun {
	
	private String brojRacuna;
	
	// konstruktor koji u sebi poziva konstruktor klase Racun (jer tu klasu ova klasa nasljedjuje)
	public TekuciRacun(Osoba vlasnikRacuna, BigDecimal stanje, String brojRacuna) {
		super(vlasnikRacuna, stanje);
		this.brojRacuna = brojRacuna;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}
	
}
