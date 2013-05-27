package hr.vvg.programiranje.java.baza;

import hr.vvg.programiranje.java.banka.DeviznaTransakcija;
import hr.vvg.programiranje.java.banka.DevizniRacun;
import hr.vvg.programiranje.java.banka.Racun;
import hr.vvg.programiranje.java.banka.TekuciRacun;
import hr.vvg.programiranje.java.banka.Transakcija;
import hr.vvg.programiranje.java.banka.Valuta;
import hr.vvg.programiranje.java.osoba.Osoba;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BazaPodataka {
	private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);

	private static Connection kreirajVezuSBazom() {
		Connection veza = null;
		try {
			veza = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/VVGBankarstvo", "vvg", "");
		} catch (SQLException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod spajanja s bazom podataka!");
		}
		return veza;
	}

	private static void odspojiSeOdBaze(Connection veza) {
		try {
			veza.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod odspajanja s bazom podataka!");
		}
	}

	public static void spremiOsobu(Osoba osoba) {
		Connection veza = kreirajVezuSBazom();
		try {
			PreparedStatement stmt = veza.prepareStatement("INSERT INTO RAZVOJ.OSOBA (PREZIME, IME, OIB) VALUES(?, ?, ?)");
			stmt.setString(1, osoba.getPrezime());
			stmt.setString(2, osoba.getIme());
			stmt.setString(3, osoba.getOib());
			stmt.executeUpdate();
		} catch(SQLException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod spremanja podataka o osobi!");
		}
		odspojiSeOdBaze(veza);
	}

	public static List<Osoba> dohvatiSveOsobe() {
		Connection veza = kreirajVezuSBazom();
		List<Osoba> listaOsoba = new ArrayList<Osoba>();
		try {
			Statement stmt = veza.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM RAZVOJ.OSOBA");
			while (rs.next()) {
				int id = rs.getInt("id");
				String prezime = rs.getString("prezime");
				String ime = rs.getString("ime");
				String oib = rs.getString("oib");
				Osoba osoba = new Osoba(id, prezime, ime, oib);
				listaOsoba.add(osoba);
			}
		} catch(SQLException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod dohvaæanja svih osoba!");
		}
		odspojiSeOdBaze(veza);
		return listaOsoba;
	}

	public static Osoba dohvatiOsobu(Integer id) {
		Connection veza = kreirajVezuSBazom();
		Osoba osoba = null;
		try {
			PreparedStatement stmt = veza.prepareStatement("SELECT * FROM RAZVOJ.OSOBA WHERE ID = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				String prezime = rs.getString("prezime");
				String ime = rs.getString("ime");
				String oib = rs.getString("oib");
				osoba = new Osoba(id, prezime, ime, oib);
			}
		} catch(SQLException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod dohvaæanja podataka o osobi!");
		}
		odspojiSeOdBaze(veza);
		return osoba;
	}

	public static void spremiRacun(Racun racun) {
		Connection veza = kreirajVezuSBazom();
		try {
			PreparedStatement stmt = veza.prepareStatement("INSERT INTO RAZVOJ.RACUN (VLASNIK_ID, STANJE, OZNAKA, VALUTA) VALUES(?, ?, ?, ?)");
			stmt.setInt(1, racun.getVlasnikRacuna().getId());
			stmt.setBigDecimal(2, racun.getStanje());
			if (racun instanceof TekuciRacun) {
				stmt.setString(3, ((TekuciRacun) racun).getBrojRacuna());
				stmt.setString(4, Valuta.HRK.toString());
			} else if (racun instanceof DevizniRacun) {
				stmt.setString(3, ((DevizniRacun) racun).getIban());
				stmt.setString(4, ((DevizniRacun) racun).getValuta().toString());
			}
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod spremanja podataka o raèunu!");
		}
		odspojiSeOdBaze(veza);
	}

	public static List<Racun> dohvatiSveRacune() {
		Connection veza = kreirajVezuSBazom();
		List<Racun> listaRacuna = new ArrayList<Racun>();
		try {
			Statement stmt = veza.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM RAZVOJ.RACUN");
			while (rs.next()) {
				int id = rs.getInt("id");
				int vlasnikId = rs.getInt("vlasnik_id");
				BigDecimal stanje = rs.getBigDecimal("stanje");
				String oznaka = rs.getString("oznaka");
				String valuta = rs.getString("valuta");
				Osoba vlasnik = dohvatiOsobu(vlasnikId);
				if (valuta.equals(Valuta.HRK.toString())) {
					TekuciRacun tekuciRacun = new TekuciRacun(id, vlasnik, stanje, oznaka);
					listaRacuna.add(tekuciRacun);
				} else {
					DevizniRacun devizniRacun = new DevizniRacun(id, vlasnik, stanje, oznaka, Valuta.valueOf(valuta));
					listaRacuna.add(devizniRacun);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod dohvaæanja svih raèuna!");
		}
		odspojiSeOdBaze(veza);
		return listaRacuna;
	}

	public static Racun dohvatiRacun(Integer id) {
		Connection veza = kreirajVezuSBazom();
		Racun racun = null;
		try {
			PreparedStatement stmt = veza.prepareStatement("SELECT * FROM RAZVOJ.RACUN WHERE ID = ?");
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int vlasnikId = rs.getInt("vlasnik_id");
				BigDecimal stanje = rs.getBigDecimal("stanje");
				String oznaka = rs.getString("oznaka");
				String valuta = rs.getString("valuta");
				Osoba vlasnik = dohvatiOsobu(vlasnikId);
				if (valuta.equals(Valuta.HRK.toString())) {
					racun = new TekuciRacun(id, vlasnik, stanje, oznaka);
				} else {
					racun = new DevizniRacun(id, vlasnik, stanje, oznaka, Valuta.valueOf(valuta));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod dohvaæanja podataka o raèunu!");
		}
		odspojiSeOdBaze(veza);
		return racun;
	}

	public static void azuzirajStanjeRacuna(Racun racun) {
		Connection veza = kreirajVezuSBazom();
		try {
			PreparedStatement stmt = veza.prepareStatement("UPDATE RAZVOJ.RACUN SET STANJE = ? WHERE ID = ?");
			stmt.setBigDecimal(1, racun.getStanje());
			stmt.setInt(2, racun.getId());
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod ažuriranja stanja raèuna!");
		}
		odspojiSeOdBaze(veza);
	}

	public static void spremiTransakciju(Transakcija<?, ?> transakcija) {
		Connection veza = kreirajVezuSBazom();
		try {
			PreparedStatement stmt = veza.prepareStatement("INSERT INTO RAZVOJ.TRANSAKCIJA (POLAZNI_RACUN_ID, ODLAZNI_RACUN_ID, IZNOS, VALUTA, DATUM) VALUES(?, ?, ?, ?, ?)");
			stmt.setInt(1, transakcija.getPolazni().getId());
			stmt.setInt(2, transakcija.getOdlazni().getId());
			stmt.setBigDecimal(3, transakcija.getIznos());
			if (transakcija.getPolazni() instanceof TekuciRacun) {
				stmt.setString(4, Valuta.HRK.toString());
			} else if (transakcija.getPolazni() instanceof DevizniRacun) {
				stmt.setString(4, ((DevizniRacun) transakcija.getPolazni()).getValuta().toString());
			}
			stmt.setDate(5, convertDate(new java.util.Date()));
			stmt.executeUpdate();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod spremanja podataka o transakciji!");
		}
		BazaPodataka.azuzirajStanjeRacuna(transakcija.getPolazni());
		BazaPodataka.azuzirajStanjeRacuna(transakcija.getOdlazni());
		odspojiSeOdBaze(veza);
	}

	public static List<Transakcija<?, ?>> dohvatiSveTransakcije() {
		Connection veza = kreirajVezuSBazom();
		List<Transakcija<?, ?>> listaRacuna = new ArrayList<Transakcija<?, ?>>();
		try {
			Statement stmt = veza.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM RAZVOJ.TRANSAKCIJA");
			while (rs.next()) {
				int id = rs.getInt("id");
				int polazniRacunId = rs.getInt("polazni_racun_id");
				int odlazniRacunId = rs.getInt("odlazni_racun_id");
				BigDecimal iznos = rs.getBigDecimal("iznos");
				//String valuta = rs.getString("valuta");
				Date datumTransakcije = rs.getDate("datum");
				Racun polazniRacun = dohvatiRacun(polazniRacunId);
				Racun odlazniRacun = dohvatiRacun(odlazniRacunId);
				if (polazniRacun instanceof TekuciRacun && odlazniRacun instanceof TekuciRacun) {
					Transakcija<TekuciRacun, TekuciRacun> transakcija = new Transakcija<>(id, (TekuciRacun) polazniRacun, (TekuciRacun) odlazniRacun, iznos, datumTransakcije);
					listaRacuna.add(transakcija);
				} else {
					DeviznaTransakcija<TekuciRacun, DevizniRacun> transakcija = new DeviznaTransakcija<>(id, (TekuciRacun) polazniRacun, (DevizniRacun) odlazniRacun, iznos, datumTransakcije);
					listaRacuna.add(transakcija);
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			JOptionPane.showMessageDialog(null, "Došlo je do pogreške kod dohvaæanja podataka o transakciji!");
		}
		odspojiSeOdBaze(veza);
		return listaRacuna;
	}

	private static java.sql.Date convertDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}
}