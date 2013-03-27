package hr.vvg.programiranje.java.glavna;

import hr.vvg.programiranje.java.banka.DeviznaTransakcija;
import hr.vvg.programiranje.java.banka.DevizniRacun;
import hr.vvg.programiranje.java.banka.TekuciRacun;
import hr.vvg.programiranje.java.banka.Transakcija;
import hr.vvg.programiranje.java.osoba.Osoba;

import java.math.BigDecimal;
import java.util.Scanner;

public class Glavna {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Scanner unos = new Scanner(System.in);
		String ime;
		String prezime;
		String oib;
		String brojRacuna;
		String iban;
		String valuta;
		BigDecimal stanje;
		
		// prikupljanje podataka o prvoj osobi i stanju njezinog racuna
		System.out.print("Unesite ime prve osobe: ");
		ime = unos.next();
		System.out.print("Unesite prezime prve osobe: ");
		prezime = unos.next();
		System.out.print("Unesite oib prve osobe: ");
		oib = unos.next();
		System.out.print("Unesite broj racuna prve osobe: ");
		brojRacuna = unos.next();
		System.out.print("Unesite stanje racuna prve osobe (KN): ");
		stanje = unos.nextBigDecimal();
		
		// kreiranje prve osobe
		Osoba prvaOsoba = new Osoba(ime, prezime, oib);
		// kreiranje tekuceg racuna prve osobe
		TekuciRacun racunPrveOsobe = new TekuciRacun(prvaOsoba, stanje, brojRacuna);
		
		// prikupljanje podataka o drugoj osobi i stanju njezinog racuna
		System.out.print("Unesite ime druge osobe: ");
		ime = unos.next();
		System.out.print("Unesite prezime druge osobe: ");
		prezime = unos.next();
		System.out.print("Unesite oib druge osobe: ");
		oib = unos.next();
		System.out.print("Unesite IBAN racuna druge osobe: ");
		iban = unos.next();
		System.out.print("Unesite valutu racuna druge osobe: ");
		valuta = unos.next();
		System.out.print("Unesite stanje racuna druge osobe: ");
		stanje = unos.nextBigDecimal();
		
		// kreiranje druge osobe
		Osoba drugaOsoba = new Osoba(ime, prezime, oib);
		// kreiranje deviznog racuna druge osobe
		DevizniRacun racunDrugeOsobe = new DevizniRacun(drugaOsoba, stanje, iban, valuta);
		
		// dohvacanje iznosa kojeg zelimo prebaciti s jednog racuna na drugi
		System.out.print("Unesite iznos (KN) koji zelite prebaciti s prvog racuna na drugi: ");
		BigDecimal iznos = unos.nextBigDecimal();
		
		// kreiranje transakcije
		Transakcija transakcija = new DeviznaTransakcija(racunPrveOsobe, racunDrugeOsobe, iznos);
		
		// provodenje same transakcije 
		transakcija.provediTransakciju();
		
		System.out.println("Stanje prvog racuna: " + racunPrveOsobe.getStanje() + "KN");
		System.out.println("Stanje drugog racuna " + racunDrugeOsobe.getStanje() + racunDrugeOsobe.getValuta());
		
		unos.close();
	}

}
