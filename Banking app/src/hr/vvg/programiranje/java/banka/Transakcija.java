package hr.vvg.programiranje.java.banka;

import java.math.BigDecimal;

public class Transakcija {
	
	// varijabli sa modifikatorom protected 
	// je moguæe pristupati samo: 
	// iz klase u kojoj se nalazi, 
	// iz klasa koje se nalaze u istom paketu 
	// i klasa koje nasljeðuju klasu s tom varijablom
	protected Racun polazni;
	protected Racun odlazni;
	protected BigDecimal iznos;
	
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
