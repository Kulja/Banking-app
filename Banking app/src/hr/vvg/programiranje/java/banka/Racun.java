package hr.vvg.programiranje.java.banka;

import hr.vvg.programiranje.java.osoba.Osoba;

import java.math.BigDecimal;

/**
 * Predstavlja entitet racuna koji je definiran osobom koja je vlasnik racuna i stanjem sredstava na racunu.
 * 
 * @author Kulja
 *
 */
public abstract class Racun {
	
	private Integer id;
	private Osoba vlasnikRacuna;
	private BigDecimal stanje;
	
	/**
	 * Inicijalizira podatak o vlasniku racuna i stanju sredstava na racunu.
	 * 
	 * @param vlasnikRacuna podatak o vlasniku racuna
	 * @param stanje podatak o stanju sredstava na racunu
	 */
	public Racun (Osoba vlasnikRacuna, BigDecimal stanje) {
		this.vlasnikRacuna = vlasnikRacuna;
		this.stanje = stanje;
	}
	
	/**
	 * Inicijalizira podatak o vlasniku racuna, stanju sredstava na racunu i id-u racuna.
	 * 
	 * @param id podatak o id-u racuna
	 * @param vlasnikRacuna podatak o vlasniku racuna
	 * @param stanje podatak o stanju sredstava na racunu
	 */
	public Racun (Integer id, Osoba vlasnikRacuna, BigDecimal stanje) {
		this.id = id;
		this.vlasnikRacuna = vlasnikRacuna;
		this.stanje = stanje;
	}
	
	public Osoba getVlasnikRacuna() {
		return vlasnikRacuna;
	}

	public BigDecimal getStanje() {
		return stanje;
	}
	
	public Integer getId() {
		return id;
	}
	
	/**
	 * Dodaje vrijednost iznosa stanju racuna.
	 * 
	 * @param iznos podatak o iznosu kojeg zelimo dodati postojecem stanju na racunu
	 */
	public void uplatiNaRacun (BigDecimal iznos) {
		this.stanje = this.stanje.add(iznos);
	}
	
	/**
	 * Oduzima vrijednost iznosa stanju racuna.
	 * 
	 * @param iznos podatak o iznosu kojeg zelimo oduzeti postojecem stanju na racunu
	 */
	public void isplatiSRacuna(BigDecimal iznos) {
		this.stanje = this.stanje.subtract(iznos);
	}

}
