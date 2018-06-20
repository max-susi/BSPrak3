package mensa;


import java.util.Comparator;



public class Student extends Thread {

    private int matrikelnummer;
    private Mensa mensa;

    public Student(int matrikelnummer, Mensa mensa) {
        this.matrikelnummer = matrikelnummer;
        this.mensa = mensa;
    }

    @Override
    public void run() {
        try {
            while (!interrupted()) {
                System.err.println(String.format("%s sucht die schnellste Kasse.", this));

                Kasse schnellsteKasse = mensa.anstellen();

                System.err.println(String.format("%s stellt sich an %s an. ", this, schnellsteKasse));

                schnellsteKasse.kaufen();

                System.err.println(String.format("%s hat etwas an %s gekauft.", this, schnellsteKasse));

                mensa.kasseVerlassen(schnellsteKasse);

                System.err.println(String.format("%s geht jetzt essen.", this));

                essen();

                System.err.println(String.format("%s hat fertig gegessen.", this));
            }
        } catch (InterruptedException e) {
            System.err.println(String.format("%s muss wieder in die Vorlesung. (Interrupted)", this));
        }
    }

    private void essen() throws InterruptedException {
        Thread.sleep(MensaUtil.getZeitEssen());
    }

    @Override
    public String toString() {
        return "Student " + Integer.toString(matrikelnummer);
    }
}
