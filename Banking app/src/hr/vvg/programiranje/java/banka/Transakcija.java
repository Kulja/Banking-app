package hr.vvg.programiranje.java.banka;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Predstavlja entitet transakcije koja je definirana polaznim i odlaznim racunom te iznosom transakcije.
 * 
 * @author Kulja
 *
 * @param <T> podatak koji mora biti podklasa klase <code>Racun</code> i predstavlja polazni racun
 * @param <S> podatak koji mora biti podklasa klase <code>Racun</code> i predstavlja odlazni racun
 */
public class Transakcija<T extends Racun, S extends Racun> {
	
	protected Integer id;
	protected T polazni;
	protected S odlazni;
	protected BigDecimal iznos;
	protected Date datumTransakcije;
	
	/**
	 * Inicijalizira podatke o polaznom racunu, odlaznom racunu i iznosu transakcije.
	 * 
	 * @param polazni podatak o polaznom racunu
	 * @param odlazni podatak o odlaznom racunu
	 * @param iznos podatak o iznosu transakcije
	 */
	public Transakcija (T polazni, S odlazni, BigDecimal iznos) {
		this.polazni = polazni;
		this.odlazni = odlazni;
		this.iznos = iznos;
	}
	
	/**
	 * Inicijalizira podatke o polaznom racunu, odlaznom racunu, iznosu transakcije, id-u transakcije i datumu transakcije.
	 * 
	 * @param id podatak o id-u transakcije
	 * @param polazni podatak o polaznom racunu
	 * @param odlazni podatak o odlaznom racunu
	 * @param iznos podatak o iznosu transakcije
	 * @param datumTransakcije podatak o datumu transakcije
	 */
	public Transakcija (Integer id, T polazni, S odlazni, BigDecimal iznos, Date datumTransakcije) {
		this.id = id;
		this.polazni = polazni;
		this.odlazni = odlazni;
		this.iznos = iznos;
		this.datumTransakcije = datumTransakcije;
	}
	
	public T getPolazni() {
		return polazni;
	}

	public S getOdlazni() {
		return odlazni;
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
