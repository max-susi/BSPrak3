package ssp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Tisch {
	
	public int runden = 0;
	public int gewinner1 = 0;
	public int gewinner2 = 0;
	public int unentschieden = 0;
	private Semaphore turn = new Semaphore(1,true);
	//TODO 2 Zustände müssen nur gemerkt werden
	private  List<SpieltischPosition> spieltisch = new ArrayList<>();
	private Spieler spieler1;
	private Spieler spieler2;
	private Schiedsrichter schiri;

	public Tisch(String spieler1, String spieler2) {
		this.spieler1 = new Spieler(spieler1, this);
		this.spieler2 = new Spieler(spieler2, this);
	
	}
	
	public void starteSpiel() throws InterruptedException {
		schiri = new Schiedsrichter(this);
		spieler1.start();
		spieler2.start();
		Thread.sleep(200);
		schiri.start();
	}

	public void spielbeenden() {
		spieler1.interrupt();
		spieler2.interrupt();
		schiri.interrupt();
		druckeAuswertung();
		
	}
	
	public void druckeAuswertung() {
		System.out.println("--------------------------");
		System.out.println("Auswertung:");
		System.out.println("Spiele gespielt: " + runden);
		System.out.println("Davon unentschieden: " + unentschieden);
		System.out.println("Gewinne: " + (runden - unentschieden));
	}

	public synchronized void spielzug(Spieler spieler) {
		System.out.println(spieler.getSpielername() + ": Ching chang chong!");
		getSpieltisch().add(new SpieltischPosition(spieler, spielen()));
		
	}
	
    private Spielobjekt spielen(){
        return Spielobjekt.values()[new Random().nextInt(3)];
    }

	public List<SpieltischPosition> getSpieltisch() {
		return spieltisch;
	}

	public void setSpieltisch(List<SpieltischPosition> spieltisch) {
		this.spieltisch = spieltisch;
	}

	public void rundeerhoehen() {
		runden++;
	}
	
	public void gewinnSpieler1(){
		gewinner1++;
	}
	
	public void gewinnSpieler2(){
		gewinner2++;
	}

	public void erhoeheunentschieden() {
		unentschieden++;
	}
	

}
