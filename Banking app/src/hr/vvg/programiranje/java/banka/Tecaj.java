package hr.vvg.programiranje.java.banka;

import java.math.BigDecimal;

/**
 * Predstavlja entitet tecaja koji je definiran valutom i tecajem prema kuni.
 * 
 * @author Kulja
 *
 */
public class Tecaj {
	
	private Valuta valuta;
	private BigDecimal tecajPremaKuni;
	
	/**
	 * Inicijalizira podatak o valuti i tecaju prema kuni.
	 * 
	 * @param valuta podatak o valuti
	 * @param tecajPremaKuni podatak o vrijednosti tecaja prema kuni
	 */
	public Tecaj(Valuta valuta, BigDecimal tecajPremaKuni) {
		this.valuta = valuta;
		this.tecajPremaKuni = tecajPremaKuni;
	}

	public Valuta getValuta() {
		return valuta;
	}

	public BigDecimal getTecajPremaKuni() {
		return tecajPremaKuni;
	}

}
