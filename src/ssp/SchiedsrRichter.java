package Aufgabe2;

public class SchiedsrRichter extends Thread {

    public final int MAX_IDLE_TIME = 100; //max. Pause zwischen den Pufferzugriffen

    private Tisch<SpielObjekt> currentTisch;
    private SpielObjekt item;

    public SchiedsrRichter(Tisch<SpielObjekt> tisch){
        currentTisch = tisch;
    }

    public void run(){
        try {
            while(!isInterrupted()){
                statusmeldungZugriffswunsch();

                //SpielObjekt vom Tisch nehmen, dazu Tisch-Zugruffsmethode aufrufen
                //--> Synchronisation ueber den Tisch
                currentTisch.judge();

               // pause();

            }
        } catch (InterruptedException ex){
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
}
