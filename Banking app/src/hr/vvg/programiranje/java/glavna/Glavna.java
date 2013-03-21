package hr.vvg.programiranje.java.glavna;

import hr.vvg.programiranje.java.banka.Racun;
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
		BigDecimal stanje;
		
		// prikupljanje podataka o prvoj osobi i stanju njezinog racuna
		System.out.print("Unesite ime prve osobe: ");
		ime = unos.next();
		System.out.print("Unesite prezime prve osobe: ");
		prezime = unos.next();
		System.out.print("Unesite oib prve osobe: ");
		oib = unos.next();
		System.out.print("Unesite stanje racuna prve osobe: ");
		stanje = unos.nextBigDecimal();
		
		// kreiranje prve osobe
		Osoba prvaOsoba = new Osoba(ime, prezime, oib);
		// kreiranje racuna prve osobe
		Racun racunPrveOsobe = new Racun(prvaOsoba, stanje);
		
		// prikupljanje podataka o drugoj osobi i stanju njezinog racuna
		System.out.print("Unesite ime druge osobe: ");
		ime = unos.next();
		System.out.print("Unesite prezime druge osobe: ");
		prezime = unos.next();
		System.out.print("Unesite oib druge osobe: ");
		oib = unos.next();
		System.out.print("Unesite stanje racuna druge osobe: ");
		stanje = unos.nextBigDecimal();
		
		// kreiranje druge osobe
		Osoba drugaOsoba = new Osoba(ime, prezime, oib);
		// kreiranje racuna druge osobe
		Racun racunDrugeOsobe = new Racun(drugaOsoba, stanje);
		
		// dohvacanje iznosa kojeg zelimo prebaciti s jednog racuna na drugi
		System.out.print("Unesite iznos koji zelite prebaciti s prvog racuna na drugi: ");
		BigDecimal iznos = unos.nextBigDecimal();
		
		// kreiranje transakcije
		Transakcija transakcija = new Transakcija(racunPrveOsobe, racunDrugeOsobe, iznos);
		
		// provodenje same transakcije 
		transakcija.provediTransakciju();
		
		System.out.println("Stanje prvog racuna: " + racunPrveOsobe.getStanje());
		System.out.println("Stanje drugog racuna " + racunDrugeOsobe.getStanje());
		
		unos.close();
	}

}
