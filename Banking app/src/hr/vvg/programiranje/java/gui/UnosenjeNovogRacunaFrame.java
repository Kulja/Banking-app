package hr.vvg.programiranje.java.gui;

import hr.vvg.programiranje.java.banka.DevizniRacun;
import hr.vvg.programiranje.java.banka.Racun;
import hr.vvg.programiranje.java.banka.TekuciRacun;
import hr.vvg.programiranje.java.banka.Valuta;
import hr.vvg.programiranje.java.banka.VrstaRacuna;
import hr.vvg.programiranje.java.osoba.Osoba;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class UnosenjeNovogRacunaFrame extends JFrame {

	private static final long serialVersionUID = 1839582901166867577L;
	private JPanel contentPane;
	private JTextField textFieldStanjeRacuna;
	private JTextField textFieldBrojRacunaIban;

	/**
	 * Launch the application.
	 */
	public void prikaziEkran() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UnosenjeNovogRacunaFrame(final List<Osoba> listaOsoba, final List<Racun> listaRacuna) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 452, 188);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[] {0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		final JLabel labelStanjeRacuna = new JLabel("Stanje ra\u010Duna: ");
		GridBagConstraints gbc_labelStanjeRacuna = new GridBagConstraints();
		gbc_labelStanjeRacuna.anchor = GridBagConstraints.EAST;
		gbc_labelStanjeRacuna.insets = new Insets(0, 0, 5, 5);
		gbc_labelStanjeRacuna.gridx = 0;
		gbc_labelStanjeRacuna.gridy = 2;
		contentPane.add(labelStanjeRacuna, gbc_labelStanjeRacuna);
		
		textFieldStanjeRacuna = new JTextField();
		GridBagConstraints gbc_textFieldStanjeRacuna = new GridBagConstraints();
		gbc_textFieldStanjeRacuna.insets = new Insets(5, 0, 5, 5);
		gbc_textFieldStanjeRacuna.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldStanjeRacuna.gridx = 1;
		gbc_textFieldStanjeRacuna.gridy = 2;
		contentPane.add(textFieldStanjeRacuna, gbc_textFieldStanjeRacuna);
		textFieldStanjeRacuna.setColumns(10);
		
		JLabel labelVrstaRacuna = new JLabel("Vrsta ra\u010Duna: ");
		GridBagConstraints gbc_labelVrstaRacuna = new GridBagConstraints();
		gbc_labelVrstaRacuna.anchor = GridBagConstraints.EAST;
		gbc_labelVrstaRacuna.insets = new Insets(0, 0, 5, 5);
		gbc_labelVrstaRacuna.gridx = 0;
		gbc_labelVrstaRacuna.gridy = 0;
		contentPane.add(labelVrstaRacuna, gbc_labelVrstaRacuna);
		
		final JLabel labelBrojRacunaIban = new JLabel("Broj ra\u010Duna: ");
		GridBagConstraints gbc_labelBrojRacunaIban = new GridBagConstraints();
		gbc_labelBrojRacunaIban.anchor = GridBagConstraints.EAST;
		gbc_labelBrojRacunaIban.insets = new Insets(0, 0, 5, 5);
		gbc_labelBrojRacunaIban.gridx = 0;
		gbc_labelBrojRacunaIban.gridy = 3;
		contentPane.add(labelBrojRacunaIban, gbc_labelBrojRacunaIban);
		
		textFieldBrojRacunaIban = new JTextField();
		GridBagConstraints gbc_textFieldBrojRacunaIban = new GridBagConstraints();
		gbc_textFieldBrojRacunaIban.insets = new Insets(0, 0, 5, 5);
		gbc_textFieldBrojRacunaIban.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldBrojRacunaIban.gridx = 1;
		gbc_textFieldBrojRacunaIban.gridy = 3;
		contentPane.add(textFieldBrojRacunaIban, gbc_textFieldBrojRacunaIban);
		textFieldBrojRacunaIban.setColumns(10);

		final JComboBox<Valuta> comboBoxValuta = new JComboBox<Valuta>();
		comboBoxValuta.setMaximumRowCount(13);
		for (Valuta valuta : Valuta.values()) {
			comboBoxValuta.addItem(valuta);
		}
		GridBagConstraints gbc_comboBoxValuta = new GridBagConstraints();
		gbc_comboBoxValuta.anchor = GridBagConstraints.WEST;
		gbc_comboBoxValuta.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxValuta.gridx = 2;
		gbc_comboBoxValuta.gridy = 2;
		contentPane.add(comboBoxValuta, gbc_comboBoxValuta);
		
		final JComboBox<VrstaRacuna> comboBoxVrstaRacuna = new JComboBox<VrstaRacuna>();
		comboBoxVrstaRacuna.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VrstaRacuna odabranaVrstaRacuna = (VrstaRacuna)comboBoxVrstaRacuna.getSelectedItem();
				if (odabranaVrstaRacuna.equals(VrstaRacuna.DEVIZNI)) {
					comboBoxValuta.setEnabled(true);
					comboBoxValuta.removeItemAt(0);
					labelBrojRacunaIban.setText("IBAN");
					labelStanjeRacuna.setText("Stanje ra\u010Duna: ");
				} else {
					comboBoxValuta.setEnabled(false);
					if(!comboBoxValuta.getItemAt(0).equals(Valuta.valueOf("HRK"))) {
						comboBoxValuta.insertItemAt(Valuta.valueOf("HRK"), 0);
						comboBoxValuta.setSelectedIndex(0);
					}
					labelBrojRacunaIban.setText("Broj ra\u010Duna: ");
					labelStanjeRacuna.setText("Stanje ra\u010Duna: ");
				}
			}
		});
		for (VrstaRacuna vrstaRacuna : VrstaRacuna.values()) {
			comboBoxVrstaRacuna.addItem(vrstaRacuna);
		}
		GridBagConstraints gbc_comboBoxVrstaRacuna = new GridBagConstraints();
		gbc_comboBoxVrstaRacuna.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxVrstaRacuna.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxVrstaRacuna.gridx = 1;
		gbc_comboBoxVrstaRacuna.gridy = 0;
		contentPane.add(comboBoxVrstaRacuna, gbc_comboBoxVrstaRacuna);
		
		JLabel labelVlasnikRacuna = new JLabel("Vlasnik ra\u010Duna: ");
		GridBagConstraints gbc_labelVlasnikRacuna = new GridBagConstraints();
		gbc_labelVlasnikRacuna.anchor = GridBagConstraints.EAST;
		gbc_labelVlasnikRacuna.insets = new Insets(0, 0, 5, 5);
		gbc_labelVlasnikRacuna.gridx = 0;
		gbc_labelVlasnikRacuna.gridy = 1;
		contentPane.add(labelVlasnikRacuna, gbc_labelVlasnikRacuna);
		
		final JComboBox<Osoba> comboBoxVlasnikRacuna = new JComboBox<Osoba>();
		for (Osoba osoba : listaOsoba) {
			comboBoxVlasnikRacuna.addItem(osoba);
		}
		GridBagConstraints gbc_comboBoxVlasnikRacuna = new GridBagConstraints();
		gbc_comboBoxVlasnikRacuna.insets = new Insets(0, 0, 5, 5);
		gbc_comboBoxVlasnikRacuna.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBoxVlasnikRacuna.gridx = 1;
		gbc_comboBoxVlasnikRacuna.gridy = 1;
		contentPane.add(comboBoxVlasnikRacuna, gbc_comboBoxVlasnikRacuna);
		
		JButton buttonSpremiRacun = new JButton("Spremi ra\u010Dun");
		buttonSpremiRacun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VrstaRacuna odabranaVrstaRacuna = (VrstaRacuna) comboBoxVrstaRacuna.getSelectedItem();
				if (odabranaVrstaRacuna.equals(VrstaRacuna.DEVIZNI)) {
					String iban = textFieldBrojRacunaIban.getText();
					Valuta valuta = (Valuta) comboBoxValuta.getSelectedItem();
					BigDecimal stanjeRacuna = new BigDecimal(textFieldStanjeRacuna.getText());
					Osoba vlasnikRacuna = (Osoba) comboBoxVlasnikRacuna.getSelectedItem();
					DevizniRacun devizni = new DevizniRacun(vlasnikRacuna, stanjeRacuna, iban, valuta);
					listaRacuna.add(devizni);
					JOptionPane.showMessageDialog(null, "Uspješno ste dodali novi devizni raèun: " + iban);
				} else {
					String brojRacuna = textFieldBrojRacunaIban.getText();
					BigDecimal stanjeRacuna = new BigDecimal(textFieldStanjeRacuna.getText());
					Osoba vlasnikRacuna = (Osoba) comboBoxVlasnikRacuna.getSelectedItem();
					TekuciRacun tekuci = new TekuciRacun(vlasnikRacuna, stanjeRacuna, brojRacuna);
					listaRacuna.add(tekuci);
					JOptionPane.showMessageDialog(null, "Uspješno ste dodali novi tekuæi raèun: " + brojRacuna);
				}
				dispose();
			}
		});
		GridBagConstraints gbc_buttonSpremiRacun = new GridBagConstraints();
		gbc_buttonSpremiRacun.insets = new Insets(0, 0, 0, 5);
		gbc_buttonSpremiRacun.gridwidth = 3;
		gbc_buttonSpremiRacun.gridx = 0;
		gbc_buttonSpremiRacun.gridy = 4;
		contentPane.add(buttonSpremiRacun, gbc_buttonSpremiRacun);
	}

}
