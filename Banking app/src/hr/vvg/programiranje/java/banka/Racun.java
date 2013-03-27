package hr.vvg.programiranje.java.banka;

import hr.vvg.programiranje.java.osoba.Osoba;

import java.math.BigDecimal;

public abstract class Racun {
	
	private Osoba vlasnikRacuna;
	private BigDecimal stanje;
	
	// konstruktor
	public Racun (Osoba vlasnikRacuna, BigDecimal stanje) {
		this.vlasnikRacuna = vlasnikRacuna;
		this.stanje = stanje;
	}
	
	public Osoba getVlasnikRacuna() {
		return vlasnikRacuna;
	}

	public BigDecimal getStanje() {
		return stanje;
	}
	
	// metoda za uplatu na raèun i spremanje novog stanja raèuna
	public void uplatiNaRacun (BigDecimal iznos) {
		this.stanje = this.stanje.add(iznos);
	}
	
	// metoda za isplatu sa raèuna i spremanje novog stanja raèuna
	public void isplatiSRacuna(BigDecimal iznos) {
		this.stanje = this.stanje.subtract(iznos);
	}

}
