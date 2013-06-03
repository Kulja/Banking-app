package hr.vvg.programiranje.java.nit;

import hr.vvg.programiranje.java.banka.Tecaj;
import hr.vvg.programiranje.java.banka.Tecajnica;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import org.joda.time.DateTime;

public class DohvatTecajevaNit implements Runnable {
	
	private JLabel labelaZaOsvjezavanje;
	private List<Tecaj> listaTecajeva;
	
	public DohvatTecajevaNit(JLabel labelaZaOsvjezavanje) {
		this.labelaZaOsvjezavanje = labelaZaOsvjezavanje;
		this.listaTecajeva = new ArrayList<>();
	}
	
	public List<Tecaj> dohvatiPosljednjuListuTecajeva() {
		return this.listaTecajeva;
	}

	@Override
	public void run() {
		
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			DateTime date = new DateTime();
			this.listaTecajeva = Tecajnica.dohvatiTecajeve(date);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy. HH:mm:ss");
			this.labelaZaOsvjezavanje.setText("Posljednji dohva\u0107eni te\u010Daj u vrijeme: " + sdf.format(date.toDate()));
		}
	}

}
