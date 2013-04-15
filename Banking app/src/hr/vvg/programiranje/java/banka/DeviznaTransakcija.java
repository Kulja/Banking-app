package hr.vvg.programiranje.java.banka;

import hr.vvg.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.vvg.programiranje.java.iznimke.NepodrzanaValutaException;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Predstavlja entitet devizne transakcije koja nasljeduje transakciju i implementira devizno sucelje.
 * 
 * @see Transakcija
 * 
 * @author Kulja
 *
 */
public class DeviznaTransakcija extends Transakcija implements Devizna {
	
	/**
	 * Poziva konstruktor nadklase
	 * 
	 * @param polazniRacun podatak o polaznom racunu
	 * @param dolazniRacun podatak o odlaznom racunu
	 * @param iznos podatak o iznosu transakcije
	 */
	public DeviznaTransakcija(TekuciRacun polazniRacun, DevizniRacun dolazniRacun, BigDecimal iznos) {
		super(polazniRacun, dolazniRacun, iznos);
	}
	
	@Override
	public BigDecimal mjenjacnica(BigDecimal polazniIznosKN, Valuta valuta) {
		for (Tecaj tecaj : Tecajnica.dohvatiTecajeve()) {
			if (tecaj.getValuta().compareTo(valuta) == 0) {
				BigDecimal iznos = polazniIznosKN.divide(tecaj.getTecajPremaKuni(), 2, RoundingMode.HALF_UP);
				return iznos;
			}
		}
		return polazniIznosKN;
	}
	
	/** 
	 * Izvrsava transakciju ukoliko na polaznom racunu ima dovoljno sredstava za isplatu. Ukoliko nema dovoljno 
	 * sredstava za isplatu baca iznimku NedozvoljenoStanjeRacunaException. Ukoliko ima, uzima u obzir valutu 
	 * drugog racuna i radi konverziju iz kune u valutu odlaznog racuna pozivanjem metode mjenjacnica. 
	 * 
	 * @see hr.vvg.programiranje.java.banka.Transakcija#provediTransakciju()
	 * @throws NedozvoljenoStanjeRacunaException
	 */
	@Override
	public void provediTransakciju() {
		if(polazni.getStanje().compareTo(iznos) == -1) {
			throw new NedozvoljenoStanjeRacunaException("Na prvom raèunu nema dovoljno sredstava (" + polazni.getStanje() + ") za provoðenje transakcije! (" + iznos + ")");
		} else {
			polazni.isplatiSRacuna(super.iznos);
			BigDecimal konvertiraniIznos = mjenjacnica(super.iznos, ((DevizniRacun)odlazni).getValuta()); 
			odlazni.uplatiNaRacun(konvertiraniIznos);
		}
	}
	
	/**
	 * Provjerava je li valuta tipa string prodrzana u enumeraciji valuta. Ukoliko je, vraca njenu enumeriranu vrijednost.
	 * 
	 * @param valuta podatak o valuti za koju provjeravamo je li podrzana
	 * @return enumeriranu valutu
	 * @throws NepodrzanaValutaException
	 * @see Valuta
	 */
	public static Valuta provjeriValutu(String valuta) throws NepodrzanaValutaException {
		try {
			return Valuta.valueOf(valuta);
		} catch (IllegalArgumentException ex) {
			throw new NepodrzanaValutaException("Valuta " + valuta + " nije podržana!", ex);
		}
	}

}
