package hr.vvg.programiranje.java.banka;

import java.math.BigDecimal;

// sucelje
public interface Devizna {
	
	BigDecimal mjenjacnica(BigDecimal polazniIznosKN, String valuta);

}
