package hr.vvg.programiranje.java.glavna;

import hr.vvg.programiranje.java.banka.DeviznaTransakcija;
import hr.vvg.programiranje.java.banka.DevizniRacun;
import hr.vvg.programiranje.java.banka.TekuciRacun;
import hr.vvg.programiranje.java.banka.Transakcija;
import hr.vvg.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.vvg.programiranje.java.iznimke.NepodrzanaValutaException;
import hr.vvg.programiranje.java.osoba.Osoba;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

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
		String valuta;
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
		
		// dohvacanje iznosa kojeg zelimo prebaciti s jednog racuna na drugi
		System.out.print("Unesite iznos (KN) koji zelite prebaciti s prvog racuna na drugi: ");
		BigDecimal iznos = unos.nextBigDecimal();
		logger.info("Unesen iznos (KN) koji zelimo prebaciti s prvog racuna na drugi: " + iznos);
		
		// kreiranje transakcije
		Transakcija transakcija = new DeviznaTransakcija(racunPrveOsobe, racunDrugeOsobe, iznos);
		
		// provodenje same transakcije 
		// ukoliko dodje do iznimke transakcija se ne provodi
		try {
			transakcija.provediTransakciju();
			String message = "Transakcija uspjesno provedena!";
			System.out.println(message);
			logger.info(message);
		} catch (NedozvoljenoStanjeRacunaException ex) {
			System.out.println(ex.getMessage());
			logger.error(ex.getMessage(), ex);
		}
		
		System.out.println("Stanje prvog racuna: " + racunPrveOsobe.getStanje() + "KN");
		System.out.println("Stanje drugog racuna " + racunDrugeOsobe.getStanje() + racunDrugeOsobe.getValuta());
		
		unos.close();
	}
	
	// metoda koja provjerava je li korisnik upisao dobar broj
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
		} while (neispravanUnos == true);
		
		return broj;
	}
	
	// metoda koja provjerava je li unesena valuta podržana
	public static String unesiValjanuValutu() {
		String valuta;
		boolean neispravanUnos = true;
		
		do {
			System.out.print("Unesite valutu racuna druge osobe: ");
			valuta = unos.next();
			try {
				DeviznaTransakcija.provjeriValutu(valuta);
				neispravanUnos = false;
				logger.info("Unesena valuta racuna druge osobe: " + valuta);
			} catch (NepodrzanaValutaException ex) {
				System.out.println(ex.getMessage());
				logger.error(ex.getMessage(), ex);
				unos.nextLine();
			}
		} while (neispravanUnos == true);
		
		return valuta;
	}
}
