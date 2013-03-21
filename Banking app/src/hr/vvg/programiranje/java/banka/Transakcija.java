package hr.vvg.programiranje.java.banka;

import java.math.BigDecimal;

public class Transakcija {
	
	private Racun polazni;
	private Racun odlazni;
	private BigDecimal iznos;
	
	// konstruktor
	public Transakcija (Racun polazni, Racun odlazni, BigDecimal iznos) {
		this.polazni = polazni;
		this.odlazni = odlazni;
		this.iznos = iznos;
	}
	
	// metoda za izvrsavanje transakcije
	// provodimo ju pozivanjem metoda iz klase Racun
	public void provediTransakciju () {
		this.odlazni.uplatiNaRacun(this.iznos);
		this.polazni.isplatiSRacuna(this.iznos);
	}
	
}
