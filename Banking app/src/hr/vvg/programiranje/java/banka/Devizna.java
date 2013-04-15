package hr.vvg.programiranje.java.banka;

import java.math.BigDecimal;

/**
 * Predstavlja metode za rad s devizama.
 * 
 * @author Kulja
 *
 */
public interface Devizna {
	
	/** 
	 * Konvertira kunski iznos u zadanu valuti ukoliko ta valuta postoji. Ako valuta ne postoji vraca nepromjenjeni iznos u kunama.
	 * 
	 * @param polazniIznosKN podatak o iznosu u kunama
	 * @param valuta podatak o valuti za koju radi konverziju
	 * 
	 * @return prebaceni iznos u zadanoj valuti ili pocetni iznos u kunama ako zadan valuta ne postoji
	 * @see hr.vvg.programiranje.java.banka.Devizna#mjenjacnica(java.math.BigDecimal, hr.vvg.programiranje.java.banka.Valuta)
	 */
	BigDecimal mjenjacnica(BigDecimal polazniIznosKN, Valuta valuta);

}
