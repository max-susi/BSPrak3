package ssp;

import java.util.Random;

public class Spieler extends Thread{
    private String spielername;
	private Tisch tisch;
    
    public Spieler(String spielername, Tisch tisch) {
    	this.spielername = spielername;
    	this.tisch = tisch;
    }

    @Override
    public void run(){
    	
    	while(!isInterrupted()){
    		tisch.spielzug(this);
    		try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    	}
    }

    public String getSpielername() {
        return spielername;
    }

}
