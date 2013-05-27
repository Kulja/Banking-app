package hr.vvg.programiranje.java.banka;

import hr.vvg.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.vvg.programiranje.java.iznimke.NepodrzanaValutaException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * Predstavlja entitet devizne transakcije koja nasljeduje transakciju i implementira devizno sucelje.
 * 
 * @author Kulja
 *
 * @param <T> podatak koji mora biti podklasa klase <code>TekuciRacun</code> i predstavlja polazni racun
 * @param <S> podatak koji mora biti podklasa klase <code>DevizniRacun</code> i predstavlja odlazni racun
 */
public class DeviznaTransakcija<T extends TekuciRacun, S extends DevizniRacun> extends Transakcija<T, S> implements Devizna {
	
	/**
	 * Poziva konstruktor nadklase.
	 * 
	 * @param polazniRacun podatak o polaznom racunu
	 * @param dolazniRacun podatak o odlaznom racunu
	 * @param iznos podatak o iznosu transakcije
	 */
	public DeviznaTransakcija(T polazniRacun, S dolazniRacun, BigDecimal iznos) {
		super(polazniRacun, dolazniRacun, iznos);
	}
	
	
	/**
	 * Poziva konstruktor nadklase.
	 * 
	 * @param id podatak o id-u transakcije
	 * @param polazniRacun podatak o polaznom racunu
	 * @param dolazniRacun podatak o odlaznom racunu
	 * @param iznos podatak o iznos
	 * @param datumTransakcije podatak o datumu transakcije
	 */
	public DeviznaTransakcija(Integer id, T polazniRacun, S dolazniRacun, BigDecimal iznos, Date datumTransakcije) {
		super(id, polazniRacun, dolazniRacun, iznos, datumTransakcije);
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
	 * sredstava za isplatu baca iznimku <code>NedozvoljenoStanjeRacunaException</code>. Ukoliko ima, uzima u obzir valutu 
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
			BigDecimal konvertiraniIznos = mjenjacnica(super.iznos, odlazni.getValuta()); 
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
