package mensa;



public class Main {

    public static void main(String[] args) throws InterruptedException {
        int kassen = 3;
        int studenten = 10;
        int dauer = 10000;

        System.out.println("Eine neue Mensa Simulation wird gestartet.");
        System.out.println("Anzahl der Kassen:       " + kassen);
        System.out.println("Anzahl der Studenten:    " + studenten);
        System.out.println("Öffnungszeit (Sekunden): " + dauer/1000);
        System.out.println("\n");

        Mensa mensa = new Mensa(kassen, studenten);

        System.out.println("Die Mensa wird geöffnet.");

        mensa.kassenOeffnen();

        Thread.sleep(dauer);

        System.out.println("\n\nDie Mensa wird wieder geschlossen.");

        mensa.kassenSchliessen();

        System.out.println("Die Mensa ist geschlossen. Bis zum nächsten Mal!\n\n");
    }
}
