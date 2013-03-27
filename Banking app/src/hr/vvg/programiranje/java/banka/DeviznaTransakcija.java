package hr.vvg.programiranje.java.banka;

import java.math.BigDecimal;
import java.math.RoundingMode;

//ova klasa nasljedjuje klasu Transakcija i implementira sucelje Devizna
public class DeviznaTransakcija extends Transakcija implements Devizna {
	
	private static final BigDecimal TECAJ_EUR_KN = new BigDecimal(7.5);
	
	// konstruktor koji u sebi poziva konstruktor klase Devizna (jer tu klasu ova klasa nasljedjuje)
	public DeviznaTransakcija(TekuciRacun polazniRacun, DevizniRacun dolazniRacun, BigDecimal iznos) {
		super(polazniRacun, dolazniRacun, iznos);
	}

	// metoda koja vraca iznos u eurima ukoliko je String valuta jednaka EUR
	// inace vraca nepromjenjeni iznos
	// override je tu zato jer ova metodu vec postoji u sucelju Devizna ali tamo nije (i ne moze biti) implementirana
	@Override
	public BigDecimal mjenjacnica(BigDecimal polazniIznosKN, String valuta) {
		
		if ("EUR".equals(valuta)) {
			 BigDecimal iznos = polazniIznosKN.divide(TECAJ_EUR_KN, 2, RoundingMode.HALF_UP);
			 return iznos;
		} else {
			return polazniIznosKN;
		}
	}
	
	// metoda koja provodi transakciju tako da prvo uzme novce s prvog racuna i 
	// onda taj iznos i valutu drugog racuna provodi kroz metodu mjenjacnica te
	// dobiveni iznos uplacuje na drugi racun
	// override je tu zato jer ova metodu vec postoji u klasi Transakcija ali 
	// ne zelimo da se ponasa kao u toj klasi vec zelimo da uzima u obzir iznos valute
	@Override
	public void provediTransakciju() {
		polazni.isplatiSRacuna(super.iznos);
		BigDecimal konvertiraniIznos = mjenjacnica(super.iznos, ((DevizniRacun)odlazni).getValuta()); 
		odlazni.uplatiNaRacun(konvertiraniIznos);
	}

}
