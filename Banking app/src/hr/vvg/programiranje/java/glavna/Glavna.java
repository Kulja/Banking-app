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

import java.io.FileWriter;
import java.io.IOException;
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
	public static final String NAZIV_DATOTEKE = "uneseniPodaci.txt";
	
	public static void main(String[] args) {
		
		logger.info("Aplikacija pokrenuta!");
		
		FileWriter writer = null;
		try {
			writer = new FileWriter(NAZIV_DATOTEKE);
		} catch (IOException e) {
			logger.error("Doslo je do pogreske kod otvaranja datoteke", e.getMessage());
			e.printStackTrace();
		}
		
		Scanner unos = new Scanner(System.in);
		boolean neispravanUnos = true;
		
		// kreiranje liste za unos racuna te unosenje 
		// podataka o racunima dok unos ne bude takav da je prvi racun 
		// tekuci racun a drugi racun tekuci ili devizni racun
		List<Racun> listaRacuna = new ArrayList<>();
		do {
			for(int i = 0; i < BROJ_RACUNA; i++) {
				System.out.print("Unesite vrstu ra�una (T - teku�i; ostalo - devizni): ");
				
				if("T".equals(unos.next())) {
					listaRacuna.add(i, unosTekucegRacuna(unos, writer, i));
				} else {
					listaRacuna.add(i, unosDeviznogRacuna(unos, writer, i));
				}
			}
			
			if (listaRacuna.get(0) instanceof DevizniRacun) {
				String tekstPogreske = "Pogre�ka kod unosa. Prvi ra�un mora biti teku�i ra�un!";
				System.out.println(tekstPogreske);
				zapisiUDatoteku(writer, tekstPogreske);
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
			zapisiUDatoteku(writer, "Iznos transakcije: " + iznos + "KN");
			
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

			System.out.print("�elite li unijeti novu transakciju (D/N)? ");
			if(unos.next().equals("D")) {
				neispravanUnos = true;
			} else {
				neispravanUnos = false;
			}
			
		} while(neispravanUnos);

		System.out.println("Stanje prvog racuna: " + listaRacuna.get(0).getStanje() + "KN");
		zapisiUDatoteku(writer, "Stanje na prvom ra�unu: " + listaRacuna.get(0).getStanje() + "KN");
		if(listaRacuna.get(1) instanceof DevizniRacun) { 
			System.out.println("Stanje drugog racuna " + listaRacuna.get(1).getStanje() + ((DevizniRacun)listaRacuna.get(1)).getValuta());
			zapisiUDatoteku(writer, "Stanje na drugom ra�unu: " + listaRacuna.get(1).getStanje() + ((DevizniRacun)listaRacuna.get(1)).getValuta());
		} else {
			System.out.println("Stanje drugog racuna: " + listaRacuna.get(1).getStanje() + "KN");
			zapisiUDatoteku(writer, "Stanje na drugom ra�unu: " + listaRacuna.get(1).getStanje() + "KN");
		}
		System.out.println("Transakcija s najvi�e sredstava: " + ((Transakcija<?, ?>)setTransakcija.first()).getIznos() + "KN");
		
		unos.close();
		
		try {
			writer.close();
		} catch (IOException e) {
			logger.error("Doslo je do pogreske kod zatvaranja datoteke", e.getMessage());
			e.printStackTrace();
		}

	}
	
	/**
	 * Zapisuje poruke u odabranu datoteku
	 * 
	 * @param writer predstavlja datoteku u koju se zapisuju poruke
	 * @param poruka predstavlja poruku koja se zapisuje u datoteku
	 */
	public static void zapisiUDatoteku(FileWriter writer, String poruka) {
		try {
			writer.write(poruka + "\n");
		} catch (IOException e) {
			logger.error("Doslo je kod pogreske u zapisivanju poruke u datoteku", e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Prikuplja podatke o osobi i stanju na racunu te kreira objekt <TekuciRacun> kojeg vraca. 
	 * 
	 * @param unos predstavlja parser za unos podataka preko tipkovnice
	 * @param writer predstavlja datoteku u koju se zapisuju svi podaci
	 * @param redniBrojRacuna podatak koji se koristi za ispis rednoj broja racuna koji se kreira
	 * @return kreirani <TekuciRacun>
	 */
	public static TekuciRacun unosTekucegRacuna(Scanner unos, FileWriter writer, int redniBrojRacuna) {
		redniBrojRacuna++;
		// prikupljanje podataka o osobi i stanju njezinog racuna, 
		// logiranje unesenih podataka i njihovo spremanje u datoteku
		
		zapisiUDatoteku(writer, "Vrsta " + redniBrojRacuna + " racuna: T");
		
		System.out.print("Unesite ime " + redniBrojRacuna + ". osobe: ");
		String ime = unos.next();
		logger.info("Uneseno ime vlasnika " + redniBrojRacuna + ". ra�una: " + ime);
		zapisiUDatoteku(writer, "Ime vlasnika " + redniBrojRacuna + ". ra�una: " + ime);
		System.out.print("Unesite prezime " + redniBrojRacuna + ". osobe: ");
		String prezime = unos.next();
		logger.info("Uneseno prezime vlasnika " + redniBrojRacuna + ". ra�una: " + prezime);
		zapisiUDatoteku(writer, "Prezime vlasnika " + redniBrojRacuna + ". ra�una: " + prezime);
		System.out.print("Unesite oib " + redniBrojRacuna + ". osobe: ");
		String oib = unos.next();
		logger.info("Unesen oib vlasnika " + redniBrojRacuna + ". ra�una: " + oib);
		zapisiUDatoteku(writer, "OIB vlasnika " + redniBrojRacuna + ". ra�una: " + oib);
		System.out.print("Unesite broj racuna " + redniBrojRacuna + ". osobe: ");
		String brojRacuna = unos.next();
		logger.info("Unesen broj " + redniBrojRacuna + ". ra�una: " + brojRacuna);
		zapisiUDatoteku(writer, "Broj " + redniBrojRacuna + ". ra�una: " + brojRacuna);

		// pozivanje metode unesiValjaniIznos koja od korisnika trazi da
		// unosi stanja racuna dok ne unese valjani iznos
		BigDecimal stanje = unesiValjaniIznos(unos, "Unesite stanje racuna prve osobe: ", "Unesen neispravan iznos za stanje prvog ra�una!", "Uneseno stanje racuna prve osobe: ");
		zapisiUDatoteku(writer, "Stanje " + redniBrojRacuna + ". ra�una: " + stanje + "KN");
		
		// kreiranje osobe
		Osoba prvaOsoba = new Osoba(ime, prezime, oib);
		
		// kreiranje i vracanje tekuceg racuna osobe
		return new TekuciRacun(prvaOsoba, stanje, brojRacuna);
	}
	
	/**
	 * Prikuplja podatke o osobi i stanju na racunu te kreira objekt <DevizniRacun> kojeg vraca.
	 * 
	 * @param unos predstavlja parser za unos podataka preko tipkovnice
	 * @param writer predstavlja datoteku u koju se zapisuju svi podaci
	 * @param redniBrojRacuna podatak koji se koristi za ispis rednoj broja racuna koji se kreira
	 * @return kreirani <DevizniRacun>
	 */
	public static DevizniRacun unosDeviznogRacuna(Scanner unos, FileWriter writer, int redniBrojRacuna) {
		redniBrojRacuna++;
		// prikupljanje podataka o osobi i stanju njezinog racuna, 
		// logiranje unesenih podataka i njihovo spremanje u datoteku
		
		zapisiUDatoteku(writer, "Vrsta " + redniBrojRacuna + " racuna: D");
		
		System.out.print("Unesite ime " + redniBrojRacuna + ". osobe: ");
		String ime = unos.next();
		logger.info("Uneseno ime vlasnika " + redniBrojRacuna + ". ra�una: " + ime);
		zapisiUDatoteku(writer, "Ime vlasnika " + redniBrojRacuna + ". ra�una: " + ime);
		System.out.print("Unesite prezime " + redniBrojRacuna + ". osobe: ");
		String prezime = unos.next();
		logger.info("Uneseno prezime vlasnika " + redniBrojRacuna + ". ra�una: " + prezime);
		zapisiUDatoteku(writer, "Prezime vlasnika " + redniBrojRacuna + ". ra�una: " + prezime);
		System.out.print("Unesite oib " + redniBrojRacuna + ". osobe: ");
		String oib = unos.next();
		logger.info("Unesen oib vlasnika " + redniBrojRacuna + ". ra�una: " + oib);
		zapisiUDatoteku(writer, "OIB vlasnika " + redniBrojRacuna + ". ra�una: " + oib);
		System.out.print("Unesite IBAN racuna " + redniBrojRacuna + ". osobe: ");
		String iban = unos.next();
		logger.info("Unesen IBAN " + redniBrojRacuna + ". ra�una: " + iban);
		zapisiUDatoteku(writer, "IBAN " + redniBrojRacuna + ". ra�una: " + oib);

		// pozivanje metode unesiValjanuValutu koja od korisnika trazi da
		// unosi valute dok ne unese podrzanu valutu
		Valuta valuta = unesiValjanuValutu(unos);
		zapisiUDatoteku(writer, "Valuta " + redniBrojRacuna + ". ra�una: " + valuta);

		// pozivanje metode unesiValjaniIznos koja od korisnika trazi da
		// unosi stanja racuna dok ne unese valjani iznos
		BigDecimal stanje = unesiValjaniIznos(unos, "Unesite stanje racuna druge osobe: ", "Unesen neispravan iznos za stanje drugog ra�una!", "Uneseno stanje racuna druge osobe: ");
		zapisiUDatoteku(writer, "Stanje " + redniBrojRacuna + ". ra�una: " + stanje + valuta);

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
