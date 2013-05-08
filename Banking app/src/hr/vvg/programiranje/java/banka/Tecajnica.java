package hr.vvg.programiranje.java.banka;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Predstavlja entitet tecajnice
 * 
 * @author Kulja
 *
 */
public class Tecajnica {
	
	/**
	 * Dohvaca listu tecaja s HNB sajta i njihovim vrijednostima prema kuni.
	 * 
	 * @return listu podrzanih tecajeva.
	 */
	public static List<Tecaj> dohvatiTecajeve() {
		
		List<Tecaj> listaTecajeva = new ArrayList<>();
		
		try {
			URL u = new URL("http://www.hnb.hr/tecajn/f270413.dat");
			InputStream in = u.openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String line = "";
			int brojac = 0;
			
			while ((line = reader.readLine()) != null) {
				if (brojac == 0) {
					brojac++;
					continue;
				}
			
				String valuta = line.substring(3, 6);
				String brojJedinica = line.substring(6, 9);

				StringTokenizer tokenizer = new StringTokenizer(line, " ");
				tokenizer.nextToken();
				tokenizer.nextToken();
				String tecaj = tokenizer.nextToken();
				tecaj = tecaj.replace(',', '.');
				BigDecimal tecajBigDecimal = new BigDecimal(tecaj);
				BigDecimal brojJedinicaBigDecimal = new BigDecimal(brojJedinica);
				BigDecimal konacanTecaj = tecajBigDecimal.divide(brojJedinicaBigDecimal);

				Valuta novaValuta = Valuta.valueOf(valuta);
				Tecaj noviTecaj = new Tecaj(novaValuta, konacanTecaj);

				listaTecajeva.add(noviTecaj);
			}
			
		} catch(Throwable ex) {
			ex.printStackTrace();
		}
		
		return listaTecajeva;
	}
	
}
