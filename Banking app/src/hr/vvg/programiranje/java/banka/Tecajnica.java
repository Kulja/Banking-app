package hr.vvg.programiranje.java.banka;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Predstavlja entitet tecajnice
 * 
 * @author Kulja
 *
 */
public class Tecajnica {
	
	/**
	 * Kreira listu podrzanih tecaja s njihovim vrijednostima prema kuni.
	 * 
	 * @return listu podrzanih tecajeva.
	 */
	public static List<Tecaj> dohvatiTecajeve() {
		List<Tecaj> listaValuta = new ArrayList<>();

		Tecaj eur = new Tecaj(Valuta.EUR, new BigDecimal("7.61"));
		Tecaj usd = new Tecaj(Valuta.USD, new BigDecimal("5.83"));
		Tecaj gbp = new Tecaj(Valuta.GBP, new BigDecimal("8.96"));

		listaValuta.add(eur);
		listaValuta.add(usd);
		listaValuta.add(gbp);
		
		return listaValuta;
	}
	
}
