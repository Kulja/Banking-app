package hr.vvg.programiranje.java.banka;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.joda.time.DateTime;

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
	public static List<Tecaj> dohvatiTecajeve(DateTime date) {
		
		List<Tecaj> listaTecajeva = new ArrayList<>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		InputStream in = null;
		try {
			while(true) {
				String trenutniDatumString = sdf.format(date.toDate());
				URL u = new URL("http://www.hnb.hr/tecajn/f" + trenutniDatumString + ".dat");
				try {
					in = u.openStream();
					break;
				} catch (FileNotFoundException ex) {
					date = date.minusDays(1);
				}
			}

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
