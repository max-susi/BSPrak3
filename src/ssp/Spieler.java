package ssp;
import java.util.Random;

public class Spieler extends Thread{
    public final int MAX_IDLE_TIME = 100; //max. Pause zwischen den Pufferzugriffen in ms

    private Tisch<SpielObjekt> currentTisch;
    private SpielObjekt item;

    public Spieler(Tisch<SpielObjekt> tischBuffer){
        currentTisch = tischBuffer;
    }

    public void run(){
        try {
            while(!isInterrupted()){
                item = getSpielObjekt();

                statusmeldungZugriffswunsch();

                currentTisch.put(item);

              //  pause();
            }
        } catch (InterruptedException ex) {
            // Interrupt aufgetreten --> fertig
            System.err.println(this.getName() + " wurde erfolgreich interrupted!");
        }
    }

    public void statusmeldungZugriffswunsch() {
        /* Gib einen Zugriffswunsch auf der Konsole aus */
        System.err.println("                                           "
                + this.getName() + " moechte auf den Puffer zugreifen!");
    }

    public void pause() throws InterruptedException {
        /*
         * Verbraucher benutzen diese Methode, um fuer eine Zufallszeit untaetig
         * zu sein
         */
        int sleepTime = (int) (MAX_IDLE_TIME * Math.random());

        // Thread blockieren
        Thread.sleep(sleepTime);
    }

    private SpielObjekt getSpielObjekt(){
        SpielObjekt item;
        Random rand = new Random();
        int num = rand.nextInt(2);
        switch (num){
            case 0: item = SpielObjekt.SCHERE;
            break;
            case 1: item = SpielObjekt.STEIN;
            break;
            default: item = SpielObjekt.PAPIER;
        }
        return item;
    }

}



