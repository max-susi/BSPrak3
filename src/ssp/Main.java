package ssp;


public class Main {


	public static void main(String[] args) throws InterruptedException {
		int dauer = 5000;
		Tisch tisch = new Tisch("Markus", "Connor");
		
		tisch.starteSpiel();

		Thread.sleep(dauer);
		tisch.spielbeenden();


	}

}
