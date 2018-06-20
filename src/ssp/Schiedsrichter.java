package ssp;

import java.util.*;

public class Schiedsrichter extends Thread {
	

	private Tisch tisch;

	@Override
	public void run() {
		while (!isInterrupted()) {
			System.out.println("schirri");
			tisch.rundeerhoehen();
			if (!bestimmeGewinner(tisch.getSpieltisch())) {
				tisch.erhoeheunentschieden();
			}
		}

	}

	public Schiedsrichter(Tisch tisch) {
		this.tisch = tisch;
		befuelleGewinnmatrix();
	}

	/*
	 * Key gewinnt gegen value. example: key=Schere value=Papier => Schere
	 * gewinnt gegen papier
	 */
	public Map<Spielobjekt, Spielobjekt> gewinnmatrix;

	private void befuelleGewinnmatrix() {
		gewinnmatrix = new HashMap<>();
		gewinnmatrix.put(Spielobjekt.valueOf("Schere"), Spielobjekt.valueOf("Papier"));
		gewinnmatrix.put(Spielobjekt.valueOf("Stein"), Spielobjekt.valueOf("Schere"));
		gewinnmatrix.put(Spielobjekt.valueOf("Papier"), Spielobjekt.valueOf("Stein"));
	}

	/*
	 * aufgebaut wie compare
	 * 
	 * return: 0 unentschieden -1 ergebnis1 gewinnt 1 ergebnis2 gewinnt
	 */
	public int werteSpielAus(Spielobjekt ergebnis1, Spielobjekt ergebnis2) {
		if (ergebnis1.equals(ergebnis2)) {
			return 0;
		}
		if (gewinnmatrix.get(ergebnis1).equals(ergebnis2)) {
			return -1;
		} else {
			return 1;
		}
	}

	/*
	 * return: false untentschieden true ein gewinner wurde ermittelt
	 */
	public boolean bestimmeGewinner(List<SpieltischPosition> spieltisch) {

		//TODO gewinne speichern
		
		switch (werteSpielAus(spieltisch.get(0).getSpielobjekt(), spieltisch.get(1).getSpielobjekt())) {
		case 0:
			System.out.println("unentschieden");
			return false;
		case -1:
			System.out.println(spieltisch.get(0).getSpieler().getSpielername() + " gewinnt!");
			break;
		case 1:
			System.out.println(spieltisch.get(1).getSpieler().getSpielername() + " gewinnt!");
			break;
		}

		return true;

	}

}
