package hr.vvg.programiranje.java.gui;

import hr.vvg.programiranje.java.banka.Racun;
import hr.vvg.programiranje.java.osoba.Osoba;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GlavniEkran {

	private JFrame frame;
	private List<Osoba> listaOsoba;
	private List<Racun> listaRacuna;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlavniEkran window = new GlavniEkran();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GlavniEkran() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		listaOsoba = new ArrayList<Osoba>();
		listaRacuna = new ArrayList<Racun>();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu menuOsoba = new JMenu("Osobe");
		menuBar.add(menuOsoba);
		
		JMenuItem menuItemDodajNovuOsobu = new JMenuItem("Dodaj novu osobu");
		menuItemDodajNovuOsobu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UnosenjeNoveOsobeFrame frame = new UnosenjeNoveOsobeFrame(listaOsoba);
				frame.prikaziEkran();
			}
		});
		menuOsoba.add(menuItemDodajNovuOsobu);
		
		JMenu menuRacun = new JMenu("Ra\u010Duni");
		menuBar.add(menuRacun);
		
		JMenuItem menuItemDodajNoviRaun = new JMenuItem("Dodaj novi ra\u010Dun");
		menuItemDodajNoviRaun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UnosenjeNovogRacunaFrame frame = new UnosenjeNovogRacunaFrame(listaOsoba, listaRacuna);
				frame.prikaziEkran();
			}
		});
		menuRacun.add(menuItemDodajNoviRaun);
	}

}
