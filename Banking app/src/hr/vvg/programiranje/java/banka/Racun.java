package hr.vvg.programiranje.java.banka;

import hr.vvg.programiranje.java.osoba.Osoba;

import java.math.BigDecimal;

public class Racun {
	
	private Osoba vlasnikRacuna;
	private BigDecimal stanje;
	
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
	
	public void uplatiNaRacun (BigDecimal iznos) {
		this.stanje = this.stanje.add(iznos);
	}
	
	public void isplatiSRacuna(BigDecimal iznos) {
		this.stanje = this.stanje.subtract(iznos);
	}

}
