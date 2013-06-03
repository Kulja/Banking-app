package hr.vvg.programiranje.java.sortiranje;

import hr.vvg.programiranje.java.banka.Transakcija;

import java.util.Comparator;

/**
 * Predstavlja entitet sortirane transakcije koja imeplementira comparator sucelje.
 * 
 * @author Kulja
 *
 */
public class SortiranjeTransakcija implements Comparator<Transakcija<?, ?>> {

	/** 
	 * Usporeduje iznose transakcija i sortira ih od najvise prema najmanjoj.
	 * 
	 * @param transakcija1 podatak o prvoj transakciji
	 * @param transakcija2 podatak o drugoj transakciji
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Transakcija<?, ?> transakcija1, Transakcija<?, ?> transakcija2) {
		return transakcija1.getIznos().compareTo(transakcija2.getIznos()) * (-1);
	}

}
