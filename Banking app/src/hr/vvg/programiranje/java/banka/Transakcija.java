package hr.vvg.programiranje.java.banka;

import java.math.BigDecimal;

/**
 * Predstavlja entitet transakcije koja je definirana polaznim i odlaznim racunom te iznosom transakcije.
 * 
 * @author Kulja
 *
 */
public class Transakcija {
	
	protected Racun polazni;
	protected Racun odlazni;
	protected BigDecimal iznos;
	
	/**
	 * Inicijalizira podatke o polaznom racunu, odlaznom racunu i iznosu transakcije.
	 * 
	 * @param polazni podatak o polaznom racunu
	 * @param odlazni podatak o odlaznom racunu
	 * @param iznos podatak o iznosu transakcije
	 */
	public Transakcija (Racun polazni, Racun odlazni, BigDecimal iznos) {
		this.polazni = polazni;
		this.odlazni = odlazni;
		this.iznos = iznos;
	}
	
	public BigDecimal getIznos() {
		return iznos;
	}
	
	/**
	 * Izvrsava transakciju pozivanjem metode za isplatu sredstava nad polaznim racunom i uplatu sredstava nad odlaznim racunom.
	 */
	public void provediTransakciju () {
		this.polazni.isplatiSRacuna(this.iznos);
		this.odlazni.uplatiNaRacun(this.iznos);
	}
	
}
