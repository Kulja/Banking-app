package hr.vvg.programiranje.java.glavna;

import hr.vvg.programiranje.java.banka.DeviznaTransakcija;
import hr.vvg.programiranje.java.banka.DevizniRacun;
import hr.vvg.programiranje.java.banka.Racun;
import hr.vvg.programiranje.java.banka.TekuciRacun;
import hr.vvg.programiranje.java.banka.Transakcija;
import hr.vvg.programiranje.java.banka.Valuta;
import hr.vvg.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.vvg.programiranje.java.iznimke.NepodrzanaValutaException;
import hr.vvg.programiranje.java.osoba.Osoba;
import hr.vvg.programiranje.java.sortiranje.SortiranjeTransakcija;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Glavna {

	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
	private static final int BROJ_RACUNA = 2;
	
	public static void main(String[] args) {
		
		logger.info("Aplikacija pokrenuta!");
		
		Scanner unos = new Scanner(System.in);
		boolean neispravanUnos = true;
		
		// kreiranje liste za unos racuna te unosenje 
		// podataka o racunima dok unos ne bude takav da je prvi racun 
		// tekuci racun a drugi racun tekuci ili devizni racun
		List<Racun> listaRacuna = new ArrayList<>();
		do {
			for(int i = 0; i < BROJ_RACUNA; i++) {
				System.out.print("Unesite vrstu raèuna (T - tekuæi; ostalo - devizni): ");
				if("T".equals(unos.next())) {
					listaRacuna.add(i, unosTekucegRacuna(unos, i));
				} else {
					listaRacuna.add(i, unosDeviznogRacuna(unos, i));
				}
			}
			
			if (listaRacuna.get(0) instanceof DevizniRacun) {
				System.out.println("Pogreška kod unosa. Prvi raèun mora biti tekuæi raèun!");
				neispravanUnos = true;
			} else {
				neispravanUnos = false;
			}
		} while(neispravanUnos);
		
		// raw object
		SortedSet setTransakcija = new TreeSet<>(new SortiranjeTransakcija());
		do {
			// pozivanje metode unesiValjaniIznos koja od korisnika trazi da 
			// unosi iznos transakcije dok ne unese valjani iznos
			BigDecimal iznos = unesiValjaniIznos(unos, "Unesite iznos (KN) koji zelite prebaciti s prvog racuna na drugi: ", "Unesen neispravan iznos za transakciju!", "Unesen iznos transakcije: ");
			
			Transakcija<TekuciRacun, TekuciRacun> transakcija = null;
			DeviznaTransakcija<TekuciRacun, DevizniRacun> deviznaTransakcija = null;
			
			// provjerava je li odlazni racun tipa DevizniRacun ako je 
			// tada se kreira devizna transakcija a 
			// ukoliko nije tada se kreira kunska transakcija
			if(listaRacuna.get(1) instanceof DevizniRacun) {
				deviznaTransakcija = new DeviznaTransakcija<TekuciRacun, DevizniRacun>((TekuciRacun)listaRacuna.get(0), (DevizniRacun)listaRacuna.get(1), iznos);
			} else {
				transakcija = new Transakcija<TekuciRacun, TekuciRacun>((TekuciRacun)listaRacuna.get(0), (TekuciRacun)listaRacuna.get(1), iznos);
			}
			
			// provodenje same transakcije 
			// ukoliko dodje do iznimke transakcija se ne provodi
			try {
				// provjerava je li odlazni racun tipa DevizniRacun ako je 
				// tada se provodi devizna transakcija a 
				// ukoliko nije tada se provodi kunska transakcija
				if(listaRacuna.get(1) instanceof DevizniRacun) {
					deviznaTransakcija.provediTransakciju();
					setTransakcija.add(deviznaTransakcija);
				} else {
					transakcija.provediTransakciju();
					setTransakcija.add(transakcija);
				}
				
				String message = "Transakcija uspjesno provedena!";
				
				System.out.println(message);
				logger.info(message);
			} catch (NedozvoljenoStanjeRacunaException ex) {
				System.out.println(ex.getMessage());
				logger.error(ex.getMessage(), ex);
			}

			System.out.print("Želite li unijeti novu transakciju (D/N)? ");
			if(unos.next().equals("D")) {
				neispravanUnos = true;
			} else {
				neispravanUnos = false;
			}
			
		} while(neispravanUnos);

		System.out.println("Stanje prvog racuna: " + listaRacuna.get(0).getStanje() + "KN");
		if(listaRacuna.get(1) instanceof DevizniRacun) { 
			System.out.println("Stanje drugog racuna " + listaRacuna.get(1).getStanje() + ((DevizniRacun)listaRacuna.get(1)).getValuta());
		} else {
			System.out.println("Stanje drugog racuna: " + listaRacuna.get(1).getStanje() + "KN");
		}
		System.out.println("Transakcija s najviše sredstava: " + ((Transakcija<?, ?>)setTransakcija.first()).getIznos() + "KN");
		
		unos.close();
	}
	
	/**
	 * Prikuplja podatke o osobi i stanju na racunu te kreira objekt <TekuciRacun> kojeg vraca. 
	 * 
	 * @param unos predstavlja parser za unos podataka preko tipkovnice
	 * @param redniBrojRacuna podatak koji se koristi za ispis rednoj broja racuna koji se kreira
	 * @return kreirani <TekuciRacun>
	 */
	public static TekuciRacun unosTekucegRacuna(Scanner unos, int redniBrojRacuna) {
		redniBrojRacuna++;
		// prikupljanje podataka o osobi i stanju njezinog racuna
		// i logiranje unesenih podataka
		System.out.print("Unesite ime " + redniBrojRacuna + ". osobe: ");
		String ime = unos.next();
		logger.info("Uneseno ime vlasnika " + redniBrojRacuna + ". raèuna: " + ime);
		System.out.print("Unesite prezime " + redniBrojRacuna + ". osobe: ");
		String prezime = unos.next();
		logger.info("Uneseno prezime vlasnika " + redniBrojRacuna + ". raèuna: " + prezime);
		System.out.print("Unesite oib " + redniBrojRacuna + ". osobe: ");
		String oib = unos.next();
		logger.info("Unesen oib vlasnika " + redniBrojRacuna + ". raèuna: " + oib);
		System.out.print("Unesite broj racuna " + redniBrojRacuna + ". osobe: ");
		String brojRacuna = unos.next();
		logger.info("Unesen broj racuna " + redniBrojRacuna + ". raèuna: " + brojRacuna);

		// pozivanje metode unesiValjaniIznos koja od korisnika trazi da
		// unosi stanja racuna dok ne unese valjani iznos
		BigDecimal stanje = unesiValjaniIznos(unos, "Unesite stanje racuna prve osobe: ", "Unesen neispravan iznos za stanje prvog raèuna!", "Uneseno stanje racuna prve osobe: ");

		// kreiranje osobe
		Osoba prvaOsoba = new Osoba(ime, prezime, oib);
		
		// kreiranje i vracanje tekuceg racuna osobe
		return new TekuciRacun(prvaOsoba, stanje, brojRacuna);
	}
	
	/**
	 * Prikuplja podatke o osobi i stanju na racunu te kreira objekt <DevizniRacun> kojeg vraca.
	 * 
	 * @param unos predstavlja parser za unos podataka preko tipkovnice
	 * @param redniBrojRacuna podatak koji se koristi za ispis rednoj broja racuna koji se kreira
	 * @return kreirani <DevizniRacun>
	 */
	public static DevizniRacun unosDeviznogRacuna(Scanner unos, int redniBrojRacuna) {
		redniBrojRacuna++;
		// prikupljanje podataka o osobi i stanju njezinog racuna
		// i logiranje unesenih podataka
		System.out.print("Unesite ime " + redniBrojRacuna + ". osobe: ");
		String ime = unos.next();
		logger.info("Uneseno ime vlasnika " + redniBrojRacuna + ". raèuna: " + ime);
		System.out.print("Unesite prezime " + redniBrojRacuna + ". osobe: ");
		String prezime = unos.next();
		logger.info("Uneseno prezime vlasnika " + redniBrojRacuna + ". raèuna: " + prezime);
		System.out.print("Unesite oib " + redniBrojRacuna + ". osobe: ");
		String oib = unos.next();
		logger.info("Unesen oib vlasnika " + redniBrojRacuna + ". raèuna: " + oib);
		System.out.print("Unesite IBAN racuna " + redniBrojRacuna + ". osobe: ");
		String iban = unos.next();
		logger.info("Unesen IBAN " + redniBrojRacuna + ". raèuna: " + iban);

		// pozivanje metode unesiValjanuValutu koja od korisnika trazi da
		// unosi valute dok ne unese podrzanu valutu
		Valuta valuta = unesiValjanuValutu(unos);

		// pozivanje metode unesiValjaniIznos koja od korisnika trazi da
		// unosi stanja racuna dok ne unese valjani iznos
		BigDecimal stanje = unesiValjaniIznos(unos, "Unesite stanje racuna druge osobe: ", "Unesen neispravan iznos za stanje drugog raèuna!", "Uneseno stanje racuna druge osobe: ");

		// kreiranje osobe
		Osoba drugaOsoba = new Osoba(ime, prezime, oib);
		
		// kreiranje i vracanje deviznog racuna osobe
		return new DevizniRacun(drugaOsoba, stanje, iban, valuta);
	}
	
	/**
	 * Provjerava unosa brojeva. Trazi novi unos sve dok korisnik ne unese valjani broj.
	 * 
	 * @param unos predstavlja parser za unos podataka preko tipkovnice
	 * @param pitanje podatak o poruci koja se ispisuje korisniku na koju on odgovara
	 * @param odgovorZaKriviUnos podatak o poruci koja se ispisuje ukoliko je unos broja kriv
	 * @param odgovorZaDobarUnos podatak o poruci koja se ispisuje ukoliko je unos broja dobar
	 * @return valjani BigDecimal broj
	 */
	public static BigDecimal unesiValjaniIznos(Scanner unos, String pitanje, String odgovorZaKriviUnos, String odgovorZaDobarUnos) {
		BigDecimal broj = null;
		boolean neispravanUnos = true;
		
		do {
			System.out.print(pitanje);
			try {
				broj = unos.nextBigDecimal();
				neispravanUnos = false;
				logger.info(odgovorZaDobarUnos + broj);
			} catch (InputMismatchException ex) {
				System.out.println(odgovorZaKriviUnos);
				logger.error(odgovorZaKriviUnos, ex);
				unos.nextLine();
			}
		} while (neispravanUnos);
		
		return broj;
	}
	
	/**
	 * Provjeraca unos valute. Trazi novi unos sve dok unesena valute nije podrzana.
	 * 
	 * @param unos predstavlja parser za unos podataka preko tipkovnice
	 * @return enumeriranu valutu
	 */
	public static Valuta unesiValjanuValutu(Scanner unos) {
		Valuta valuta = null;
		boolean neispravanUnos = true;
		
		do {
			System.out.print("Unesite valutu racuna druge osobe: ");
			try {
				valuta = DeviznaTransakcija.provjeriValutu(unos.next());
				neispravanUnos = false;
				logger.info("Unesena valuta racuna druge osobe: " + valuta);
			} catch (NepodrzanaValutaException ex) {
				System.out.println(ex.getMessage());
				logger.error(ex.getMessage(), ex);
				unos.nextLine();
			}
		} while (neispravanUnos);
		
		return valuta;
	}
}
