package hr.vvg.programiranje.java.banka;

import hr.vvg.programiranje.java.osoba.Osoba;

import java.math.BigDecimal;

/**
 * Predstavlja entitet tekuceg racuna koji nasljeduje racun i definiran je brojem racuna.
 * 
 * @see Racun
 * 
 * @author Kulja
 *
 */
public class TekuciRacun extends Racun {
	
	private String brojRacuna;

	/**
	 * Poziva konstruktor nadklase i inicijalizira podatak o broju racuna.
	 * 
	 * @param vlasnikRacuna podatak o vlasniku racuna
	 * @param stanje podatak o stanju sredstava na racunu
	 * @param brojRacuna podatak o broju racuna
	 */
	public TekuciRacun(Osoba vlasnikRacuna, BigDecimal stanje, String brojRacuna) {
		super(vlasnikRacuna, stanje);
		this.brojRacuna = brojRacuna;
	}

	public String getBrojRacuna() {
		return brojRacuna;
	}
	
	public String toString() {
		return brojRacuna;
	}
	
}
