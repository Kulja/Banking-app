package hr.vvg.programiranje.java.gui;

import hr.vvg.programiranje.java.osoba.Osoba;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Predstavlja ekran za unosenje osoba.
 * 
 * @author Kulja
 *
 */
public class UnosenjeNoveOsobeFrame extends JFrame {

	private static final long serialVersionUID = -1077892338765222221L;
	private JPanel contentPane;
	private JTextField textFieldIme;
	private JTextField textFieldPrezime;
	private JTextField textFieldOib;

	/**
	 * Prikaz ekrana.
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
	 * Kreiranje sadrzaja ekrana.
	 */
	public UnosenjeNoveOsobeFrame(final List<Osoba> listaOsoba) {
		setTitle("Dodaj novu osobu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 451, 146);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		JLabel labelIme = new JLabel("Ime: ");
		GridBagConstraints gbc_labelIme = new GridBagConstraints();
		gbc_labelIme.anchor = GridBagConstraints.EAST;
		gbc_labelIme.insets = new Insets(0, 0, 5, 5);
		gbc_labelIme.gridx = 0;
		gbc_labelIme.gridy = 0;
		contentPane.add(labelIme, gbc_labelIme);
		
		textFieldIme = new JTextField();
		GridBagConstraints gbc_textFieldIme = new GridBagConstraints();
		gbc_textFieldIme.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldIme.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldIme.gridx = 1;
		gbc_textFieldIme.gridy = 0;
		contentPane.add(textFieldIme, gbc_textFieldIme);
		textFieldIme.setColumns(10);
		
		JLabel labelPrezime = new JLabel("Prezime: ");
		GridBagConstraints gbc_labelPrezime = new GridBagConstraints();
		gbc_labelPrezime.anchor = GridBagConstraints.EAST;
		gbc_labelPrezime.insets = new Insets(0, 0, 5, 5);
		gbc_labelPrezime.gridx = 0;
		gbc_labelPrezime.gridy = 1;
		contentPane.add(labelPrezime, gbc_labelPrezime);
		
		textFieldPrezime = new JTextField();
		GridBagConstraints gbc_textFieldPrezime = new GridBagConstraints();
		gbc_textFieldPrezime.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldPrezime.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldPrezime.gridx = 1;
		gbc_textFieldPrezime.gridy = 1;
		contentPane.add(textFieldPrezime, gbc_textFieldPrezime);
		textFieldPrezime.setColumns(10);
		
		JLabel labelOib = new JLabel("OIB: ");
		GridBagConstraints gbc_labelOib = new GridBagConstraints();
		gbc_labelOib.anchor = GridBagConstraints.EAST;
		gbc_labelOib.insets = new Insets(0, 0, 5, 5);
		gbc_labelOib.gridx = 0;
		gbc_labelOib.gridy = 2;
		contentPane.add(labelOib, gbc_labelOib);
		
		textFieldOib = new JTextField();
		GridBagConstraints gbc_textFieldOib = new GridBagConstraints();
		gbc_textFieldOib.insets = new Insets(0, 0, 5, 0);
		gbc_textFieldOib.fill = GridBagConstraints.HORIZONTAL;
		gbc_textFieldOib.gridx = 1;
		gbc_textFieldOib.gridy = 2;
		contentPane.add(textFieldOib, gbc_textFieldOib);
		textFieldOib.setColumns(10);
		
		JButton buttonSpremiOsobu = new JButton("Spremi osobu");
		buttonSpremiOsobu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String ime = textFieldIme.getText();
				String prezime = textFieldPrezime.getText();
				String oib = textFieldOib.getText();
				Osoba novaOsoba = new Osoba(ime, prezime, oib);
				listaOsoba.add(novaOsoba);
				dispose();
				JOptionPane.showMessageDialog(null, "Uspješno ste unijeli osobu: " + ime + " " + prezime);
			}
		});
		GridBagConstraints gbc_buttonSpremiOsobu = new GridBagConstraints();
		gbc_buttonSpremiOsobu.gridwidth = 2;
		gbc_buttonSpremiOsobu.gridx = 0;
		gbc_buttonSpremiOsobu.gridy = 3;
		contentPane.add(buttonSpremiOsobu, gbc_buttonSpremiOsobu);
	}

}
