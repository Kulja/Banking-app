package hr.vvg.programiranje.java.gui;

import hr.vvg.programiranje.java.banka.DeviznaTransakcija;
import hr.vvg.programiranje.java.banka.DevizniRacun;
import hr.vvg.programiranje.java.banka.Racun;
import hr.vvg.programiranje.java.banka.TekuciRacun;
import hr.vvg.programiranje.java.banka.Transakcija;
import hr.vvg.programiranje.java.iznimke.NedozvoljenoStanjeRacunaException;
import hr.vvg.programiranje.java.osoba.Osoba;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Predstavlja pocetni ekran aplikacije.
 * 
 * @author Kulja
 *
 */
public class GlavniEkran {

	private JFrame frmBankingApp;
	private static List<Osoba> listaOsoba;
	private static List<Racun> listaRacuna;
	
	private static JComboBox<Racun> prviRacunComboBox;
	private static JComboBox<Racun> drugiRacunComboBox;
	private static JPanel stanjePrvogRacunaPanel;
	private static JPanel stanjeDrugogRacunaPanel;
	private static final Logger logger = LoggerFactory.getLogger(GlavniEkran.class);
	private static JTable transakcijeTable;
	private JTextField iznosTransakcijeTextField;

	/**
	 * Pokretanje aplikacije.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GlavniEkran window = new GlavniEkran();
					window.frmBankingApp.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Kreiranje ekrana.
	 */
	public GlavniEkran() {
		initialize();
	}

