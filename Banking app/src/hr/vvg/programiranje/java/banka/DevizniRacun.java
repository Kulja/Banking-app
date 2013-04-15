package hr.vvg.programiranje.java.banka;

import hr.vvg.programiranje.java.osoba.Osoba;

import java.math.BigDecimal;

/**
 * Predstavlja entitet deviznog racuna koji nasljeduje racun i definiran je ibanom i valutom.
 * 
 * @see Racun
 * 
 * @author Kulja
 *
 */
public class DevizniRacun extends Racun {
	
	private String iban;
	private Valuta valuta;
	
	/**
	 * Poziva konstruktor nadklase i inicijalizira podatak o ibanu i valuti.
	 * 
	 * @param vlasnikRacuna podatak o vlasniku racuna
	 * @param stanje podatak o stanju sredstava na racunu
	 * @param iban podatak o internacionalnom broju racuna
	 * @param valuta podatak o valuti racuna
	 */
	public DevizniRacun(Osoba vlasnikRacuna, BigDecimal stanje, String iban, Valuta valuta) {
		super(vlasnikRacuna, stanje);
		this.iban = iban;
		this.valuta = valuta;
	}

	public String getIban() {
		return iban;
	}

	public Valuta getValuta() {
		return valuta;
	}
	
}
