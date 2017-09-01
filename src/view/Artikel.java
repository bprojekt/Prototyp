package view;

import java.math.BigDecimal;

public class Artikel {
	BigDecimal preis;
	int menge;
	Artikel()
	{
		
	}
Artikel(BigDecimal preis, int menge){
	this.preis=preis;
	this.menge=menge;
}
}