	/**
	 * Inicijalizacija sadrzaja pocetnog okvira.
	 */
	private void initialize() {
		
		listaOsoba = new ArrayList<Osoba>();
		listaRacuna = new ArrayList<Racun>();
		
		frmBankingApp = new JFrame();
		frmBankingApp.setTitle("Banking");
		frmBankingApp.setBounds(100, 100, 478, 278);
		frmBankingApp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenuBar menuBar = new JMenuBar();
		frmBankingApp.setJMenuBar(menuBar);
		
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
		frmBankingApp.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel racuniPanel = new JPanel();
		frmBankingApp.getContentPane().add(racuniPanel, BorderLayout.NORTH);
		racuniPanel.setLayout(new BoxLayout(racuniPanel, BoxLayout.X_AXIS));
		
		JPanel panelPrviRacun = new JPanel();
		panelPrviRacun.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Polazni ra\u010Dun", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		racuniPanel.add(panelPrviRacun);
		panelPrviRacun.setLayout(new BoxLayout(panelPrviRacun, BoxLayout.Y_AXIS));
		
		stanjePrvogRacunaPanel = new JPanel();
		panelPrviRacun.add(stanjePrvogRacunaPanel);
		stanjePrvogRacunaPanel.setVisible(false);
		
		JLabel stanjePrvogRacunaTextLabel = new JLabel("Stanje ra\u010Duna:");
		stanjePrvogRacunaPanel.add(stanjePrvogRacunaTextLabel);
		
		final JLabel stanjePrvogRacunaLabel = new JLabel("0");
		stanjePrvogRacunaPanel.add(stanjePrvogRacunaLabel);
		
		final JLabel valutaPrvogRacunaLabel = new JLabel("KN");
		stanjePrvogRacunaPanel.add(valutaPrvogRacunaLabel);
		
		prviRacunComboBox = new JComboBox<Racun>();
		prviRacunComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Racun prviRacun = prviRacunComboBox.getItemAt(prviRacunComboBox.getSelectedIndex());
				if (prviRacun == null) {
					prviRacun = listaRacuna.get(0);
				}
				if (prviRacun instanceof TekuciRacun) {
					stanjePrvogRacunaLabel.setText(prviRacun.getStanje().toString());
					valutaPrvogRacunaLabel.setText("KN");
				} else {
					stanjePrvogRacunaLabel.setText(prviRacun.getStanje().toString());
					valutaPrvogRacunaLabel.setText(((DevizniRacun) prviRacun).getValuta().toString());
				}
				stanjePrvogRacunaPanel.setVisible(true);
				
			}
		});
		panelPrviRacun.add(prviRacunComboBox);
		
		JPanel panelDrugiRacun = new JPanel();
		panelDrugiRacun.setBorder(new TitledBorder(null, "Dolazni ra\u010Dun", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		racuniPanel.add(panelDrugiRacun);
		panelDrugiRacun.setLayout(new BoxLayout(panelDrugiRacun, BoxLayout.Y_AXIS));
		
		stanjeDrugogRacunaPanel = new JPanel();
		panelDrugiRacun.add(stanjeDrugogRacunaPanel);
		stanjeDrugogRacunaPanel.setVisible(false);
		
		JLabel stanjeDrugogRacunaTextLabel = new JLabel("Stanje ra\u010Duna:");
		stanjeDrugogRacunaPanel.add(stanjeDrugogRacunaTextLabel);
		
		final JLabel stanjeDrugogRacunaLabel = new JLabel("0");
		stanjeDrugogRacunaPanel.add(stanjeDrugogRacunaLabel);
		
		final JLabel valutaDrugogRacunaLabel = new JLabel("");
		stanjeDrugogRacunaPanel.add(valutaDrugogRacunaLabel);
		
		drugiRacunComboBox = new JComboBox<>();
		drugiRacunComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Racun drugiRacun = drugiRacunComboBox.getItemAt(drugiRacunComboBox.getSelectedIndex());
				if (drugiRacun == null) {
					drugiRacun = listaRacuna.get(0);
				}
				if (drugiRacun instanceof TekuciRacun) {
					stanjeDrugogRacunaLabel.setText(drugiRacun.getStanje().toString());
					valutaDrugogRacunaLabel.setText("KN");
				} else {
					stanjeDrugogRacunaLabel.setText(drugiRacun.getStanje().toString());
					valutaDrugogRacunaLabel.setText(((DevizniRacun) drugiRacun).getValuta().toString());
				}
				stanjeDrugogRacunaPanel.setVisible(true);
				
			}
		});
		panelDrugiRacun.add(drugiRacunComboBox);
		
		JPanel iznosTransakcijePanel = new JPanel();
		frmBankingApp.getContentPane().add(iznosTransakcijePanel, BorderLayout.CENTER);
		
		JLabel unosIznosaTransakcijeLabel = new JLabel("Iznos transakcije:");
		iznosTransakcijePanel.add(unosIznosaTransakcijeLabel);
		
		iznosTransakcijeTextField = new JTextField();
		iznosTransakcijePanel.add(iznosTransakcijeTextField);
		iznosTransakcijeTextField.setColumns(10);
		
		JLabel iznosTransakcijeValutaLabel = new JLabel("KN");
		iznosTransakcijePanel.add(iznosTransakcijeValutaLabel);
		
		JButton izvrsavanjeTransakcijeButton = new JButton("Izvr\u0161i transakciju");
		izvrsavanjeTransakcijeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Racun prviRacun = prviRacunComboBox.getItemAt(prviRacunComboBox.getSelectedIndex());
				Racun drugiRacun = drugiRacunComboBox.getItemAt(drugiRacunComboBox.getSelectedIndex());
				if (prviRacun instanceof TekuciRacun && drugiRacun instanceof TekuciRacun) {
					Transakcija<Racun, Racun> transakcija = new Transakcija<>(prviRacun, drugiRacun, new BigDecimal(iznosTransakcijeTextField.getText()));
					try {
						transakcija.provediTransakciju();
						dodajTransakcijuUTablicu(prviRacun, drugiRacun, transakcija.getIznos());
						stanjePrvogRacunaLabel.setText(prviRacun.getStanje().toString());
						stanjeDrugogRacunaLabel.setText(drugiRacun.getStanje().toString());
					} catch (NedozvoljenoStanjeRacunaException ex) {
						String message = "Transakcija se nije provela! Nedozvoljeno stanje raèuna!";
						System.out.println(message);
						JOptionPane.showMessageDialog(null, message);
						logger.error(message, ex);
					}
				} else if (prviRacun instanceof TekuciRacun && drugiRacun instanceof DevizniRacun) {
					DeviznaTransakcija<TekuciRacun, DevizniRacun> transakcija = new DeviznaTransakcija<>((TekuciRacun) prviRacun, (DevizniRacun) drugiRacun, new BigDecimal(iznosTransakcijeTextField.getText()));
					try {
						transakcija.provediTransakciju();
						dodajTransakcijuUTablicu(prviRacun, drugiRacun, transakcija.getIznos());
						stanjePrvogRacunaLabel.setText(prviRacun.getStanje().toString());
						stanjeDrugogRacunaLabel.setText(drugiRacun.getStanje().toString());
					} catch (NedozvoljenoStanjeRacunaException ex) {
						String message = "Transakcija se nije provela! Nedozvoljeno stanje raèuna!";
						System.out.println(message);
						JOptionPane.showMessageDialog(null, message);
						logger.error(message, ex);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Odabrali ste nepodržanu transakciju!");
				}		
				
			}
		});
		iznosTransakcijePanel.add(izvrsavanjeTransakcijeButton);
		
		JPanel popisTransakcijaPanel = new JPanel();
		frmBankingApp.getContentPane().add(popisTransakcijaPanel, BorderLayout.SOUTH);
		
		transakcijeTable = new JTable();
		transakcijeTable.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Rbr.", "Polazni ra\u010Dun", "Dolazni ra\u010Dun", "Iznos", "Valuta" }));
		transakcijeTable.getColumnModel().getColumn(0).setPreferredWidth(35);
		transakcijeTable.getColumnModel().getColumn(0).setMinWidth(35);
		transakcijeTable.getColumnModel().getColumn(4).setPreferredWidth(50);
		transakcijeTable.setPreferredScrollableViewportSize(new Dimension(450, 70));
		JScrollPane transakcijeTableScrollPane = new JScrollPane(transakcijeTable);
		popisTransakcijaPanel.add(transakcijeTableScrollPane);
		
	}
	
	/**
	 * Dodaje sve racune iz liste racuna u oba combo boxa.
	 */
	public static void osvjeziPopisRacuna() {
		prviRacunComboBox.removeAllItems();
		drugiRacunComboBox.removeAllItems();
		for (Racun racun : listaRacuna) {
			prviRacunComboBox.addItem(racun);
			drugiRacunComboBox.addItem(racun);
		}
	}
	
	/**
	 * Dodaje transakciju u tablicu transakcija.
	 * 
	 * @param polazni podatak o polaznom racunu s kojeg je prebacen iznos
	 * @param dolazni podatak o odlaznom racunu na koji je prebacen iznos
	 * @param iznos podatak o iznosu koji je prebacen s polaznog na odlazni racun
	 */
	public static void dodajTransakcijuUTablicu(Racun polazni, Racun dolazni, BigDecimal iznos) {
		Object[] podaciUTablici = new Object[5];
		podaciUTablici[0] = transakcijeTable.getRowCount() + 1 + ".";
		podaciUTablici[1] = ((TekuciRacun) polazni).getBrojRacuna();
		if (dolazni instanceof TekuciRacun) {
			podaciUTablici[2] = ((TekuciRacun) dolazni).getBrojRacuna();
		} else if (dolazni instanceof DevizniRacun) {
			podaciUTablici[2] = ((DevizniRacun) dolazni).getIban();
		}
		podaciUTablici[3] = iznos.toString();
		podaciUTablici[4] = "KN";
		((DefaultTableModel) transakcijeTable.getModel()).addRow(podaciUTablici);
	}

}
