package hr.vvg.programiranje.java.glavna;

import hr.vvg.programiranje.java.banka.DeviznaTransakcija;
import hr.vvg.programiranje.java.banka.DevizniRacun;
import hr.vvg.programiranje.java.banka.TekuciRacun;
import hr.vvg.programiranje.java.banka.Transakcija;
import hr.vvg.programiranje.java.banka.Valuta;
import hr.vvg.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.vvg.programiranje.java.iznimke.NepodrzanaValutaException;
import hr.vvg.programiranje.java.osoba.Osoba;
import hr.vvg.programiranje.java.sortiranje.SortiranjeTransakcija;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Glavna {

	private static final Logger logger = LoggerFactory.getLogger(Glavna.class);
	private static Scanner unos = new Scanner(System.in);

	public static void main(String[] args) {
		
		String ime;
		String prezime;
		String oib;
		String brojRacuna;
		String iban;
		Valuta valuta;
		BigDecimal stanje = null;
		
		logger.info("Aplikacija pokrenuta!");
		
		// prikupljanje podataka o prvoj osobi i stanju njezinog racuna
		// i logiranje unesenih podataka
		System.out.print("Unesite ime prve osobe: ");
		ime = unos.next();
		logger.info("Uneseno ime vlasnika prvog raèuna: " + ime);
		System.out.print("Unesite prezime prve osobe: ");
		prezime = unos.next();
		logger.info("Uneseno prezime vlasnika prvog raèuna: " + prezime);
		System.out.print("Unesite oib prve osobe: ");
		oib = unos.next();
		logger.info("Unesen oib vlasnika prvog raèuna: " + oib);
		System.out.print("Unesite broj racuna prve osobe: ");
		brojRacuna = unos.next();
		logger.info("Unesen broj racuna prvog raèuna: " + brojRacuna);
		
		// pozivanje metode unesiValjaniIznos koja od korisnika trazi da 
		// unosi stanja racuna dok ne unese valjani iznos
		stanje = unesiValjaniIznos("Unesite stanje racuna prve osobe: ", "Unesen neispravan iznos za stanje prvog raèuna!", "Uneseno stanje racuna prve osobe: ");
		
		// kreiranje prve osobe
		Osoba prvaOsoba = new Osoba(ime, prezime, oib);
		// kreiranje tekuceg racuna prve osobe
		TekuciRacun racunPrveOsobe = new TekuciRacun(prvaOsoba, stanje, brojRacuna);
		
		// prikupljanje podataka o drugoj osobi i stanju njezinog racuna
		// i logiranje unesenih podataka
		System.out.print("Unesite ime druge osobe: ");
		ime = unos.next();
		logger.info("Uneseno ime vlasnika drugog raèuna: " + ime);
		System.out.print("Unesite prezime druge osobe: ");
		prezime = unos.next();
		logger.info("Uneseno prezime vlasnika drugog raèuna: " + prezime);
		System.out.print("Unesite oib druge osobe: ");
		oib = unos.next();
		logger.info("Unesen oib vlasnika drugog raèuna: " + oib);
		System.out.print("Unesite IBAN racuna druge osobe: ");
		iban = unos.next();
		logger.info("Unesen IBAN drugog raèuna: " + iban);
		
		// pozivanje metode unesiValjanuValutu koja od korisnika trazi da 
		// unosi valute dok ne unese podrzanu valutu
		valuta = unesiValjanuValutu();

		// pozivanje metode unesiValjaniIznos koja od korisnika trazi da 
		// unosi stanja racuna dok ne unese valjani iznos
		stanje = unesiValjaniIznos("Unesite stanje racuna druge osobe: ", "Unesen neispravan iznos za stanje drugog raèuna!", "Uneseno stanje racuna druge osobe: ");
		
		// kreiranje druge osobe
		Osoba drugaOsoba = new Osoba(ime, prezime, oib);
		// kreiranje deviznog racuna druge osobe
		DevizniRacun racunDrugeOsobe = new DevizniRacun(drugaOsoba, stanje, iban, valuta);
		
		boolean neispravanUnos = true;
		SortedSet<Transakcija> setTransakcija = new TreeSet<>(new SortiranjeTransakcija());
		do {
			// pozivanje metode unesiValjaniIznos koja od korisnika trazi da 
			// unosi iznos transakcije dok ne unese valjani iznos
			BigDecimal iznos = unesiValjaniIznos("Unesite iznos (KN) koji zelite prebaciti s prvog racuna na drugi: ", "Unesen neispravan iznos za transakciju!", "Unesen iznos transakcije: ");
			
			// kreiranje transakcije
			Transakcija transakcija = new DeviznaTransakcija(racunPrveOsobe, racunDrugeOsobe, iznos);
			
			// provodenje same transakcije 
			// ukoliko dodje do iznimke transakcija se ne provodi
			try {
				transakcija.provediTransakciju();
				String message = "Transakcija uspjesno provedena!";
				
				// dodavanje transakcije u set transakcija
				setTransakcija.add(transakcija);
				
				System.out.println(message);
				logger.info(message);
			} catch (NedozvoljenoStanjeRacunaException ex) {
				System.out.println(ex.getMessage());
				logger.error(ex.getMessage(), ex);
			}

			System.out.print("Želite li unijeti novu transakciju (D/N)? ");
			if(!unos.next().equals("D")) {
				neispravanUnos = false;
			}
			
		} while(neispravanUnos);

		System.out.println("Stanje prvog racuna: " + racunPrveOsobe.getStanje() + "KN");
		System.out.println("Stanje drugog racuna " + racunDrugeOsobe.getStanje() + racunDrugeOsobe.getValuta());
		System.out.println("Transakcija s najviše sredstava: " + setTransakcija.first().getIznos() + "KN");
		
		unos.close();
	}
	
	/**
	 * Provjerava unosa brojeva. Trazi novi unos sve dok korisnik ne unese valjani broj.
	 * 
	 * @param pitanje podatak o poruci koja se ispisuje korisniku na koju on odgovara
	 * @param odgovorZaKriviUnos podatak o poruci koja se ispisuje ukoliko je unos broja kriv
	 * @param odgovorZaDobarUnos podatak o poruci koja se ispisuje ukoliko je unos broja dobar
	 * @return valjani BigDecimal broj
	 */
	public static BigDecimal unesiValjaniIznos(String pitanje, String odgovorZaKriviUnos, String odgovorZaDobarUnos) {
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
	 * @return enumeriranu valutu
	 */
	public static Valuta unesiValjanuValutu() {
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
